package com.goop.chae.events;

import com.goop.chae.Thrower;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCEntity;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;

import java.util.HashMap;
import java.util.Map;

public class MCEnderDragonChangePhaseEvent {

    @api
    public static class CHEnderDragonChangePhaseAPI extends AbstractEvent {

        @Override
        public String getName() {
            return "ender_dragon_change_phase";
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
            if(e instanceof CHEnderDragonChangePhaseInterface){
                CHEnderDragonChangePhaseInterface evt = (CHEnderDragonChangePhaseInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                array.put("currentphase", evt.getCurrentPhase());
                array.put("newphase", evt.getNewPhase());
                array.put("id", new CString(evt.getEntity().getUniqueId().toString(), t));

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
            if(key.equalsIgnoreCase("newphase")){
                CHEnderDragonChangePhaseInterface e = (CHEnderDragonChangePhaseInterface) event;
                switch(value.getValue().toLowerCase()){
                    case "breath_attack":
                        e.setNewPhase(EnderDragon.Phase.BREATH_ATTACK);
                        return true;
                    case "charge_player":
                        e.setNewPhase(EnderDragon.Phase.CHARGE_PLAYER);
                        return true;
                    case "circling":
                        e.setNewPhase(EnderDragon.Phase.CIRCLING);
                        return true;
                    case "dying":
                        e.setNewPhase(EnderDragon.Phase.DYING);
                        return true;
                    case "fly_to_portal":
                        e.setNewPhase(EnderDragon.Phase.FLY_TO_PORTAL);
                        return true;
                    case "hover":
                        e.setNewPhase(EnderDragon.Phase.HOVER);
                        return true;
                    case "land_on_portal":
                        e.setNewPhase(EnderDragon.Phase.LAND_ON_PORTAL);
                        return true;
                    case "leave_portal":
                        e.setNewPhase(EnderDragon.Phase.LEAVE_PORTAL);
                        return true;
                    case "roar_before_attack":
                        e.setNewPhase(EnderDragon.Phase.ROAR_BEFORE_ATTACK);
                        return true;
                    case "search_for_breath_attack_target":
                        e.setNewPhase(EnderDragon.Phase.SEARCH_FOR_BREATH_ATTACK_TARGET);
                        return true;
                    case "strafing":
                        e.setNewPhase(EnderDragon.Phase.STRAFING);
                        return true;
                    default:
                        Thrower.throwCREIllegalException1(value.getValue());
                }
            }else if(key.equalsIgnoreCase("cancelled")) {
                switch (value.getValue().toLowerCase()) {
                    case "true":
                        ((CHEnderDragonChangePhaseInterface)event).setCancelled(true);
                        return true;
                    case "false":
                        ((CHEnderDragonChangePhaseInterface)event).setCancelled(false);
                        return true;
                    default:
                        Thrower.throwCRECastException1("boolean", 2, "modify_event");
                }
            }
            return false;
        }
    }

    public static class CHEnderDragonChangePhaseEvent implements  CHEnderDragonChangePhaseInterface {

        EnderDragonChangePhaseEvent e;

        public CHEnderDragonChangePhaseEvent(EnderDragonChangePhaseEvent e){ this.e = e; }

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public Construct getCurrentPhase() {
            return (e.getCurrentPhase() == null) ? CNull.NULL : new CString(e.getCurrentPhase().name(), Target.UNKNOWN);
        }

        @Override
        public MCEntity getEntity() {
            return new BukkitMCEntity(e.getEntity());
        }

        @Override
        public Construct getNewPhase() {
            return (e.getNewPhase() == null) ? CNull.NULL : new CString(e.getNewPhase().name(), Target.UNKNOWN);
        }

        @Override
        public boolean isCancelled() {
            return e.isCancelled();
        }

        @Override
        public void setCancelled(boolean cancel) {
            e.setCancelled(cancel);
        }

        @Override
        public void setNewPhase(EnderDragon.Phase newPhase) {
            e.setNewPhase(newPhase);
        }
    }

    public interface CHEnderDragonChangePhaseInterface extends BindableEvent {

        public Construct getCurrentPhase();
        public MCEntity getEntity();
        public Construct getNewPhase();
        public boolean isCancelled();
        public void setCancelled(boolean cancel);
        public void setNewPhase(EnderDragon.Phase newPhase);
    }
}
