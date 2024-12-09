import java.io.File;
import javax.sound.sampled.*;

public class Sounds {
	
	
	public static void playSound(String soundFile) {
		
		//https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
		try {
			
		    File f = new File("./" + soundFile);
		    AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
		    Clip clip = AudioSystem.getClip();
		    clip.open(audioIn);
		    clip.start();
		    
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			
		}
		
	}
	
}