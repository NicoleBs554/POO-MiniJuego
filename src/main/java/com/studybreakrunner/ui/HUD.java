package com.studybreakrunner.ui;

import com.studybreakrunner.game.GameConfig;
import java.awt.*;

public class HUD {
    private int score;
    private int coins;
    private int health;
    private int maxHealth;
    private float time;
    
    public HUD() {
        score = 0;
        coins = 0;
        health = 100;
        maxHealth = 100;
        time = 0;
    }
    
    public void update(float deltaTime) {
        time += deltaTime;
    }
    
    public void render(Graphics2D g2d, int screenWidth, int screenHeight) {
        // Configurar fuente
        g2d.setFont(GameConfig.HUD_FONT);
        
        // Panel superior
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(10, 10, screenWidth - 20, 60);
        
        // Puntuación
        g2d.setColor(Color.WHITE);
        g2d.drawString("Puntuación: " + score, 20, 40);
        
        // Monedas
        g2d.setColor(Color.YELLOW);
        g2d.drawString("₿ " + coins, screenWidth / 2 - 30, 40);
        
        // Tiempo
        int minutes = (int)(time / 60);
        int seconds = (int)(time % 60);
        String timeString = String.format("Tiempo: %02d:%02d", minutes, seconds);
        g2d.setColor(Color.WHITE);
        g2d.drawString(timeString, screenWidth - 200, 40);
        
        // Barra de salud
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(20, screenHeight - 60, 200, 30);
        
        g2d.setColor(new Color(46, 204, 113)); // Verde
        int healthWidth = (int)(200 * ((float)health / maxHealth));
        g2d.fillRect(20, screenHeight - 60, healthWidth, 30);
        
        g2d.setColor(Color.WHITE);
        g2d.drawString("Salud: " + health + "/" + maxHealth, 25, screenHeight - 40);
    }
    
    // Getters y Setters
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    
    public int getCoins() { return coins; }
    public void setCoins(int coins) { this.coins = coins; }
    
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = Math.max(0, Math.min(health, maxHealth)); }
    
    public float getTime() { return time; }
    
    public void reset() {
        score = 0;
        coins = 0;
        health = maxHealth;
        time = 0;
    }
}