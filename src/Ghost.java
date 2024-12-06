import java.util.Stack;

public class Ghost extends Mover{

	private Cell[][] mazeMatrix;
	
	public Ghost(Board board, int row, int column) {
		super(board, row, column);

		mazeMatrix = getMazeMatrix();
	}
	
	public void moveRandomly() {
		
		// Initialize the direction variable
		int direction = 0;
		
		// Check if the movement isn't in opposite directions
		do {
			direction = (int)(Math.random() * 4);
		} while (Math.abs(this.getDirection() - direction) == 2 && mazeMatrix[this.getNextColumn()][this.getNextColumn()].getId() == Settings.ID_WALL);
		
		// Set the ghost's direction to the random direction chosen
		this.setDirection(direction);
	}
	
	public void movePath(Cell targetCell) {
		
		updatePath(targetCell);
		Stack<Integer> directionSequence = calcDirectionSequence(targetCell);
		
		if (!directionSequence.isEmpty()) this.setDirection(directionSequence.pop());
	}

}
