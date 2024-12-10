import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TitlePage extends JFrame implements ActionListener{
	
	private JLabel titleLabel = new JLabel(Icons.TITLE);
	
	private JButton playButton = new JButton("PLAY");
	
	private JButton leaderboardButton = new JButton("LEADERBOARD");
	
	private JButton exitButton = new JButton("EXIT");
	
	public TitlePage() {
		
		setSize(PacManGame.SCREEN_X, PacManGame.SCREEN_Y);
		setIconImage(Icons.LOGO.getImage());
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
		
		button.setFont(Fonts.play_font);
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
			
			
			
		} else if (e.getSource() == exitButton) {
			
			System.exit(0);
			
		}
		
	}

}
