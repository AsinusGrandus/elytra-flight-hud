package net.asinusgrandus.minecraft.elytrahud.flight_instruments;

import net.asinusgrandus.minecraft.elytrahud.DrawLineArguments;
import net.asinusgrandus.minecraft.elytrahud.Drawer;

public class TotalVelocityVector extends FlightInstrument {
    public TotalVelocityVector(boolean isEnabled, Drawer drawer, FlightInstrumentData data){
        super(isEnabled, drawer, data);
    }

    @Override
    public void renderLines() {
        drawSmallCircle();
        drawThreeSmallLines();
        drawLongLine();
    }

    @Override
    public void renderText() {}

    private void drawSmallCircle(){
        // Circle:
        for (DrawLineArguments line : Drawer.circleLines(this.data.flight_vector_x, this.data.flight_vector_y, this.data.flight_vector_radius, 10)) {
            drawer.drawLine(line.color(0f, 1f, 0f, this.drawer.hud_alpha));
        }
    }

    private void drawThreeSmallLines(){
        // Three small lines:
        drawer.drawLine(DrawLineArguments.make(
                this.data.flight_vector_x + (float) Math.sin(this.data.elytra_roll) * this.data.flight_vector_radius,
                this.data.flight_vector_y - (float) Math.cos(this.data.elytra_roll) * this.data.flight_vector_radius,
                this.data.flight_vector_x + (float) Math.sin(this.data.elytra_roll) * (this.data.flight_vector_radius + this.data.flight_vector_size * 0.4f),
                this.data.flight_vector_y - (float) Math.cos(this.data.elytra_roll) * (this.data.flight_vector_radius + this.data.flight_vector_size * 0.4f)
        ).color(0f, 1f, 0f, this.drawer.hud_alpha));

        drawer.drawLine(DrawLineArguments.make(
                this.data.flight_vector_x + (float) Math.sin(this.data.elytra_roll + 0.5 * Math.PI) * this.data.flight_vector_radius,
                this.data.flight_vector_y - (float) Math.cos(this.data.elytra_roll + 0.5 * Math.PI) * this.data.flight_vector_radius,
                this.data.flight_vector_x + (float) Math.sin(this.data.elytra_roll + 0.5 * Math.PI) * (this.data.flight_vector_radius + this.data.flight_vector_size * 0.5f),
                this.data.flight_vector_y - (float) Math.cos(this.data.elytra_roll + 0.5 * Math.PI) * (this.data.flight_vector_radius + this.data.flight_vector_size * 0.5f)
        ).color(0f, 1f, 0f, this.drawer.hud_alpha));

        drawer.drawLine(DrawLineArguments.make(
                this.data.flight_vector_x + (float) Math.sin(this.data.elytra_roll - 0.5 * Math.PI) * this.data.flight_vector_radius,
                this.data.flight_vector_y - (float) Math.cos(this.data.elytra_roll - 0.5 * Math.PI) * this.data.flight_vector_radius,
                this.data.flight_vector_x + (float) Math.sin(this.data.elytra_roll - 0.5 * Math.PI) * (this.data.flight_vector_radius + this.data.flight_vector_size * 0.5f),
                this.data.flight_vector_y - (float) Math.cos(this.data.elytra_roll - 0.5 * Math.PI) * (this.data.flight_vector_radius + this.data.flight_vector_size * 0.5f)
        ).color(0f, 1f, 0f, this.drawer.hud_alpha));
    }

    private void drawLongLine(){
        // Long line:
        double flight_vector_angle = Math.atan2(this.data.screenCenterY - this.data.flight_vector_y, this.data.screenCenterX - this.data.flight_vector_x);
        drawer.drawLine(DrawLineArguments.make(
                this.data.screenCenterX + 0.66f * (this.data.flight_vector_x - this.data.screenCenterX),
                this.data.screenCenterY + 0.66f * (this.data.flight_vector_y - this.data.screenCenterY),
                this.data.flight_vector_x + (this.data.flight_vector_size / 2f) * (float) Math.cos(flight_vector_angle),
                this.data.flight_vector_y + (this.data.flight_vector_size / 2f) * (float) Math.sin(flight_vector_angle)).color(0f, 1f, 0f, this.drawer.hud_alpha));
    }
}
