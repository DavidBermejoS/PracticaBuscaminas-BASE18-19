import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

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
                pw.println("User:" + user + " --->Score:" + ventana.pantallaPuntuacion.getText() +"---> Tiempo:" + ventana.labelTiempo.getText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                    info += "\n"+(i + 1) + "- Nombre: " + users.get(i).name + " --- Puntuacion: " + users.get(i).score + " --- Tiempo: " + users.get(i).time;
                }else{
                    info+="\n*******************************************************";
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
