package com.studybreakrunner.game;

public enum GameState {
    MENU {
        @Override
        public String getDisplayName() {
            return "Menú Principal";
        }
    },
    PLAYING {
        @Override
        public String getDisplayName() {
            return "En Juego";
        }
    },
    PAUSED {
        @Override
        public String getDisplayName() {
            return "Pausado";
        }
    },
    GAME_OVER {
        @Override
        public String getDisplayName() {
            return "Game Over";
        }
    },
    SHOP {
        @Override
        public String getDisplayName() {
            return "Tienda";
        }
    },
    SETTINGS {
        @Override
        public String getDisplayName() {
            return "Configuración";
        }
    },
    CREDITS {
        @Override
        public String getDisplayName() {
            return "Créditos";
        }
    };
    
    public abstract String getDisplayName();
    
    public boolean isGameActive() {
        return this == PLAYING || this == PAUSED || this == GAME_OVER;
    }
    
    public boolean canPause() {
        return this == PLAYING;
    }
    
    public boolean canShowHUD() {
        return this == PLAYING || this == PAUSED || this == GAME_OVER;
    }
}