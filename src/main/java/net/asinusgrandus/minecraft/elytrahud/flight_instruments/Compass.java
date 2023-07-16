package net.asinusgrandus.minecraft.elytrahud.flight_instruments;

import net.asinusgrandus.minecraft.elytrahud.DrawLineArguments;
import net.asinusgrandus.minecraft.elytrahud.Drawer;
import net.asinusgrandus.minecraft.elytrahud.ExtraMath;

public class Compass extends FlightInstrument {
    public Compass(boolean isEnabled, Drawer drawer, AirDataInertialReferenceUnit data){
        super(isEnabled, drawer, data);
    }

    @Override
    public void renderLines() {
        /*
         * LittleTriangle needs a value calculated in CompassLines, so...
         * */
        float compass_blip_height = drawCompasLines();
        drawLittleTriangle(compass_blip_height);
    }

    @Override
    public void renderText() {
        drawCompassText();
    }

    private float drawCompasLines(){
        // Compass
        float heading = this.data.heading;
        float compass_width = this.data.compass_width;
        int heading_tens = Math.round(heading / 10f);
        float heading_fives = this.data.heading_fives;
        float compass_blip_height = this.data.screenHeight / 200f;

        for (float heading_blip = heading_fives - 3f * 0.5f; heading_blip <= heading / 10f + 3 * 0.5f; heading_blip += 0.5f) {
            // Skip first blip if it's outside.
            if (heading_blip < heading / 10f - 3 * 0.5f) continue;
            float heading_offset = heading - (heading_blip * 10f);
            float heading_x = this.data.screenCenterX - (heading_offset / 15f) * compass_width / 2f;
            float font_height = this.data.fontHeight;

            drawer.drawLine(DrawLineArguments.make(heading_x, this.data.screenCenterY + this.data.screenHeight / 4f + font_height * 1.05f, heading_x, this.data.screenCenterY + this.data.screenHeight / 4f + font_height * 1.05f + compass_blip_height).color(0f, 1f, 0f, this.drawer.hud_alpha));
        }

        return compass_blip_height;
    }

    private void drawLittleTriangle(float compass_blip_height){
        float compass_triangle_y = this.data.screenCenterY + this.data.screenHeight / 4f + this.data.fontHeight * 1.05f + compass_blip_height * 0.95f;
        float compass_triangle_height = compass_blip_height;
        float compass_triangle_width = compass_triangle_height * 2f;

        drawer.drawLine(DrawLineArguments.make((float) this.data.screenCenterX, compass_triangle_y, this.data.screenCenterX + compass_triangle_width / 2f, compass_triangle_y + compass_triangle_height).color(0f, 1f, 0f, this.drawer.hud_alpha));
        drawer.drawLine(DrawLineArguments.make((float) this.data.screenCenterX + compass_triangle_width / 2f, compass_triangle_y + compass_triangle_height, this.data.screenCenterX - compass_triangle_width / 2f, compass_triangle_y + compass_triangle_height).color(0f, 1f, 0f, this.drawer.hud_alpha));
        drawer.drawLine(DrawLineArguments.make((float) this.data.screenCenterX - compass_triangle_width / 2f, compass_triangle_y + compass_triangle_height, this.data.screenCenterX, compass_triangle_y).color(0f, 1f, 0f, this.drawer.hud_alpha));
    }

    private void drawCompassText(){
        for (float heading_blip = this.data.heading_fives - 3f * 0.5f; heading_blip <= this.data.heading / 10f + 3 * 0.5f; heading_blip += 0.5f) {
            // Skip first blip if it's outside.
            if (heading_blip < this.data.heading / 10f - 3 * 0.5f) continue;
            if (Math.floor(heading_blip) == heading_blip) {
                float heading_blip_360 = heading_blip < 0 ? (36 - ExtraMath.realMod(heading_blip, 36)) : (heading_blip % 36);
                if (heading_blip_360 == 36) heading_blip_360 = 0;
                String heading_text = ((Math.floor(heading_blip_360) < 10) ? "0" : "") + (int) Math.floor(heading_blip_360);
                float heading_offset = this.data.heading - (heading_blip * 10f);
                float heading_x = this.data.screenCenterX - (heading_offset / 15f) * this.data.compass_width / 2f;
                drawer.drawText(heading_text, (int)(heading_x - this.drawer.getTextWidth(heading_text) / 2f), (int) (this.data.screenCenterY + this.data.screenHeight / 4f), 0x00FF00, false);
            }
        }
    }
}
