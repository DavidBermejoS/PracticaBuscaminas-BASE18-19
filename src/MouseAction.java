import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * <h2>Clase MouseAction</h2>
 * Esta clase gestionará los eventos de raton tales como
 * hacer click derecho (para anadir una bandera) o realizar clicks con los
 * dos botones a la vez.
 * Implementa la interfaz MouseListener
 *
 * @author David Bermejo Simon
 * @see MouseListener
 * @since v1.6.0
 */

public class MouseAction implements MouseListener {
    /**
     * VentanaPrincipal ventana: instancia de la clase VentanaPrincipal
     *
     * @see VentanaPrincipal
     */
    VentanaPrincipal ventana;
    /**
     * JButton buttonAux : boton auxiliar en el que se almacenará el boton que ha iniciado el evento del listener
     */
    JButton buttonAux;
    /**
     * int col: número entero correspondiente a la columna donde se encuentra el boton que ha iniciado el evento.
     */
    int col;
    /**
     * int row: número entero correspondiente a la fila donde se encuentra el boton que ha iniciado el evento.
     */
    int row;

    /**
     * <b>Constructor de la clase principal</b>
     *
     * @param ventana : instancia de ventana principal para acceder al contenido de la interfaz y sus métodos
     */
    public MouseAction(VentanaPrincipal ventana) {
        this.ventana = ventana;
    }

    /**
     * <b>Constructor de la clase principal</b>
     *
     * @param ventana : instancia de ventana principal para acceder al contenido de la interfaz y sus métodos
     * @param i       : número entero correspondiente al número de fila  del botón pulsado.
     * @param j       : número entero correspondiente al número de columna del botón pulsado.
     */
    public MouseAction(VentanaPrincipal ventana, int i, int j) {
        this.ventana = ventana;
        this.col = i;
        this.row = j;
    }

    /**
     * Metodo que indica que acciones se llevarán a cabo cuando se pulse el botón derecho del mouse.
     * En este caso al pulsarse reproducirá el sonido click y en caso de que se encuentre con una mina
     * reproducirá el sonido de mina
     *
     * @param mouseEvent : evento del mouse
     * @see MouseListener#mouseClicked(MouseEvent)
     * @see SoundControl
     * @since v1.6.0
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        buttonAux = (JButton) mouseEvent.getSource();
        if (SwingUtilities.isRightMouseButton(mouseEvent)) {
            SoundControl sc = new SoundControl("click");
            if (buttonAux.getText().equals("-")) {
                Icon flag = new ImageIcon("resources/flag_orange.png");
                buttonAux.setIcon(flag);
                buttonAux.setText("");
            } else {
                buttonAux.setIcon(null);
                buttonAux.setText("-");
            }
        }
    }

    /**
     * Metodo que indica que acciones se llevarán a cabo cuando se pulse el botón central del mouse.
     * En este caso al pulsarse reproducirá el sonido click y en caso de que se encuentre con una mina
     * reproducirá el sonido de mina
     *
     * @param mouseEvent : evento del mouse
     * @see MouseListener#mousePressed(MouseEvent)
     * @see SoundControl
     * @since v1.6.0
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        ControlJuego monitor = ventana.getJuego();
        SoundControl sc;
        if (SwingUtilities.isMiddleMouseButton(mouseEvent)) {
            sc = new SoundControl("click");
            if (!ventana.mostrarGrupoCasillas(col, row)) {
                sc = new SoundControl("mina");
                ventana.actualizarPuntuacion();
                ventana.refrescarPantalla();
                ventana.printMines();
                ventana.mostrarFinJuego(true);
            } else {
                ventana.actualizarPuntuacion();
                ventana.refrescarPantalla();
                if (ventana.getJuego().esFinJuego()) {
                    ventana.mostrarFinJuego(false);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
