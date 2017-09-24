package io.cloudsystem.addon;

import io.cloudsystem.Proxy;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;

public class HubCommand extends Command {

    public HubCommand() {
        super("hub");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {

        ProxiedPlayer p = (ProxiedPlayer) commandSender;

        List<ServerInfo> lobbies = new ArrayList<>();
        ProxyServer.getInstance().getServers().values().forEach(serverInfo -> {
            if (serverInfo.getName().toLowerCase().contains("lobby")) {
                lobbies.add(serverInfo);
            }
        });

        if (lobbies.isEmpty()) {
            p.sendMessage(ChatMessageType.CHAT, new TextComponent(Proxy.PREFIX + "§cThere is no lobby server available."));
            return;
        }

        final ServerInfo[] info = {null};

        lobbies.forEach(serverInfo -> {
            if (info[0] != null) {
                if (info[0].getPlayers().size() > serverInfo.getPlayers().size()) {
                    if (serverInfo.canAccess(p)) {
                        info[0] = serverInfo;
                    }
                }
            } else {
                if (serverInfo.canAccess(p)) {
                    info[0] = serverInfo;
                }
            }
        });

        if (info[0] == null) {
            p.sendMessage(ChatMessageType.CHAT, new TextComponent(Proxy.PREFIX + "§cThere is no lobby server available."));
            return;
        }

        p.connect(info[0]);

    }

}
