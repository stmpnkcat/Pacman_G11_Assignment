import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TitlePage extends JFrame implements ActionListener{

	JLabel titleLabel = new JLabel(Icons.TITLE);
	
	JButton playButton = new JButton("PLAY");
	
	JButton leaderboardButton = new JButton("LEADERBOARD");
	
	JButton exitButton = new JButton("EXIT");
	
	public TitlePage() {
		
		setTitle("PacMan");
		setSize(PacManGame.SCREEN_X, PacManGame.SCREEN_Y);
		setIconImage(Icons.LOGO.getImage());
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		setContentPane(new JLabel(Icons.TITLE_BACKGROUND));
		titleLabel.setBounds(0, 50, 600, 140);
		add(titleLabel);
		
		formatButton(playButton);
		
		playButton.setBounds(150, 400, 300, 50);
		add(playButton);
		
		formatButton(leaderboardButton);
		
		leaderboardButton.setBounds(150, 450, 300, 50);
		add(leaderboardButton);
		
		formatButton(exitButton);
		
		exitButton.setBounds(150, 500, 300, 50);
		add(exitButton);
		
		setVisible(true);
		
	}
	
	private void formatButton(JButton button) {
		
		button.setFont(Fonts.font_big);
		button.setForeground(Color.WHITE);
		button.addActionListener(this);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		
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
