package me.skymc.theborder.listener;

import me.skymc.taboolib.display.TitleUtils;
import me.skymc.theborder.TheBorders;
import me.skymc.theborder.game.BorderState;
import me.skymc.theborder.handler.GameHandler;
import me.skymc.theborder.handler.ScoreboardHandler;
import me.skymc.theborder.handler.SettingHandler;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.List;

public class ListenerJoin implements Listener {

    private static int timer = 0;
    private static List<Integer> timerCycle = Arrays.asList(30, 15, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
    private static BukkitTask gameTask;

    public ListenerJoin() {
        timer = SettingHandler.getInt("timer.until_start_timer");
        gameTask = new BukkitRunnable() {

            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().size() < SettingHandler.getInt("options.min_players")) {
                    setTimer(SettingHandler.getInt("timer.until_start_timer"));
                } else {
                    setTimer(getTimer() - 1);
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.setLevel(timer);
                        player.setExp(1 - (float) ((double) timer / SettingHandler.getInt("timer.until_start_timer")));
                        if (timerCycle.contains(timer)) {
                            TitleUtils.sendTitle(player, "", SettingHandler.getString("messages.starting_game").replace("<time>", String.valueOf(timer)), 10, 20, 10);
                        }
                    }
                }
                if (timer == 0) {
                    cancel();
                    TheBorders.getBorderGame().start();
                    Bukkit.getScheduler().runTaskLater(TheBorders.getInstance(), () -> TheBorders.getInstance().deleteLobby(), 40);
                }
            }
        }.runTaskTimer(TheBorders.getInstance(), 0, 20);
    }


    @EventHandler
    public void preJoin(AsyncPlayerPreLoginEvent e) {
        if (!BorderState.isState(BorderState.WAIT)) {
            Player player = Bukkit.getPlayer(e.getUniqueId());
            if (player == null) {
                return;
            }
            if (!TheBorders.getPlayerInGame().contains(player.getName())) {
                e.setKickMessage("Game Already Start");
                return;
            }
        }
        if (BorderState.isState(BorderState.FINISH)) {
            e.setKickMessage("End !");
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        player.getActivePotionEffects().forEach(x -> player.removePotionEffect(x.getType()));
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.setLevel(0);
        player.teleport(TheBorders.getSpawnLocation());
        player.playSound(player.getLocation(), Sound.WITHER_SPAWN, 1f, 1f);

        ListenerDamage.kills.put(player, 0);

        if ((BorderState.isState(BorderState.WAIT))) {
            if (!TheBorders.getPlayerInGame().contains(player.getName())) {
                TheBorders.getPlayerInGame().add(player.getName());
            }
            e.setJoinMessage(SettingHandler.getString("prefix") + SettingHandler.getString("messages.join_msg").replace("<maxonline>", String.valueOf(Bukkit.getMaxPlayers())).replace("<online>", String.valueOf(TheBorders.getPlayerInGame().size())).replace("<player>", player.getName()));
            player.setGameMode(GameMode.SURVIVAL);
        } else {
            e.setJoinMessage(null);
            player.setGameMode(GameMode.SPECTATOR);
        }
    }

    @EventHandler
    public void quit(PlayerQuitEvent e) {
        e.setQuitMessage(null);

        TheBorders.getPlayerInGame().remove(e.getPlayer().getName());

        if (BorderState.isState(BorderState.GAME) || BorderState.isState(BorderState.GAME_PVP)) {
            GameHandler.checkWin();
        }
    }

    // *********************************
    //
    //        Getter and Setter
    //
    // *********************************

    public static int getTimer() {
        return timer;
    }

    public static void setTimer(int timer) {
        ListenerJoin.timer = timer;
    }

    public static List<Integer> getTimerCycle() {
        return timerCycle;
    }

    public static BukkitTask getGameTask() {
        return gameTask;
    }
}