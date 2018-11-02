import com.sun.tools.javac.Main;

import javax.sound.sampled.*;
import java.io.*;

/**
 * <h2>Clase SoundConrol</h2>
 * Clase encargada de reproducir los sonidos al clickear en una casilla libre
 * o en una mina. Esta clase hereda de la clase Thread
 *
 * @author David Bermejo Simon
 * @see Thread
 * <p>
 * Los recursos han sido bajados de:
 * @see <a href="http://soundbible.com">Soundbible</a>
 * El sonido de la explosión es obra de Daniel Simon. Puedes encontrar el recurso en:
 * @see <a href="http://soundbible.com/2140-Grenade-Launcher-2.html">Sounbible granade launcher</a>
 * </p>
 * @since v1.10.0
 */
public class SoundControl extends Thread {

    AudioInputStream audioStream;
    Clip clip;

    /**
     * <b>Constructor de la clase</b>
     * Aquí se  inicializarán los sonidos a reproducir dependiendo del sistema operativo en el que se ejecute el
     * programa
     *
     * @param sonido : cadena de texto con el sonido a ejecutar
     */
    public SoundControl(String sonido) {
        try {
            String OS = System.getProperty("os.name").toLowerCase();
            clip = AudioSystem.getClip();
            if (sonido.equals("click")) {
                try(InputStream inputStream = Principal.class.getResourceAsStream("click.wav")){
                    InputStream buffer = new BufferedInputStream(inputStream);
                    audioStream = AudioSystem.getAudioInputStream(buffer);
                    clip.open(audioStream);
                    clip.start();
                }
               // audioStream = AudioSystem.getAudioInputStream(new File("resources/click.wav"));
            } else if (sonido.equals("mina")) {
                //si el sistema operativo en el que se ejecuta el programa es de la familia windows
                if (OS.indexOf("win") >= 0) {
                    try(InputStream inputStream = Principal.class.getResourceAsStream("Grenade.wav")){
                        InputStream buffer = new BufferedInputStream(inputStream);
                        audioStream = AudioSystem.getAudioInputStream(buffer);
                        clip.open(audioStream);
                        clip.start();
                    }
                    //si el sistema operativo en el que se ejecuta el programa es de la familia unix
                } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") >= 0 || OS.indexOf("mac") >= 0) {
                    try(InputStream inputStream = Principal.class.getResourceAsStream("mina.wav")){
                        InputStream buffer = new BufferedInputStream(inputStream);
                        audioStream = AudioSystem.getAudioInputStream(buffer);
                        clip.open(audioStream);
                        clip.start();
                    }
                }


            }
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
