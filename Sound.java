// Java program to play an Audio 
// file using Clip Object 
import java.io.File; 
import java.io.IOException;
  
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 
  
public class Sound
{ 
    private Clip clip;
    private AudioInputStream audioInputStream;
    private String Filepath;
   
    public Sound(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException  
    { 
        Filepath = filePath;
        // create AudioInputStream object 
        audioInputStream = AudioSystem.getAudioInputStream(new File(Filepath).getAbsoluteFile()); 
          
        // create clip reference 
        clip = AudioSystem.getClip(); 
          
        // open audioInputStream to the clip 
        clip.open(audioInputStream);
    }

    public void play()  
    {
        clip.start();
        if (Filepath == "wakka.wav" && clip.getFramePosition() == 24255){
            clip.setFramePosition(0);
        }
    }
}