package me.skymc.theborder.handler;

import me.skymc.theborder.TheBorders;
import me.skymc.theborder.game.BorderState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @Author sky
 * @Since 2018-06-09 10:45
 */
public class GameHandler {

    public static void checkWin() {
        if (TheBorders.getPlayerInGame().isEmpty()) {
            stopGame(0);
        }

        if (TheBorders.getPlayerInGame().size() == 1) {
            String player = TheBorders.getPlayerInGame().get(0);
            Bukkit.broadcastMessage(SettingHandler.getString("prefix") + SettingHandler.getString("messages.win_message").replace("<name>", player));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nl addxp " + player + " 1");
            stopGame(3);
        }
    }

    public static void stopGame(int paramInt) {
        Bukkit.getScheduler().runTaskLater(TheBorders.getInstance(), () -> {
            BorderState.setState(BorderState.FINISH);

            for (Player localPlayer : Bukkit.getOnlinePlayers()) {
                if (!SettingHandler.getBoolean("bungee.enable")) {
                    localPlayer.kickPlayer(SettingHandler.getString("messages.stop_server"));
                } else {
                    TheBorders.getInstance().bungeeTP(localPlayer, SettingHandler.getString("bungee.hubName"));
                }
            }

            if (SettingHandler.getBoolean("options.shutdownServer")) {
                Bukkit.shutdown();
            }
        }, paramInt * 20);
    }

}
