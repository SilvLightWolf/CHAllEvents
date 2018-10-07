package com.goop.chae;


import com.goop.chae.events.*;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.BrewingStandFuelEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

public class Listener implements org.bukkit.event.Listener {

    private static org.bukkit.event.Listener listener;

    public static void register(){
        if( listener == null )
            listener = new Listener();
        CommandHelperPlugin.self.registerEvents(listener);
    }

    public static void unregister(){
        AreaEffectCloudApplyEvent.getHandlerList().unregister(listener);
        AsyncPlayerPreLoginEvent.getHandlerList().unregister(listener);
        //BatToggleSleepEvent.getHandlerList().unregister(listener);
        BlockCanBuildEvent.getHandlerList().unregister(listener);
        BlockDamageEvent.getHandlerList().unregister(listener);
        BlockExpEvent.getHandlerList().unregister(listener);
        BlockExplodeEvent.getHandlerList().unregister(listener);
        BlockFertilizeEvent.getHandlerList().unregister(listener);
        BlockPhysicsEvent.getHandlerList().unregister(listener);
        BrewEvent.getHandlerList().unregister(listener);
        BrewingStandFuelEvent.getHandlerList().unregister(listener);
        CauldronLevelChangeEvent.getHandlerList().unregister(listener);
        ChunkLoadEvent.getHandlerList().unregister(listener);
        ChunkPopulateEvent.getHandlerList().unregister(listener);
        ChunkUnloadEvent.getHandlerList().unregister(listener);
        CraftItemEvent.getHandlerList().unregister(listener);
        CreeperPowerEvent.getHandlerList().unregister(listener);
        EnderDragonChangePhaseEvent.getHandlerList().unregister(listener);
        EntityAirChangeEvent.getHandlerList().unregister(listener);
        EntityCombustByBlockEvent.getHandlerList().unregister(listener);
        EntityCombustByEntityEvent.getHandlerList().unregister(listener);
        EntityCreatePortalEvent.getHandlerList().unregister(listener);
        EntityDismountEvent.getHandlerList().unregister(listener);
        EntityMountEvent.getHandlerList().unregister(listener);
    }

    @EventHandler
    public void onEvent1(AreaEffectCloudApplyEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "area_effect_cloud_apply",
                new MCAreaEffectCloudApplyEvent.CHAreaEffectCloudApplyEvent(e));
    }

    @EventHandler
    public void onEvent2(AsyncPlayerPreLoginEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "async_player_pre_login",
                new MCAsyncPlayerPreLogin.CHAsyncPlayerPreLoginEvent(e));
    }

    /*@EventHandler
    public void onEvent3(BatToggleSleepEvent e){

    }*/

    @EventHandler
    public void onEvent4(BlockCanBuildEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "block_can_build",
                new MCBlockCanBuildEvent.CHBlockCanBuildEvent(e));
    }

    @EventHandler
    public void onEvent5(BlockDamageEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "block_damage",
                new MCBlockDamageEvent.CHBlockDamageEvent(e));
    }

    @EventHandler
    public void onEvent6(BlockExpEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "block_exp",
                new MCBlockExpEvent.CHBlockExpEvent(e));
    }

    @EventHandler
    public void onEvent7(BlockExplodeEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "block_explode",
                new MCBlockExplodeEvent.CHBlockExplodeEvent(e));
    }

    @EventHandler
    public void onEvent8(BlockFertilizeEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "block_fertilize",
                new MCBlockFertilizeEvent.CHBlockFertilizeEvent(e));
    }

    @EventHandler
    public void onEvent9(BlockPhysicsEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "block_physics",
                new MCBlockPhysicsEvent.CHBlockPhysicsEvent(e));
    }

    @EventHandler
    public void onEvent10(BlockRedstoneEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "block_redstone",
                new MCBlockRedstoneEvent.CHBlockRedstoneEvent(e));
    }

    @EventHandler
    public void onEvent11(BrewEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "brew",
                new MCBrewEvent.CHBrewEvent(e));
    }

    @EventHandler
    public void onEvent12(BrewingStandFuelEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "brewing_stand_fuel",
                new MCBrewingStandFuelEvent.CHBrewingStandFuelEvent(e));
    }

    @EventHandler
    public void onEvent13(CauldronLevelChangeEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "cauldron_level_change",
                new MCCauldronLevelChangeEvent.CHCauldronLevelChangeEvent(e));
    }

    @EventHandler
    public void onEvent14(ChunkLoadEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "chunk_load",
                new MCChunkLoadEvent.CHChunkLoadEvent(e));
    }

    @EventHandler
    public void onEvent15(ChunkPopulateEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "chunk_populate",
                new MCChunkPopulateEvent.CHChunkPopulateEvent(e));
    }

    @EventHandler
    public void onEvent16(ChunkUnloadEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "chunk_unload",
                new MCChunkUnloadEvent.CHChunkUnloadEvent(e));
    }

    @EventHandler
    public void onEvent17(CraftItemEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "craft_item",
                new MCCraftItemEvent.CHCraftItemEvent(e));
    }

    @EventHandler
    public void onEvent18(CreeperPowerEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "creeper_power",
                new MCCreeperPowerEvent.CHCreeperPowerEvent(e));
    }

    @EventHandler
    public void onEvent19(EnderDragonChangePhaseEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "ender_dragon_Change_phase",
                new MCEnderDragonChangePhaseEvent.CHEnderDragonChangePhaseEvent(e));
    }

    @EventHandler
    public void onEvent20(EntityAirChangeEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "entity_air_change",
                new MCEntityAirChangeEvent.CHEntityAirChangeEvent(e));
    }

    @EventHandler
    public void onEvent21(EntityCombustByBlockEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "entity_combust_by_block",
                new MCEntityCombustByBlockEvent.CHEntityCombustByBlockEvent(e));
    }

    @EventHandler
    public void onEvent22(EntityCombustByEntityEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "entity_combust_by_entity",
                new MCEntityCombustByEntityEvent.CHEntityCombustByEntityEvent(e));
    }

    @EventHandler
    public void onEvent23(EntityCreatePortalEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "entity_create_portal",
                new MCEntityCreatePortalEvent.CHEntityCreatePortalEvent(e));
    }

    @EventHandler
    public void onEvent24(EntityDismountEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "entity_dismount",
                new MCEntityDismountEvent.CHEntityDismountEvent(e));
    }

    @EventHandler
    public void onEvent25(EntityMountEvent e){
        EventUtils.TriggerListener(Driver.EXTENSION, "entity_mount",
                new MCEntityMountEvent.CHEntityMountEvent(e));
    }


}
