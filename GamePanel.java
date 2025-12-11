import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final int GAME_TICK = 10;
    private Timer gameTimer;
    private Player player;
    private WorldManager worldManager;
    private boolean isRunning = true;
    private boolean isPaused = false;
    
    // Constantes del juego
    private final int GROUND_LEVEL = 400;
    private final Color BACKGROUND_COLOR = new Color(135, 206, 235); // Cielo azul
    private final Color GROUND_COLOR = new Color(139, 69, 19); // Marrón tierra
    
    // HUD
    private int score = 0;
    private int comboMultiplier = 1;
    private int scholarshipPoints = 0;

    public GamePanel() {
        // Inicializar objetos
        player = new Player(100, GROUND_LEVEL - 60);
        worldManager = new WorldManager();
        
        // Configurar panel
        setPreferredSize(new Dimension(800, 600));
        
        // Configurar Timer
        gameTimer = new Timer(GAME_TICK, this);
        gameTimer.start();
        
        // Configurar controles
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning && !isPaused) {
            updateGame();
        }
        repaint();
    }

    private void updateGame() {
        player.update();
        worldManager.update(player);
        
        // Detección de colisiones
        for (GameObject obj : worldManager.getGameObjects()) {
            if (checkCollision(player, obj)) {
                handleCollision(player, obj);
            }
        }
        
        // Actualizar puntuación
        score += 1 * comboMultiplier;
        
        // Generar nuevos objetos
        worldManager.generateObjects();
        
        // Limpiar objetos fuera de pantalla
        worldManager.cleanUp();
        
        // Verificar Game Over
        if (player.getStamina() <= 0) {
            gameOver();
        }
    }

    private boolean checkCollision(Player p, GameObject obj) {
        Rectangle playerBounds = p.getBounds();
        Rectangle objBounds = obj.getBounds();
        return playerBounds.intersects(objBounds);
    }

    private void handleCollision(Player player, GameObject obj) {
        if (obj instanceof Professor) {
            player.reduceStamina(15);
            comboMultiplier = 1;
            scholarshipPoints = Math.max(0, scholarshipPoints - 10);
        } else if (obj instanceof Obstacle) {
            Obstacle obstacle = (Obstacle) obj;
            if (!obstacle.isCollected()) {
                obstacle.setCollected(true);
                scholarshipPoints += 50;
                comboMultiplier++;
                if (comboMultiplier > 5) comboMultiplier = 5;
            }
        }
    }

    private void gameOver() {
        isRunning = false;
        gameTimer.stop();
        System.out.println("Game Over! Puntuación Final: " + score);
        System.out.println("Puntos de Beca: " + scholarshipPoints);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Mejorar calidad de renderizado
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Dibujar fondo
        drawBackground(g2d);
        
        // Dibujar entidades
        player.draw(g2d);
        worldManager.draw(g2d);
        
        // Dibujar HUD
        drawHUD(g2d);
        
        // Dibujar pantalla de pausa
        if (isPaused) {
            drawPauseScreen(g2d);
        }
        
        // Dibujar pantalla de Game Over
        if (!isRunning) {
            drawGameOverScreen(g2d);
        }
    }

    private void drawBackground(Graphics2D g2d) {
        // Cielo
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, getWidth(), GROUND_LEVEL);
        
        // Suelo
        g2d.setColor(GROUND_COLOR);
        g2d.fillRect(0, GROUND_LEVEL, getWidth(), getHeight() - GROUND_LEVEL);
        
        // Líneas de carril
        g2d.setColor(Color.YELLOW);
        int[] lanes = {150, 300, 450};
        for (int lane : lanes) {
            g2d.fillRect(0, GROUND_LEVEL + lane, getWidth(), 5);
        }
    }

    private void drawHUD(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Puntuación
        g2d.drawString("Puntuación: " + score, 20, 30);
        g2d.drawString("Combo: x" + comboMultiplier, 20, 55);
        
        // Barra de estamina
        g2d.drawString("Estamina: ", 20, 80);
        g2d.setColor(Color.GREEN);
        g2d.fillRect(100, 65, player.getStamina() * 2, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(100, 65, 200, 20);
        
        // Puntos de beca
        g2d.setColor(Color.BLUE);
        g2d.drawString("Beca: " + scholarshipPoints, getWidth() - 120, 30);
        
        // Carril actual
        g2d.drawString("Carril: " + (player.getCurrentLane() + 1), getWidth() - 120, 55);
    }

    private void drawPauseScreen(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        String pauseText = "PAUSA";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(pauseText)) / 2;
        g2d.drawString(pauseText, x, getHeight() / 2);
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 24));
        String continueText = "Presiona P para continuar";
        fm = g2d.getFontMetrics();
        x = (getWidth() - fm.stringWidth(continueText)) / 2;
        g2d.drawString(continueText, x, getHeight() / 2 + 50);
    }

    private void drawGameOverScreen(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 200));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        String gameOverText = "GAME OVER";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(gameOverText)) / 2;
        g2d.drawString(gameOverText, x, getHeight() / 2 - 50);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 24));
        String scoreText = "Puntuación Final: " + score;
        fm = g2d.getFontMetrics();
        x = (getWidth() - fm.stringWidth(scoreText)) / 2;
        g2d.drawString(scoreText, x, getHeight() / 2);
        
        String scholarshipText = "Puntos de Beca: " + scholarshipPoints;
        fm = g2d.getFontMetrics();
        x = (getWidth() - fm.stringWidth(scholarshipText)) / 2;
        g2d.drawString(scholarshipText, x, getHeight() / 2 + 40);
    }

    // KeyListener methods
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        if (keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_UP) {
            player.jump();
        }
        
        if (keyCode == KeyEvent.VK_LEFT) {
            player.moveLane(-1);
        }
        
        if (keyCode == KeyEvent.VK_RIGHT) {
            player.moveLane(1);
        }
        
        if (keyCode == KeyEvent.VK_P) {
            togglePause();
        }
        
        if (keyCode == KeyEvent.VK_R && !isRunning) {
            restartGame();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            gameTimer.stop();
        } else {
            gameTimer.start();
        }
        repaint();
    }

    private void restartGame() {
        player = new Player(100, GROUND_LEVEL - 60);
        worldManager = new WorldManager();
        score = 0;
        comboMultiplier = 1;
        scholarshipPoints = 0;
        isRunning = true;
        isPaused = false;
        gameTimer.start();
        requestFocusInWindow();
    }
}