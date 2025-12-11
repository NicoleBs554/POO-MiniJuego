import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String TITLE = "Study Break Runner";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame(TITLE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        
        frame.setResizable(false);
        frame.setVisible(true);
        
        gamePanel.requestFocusInWindow();
    }
}