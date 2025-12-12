package com.studybreakrunner.game;

import com.studybreakrunner.entities.Player;
import com.studybreakrunner.managers.WorldManager;
import com.studybreakrunner.managers.CollisionManager;
import com.studybreakrunner.managers.SoundManager;
import com.studybreakrunner.input.InputHandler;
import com.studybreakrunner.ui.HUD;
import com.studybreakrunner.ui.MenuScreen;
import com.studybreakrunner.ui.GameOverScreen;
import com.studybreakrunner.ui.PauseScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {
    private Timer gameTimer;
    private GameState gameState;
    private Player player;
    private WorldManager worldManager;
    private InputHandler inputHandler;
    private SoundManager soundManager;
    
    // Pantallas UI
    private HUD hud;
    private MenuScreen menuScreen;
    private GameOverScreen gameOverScreen;
    private PauseScreen pauseScreen;
    
    // Datos del juego
    private int score;
    private float gameTime;
    private boolean isRunning;
    private boolean soundInitialized = false; // Bandera para controlar inicialización de sonido
    
    public GamePanel() {
        // Configurar panel
        setPreferredSize(new Dimension(
            GameConfig.WINDOW_WIDTH,
            GameConfig.WINDOW_HEIGHT
        ));
        setBackground(GameConfig.BACKGROUND_COLOR);
        setFocusable(true);
        
        // Inicializar componentes
        initComponents();
        
        // Configurar manejador de entrada
        inputHandler = new InputHandler();
        inputHandler.setGamePanel(this);
        addKeyListener(inputHandler);
        
        // Iniciar en menú
        gameState = GameState.MENU;
    }
    
    private void initComponents() {
        // Inicializar jugador
        player = new Player(
            GameConfig.PLAYER_START_X,
            GameConfig.PLAYER_START_Y
        );
        
        // Inicializar gestores
        worldManager = new WorldManager();
        
        // Inicializar SoundManager (manejar excepciones silenciosamente)
        try {
            soundManager = SoundManager.getInstance();
            soundInitialized = true;
        } catch (Exception e) {
            System.err.println("Advertencia: No se pudo inicializar SoundManager. El juego continuará sin sonido.");
            soundInitialized = false;
        }
        
        // Inicializar pantallas UI
        hud = new HUD();
        menuScreen = new MenuScreen();
        gameOverScreen = new GameOverScreen();
        pauseScreen = new PauseScreen();
        
        // Inicializar temporizador
        gameTimer = new Timer(GameConfig.TARGET_FRAME_TIME, this);
        
        // Inicializar datos
        score = 0;
        gameTime = 0;
        isRunning = false;
    }
    
    public void startGame() {
        // Ya no iniciamos el temporizador automáticamente
        // Se inicia cuando cambiamos a estado PLAYING
        if (soundInitialized) {
            try {
                soundManager.startBackgroundMusic();
            } catch (Exception e) {
                // Silenciar error de sonido
                soundInitialized = false;
            }
        }
    }
    
    public void setGameState(GameState newState) {
        this.gameState = newState;
        
        switch (newState) {
            case MENU:
                stopGameLoop();
                resetGame();
                if (soundInitialized) {
                    try {
                        soundManager.startBackgroundMusic();
                    } catch (Exception e) {
                        // Ignorar error
                    }
                }
                break;
                
            case PLAYING:
                if (!isRunning) {
                    startGameLoop();
                    if (soundInitialized) {
                        try {
                            soundManager.startBackgroundMusic();
                        } catch (Exception e) {
                            // Ignorar error
                        }
                    }
                }
                break;
                
            case PAUSED:
                stopGameLoop();
                if (soundInitialized) {
                    try {
                        soundManager.pauseBackgroundMusic();
                    } catch (Exception e) {
                        // Ignorar error
                    }
                }
                break;
                
            case GAME_OVER:
                stopGameLoop();
                gameOverScreen.setFinalStats(score, hud.getCoins(), gameTime);
                if (soundInitialized) {
                    try {
                        soundManager.playGameOverSound();
                    } catch (Exception e) {
                        // Ignorar error
                    }
                }
                break;
        }
        
        repaint();
    }
    
    private void startGameLoop() {
        if (!gameTimer.isRunning()) {
            gameTimer.start();
            isRunning = true;
        }
    }
    
    private void stopGameLoop() {
        if (gameTimer.isRunning()) {
            gameTimer.stop();
            isRunning = false;
        }
    }
    
    private void resetGame() {
        // Reiniciar jugador
        player = new Player(
            GameConfig.PLAYER_START_X,
            GameConfig.PLAYER_START_Y
        );
        
        // Reiniciar mundo
        worldManager.reset();
        
        // Reiniciar datos
        score = 0;
        gameTime = 0;
        hud.reset();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameState != GameState.PLAYING) return;
        
        updateGame();
        repaint();
    }
    
    private void updateGame() {
        float deltaTime = 1.0f / GameConfig.FPS;
        
        // Actualizar tiempo
        gameTime += deltaTime;
        score += GameConfig.SCORE_PER_SECOND;
        
        // Actualizar jugador
        player.update(deltaTime);
        
        // Actualizar mundo
        worldManager.update(deltaTime, player);
        
        // Verificar colisiones
        CollisionManager.handlePlayerCollisions(player, worldManager.getGameObjects());
        
        // Verificar condición de game over
        if (player.getLives() <= 0) {
            setGameState(GameState.GAME_OVER);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Aplicar configuración de renderizado
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Renderizar según estado
        switch (gameState) {
            case MENU:
                renderMenu(g2d);
                break;
                
            case PLAYING:
                renderGame(g2d);
                break;
                
            case PAUSED:
                renderGame(g2d);
                renderPause(g2d);
                break;
                
            case GAME_OVER:
                renderGame(g2d);
                renderGameOver(g2d);
                break;
        }
    }
    
    private void renderMenu(Graphics2D g2d) {
        menuScreen.render(g2d, getWidth(), getHeight());
    }
    
    private void renderGame(Graphics2D g2d) {
        // Fondo
        g2d.setColor(GameConfig.BACKGROUND_COLOR);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Suelo
        g2d.setColor(GameConfig.GROUND_COLOR);
        g2d.fillRect(0, GameConfig.GROUND_LEVEL,
                     getWidth(), getHeight() - GameConfig.GROUND_LEVEL);
        
        // Renderizar mundo
        worldManager.draw(g2d);
        
        // Renderizar jugador
        player.render(g2d);
        
        // Renderizar HUD
        hud.setScore(score);
        hud.update(1.0f / GameConfig.FPS);
        hud.render(g2d, getWidth(), getHeight());
    }
    
    private void renderPause(Graphics2D g2d) {
        // Fondo semitransparente
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Pantalla de pausa
        pauseScreen.render(g2d, getWidth(), getHeight());
    }
    
    private void renderGameOver(Graphics2D g2d) {
        // Fondo semitransparente
        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Pantalla de game over
        gameOverScreen.render(g2d, getWidth(), getHeight());
    }
    
    // Métodos para InputHandler
    public void keyPressed(int keyCode) {
        switch (gameState) {
            case MENU:
                handleMenuKey(keyCode);
                break;
                
            case PLAYING:
                handleGameKey(keyCode);
                break;
                
            case PAUSED:
                handlePauseKey(keyCode);
                break;
                
            case GAME_OVER:
                handleGameOverKey(keyCode);
                break;
        }
    }
    
    public void keyReleased(int keyCode) {
        if (gameState == GameState.PLAYING) {
            if (keyCode == 37 || keyCode == 39) { // LEFT o RIGHT
                player.stopMoving();
            }
        }
    }
    
    private void handleMenuKey(int keyCode) {
        if (keyCode == 32) { // SPACE
            setGameState(GameState.PLAYING);
        } else if (keyCode == 27) { // ESC
            if (soundInitialized) {
                try {
                    soundManager.cleanup();
                } catch (Exception e) {
                    // Ignorar error
                }
            }
            System.exit(0);
        }
    }
    
    private void handleGameKey(int keyCode) {
        switch (keyCode) {
            case 32: // SPACE
                player.jump();
                if (soundInitialized) {
                    try {
                        soundManager.playJumpSound();
                    } catch (Exception e) {
                        // Ignorar error
                    }
                }
                break;
                
            case 37: // LEFT
                player.moveLeft();
                break;
                
            case 39: // RIGHT
                player.moveRight();
                break;
                
            case 80: // P
                setGameState(GameState.PAUSED);
                break;
                
            case 27: // ESC
                setGameState(GameState.MENU);
                break;
        }
    }
    
    private void handlePauseKey(int keyCode) {
        if (keyCode == 80) { // P
            setGameState(GameState.PLAYING);
            if (soundInitialized) {
                try {
                    soundManager.resumeBackgroundMusic();
                } catch (Exception e) {
                    // Ignorar error
                }
            }
        } else if (keyCode == 27) { // ESC
            setGameState(GameState.MENU);
        }
    }
    
    private void handleGameOverKey(int keyCode) {
        if (keyCode == 82) { // R
            if (soundInitialized) {
                try {
                    soundManager.startBackgroundMusic();
                } catch (Exception e) {
                    // Ignorar error
                }
            }
            setGameState(GameState.MENU);
        } else if (keyCode == 27) { // ESC
            if (soundInitialized) {
                try {
                    soundManager.cleanup();
                } catch (Exception e) {
                    // Ignorar error
                }
            }
            System.exit(0);
        }
    }
    
    // Getters
    public GameState getGameState() { return gameState; }
    public Player getPlayer() { return player; }
    public int getScore() { return score; }
    public boolean isSoundInitialized() { return soundInitialized; }
}