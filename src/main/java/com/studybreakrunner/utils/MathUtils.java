package com.studybreakrunner.utils;

import java.util.Random;

public class MathUtils {
    private static final Random random = new Random();
    
    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
    
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
    
    public static float lerp(float a, float b, float t) {
        return a + (b - a) * clamp(t, 0, 1);
    }
    
    public static float randomFloat(float min, float max) {
        return min + random.nextFloat() * (max - min);
    }
    
    public static int randomInt(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }
    
    public static boolean randomBoolean() {
        return random.nextBoolean();
    }
}