package net.asinusgrandus.minecraft.elytrahud.avionics.instruments;

import net.asinusgrandus.minecraft.elytrahud.Drawer;

public class AltitudeIndicator extends FlightInstrument {
    public AltitudeIndicator(){
        super();
    }

    @Override
    public void renderLines() {}

    @Override
    public void renderText() {
        renderRadarHeight();
        renderYHeight();
    }

    private void renderRadarHeight() {
        drawer.drawText(
                this.data.radar_height + "R",
                (int)(this.data.screenCenterX + this.data.horizon_width * 0.75f),
                (int)(this.data.screenCenterY + this.data.screenHeight * (1f / 8f)),
                0x00FF00,
                false
        );
    }

    private void renderYHeight(){
        drawer.drawText(
                "" + ((int) Math.floor(this.data.player_pos.y)),
                (int)(this.data.screenCenterX + this.data.horizon_width * 0.75f),
                this.data.screenCenterY,
                0x00FF00,
                false
        );
    }
}
