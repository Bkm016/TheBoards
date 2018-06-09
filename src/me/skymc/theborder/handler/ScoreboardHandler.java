package me.skymc.theborder.handler;

import me.skymc.taboolib.scoreboard.ScoreboardUtil;
import me.skymc.theborder.TheBorders;
import me.skymc.theborder.game.BorderGame;
import me.skymc.theborder.game.BorderState;
import me.skymc.theborder.listener.ListenerJoin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ScoreboardHandler {

    private static AtomicReference<SimpleDateFormat> simpleDateFormat = new AtomicReference<>(new SimpleDateFormat("mm:ss"));

    public static void refresh() {
        if (BorderState.isState(BorderState.WAIT)) {
            List<String> scoreboard = SettingHandler.getList("scoreboards.waiting").stream().map(x -> x
                    .replace("&", "§")
                    .replace("<playersAmount>", String.valueOf(TheBorders.getPlayerInGame().size()))
                    .replace("<timer>", String.valueOf(ListenerJoin.getTimer())))
                    .collect(Collectors.toList());

            scoreboard.add(0, SettingHandler.getString("scoreboards.prefix"));
            Bukkit.getOnlinePlayers().forEach(player -> ScoreboardUtil.unrankedSidebarDisplay(player, scoreboard.toArray(new String[0])));
        } else {
            int j = SettingHandler.getInt("border.timer.start_pvp_timer") - BorderGame.timer;
            if (BorderState.isState(BorderState.GAME_PVP)) {
                j = 0;
            }

            String str2 = simpleDateFormat.get().format(j * 1000);

            double d1 = BorderHandler.getBorder();
            double d2 = BorderHandler.y;

            List<String> scoreboard = SettingHandler.getList("scoreboards.game").stream().map(x -> x
                    .replace("&", "§")
                    .replace("<playersAmount>", String.valueOf(TheBorders.getPlayerInGame().size()))
                    .replace("<gameMode>", BorderGame.isHardMode() ? "§4黑夜" : "§7普通")
                    .replace("<timer>", str2)
                    .replace("<YborderSize>", String.valueOf(d2))
                    .replace("<XZborderSize>", String.valueOf(d1))).collect(Collectors.toList());

            scoreboard.add(0, SettingHandler.getString("scoreboards.prefix"));
            Bukkit.getOnlinePlayers().forEach(player -> ScoreboardUtil.unrankedSidebarDisplay(player, scoreboard.toArray(new String[0])));
        }
    }
}