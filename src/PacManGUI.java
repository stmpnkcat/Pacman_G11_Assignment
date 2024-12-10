import java.awt.*;

import javax.swing.*;

/*
 * This class creates the frame which the entire game is displayed on
 */
public class PacManGUI extends JFrame {

	// Create a new board (which is a panel) for displaying the game board
	private Board board = new Board(this);
	
	private JPanel headerPanel = new JPanel();
	
	private JPanel scorePanel = new JPanel();
	
	private JLabel scoreTextLabel = new JLabel("SCORE");
	
	private JLabel scoreLabel = new JLabel();
	
	private JPanel livesPanel = new JPanel();
	
	private JLabel livesTextLabel = new JLabel("LIVES");
	
	private JLabel livesLabel = new JLabel();

	
	// This method is a constructor for when the GUI is initialized
	public PacManGUI() {
		
		// Set size and title of the frame
		setSize(PacManGame.SCREEN_X, PacManGame.SCREEN_Y);
		setTitle("Daniel's PacMan Game");
		getContentPane().setBackground(Color.BLACK);
		setIconImage(Icons.LOGO.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the frame when the program stops
		setLayout(new BorderLayout());
		
		// Add the board to the main board GUI
		add(board, BorderLayout.CENTER);

		headerPanel.setLayout(new BorderLayout());
		headerPanel.setBackground(Color.BLACK);

		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
		scorePanel.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
		scorePanel.setBackground(Color.BLACK);
		
		scoreTextLabel.setForeground(Color.WHITE);
		scoreTextLabel.setFont(Fonts.score_font);
		
		scorePanel.add(scoreTextLabel);

		updateScore(0);
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setFont(Fonts.score_font);
		scorePanel.add(scoreLabel);
		
		headerPanel.add(scorePanel, BorderLayout.WEST);
		
		livesPanel.setLayout(new BoxLayout(livesPanel, BoxLayout.Y_AXIS));
		livesPanel.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
		livesPanel.setBackground(Color.BLACK);
		
		livesTextLabel.setForeground(Color.WHITE);
		livesTextLabel.setFont(Fonts.score_font);
		
		livesPanel.add(livesTextLabel);
		
		updateLives(PacManGame.DEFAULT_LIVES);
		livesLabel.setForeground(Color.WHITE);
		livesLabel.setFont(Fonts.score_font);
		
		livesPanel.add(livesLabel);
		
		headerPanel.add(livesPanel, BorderLayout.EAST);

		add(headerPanel, BorderLayout.NORTH);
		
		// Adding a key listener to the board to receive keyboard input
		addKeyListener(board);
		
		// Showing the GUI frame
		setVisible(true);
		
	}
	
	public void updateScore(int score) {
		scoreLabel.setText(""+score);
	}
	
	public void updateLives(int lives) {
		livesLabel.setText(""+lives);
	}
	
}
