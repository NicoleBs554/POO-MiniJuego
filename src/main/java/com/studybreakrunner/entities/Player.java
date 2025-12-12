package com.studybreakrunner.entities;

import com.studybreakrunner.game.GameConfig;
import com.studybreakrunner.utils.Vector2D;
import java.awt.*;

public class Player extends GameObject {
    private boolean isJumping;
    private boolean isGrounded;
    private float jumpForce;
    private int lives;
    private int score;
    private int coins;
    
    public Player(float x, float y) {
        super(x, y, GameConfig.PLAYER_WIDTH, GameConfig.PLAYER_HEIGHT);
        initialize();
    }
    
    private void initialize() {
        isJumping = false;
        isGrounded = false;
        jumpForce = GameConfig.PLAYER_JUMP_FORCE;
        lives = 5;
        score = 0;
        coins = 0;
        acceleration.y = GameConfig.GRAVITY;
    }
    
    @Override
    public void update(float deltaTime) {
        // Aplicar gravedad
        move(deltaTime);
        
        // Limitar velocidad de caída
        if (velocity.y > GameConfig.TERMINAL_VELOCITY) {
            velocity.y = GameConfig.TERMINAL_VELOCITY;
        }
        
        // Verificar colisión con el suelo
        if (position.y >= GameConfig.GROUND_LEVEL - height) {
            position.y = GameConfig.GROUND_LEVEL - height;
            velocity.y = 0;
            isGrounded = true;
            isJumping = false;
        }
    }
    
    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(GameConfig.PLAYER_COLOR);
        g2d.fillRect((int)position.x, (int)position.y, (int)width, (int)height);
        
        // Dibujar ojos
        g2d.setColor(Color.WHITE);
        g2d.fillOval((int)position.x + 10, (int)position.y + 10, 10, 15);
        g2d.fillOval((int)position.x + 30, (int)position.y + 10, 10, 15);
        
        // Dibujar boca
        g2d.setColor(Color.RED);
        g2d.fillRect((int)position.x + 15, (int)position.y + 35, 20, 5);
    }
    
    public void jump() {
        if (isGrounded) {
            velocity.y = -jumpForce;
            isGrounded = false;
            isJumping = true;
        }
    }
    
    public void moveLeft() {
        velocity.x = -GameConfig.PLAYER_MOVE_SPEED;
    }
    
    public void moveRight() {
        velocity.x = GameConfig.PLAYER_MOVE_SPEED;
    }
    
    public void stopMoving() {
        velocity.x = 0;
    }
    
    public void takeDamage(int damage) {
        lives -= damage;
        if (lives < 0) lives = 0;
    }
    
    public void heal(int amount) {
        lives += amount;
        if (lives > 10) lives = 10; // Máximo 10 vidas
    }
    
    public void addScore(int points) {
        score += points;
    }
    
    public void addCoins(int amount) {
        coins += amount;
    }
    
    // Getters
    public boolean isJumping() { return isJumping; }
    public boolean isGrounded() { return isGrounded; }
    public int getLives() { return lives; }
    public int getScore() { return score; }
    public int getCoins() { return coins; }
    
    // Setters
    public void setGrounded(boolean grounded) { isGrounded = grounded; }
}