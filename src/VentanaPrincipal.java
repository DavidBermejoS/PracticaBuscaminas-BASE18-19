import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class VentanaPrincipal {

    //La ventana principal, en este caso, guarda todos los componentes:
    JFrame ventana;
    JPanel panelImagen;
    JPanel panelEmpezar;
    JPanel panelPuntuacion;
    JPanel panelJuego;

    //Todos los botones se meten en un panel independiente.
    //Hacemos esto para que podamos cambiar después los componentes por otros
    JPanel[][] panelesJuego;
    JButton[][] botonesJuego;

    //Correspondencia de colores para las minas:
    Color correspondenciaColores[] = {Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED};

    JButton botonEmpezar;
    JTextField pantallaPuntuacion;


    //LA VENTANA GUARDA UN CONTROL DE JUEGO:
    ControlJuego juego;

    //LA VENTANA EJECUTARÁ UN HILO PARA PINTAR EL TIEMPO EN EL CONTADOR
    PaintTime paintTime;
    JLabel labelTiempo; //etiqueta donde se contendrá el tiempo transcurrido
    //booleano para controlar el final de la ejecución del hilo PainTime
    boolean finishTime;


    //Constructor, marca el tamaño y el cierre del frame
    public VentanaPrincipal() {
        ventana = new JFrame();
        ventana.setBounds(100, 100, 700, 500);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        juego = new ControlJuego();
    }

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
     * Método que inicializa todos los lísteners que necesita inicialmente el programa
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
     * Se pinta el color del texto según la siguiente correspondecia (consultar la variable correspondeciaColor):
     * - 0 : negro
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
        label.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        label.setForeground(correspondenciaColores[minasAlrededor]);
        panelesJuego[i][j].add(label);
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
                        if (juego.abrirCasilla(k, l) && botonesJuego[k][l].isVisible()) {
                            mostrarNumMinasAlrededor(k, l);
                        } else if (!juego.abrirCasilla(k, l)) {
                            return true;
                        }
                    }
                } catch (Exception e) {

                }
            }
        }
        return false;
    }


    /**
     * Muestra una ventana que indica el fin del juego.
     *
     * @param porExplosion : Un booleano que indica si es final del juego porque ha explotado una mina (true) o bien porque hemos desactivado todas (false)
     * @post : Todos los botones se desactivan excepto el de volver a iniciar el juego.
     */
    public void mostrarFinJuego(boolean porExplosion) {
        int option;


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
        //este bloque lo utilizaremos para preguntar al usuario si quiere seguir jugando o si quiere cerrar el juego
        option = JOptionPane.showConfirmDialog(null, "¿Quieres cerrar el juego? (Si pulsas que no, podrás reiniciar el juego en el botón principal 'Go!') ", "¿Quieres cerrar el juego?", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }

    }

    /**
     * Metodo encargado de mostrar al usuario dos paneles relativos a la puntuación.
     * Uno de ellos le preguntará al nombre para posteriormente guardarlo en un fichero y el segundo
     * muestra los paneles de los mejores clasificados
     * @since v1.7.0
     */
    public void showHighScorePanels(){
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
     * Método que muestra la puntuación por pantalla.
     */
    public void actualizarPuntuacion() {
        pantallaPuntuacion.setText(String.valueOf(juego.getPuntuacion()));
    }


    /**
     * Método que muestra en el panel izquierdo el tiempo transcurrido en el juego.
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
     * Método para inicializar el programa
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
