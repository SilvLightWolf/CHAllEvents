package com.goop.chae.events;

import com.goop.chae.Thrower;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.blocks.MCBlock;
import com.laytonsmith.abstraction.bukkit.blocks.BukkitMCBlock;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import org.bukkit.event.entity.EntityCombustByBlockEvent;

import java.util.HashMap;
import java.util.Map;

public class MCEntityCombustByBlockEvent {

    @api
    public static class CHEntityCombustByBlockAPI extends AbstractEvent {

        @Override
        public String getName() {
            return "entity_combust_by_block";
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
            if(e instanceof CHEntityCombustByBlockInterface){
                CHEntityCombustByBlockInterface evt = (CHEntityCombustByBlockInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                array.put("duration", evt.getDuration());

                if(evt.getBlock() instanceof CNull){
                    array.put("combuster", CNull.NULL);
                }else{
                    CArray block = new CArray(t);
                    MCBlock b = (MCBlock) evt.getBlock();
                    block.set("x", new CInt(b.getLocation().getBlockX(), t), t);
                    block.set("y", new CInt(b.getLocation().getBlockY(), t), t);
                    block.set("z", new CInt(b.getLocation().getBlockZ(), t), t);
                    block.set("world", b.getLocation().getWorld().getName());
                    block.set("block", b.getType().getName());
                    array.put("combuster", block);
                }

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
            if(key.equalsIgnoreCase("duration")){
                if(value instanceof CInt) {
                    ((CHEntityCombustByBlockInterface) event).setDuration(Integer.parseInt(value.getValue()));
                    return true;
                }else{
                    Thrower.throwCRECastException1("int", 2, "modify_event");
                }
            }else if(key.equalsIgnoreCase("cancelled")) {
                switch (value.getValue().toLowerCase()) {
                    case "true":
                        ((CHEntityCombustByBlockInterface)event).setCancelled(true);
                        return true;
                    case "false":
                        ((CHEntityCombustByBlockInterface)event).setCancelled(false);
                        return true;
                    default:
                        Thrower.throwCRECastException1("boolean", 2, "modify_event");
                }
            }
            return false;
        }
    }

    public static class CHEntityCombustByBlockEvent implements CHEntityCombustByBlockInterface {

        EntityCombustByBlockEvent e;

        public CHEntityCombustByBlockEvent(EntityCombustByBlockEvent e){
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
        public Object getBlock() {
            return (e.getCombuster() == null) ? CNull.NULL : new BukkitMCBlock(e.getCombuster());
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

    public interface CHEntityCombustByBlockInterface extends BindableEvent {

        public CInt getDuration();
        public boolean isCancelled();
        public Object getBlock();
        public void setCancelled(boolean cancel);
        public void setDuration(int duration);

    }
}
