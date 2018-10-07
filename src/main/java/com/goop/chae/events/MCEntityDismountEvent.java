package com.goop.chae.events;

import com.goop.chae.Thrower;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.blocks.MCBlockState;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCEntity;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.HashMap;
import java.util.Map;

public class MCEntityDismountEvent {

    @api
    public static class CHEntityDismountAPI extends AbstractEvent {

        @Override
        public String getName() {
            return "entity_dismount";
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
            if(e instanceof CHEntityDismountInterface){
                CHEntityDismountInterface evt = (CHEntityDismountInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                array.put("dismounted", new CString(evt.getDismounted().getUniqueId().toString(), t));
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
                        ((CHEntityDismountInterface)event).setCancelled(true);
                        return true;
                    case "false":
                        ((CHEntityDismountInterface)event).setCancelled(false);
                        return true;
                    default:
                        Thrower.throwCRECastException1("boolean", 2, "modify_event");
                }
            }
            return false;
        }
    }

    public static class CHEntityDismountEvent implements CHEntityDismountInterface {

        EntityDismountEvent e;

        public CHEntityDismountEvent(EntityDismountEvent e){ this.e = e; }

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public MCEntity getDismounted() {
            return new BukkitMCEntity(e.getDismounted());
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

    public interface CHEntityDismountInterface extends BindableEvent {

        public MCEntity getDismounted();
        public MCEntity getEntity();
        public boolean isCancelled();
        public void setCancelled(boolean cancelled);

    }
}
