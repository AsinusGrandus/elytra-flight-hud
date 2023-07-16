package net.asinusgrandus.minecraft.elytrahud;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.asinusgrandus.minecraft.elytrahud.flight_instruments.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class HudRenderer {

    private static boolean isHudEnabled = true;
    private static float hud_alpha = 0.0f;
    public static boolean shouldRenderHud(){
        return isHudEnabled;
    }

    public static void flipIsHudEnabled(){
        isHudEnabled = !isHudEnabled;
    }

    public static void onHudRender(DrawContext context, float tickDelta) {

        MinecraftClient client = MinecraftClient.getInstance();

        ClientPlayerEntity player = client.player;
        if (player == null) return;

        /**
         * Figure out if the overlay needs to be rendered:
         * - the hud is enabled &&
         * - the player is falling
         * */
        float hud_alpha_target = isHudEnabled && player.isFallFlying() ? 1.0f : 0.0f;

        // Fade in and out effect when overlay starts/stops rendering
        hud_alpha = Math.max(0f, Math.min(1f, hud_alpha + Math.signum(hud_alpha_target - hud_alpha) * tickDelta * 0.1f));

        // If the hud_alpha is high enough, render the overlay
        if (hud_alpha >= 0.0001) {
            // (method_34540) 1.19.x: GameRenderer::getPositionColorShader => 1.20.x: GameRenderer::getPositionColorProgram
            RenderSystem.setShader(GameRenderer::getPositionColorProgram);
            RenderSystem.enableBlend();

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            bufferBuilder.begin(VertexFormat.DrawMode.LINES, VertexFormats.LINES);

            Matrix4f matrix4f = context.getMatrices().peek().getPositionMatrix();
            Matrix3f matrix3f = context.getMatrices().peek().getNormalMatrix();

            Drawer drawer = new Drawer(bufferBuilder, client.textRenderer, context, matrix4f, matrix3f, hud_alpha);
            AirDataInertialReferenceUnit data = new AirDataInertialReferenceUnit(client);

            FlightInstrument[] flightInstruments = {
                    new AirSpeedIndicator(true, drawer, data),
                    new AttitudeIndicator(true, drawer, data),
                    new Compass(true, drawer, data),
                    new RadarHeight(true, drawer, data),
                    new TotalVelocityVector(true, drawer, data),
                    new Variometer(true, drawer, data),
                    new YLevelHeight(true, drawer, data)
            };

            GlStateManager._depthMask(false);
            GlStateManager._disableCull();

            // (method_34535) 1.19.x: GameRenderer::getRenderTypeLinesShader => 1.20.x GameRenderer::getRenderTypeLinesProgram
            RenderSystem.setShader(GameRenderer::getRenderTypeLinesProgram);

            RenderSystem.lineWidth(2.0F);

            for (FlightInstrument f: flightInstruments){
                if (f.isEnabled()) {
                    f.renderLines();
                }
            }

            tessellator.draw();
            RenderSystem.lineWidth(1.0F);

            if (drawer.hud_alpha > 0.66){
                for (FlightInstrument f: flightInstruments){
                    if (f.isEnabled()) {
                        f.renderText();
                    }
                }
            }

            GlStateManager._enableCull();
            GlStateManager._depthMask(true);

        }
    }
}
