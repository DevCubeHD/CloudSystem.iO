package io.cloudsystem.addon;

import io.cloudsystem.listeners.LobbyAllocator;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {

    @Override
    public void onEnable() {

        ProxyServer.getInstance().getPluginManager().registerListener(this, new LobbyAllocator());

    }

    @Override
    public void onDisable() {

    }

}