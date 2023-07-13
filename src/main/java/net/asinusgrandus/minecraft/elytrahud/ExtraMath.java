package net.asinusgrandus.minecraft.elytrahud;

public class ExtraMath {
    /**
     * Calculates mod from signed floats
     * */
    public static float realMod(float a, float b) {
        float result = a % b;
        return result < 0 ? result + b : result;
    }
}
