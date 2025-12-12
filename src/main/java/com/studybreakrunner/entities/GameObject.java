package com.studybreakrunner.entities;

import com.studybreakrunner.utils.Vector2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class GameObject {
    protected Vector2D position;
    protected Vector2D velocity;
    protected Vector2D acceleration;
    protected float width;
    protected float height;
    protected Rectangle bounds;
    protected boolean active;
    
    public GameObject(float x, float y, float width, float height) {
        this.position = new Vector2D(x, y);
        this.velocity = new Vector2D(0, 0);
        this.acceleration = new Vector2D(0, 0);
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle((int)x, (int)y, (int)width, (int)height);
        this.active = true;
    }
    
    public abstract void update(float deltaTime);
    public abstract void render(Graphics2D g2d);
    
    public void move(float deltaTime) {
        // v = v0 + a*t
        velocity.x += acceleration.x * deltaTime;
        velocity.y += acceleration.y * deltaTime;
        
        // p = p0 + v*t
        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;
        
        updateBounds();
    }
    
    protected void updateBounds() {
        bounds.setLocation((int)position.x, (int)position.y);
    }
    
    public boolean collidesWith(GameObject other) {
        return bounds.intersects(other.bounds);
    }
    
    // Getters y Setters
    public Vector2D getPosition() { return position; }
    public void setPosition(Vector2D position) { 
        this.position = position; 
        updateBounds();
    }
    
    public Vector2D getVelocity() { return velocity; }
    public void setVelocity(Vector2D velocity) { this.velocity = velocity; }
    
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    
    public Rectangle getBounds() { return bounds; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}