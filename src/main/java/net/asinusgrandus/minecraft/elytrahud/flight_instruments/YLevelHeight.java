package net.asinusgrandus.minecraft.elytrahud.flight_instruments;

import net.asinusgrandus.minecraft.elytrahud.Drawer;

public class YLevelHeight extends FlightInstrument {
    public YLevelHeight(boolean isEnabled, Drawer drawer, AirDataInertialReferenceUnit data){
        super(isEnabled, drawer, data);
    }

    @Override
    public void renderLines() {}

    @Override
    public void renderText() {
        drawer.drawText(
                "" + ((int) Math.floor(this.data.player_pos.y)),
                (int)(this.data.screenCenterX + this.data.horizon_width * 0.8f),
                this.data.screenCenterY,
                0x00FF00,
                false
        );
    }
}
