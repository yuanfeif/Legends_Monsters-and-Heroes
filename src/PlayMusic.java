/**
 * @ClassName PlayMusic
 * @Description
 * @Author Vincent Yuan
 * @Date 2021/11/21 19:46
 */
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JApplet;

public class PlayMusic {
    public static AudioClip loadSound(String filename) {
        URL url = null;
        try {
            url = new URL("file:" + filename);
        }
        catch (MalformedURLException e) {;}
        return JApplet.newAudioClip(url);
    }
    public void play() {
        AudioClip christmas = loadSound("src/ConfigFiles/bg_music.wav");
        christmas.play();
    }

    public void stop() {

    }
}