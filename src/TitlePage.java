import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/*
 * This class creates the title page of the frame
 * It contains buttons for playing the game, viewing the leaderboard, and exiting the game
 */
public class TitlePage extends JFrame implements ActionListener{

	JLabel titleLabel = new JLabel(Icons.TITLE); // Label to display title	
	JButton playButton = new JButton("PLAY"); // Button to start the game
	JButton leaderboardButton = new JButton("LEADERBOARD"); // Button to view leaderboard
	JButton exitButton = new JButton("EXIT"); // Button to exit game

	// Constructor for the TitlePage class
	public TitlePage() {
	    
		// Format the frame using utility methods
		Utilities.formatFrame(this);
		setLayout(null);
	    
		// Set the background of the content pane to the background image
	    setContentPane(new JLabel(Icons.TITLE_BACKGROUND));
	    
	    // Create the title Label
	    titleLabel.setBounds(0, 50, 600, 140);
	    add(titleLabel);
	    
	    // Format and add the play button to the frame
	    JPanel playButtonPanel = Utilities.formatButton(this, playButton);
	    playButtonPanel.setBounds(150, 400, 350, 50);
	    add(playButtonPanel);
	    
	    // Format and add the leaderboard to the frame
	    JPanel leaderboardButtonPanel = Utilities.formatButton(this, leaderboardButton);
	    leaderboardButtonPanel.setBounds(150, 450, 350, 50);
	    add(leaderboardButtonPanel);
	    
	    // Format and add the exit button to the frame
	    JPanel exitButtonPanel = Utilities.formatButton(this, exitButton);
	    exitButtonPanel.setBounds(150, 500, 350, 50);
	    add(exitButtonPanel);
	    
	    // Show the frame
	    setVisible(true);
	    
	}

	// Method to handle button clicks
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// If the play button is clicked, close this frame and start the game
		if (e.getSource() == playButton) {

			dispose();
			new PacManGUI();
			
		// If the leaderboard button is clicked, close this frame and show the leaderboard
		} else if (e.getSource() == leaderboardButton) {
			
			dispose();
			new LeaderboardPage();
			
		// If the exit button is clicked, close the game
		} else if (e.getSource() == exitButton) {
			
			System.exit(0);
			
		}
		
	}

}
