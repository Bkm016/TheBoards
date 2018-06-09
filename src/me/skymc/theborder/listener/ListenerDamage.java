package me.skymc.theborder.listener;

import me.skymc.theborder.TheBorders;
import me.skymc.theborder.handler.GameHandler;
import me.skymc.theborder.game.BorderState;
import me.skymc.theborder.handler.SettingHandler;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class ListenerDamage implements Listener {

    public static HashMap<Player, Integer> kills = new HashMap();

    @EventHandler
    public void fakeDeath(PlayerDeathEvent e) {
        Location location = e.getEntity().getLocation().clone();

        e.getEntity().playSound(e.getEntity().getLocation(), org.bukkit.Sound.AMBIENCE_THUNDER, 1f, 1f);
        e.setDeathMessage(SettingHandler.getString("prefix") + SettingHandler.getString("messages.death_message").replace("<name>", e.getEntity().getName()));

        new BukkitRunnable() {

            @Override
            public void run() {
                e.getEntity().spigot().respawn();
                e.getEntity().teleport(location);
                e.getEntity().setGameMode(GameMode.SPECTATOR);

                TheBorders.getPlayerInGame().remove(e.getEntity().getName());
                GameHandler.checkWin();
            }
        }.runTask(TheBorders.getInstance());
    }

    @EventHandler
    public void dropMobs(EntityDeathEvent e) {
        Location localLocation = e.getEntity().getLocation();
        String str1 = e.getEntity().getType().name();

        if (TheBorders.getInstance().getConfig().contains("mobDrops.mob" + str1 + ".drops")) {
            for (String str2 : SettingHandler.getList("mobDrops.mob" + str1 + ".drops")) {
                String[] arrayOfString1 = str2.split(",");
                String str3 = arrayOfString1[0];
                String str4 = arrayOfString1[1];
                short s = 0;

                if (str3.contains(":")) {
                    String[] arrayOfString2 = str3.split(":");
                    str3 = arrayOfString2[0];
                    s = Byte.valueOf(arrayOfString2[1]);
                }

                e.getDrops().clear();
                localLocation.getWorld().dropItemNaturally(localLocation, new ItemStack(Material.getMaterial(str3), Integer.valueOf(str4), s));
            }
        }
    }


    @EventHandler
    public void fakeDamageDeath(EntityDamageEvent e) {
        if (((BorderState.isState(BorderState.WAIT)) || (BorderState.isState(BorderState.PREGAME))) && ((e.getEntity() instanceof Player))) {
            e.setCancelled(true);
        }

        if ((SettingHandler.getBoolean("options.instantKillPassiveMobs")) && (
                ((e.getEntity() instanceof Bat)) ||
                        ((e.getEntity() instanceof Chicken)) ||
                        ((e.getEntity() instanceof Cow)) ||
                        ((e.getEntity() instanceof org.bukkit.entity.Pig)) ||
                        ((e.getEntity() instanceof Rabbit)) ||
                        ((e.getEntity() instanceof Sheep)) ||
                        ((e.getEntity() instanceof Squid)))) {

            e.setDamage(100.0D);
        }
    }
}