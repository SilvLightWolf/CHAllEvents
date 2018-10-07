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
import org.bukkit.event.block.BlockExpEvent;

import java.util.HashMap;
import java.util.Map;

public class MCBlockExpEvent {

    @api
    public static class CHBlockExpAPI extends AbstractEvent{

        @Override
        public String getName() {
            return "block_exp";
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
            if(e instanceof CHBlockExpInterface){
                CHBlockExpInterface evt = (CHBlockExpInterface) e;
                Map<String,Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                array.put("exp", evt.getExpToDrop());
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
            if(key.equalsIgnoreCase("exp")){
                if(value instanceof CInt || value instanceof CDouble) {
                    ((CHBlockExpInterface) event).setExpToDrop(Integer.parseInt(value.getValue()));
                    return true;
                }else{
                    Thrower.throwCRECastException1("number", 2, "modify_event");
                }
            }
            return false;
        }
    }

    public static class CHBlockExpEvent implements CHBlockExpInterface {

        BlockExpEvent e;

        public CHBlockExpEvent(BlockExpEvent e){ this.e = e;}

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public CInt getExpToDrop() {
            return new CInt(e.getExpToDrop(), Target.UNKNOWN);
        }

        @Override
        public void setExpToDrop(int exp) {
            e.setExpToDrop(exp);
        }
    }

    public interface CHBlockExpInterface extends BindableEvent {

        public CInt getExpToDrop();
        public void setExpToDrop(int exp);

    }
}
