import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TitlePage extends JFrame implements ActionListener{
	
	private JLabel backgroundLabel = new JLabel();
	private JLabel titleLabel = new JLabel("PACMAN");
	private JButton playButton = new JButton("PLAY");
	
	public TitlePage() {
		
		setSize(Settings.SCREEN_X, Settings.SCREEN_Y);
		
		setLayout(null);
		
		backgroundLabel.setBounds(0, 0, Settings.SCREEN_X, Settings.SCREEN_Y);
		add(backgroundLabel);
		
		titleLabel.setFont(new Font("ARIAL", Font.BOLD, 40));
		titleLabel.setBounds(150, 100, 300, 75);
		add(titleLabel);
		
		playButton.setFont(new Font("ARIAL", Font.BOLD, 40));
		playButton.addActionListener(this);
		playButton.setBounds(150, 400, 300, 75);
		add(playButton);
		
		setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == playButton) {

			dispose();
			new PacManGUI();
			
		}
	}
}
