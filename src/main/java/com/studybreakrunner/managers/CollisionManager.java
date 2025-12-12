package com.studybreakrunner.managers;

import java.util.ArrayList;

import com.studybreakrunner.entities.GameObject;
import com.studybreakrunner.entities.Obstacle;
import com.studybreakrunner.entities.Player;
import com.studybreakrunner.entities.Professor;

public class CollisionManager {
    
    public static void handlePlayerCollisions(Player player, ArrayList<GameObject> gameObjects) {
        SoundManager soundManager = SoundManager.getInstance();  // <-- OBTENER INSTANCIA
        
        for (GameObject obj : gameObjects) {
            if (!obj.isActive()) continue;
            
            if (player.collidesWith(obj)) {
                if (obj instanceof Professor) {
                    handleProfessorCollision(player, (Professor) obj, soundManager);
                } else if (obj instanceof Obstacle) {
                    handleObstacleCollision(player, (Obstacle) obj, soundManager);
                }
            }
        }
    }
    
    private static void handleProfessorCollision(Player player, Professor professor, SoundManager soundManager) {
        // El jugador recibe daño
        player.takeDamage(professor.getDamage());
        
        // Reproducir sonido de golpe
        if (soundManager != null) {
            soundManager.playHitSound();  // <-- SONIDO DE GOLPE
        }
        
        // Aplicar retroceso
        if (player.getPosition().x < professor.getPosition().x) {
            player.getVelocity().x = -5.0f;
        } else {
            player.getVelocity().x = 5.0f;
        }
        player.getVelocity().y = -3.0f;
        
        // Desactivar profesor
        professor.setActive(false);
    }
    
    private static void handleObstacleCollision(Player player, Obstacle obstacle, SoundManager soundManager) {
        if (!obstacle.isCollected()) {
            // Añadir puntuación
            player.addScore(obstacle.getScoreValue());
            player.addCoins(1);
            
            // Reproducir sonido de colección
            if (soundManager != null) {
                soundManager.playCollectSound();  // <-- SONIDO DE COLECCIÓN
            }
            
            // Marcar como recolectado
            obstacle.collect();
        }
    }
    
    public static boolean isPlayerGrounded(Player player, ArrayList<GameObject> gameObjects, float groundLevel) {
        // Verificar si está en el suelo base
        if (player.getPosition().y >= groundLevel - player.getHeight()) {
            return true;
        }
        
        // Verificar colisión con objetos
        for (GameObject obj : gameObjects) {
            if (obj.isActive() && player.collidesWith(obj)) {
                // Solo cuenta como suelo si el objeto está debajo del jugador
                if (player.getPosition().y + player.getHeight() <= obj.getPosition().y + 10) {
                    return true;
                }
            }
        }
        
        return false;
    }
}