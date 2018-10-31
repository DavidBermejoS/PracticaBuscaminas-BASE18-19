import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.*;

/**
 * <h2>Clase VentanaPrincipal</h2>
 * Esta clase será la gestora de la interfaz, es decir, se encargará de pintar en la ventana principal los distintos
 * elementos y además se encargará de gestionar las instancias de otras clases como ControlJuego o PaintTime.
 * Además se encargará de manipular los distintos elementos de la interfaz según las opciones que se vayan desarrollando
 * a lo largo del juego mediante llamadas a otros métodos de las demás clases (como por ejemplo pintar el tiempo
 * o consultar si el botón seleccionado corresponde con una mina).
 *
 * @author David Bermejo Simon
 * @author Jesus Redondo García
 * @version v1.13.0
 * @since v1.0.0
 */
public class VentanaPrincipal {

    /**
     * JFrame ventana: ventana principal
     */
    JFrame ventana;
    /**
     * JPanel panelImagen: Panel donde se colocará el temporizador
     */
    JPanel panelImagen;
    /**
     * JPanel panelEmpezar: Panel donde se situará el boton de empezar
     */
    JPanel panelEmpezar;
    /**
     * JPanel panelPuntuacion: Panel donde se situará la puntuación de la partida
     */
    JPanel panelPuntuacion;
    /**
     * JPanel panelJuego: Panel donde se colocarán todos los botones
     */
    JPanel panelJuego;

    /**
     * Todos los botones se meten en un panel independiente.
     * Hacemos esto para que podamos cambiar después los componentes por otros
     * <p>
     * - JButton[][] botonesJuego = Array bidimensional con todos los botones del juego
     * - JPanel[][]panelesJuego = Array bidimensional con todos los paneles donde se colocaran los botones del juego
     */

    JPanel[][] panelesJuego;
    JButton[][] botonesJuego;

    /**
     * Color[] correspondenciaColores = Array donde se guardarán los posibles valores para los colores
     * de los numeros que identifican la cercanía de una mina
     */
    Color correspondenciaColores[] = {Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED};

    /**
     * JButton botonEmpezar: JButton que reseteará la partida.
     */
    JButton botonEmpezar;
    /**
     * JTextField pantallaPuntuacion: JTextField que guardará la puntuación actualizada de la partida
     */
    JTextField pantallaPuntuacion;


    /**
     * ControlJuego juego: instancia de la clase ControlJuego que gestionará la partida, creará el tablero y comprobará
     * si la partida termina o no.
     *
     * @link ControlJuego
     */
    ControlJuego juego;

    /**
     * PaintTime paintTime: hilo de la clase PaintTime que se encargará de pintar el tiempo en labelTiempo.
     */
    PaintTime paintTime;
    /**
     * JLabel labelTiempo: JLabel donde se mantendrá actualizado el tiempo transcurrido de partida
     */
    JLabel labelTiempo;

    /**
     * boolean finishTime: booleano para controlar el final de la ejecución del hilo PainTime. Este será true si
     * se produce el final de la partida
     */
    boolean finishTime;

    /**
     * <b>Constructor de la clase.</b>
     * Crea una nueva ventana de 700x500 pixeles y establece la lógica del juego instanciando un objeto
     * de la clase ControlJuego
     *
     * @see ControlJuego
     */
    //Constructor, marca el tamaño y el cierre del frame
    public VentanaPrincipal() {
        ventana = new JFrame();
        ventana.setBounds(100, 100, 700, 500);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        juego = new ControlJuego();
    }

