package com.studybreakrunner.managers;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.studybreakrunner.entities.GameObject;
import com.studybreakrunner.entities.Obstacle;
import com.studybreakrunner.entities.Player;
import com.studybreakrunner.entities.Professor;
import com.studybreakrunner.game.GameConfig;

public class WorldManager {
    private ArrayList<GameObject> gameObjects;
    private Random random;
    private int generationTimer;
    private float scrollSpeed;
    private final int GENERATION_INTERVAL = 90; // Frames entre generaciones
    
    public WorldManager() {
        gameObjects = new ArrayList<>();
        random = new Random();
        generationTimer = 0;
        scrollSpeed = GameConfig.BASE_SCROLL_SPEED;
    }
    
    public void update(float deltaTime, Player player) {
        // Actualizar objetos
        for (GameObject obj : gameObjects) {
            obj.update(deltaTime);
        }
        
        // Generar nuevos objetos
        generationTimer++;
        if (generationTimer >= GENERATION_INTERVAL) {
            generateObject();
            generationTimer = 0;
        }
        
        // Limpiar objetos fuera de pantalla o inactivos
        cleanUp();
    }
    
    private void generateObject() {
        float x = GameConfig.SCREEN_WIDTH;
        float y = GameConfig.GROUND_LEVEL - 80;
        
        GameObject obj;
        int type = random.nextInt(100);
        
        if (type < 70) { // 70% profesores
            obj = new Professor(x, y);
        } else { // 30% asignaciones
            obj = new Obstacle(x, y);
        }
        
        gameObjects.add(obj);
    }
    
    public void draw(Graphics2D g2d) {
        for (GameObject obj : gameObjects) {
            if (obj.isActive()) {
                obj.render(g2d);
            }
        }
    }
    
    private void cleanUp() {
        Iterator<GameObject> iterator = gameObjects.iterator();
        while (iterator.hasNext()) {
            GameObject obj = iterator.next();
            if (!obj.isActive() || obj.getPosition().x < -100) {
                iterator.remove();
            }
        }
    }
    
    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }
    
    public void setScrollSpeed(float speed) {
        this.scrollSpeed = speed;
    }
    
    public float getScrollSpeed() {
        return scrollSpeed;
    }
    
    public void reset() {
        gameObjects.clear();
        generationTimer = 0;
        scrollSpeed = GameConfig.BASE_SCROLL_SPEED;
    }
}