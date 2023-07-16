package net.asinusgrandus.minecraft.elytrahud.flight_instruments;

import net.asinusgrandus.minecraft.elytrahud.Drawer;

public class RadarHeight extends FlightInstrument {
    public RadarHeight(boolean isEnabled, Drawer drawer, AirDataInertialReferenceUnit data){
        super(isEnabled, drawer, data);
    }

    @Override
    public void renderLines() {}

    @Override
    public void renderText() {
        drawer.drawText(
                this.data.radar_height + "R",
                (int)(this.data.screenCenterX + this.data.horizon_width * 0.75f),
                (int)(this.data.screenCenterY + this.data.screenHeight * (1f / 8f)),
                0x00FF00,
                false);
    }
}
