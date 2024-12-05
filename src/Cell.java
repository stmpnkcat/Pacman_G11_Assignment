import javax.swing.JLabel;

/*
 * This class creates a blueprint cell to be created by other classes
 */
public class Cell extends JLabel{
	
	// Declare the item for this cell
	private char item;
	
	// This method is a constructor which runs when a new cell is created
	public Cell(char item) {

		super();		
		this.item = item;
		
		// Setting the cell's image based on the character
		setCodeIcon();;
		
	}

	// This method allows other classes to get and set the item of this cell
	public char getItem() {
		return item;
	}
	
	public void setItem (char item) {
		this.item = item;
	}
	
	// This method sets the icon of this cell
	private void setCodeIcon() {
		
		if (item == 'P') setIcon(Icons.PACMAN[0]);
		if (item == '0') setIcon(Icons.GHOST[0]);
		if (item == '1') setIcon(Icons.GHOST[1]);
		if (item == '2') setIcon(Icons.GHOST[2]);
		if (item == 'W') setIcon(Icons.WALL);
		if (item == 'F') setIcon(Icons.FOOD);
		if (item == 'D') setIcon(Icons.DOOR);
		
	}
}
