package com.goop.chae.events;

import com.goop.chae.Thrower;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCEntity;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import org.bukkit.event.entity.EntityCombustByEntityEvent;

import java.util.HashMap;
import java.util.Map;

public class MCEntityCombustByEntityEvent {

    @api
    public static class CHEntityCombustByEntityAPI extends AbstractEvent {

        @Override
        public String getName() {
            return "entity_combust_by_entity";
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
            if(e instanceof CHEntityCombustByEntityInterface){
                CHEntityCombustByEntityInterface evt = (CHEntityCombustByEntityInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                array.put("duration", evt.getDuration());
                array.put("combuster", new CString(evt.getEntity().getUniqueId().toString(), t));

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
            if(key.equalsIgnoreCase("amount")){
                if(value instanceof CInt) {
                    ((CHEntityCombustByEntityInterface) event).setDuration(Integer.parseInt(value.getValue()));
                    return true;
                }else{
                    Thrower.throwCRECastException1("int", 2, "modify_event");
                }
            }else if(key.equalsIgnoreCase("cancelled")) {
                switch (value.getValue().toLowerCase()) {
                    case "true":
                        ((CHEntityCombustByEntityInterface)event).setCancelled(true);
                        return true;
                    case "false":
                        ((CHEntityCombustByEntityInterface)event).setCancelled(false);
                        return true;
                    default:
                        Thrower.throwCRECastException1("boolean", 2, "modify_event");
                }
            }
            return false;
        }
    }

    public static class CHEntityCombustByEntityEvent implements CHEntityCombustByEntityInterface {

        EntityCombustByEntityEvent e;

        public CHEntityCombustByEntityEvent(EntityCombustByEntityEvent e){
            this.e = e;
        }

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public CInt getDuration() {
            return new CInt(e.getDuration(), Target.UNKNOWN);
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public MCEntity getEntity() {
            return new BukkitMCEntity(e.getEntity());
        }

        @Override
        public void setCancelled(boolean cancel) {
            e.setCancelled(cancel);
        }

        @Override
        public void setDuration(int duration) {
            e.setDuration(duration);
        }
    }

    public interface CHEntityCombustByEntityInterface extends BindableEvent {

        public CInt getDuration();
        public boolean isCancelled();
        public MCEntity getEntity();
        public void setCancelled(boolean cancel);
        public void setDuration(int duration);
    }
}
