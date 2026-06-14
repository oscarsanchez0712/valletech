package valletech;

import valletech.view.LoginView;

import javax.swing.*;

/**
 * Punto de entrada — ValleTech Desktop
 */
public class Main {

    public static void main(String[] args) {
        // Look & Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Lanzar UI en el hilo de eventos Swing
        SwingUtilities.invokeLater(LoginView::new);
    }
}
