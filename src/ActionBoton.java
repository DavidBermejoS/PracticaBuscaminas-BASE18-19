import javax.swing.*;
import java.awt.*;
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

	public ActionBoton(VentanaPrincipal ventanaPrincipal,int col, int row) {
		this.ventana=ventanaPrincipal;
		this.col = col;
		this.row = row;
	}

	public ActionBoton(VentanaPrincipal ventanaPrincipal){
		this.ventana=ventanaPrincipal;
	}
	
	/**
	 *Acción que ocurrirá cuando pulsamos uno de los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ControlJuego monitor = ventana.getJuego();
		JButton botonAux =(JButton) e.getSource();

		if (botonAux.getText().equalsIgnoreCase("Go!")) {
			monitor.inicializarPartida();
			ventana.getVentana().setContentPane(new JPanel(new BorderLayout()));
			ventana.inicializar();
			ventana.refrescarPantalla();
		}

		if(botonAux.getText().equalsIgnoreCase("-")) {
			if (monitor.abrirCasilla(col, row)) {
				ventana.actualizarPuntuacion();
				ventana.mostrarNumMinasAlrededor(col, row);
				ventana.refrescarPantalla();
				if(ventana.getJuego().esFinJuego()){
					ventana.mostrarFinJuego(false);
				}
			} else {
				ventana.mostrarFinJuego(true);
				ventana.refrescarPantalla();
			}
		}
	}

}
