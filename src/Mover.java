import java.util.*;

import javax.swing.*;

/*
 * This class acts as a parent for other entities that move
 */
public class Mover extends JLabel{
	
	// Setting the default row and column
	private int defaultRow;
	private int defaultColumn;

	// Declaring row and column number
	private int row;
	private int column;
	
	// Declare the board and maze matrix
	private Board board;
	private Cell[][] mazeMatrix;
	
	// Declaring variables for where to move
	private int dRow;
	private int dColumn;
	
	// Declaring variable for if an entity is dead
	private boolean isDead;

	// Constructor for when mover is initialized
	public Mover(Board board, int row, int column) {
		super();
		
		// Setting the fields
		defaultRow = row;
		defaultColumn = column;
		this.row = row;
		this.column = column;
		this.board = board;
		
		// Setthe maze matrix
		mazeMatrix = board.getMazeMatrix();
		
	}

	// https://www.youtube.com/watch?v=-L-WgKMFuhE
	// A* Algorithm
	// This algorithm assigns 3 costs, H cost, G cost, and total cost
	// Moving across one cell has a cost of one
	// Crossing a cell with a ghost will increase the cost by the Neighbouring Cost Penalty,
	// this discourages ghosts moving in a line and encourages coordinated attacks on the player
	// H cost is calculated using Manhattan distance to the target cell (heuristic)
	// G cost is the sum of G costs to get to the selected cell
	public void updatePath(Cell targetCell) {
		
		// Creating an arraylist of open cells, which are the next ones to be searched
		ArrayList<Cell> open = new ArrayList<>();
		
		// Creating an arraylist of closed cells, which have already been searched
		ArrayList<Cell> closed = new ArrayList<>();
		
		// Get the current cell the mover is on
		Cell selfCell = mazeMatrix[getRow()][getColumn()];

		// Set the base costs
		selfCell.setCostG(0); // Set the G cost, which is zero because it is 
		selfCell.setCostH(Math.abs(selfCell.getRow() - targetCell.getRow()) +
						Math.abs(selfCell.getColumn() - targetCell.getColumn())); // Set the H cost using Manhattan distance       
		selfCell.setCost(selfCell.getCostH()); // Set the cost to the cost H
		
		// Add the self cell to the open list
		open.add(selfCell);
		
		// While there are more cells to search
		while (!open.isEmpty()) {
			
			// Sort the two arrays
			open.sort(Comparator.comparing(Cell::getCost));
			closed.sort(Comparator.comparing(Cell::getCost));
			
			// Get the current cell
			Cell current = open.get(0);

			// Remove the current cell from the open list
			open.remove(current);
			
			// Add the current cell to the closed list
			closed.add(current);
			
			// Check if the open cell is the target cell
			if (current == targetCell) break;
			
			// Initialize a list for the neighbours
			ArrayList<Cell> neighbours = new ArrayList<>();
			
			// Check if the neighbours are valid, then add them
			if (current.getRow() - 1 >= 0) neighbours.add(mazeMatrix[current.getRow() - 1][current.getColumn()]);
			if (current.getColumn() - 1 >= 0) neighbours.add(mazeMatrix[current.getRow()][current.getColumn() - 1]);
			if (current.getRow() + 1 < PacManGame.ROWS) neighbours.add(mazeMatrix[current.getRow() + 1][current.getColumn()]);
			if (current.getColumn() + 1 < PacManGame.COLUMNS) neighbours.add(mazeMatrix[current.getRow()][current.getColumn() + 1]);
			
			// Check the 4 neighbours
			for (Cell neighbour : neighbours) {
				
				// If the neighbour is a wall or has already been checked, go to the next neighbour
				if (neighbour.getId() == PacManGame.ID_WALL || closed.contains(neighbour))
					continue;
				
				// Change the costs
				int neighbourCostH = Math.abs(neighbour.getRow() - targetCell.getRow()) + 
						Math.abs(neighbour.getColumn() - targetCell.getColumn());
				int neighbourCostG = current.getCostG() + 1; // Add one to the previous path cost
				int neighbourCost = neighbourCostH + neighbourCostG; // Total cost
				
				// Check if the neighbours have less cost than the current cell or it has not been searched yet
				if (current.getCost() < neighbourCost || !open.contains(neighbour)) {
					
					// If there is a ghost in the cell, add a penalty for going that way
					if (board.getIdOfMover(neighbour.getRow(), neighbour.getColumn()) == PacManGame.ID_GHOST) {
						neighbourCost += PacManGame.NEIGHBOURING_PENALTY;
					}
					
					// Set the costs
					neighbour.setCostH(neighbourCostH);
					neighbour.setCostG(neighbourCostG);
					neighbour.setCost(neighbourCost);
					neighbour.setParent(current.getRow(), current.getColumn()); // Set the parent of the neighbour
					
					// If neighbour hasn't been checked yet, add it
					if (!open.contains(neighbour))
						open.add(neighbour);
					
				}
				
			}
			
		}
		
	}
	
