package com.goop.chae.events;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
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
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MCAreaEffectCloudApplyEvent {

    @api
    public static class CHAreaEffectCloudApplyAPI extends AbstractEvent{

        public String getName() {
            return "area_effect_cloud_apply";
        }

        public String docs() {
            return "";
        }

        public Version since() {
            return new SimpleVersion(1, 0,0 );
        }

        public boolean matches(Map<String, Construct> prefilter, BindableEvent e) throws PrefilterNonMatchException {
            return true;
        }

        public BindableEvent convert(CArray manualObject, Target t) {
            return null;
        }

        public Map<String, Construct> evaluate(BindableEvent e) throws EventException {
            if(e instanceof CHAreaEffectCloudApplyInterface){

                CHAreaEffectCloudApplyInterface evt = (CHAreaEffectCloudApplyInterface) e;
                Map<String, Construct> array = new HashMap<String, Construct>();
                Target t = Target.UNKNOWN;

                CArray list = new CArray(t);
                for(Entity entity : evt.getAffectedEntities())
                    list.push(new CString(entity.getUniqueId().toString(), t), t);

                array.put("affected", list);
                array.put("entity", new CString(evt.getEntity().getUniqueId().toString(), t));
                array.put("event_type", new CString(getName(), t));
                array.put("macrotype", new CString("entity", t));

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



    public static class CHAreaEffectCloudApplyEvent implements CHAreaEffectCloudApplyInterface{

        AreaEffectCloudApplyEvent e;

        public CHAreaEffectCloudApplyEvent(AreaEffectCloudApplyEvent e){ this.e = e; }

        public Object _GetObject() {
            return null;
        }

        public List<LivingEntity> getAffectedEntities() {
            return e.getAffectedEntities();
        }

        public Entity getEntity() {
            return e.getEntity();
        }
    }



    public interface CHAreaEffectCloudApplyInterface extends BindableEvent{

        public List<LivingEntity> getAffectedEntities();
        public Entity getEntity();
    }


}
