package com.goop.chae.events;

import com.goop.chae.Thrower;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCItemStack;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.bukkit.BukkitMCItemStack;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCPlayer;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import org.bukkit.event.block.BlockDamageEvent;

import java.util.HashMap;
import java.util.Map;

public class MCBlockDamageEvent {

    @api
    public static class CHBlockDamageAPI extends AbstractEvent {

        @Override
        public String getName() {
            return "block_damage";
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
            if(e instanceof CHBlockDamageInterface){
                CHBlockDamageInterface evt = (CHBlockDamageInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                array.put("instabreak", CBoolean.get(evt.getInstaBreak()));
                array.put("player", new CString(evt.getPlayer().getName(), t));
                array.put("macro_type", new CString("block", t));
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
            if(key.equalsIgnoreCase("instabreak")){
                switch(value.getValue().toLowerCase()){
                    case "true":
                        ((CHBlockDamageInterface)event).setInstaBreak(true);
                        return true;
                    case "false":
                        ((CHBlockDamageInterface)event).setInstaBreak(false);
                        return true;
                    default:
                        Thrower.throwCRECastException1("boolean", 2, "modify_event");
                }
            }else if(key.equalsIgnoreCase("cancelled")) {
                switch (value.getValue().toLowerCase()) {
                    case "true":
                        ((CHBlockDamageInterface)event).setCancelled(true);
                        return true;
                    case "false":
                        ((CHBlockDamageInterface)event).setCancelled(false);
                        return true;
                    default:
                        Thrower.throwCRECastException1("boolean", 2, "modify_event");
                }
            }
            return false;
        }
    }

    public static class CHBlockDamageEvent implements CHBlockDamageInterface {

        BlockDamageEvent e;

        public CHBlockDamageEvent(BlockDamageEvent e){ this.e = e; }

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public boolean getInstaBreak() {
            return e.getInstaBreak();
        }

        @Override
        public MCItemStack getItemInHand() {
            return new BukkitMCItemStack(e.getItemInHand());
        }

        @Override
        public MCPlayer getPlayer() {
            return new BukkitMCPlayer(e.getPlayer());
        }

        @Override
        public boolean isCancelled() {
            return e.isCancelled();
        }

        @Override
        public void setCancelled(boolean cancel) {
            e.setCancelled(cancel);
        }

        @Override
        public void setInstaBreak(boolean bool) {
            e.setInstaBreak(bool);
        }
    }

    public interface CHBlockDamageInterface extends BindableEvent {

        public boolean getInstaBreak();
        public MCItemStack getItemInHand();
        public MCPlayer getPlayer();
        public boolean isCancelled();
        public void setCancelled(boolean cancel);
        public void setInstaBreak(boolean bool);

    }
}
