package com.goop.chae.events;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCChunk;
import com.laytonsmith.abstraction.bukkit.BukkitMCChunk;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import org.bukkit.event.world.ChunkPopulateEvent;

import java.util.HashMap;
import java.util.Map;

public class MCChunkPopulateEvent {

    @api
    public static class CHChunkPopulateAPI extends AbstractEvent {

        @Override
        public String getName() {
            return "chunk_populate";
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
            if(e instanceof CHChunkPopulateInterface){
                CHChunkPopulateInterface evt = (CHChunkPopulateInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                CArray chunk = new CArray(t);
                chunk.set("x", new CInt(evt.getChunk().getX(), t), t);
                chunk.set("z", new CInt(evt.getChunk().getZ(),t ), t);
                chunk.set("world", evt.getChunk().getWorld().getName());
                array.put("chunk", chunk);

                array.put("macrotype", new CString("world", t));
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

    public static class CHChunkPopulateEvent implements CHChunkPopulateInterface {

        ChunkPopulateEvent e;

        public CHChunkPopulateEvent(ChunkPopulateEvent e){ this.e = e; }

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public MCChunk getChunk() {
            return new BukkitMCChunk(e.getChunk());
        }
    }

    public interface CHChunkPopulateInterface extends BindableEvent {

        public MCChunk getChunk();

    }
}
