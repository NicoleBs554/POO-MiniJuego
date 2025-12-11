import java.awt.*;

public abstract class GameObject {
    protected int x, y;
    protected int width, height;
    protected Rectangle bounds;
    
    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle(x, y, width, height);
    }
    
    public abstract void update();
    public abstract void draw(Graphics2D g2d);
    
    public Rectangle getBounds() {
        bounds.setLocation(x, y);
        return bounds;
    }
    
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}