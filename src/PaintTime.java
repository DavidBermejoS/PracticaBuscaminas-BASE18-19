/**
 * <b>Clase PaintTime</b>
 * Esta clase se encargará de pintar el tiempo en la interfaz.
 *
 * @author David Bermejo Simon
 * @since v1.1.0
 **/
public class PaintTime extends Thread {
    /**
     * VentanaPrincipal ventana: instancia de la clase VentanaPrincipal
     *
     * @see VentanaPrincipal
     */
    VentanaPrincipal ventana;

    /**
     * <b>Constructor de la clase</b>
     * Recibe por parámetro una instancia de la clase VentanaPrincipal para gestionar los elementos de la interfaz
     *
     * @param ventana
     * @see VentanaPrincipal
     */
    public PaintTime(VentanaPrincipal ventana) {
        this.ventana = ventana;
    }

    /**
     * Método run de la clase Thread
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (!ventana.finishTime) {
            try {
                sleep(1000);
                ventana.actualizarTiempo(ventana.getJuego().getTiempo());
                ventana.refrescarPantalla();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
