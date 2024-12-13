import java.util.Stack;

/*
 * This class controls the ghost movement
 * It has two phases: moving randomly, and pathfinding
 */
public class Ghost extends Mover{

	private Board board; // Declaring the board
	private Cell[][] mazeMatrix; // Declaring the maze matrix
	
	private boolean isVulnerable; // Declaring the vulnerable function
	private int vulnerableTime = 0; // Declaring the time the ghost has been vulnerable fore
	
	// This method is the constructor for when a ghost is created
	public Ghost(Board board, int row, int column) {
		
		// Send the board, row and column to parent
		super(board, row, column);

		// Set the board field
		this.board = board;
		
		// Set the mazeMatrix field
		mazeMatrix = getMazeMatrix();
		
	}
	
	// This method is for the ghost moving randomly
	public void moveRandomly() {
		
		// Initialize the direction variable
		int direction = 0;
		
		// Get the previous direction
		int prevDirection = getDirection();
		
		// Check if the ghost can't move in another direction except backwards
		// The purpose of checking this is because moving backwards makes the ghost movement feel rough
		// Preferably, the ghost doesn't move backwards, but if it has to, it will
		if (isStuck(prevDirection)) {
			
			// Set direction to backwards
			if (prevDirection == 0) direction = 2;
			else if (prevDirection == 1) direction = 3;
			else if (prevDirection == 2) direction = 0;
			else if (prevDirection == 3) direction = 1;
			
			setDirection(direction);
			
		// Else the ghost can move
		} else {
			
			// Check if the movement isn't in opposite directions
			do {
				
				// Get a random direction
				direction = (int)(Math.random() * 4);
				
				// Set the direction
				setDirection(direction);;

			// Allow the direction to go through if the direction is not backwards and the next cell is not a wall or a ghost
			} while (Math.abs(prevDirection - direction) == 2 ||
					mazeMatrix[getNextRow()][getNextColumn()].getId() == PacManGame.ID_WALL ||
					board.isOverlapping(this, mazeMatrix[getNextRow()][getNextColumn()]));
			
		}
		
	}
	
	// Check if the ghost is stuck if moving backwards isn't an option
	public boolean isStuck(int prevDirection) {
		
		// Check all 4 directions
		for (int dir = 0; dir < 4; dir++) {
			
			// Set the direction
			setDirection(dir);
			
			// Set the next cell
			Cell nextCell = mazeMatrix[getNextRow()][getNextColumn()];
			
			// Check if the next cell can be moved to or if it is backwards, if it is, return it is not stuck
			if (Math.abs(prevDirection - dir) != 2 &&
					nextCell.getId() != PacManGame.ID_WALL &&
					!board.isOverlapping(this, nextCell)) 
				return false;
			
		}
		
		// Return that it is stuck
		return true;
		
	}
	
	// Method for moving the ghost on a path
	public void movePath(Cell targetCell) {
		
		// Updating the path
		updatePath(targetCell);
		
		// Getting the direction sequence
		Stack<Integer> directionSequence = calcDirectionSequence(targetCell);
		
		// If the direction sequence has more elements, set the direction to the next direction
		if (!directionSequence.isEmpty())
			this.setDirection(directionSequence.pop());
		
	}

	// Getters & Setters
	public boolean isVulnerable() {
		return isVulnerable;
	}

	public void setVulnerable(boolean isVulnerable) {
		this.isVulnerable = isVulnerable;
	}

	public int getVulnerableTime() {
		return vulnerableTime;
	}

	public void setVulnerableTime(int vulnerableTime) {
		this.vulnerableTime = vulnerableTime;
	}

}
