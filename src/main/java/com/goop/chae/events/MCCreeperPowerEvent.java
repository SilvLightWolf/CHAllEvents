package com.goop.chae.events;

import com.goop.chae.Thrower;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCEntity;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCLightningStrike;
import com.laytonsmith.abstraction.entities.MCLightningStrike;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import org.bukkit.event.entity.CreeperPowerEvent;

import java.util.HashMap;
import java.util.Map;

public class MCCreeperPowerEvent {

    @api
    public static class CHCreeperPowerAPI extends AbstractEvent {

        @Override
        public String getName() {
            return "creeper_power";
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
            if(e instanceof CHCreeperPowerInterface){
                CHCreeperPowerInterface evt = (CHCreeperPowerInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                array.put("cause", evt.getCause());
                array.put("id", new CString(evt.getEntity().getUniqueId().toString(), t));

                array.put("macrotype", new CString("entity", t));
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
            if(key.equalsIgnoreCase("cancelled")) {
                switch (value.getValue().toLowerCase()) {
                    case "true":
                        ((CHCreeperPowerInterface) event).setCancelled(true);
                        return true;
                    case "false":
                        ((CHCreeperPowerInterface) event).setCancelled(false);
                        return true;
                    default:
                        Thrower.throwCRECastException1("boolean", 2, "modify_event");
                }
            }
            return false;
        }
    }

    public static class CHCreeperPowerEvent implements CHCreeperPowerInterface {

        CreeperPowerEvent e;

        public CHCreeperPowerEvent(CreeperPowerEvent e){ this.e = e; }

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public CString getCause() {
            return new CString(e.getCause().name(), Target.UNKNOWN);
        }

        @Override
        public MCEntity getEntity() {
            return new BukkitMCEntity(e.getEntity());
        }

        @Override
        public boolean isCancelled() {
            return e.isCancelled();
        }

        @Override
        public void setCancelled(boolean cancelled) {
            e.setCancelled(cancelled);
        }
    }

    public interface CHCreeperPowerInterface extends BindableEvent {

        public CString getCause();
        public MCEntity getEntity();
        public boolean isCancelled();
        public void setCancelled(boolean cancelled);

    }
}
