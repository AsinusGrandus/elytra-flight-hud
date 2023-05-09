package eu.deltatimo.minecraft.elytrahud;

public class DrawLineArguments {
    public float x;
    public float y;
    public float x2;
    public float y2;
    public float r = 1.0f;
    public float g = 1.0f;
    public float b = 1.0f;
    public float a = 1.0f;
    public float r2 = 1.0f;
    public float g2 = 1.0f;
    public float b2 = 1.0f;
    public float a2 = 1.0f;

    public DrawLineArguments(float x, float y, float x2, float y2) {
        position(x, y, x2, y2);
    }

    public DrawLineArguments(float x, float y, float x2, float y2, float r, float g, float b) {
        position(x, y, x2, y2).color(r, g, b);
    }

    public DrawLineArguments(float x, float y, float x2, float y2, float r, float g, float b, float a) {
        position(x, y, x2, y2).color(r, g, b, a);
    }

    public DrawLineArguments position(float x, float y, float x2, float y2) {
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        return this;
    }

    public DrawLineArguments color(float r, float g, float b) {
        return color_start(r, g, b).color_end(r, g, b);
    }

    public DrawLineArguments color(float r, float g, float b, float a) {
        return color_start(r, g, b, a).color_end(r, g, b, a);
    }

    public DrawLineArguments alpha(float a) {
        return alpha_start(a).alpha_end(a);
    }

    public DrawLineArguments color_start(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        return this;
    }

    public DrawLineArguments color_start(float r, float g, float b, float a) {
        return color_start(r, g, b).alpha_start(a);
    }

    public DrawLineArguments alpha_start(float a) {
        this.a = a;
        return this;
    }

    public DrawLineArguments color_end(float r, float g, float b) {
        this.r2 = r;
        this.g2 = g;
        this.b2 = b;
        return this;
    }

    public DrawLineArguments color_end(float r, float g, float b, float a) {
        return color_end(r, g, b).alpha_end(a);
    }

    public DrawLineArguments alpha_end(float a) {
        this.a2 = a;
        return this;
    }

    public static DrawLineArguments make(float x, float y, float x2, float y2) {
        return new DrawLineArguments(x, y, x2, y2);
    }
}
