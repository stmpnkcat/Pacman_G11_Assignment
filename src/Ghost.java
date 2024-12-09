import java.util.Stack;

public class Ghost extends Mover{

	private Board board;
	
	private Cell[][] mazeMatrix;
	
	private double randomInterval;
	
	public Ghost(Board board, int row, int column) {
		
		super(board, row, column);

		this.board = board;
		
		mazeMatrix = getMazeMatrix();
		
		randomInterval = Math.random() * 9;
		
	}
	
	public void moveRandomly() {
		
		// Initialize the direction variable
		int direction = 0;
		int prevDirection = getDirection();
		
		if (isStuck(prevDirection)) {
			
			if (prevDirection == 0) direction = 2;
			else if (prevDirection == 1) direction = 3;
			else if (prevDirection == 2) direction = 0;
			else if (prevDirection == 3) direction = 1;
			
			setDirection(direction);
			
		} else {
			
			// Check if the movement isn't in opposite directions
			do {
				
				direction = (int)(Math.random() * 4);
				
				setDirection(direction);;

			} while (Math.abs(prevDirection - direction) == 2 ||
					mazeMatrix[getNextRow()][getNextColumn()].getId() == PacManGame.ID_WALL ||
					board.isOverlapping(this, mazeMatrix[getNextRow()][getNextColumn()]));
			
		}
		
	}
	
	public boolean isStuck(int prevDirection) {
		
		for (int dir = 0; dir < 4; dir++) {
			
			setDirection(dir);
			
			Cell nextCell = mazeMatrix[getNextRow()][getNextColumn()];
			
			if (Math.abs(prevDirection - dir) != 2 &&
					nextCell.getId() != PacManGame.ID_WALL &&
					!board.isOverlapping(this, nextCell)) 
				return false;
			
		}
		
		return true;
		
	}
	
	public void movePath(Cell targetCell) {
		
		updatePath(targetCell);
		
		Stack<Integer> directionSequence = calcDirectionSequence(targetCell);
		
		if (!directionSequence.isEmpty())
			this.setDirection(directionSequence.pop());
		
	}

	public double getRandomInterval() {
		return randomInterval;
	}

	public void setRandomInterval(double randomInterval) {
		this.randomInterval = randomInterval;
	}

}
