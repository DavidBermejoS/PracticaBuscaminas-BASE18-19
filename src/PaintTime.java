/**
 * @author David Bermejo Simon
 **/
public class PaintTime extends Thread {
    VentanaPrincipal ventana;

    public PaintTime(VentanaPrincipal ventana) {
        this.ventana = ventana;
    }


    @Override
    public void run() {
        while (!ventana.getJuego().esFinJuego()) {
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
