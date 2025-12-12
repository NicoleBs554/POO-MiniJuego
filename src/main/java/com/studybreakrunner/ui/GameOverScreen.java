package com.studybreakrunner.ui;

import java.awt.*;

public class GameOverScreen {
    private int finalScore;
    private int collectedCoins;
    private float timeSurvived;
    
    public GameOverScreen() {
        finalScore = 0;
        collectedCoins = 0;
        timeSurvived = 0;
    }
    
    public void render(Graphics2D g2d, int screenWidth, int screenHeight) {
        // Panel de game over
        g2d.setColor(new Color(0, 0, 0, 220));
        g2d.fillRect(screenWidth / 2 - 250, screenHeight / 2 - 200, 500, 400);
        
        // Título
        g2d.setColor(new Color(231, 76, 60)); // Rojo
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        
        FontMetrics fm = g2d.getFontMetrics();
        String gameOverText = "GAME OVER";
        int textWidth = fm.stringWidth(gameOverText);
        g2d.drawString(gameOverText, (screenWidth - textWidth) / 2, screenHeight / 2 - 100);
        
        // Estadísticas
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 24));
        
        int startY = screenHeight / 2 - 30;
        int lineHeight = 40;
        
        g2d.drawString("Puntuación Final: " + finalScore, 
                      (screenWidth - g2d.getFontMetrics().stringWidth("Puntuación Final: " + finalScore)) / 2,
                      startY);
        
        g2d.setColor(Color.YELLOW);
        g2d.drawString("Monedas: " + collectedCoins,
                      (screenWidth - g2d.getFontMetrics().stringWidth("Monedas: " + collectedCoins)) / 2,
                      startY + lineHeight);
        
        g2d.setColor(new Color(52, 152, 219)); // Azul
        int minutes = (int)(timeSurvived / 60);
        int seconds = (int)(timeSurvived % 60);
        String timeString = String.format("Tiempo: %02d:%02d", minutes, seconds);
        g2d.drawString(timeString,
                      (screenWidth - g2d.getFontMetrics().stringWidth(timeString)) / 2,
                      startY + lineHeight * 2);
        
        // Instrucciones
        g2d.setColor(new Color(46, 204, 113)); // Verde
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        g2d.drawString("Presiona R para reiniciar",
                      (screenWidth - g2d.getFontMetrics().stringWidth("Presiona R para reiniciar")) / 2,
                      startY + lineHeight * 3 + 30);
        
        g2d.setColor(Color.WHITE);
        g2d.drawString("Presiona ESC para salir",
                      (screenWidth - g2d.getFontMetrics().stringWidth("Presiona ESC para salir")) / 2,
                      startY + lineHeight * 4);
    }
    
    public void setFinalStats(int score, int coins, float time) {
        this.finalScore = score;
        this.collectedCoins = coins;
        this.timeSurvived = time;
    }
}