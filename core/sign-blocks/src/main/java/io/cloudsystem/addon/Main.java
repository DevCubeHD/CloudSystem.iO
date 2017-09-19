package io.cloudsystem.addon;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        //Register Listener (SignUpdateEvent - custom by Core)
        getServer().getPluginManager().registerEvents(new SignUpdateListener(), this);

    }

    @Override
    public void onDisable() {

    }

}