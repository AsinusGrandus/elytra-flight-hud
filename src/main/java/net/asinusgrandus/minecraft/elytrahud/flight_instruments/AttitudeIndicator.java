package net.asinusgrandus.minecraft.elytrahud.flight_instruments;

import net.asinusgrandus.minecraft.elytrahud.DrawLineArguments;
import net.asinusgrandus.minecraft.elytrahud.Drawer;
import net.asinusgrandus.minecraft.elytrahud.ElytraFlightHudModClient;

public class AttitudeIndicator extends FlightInstrument {

    public AttitudeIndicator(boolean isEnabled, Drawer drawer, FlightInstrumentData data){
        super(isEnabled, drawer, data);
    }

    @Override
    public void renderLines() {
        drawFullLines();
        drawMiddleLine();
        drawStippledLines();
    }

    @Override
    public void renderText() {
        drawAttitudeIndicatorText();
    }

    private void drawAttitudeIndicatorText(){

        for (int i = -90; i < 90; i += 10) {
            if (i == 0) continue;
            // Out of upper screen bound. next!
            // if (i < screen_upper_pitch_deg) continue;
            double diff_deg = i - this.data.camera_pitch_deg; // -90 - -30 = -90 + 30 = -60
            float diff_pixels = (float) (this.data.pixels_per_deg * diff_deg);
            // float height = screenCenterY + diff_pixels - screenHeight / 160f;
            float height = this.data.screenCenterY + diff_pixels - (i > 0 ? (0.9f * this.data.fontHeight) : (this.data.fontHeight * (0.15f)));
            if (height > this.data.screenHeight * 0.85f || height < this.data.screenHeight * 0.15f) continue;
            String text = "" + Math.abs(i);
            drawer.drawText(text, (int) (this.data.screenCenterX + this.data.horizon_width / 2f), (int) height, 0x00FF00, false);
            drawer.drawText(text, (int) (this.data.screenCenterX - this.data.horizon_width / 2f - this.drawer.getTextWidth(text) - this.data.screenWidth / 500f), (int) height, 0x00FF00, false);
        }
    }

    private void drawMiddleLine(){
        if (!(this.data.center_height > this.data.screenHeight * 0.85f || this.data.center_height < this.data.screenHeight * 0.15f)) {
            drawer.drawLine(DrawLineArguments.make(this.data.screenCenterX - this.data.horizon_width / 2f, this.data.center_height, this.data.screenCenterX - this.data.horizon_width / 2f + this.data.horizon_width / 3, this.data.center_height).color(0f, 1f, 0f, this.drawer.hud_alpha));
            drawer.drawLine(DrawLineArguments.make(this.data.screenCenterX - this.data.horizon_width / 2f + 2 * this.data.horizon_width / 3f, this.data.center_height, this.data.screenCenterX + this.data.horizon_width / 2f, this.data.center_height).color(0f, 1f, 0f, this.drawer.hud_alpha));
        }
    }

    private void drawFullLines(){
        ElytraFlightHudModClient.LOGGER.info("drawFullLines");
        // Draw the full lines upwards (90 to 0 degrees)
        for (int i = -90; i < 0; i += 10) {
            // Out of upper screen bound. next!
            // if (i < screen_upper_pitch_deg) continue;
            double diff_deg = i - this.data.camera_pitch_deg; // -90 - -30 = -90 + 30 = -60
            float diff_pixels = (float) (this.data.pixels_per_deg * diff_deg);
            float height = this.data.screenCenterY + diff_pixels;
            if (height > this.data.screenHeight * 0.85f || height < this.data.screenHeight * 0.15f) continue;

            drawer.drawLine(DrawLineArguments.make(this.data.screenCenterX - this.data.horizon_width / 2f, height, this.data.screenCenterX - this.data.horizon_width / 2f + this.data.horizon_width / 3, height).color(0f, 1f, 0f, this.drawer.hud_alpha));
            drawer.drawLine(DrawLineArguments.make(this.data.screenCenterX - this.data.horizon_width / 2f, height, this.data.screenCenterX - this.data.horizon_width / 2f, height + this.data.horizon_vertical_blip_length).color(0f, 1f, 0f, this.drawer.hud_alpha));
            drawer.drawLine(DrawLineArguments.make(this.data.screenCenterX - this.data.horizon_width / 2f + 2 * this.data.horizon_width / 3f, height, this.data.screenCenterX - this.data.horizon_width / 2f + this.data.horizon_width, height).color(0f, 1f, 0f, this.drawer.hud_alpha));
            drawer.drawLine(DrawLineArguments.make(this.data.screenCenterX - this.data.horizon_width / 2f + this.data.horizon_width, height, this.data.screenCenterX - this.data.horizon_width / 2f + this.data.horizon_width, height + this.data.horizon_vertical_blip_length).color(0f, 1f, 0f, this.drawer.hud_alpha));
        }
    }

    private void drawStippledLines(){
        // Draw the stippled lines downwards (0 to -90 degrees)
        for (int i = 90; i > 0; i -= 10) {
            // Out of upper screen bound. next!
            // if (i < screen_upper_pitch_deg) continue;
            double diff_deg = i - this.data.camera_pitch_deg; // -90 - -30 = -90 + 30 = -60
            float diff_pixels = (float) (this.data.pixels_per_deg * diff_deg);
            float height = this.data.screenCenterY + diff_pixels;
            if (height > this.data.screenHeight * 0.85f || height < this.data.screenHeight * 0.15f) continue;

            // left horizontal lines
            float leftx = this.data.screenCenterX - this.data.horizon_width / 2f;
            float part_width = this.data.horizon_width / 3f / 3f;
            drawer.drawLine(DrawLineArguments.make(leftx, height, leftx + part_width - part_width / 3, height).color(0f, 1f, 0f, this.drawer.hud_alpha));
            drawer.drawLine(DrawLineArguments.make(leftx + part_width, height, leftx + 2 * part_width - part_width / 3, height).color(0f, 1f, 0f, this.drawer.hud_alpha));
            drawer.drawLine(DrawLineArguments.make(leftx + 2 * part_width, height, leftx + 3 * part_width, height).color(0f, 1f, 0f, this.drawer.hud_alpha));
            // right horizontal lines
            float rightx = this.data.screenCenterX + this.data.horizon_width / 2f;
            drawer.drawLine(DrawLineArguments.make(rightx, height, rightx - part_width + part_width / 3, height).color(0f, 1f, 0f, this.drawer.hud_alpha));
            drawer.drawLine(DrawLineArguments.make(rightx - part_width, height, rightx - 2 * part_width + part_width / 3, height).color(0f, 1f, 0f, this.drawer.hud_alpha));
            drawer.drawLine(DrawLineArguments.make(rightx - 2 * part_width, height, rightx - 3 * part_width, height).color(0f, 1f, 0f, this.drawer.hud_alpha));
            // vertical ends
            drawer.drawLine(DrawLineArguments.make(this.data.screenCenterX - this.data.horizon_width / 2f, height, this.data.screenCenterX - this.data.horizon_width / 2f, height - this.data.horizon_vertical_blip_length).color(0f, 1f, 0f, 1f * this.drawer.hud_alpha));
            drawer.drawLine(DrawLineArguments.make(this.data.screenCenterX - this.data.horizon_width / 2f + this.data.horizon_width, height, this.data.screenCenterX - this.data.horizon_width / 2f + this.data.horizon_width, height - this.data.horizon_vertical_blip_length).color(0f, 1f, 0f, this.drawer.hud_alpha));
        }
    }
}
