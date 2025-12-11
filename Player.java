import java.awt.*;

public class Player extends GameObject {
    private int stamina;
    private boolean isJumping;
    private int yVelocity;
    private final int JUMP_FORCE = -20;
    private final int GRAVITY = 1;
    private final int GROUND_LEVEL = 400;
    
    // Sistema de carriles
    private int currentLane;
    private final int[] LANE_POSITIONS = {150, 300, 450};
    private final int LANE_WIDTH = 150;
    
    public Player(int x, int y) {
        super(x, y, 50, 60);
        this.stamina = 100;
        this.isJumping = false;
        this.yVelocity = 0;
        this.currentLane = 1; // Carril central
        this.x = LANE_POSITIONS[currentLane] - width/2;
        this.y = GROUND_LEVEL - height;
    }
    
    @Override
    public void update() {
        // Actualizar posición Y si está saltando
        if (isJumping) {
            y += yVelocity;
            yVelocity += GRAVITY;
            
            // Si toca el suelo
            if (y >= GROUND_LEVEL - height) {
                y = GROUND_LEVEL - height;
                isJumping = false;
                yVelocity = 0;
            }
        }
        
        // Regenerar estamina lentamente
        if (!isJumping && stamina < 100) {
            stamina += 0.1;
            if (stamina > 100) stamina = 100;
        }
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        // Cuerpo del estudiante
        g2d.setColor(new Color(30, 144, 255)); // Azul estudiante
        g2d.fillRect(x, y, width, height);
        
        // Cabeza
        g2d.setColor(new Color(255, 218, 185)); // Color piel
        g2d.fillOval(x + width/2 - 15, y - 20, 30, 30);
        
        // Mochila
        g2d.setColor(new Color(160, 82, 45)); // Marrón
        g2d.fillRect(x + width - 10, y + 10, 15, 30);
        
        // Indicador de carril
        g2d.setColor(new Color(255, 255, 0, 100));
        g2d.fillRect(LANE_POSITIONS[currentLane] - LANE_WIDTH/2, 
                     GROUND_LEVEL + 10, LANE_WIDTH, 10);
    }
    
    public void jump() {
        if (!isJumping && stamina >= 20) {
            isJumping = true;
            yVelocity = JUMP_FORCE;
            stamina -= 20;
        }
    }
    
    public void moveLane(int direction) {
        int newLane = currentLane + direction;
        if (newLane >= 0 && newLane < LANE_POSITIONS.length) {
            currentLane = newLane;
            x = LANE_POSITIONS[currentLane] - width/2;
        }
    }
    
    public void reduceStamina(int amount) {
        stamina -= amount;
        if (stamina < 0) stamina = 0;
    }
    
    // Getters
    public int getStamina() { return stamina; }
    public boolean isJumping() { return isJumping; }
    public int getCurrentLane() { return currentLane; }
}