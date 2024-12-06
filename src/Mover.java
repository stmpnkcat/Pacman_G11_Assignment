import java.util.*;
import javax.swing.JLabel;

/*
 * This class acts as a parent for other entities that move
 */
public class Mover extends JLabel{
	
	// Declaring row and column number
	private int row;
	private int column;
	
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
		this.row = row;
		this.column = column;
		this.board = board;
		
		mazeMatrix = board.getMazeMatrix();
	}

	//https://www.youtube.com/watch?v=-L-WgKMFuhE
	//used pseudocode
	public void updatePath(Cell targetCell) {
		for (Cell[] cellArray : mazeMatrix) {
			for (Cell cell : cellArray) {
				cell.setCostG(Integer.MAX_VALUE);
				cell.setCostH(Integer.MAX_VALUE);
				cell.setCost(Integer.MAX_VALUE);
			}
		}
		
		
		ArrayList<Cell> open = new ArrayList<>();
		ArrayList<Cell> closed = new ArrayList<>();
		
		Cell selfCell = mazeMatrix[getRow()][getColumn()];

		selfCell.setCostG(0);
		selfCell.setCostH(Math.abs(selfCell.getRow() - targetCell.getRow()) + 
						Math.abs(selfCell.getColumn() - targetCell.getColumn()));
		selfCell.setCost(selfCell.getCostH());
		
		open.add(selfCell);
		
		while (!open.isEmpty()) {
			
			open.sort(Comparator.comparing(Cell::getCost));
			closed.sort(Comparator.comparing(Cell::getCost));
			
			Cell current = open.get(0);

			open.remove(current);
			
			closed.add(current);
			
			if (current == targetCell) break;
			
			ArrayList<Cell> neighbours = new ArrayList<>();
			
			if (current.getRow() - 1 >= 0) neighbours.add(mazeMatrix[current.getRow() - 1][current.getColumn()]);
			if (current.getColumn() - 1 >= 0) neighbours.add(mazeMatrix[current.getRow()][current.getColumn() - 1]);
			if (current.getRow() + 1 < Settings.ROWS) neighbours.add(mazeMatrix[current.getRow() + 1][current.getColumn()]);
			if (current.getColumn() + 1 < Settings.COLUMNS) neighbours.add(mazeMatrix[current.getRow()][current.getColumn() + 1]);
			
			System.out.println("checking neighbour");
			for (Cell neighbour : neighbours) {
				if (neighbour.getId() == Settings.ID_WALL || closed.contains(neighbour))
					continue;
				
				int neighbourCostH = Math.abs(neighbour.getRow() - targetCell.getRow()) + 
						Math.abs(neighbour.getColumn() - targetCell.getColumn());
				int neighbourCostG = current.getCostG() + 1;
				int neighbourCost = neighbourCostH + neighbourCostG;
				
				if (current.getCost() < neighbourCost || !open.contains(neighbour)) {
					
					if (board.getIdOfMover(neighbour.getRow(), neighbour.getColumn()) == Settings.ID_GHOST) {
						neighbourCost+=Settings.NEIGHBOURING_PENALTY;
					}
					
					neighbour.setCostH(neighbourCostH);
					neighbour.setCostG(neighbourCostG);
					neighbour.setCost(neighbourCost);
					neighbour.setParent(current.getRow(), current.getColumn());
					
					if (!open.contains(neighbour)){
						open.add(neighbour);
					}
					
				}
				
			}
			
		}
		
	}
	
	public Stack<Integer> calcDirectionSequence (Cell target) {

		Stack<Integer> directionStack = new Stack<>();
		
		int currentRow = target.getRow();
		int currentColumn = target.getColumn();
		
		int dRow = 0;
		int dColumn = 0;
		
		Cell current = mazeMatrix[currentRow][currentColumn];
		
		int direction = -1;
		
		while (true) {
			currentRow = current.getRow();
			currentColumn = current.getColumn();
			
			current = mazeMatrix[current.getParentRow()][current.getParentColumn()];
			
			if (currentRow == getRow() && currentColumn == getColumn())
				return directionStack;
			
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
	
}
