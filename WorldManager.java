import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class WorldManager {
    private ArrayList<GameObject> gameObjects;
    private Random random;
    private int generationTimer;
    private final int GENERATION_INTERVAL = 60; // Frames entre generaciones
    
    public WorldManager() {
        gameObjects = new ArrayList<>();
        random = new Random();
        generationTimer = 0;
        
        // Generar algunos objetos iniciales
        generateInitialObjects();
    }
    
    private void generateInitialObjects() {
        for (int i = 0; i < 3; i++) {
            generateObject(800 + i * 200, 400);
        }
    }
    
    public void update(Player player) {
        // Actualizar todos los objetos
        for (GameObject obj : gameObjects) {
            obj.update();
        }
        
        generationTimer++;
    }
    
    public void generateObjects() {
        if (generationTimer >= GENERATION_INTERVAL) {
            generateObject(800, 400);
            generationTimer = 0;
            
            // Ocasionalmente generar objeto en carril diferente
            if (random.nextInt(100) < 30) {
                int randomY = 300 + random.nextInt(3) * 50;
                generateObject(850, randomY);
            }
        }
    }
    
    private void generateObject(int x, int y) {
        GameObject obj;
        
        // 70% probabilidad de profesor, 30% de asignación
        if (random.nextInt(100) < 70) {
            obj = new Professor(x, y);
        } else {
            obj = new Obstacle(x, y);
        }
        
        gameObjects.add(obj);
    }
    
    public void draw(Graphics2D g2d) {
        for (GameObject obj : gameObjects) {
            obj.draw(g2d);
        }
    }
    
    public void cleanUp() {
        Iterator<GameObject> iterator = gameObjects.iterator();
        while (iterator.hasNext()) {
            GameObject obj = iterator.next();
            
            // Eliminar si está fuera de pantalla
            if (obj.getX() < -100) {
                iterator.remove();
            }
            
            // Eliminar asignaciones recolectadas
            if (obj instanceof Obstacle) {
                Obstacle obstacle = (Obstacle) obj;
                if (obstacle.isCollected()) {
                    iterator.remove();
                }
            }
        }
    }
    
    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }
}