import javax.swing.JLabel;

/*
 * This class creates a blueprint cell to be created by other classes
 */
public class Cell extends JLabel{
	
	// Declare the fields
	private char id;
	private int row;
	private int column;
	
	// used for pathfinding
	private int costG; // path to the ghost
	private int costH; // path to the target cell (estimate)
	private int cost; // total cost of this route
	private int parentRow;
	private int parentColumn;

	// This method is a constructor which runs when a new cell is created
	public Cell(char id, int row, int column) {

		super();
		this.id = id;
		this.row = row;
		this.column = column;
		
		// Setting the cell's image based on the character
		setIdIcon(id);;
		setFocusable(false);
		
	}

	// This method sets the icon of this cell
	public void setIdIcon(char id) {
		
		if (id == PacManGame.ID_PLAYER) setIcon(Icons.PACMAN[0]);
		else if (id == '0') setIcon(Icons.GHOST[0]);
		else if (id == '1') setIcon(Icons.GHOST[1]);
		else if (id == '2') setIcon(Icons.GHOST[2]);
		else if (id == PacManGame.ID_WALL) setIcon(Icons.WALL);
		else if (id == PacManGame.ID_FOOD) setIcon(Icons.FOOD);
		else if (id == PacManGame.ID_DOOR) setIcon(Icons.DOOR);
		else if (id == PacManGame.ID_EMPTY) setIcon(Icons.BLANK);
		
	}

	// Setters and getters
	public int getParentRow() {
		return parentRow;
	}

	public int getParentColumn() {
		return parentColumn;
	}

	public void setParent(int parentRow, int parentColumn) {
		this.parentRow = parentRow;
		this.parentColumn = parentColumn;
	}
	
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public char getId() {
		return id;
	}
	
	public void setId (char id) {
		this.id = id;
	}
	
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

	public int getCostG() {
		return costG;
	}

	public void setCostG(int costG) {
		this.costG = costG;
	}

	public int getCostH() {
		return costH;
	}

	public void setCostH(int costH) {
		this.costH = costH;
	}
	
}
