import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TitlePage extends JFrame implements ActionListener{

	JLabel titleLabel = new JLabel(Icons.TITLE);
	
	JButton playButton = new JButton("PLAY");
	
	JButton leaderboardButton = new JButton("LEADERBOARD");
	
	JButton exitButton = new JButton("EXIT");
	
	public TitlePage() {
	    
		Utilities.formatFrame(this);
		setLayout(null);
	    
	    setContentPane(new JLabel(Icons.TITLE_BACKGROUND));
	    
	    titleLabel.setBounds(0, 50, 600, 140);
	    add(titleLabel);
	    
	    JPanel playButtonPanel = Utilities.formatButton(this, playButton);
	    playButtonPanel.setBounds(150, 400, 350, 50);
	    add(playButtonPanel);
	    
	    JPanel leaderboardButtonPanel = Utilities.formatButton(this, leaderboardButton);
	    leaderboardButtonPanel.setBounds(150, 450, 350, 50);
	    add(leaderboardButtonPanel);
	    
	    JPanel exitButtonPanel = Utilities.formatButton(this, exitButton);
	    exitButtonPanel.setBounds(150, 500, 350, 50);
	    add(exitButtonPanel);
	    
	    setVisible(true);
	    
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
