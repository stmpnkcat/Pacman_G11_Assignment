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

public class LeaderboardPage extends JFrame implements ActionListener{

	private JLabel titleLabel = new JLabel ("LEADERBOARD", SwingConstants.CENTER);
	
	private JPanel leaderboardPanel;
	
	private JButton backButton = new JButton("BACK");
	
	public LeaderboardPage () {
		
		setTitle("PacMan");
		setSize(PacManGame.SCREEN_X, PacManGame.SCREEN_Y);
		setIconImage(Icons.LOGO.getImage());
		getContentPane().setBackground(Color.BLACK);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		titleLabel.setFont(Fonts.font_big);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBackground(Color.BLACK);
		titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		add(titleLabel, BorderLayout.NORTH);
		
		leaderboardPanel = createLeaderboardPanel();
		leaderboardPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		add(leaderboardPanel, BorderLayout.CENTER);
		
		backButton.setFont(Fonts.font_big);
		backButton.setForeground(Color.WHITE);
		backButton.addActionListener(this);
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		add(backButton, BorderLayout.SOUTH);
		
		setVisible(true);
		
	}
	
	public static JPanel createLeaderboardPanel() {
		
		JPanel leaderboardPanel = new JPanel();
		leaderboardPanel.setLayout(new GridBagLayout());
		leaderboardPanel.setBackground(Color.BLACK);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(0, 0, 20, 150);
		gbc.anchor = GridBagConstraints.WEST;
		
		Map<String, Integer> leaderboardMap = getLeaderboard("leaderboards/maze_1.txt");
		
		int row = 1;
		for (Map.Entry<String, Integer> entry : leaderboardMap.entrySet()) {
			
			gbc.gridx = 0;
			gbc.gridy = row;
			
			JLabel nameLabel = new JLabel(entry.getKey());
			nameLabel.setFont(Fonts.font_small);
			nameLabel.setBackground(Color.BLACK);
			nameLabel.setForeground(Color.WHITE);
			
			leaderboardPanel.add(nameLabel, gbc);
			
			gbc.gridx = 1;
			
			JLabel scoreLabel = new JLabel(entry.getValue()+"");
			scoreLabel.setFont(Fonts.font_small);
			scoreLabel.setBackground(Color.BLACK);
			scoreLabel.setForeground(Color.WHITE);
			
			leaderboardPanel.add(scoreLabel, gbc);
			
			row++;
			
		}
		
		return leaderboardPanel;
		
	}
	
	public static boolean isOnLeaderboard(int value) {
		
		Map<String, Integer> leaderboard = getLeaderboard("leaderboards/maze_1.txt");
		
		leaderboard.put(" ", value);
		
		if (sortByValues(leaderboard).containsKey(" "))
			return true;
		
		return false;
		
	}
	
	private static Map<String, Integer> getLeaderboard(String filePath){
		
		Map<String, Integer> leaderboardMap = new HashMap<>();
		
		// Declaring the scanner
		Scanner inputFile;
		
		// Trying to get the image, if it fails, send error message
		try {
			
			// Setting the scanner to read the file location given
			inputFile = new Scanner(new File(filePath));
			
			// Iterate through the rows of the file
			while (inputFile.hasNext()) {
				
				String nextLine = inputFile.nextLine();
				
				if (nextLine == "") break;
				
				// Create a char array of the current row
				leaderboardMap.put(nextLine.split(" ")[0], Integer.parseInt(nextLine.split(" ")[1]));
				
			}
			
			// Close the input file
			inputFile.close();
			
		// If the file cannot be found, return an error message.
		} catch (FileNotFoundException error) {
			
			System.out.println("File not found");
			
		}
		
		leaderboardMap = sortByValues(leaderboardMap);
		
		return leaderboardMap;
		
	}
	
	private static Map<String, Integer> sortByValues(Map<String, Integer> unsortedMap){
		
		//https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
        List<Entry<String, Integer> > list =
               new LinkedList<Entry<String, Integer> >(unsortedMap.entrySet());
        
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
				
			}
		});
		
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		
		for (int row = 0; row < PacManGame.LEADERBOARD_MAX_ROWS; row++) {
			
			if (sortedMap.size() == list.size()) break;
			
			Entry<String, Integer> entry = list.get(row);
            sortedMap.put(entry.getKey(), entry.getValue());
            
		}
//      

        return sortedMap;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == backButton) {

			dispose();
			new TitlePage();
			
		}
		
	}
	
}
