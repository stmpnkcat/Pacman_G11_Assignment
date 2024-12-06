import java.util.Stack;

public class Ghost extends Mover{

	private Cell[][] mazeMatrix;
	
	private double randomInterval;
	
	public Ghost(Board board, int row, int column) {
		
		super(board, row, column);

		mazeMatrix = getMazeMatrix();
		
		randomInterval = Math.random() * 9;
		
	}
	
	public void moveRandomly() {
		
		// Initialize the direction variable
		int direction = 0;
		
		// Check if the movement isn't in opposite directions
		while (true) {
			direction = (int)(Math.random() * 4);
			setDirection(direction);
			if (Math.abs(this.getDirection() - direction) != 2 & 
					mazeMatrix[this.getNextRow()][this.getNextColumn()].getId() != Settings.ID_WALL) 
				break;
		}
		
	}
	
	public void movePath(Cell targetCell) {
		
		updatePath(targetCell);
		Stack<Integer> directionSequence = calcDirectionSequence(targetCell);
		
		if (!directionSequence.isEmpty()) this.setDirection(directionSequence.pop());
	}

	public double getRandomInterval() {
		return randomInterval;
	}

	public void setRandomInterval(double randomInterval) {
		this.randomInterval = randomInterval;
	}

}
