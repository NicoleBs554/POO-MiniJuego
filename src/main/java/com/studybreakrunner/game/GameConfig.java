package com.studybreakrunner.game;

import java.awt.Color;
import java.awt.Font;

public class GameConfig {
    // Ventana
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final String GAME_TITLE = "Study Break Runner";
    public static final int FPS = 60;
    public static final int TARGET_FRAME_TIME = 1000 / FPS;
    
    // Jugador
    public static final int PLAYER_START_X = 100;
    public static final int PLAYER_START_Y = 400;
    public static final int PLAYER_WIDTH = 50;
    public static final int PLAYER_HEIGHT = 60;
    public static final float PLAYER_MOVE_SPEED = 5.0f;
    public static final float PLAYER_JUMP_FORCE = 15.0f;
    public static final int PLAYER_MAX_HEALTH = 100;
    
    // Física
    public static final float GRAVITY = 0.8f;
    public static final float TERMINAL_VELOCITY = 20.0f;
    
    // Mundo
    public static final int GROUND_LEVEL = 500;
    public static final float BASE_SCROLL_SPEED = 3.0f;
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    
    // Puntuación
    public static final int SCORE_PER_SECOND = 10;
    
    // Colores
    public static final Color BACKGROUND_COLOR = new Color(135, 206, 235); // Sky blue
    public static final Color GROUND_COLOR = new Color(139, 69, 19);      // Brown
    public static final Color PLAYER_COLOR = new Color(30, 144, 255);     // Dodger blue
    public static final Color PROFESSOR_COLOR = new Color(178, 34, 34);   // Firebrick
    public static final Color OBSTACLE_COLOR = new Color(255, 215, 0);    // Gold
    
    // Fuentes
    public static final Font HUD_FONT = new Font("Arial", Font.BOLD, 16);
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 48);
}