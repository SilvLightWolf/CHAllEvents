package com.goop.chae.events;

import com.goop.chae.Thrower;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import org.bukkit.event.entity.EntityAirChangeEvent;

import java.util.HashMap;
import java.util.Map;

public class MCEntityAirChangeEvent {

    @api
    public static class CHEntityAirChangeAPI extends AbstractEvent {

        @Override
        public String getName() {
            return "entity_air_change";
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
            if(e instanceof CHEntityAirChangeInterface){
                CHEntityAirChangeInterface evt = (CHEntityAirChangeInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                array.put("amount", evt.getAmount());

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
                if(value instanceof CInt){
                    ((CHEntityAirChangeInterface)event).setAmount(Integer.parseInt(value.getValue()));
                    return true;
                }else{
                    Thrower.throwCRECastException1("int", 2, "modify_event");
                }
            }
            return false;
        }
    }

    public static class CHEntityAirChangeEvent implements CHEntityAirChangeInterface {

        EntityAirChangeEvent e;

        public CHEntityAirChangeEvent(EntityAirChangeEvent e){ this.e = e; }

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public CInt getAmount() {
            return new CInt(e.getAmount(), Target.UNKNOWN);
        }

        @Override
        public boolean isCancelled() {
            return e.isCancelled();
        }

        @Override
        public void setAmount(int amount) {
            e.setAmount(amount);
        }

        @Override
        public void setCancelled(boolean cancelled) {
            e.setCancelled(cancelled);
        }
    }

    public interface CHEntityAirChangeInterface extends BindableEvent {

        public CInt getAmount();
        public boolean isCancelled();
        public void setAmount(int amount);
        public void setCancelled(boolean cancelled);

    }
}
