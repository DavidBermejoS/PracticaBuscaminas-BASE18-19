import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clase gestora del tablero de juego.
 * Guarda una matriz de enteros representado el tablero.
 * Si hay una mina en una posición guarda el número -1
 * Si no hay una mina, se guarda cuántas minas hay alrededor.
 * Almacena la puntuación de la partida
 *
 * @author David Bermejo Simon
 */
public class ControlJuego {

    private final static int MINA = -1;
    final int MINAS_INICIALES = 20;
    final int LADO_TABLERO = 10;
    LocalTime timeStart;


    private int[][] tablero;
    private int puntuacion;


    public ControlJuego() {
        //Creamos el tablero:
        tablero = new int[LADO_TABLERO][LADO_TABLERO];
        //Inicializamos una nueva partida
        inicializarPartida();
    }


    /**
     * Método para generar un nuevo tablero de partida:
     *
     * @pre: La estructura tablero debe existir.
     * @post: Al final el tablero se habrá inicializado con tantas minas como marque la variable MINAS_INICIALES.
     * El resto de posiciones que no son minas guardan en el entero cuántas minas hay alrededor de la celda
     */
    public void inicializarPartida() {
        //TODO: Repartir minas e inicializar puntacion. Si hubiese un tablero anterior, lo pongo todo a cero para inicializarlo.

        //inicializamos el temporizador
        timeStart = LocalTime.now();

        //ponemos todo el tablero a 0
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j]=0;
            }
        }

        //inicializamos puntuacion
        puntuacion = 0;
        Random rd = new Random();
        int col, row;

        //Colocamos las minas
        for (int i = 0; i < MINAS_INICIALES; i++) {
            do {
                col = rd.nextInt(10);
                row = rd.nextInt(10);
            } while (tablero[row][col] == MINA);
            tablero[row][col] = MINA;
        }

        //Al final del metodo hay que guardar el numero de minas para las casillas que no son mina:
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] != MINA) {
                    tablero[i][j] = calculoMinasAdjuntas(i, j);
                }
            }
        }
    }

    /**
     * Cálculo de las minas adjuntas:
     * Para calcular el número de minas tenemos que tener en cuenta que no nos salimos nunca del tablero.
     * Por lo tanto, como mucho la i y la j valdrán LADO_TABLERO-1.
     * Por lo tanto, como poco la i y la j valdrán 0.
     *
     * @param i: posición vertical de la casilla a rellenar
     * @param j: posición horizontal de la casilla a rellenar
     * @return : El número de minas que hay alrededor de la casilla [i][j]
     **/
    private int calculoMinasAdjuntas(int i, int j) {
        int countMinas=0;
        //Se hace el recuento de las minas que hay alrededor del boton pulsado
        for (int k = i-1; k < i+2; k++) {
            for (int l = j-1; l < j+2; l++) {
                //colocamos un try/catch para controlar la excepcion de los bordes del tablero
                try {
                    if (tablero[k][l] == MINA) {
                        countMinas++;
                    }
                }catch (Exception e){

                }
            }
        }
        return countMinas;
    }

    /**
     * Método que nos permite verificar si podemos abrir una casilla. Para ello se comprobará que no existe una mina
     * en el interior de la misma.k
     *
     * @param i: posición verticalmente de la casilla a abrir
     * @param j: posición horizontalmente de la casilla a abrir
     * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
     * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por el ControlJuego. Por lo tanto siempre sumaremos puntos
     */
    public boolean abrirCasilla(int i, int j) {
        if(tablero[i][j]==MINA){
            return false;
        }
        return true;
    }

    public void actualizarPuntuacion(int score){
        this.puntuacion=score;
    }


    /**
     * Método que checkea si se ha terminado el juego porque se han abierto todas las casillas.
     *
     * @return Devuelve verdadero si se han abierto todas las celdas que no son minas.
     **/
    public boolean esFinJuego() {
        if(puntuacion==(LADO_TABLERO*LADO_TABLERO)-MINAS_INICIALES){
            return true;
        }
        return false;
    }


    /**
     * Método que pinta por pantalla toda la información del tablero, se utiliza para depurar
     */
    public void depurarTablero() {
        System.out.println("---------TABLERO--------------");
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                System.out.print(tablero[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("\nPuntuación: " + puntuacion);
    }

    /**
     * Método que se utiliza para obtener las minas que hay alrededor de una celda
     *
     * @param i : posición vertical de la celda.
     * @param j : posición horizontal de la cela.
     * @return Un entero que representa el número de minas alrededor de la celda
     * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace falta calcularlo, símplemente consultarlo
     */
    public int getMinasAlrededor(int i, int j) {
        int countMinas=0;
        //Se hace el recuento de las minas que hay alrededor del boton pulsado
        for (int k = i-1; k < i+2; k++) {
            for (int l = j-1; l < j+2; l++) {
                //colocamos un try/catch para controlar la excepcion de los bordes del tablero
                try {
                    if (tablero[k][l] == MINA) {
                        countMinas++;
                    }
                }catch (Exception e){

                }
            }
        }
        return countMinas;
    }

    /**
     * Método que devuelve la puntuación actual
     *
     * @return Un entero con la puntuación actual
     */
    public int getPuntuacion() {
        return puntuacion;

    }

    /**
     * Metodo que obtiene el tiempo actual
     * @return Un array de string con la hora, minutos y segundos desde que comenzó la partida
     */
    public String[] getTiempo(){
        LocalTime timeNow = LocalTime.now();
        timeNow=timeNow.minusHours(timeStart.getHour());
        timeNow=timeNow.minusMinutes(timeStart.getMinute());
        timeNow=timeNow.minusSeconds(timeStart.getSecond());
        String[] unidadesTiempo = new String [3];
        unidadesTiempo[0]= String.valueOf(timeNow.getSecond());
        unidadesTiempo[1]= String.valueOf(timeNow.getMinute());
        unidadesTiempo[2]= String.valueOf(timeNow.getHour());
        return unidadesTiempo;
    }

}
