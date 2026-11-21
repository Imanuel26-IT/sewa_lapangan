import gui.MainMenu;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}
