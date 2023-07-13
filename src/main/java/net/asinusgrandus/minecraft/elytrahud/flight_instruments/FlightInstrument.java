package net.asinusgrandus.minecraft.elytrahud.flight_instruments;

import net.asinusgrandus.minecraft.elytrahud.Drawer;

public abstract class FlightInstrument {
    protected boolean isEnabled;
    protected Drawer drawer;
    protected FlightInstrumentData data;
    public FlightInstrument(boolean isEnabled, Drawer drawer, FlightInstrumentData data){
        this.isEnabled = isEnabled;
        this.drawer = drawer;
        this.data = data;
    }
    public boolean isEnabled() { return this.isEnabled; }
    public void enable(){
        this.isEnabled = true;
    }

    public void disable(){
        this.isEnabled = false;
    }

    abstract public void renderLines();
    abstract public void renderText();
}
