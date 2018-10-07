package com.goop.chae.events;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.blocks.MCBlockState;
import com.laytonsmith.abstraction.bukkit.blocks.BukkitMCBlockState;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCEntity;
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
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.entity.EntityCreatePortalEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MCEntityCreatePortalEvent {

    @api
    public static class CHEntityCreatePortalAPI extends AbstractEvent {

        @Override
        public String getName() {
            return "entity_create_portal";
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
            if(e instanceof CHEntityCreatePortalInterface){
                CHEntityCreatePortalInterface evt = (CHEntityCreatePortalInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                CArray blocks = new CArray(t);
                for(MCBlockState bs : evt.getBlocks())
                    blocks.push(ObjectGenerator.GetGenerator().location(bs.getLocation()), t);
                array.put("blocks", blocks);

                array.put("id", new CString(evt.getEntity().getUniqueId().toString(), t));
                array.put("type", evt.getPortalType());

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
            return false;
        }
    }

    public static class CHEntityCreatePortalEvent implements CHEntityCreatePortalInterface {

        EntityCreatePortalEvent e;

        public CHEntityCreatePortalEvent(EntityCreatePortalEvent e){ this.e = e; }

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
        public MCEntity getEntity() {
            return new BukkitMCEntity(e.getEntity());
        }

        @Override
        public CString getPortalType() {
            return new CString(e.getPortalType().name(), Target.UNKNOWN);
        }

        @Override
        public boolean isCancelled() {
            return e.isCancelled();
        }

        @Override
        public void setCancelled(boolean cancelled) {
            e.setCancelled(cancelled);
        }
    }

    public interface CHEntityCreatePortalInterface extends BindableEvent {

        public List<MCBlockState> getBlocks();
        public MCEntity getEntity();
        public CString getPortalType();
        public boolean isCancelled();
        public void setCancelled(boolean cancelled);
    }
}
