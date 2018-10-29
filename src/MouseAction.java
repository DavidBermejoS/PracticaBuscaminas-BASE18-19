import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Esta clase gestionará los eventos de raton tales como
 * hacer click derecho (para anadir una bandera) o realizar clicks con los
 * dos botones a la vez
 */

public class MouseAction implements MouseListener {
    VentanaPrincipal ventana;
    JButton buttonAux;
    int col;
    int row;

    public MouseAction(VentanaPrincipal ventana) {
        this.ventana = ventana;
    }

    public MouseAction(VentanaPrincipal ventana, int i, int j) {
        this.ventana = ventana;
        this.col = i;
        this.row = j;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        buttonAux = (JButton) mouseEvent.getSource();
        if (SwingUtilities.isRightMouseButton(mouseEvent)) {
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

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

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
