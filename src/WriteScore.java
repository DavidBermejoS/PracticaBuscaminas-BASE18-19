import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteScore {
    VentanaPrincipal ventana;
    File fich;

    public WriteScore(VentanaPrincipal ventana) {
        this.ventana = ventana;
        this.fich = new File("Score/high_score");
    }

    public void writeFile(String user) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fich, true))) {
            if (user != null) {
                pw.println("User: " + user + " ---> Score: " + ventana.pantallaPuntuacion.getText() + " ---> Tiempo: " + ventana.labelTiempo.getText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
