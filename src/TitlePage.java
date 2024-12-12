import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

public class TitlePage extends JFrame implements ActionListener{

	JLabel titleLabel = new JLabel(Icons.TITLE);
	
	JButton playButton = new JButton("PLAY");
	
	JButton leaderboardButton = new JButton("LEADERBOARD");
	
	JButton exitButton = new JButton("EXIT");
	
	public TitlePage() {
	    
	    formatFrame(this);
		setLayout(null);
	    
	    setContentPane(new JLabel(Icons.TITLE_BACKGROUND));
	    titleLabel.setBounds(0, 50, 600, 140);
	    add(titleLabel);
	    
	    JPanel playButtonPanel = formatButton(this, playButton);
	    playButtonPanel.setBounds(150, 400, 350, 50);
	    add(playButtonPanel);
	    
	    JPanel leaderboardButtonPanel = formatButton(this, leaderboardButton);
	    leaderboardButtonPanel.setBounds(150, 450, 350, 50);
	    add(leaderboardButtonPanel);
	    
	    JPanel exitButtonPanel = formatButton(this, exitButton);
	    exitButtonPanel.setBounds(150, 500, 350, 50);
	    add(exitButtonPanel);
	    
	    setVisible(true);
	    
	}
	
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

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == playButton) {

			dispose();
			new PacManGUI();
			
		} else if (e.getSource() == leaderboardButton) {
			
			dispose();
			new LeaderboardPage();
			
		} else if (e.getSource() == exitButton) {
			
			System.exit(0);
			
		}
		
	}

}
