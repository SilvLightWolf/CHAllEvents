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
import org.bukkit.event.block.CauldronLevelChangeEvent;

import java.util.HashMap;
import java.util.Map;

public class MCCauldronLevelChangeEvent {

    @api
    public static class CHCauldronLevelChangeAPI extends AbstractEvent {

        @Override
        public String getName() {
            return "cauldron_level_change";
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
            if(e instanceof CHCauldronLevelChangeInterface){
                CHCauldronLevelChangeInterface evt = (CHCauldronLevelChangeInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                array.put("entity", new CString(evt.getEntity().getUniqueId().toString(), t));
                array.put("newlevel", evt.getNewLevel());
                array.put("oldlevel", evt.getOldLevel());
                array.put("reason", evt.getReason());

                array.put("event_type", new CString(getName(), t));
                array.put("macrotype", new CString("block", t));

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
            if(key.equalsIgnoreCase("newlevel")){
                if(value instanceof CInt){
                    ((CHCauldronLevelChangeInterface)event).setNewLevel(Integer.parseInt(value.getValue()));
                    return true;
                }else{
                    Thrower.throwCRECastException1("int", 2, "modify_event");
                }
            }
            return false;
        }
    }

    public static class CHCauldronLevelChangeEvent implements CHCauldronLevelChangeInterface {

        CauldronLevelChangeEvent e;

        public CHCauldronLevelChangeEvent(CauldronLevelChangeEvent e){
            this.e = e;
        }

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public MCEntity getEntity() {
            return new BukkitMCEntity(e.getEntity());
        }

        @Override
        public CInt getNewLevel() {
            return new CInt(e.getNewLevel(), Target.UNKNOWN);
        }

        @Override
        public CInt getOldLevel() {
            return new CInt(e.getOldLevel(), Target.UNKNOWN);
        }

        @Override
        public CString getReason() {
            return new CString(e.getReason().name(), Target.UNKNOWN);
        }

        @Override
        public boolean isCancelled() {
            return e.isCancelled();
        }

        @Override
        public void setCancelled(boolean cancelled) {
            e.setCancelled(cancelled);
        }

        @Override
        public void setNewLevel(int newLevel) {
            e.setNewLevel(newLevel);
        }
    }

    public interface CHCauldronLevelChangeInterface extends BindableEvent {

        public MCEntity getEntity();
        public CInt getNewLevel();
        public CInt getOldLevel();
        public CString getReason();
        public boolean isCancelled();
        public void setCancelled(boolean cancelled);
        public void setNewLevel(int newLevel);

    }
}
