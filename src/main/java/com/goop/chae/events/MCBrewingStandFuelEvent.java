package com.goop.chae.events;

import com.goop.chae.Thrower;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCItemStack;
import com.laytonsmith.abstraction.bukkit.BukkitMCItemStack;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import org.bukkit.event.inventory.BrewingStandFuelEvent;

import java.util.HashMap;
import java.util.Map;

public class MCBrewingStandFuelEvent {

    @api
    public static class CHBrewingStandFuelAPI extends AbstractEvent {

        @Override
        public String getName() {
            return "brewing_stand_fuel";
        }

        @Override
        public String docs() {
            return "";
        }

        @Override
        public Version since() {
            return new SimpleVersion(1, 0, 0);
        }

        @Override
        public boolean matches(Map<String, Construct> prefilter, BindableEvent e) throws PrefilterNonMatchException {
            return true;
        }

        @Override
        public BindableEvent convert(CArray manualObject, Target t) {
            return null;
        }

        @Override
        public Map<String, Construct> evaluate(BindableEvent e) throws EventException {
            if(e instanceof CHBrewingStandFuelInterface){
                CHBrewingStandFuelInterface evt = (CHBrewingStandFuelInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                array.put("fuel", ObjectGenerator.GetGenerator().item(evt.getFuel(), t));
                array.put("fuelpower", evt.getFuelPower());
                array.put("consuming", CBoolean.get(evt.isConsuming()));

                array.put("macrotype", new CString("block", t));
                array.put("event_type", new CString(getName(), t));

                return array;
            }
            return null;
        }

        @Override
        public Driver driver() {
            return Driver.EXTENSION;
        }

        @Override
        public boolean modifyEvent(String key, Construct value, BindableEvent event) {
            if(key.equalsIgnoreCase("consuming")){
                switch(value.getValue().toLowerCase()){
                    case "true":
                        ((CHBrewingStandFuelInterface)event).setConsuming(true);
                        return true;
                    case "false":
                        ((CHBrewingStandFuelInterface)event).setConsuming(false);
                        return true;
                    default:
                        Thrower.throwCRECastException1("boolean", 2, "modify_event");
                }
            }else if(key.equalsIgnoreCase("fuelpower")){
                if(value instanceof CInt){
                    ((CHBrewingStandFuelInterface)event).setFuelPower(Integer.parseInt(value.getValue()));
                    return true;
                }else{
                    Thrower.throwCRECastException1("int", 2, "modify_event");
                }
            }else if(key.equalsIgnoreCase("cancelled")) {
                switch (value.getValue().toLowerCase()) {
                    case "true":
                        ((CHBrewingStandFuelInterface)event).setCancelled(true);
                        return true;
                    case "false":
                        ((CHBrewingStandFuelInterface)event).setCancelled(false);
                        return true;
                    default:
                        Thrower.throwCRECastException1("boolean", 2, "modify_event");
                }
            }
            return false;
        }
    }

    public static class CHBrewingStandFuelEvent implements CHBrewingStandFuelInterface {

        BrewingStandFuelEvent e;

        public CHBrewingStandFuelEvent(BrewingStandFuelEvent e){ this.e = e; }

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public MCItemStack getFuel() {
            return new BukkitMCItemStack(e.getFuel());
        }

        @Override
        public CInt getFuelPower() {
            return new CInt(e.getFuelPower(), Target.UNKNOWN);
        }

        @Override
        public boolean isConsuming() {
            return e.isConsuming();
        }

        @Override
        public boolean isCancelled() {
            return e.isCancelled();
        }

        @Override
        public void setFuelPower(int fuelPower) {
            e.setFuelPower(fuelPower);
        }

        @Override
        public void setConsuming(boolean consuming) {
            e.setConsuming(consuming);
        }

        @Override
        public void setCancelled(boolean cancel) {
            e.setCancelled(cancel);
        }
    }

    public interface CHBrewingStandFuelInterface extends BindableEvent {

        public MCItemStack getFuel();
        public CInt getFuelPower();
        public boolean isConsuming();
        public boolean isCancelled();
        public void setFuelPower(int fuelPower);
        public void setConsuming(boolean consuming);
        public void setCancelled(boolean cancel);

    }
}
