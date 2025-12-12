package com.studybreakrunner.entities;

import com.studybreakrunner.game.GameConfig;
import com.studybreakrunner.utils.Vector2D;
import java.awt.*;

public class Obstacle extends GameObject {
    private int scoreValue;
    private boolean collected;
    
    public Obstacle(float x, float y) {
        super(x, y, 40, 40);
        initialize();
    }
    
    private void initialize() {
        scoreValue = 50;
        collected = false;
        velocity.x = -GameConfig.BASE_SCROLL_SPEED; // Se mueve con el fondo
    }
    
    @Override
    public void update(float deltaTime) {
        move(deltaTime);
        
        // Si sale de pantalla por la izquierda, desactivar
        if (position.x + width < 0) {
            active = false;
        }
    }
    
    @Override
    public void render(Graphics2D g2d) {
        if (!collected) {
            // Libro
            g2d.setColor(GameConfig.OBSTACLE_COLOR);
            g2d.fillRect((int)position.x, (int)position.y, (int)width, (int)height);
            
            // Lomo del libro
            g2d.setColor(new Color(210, 180, 140)); // Beige
            g2d.fillRect((int)position.x, (int)position.y, 8, (int)height);
            
            // Texto
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.drawString("HW", (int)position.x + 15, (int)position.y + 25);
        }
    }
    
    public void collect() {
        collected = true;
        active = false;
    }
    
    public boolean isCollected() {
        return collected;
    }
    
    public int getScoreValue() {
        return scoreValue;
    }
    
    public void setScrollSpeed(float speed) {
        velocity.x = -speed;
    }
}