package net.asinusgrandus.minecraft.elytrahud.avionics.instruments;

import net.asinusgrandus.minecraft.elytrahud.Drawer;
import net.asinusgrandus.minecraft.elytrahud.avionics.AirDataInertialReferenceUnit;

public abstract class FlightInstrument {
    protected boolean isEnabled;
    protected Drawer drawer;
    protected AirDataInertialReferenceUnit data;

    public FlightInstrument(){
        this.isEnabled = true;
        this.drawer = null;
        this.data = null;
    }
    public boolean isEnabled() { return this.isEnabled; }
    public void enable(){
        this.isEnabled = true;
    }

    public void disable(){
        this.isEnabled = false;
    }

    public void listen(AirDataInertialReferenceUnit data){
        this.data = data;
    }
    public void update(Drawer drawer) { this.drawer = drawer; };

    abstract public void renderLines();
    abstract public void renderText();
}
