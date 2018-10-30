import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Clase encargada de gestionar la escritura del fichero donde se almacenará la puntuación el nombre y el tiempo de los
 * usuarios que deseen ingresar sus estadísticas en el juego para así poder mostrarlos posteriormente en una tabla
 * de HighScore. Todos los cambios se guardarán en un fichero "highscore".
 */
public class WriteScore {
    VentanaPrincipal ventana;
    File fich;

    public WriteScore(VentanaPrincipal ventana) {
        this.ventana = ventana;
        this.fich = new File("Score/high_score");
    }

    /**
     * Metodo encargado de escribir en fichero los usuarios con sus respectivas puntuaciones
     * @param user : cadena de texto con el nombre del usuario para guardarlo.
     */
    public void writeFile(String user) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fich, true))) {
            if (user != null) {
                pw.println("User:" + user + " --->Score:" + ventana.pantallaPuntuacion.getText() +"---> Tiempo:" + ventana.labelTiempo.getText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método encargado de devolver una cadena de texto con la información de los 10 mejores usuarios para mostrarlos
     * en un panel
     * @return info : cadena de texto con el nombre, puntuación y tiempo de los 10 mejores usuarios ordenados por
     * puntuación
     */
    public String showFileInfo() {
        String line;
        String info = "";
        try (BufferedReader br = new BufferedReader(new FileReader(fich))) {
            String highScore;
            ArrayList<User> users = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                User user = new User(line);
                users.add(user);
            }
            Collections.sort(users);
            for (int i = 0; i < 10; i++) {
                if(users.size()>i){
                    info += "\n"+(i + 1) + "- Nombre: " + users.get(i).name + " ---> Puntuacion: " + users.get(i).score + " ---> Tiempo: " + users.get(i).time;
                }else{
                    info += "\n"+(i + 1) + "- Nombre: " + "_________"+ " ---> Puntuacion: " + "_________" + " ---> Tiempo: " + "_________";
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return info;
    }
}
