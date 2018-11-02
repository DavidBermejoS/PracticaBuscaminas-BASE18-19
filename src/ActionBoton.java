import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que implementa el listener de los botones del Buscaminas.
 * De alguna manera tendrá que poder acceder a la ventana principal.
 * Se puede lograr pasando en el constructor la referencia a la ventana.
 * Recuerda que desde la ventana, se puede acceder a la variable de tipo ControlJuego.
 * Implementa la interfaz ActionListener
 *
 * @author David Bermejo Simon
 * @author Jesus Redondo García
 * @see ActionListener
 * @since v1.0.0
 * *
 */
public class ActionBoton implements ActionListener {

    VentanaPrincipal ventana;
    int col;
    int row;

    /**
     * Constructor de la clase principal
     *
     * @param ventanaPrincipal : instancia de ventana principal para acceder al contenido de la interfaz y sus métodos
     * @param col              : número entero correspondiente al número de columna del botón pulsado.
     * @param row              : número entero correspondiente al número de fila del botón pulsado.
     */
    public ActionBoton(VentanaPrincipal ventanaPrincipal, int col, int row) {
        this.ventana = ventanaPrincipal;
        this.col = col;
        this.row = row;
    }

    /**
     * Constructor de la clase principal
     *
     * @param ventanaPrincipal : instancia de ventana principal para acceder al contenido de la interfaz y sus métodos
     */
    public ActionBoton(VentanaPrincipal ventanaPrincipal) {
        this.ventana = ventanaPrincipal;
    }

    /**
     * Acción que ocurrirá cuando pulsamos uno de los botones.
     * <p>
     * <p>
     * - Si el botón pulsado contiene el texto "Go!" (botonEmpezar en clase VentanaPrincipal)
     * se encargará de inicializar de nuevo los componentes de la ventana
     *
     * @see VentanaPrincipal#inicializar()
     * <p>
     * - Si el botón pulsado contiene el texto "-" realizará las comprobaciones para comprobar si
     * el botón pulsado corresponde con una mina (clase ControlJuego abrirCasilla)
     * y en caso contrario pintar el número de minas que rodean al mismo
     * (VentanaPrincipal mostrarNumMinasAlrededor)
     * Además actualizará la puntuación y refresacara la pantalla.
     * @see ControlJuego#abrirCasilla(int, int)
     * @see VentanaPrincipal#mostrarNumMinasAlrededor(int, int)
     * @see VentanaPrincipal#actualizarPuntuacion()
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        ControlJuego monitor = ventana.getJuego();
        JButton botonAux = (JButton) e.getSource();

        if (botonAux.getText().equalsIgnoreCase("Go!")) {
            monitor.inicializarPartida();
            ventana.getVentana().setContentPane(new JPanel(new BorderLayout()));
            ventana.inicializar();
            ventana.refrescarPantalla();
        }

        if (botonAux.getText().equalsIgnoreCase("-")) {
            if (monitor.abrirCasilla(col, row)) {
                SoundControl sc = new SoundControl("click");
                ventana.mostrarNumMinasAlrededor(col, row);
                ventana.actualizarPuntuacion();
                ventana.refrescarPantalla();
                if (ventana.getJuego().esFinJuego()) {
                    ventana.mostrarFinJuego(false);
                }
            } else {
                SoundControl sc = new SoundControl("mina");
                ventana.printMines();
                ventana.mostrarFinJuego(true);
                ventana.refrescarPantalla();
            }
        }
    }

}
