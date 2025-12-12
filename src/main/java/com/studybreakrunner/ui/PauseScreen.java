package com.studybreakrunner.ui;

import java.awt.*;

public class PauseScreen {
    
    public void render(Graphics2D g2d, int screenWidth, int screenHeight) {
        // Panel de pausa
        g2d.setColor(new Color(0, 0, 0, 200));
        g2d.fillRect(screenWidth / 2 - 200, screenHeight / 2 - 150, 400, 300);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        
        FontMetrics fm = g2d.getFontMetrics();
        String pauseText = "JUEGO PAUSADO";
        int textWidth = fm.stringWidth(pauseText);
        g2d.drawString(pauseText, (screenWidth - textWidth) / 2, screenHeight / 2 - 50);
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        String instruction = "Presiona P para continuar";
        int instrWidth = g2d.getFontMetrics().stringWidth(instruction);
        g2d.drawString(instruction, (screenWidth - instrWidth) / 2, screenHeight / 2);
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
        String escInstruction = "Presiona ESC para salir al menú";
        int escWidth = g2d.getFontMetrics().stringWidth(escInstruction);
        g2d.drawString(escInstruction, (screenWidth - escWidth) / 2, screenHeight / 2 + 50);
    }
}