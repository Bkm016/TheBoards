package me.skymc.theborder;

import me.skymc.theborder.game.BorderGame;
import me.skymc.theborder.game.BorderState;
import me.skymc.theborder.handler.GameHandler;
import me.skymc.theborder.handler.ScoreboardHandler;
import me.skymc.theborder.handler.SettingHandler;
import me.skymc.theborder.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TheBorders extends org.bukkit.plugin.java.JavaPlugin {

    private static List<String> playerInGame = new CopyOnWriteArrayList<>();
    private static TheBorders instance;
    private static Location spawnLocation;
    private static BorderGame borderGame;

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        instance = this;
        borderGame = new BorderGame(this);

        Bukkit.getPluginManager().registerEvents(new ListenerTreeChop(), this);
        Bukkit.getPluginManager().registerEvents(new ListenerWorld(), this);
        Bukkit.getPluginManager().registerEvents(new ListenerDamage(), this);
        Bukkit.getPluginManager().registerEvents(new ListenerRecipe(), this);
        Bukkit.getPluginManager().registerEvents(new ListenerBorder(), this);
        Bukkit.getPluginManager().registerEvents(new ListenerPlayer(), this);
        Bukkit.getPluginManager().registerEvents(new ListenerBlock(), this);
        Bukkit.getPluginManager().registerEvents(new ListenerJoin(), this);
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        new BukkitRunnable() {
            @Override
            public void run() {
                spawnLocation = new Location(Bukkit.getWorld("world"), 12.0D, 165.0D, 13.0D);
                BorderState.setState(BorderState.WAIT);
            }
        }.runTask(this);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (spawnLocation != null) {
                    spawnLocation.getChunk().load();
                }
                ScoreboardHandler.refresh();
            }
        }.runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable() {
        if (SettingHandler.getBoolean("options.deleteWorldWhenGameRestart")) {
            org.bukkit.Bukkit.unloadWorld("world", false);
            deleteWorld(new File("world"));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§7[TheBorders] §f/theborders start §8- §7强制开局");
            sender.sendMessage("§7[TheBorders] §f/theborders reload §8- §7重载插件");
        }
        else if (args[0].equalsIgnoreCase("start")) {
            ListenerJoin.getGameTask().cancel();
            TheBorders.getBorderGame().start();
            Bukkit.getScheduler().runTaskLater(TheBorders.getInstance(), this::deleteLobby, 40);
        }
        else if (args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            sender.sendMessage("reload ok!");
        }
        return true;
    }

    public void bungeeTP(Player paramPlayer, String paramString) {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
        try {
            localDataOutputStream.writeUTF("Connect");
            localDataOutputStream.writeUTF(paramString);
        } catch (IOException ignored) {
        }
        paramPlayer.sendPluginMessage(getInstance(), "BungeeCord", localByteArrayOutputStream.toByteArray());
    }

    public boolean deleteWorld(File file) {
        if (file.exists()) {
            File[] arrayOfFile = file.listFiles();
            for (File anArrayOfFile : arrayOfFile) {
                if (anArrayOfFile.isDirectory()) {
                    deleteWorld(anArrayOfFile);
                } else {
                    anArrayOfFile.delete();
                }
            }
        }
        return file.delete();
    }

    public void deleteLobby() {
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

    public static TheBorders getInstance() {
        return instance;
    }

    public static Location getSpawnLocation() {
        return spawnLocation;
    }

    public static List<String> getPlayerInGame() {
        return playerInGame;
    }

    public static BorderGame getBorderGame() {
        return borderGame;
    }
}