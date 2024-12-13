import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

/*
 * This class is responsible for the utility functions
 * Other classes call this class, hence all the methods are static
 */
public class Utilities {

	// Method to format the frame
	public static void formatFrame(JFrame frame) {
		
		frame.setTitle("PacMan"); // Setting title
		frame.setSize(PacManGame.SCREEN_X, PacManGame.SCREEN_Y); // Setting size
		frame.getContentPane().setBackground(Color.BLACK); // Setting background
		frame.setIconImage(Icons.LOGO.getImage()); // Setting icon image
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Setting the frame to close when the program ends
		frame.setResizable(false); // Setting frame to not allow resizing
		
	}
	
	// Method to format buttons6
	public static JPanel formatButton(JFrame frame, JButton button) {

		// Create the arrow, which shows when the button is focused 
	    JLabel arrowLabel = new JLabel(">");
	    arrowLabel.setFont(Fonts.font_big);
	    arrowLabel.setForeground(Color.WHITE);
	    arrowLabel.setVisible(false); // Hide it at first
	    
	    //https://stackoverflow.com/questions/5359955/simple-focus-listener-in-java
	    FocusListener focusListener = new FocusListener() {
	        
	    	// When the button is focused, display the arrow
	        @Override
	        public void focusGained(FocusEvent e) {
	            arrowLabel.setVisible(true);
	        }

	        // When the button is no longer focused, hide the arrow
	        @Override
	        public void focusLost(FocusEvent e) {
	            arrowLabel.setVisible(false);
	        }
	        
	    };
	    
	    // Format the button
	    button.setFont(Fonts.font_big);
	    button.setForeground(Color.WHITE);
	    
	    // Make the button transparent
	    button.setOpaque(false);
	    button.setContentAreaFilled(false);
	    button.setBorderPainted(false);
	    
	    // Add action and focus listener
	    button.addActionListener((ActionListener) frame);
	    button.addFocusListener(focusListener);
	    
	    // Create the button panel to place the arrow and the button on
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	    buttonPanel.setOpaque(false);

	    // Adding the button and the arrow to the panel
	    buttonPanel.add(arrowLabel);
	    buttonPanel.add(button);
	    
	    return buttonPanel;
	    
	}
	
	// Method to play a sound
	public static void playSound(String soundFile) {

		// Try/catch for getting the file
		// https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
		try {
			
		    File file = new File("./" + soundFile); // Create the sound file
		    AudioInputStream audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL());   // Create the audo input stream using the file
		    Clip clip = AudioSystem.getClip(); // Get the clip of the sound
		    clip.open(audioIn); // Open the clip
		    clip.start(); // Start the clip
		    
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			
		}
		
	}
	
	// This method sorts the map by its values and gets the top 5 entries
	public static Map<String, Integer> sortByValues(Map<String, Integer> unsortedMap){
		
		// Create a new list of entries
		//https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
        List<Entry<String, Integer> > list =
               new LinkedList<Entry<String, Integer> >(unsortedMap.entrySet());
        
        // Sort the list using a custom comparator
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
				
			}
		});
		
		// Create a new linked hashmap
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		
		// Get the top 5 entries
		for (int row = 0; row < PacManGame.LEADERBOARD_MAX_ROWS; row++) {
			
			// If the list has no more elements, stop the loop
			if (sortedMap.size() == list.size()) break;
			
			// Get the ntry from the list
			Entry<String, Integer> entry = list.get(row);
			
			// Put the entry in the linked hash map
            sortedMap.put(entry.getKey(), entry.getValue());
            
		}

		// Return the sorted map
        return sortedMap;
        
	}	
	
}