	// Method to get the direction sequence
	public Stack<Integer> calcDirectionSequence (Cell target) {

		// Initialize th direction stack
		Stack<Integer> directionStack = new Stack<>();
		
		// Get the current row an column
		int currentRow = target.getRow();
		int currentColumn = target.getColumn();
		
		// Initialize the delta row and column
		int dRow = 0;
		int dColumn = 0;
		
		// Declare the current cell
		Cell current = mazeMatrix[currentRow][currentColumn];
		
		// Initialize the direction
		int direction = -1;
		
		// Keep looping
		while (true) {
			
			// Set the row and column
			currentRow = current.getRow();
			currentColumn = current.getColumn();
			
			// Get the current cell
			current = mazeMatrix[current.getParentRow()][current.getParentColumn()];
			
			// If the target cell is equal to the current cell, return the sequence
			if (currentRow == getRow() && currentColumn == getColumn())
				return directionStack;
			
			// Get the delta values
			dRow = currentRow - current.getRow();
			dColumn = currentColumn - current.getColumn();
			
			// Sending the direction value based on the delta values
			if (dRow == 0 && dColumn == -1)
				direction = 0;
			else if (dRow == -1 && dColumn == 0)
				direction = 1;
			else if (dRow == 0 && dColumn == 1)
				direction = 2;
			else
				direction = 3;
			
			// Add the direction to the direction stack
			directionStack.add(direction);
			
		}
		
	}
	
	// This method moves the mover by the delta row and column, which is usually 1 or -1
	public void move() {
		
		row += dRow;
		column += dColumn;
		
	}
	
	// This method sets the delta values of the mover based on the directions
	public void setDirection(int direction) {
		
		// Resetting the delta values
		dRow = 0;
		dColumn = 0;
		
		// Changing the delta values based on the direction
		if (direction == 0)
			dColumn = -1;
		else if (direction == 1)
			dRow = -1;
		else if (direction == 2)
			dColumn = 1;
		else if (direction == 3)
			dRow = 1;
		
	}

	// This method gets the direction of the mover based on the current delta values
	public int getDirection() {
		
		// Sending the direction value based on the delta values
		if (dRow == 0 && dColumn == -1)
			return 0;
		else if (dRow == -1 && dColumn == 0)
			return 1;
		else if (dRow == 0 && dColumn == 1)
			return 2;
		else
			return 3;
		
	}
	
	// This method gets the next row based on the delta values
	public int getNextRow() {
		
		return row + dRow;
		
	}
	
	// This method gets the next column based on the delta values
	public int getNextColumn() {
		
		return column + dColumn;
		
	}

	// Initializing getters and setters
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getdRow() {
		return dRow;
	}

	public void setdRow(int dRow) {
		this.dRow = dRow;
	}

	public int getdColumn() {
		return dColumn;
	}

	public void setdColumn(int dColumn) {
		this.dColumn = dColumn;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Cell[][] getMazeMatrix() {
		return mazeMatrix;
	}

	public void setMazeMatrix(Cell[][] mazeMatrix) {
		this.mazeMatrix = mazeMatrix;
	}
	
	public int getDefaultRow() {
		return defaultRow;
	}

	public void setDefaultRow(int defaultRow) {
		this.defaultRow = defaultRow;
	}

	public int getDefaultColumn() {
		return defaultColumn;
	}

	public void setDefaultColumn(int defaultColumn) {
		this.defaultColumn = defaultColumn;
	}
	
}
