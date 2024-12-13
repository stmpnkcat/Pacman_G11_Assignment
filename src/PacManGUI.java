import java.awt.*;

import javax.swing.*;

/*
 * This class creates the frame which the entire game is displayed on
 * It consists of the score, the lives, and the actual game board
 */
public class PacManGUI extends JFrame {

	// Panel for displaying the game board
	private Board board = new Board(this);
	
	// Creating the swing components
	private JPanel headerPanel = new JPanel(); // Panel for the header section
	private JPanel scorePanel = new JPanel(); // Panel for displaying the score
	private JLabel scoreTextLabel = new JLabel("SCORE"); // Label for the score text
	private JLabel scoreLabel = new JLabel(); // Label to display the actual score value
	private JPanel livesPanel = new JPanel(); // Panel for displaying the lives remaining
	private JLabel livesTextLabel = new JLabel("LIVES"); // Label for the lives text
	private JLabel livesLabel = new JLabel(); // Label to display the actual lives value
	
	// This method is a constructor for when the GUI is initialized
	public PacManGUI() {
		
		// Set size and title of the frame
		Utilities.formatFrame(this);
		setLayout(new BorderLayout());
		
		// Add the board to the main board GUI
		add(board, BorderLayout.CENTER);

		// Set layout and background for the header panel
		headerPanel.setLayout(new BorderLayout());
		headerPanel.setBackground(Color.BLACK);

		// Set layout and border for the score panel
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
		scorePanel.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
		scorePanel.setBackground(Color.BLACK);
		
		// Change the score text label appearance
		scoreTextLabel.setForeground(Color.WHITE);
		scoreTextLabel.setFont(Fonts.font_small);
		
		// Add the score text label to the score panel
		scorePanel.add(scoreTextLabel);

		// Initialize and configure the score label
		updateScore(0);
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setFont(Fonts.font_small);
		scorePanel.add(scoreLabel);
		
		// Add the score panel to the header panel
		headerPanel.add(scorePanel, BorderLayout.WEST);
		
		// Set layout and border for the lives panel
		livesPanel.setLayout(new BoxLayout(livesPanel, BoxLayout.Y_AXIS));
		livesPanel.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
		livesPanel.setBackground(Color.BLACK);
		
		// Change the lives text label appearance
		livesTextLabel.setForeground(Color.WHITE);
		livesTextLabel.setFont(Fonts.font_small);
		
		// Add the lives text label
		livesPanel.add(livesTextLabel);
		
		// Initialize and configure the lives label
		updateLives(PacManGame.DEFAULT_LIVES);
		livesLabel.setForeground(Color.WHITE);
		livesLabel.setFont(Fonts.font_small);
		
		// Add the lives label to the lives panel
		livesPanel.add(livesLabel);
		
		// Add the lives panel to the header panel
		headerPanel.add(livesPanel, BorderLayout.EAST);

		// Add the header panel to the main frame
		add(headerPanel, BorderLayout.NORTH);
		
		// Adding a key listener to the board to receive keyboard input
		addKeyListener(board);
		
		// Showing the GUI frame
		setVisible(true);
		
	}
	
	// Method to update the score displayed in the GUI
	public void updateScore(int score) {
		scoreLabel.setText(""+score);
	}
	
	// Method to update the lives displayed in the GUI
	public void updateLives(int lives) {
		livesLabel.setText(""+lives);
	}
	
}
