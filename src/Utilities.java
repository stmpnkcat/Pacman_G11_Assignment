import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.*;

public class Utilities {

public static void formatFrame(JFrame frame) {
		
		frame.setTitle("PacMan");
		frame.setSize(PacManGame.SCREEN_X, PacManGame.SCREEN_Y);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setIconImage(Icons.LOGO.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
	}
	
	public static JPanel formatButton(JFrame frame, JButton button) {

	    JLabel arrowLabel = new JLabel(">");
	    arrowLabel.setFont(Fonts.font_big);
	    arrowLabel.setForeground(Color.WHITE);
	    arrowLabel.setVisible(false);
	    
	    //https://stackoverflow.com/questions/5359955/simple-focus-listener-in-java
	    FocusListener focusListener = new FocusListener() {
	        
	        @Override
	        public void focusGained(FocusEvent e) {
	            arrowLabel.setVisible(true);
	        }

	        @Override
	        public void focusLost(FocusEvent e) {
	            arrowLabel.setVisible(false);
	        }
	        
	    };
	    
	    button.setFont(Fonts.font_big);
	    button.setForeground(Color.WHITE);
	    button.setOpaque(false);
	    button.setContentAreaFilled(false);
	    button.setBorderPainted(false);
	    button.addActionListener((ActionListener) frame);
	    button.addFocusListener(focusListener);
	    
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	    buttonPanel.setOpaque(false);

	    buttonPanel.add(arrowLabel);
	    buttonPanel.add(button);
	    
	    return buttonPanel;
	    
	}
	

	
	public static Map<String, Integer> sortByValues(Map<String, Integer> unsortedMap){
		
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

        return sortedMap;
	}	
	
}
