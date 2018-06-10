package me.skymc.theborder.listener;

import me.skymc.theborder.TheBorders;
import me.skymc.theborder.game.BorderState;
import me.skymc.theborder.handler.SettingHandler;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @Author sky
 * @Since 2018-06-09 10:56
 */
public class ListenerPlayer implements Listener {

    @EventHandler
    public void portal(PortalCreateEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getItem() != null && e.getItem().getType().equals(Material.COMPASS)) {
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 1f, 1f);
            e.getPlayer().sendMessage("§6§lHuaJiCheng §8> §7你的坐标: §fX: §e" + e.getPlayer().getLocation().getBlockX() + "§f, Y: §e" + e.getPlayer().getLocation().getBlockY() + "§f, Z: §e" + e.getPlayer().getLocation().getBlockZ());
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().startsWith("/loc")) {
            e.setCancelled(true);
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 1f, 1f);
            e.getPlayer().sendMessage("§6§lHuaJiCheng §8> §7你的坐标: §fX: §e" + e.getPlayer().getLocation().getBlockX() + "§f, Y: §e" + e.getPlayer().getLocation().getBlockY() + "§f, Z: §e" + e.getPlayer().getLocation().getBlockZ());
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        if (e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            e.getRecipients().forEach(player -> player.sendMessage("§7" + e.getPlayer().getName() + ": §f" + e.getMessage()));
        } else {
            e.getRecipients().forEach(player -> player.sendMessage("§8" + e.getPlayer().getName() + ": §7" + e.getMessage()));
        }
    }

    @EventHandler
    public void feed(FoodLevelChangeEvent e) {
        if (BorderState.isState(BorderState.WAIT)) {
            e.setFoodLevel(20);
        }
    }

    @EventHandler
    public void regainHealth(EntityRegainHealthEvent e) {
        if (((e.getEntity() instanceof Player)) && (e.getRegainReason() == EntityRegainHealthEvent.RegainReason.EATING)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void eat(PlayerItemConsumeEvent e) {
        if (e.getItem().getType().equals(Material.GOLDEN_APPLE)) {
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.OBSIDIAN) {
            e.setCancelled(true);
            e.getPlayer().getWorld().playEffect(e.getClickedBlock().getLocation(), Effect.STEP_SOUND, e.getClickedBlock().getType());
            e.getClickedBlock().breakNaturally();
        }
    }

    @EventHandler
    public void room(PlayerMoveEvent paramPlayerMoveEvent) {
        if (BorderState.isState(BorderState.WAIT)) {
            Player localPlayer = paramPlayerMoveEvent.getPlayer();
            if ((localPlayer.getLocation().getBlockY() <= 120) && (SettingHandler.getBoolean("options.waitingRoomTeleport"))) {
                localPlayer.teleport(TheBorders.getSpawnLocation());
            }
        }
    }
}
