package com.studybreakrunner.managers;

import javax.sound.sampled.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static SoundManager instance;
    private Clip backgroundMusic;
    private Map<String, Clip> soundEffects;
    private float volume = 0.7f; // Volumen por defecto (0.0 a 1.0)
    private boolean soundEnabled = true;
    private boolean musicEnabled = true;
    
    private SoundManager() {
        soundEffects = new HashMap<>();
        loadSoundEffects();
    }
    
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }
    
    private void loadSoundEffects() {
        try {
            // Cargar efectos de sonido desde carpeta fx/
            loadSound("jump", "resources/audio/sfx/jump.wav");
            loadSound("collect", "resources/audio/sfx/collect.wav");
            loadSound("hit", "resources/audio/sfx/hit.wav");
            loadSound("game_over", "resources/audio/sfx/game_over.wav");
            
            // Cargar música de fondo
            loadBackgroundMusic("resources/audio/music/main_theme.ogg");
            
            // Iniciar música de fondo automáticamente
            if (musicEnabled && backgroundMusic != null) {
                backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
                setClipVolume(backgroundMusic, volume);
            }
            
        } catch (Exception e) {
            System.err.println("Error cargando archivos de audio: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadSound(String key, String path) {
        try {
            System.out.println("Intentando cargar sonido: " + path);
            
            InputStream audioSrc = getClass().getResourceAsStream(path);
            if (audioSrc == null) {
                System.err.println("ERROR: No se encontró el archivo en: " + path);
                System.err.println("Ruta actual: " + new File(".").getAbsolutePath());
                return;
            }
            
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            
            // Crear un formato compatible si es necesario
            AudioFormat baseFormat = audioStream.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false
            );
            
            // Convertir a formato compatible
            AudioInputStream decodedStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);
            
            DataLine.Info info = new DataLine.Info(Clip.class, decodedFormat);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(decodedStream);
            
            soundEffects.put(key, clip);
            System.out.println("Sonido cargado exitosamente: " + key);
            
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Formato de audio no soportado para " + key + ": " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S cargando " + key + ": " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.err.println("Línea de audio no disponible para " + key + ": " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado cargando " + key + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadBackgroundMusic(String path) {
        try {
            System.out.println("Intentando cargar música de fondo: " + path);
            
            // Para MP3, necesitamos usar SPI
            AudioInputStream audioStream;
            
            if (path.toLowerCase().endsWith(".ogg")) {
                // Para MP3
                InputStream audioSrc = getClass().getResourceAsStream(path);
                if (audioSrc == null) {
                    System.err.println("ERROR: No se encontró el archivo MP3: " + path);
                    return;
                }
                
                // El SPI de MP3 debería estar registrado automáticamente
                audioStream = AudioSystem.getAudioInputStream(
                    new BufferedInputStream(audioSrc)
                );
            } else {
                // Para WAV y otros formatos nativos
                InputStream audioSrc = getClass().getResourceAsStream(path);
                if (audioSrc == null) {
                    System.err.println("ERROR: No se encontró el archivo: " + path);
                    return;
                }
                audioStream = AudioSystem.getAudioInputStream(
                    new BufferedInputStream(audioSrc)
                );
            }
            
            // Convertir a formato PCM para mejor compatibilidad
            AudioFormat baseFormat = audioStream.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false
            );
            
            AudioInputStream decodedStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);
            
            DataLine.Info info = new DataLine.Info(Clip.class, decodedFormat);
            backgroundMusic = (Clip) AudioSystem.getLine(info);
            backgroundMusic.open(decodedStream);
            
            System.out.println("Música de fondo cargada exitosamente");
            
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Formato de música no soportado: " + e.getMessage());
            System.err.println("Sugerencia: Convierte el MP3 a WAV o añade las dependencias MP3 SPI");
        } catch (Exception e) {
            System.err.println("Error cargando música: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Métodos públicos para reproducir sonidos
    public void playJumpSound() {
        System.out.println("Reproduciendo sonido de salto");
        playSound("jump");
    }
    
    public void playCollectSound() {
        System.out.println("Reproduciendo sonido de colección");
        playSound("collect");
    }
    
    public void playHitSound() {
        System.out.println("Reproduciendo sonido de golpe");
        playSound("hit");
    }
    
    public void playGameOverSound() {
        System.out.println("Reproduciendo sonido de game over");
        playSound("game_over");
        stopBackgroundMusic();
    }
    
    private void playSound(String key) {
        if (!soundEnabled) {
            System.out.println("Sonidos deshabilitados, no se reproduce: " + key);
            return;
        }
        
        Clip clip = soundEffects.get(key);
        if (clip != null) {
            try {
                // Reiniciar si ya se está reproduciendo
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.setFramePosition(0);
                setClipVolume(clip, volume);
                clip.start();
                System.out.println("Sonido " + key + " reproducido");
            } catch (Exception e) {
                System.err.println("Error reproduciendo sonido " + key + ": " + e.getMessage());
            }
        } else {
            System.err.println("Sonido no encontrado: " + key);
        }
    }
    
    // Control de música de fondo
    public void startBackgroundMusic() {
        if (!musicEnabled || backgroundMusic == null) return;
        
        if (!backgroundMusic.isRunning()) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            setClipVolume(backgroundMusic, volume);
        }
    }
    
    public void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
    }
    
    public void pauseBackgroundMusic() {
        stopBackgroundMusic();
    }
    
    public void resumeBackgroundMusic() {
        startBackgroundMusic();
    }
    
    // Control de volumen
    private void setClipVolume(Clip clip, float volume) {
        if (clip == null) return;
        
        try {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();
            
            // Convertir volumen (0.0-1.0) a dB
            float dB = min + (max - min) * volume;
            gainControl.setValue(dB);
        } catch (IllegalArgumentException e) {
            // Algunos clips no soportan control de volumen
            System.err.println("Clip no soporta control de volumen: " + e.getMessage());
        }
    }
    
    public void setVolume(float volume) {
        this.volume = Math.max(0.0f, Math.min(1.0f, volume));
        
        // Aplicar a música de fondo
        if (backgroundMusic != null) {
            setClipVolume(backgroundMusic, this.volume);
        }
        
        // Aplicar a todos los efectos de sonido
        for (Clip clip : soundEffects.values()) {
            setClipVolume(clip, this.volume);
        }
    }
    
    // Getters y setters
    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
    }
    
    public void setMusicEnabled(boolean enabled) {
        this.musicEnabled = enabled;
        if (!enabled) {
            stopBackgroundMusic();
        } else {
            startBackgroundMusic();
        }
    }
    
    public boolean isSoundEnabled() {
        return soundEnabled;
    }
    
    public boolean isMusicEnabled() {
        return musicEnabled;
    }
    
    public float getVolume() {
        return volume;
    }
    
    // Limpieza
    public void cleanup() {
        System.out.println("Limpiando recursos de audio");
        stopBackgroundMusic();
        
        if (backgroundMusic != null) {
            backgroundMusic.close();
        }
        
        for (Map.Entry<String, Clip> entry : soundEffects.entrySet()) {
            Clip clip = entry.getValue();
            clip.stop();
            clip.close();
        }
        
        soundEffects.clear();
    }
}