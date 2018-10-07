package com.goop.chae.events;

import com.goop.chae.Thrower;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCInventory;
import com.laytonsmith.abstraction.bukkit.BukkitMCInventory;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import org.bukkit.event.inventory.BrewEvent;

import java.util.HashMap;
import java.util.Map;

public class MCBrewEvent {

    @api
    public static class CHBrewAPI extends AbstractEvent {

        @Override
        public String getName() {
            return "brew";
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
            if(e instanceof CHBrewInterface){
                CHBrewInterface evt = (CHBrewInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                CArray inv = new CArray(t);
                for(int i = 0 ; i < evt.getContents().getSize() ; i ++)
                    inv.set(i, ObjectGenerator.GetGenerator().item(evt.getContents().getItem(i), t), t);
                array.put("inventory", inv);

                array.put("fuellevel", evt.getFuelLevel());

                array.put("macrotype", new CString("block", t));
                array.put("event_type", new CString(getName(), t));

            }
            return null;
        }

        @Override
        public Driver driver() {
            return Driver.EXTENSION;
        }

        @Override
        public boolean modifyEvent(String key, Construct value, BindableEvent event) {
            if(key.equalsIgnoreCase("cancelled")) {
                switch (value.getValue().toLowerCase()) {
                    case "true":
                        ((CHBrewInterface)event).setCancelled(true);
                        return true;
                    case "false":
                        ((CHBrewInterface)event).setCancelled(false);
                        return true;
                    default:
                        Thrower.throwCRECastException1("boolean", 2, "modify_event");
                }
            }
            return false;
        }
    }

    public static class CHBrewEvent implements CHBrewInterface {

        BrewEvent e;

        public CHBrewEvent(BrewEvent e){ this.e = e; }

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public MCInventory getContents() {
            return new BukkitMCInventory(e.getContents());
        }

        @Override
        public CInt getFuelLevel() {
            return new CInt(e.getFuelLevel(), Target.UNKNOWN);
        }

        @Override
        public boolean isCancelled() {
            return e.isCancelled();
        }

        @Override
        public void setCancelled(boolean cancel) {
            e.setCancelled(cancel);
        }
    }

    public interface CHBrewInterface extends BindableEvent{

        public MCInventory getContents();
        public CInt getFuelLevel();
        public boolean isCancelled();
        public void setCancelled(boolean cancel);

    }
}
