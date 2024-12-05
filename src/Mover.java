import javax.swing.JLabel;

/*
 * This class acts as a parent for other entities that move
 */
public class Mover extends JLabel{
	
	// Declaring row and column number
	private int row;
	private int column;
	
	// Declaring variables for where to move
	private int dRow;
	private int dColumn;
	
	// Declaring variable for if an entity is dead
	private boolean isDead;
	
	// Constructor for when mover is initialized
	public Mover(int row, int column) {
		super();
		this.row = row;
		this.column = column;
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
}
