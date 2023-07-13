package net.asinusgrandus.minecraft.elytrahud.flight_instruments;

import net.asinusgrandus.minecraft.elytrahud.Drawer;

public class Variometer extends FlightInstrument {
    public Variometer(boolean isEnabled, Drawer drawer, FlightInstrumentData data){
        super(isEnabled, drawer, data);
    }

    @Override
    public void renderLines() {}

    @Override
    public void renderText() {
        float fallDistance = this.data.playerFallDisctance;
        float safeFallDisctance = this.data.safePlayerFallDistance;
        int fall_distance_color = 0x00FF00;
        if (fallDistance > safeFallDisctance * 2f) {
            fall_distance_color = 0xFF0000;
        } else if (fallDistance > safeFallDisctance) {
            fall_distance_color = 0xFF8800;
        } else if (fallDistance > safeFallDisctance * 0.75f) {
            fall_distance_color = 0xFFFF00;
        }

        // Text that changes colours (green-black)
        drawer.drawText(
                "" + (Math.round((float) this.data.player_velocity_vector.y * 10f)),
                (int)(this.data.screenCenterX + this.data.horizon_width * 0.8f),
                (int) (this.data.screenCenterY + drawer.getFontHeight() * 1.5f),
                fall_distance_color,
                false
        );
    }
}
