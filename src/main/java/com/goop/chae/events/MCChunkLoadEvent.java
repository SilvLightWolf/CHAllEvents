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
import org.bukkit.event.world.ChunkLoadEvent;

import java.util.HashMap;
import java.util.Map;

public class MCChunkLoadEvent {

    @api
    public static class CHChunkLoadAPI extends AbstractEvent {

        @Override
        public String getName() {
            return "chunk_load";
        }

        @Override
        public String docs() {
            return "";
        }

        @Override
        public Version since() {
            return new SimpleVersion(1, 0 ,0);
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
            if(e instanceof CHChunkLoadInterface){
                CHChunkLoadInterface evt = (CHChunkLoadInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                CArray chunk = new CArray(t);
                chunk.set("x", new CInt(evt.getChunk().getX(), t), t);
                chunk.set("z", new CInt(evt.getChunk().getZ(),t ), t);
                chunk.set("world", evt.getChunk().getWorld().getName());
                array.put("chunk", chunk);

                array.put("newchunk", CBoolean.get(evt.isNewChunk()));

                array.put("event_type", new CString(getName(), t));
                array.put("macrotype", new CString("world", t));

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

    public static class CHChunkLoadEvent implements CHChunkLoadInterface {

        ChunkLoadEvent e;

        public CHChunkLoadEvent(ChunkLoadEvent e){
            this.e = e;
        }

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public boolean isNewChunk() {
            return e.isNewChunk();
        }

        @Override
        public MCChunk getChunk() {
            return new BukkitMCChunk(e.getChunk());
        }
    }

    public interface CHChunkLoadInterface extends BindableEvent {
        public boolean isNewChunk();
        public MCChunk getChunk();
    }
}
