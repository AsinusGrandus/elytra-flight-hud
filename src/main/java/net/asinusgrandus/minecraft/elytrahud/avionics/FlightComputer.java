package net.asinusgrandus.minecraft.elytrahud.avionics;

import net.asinusgrandus.minecraft.elytrahud.Drawer;
import net.asinusgrandus.minecraft.elytrahud.avionics.instruments.*;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.function.Consumer;

public class FlightComputer {
    private MinecraftClient client;
    private Drawer drawer;

    private final AirDataInertialReferenceUnit ADIRU;
    private final ArrayList<FlightInstrument> instruments;

    private final AirSpeedIndicator airSpeedIndicator;
    private final AltitudeIndicator altitudeIndicator;
    private final AttitudeIndicator attitudeIndicator;
    private final Compass compass;
    private final Variometer variometer;
    private final VelocityVector velocityVector;

    public FlightComputer(){
        this.client = null;
        this.drawer = null;

        this.ADIRU = new AirDataInertialReferenceUnit();

        this.instruments = new ArrayList<>();

        this.airSpeedIndicator = new AirSpeedIndicator();
        this.altitudeIndicator = new AltitudeIndicator();
        this.attitudeIndicator = new AttitudeIndicator();
        this.compass = new Compass();
        this.variometer = new Variometer();
        this.velocityVector = new VelocityVector();
    }

    public void mountInstruments(){
        this.connect(this.airSpeedIndicator);
        this.connect(this.altitudeIndicator);
        this.connect(this.attitudeIndicator);
        this.connect(this.compass);
        this.connect(this.variometer);
        this.connect(this.velocityVector);
    }

    public void update(MinecraftClient client, Drawer drawer){
        this.client = client;
        this.drawer = drawer;

        this.ADIRU.update(client);

        for (FlightInstrument instrument: this.instruments){
            instrument.update(drawer);
        }
    }

    public void connect(FlightInstrument instrument){
        this.instruments.add(instrument);
        instrument.listen(this.ADIRU);
    }

    public void forEachInstrument(Consumer<FlightInstrument> action){
        this.instruments.forEach(action);
    }
}
