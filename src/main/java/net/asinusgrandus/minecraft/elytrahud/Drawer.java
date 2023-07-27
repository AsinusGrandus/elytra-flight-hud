package net.asinusgrandus.minecraft.elytrahud;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Collection;

public class Drawer {

    private final BufferBuilder bufferBuilder;
    private final TextRenderer textRenderer;
    private final DrawContext context;
    private final Matrix4f matrix4f;
    private final Matrix3f matrix3f;
    public final float hud_alpha;

    public Drawer(BufferBuilder bufferBuilder, TextRenderer textRenderer, DrawContext context, Matrix4f matrix4f, Matrix3f matrix3f, float hud_alpha){
        this.bufferBuilder = bufferBuilder;
        this.textRenderer = textRenderer;
        this.context = context;
        this.matrix4f = matrix4f;
        this.matrix3f = matrix3f;
        this.hud_alpha = hud_alpha;
    }

    public void drawText(String text, int x, int y, int color, boolean shadow){
        context.drawText(this.textRenderer, text, x, y, color, shadow);
    }

    public int getTextWidth(String text){
        return this.textRenderer.getWidth(text);
    }
    public int getFontHeight() { return this.textRenderer.fontHeight; }

    public void drawLine(DrawLineArguments a){
        float normal_x = a.x2 - a.x;
        float normal_y = a.y2 - a.y;
        float length = (float) Math.sqrt(normal_x * normal_x + normal_y * normal_y);
        if (Math.abs(length) < 0.0001) return;
        normal_x /= length;
        normal_y /= length;
        bufferBuilder.vertex(matrix4f, a.x, a.y, -90.0f).color(a.r, a.g, a.b, a.a).normal(matrix3f, normal_x, normal_y, 0f).next();
        bufferBuilder.vertex(matrix4f, a.x2, a.y2, -90.0f).color(a.r2, a.g2, a.b2, a.a2).normal(matrix3f, normal_x, normal_y, 0f).next();
    }

    /**
     * Calculates the lines for a circle with center x, y and a radius
     * by dividing the circle into small straight lines
     * */
    public static Collection<DrawLineArguments> circleLines(float x, float y, float radius, int parts) {
        ArrayList<DrawLineArguments> lines = new ArrayList<>(parts);

        for (double phi = 0D; phi < Constants.TWO_PI; phi += Constants.TWO_PI/parts) {
            double x1 = x + Math.cos(phi) * radius;
            double x2 = x + Math.cos(phi + Constants.TWO_PI/parts) * radius;
            double y1 = y + Math.sin(phi) * radius;
            double y2 = y + Math.sin(phi + Constants.TWO_PI/parts) * radius;

            lines.add(DrawLineArguments.make((float)x1, (float)y1, (float)x2, (float)y2));
        }

        return lines;
    }
}
