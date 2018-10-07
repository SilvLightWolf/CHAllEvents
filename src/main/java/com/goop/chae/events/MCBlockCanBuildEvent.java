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
import com.laytonsmith.core.exceptions.CRE.CRECastException;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.block.BlockCanBuildEvent;

import java.util.HashMap;
import java.util.Map;

public class MCBlockCanBuildEvent {

    @api
    public static class CHBlockCanBuildAPI extends AbstractEvent{

        public String getName() {
            return "block_can_build";
        }

        public String docs() {
            return "";
        }

        public Version since() {
            return new SimpleVersion(1, 0, 0);
        }

        public boolean matches(Map<String, Construct> prefilter, BindableEvent e) throws PrefilterNonMatchException {
            return true;
        }

        public BindableEvent convert(CArray manualObject, Target t) {
            return null;
        }

        public Map<String, Construct> evaluate(BindableEvent e) throws EventException {
            if(e instanceof CHBlockCanBuildInterface){
               CHBlockCanBuildInterface evt = (CHBlockCanBuildInterface) e;
               Map<String, Construct> array = new HashMap<String, Construct>();
               Target t = Target.UNKNOWN;

               BlockData bd = evt.getBlockData();
               MCBlock block = new BukkitMCBlock(evt.getBlock());
               CArray blockarr = new CArray(t);

               blockarr.set("x", new CInt(block.getX(), t), t);
               blockarr.set("y", new CInt(block.getY(), t), t);
               blockarr.set("z", new CInt(block.getZ(), t), t);
               blockarr.set("name", block.getState().getType().getName(), t);

               array.put("blockdata", new CString(bd.getAsString(), t));
               array.put("block", blockarr);
               array.put("buildable", CBoolean.get(evt.isBuildable()));
               array.put("macrotype", new CString("block", t));
               array.put("event_type", new CString(getName(), t));

                return array;
            }
            return null;
        }

        public Driver driver() {
            return Driver.EXTENSION;
        }

        public boolean modifyEvent(String key, Construct value, BindableEvent event) {
            if (key.equalsIgnoreCase("buildable")) {
                switch(value.getValue().toLowerCase()){
                    case "true":
                        ((CHBlockCanBuildInterface) event).setBuildable(true);
                        return true;
                    case "false":
                        ((CHBlockCanBuildInterface) event).setBuildable(false);
                        return true;
                    default:
                        Thrower.throwCRECastException1("boolean", 2, "modify_event");
                }
            }
            return false;
        }
    }

    public static class CHBlockCanBuildEvent implements CHBlockCanBuildInterface{

        BlockCanBuildEvent e;

        public CHBlockCanBuildEvent(BlockCanBuildEvent e){ this.e = e; }

        public Object _GetObject() {
            return null;
        }

        public BlockData getBlockData() {
            return e.getBlockData();
        }

        public Block getBlock() {
            return e.getBlock();
        }

        public boolean isBuildable() {
            return e.isBuildable();
        }

        public void setBuildable(boolean cancel) {
            e.setBuildable(cancel);
        }
    }

    public interface CHBlockCanBuildInterface extends BindableEvent{

        public BlockData getBlockData();
        public Block getBlock();
        public boolean isBuildable();
        public void setBuildable(boolean cancel);

    }
}
