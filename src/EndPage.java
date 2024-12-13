import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.*;

/*
 * This class creates the end page
 * The end page allows the user to enter their name if they placed on the leaderboard
 */
public class EndPage extends JFrame implements ActionListener{

	private int score; // Variable for the score
	private JTextField nameTextField = new JTextField(); // Text field for getting the name
	
	// This method is the constructor when the end page is created
	public EndPage(int score) {
		
		// Setting the score
		this.score = score;
		
		// Formatting the frame and setting null layout
		Utilities.formatFrame(this);
	    setLayout(null);
	    
	    // Create the label for the final score
		JLabel scoreLabel = new JLabel("FINAL SCORE             " + score, SwingConstants.CENTER);
		scoreLabel.setFont(Fonts.font_big);
		scoreLabel.setBackground(Color.BLACK);
		scoreLabel.setForeground(Color.WHITE);

		// Setting the bounds of the label and adding it
		scoreLabel.setBounds(100, 50, 400, 50);
		add(scoreLabel);
		
		// If the score is on the leaderboard
		if (LeaderboardPage.isOnLeaderboard(score)) {
			
			// Create the prompt label, asking the player to enter their name
			JLabel promptLabel = new JLabel("ENTER NAME ", SwingConstants.CENTER);
			promptLabel.setFont(Fonts.font_small);
			promptLabel.setForeground(Color.WHITE);
			
			// Set the bounds of the prompt label and add it
			promptLabel.setBounds(100, 120, 400, 50);
			add(promptLabel);

			// Create the arrow label for when the textfield is selected
			JLabel arrowLabel = new JLabel(">");
		    arrowLabel.setFont(Fonts.font_big);
		    arrowLabel.setForeground(Color.WHITE);
		    arrowLabel.setVisible(false); // Hide the arrow
		    
		    // Create the focus listener for the textfield
		    FocusListener focusListener = new FocusListener() {
		        
		    	// When the textfield is focused, show the arrow
		        @Override
		        public void focusGained(FocusEvent e) {
		            arrowLabel.setVisible(true);
		        }

		        // When the textfield loses focus, hide the arrow
		        @Override
		        public void focusLost(FocusEvent e) {
		            arrowLabel.setVisible(false);
		        }
		        
		    };
			
		    // Format the text field
			nameTextField.setFont(Fonts.font_small);
			nameTextField.setBackground(Color.BLACK);
			nameTextField.setForeground(Color.WHITE);
			
			// Add the focus listener to the text field
			nameTextField.addFocusListener(focusListener);

			// Get the focus, which makes the user start typing on the text field
			nameTextField.grabFocus();
			
			// Create a dimension size for the text field
			Dimension nameSize = new Dimension(200, 50);
			
			// Set the size of the text field
			nameTextField.setPreferredSize(nameSize);
			nameTextField.setMaximumSize(nameSize);
			nameTextField.setMinimumSize(nameSize);
			
			// Create the name panel to store the arrow and the text field
			JPanel namePanel = new JPanel();
			namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			namePanel.setOpaque(false);
			
			// Add the arrow and the name text field
			namePanel.add(arrowLabel);
			namePanel.add(nameTextField);

			// Set the bounds of the name panel and add it
			namePanel.setBounds(200, 160, 500, 50);
			add(namePanel);
			
			// Create the leaderboard panel
			JPanel leaderboardPanel = LeaderboardPage.createLeaderboardPanel();
			
			// Add the panel to the frame
			leaderboardPanel.setBounds(100, 250, 500, 250);
			add(leaderboardPanel);
			
		// If the score is not on the leaderboard
		} else {
			
			// Create the message label
			JLabel messageLabel = new JLabel ("you didnt beat the high score", SwingConstants.CENTER);
			messageLabel.setFont(Fonts.font_small);
			messageLabel.setForeground(Color.WHITE);
			messageLabel.setBackground(Color.BLACK);
			
			// Set the bounds of the label and add it
			messageLabel.setBounds(50, 300, 500, 50);
			add(messageLabel);
			
		}
		
		// Create the confirm button and format it
		JButton confirmButton = new JButton("CONFIRM");
		JPanel confirmButtonPanel = Utilities.formatButton(this, confirmButton);

		// Set the bounds of it and add it
		confirmButtonPanel.setBounds(100, 520, 300, 50);
		add(confirmButtonPanel);
		
		// Show the frame
		setVisible(true);
		
	}

	// Method for when the confirm button is clicked
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// If the score is on the leaderboard
		if (LeaderboardPage.isOnLeaderboard(score)) {
			
			// Get the name from the text field
			String name = nameTextField.getText().toUpperCase().replace(' ', '_');
			
			// Check if the name is valid
			if (!name.equals("") && name.length() <= 9) {
				
				// Try/catch for reading the file
				try {
					
					// Appending the name and score onto the preexisting leaderboard file
					// https://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
					Files.write(Paths.get("leaderboards/maze_1.txt"), (name + " " + score + "\n").getBytes(), StandardOpenOption.APPEND);
					
				} catch (IOException e1) {

					e1.printStackTrace();
					
				}
				
				// Close the frame and show the leaderboard page
				dispose();
				new LeaderboardPage();
				
			}
			
		// If the score is not on the leaderboard page, close the frame and show the title page
		} else {

			dispose();
			new TitlePage();
			
		}
		
	}
	
}
