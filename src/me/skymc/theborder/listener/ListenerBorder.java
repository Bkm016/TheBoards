package me.skymc.theborder.listener;

import me.skymc.theborder.game.BorderState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

public class ListenerBorder implements org.bukkit.event.Listener {

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        e.setMotd(BorderState.getState().name());
    }
}