package com.studybreakrunner;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Configurar Look and Feel nativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ejecutar en EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Study Break Runner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setLayout(new BorderLayout());

            com.studybreakrunner.game.GamePanel gamePanel = new com.studybreakrunner.game.GamePanel();
            frame.add(gamePanel, BorderLayout.CENTER);

            frame.pack();
            frame.setLocationRelativeTo(null); // Centrar
            frame.setVisible(true);

            gamePanel.requestFocusInWindow(); // Para capturar eventos de teclado
            gamePanel.startGame();
        });
    }
}