package com.goop.chae.events;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.blocks.MCMaterial;
import com.laytonsmith.abstraction.bukkit.blocks.BukkitMCMaterial;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import org.bukkit.event.block.BlockPhysicsEvent;

import java.util.HashMap;
import java.util.Map;

public class MCBlockPhysicsEvent {

    @api
    public static class CHBlockPhysicsAPI extends AbstractEvent {

        @Override
        public String getName() {
            return "block_physics";
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
            if(e instanceof CHBlockPhysicsInterface){

                CHBlockPhysicsInterface evt = (CHBlockPhysicsInterface) e;
                Map<String,Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                array.put("block", new CString(evt.getChangedType().getName(), t));

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

    public static class CHBlockPhysicsEvent implements CHBlockPhysicsInterface {

        BlockPhysicsEvent e;

        public CHBlockPhysicsEvent(BlockPhysicsEvent e){ this.e = e; }

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public MCMaterial getChangedType() {
            return new BukkitMCMaterial(e.getChangedType());
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

    public interface CHBlockPhysicsInterface extends BindableEvent {

        public MCMaterial getChangedType();
        public boolean isCancelled();
        public void setCancelled(boolean cancel);

    }
}
