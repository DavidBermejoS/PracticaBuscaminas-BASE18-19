/**
 * <b>Clase User</b>
 * Clase encargada de almacenar los datos del usuario para mostrar en el highscore.
 * Implementa la interfaz comparable para poder ordenar posteriomente a los usuarios
 * según su puntuación en la clase WriteScore
 *
 * @author David Bermejo Simon
 * @see WriteScore#showFileInfo()
 * @see Comparable
 * @since v1.4.0
 */
public class User implements Comparable {
    /**
     * String name: almacenará el nombre del usuario
     */
    String name;
    /**
     * String score: almacenará la puntuación del usuario
     */
    String score;
    /**
     * String time: almacenará el tiempo de juego del usuario
     */
    String time;

    /**
     * <b>Constructor de la clase</b>
     * Recibe por parametro una linea de usuario. Utilizando el metodo split de la clase String separará la información
     * en nombre, puntuación (score) y tiempo.
     *
     * @param userLine
     * @see String#split(String)
     */
    public User(String userLine) {
        String[] userData = userLine.split("--->");
        this.name = userData[0].split(":")[1];
        this.score = userData[1].split(":")[1];
        this.time = userData[2].split(":")[1];
    }

    /**
     * Metodo que comparará objetos de la clase User por su puntuación. Sobreescribe el método compareTo de la interfaz
     * Comparable
     *
     * @param o : objecto a comparar
     * @return :
     * 1 : si la puntuación del mismo es mayor a la del usuario a comparar
     * 0: si la puntuación del mismo y del usuario a comparar son iguales
     * -1 : si la puntuación del mismo es menor a la del usuario a comparar
     * @see Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(Object o) {
        User aux = (User) o;
        int scoreThis = Integer.parseInt(this.score);
        int scoreAux = Integer.parseInt(aux.score);
        if (scoreThis > scoreAux) {
            return 1;
        } else if (scoreAux == scoreThis) {
            return 0;
        } else {
            return -1;
        }
    }


}
