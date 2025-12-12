package com.studybreakrunner.utils;

public class Vector2D {
    public float x;
    public float y;
    
    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector2D() {
        this(0, 0);
    }
    
    public void add(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
    }
    
    public void subtract(Vector2D other) {
        this.x -= other.x;
        this.y -= other.y;
    }
    
    public void multiply(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }
    
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }
    
    public void normalize() {
        float mag = magnitude();
        if (mag > 0) {
            this.x /= mag;
            this.y /= mag;
        }
    }
    
    public static Vector2D add(Vector2D a, Vector2D b) {
        return new Vector2D(a.x + b.x, a.y + b.y);
    }
    
    public static Vector2D subtract(Vector2D a, Vector2D b) {
        return new Vector2D(a.x - b.x, a.y - b.y);
    }
    
    public static Vector2D multiply(Vector2D v, float scalar) {
        return new Vector2D(v.x * scalar, v.y * scalar);
    }
}