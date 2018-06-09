package me.skymc.theborder;

import me.skymc.theborder.game.BorderGame;
import me.skymc.theborder.game.BorderState;
import me.skymc.theborder.handler.ScoreboardHandler;
import me.skymc.theborder.handler.SettingHandler;
import me.skymc.theborder.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

    @Override
    public void onDisable() {
        if (SettingHandler.getBoolean("options.deleteWorldWhenGameRestart")) {
            org.bukkit.Bukkit.unloadWorld("world", false);
            deleteWorld(new File("world"));
        }
    }

    private boolean deleteWorld(File file) {
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