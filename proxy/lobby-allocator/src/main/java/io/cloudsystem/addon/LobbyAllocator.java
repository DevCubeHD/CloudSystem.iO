package io.cloudsystem.addon;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class LobbyAllocator implements Listener {

    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {

        ProxiedPlayer proxiedPlayer = event.getPlayer();

        if (event.getTarget().getName().equals("lobby") || event.getTarget().getName().equals("fallback")) {

            List<ServerInfo> lobbies = new ArrayList<>();
            ProxyServer.getInstance().getServers().values().forEach(serverInfo -> {
                if (serverInfo.getName().toLowerCase().contains("lobby")) {
                    lobbies.add(serverInfo);
                }
            });

            if (lobbies.isEmpty()) {
                proxiedPlayer.disconnect(new TextComponent("§cThere is no lobby server available."));
                return;
            }

            final ServerInfo[] info = {null};

            lobbies.forEach(serverInfo -> {
                if (info[0] != null) {
                    if (info[0].getPlayers().size() > serverInfo.getPlayers().size()) {
                        if (serverInfo.canAccess(proxiedPlayer)) {
                            info[0] = serverInfo;
                        }
                    }
                } else {
                    if (serverInfo.canAccess(proxiedPlayer)) {
                        info[0] = serverInfo;
                    }
                }
            });

            if (info[0] == null) {
                proxiedPlayer.disconnect(new TextComponent("§cThere is no lobby server available."));
                return;
            }

            event.setTarget(info[0]);

        }

    }

}
