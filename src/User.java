/**
 * Clase encargada de almacenar los datos del usuario para mostrar en el highscore.
 */
public class User implements Comparable {
    String name;
    String score;
    String time;


    public User(String userLine){
        String [] userData = userLine.split("--->");
        this.name = userData[0].split(":")[1];
        this.score = userData[1].split(":")[1];
        this.time = userData[2].split(":")[1];
    }

    @Override
    public int compareTo(Object o) {
        User aux = (User) o;
        int scoreThis = Integer.parseInt(this.score);
        int scoreAux = Integer.parseInt(aux.score);
        if(scoreThis > scoreAux){
            return 1;
        }else if (scoreAux == scoreThis){
            return 0;
        }else{
            return -1;
        }
    }


}
