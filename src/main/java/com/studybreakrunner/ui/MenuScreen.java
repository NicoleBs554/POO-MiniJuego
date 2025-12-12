package com.studybreakrunner.ui;

import com.studybreakrunner.game.GameConfig;
import java.awt.*;

public class MenuScreen {
    
    public void render(Graphics2D g2d, int screenWidth, int screenHeight) {
        // Fondo
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(41, 128, 185),
            0, screenHeight, new Color(44, 62, 80)
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, screenWidth, screenHeight);
        
        // Título
        g2d.setFont(GameConfig.TITLE_FONT);
        g2d.setColor(Color.WHITE);
        
        FontMetrics fm = g2d.getFontMetrics();
        String title = "STUDY BREAK RUNNER";
        int titleWidth = fm.stringWidth(title);
        g2d.drawString(title, (screenWidth - titleWidth) / 2, 200);
        
        // Subtítulo
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        g2d.setColor(new Color(200, 200, 200));
        String subtitle = "Corre, salta y supera tus límites";
        int subtitleWidth = g2d.getFontMetrics().stringWidth(subtitle);
        g2d.drawString(subtitle, (screenWidth - subtitleWidth) / 2, 250);
        
        // Instrucciones
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.setColor(Color.YELLOW);
        g2d.drawString("CONTROLES:", 100, 350);
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
        g2d.setColor(Color.WHITE);
        g2d.drawString("← → : Moverse", 120, 380);
        g2d.drawString("ESPACIO : Saltar", 120, 410);
        g2d.drawString("P : Pausa", 120, 440);
        g2d.drawString("ESC : Salir al menú", 120, 470);
        
        // Mensaje para empezar
        g2d.setFont(new Font("Arial", Font.BOLD, 28));
        g2d.setColor(new Color(46, 204, 113));
        g2d.drawString("Presiona ESPACIO para empezar", 180, 550);
    }
}