    /**
     * Metodo encargado de inicializar todos los componentes en la ventana principal (JFrame ventana)
     * Una vez cargados todos los componentes de la interfaz, se marcará el booleano finisTime a false.
     * es llamado desde el método inicializar
     *
     * @see VentanaPrincipal#inicializar
     */
    //Inicializa todos los componentes del frame
    public void inicializarComponentes() {

        //Definimos el layout:
        ventana.setLayout(new GridBagLayout());

        //Inicializamos componentes
        panelImagen = new JPanel();
        panelEmpezar = new JPanel();
        panelEmpezar.setLayout(new GridLayout(1, 1));
        panelPuntuacion = new JPanel();
        panelPuntuacion.setLayout(new GridLayout(1, 1));
        panelJuego = new JPanel();
        panelJuego.setLayout(new GridLayout(10, 10));


        botonEmpezar = new JButton("Go!");
        pantallaPuntuacion = new JTextField("0");
        pantallaPuntuacion.setEditable(false);
        pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);

        //Bordes y colores:
        panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
        panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));


        //Colocamos los componentes:
        //AZUL
        GridBagConstraints settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 0;
        settings.weightx = 1;
        settings.weighty = 1;
        settings.fill = GridBagConstraints.BOTH;
        ventana.add(panelImagen, settings);
        //VERDE
        settings = new GridBagConstraints();
        settings.gridx = 1;
        settings.gridy = 0;
        settings.weightx = 1;
        settings.weighty = 1;
        settings.fill = GridBagConstraints.BOTH;
        ventana.add(panelEmpezar, settings);
        //AMARILLO
        settings = new GridBagConstraints();
        settings.gridx = 2;
        settings.gridy = 0;
        settings.weightx = 1;
        settings.weighty = 1;
        settings.fill = GridBagConstraints.BOTH;
        ventana.add(panelPuntuacion, settings);
        //ROJO
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 1;
        settings.weightx = 1;
        settings.weighty = 10;
        settings.gridwidth = 3;
        settings.fill = GridBagConstraints.BOTH;
        ventana.add(panelJuego, settings);

        //Paneles
        panelesJuego = new JPanel[10][10];
        for (int i = 0; i < panelesJuego.length; i++) {
            for (int j = 0; j < panelesJuego[i].length; j++) {
                panelesJuego[i][j] = new JPanel();
                panelesJuego[i][j].setLayout(new GridLayout(1, 1));
                panelJuego.add(panelesJuego[i][j]);
            }
        }

        //Botones
        botonesJuego = new JButton[10][10];
        for (int i = 0; i < botonesJuego.length; i++) {
            for (int j = 0; j < botonesJuego[i].length; j++) {
                botonesJuego[i][j] = new JButton("-");
                panelesJuego[i][j].add(botonesJuego[i][j]);
            }
        }

        //BotónEmpezar:
        panelEmpezar.add(botonEmpezar);
        panelPuntuacion.add(pantallaPuntuacion);

        //cuando se cargan los componentes se marca el final de partida a false
        finishTime = false;

    }

    /**
     * Método que inicializa todos los lísteners que necesita inicialmente el programa.
     * - Se inicializarán los botonesJuego con un listener ActionBoton para el click izquierdo (destapar mina)
     * - Se inicializará los botonesJuego con un listener MouseAction para el click medio del ratón (destapar área)
     *
     * @since v1.6.0
     * - Se inicializará botonEmpezar con un listener ActionBoton para reiniciar la partida
     */
    public void inicializarListeners() {
        for (int i = 0; i < botonesJuego.length; i++) {
            for (int j = 0; j < botonesJuego[i].length; j++) {
                botonesJuego[i][j].addActionListener(new ActionBoton(this, i, j));
                botonesJuego[i][j].addMouseListener(new MouseAction(this, i, j));
            }
        }
        botonEmpezar.addActionListener(new ActionBoton(this));
    }


    /**
     * Pinta en la pantalla el número de minas que hay alrededor de la celda
     * Saca el botón que haya en la celda determinada y añade un JLabel centrado y no editable con el número de minas alrededor.
     * además, si la casilla abierta es una casilla que no tenga minas alrededor, llamará al metodo mostrarGrupoCasillas para
     * abrir recursivamente todas aquellas casillas que no tengan minas alrededor.
     * Se pinta el color del texto según la siguiente correspondecia (@see correspondenciaColores):
     * - 1 : cyan
     * - 2 : verde
     * - 3 : naranja
     * - 4 ó más : rojo
     *
     * @param i: posición vertical de la celda.
     * @param j: posición horizontal de la celda.
     */
    public void mostrarNumMinasAlrededor(int i, int j) {
        //variable minas alrededor para guardar el numero de minas
        int minasAlrededor = juego.getMinasAlrededor(i, j);
        botonesJuego[i][j].setVisible(false);
        JLabel label = new JLabel(String.valueOf(minasAlrededor));
        if (minasAlrededor == 0) {
            label = new JLabel(" ");
        }
        label.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        label.setForeground(correspondenciaColores[minasAlrededor]);
        panelesJuego[i][j].add(label);
        if (minasAlrededor == 0) {
            mostrarGrupoCasillas(i, j);
        }
    }

    /**
     * Metodo encargado de llamar al metodo mostrarNumMinasAlrededor por cada casilla que rodea a la casilla
     * definida por los parametros i y j. Esta solo se abrirá si el botón está visible y si el botón no posee
     * una bandera que impide que esta se abra
     *
     * @param i fila en la que se encuentra la casilla
     * @param j columna en la que se encuentra la casilla
     * @return true si se encuentra una mina, en caso contrario se encontrara false
     * @since v1.6.0
     */
    public boolean mostrarGrupoCasillas(int i, int j) {
        for (int k = i - 1; k < i + 2; k++) {
            for (int l = j - 1; l < j + 2; l++) {
                try {
                    if (botonesJuego[k][l].getIcon() == null) {
                        if (botonesJuego[k][l].isVisible()) {
                            if (juego.abrirCasilla(k, l)) {
                                mostrarNumMinasAlrededor(k, l);
                            } else if (!juego.abrirCasilla(k, l)) {
                                return false;
                            }
                        }
                    }
                } catch (Exception e) {

                }
            }
        }
        return true;
    }


    /**
     * Muestra una ventana que indica el fin del juego.
     * Una vez muestre los paneles de fin de juego, se hará una llamada al método shoHighScorePanels
     *
     * @param porExplosion : Un booleano que indica si es final del juego porque ha explotado una mina (true) o bien porque hemos desactivado todas (false)
     * @see VentanaPrincipal#showHighScorePanels()
     */
    public void mostrarFinJuego(boolean porExplosion) {
        //se define el final de la partida poniendo el finishTime a true para que el hilo deje de pintar por pantalla el tiempo.
        finishTime = true;
        if (porExplosion) {
            JOptionPane.showMessageDialog(ventana, "Has pisado una mina" + "\n Has obtenido un total de: " + juego.getPuntuacion() + "\n Has aguantado un total de: " + labelTiempo.getText(), "¡Has perdido!", JOptionPane.INFORMATION_MESSAGE);
            for (int i = 0; i < botonesJuego.length; i++) {
                for (int j = 0; j < botonesJuego[i].length; j++) {
                    botonesJuego[i][j].setEnabled(false);
                }
            }
        } else {
            JOptionPane.showMessageDialog(ventana, "Has conseguido detectar las minas" + "\n Has obtenido un total de: " + juego.getPuntuacion() + "\n Has tardado un total de: " + labelTiempo.getText(), "¡Has Ganado!", JOptionPane.INFORMATION_MESSAGE);
            for (int i = 0; i < botonesJuego.length; i++) {
                for (int j = 0; j < botonesJuego[i].length; j++) {
                    botonesJuego[i][j].setEnabled(false);
                }
            }
        }
        showHighScorePanels();


    }

    /**
     * Metodo encargado de pintar en los paneles todas las minas si el usuario pulsa sobre una de ellas
     *
     * @since v1.13.0
     */
    public void printMines() {
        for (int i = 0; i < juego.LADO_TABLERO; i++) {
            for (int j = 0; j < juego.LADO_TABLERO; j++) {
                if (!juego.abrirCasilla(i, j)) {
                    Icon mine = new ImageIcon("resources/mine.png");
                    botonesJuego[i][j].setText(" ");
                    botonesJuego[i][j].setIcon(mine);
                }
            }
        }

    }


    /**
     * Metodo encargado de mostrar al usuario dos paneles relativos a la puntuación.
     * Uno de ellos le preguntará al nombre para posteriormente guardarlo en un fichero y el segundo
     * muestra los paneles de los mejores clasificados
     *
     * @since v1.7.0
     */
    public void showHighScorePanels() {
        String user;
        WriteScore ws;
        //este bloque se encarga de escribir por pantalla elusuario que ha jugado junto con su puntuación y tiempo
        user = JOptionPane.showInputDialog(null, "Escribe tu nombre para guardarlo en el fichero de puntuación ");
        ws = new WriteScore(this);
        ws.writeFile(user);
        String highscore = ws.showFileInfo();
        JOptionPane.showMessageDialog(this.ventana, highscore, "¡TABLA DE HIGHSCORE!", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Método que actualiza la puntuación en el JLabel de panelPuntuacion.
     *
     * @since v1.12.0
     */
    public void actualizarPuntuacion() {
        int countScore = 0;
        for (int i = 0; i < juego.LADO_TABLERO; i++) {
            for (int j = 0; j < juego.LADO_TABLERO; j++) {
                if (!botonesJuego[i][j].isVisible()) {
                    countScore++;
                }

            }

        }
        pantallaPuntuacion.setText(String.valueOf(countScore));
        juego.actualizarPuntuacion(countScore);
    }


    /**
     * Método que muestra en el panel izquierdo el tiempo transcurrido en el juego.
     * El tiempo entre el inicio de la partida y el tiempo transcurrido de partida puede consultarse en ControlJuego
     *
     * @see ControlJuego#getTiempo()
     */
    public void actualizarTiempo(String[] unidadesTiempo) {
        panelImagen.removeAll();
        labelTiempo = new JLabel();
        labelTiempo.setText(unidadesTiempo[2] + "-" + unidadesTiempo[1] + "-" + unidadesTiempo[0]);
        labelTiempo.setForeground(correspondenciaColores[5]);
        labelTiempo.setHorizontalAlignment(SwingConstants.CENTER);
        panelImagen.add(labelTiempo);
    }


    /**
     * Método para refrescar la pantalla
     */
    public void refrescarPantalla() {
        ventana.revalidate();
        ventana.repaint();
    }

    /**
     * Método que devuelve el control del juego de una ventana
     *
     * @return un ControlJuego con el control del juego de la ventana
     */
    public ControlJuego getJuego() {
        return juego;
    }

    /**
     * Metodo encargado de devolver la ventana
     *
     * @return Jframe ventana
     */
    public JFrame getVentana() {
        return this.ventana;
    }

    /**
     * Método para inicializar el programa.
     * En este método además se inicializan los componentes, los listener, se muestra el tablero por consola
     * para facilitar la depuración y se inicializa el hilo paintTime
     *
     *       <pre> public void inicializar() {
     *               ventana.setVisible(true);
     *               inicializarComponentes();
     *               juego.depurarTablero();
     *               inicializarListeners();
     *              paintTime = new PaintTime(this);
     *              paintTime.start();
     *
     *           }
     *           </pre>
     *
     * @see VentanaPrincipal#inicializarComponentes()
     * @see VentanaPrincipal#inicializarListeners()
     * @see PaintTime
     *

     */
    public void inicializar() {
        //IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS COMPONENTES.
        ventana.setVisible(true);
        inicializarComponentes();
        juego.depurarTablero();
        inicializarListeners();
        paintTime = new PaintTime(this);
        paintTime.start();

    }


}
