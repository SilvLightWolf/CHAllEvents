package com.goop.chae.events;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.blocks.MCBlock;
import com.laytonsmith.abstraction.bukkit.blocks.BukkitMCBlock;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockExplodeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MCBlockExplodeEvent {

    @api
    public static class CHBlockExplodeAPI extends AbstractEvent {

        @Override
        public String getName() {
            return "block_explode";
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
            if(e instanceof CHBlockExplodeInterface){
                CHBlockExplodeInterface evt = (CHBlockExplodeInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                CArray blocks = new CArray(t);
                for(MCBlock b : evt.getBlockList())
                    blocks.push(ObjectGenerator.GetGenerator().location(b.getLocation()), t);

                array.put("blocks", blocks);
                array.put("yield", evt.getYield());
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
            if(key.equalsIgnoreCase("yield")){
                if(value instanceof CDouble || value instanceof CInt){
                    ((CHBlockExplodeInterface)event).setYield(Float.valueOf(value.getValue()));
                    return true;
                }
            }
            return false;
        }
    }

    public static class CHBlockExplodeEvent implements CHBlockExplodeInterface {

        BlockExplodeEvent e;

        public CHBlockExplodeEvent(BlockExplodeEvent e){
            this.e = e;
        }

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public List<MCBlock> getBlockList() {
            List<MCBlock> ret = new ArrayList<>();
            for(Block b : e.blockList())
                ret.add(new BukkitMCBlock(b));
            return ret;
        }

        @Override
        public CDouble getYield() {
            return new CDouble(e.getYield(), Target.UNKNOWN);
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
        public void setYield(float yield) {
            e.setYield(yield);
        }
    }

    public interface CHBlockExplodeInterface extends BindableEvent{

        public List<MCBlock> getBlockList();
        public CDouble getYield();
        public boolean isCancelled();
        public void setCancelled(boolean cancel);
        public void setYield(float yield);

    }
}
