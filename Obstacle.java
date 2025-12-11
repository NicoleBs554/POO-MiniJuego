import java.awt.*;
import java.util.Random;

public class Obstacle extends GameObject {
    private boolean collected;
    private AssignmentType type;
    private Random random;
    
    public enum AssignmentType {
        HOMEWORK,       // +50 puntos
        ESSAY,          // +100 puntos
        PROJECT,        // +150 puntos
        EXTRA_CREDIT    // +200 puntos y estamina
    }
    
    public Obstacle(int x, int y) {
        super(x, y, 40, 40);
        this.collected = false;
        this.random = new Random();
        this.type = getRandomType();
    }
    
    private AssignmentType getRandomType() {
        AssignmentType[] types = AssignmentType.values();
        return types[random.nextInt(types.length)];
    }
    
    @Override
    public void update() {
        x -= 5; // Se mueve hacia la izquierda
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        if (!collected) {
            // Libro/carpeta
            Color bookColor = getBookColor();
            g2d.setColor(bookColor);
            g2d.fillRect(x, y, width, height);
            
            // Páginas
            g2d.setColor(Color.WHITE);
            g2d.fillRect(x + 5, y + 5, width - 10, height - 10);
            
            // Título
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 10));
            String title = getShortName();
            int titleWidth = g2d.getFontMetrics().stringWidth(title);
            g2d.drawString(title, x + (width - titleWidth)/2, y + height/2 + 5);
            
            // Brillo si es extra credit
            if (type == AssignmentType.EXTRA_CREDIT) {
                g2d.setColor(new Color(255, 215, 0, 100));
                g2d.fillOval(x - 10, y - 10, width + 20, height + 20);
            }
        }
    }
    
    private Color getBookColor() {
        switch(type) {
            case HOMEWORK: return new Color(255, 140, 0); // Naranja
            case ESSAY: return new Color(50, 205, 50); // Verde lima
            case PROJECT: return new Color(30, 144, 255); // Azul dodger
            case EXTRA_CREDIT: return new Color(255, 215, 0); // Oro
            default: return Color.GRAY;
        }
    }
    
    private String getShortName() {
        switch(type) {
            case HOMEWORK: return "HW";
            case ESSAY: return "ES";
            case PROJECT: return "PR";
            case EXTRA_CREDIT: return "EC";
            default: return "??";
        }
    }
    
    public boolean isCollected() { return collected; }
    public void setCollected(boolean collected) { this.collected = collected; }
    public AssignmentType getType() { return type; }
    
    public int getPoints() {
        switch(type) {
            case HOMEWORK: return 50;
            case ESSAY: return 100;
            case PROJECT: return 150;
            case EXTRA_CREDIT: return 200;
            default: return 0;
        }
    }
}