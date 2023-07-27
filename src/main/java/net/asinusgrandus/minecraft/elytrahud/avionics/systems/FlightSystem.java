package net.asinusgrandus.minecraft.elytrahud.avionics.systems;

import net.asinusgrandus.minecraft.elytrahud.Drawer;
import net.asinusgrandus.minecraft.elytrahud.avionics.AirDataInertialReferenceUnit;

public abstract class FlightSystem {
    protected boolean isEnabled;
    protected Drawer drawer;
    protected AirDataInertialReferenceUnit data;

    protected int FLAG;

    public FlightSystem(){
        this.isEnabled = true;
        this.drawer = null;
        this.data = null;
        this.FLAG = 0;
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

    public void setFlag(int flagNumber){
        this.FLAG = flagNumber;
    };

    public int getFLAG(){
        return this.FLAG;
    }

    abstract public void check();
}
