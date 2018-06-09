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

import java.util.Arrays;
import java.util.List;

public class ListenerJoin implements Listener {

    private static int timer = 0;
    private static List<Integer> timerCycle = Arrays.asList(30, 15, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);

    public ListenerJoin() {
        timer = SettingHandler.getInt("timer.until_start_timer");

        new BukkitRunnable() {

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
                    Bukkit.getScheduler().runTaskLater(TheBorders.getInstance(), () -> deleteLobby(), 40);
                }
            }
        }.runTaskTimer(TheBorders.getInstance(), 0, 20);
    }


    @EventHandler
    public void preJoin(AsyncPlayerPreLoginEvent e) {
        if (!BorderState.isState(BorderState.WAIT)) {
            Player player = Bukkit.getPlayer(e.getUniqueId());
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

    private void deleteLobby() {
        World localWorld = Bukkit.getWorld("world");
        Location localLocation1 = new Location(localWorld, 0.0D, 130.0D, 0.0D);
        Location localLocation2 = new Location(localWorld, 30.0D, 190.0D, 44.0D);

        int i = Math.min(localLocation1.getBlockX(), localLocation2.getBlockX());
        int j = Math.min(localLocation1.getBlockY(), localLocation2.getBlockY());
        int k = Math.min(localLocation1.getBlockZ(), localLocation2.getBlockZ());
        int m = Math.max(localLocation1.getBlockX(), localLocation2.getBlockX());
        int n = Math.max(localLocation1.getBlockY(), localLocation2.getBlockY());
        int i1 = Math.max(localLocation1.getBlockZ(), localLocation2.getBlockZ());

        for (int i2 = i; i2 <= m; i2++) {
            for (int i3 = j; i3 <= n; i3++) {
                for (int i4 = k; i4 <= i1; i4++) {
                    Block localBlock = localWorld.getBlockAt(i2, i3, i4);
                    localBlock.setType(Material.AIR);
                    for (Entity localEntity : localWorld.getEntities()) {
                        if ((localEntity instanceof org.bukkit.entity.Item)) {
                            localEntity.remove();
                        }
                    }
                }
            }
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
}