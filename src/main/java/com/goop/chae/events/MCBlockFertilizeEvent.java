package com.goop.chae.events;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.blocks.MCBlockState;
import com.laytonsmith.abstraction.bukkit.blocks.BukkitMCBlockState;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCPlayer;
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
import org.bukkit.block.BlockState;
import org.bukkit.event.block.BlockFertilizeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MCBlockFertilizeEvent {

    @api
    public static class CHBlockFertilizeAPI extends AbstractEvent {

        @Override
        public String getName() {
            return "block_fertilize";
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
            if( e instanceof CHBlockFertilizeInterface ){

                CHBlockFertilizeInterface evt = (CHBlockFertilizeInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                CArray blocks = new CArray(t);
                for(MCBlockState bs : evt.getBlocks())
                    blocks.push(ObjectGenerator.GetGenerator().location(bs.getLocation()), t);

                array.put("blocks", blocks);
                array.put("player", new CString(evt.getPlayer().getName(), t));
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
            return false;
        }
    }

    public static class CHBlockFertilizeEvent implements CHBlockFertilizeInterface {

        BlockFertilizeEvent e;

        public CHBlockFertilizeEvent(BlockFertilizeEvent e){ this.e = e; }

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public List<MCBlockState> getBlocks() {
            List<MCBlockState> ret = new ArrayList<>();
            for(BlockState bs : e.getBlocks())
                ret.add(new BukkitMCBlockState(bs));
            return ret;
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
    }

    public interface CHBlockFertilizeInterface extends BindableEvent{

        public List<MCBlockState> getBlocks();
        public MCPlayer getPlayer();
        public boolean isCancelled();
        public void setCancelled(boolean cancel);

    }
}
