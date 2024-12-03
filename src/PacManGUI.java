import javax.swing.*;

/*
 * This class creates the frame which the entire game is displayed on
 */
public class PacManGUI extends JFrame {

	// Create a new board (which is a panel) for displaying the game board
	private Board board = new Board();
	
	// This method is a constructor for when the GUI is initialized
	public PacManGUI() {
		
		// Set size and title of the frame
		setSize(600, 600);
		setTitle("Daniel's PacMan Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the frame when the program stops
		
		// Add the board to the main board GUI
		add(board);
		
		addKeyListener(board);
		
		// Showing the GUI frame
		setVisible(true);
		
	}
	
}
