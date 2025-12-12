package com.studybreakrunner.entities;

import com.studybreakrunner.game.GameConfig;
import com.studybreakrunner.utils.Vector2D;
import java.awt.*;

public class Professor extends GameObject {
    private int damage;
    private float moveSpeed;
    
    public Professor(float x, float y) {
        super(x, y, 60, 80);
        initialize();
    }
    
    private void initialize() {
        damage = 1;
        moveSpeed = GameConfig.BASE_SCROLL_SPEED;
        velocity.x = -moveSpeed; // Se mueve hacia la izquierda
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
        // Cuerpo
        g2d.setColor(GameConfig.PROFESSOR_COLOR);
        g2d.fillRect((int)position.x, (int)position.y, (int)width, (int)height);
        
        // Cabeza
        g2d.setColor(new Color(255, 200, 150)); // Piel
        g2d.fillOval((int)position.x + 15, (int)position.y - 20, 30, 30);
        
        // Gafas
        g2d.setColor(Color.BLACK);
        g2d.fillRect((int)position.x + 18, (int)position.y - 10, 10, 5);
        g2d.fillRect((int)position.x + 32, (int)position.y - 10, 10, 5);
        
        // Título
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        g2d.drawString("PROF", (int)position.x + 10, (int)position.y - 25);
    }
    
    public int getDamage() {
        return damage;
    }
    
    public void setMoveSpeed(float speed) {
        moveSpeed = speed;
        velocity.x = -moveSpeed;
    }
}