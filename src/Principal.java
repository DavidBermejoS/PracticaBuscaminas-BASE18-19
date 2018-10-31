import java.awt.EventQueue;

/**
 * <b>Clase principal</b>
 *
 * @author David Bermejo Simon
 * @author Jesus Redondo Garcia
 */
public class Principal {

    /**
     * Método main.
     * Inicializa una ventana (objeto de la clase VentanaPrincipal)
     *
     * @param args : Cadenas de parámetros del main
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaPrincipal ventana = new VentanaPrincipal();
                    ventana.inicializar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
