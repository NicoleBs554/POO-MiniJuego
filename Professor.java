import java.awt.*;
import java.util.Random;

public class Professor extends GameObject {
    private int speed;
    private ProfessorType type;
    private Random random;
    
    public enum ProfessorType {
        MATH,       // Se mueve en línea recta
        HISTORY,    // Cambia de carril aleatoriamente
        SCIENCE,    // Se mueve más rápido
        PHILOSOPHY  // Se mueve lentamente pero ocupa más espacio
    }
    
    public Professor(int x, int y) {
        super(x, y, 60, 80);
        this.random = new Random();
        this.type = getRandomType();
        this.speed = getSpeedByType();
    }
    
    private ProfessorType getRandomType() {
        ProfessorType[] types = ProfessorType.values();
        return types[random.nextInt(types.length)];
    }
    
    private int getSpeedByType() {
        switch(type) {
            case SCIENCE: return -8;
            case MATH: return -5;
            case HISTORY: return -4;
            case PHILOSOPHY: return -3;
            default: return -5;
        }
    }
    
    @Override
    public void update() {
        x += speed;
        
        // Los profesores de historia cambian de carril
        if (type == ProfessorType.HISTORY && random.nextInt(100) < 2) {
            y += random.nextInt(3) - 1;
            y = Math.max(300, Math.min(500, y));
        }
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        // Cuerpo del profesor
        Color professorColor = getColorByType();
        g2d.setColor(professorColor);
        g2d.fillRect(x, y, width, height);
        
        // Cabeza
        g2d.setColor(new Color(255, 218, 185));
        g2d.fillOval(x + width/2 - 20, y - 25, 40, 40);
        
        // Toga/corbata según tipo
        g2d.setColor(getDetailColor());
        g2d.fillRect(x + 10, y + 30, width - 20, 20);
        
        // Indicador de tipo
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        String typeText = type.name().substring(0, 3);
        g2d.drawString(typeText, x + width/2 - 15, y + 50);
    }
    
    private Color getColorByType() {
        switch(type) {
            case MATH: return new Color(220, 20, 60); // Rojo carmesí
            case HISTORY: return new Color(46, 139, 87); // Verde mar
            case SCIENCE: return new Color(70, 130, 180); // Azul acero
            case PHILOSOPHY: return new Color(128, 0, 128); // Púrpura
            default: return Color.GRAY;
        }
    }
    
    private Color getDetailColor() {
        switch(type) {
            case MATH: return Color.WHITE;
            case HISTORY: return new Color(255, 215, 0); // Oro
            case SCIENCE: return Color.BLACK;
            case PHILOSOPHY: return new Color(139, 69, 19); // Marrón
            default: return Color.GRAY;
        }
    }
    
    public ProfessorType getType() { return type; }
}