import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.swing.*;

/*
 * This class creates the leaderboard page
 * The leaderboard page shows the top 5 scores and their names
 */
public class LeaderboardPage extends JFrame implements ActionListener{

	// Creating the swing components
	private JLabel titleLabel = new JLabel ("LEADERBOARD", SwingConstants.CENTER); // Label for title
	private JPanel leaderboardPanel; // Panel for the leaderboard
	private JButton backButton = new JButton("BACK"); // Button for going back to title page

	// This method is the constructor for leaderboard page
	public LeaderboardPage () {

		// Formatting the frame and setting layout
		Utilities.formatFrame(this);
		setLayout(new BorderLayout());
		
		// Formatting the title label
		titleLabel.setFont(Fonts.font_big);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBackground(Color.BLACK);
		titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		// Add the title label
		add(titleLabel, BorderLayout.NORTH);
		
		// Create the leaderboard panel
		leaderboardPanel = createLeaderboardPanel();
		
		// Add the leaderboard panel
		add(leaderboardPanel, BorderLayout.CENTER);
		
		// Create the back button
		JPanel backButtonPanel = Utilities.formatButton(this, backButton);
		backButtonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		// Add the back button panel
		add(backButtonPanel, BorderLayout.SOUTH);
		
		// Showing the frame
		setVisible(true);
		
	}
	
	// The method creates the leaderboard panel
	public static JPanel createLeaderboardPanel() {
		
		// Declare the leaderboard panel, using grid bag layout
		JPanel leaderboardPanel = new JPanel();
		leaderboardPanel.setLayout(new GridBagLayout());
		leaderboardPanel.setBackground(Color.BLACK);
		
		// Declaring the grid bag constraints, which will organize the leaderboard
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(0, 0, 20, 150); // Creating the insets, which are like boarders
		gbc.anchor = GridBagConstraints.WEST;
		
		// Create the leaderboard "map" which is like a python dictionary
		Map<String, Integer> leaderboardMap = getLeaderboard("leaderboards/maze_1.txt");
		
		// Set the starting row, which is one
		int row = 1;
		
		// Traverse through the map's entries
		for (Map.Entry<String, Integer> entry : leaderboardMap.entrySet()) {
			
			// Setting the position of the name label
			gbc.gridx = 0;
			gbc.gridy = row;
			
			// Create the name label
			JLabel nameLabel = new JLabel(entry.getKey());
			nameLabel.setFont(Fonts.font_small);
			nameLabel.setBackground(Color.BLACK);
			nameLabel.setForeground(Color.WHITE);
			
			leaderboardPanel.add(nameLabel, gbc);
			
			// Move the position right
			gbc.gridx = 1;
			
			// Create the score label
			JLabel scoreLabel = new JLabel(entry.getValue()+"");
			scoreLabel.setFont(Fonts.font_small);
			scoreLabel.setBackground(Color.BLACK);
			scoreLabel.setForeground(Color.WHITE);
			
			leaderboardPanel.add(scoreLabel, gbc);
			
			// Move the row down one
			row++;
			
		}
		
		// Return the panel
		return leaderboardPanel;
		
	}
	
	// Check if the given value would place on the top 5 of the leaderboard
	public static boolean isOnLeaderboard(int value) {
		
		// Get the leaderboard
		Map<String, Integer> leaderboard = getLeaderboard("leaderboards/maze_1.txt");
		
		// Put in a test entry, with an invalid name to avoid overriding other entries
		leaderboard.put(" ", value);
		
		// If the top 5 entries in the leaderboard has the test entry, return true
		if (Utilities.sortByValues(leaderboard).containsKey(" "))
			return true;
		
		// If it did not return, return false
		return false;
		
	}
	
	// Get the leaderboard map using the file location
	private static Map<String, Integer> getLeaderboard(String filePath){
		
		// Initialize the leaderboard map
		Map<String, Integer> leaderboardMap = new HashMap<>();
		
		// Declaring the scanner
		Scanner inputFile;
		
		// Trying to get the file, if it fails, send error message
		try {
			
			// Setting the scanner to read the file location given
			inputFile = new Scanner(new File(filePath));
			
			// Iterate through the rows of the file
			while (inputFile.hasNext()) {
				
				// Get the next line
				String nextLine = inputFile.nextLine();
				
				// If the next line is nothing, stop the loop
				if (nextLine == "") break;
				
				// Put the name as the first string of the next line and the score as the second string of the next line
				leaderboardMap.put(nextLine.split(" ")[0], Integer.parseInt(nextLine.split(" ")[1]));
				
			}
			
			// Close the input file
			inputFile.close();
			
		// If the file cannot be found, return an error message.
		} catch (FileNotFoundException error) {
			
			System.out.println("File not found");
			
		}
		
		// Sort the leaderboard map
		leaderboardMap = Utilities.sortByValues(leaderboardMap);
		
		// Return the leaderboard map
		return leaderboardMap;
		
	}

	// This method detects button clicks
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// If the back button is clicked, close this frame and start the title page
		if (e.getSource() == backButton) {

			dispose();
			new TitlePage();
			
		}
		
	}
	
}
