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
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.HashMap;
import java.util.Map;

public class MCChunkUnloadEvent {

    @api
    public static class CHChunkUnloadAPI extends AbstractEvent{

        @Override
        public String getName() {
            return "chunk_unload";
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
            if(e instanceof CHChunkUnloadInterface){
                CHChunkUnloadInterface evt = (CHChunkUnloadInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                array.put("savechunk", CBoolean.get(evt.isSaveChunk()));

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
            if(key.equalsIgnoreCase("savechunk")){
                switch(value.getValue().toLowerCase()){
                    case "true":
                        ((CHChunkUnloadInterface)event).setSaveChunk(true);
                        return true;
                    case "false":
                        ((CHChunkUnloadInterface)event).setSaveChunk(false);
                        return true;
                    default:
                        Thrower.throwCRECastException1("boolean", 2, "modify_event");
                }
            }
            return false;
        }
    }

    public static class CHChunkUnloadEvent implements CHChunkUnloadInterface {

        ChunkUnloadEvent e;

        public CHChunkUnloadEvent(ChunkUnloadEvent e){ this.e = e; }

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public boolean isCancelled() {
            return e.isCancelled();
        }

        @Override
        public boolean isSaveChunk() {
            return e.isSaveChunk();
        }

        @Override
        public void setCancelled(boolean cancel) {
            e.setCancelled(cancel);
        }

        @Override
        public void setSaveChunk(boolean saveChunk) {
            e.setSaveChunk(saveChunk);
        }
    }

    public interface CHChunkUnloadInterface extends BindableEvent {

        public boolean isCancelled();
        public boolean isSaveChunk();
        public void setCancelled(boolean cancel);
        public void setSaveChunk(boolean saveChunk);

    }
}
