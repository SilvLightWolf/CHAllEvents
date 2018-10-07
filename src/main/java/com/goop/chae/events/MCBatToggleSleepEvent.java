package com.goop.chae.events;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;

import java.util.HashMap;
import java.util.Map;

//Not Found Event

public class MCBatToggleSleepEvent {
/*
    @api
    public static class CHBatToggleSleepAPI extends AbstractEvent {

        public String getName() {
            return "bat_toggle_sleep;
        }

        public String docs() {
            return "";
        }

        public Version since() {
            return new SimpleVersion(1, 0 ,0);
        }

        public boolean matches(Map<String, Construct> prefilter, BindableEvent e) throws PrefilterNonMatchException {
            return true;
        }

        public BindableEvent convert(CArray manualObject, Target t) {
            return null;
        }

        public Map<String, Construct> evaluate(BindableEvent e) throws EventException {
            if(e instanceof CHBatToggleSleepInterface){
                CHBatToggleSleepInterface evt = (CHBatToggleSleepInterface) e;
                Map<String, Construct> array = new HashMap<String, Construct>();
                Target t = Target.UNKNOWN;

                array.put("macrotype", new CString("entity", t));
                array.put("event_type", new CString(getName(), t));

                return array;
            }
            return null;
        }

        public Driver driver() {
            return Driver.EXTENSION;
        }

        public boolean modifyEvent(String key, Construct value, BindableEvent event) {
            return false;
        }
    }

    public static class CHBatToggleSleepEvent implements CHBatToggleSleepInterface {


        public Object _GetObject() {
            return null;
        }
    }

    public interface CHBatToggleSleepInterface extends BindableEvent{

    }*/
}
