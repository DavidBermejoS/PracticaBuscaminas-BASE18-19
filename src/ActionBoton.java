import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que implementa el listener de los botones del Buscaminas.
 * De alguna manera tendrá que poder acceder a la ventana principal.
 * Se puede lograr pasando en el constructor la referencia a la ventana.
 * Recuerda que desde la ventana, se puede acceder a la variable de tipo ControlJuego
 * @author David Bermejo Simon
 **
 */
public class ActionBoton implements ActionListener{

	VentanaPrincipal ventana;
	int col;
	int row;
	boolean reset;

	public ActionBoton(VentanaPrincipal ventanaPrincipal,int col, int row) {
		this.ventana=ventanaPrincipal;
		this.col = col;
		this.row = row;
		reset=false;
	}

	public ActionBoton(VentanaPrincipal ventanaPrincipal){
		this.ventana=ventanaPrincipal;
		reset = true;
	}
	
	/**
	 *Acción que ocurrirá cuando pulsamos uno de los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ControlJuego monitor = ventana.getJuego();
		if (reset) {
			monitor.inicializarPartida();
			ventana.inicializar();
			ventana.refrescarPantalla();
		} else {
			if (monitor.abrirCasilla(col, row)) {
				ventana.actualizarPuntuacion();
				ventana.mostrarNumMinasAlrededor(col, row);
				ventana.refrescarPantalla();
			} else {
				ventana.mostrarFinJuego(monitor.esFinJuego());
				ventana.refrescarPantalla();
			}

		}
	}

}
