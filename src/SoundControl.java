import com.sun.tools.javac.Main;

import javax.sound.sampled.*;
import java.io.*;

/**
 * Clase encargada de reproducir los sonidos al clickear en una casilla libre
 * o en una mina.
 * <p>
 * Los recursos han sido bajados de:
 *
 * @see <a href="http://soundbible.com">Soundbible</a>
 * <p>
 * El sonido de la explosión es obra de Daniel Simon. Puedes encontrar el recurso en:
 * @see <a href="http://soundbible.com/2140-Grenade-Launcher-2.html">Sounbible granade launcher</a>
 */
public class SoundControl extends Thread {

    AudioInputStream audioStream;
    Clip clip;

    /**
     * Constructor de la clase donde se inicializarán los sonidos
     *
     * @param sonido : cadena de texto con el sonido a ejecutar
     */
    public SoundControl(String sonido) {
        try {
            clip = AudioSystem.getClip();
            if (sonido.equals("click")) {
                 audioStream = AudioSystem.getAudioInputStream(new File("resources/click.wav"));
            } else if (sonido.equals("mina")) {
                 audioStream = AudioSystem.getAudioInputStream(new File("resources/mina.wav"));
            }
            clip.open(audioStream);
            clip.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
