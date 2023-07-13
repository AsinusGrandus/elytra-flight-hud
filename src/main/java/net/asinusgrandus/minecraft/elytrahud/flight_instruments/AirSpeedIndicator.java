package net.asinusgrandus.minecraft.elytrahud.flight_instruments;

import net.asinusgrandus.minecraft.elytrahud.Drawer;

public class AirSpeedIndicator extends FlightInstrument {
    public AirSpeedIndicator(boolean isEnabled, Drawer drawer, FlightInstrumentData data){
        super(isEnabled, drawer, data);
    }

    @Override
    public void renderLines() {}

    @Override
    public void renderText() {
        int air_speed_color = 0x00FF00;
        double collisionDamage = this.data.collisionDamageHorizontal;

        if (collisionDamage > 5) {
            air_speed_color = 0x88FF00;
        } else if (collisionDamage > 0) {
            air_speed_color = 0x44FF00;
        }

        drawer.drawText("" + this.data.air_speed, (int)(this.data.screenCenterX - this.data.horizon_width * 0.75f - drawer.getTextWidth("" + this.data.air_speed)), this.data.screenCenterY, air_speed_color, false);

    }
}
