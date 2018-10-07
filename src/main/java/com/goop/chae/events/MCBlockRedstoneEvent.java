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
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.HashMap;
import java.util.Map;

public class MCBlockRedstoneEvent {

    @api
    public static class CHBlockRedstoneAPI extends AbstractEvent {

        @Override
        public String getName() {
            return "block_redstone";
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
            if(e instanceof CHBlockRedstoneInterface){
                CHBlockRedstoneInterface evt = (CHBlockRedstoneInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                array.put("newcurrent", evt.getNewCurrent());
                array.put("oldcurrent", evt.getOldCurrent());

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
            if(key.equalsIgnoreCase("newcurrent")){
                if(value instanceof CInt){
                    ((CHBlockRedstoneInterface)event).setNewCurrent(Integer.valueOf(value.getValue()));
                    return true;
                }else{
                    Thrower.throwCRECastException1("int", 2, "modify_event");
                }
            }
            return false;
        }
    }

    public static class CHBlockRedstoneEvent implements CHBlockRedstoneInterface {

        BlockRedstoneEvent e;

        public CHBlockRedstoneEvent(BlockRedstoneEvent e){ this.e = e; }

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public CInt getNewCurrent() {
            return new CInt(e.getNewCurrent(), Target.UNKNOWN);
        }

        @Override
        public CInt getOldCurrent() {
            return new CInt(e.getOldCurrent(), Target.UNKNOWN);
        }

        @Override
        public void setNewCurrent(int newCurrent) {
            e.setNewCurrent(newCurrent);
        }
    }

    public interface CHBlockRedstoneInterface extends BindableEvent{

        public CInt getNewCurrent();
        public CInt getOldCurrent();
        public void setNewCurrent(int newCurrent);

    }

}
