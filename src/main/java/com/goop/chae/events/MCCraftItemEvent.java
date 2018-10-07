package com.goop.chae.events;

import com.goop.chae.Thrower;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCCraftingInventory;
import com.laytonsmith.abstraction.MCHumanEntity;
import com.laytonsmith.abstraction.MCItemStack;
import com.laytonsmith.abstraction.MCRecipe;
import com.laytonsmith.abstraction.bukkit.BukkitConvertor;
import com.laytonsmith.abstraction.bukkit.BukkitMCCraftingInventory;
import com.laytonsmith.abstraction.bukkit.BukkitMCItemStack;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCHumanEntity;
import com.laytonsmith.abstraction.enums.MCResult;
import com.laytonsmith.abstraction.enums.bukkit.BukkitMCResult;
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
import org.bukkit.event.Event;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MCCraftItemEvent {

    @api
    public static class CHCraftItemAPI extends AbstractEvent{

        @Override
        public String getName() {
            return "craft_item";
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
            if(e instanceof CHCraftItemInterface){
                CHCraftItemInterface evt = (CHCraftItemInterface) e;
                Map<String, Construct> array = new HashMap<>();
                Target t = Target.UNKNOWN;

                CArray inv = new CArray(t);
                for(int i = 0 ; i < evt.getInventory().getSize() ; i++)
                    inv.set(i, ObjectGenerator.GetGenerator().item(evt.getInventory().getItem(i), t), t);
                array.put("inventory", inv);

                array.put("player", new CString(evt.getWhoClicked().getName(), t));
                array.put("recipe", ObjectGenerator.GetGenerator().recipe(evt.getRecipe(), t));
                array.put("result", new CString(evt.getResult().name(), t));
                array.put("cursor", ObjectGenerator.GetGenerator().item(evt.getCursor(), t));

                array.put("macrotype", new CString("inventory", t));
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
            if(key.equalsIgnoreCase("result")){
                switch(value.getValue().toLowerCase()){
                    case "ALLOW":
                        ((CHCraftItemInterface)event).setResult(Event.Result.ALLOW);
                        return true;
                    case "DEFAULT":
                        ((CHCraftItemInterface)event).setResult(Event.Result.DEFAULT);
                        return true;
                    case "DENY":
                        ((CHCraftItemInterface)event).setResult(Event.Result.DENY);
                        return true;
                    default:
                        Thrower.throwCREIllegalException1(value.getValue());
                }
            }
            return false;
        }
    }

    public static class CHCraftItemEvent implements CHCraftItemInterface {

        CraftItemEvent e;

        public CHCraftItemEvent(CraftItemEvent e){ this.e = e; }

        @Override
        public Object _GetObject() {
            return null;
        }

        @Override
        public MCCraftingInventory getInventory() {
            return new BukkitMCCraftingInventory(e.getInventory());
        }

        @Override
        public MCRecipe getRecipe() {
            return BukkitConvertor.BukkitGetRecipe(e.getRecipe());
        }

        @Override
        public MCResult getResult() {
            return BukkitMCResult.getConvertor().getAbstractedEnum(e.getResult());
        }

        @Override
        public MCHumanEntity getWhoClicked() {
            return new BukkitMCHumanEntity(e.getWhoClicked());
        }

        @Override
        public MCItemStack getCursor() {
            return new BukkitMCItemStack(e.getCursor());
        }

        @Override
        public void setResult(Event.Result result) {
            e.setResult(Event.Result.valueOf(result.name()));
        }

    }

    public interface CHCraftItemInterface extends BindableEvent {

        public MCCraftingInventory getInventory();
        public MCRecipe getRecipe();
        public MCResult getResult();
        public MCHumanEntity getWhoClicked();
        public MCItemStack getCursor();
        public void setResult(Event.Result result);
    }
}
