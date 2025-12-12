package com.studybreakrunner.input;

import com.studybreakrunner.game.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    private GamePanel gamePanel;
    private boolean[] keys;
    
    public InputHandler() {
        keys = new boolean[256];
    }
    
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        
        if (gamePanel != null) {
            gamePanel.keyPressed(e.getKeyCode());
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        
        if (gamePanel != null) {
            gamePanel.keyReleased(e.getKeyCode());
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // No se usa
    }
    
    public boolean isKeyPressed(int keyCode) {
        return keys[keyCode];
    }
}