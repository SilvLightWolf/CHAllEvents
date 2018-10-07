package com.goop.chae;

import com.goop.chae.events.MCAreaEffectCloudApplyEvent;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;

@MSExtension("CHAllEvents")
public class LifeCycle extends AbstractExtension {


    @Override
    public void onStartup(){
        System.out.println("[CHAllEvents Loaded!] 24 Events Loaded! & 1 Event Errored! ");
        Listener.register();
    }

    @Override
    public void onShutdown(){
        System.out.println("[CHAllEvents Unloaded!] C'ya!");
        Listener.unregister();
    }

    public Version getVersion() {
        return new SimpleVersion(1, 0, 0);
    }


}
