import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EndPage extends JFrame implements ActionListener{

	private int score;

	private JTextField nameTextField = new JTextField();
	
	public EndPage(int score) {
		
		this.score = score;
		
		Utilities.formatFrame(this);
	    setLayout(null);
	    
		JLabel scoreLabel = new JLabel("FINAL SCORE             " + score, SwingConstants.CENTER);
		scoreLabel.setFont(Fonts.font_big);
		scoreLabel.setBackground(Color.BLACK);
		scoreLabel.setForeground(Color.WHITE);

		scoreLabel.setBounds(100, 50, 400, 50);
		add(scoreLabel);
		
		if (LeaderboardPage.isOnLeaderboard(score)) {
			
			JLabel promptLabel = new JLabel("ENTER NAME ", SwingConstants.CENTER);
			promptLabel.setFont(Fonts.font_small);
			promptLabel.setForeground(Color.WHITE);
			
			promptLabel.setBounds(100, 120, 400, 50);
			add(promptLabel);

			JLabel arrowLabel = new JLabel(">");
		    arrowLabel.setFont(Fonts.font_big);
		    arrowLabel.setForeground(Color.WHITE);
		    arrowLabel.setVisible(false);
		    
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
			
			nameTextField.setFont(Fonts.font_small);
			nameTextField.setBackground(Color.BLACK);
			nameTextField.setForeground(Color.WHITE);
			nameTextField.addFocusListener(focusListener);

			nameTextField.grabFocus();
			
			Dimension nameSize = new Dimension(200, 50);
			
			nameTextField.setPreferredSize(nameSize);
			nameTextField.setMaximumSize(nameSize);
			nameTextField.setMinimumSize(nameSize);
			
			JPanel namePanel = new JPanel();
			namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			namePanel.setOpaque(false);
			
			namePanel.add(arrowLabel);
			namePanel.add(nameTextField);

			namePanel.setBounds(200, 160, 500, 50);
			add(namePanel);
			
			JPanel leaderboardPanel = LeaderboardPage.createLeaderboardPanel();
			
			leaderboardPanel.setBounds(100, 250, 500, 250);
			add(leaderboardPanel);
			
		} else {
			
			JLabel messageLabel = new JLabel ("you didnt beat the high score", SwingConstants.CENTER);
			messageLabel.setFont(Fonts.font_small);
			messageLabel.setForeground(Color.WHITE);
			messageLabel.setBackground(Color.BLACK);
			
			messageLabel.setBounds(50, 300, 500, 50);
			add(messageLabel);
			
		}
		
		JButton confirmButton = new JButton("CONFIRM");
		JPanel confirmButtonPanel = Utilities.formatButton(this, confirmButton);

		confirmButtonPanel.setBounds(100, 520, 300, 50);
		add(confirmButtonPanel);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (LeaderboardPage.isOnLeaderboard(score)) {
			
			String name = nameTextField.getText().toUpperCase().replace(' ', '_');
			
			if (!name.equals("") || name.length() > 9) {
				
				try {
					
					//https://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
					Files.write(Paths.get("leaderboards/maze_1.txt"), (name + " " + score + "\n").getBytes(), StandardOpenOption.APPEND);
					
				} catch (IOException e1) {

					e1.printStackTrace();
					
				}
				
				dispose();
				new LeaderboardPage();
				
			}
			
		} else {

			dispose();
			new TitlePage();
			
		}
		
	}
	
}
