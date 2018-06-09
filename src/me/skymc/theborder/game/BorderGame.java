package me.skymc.theborder.game;

import me.skymc.taboolib.display.ActionUtils;
import me.skymc.taboolib.display.TitleUtils;
import me.skymc.taboolib.particle.EffLib;
import me.skymc.theborder.TheBorders;
import me.skymc.theborder.handler.BorderHandler;
import me.skymc.theborder.handler.GameHandler;
import me.skymc.theborder.handler.SettingHandler;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.stream.Collectors;

public class BorderGame {

    public static int timer = 0;
    public static int task;
    public static boolean hardMode;

    public TheBorders plugin;
    public Random random = new Random();

    public BorderGame(TheBorders paramTheBorders) {
        this.plugin = paramTheBorders;
    }

    public void start() {
        BorderState.setState(BorderState.PREGAME);

        hardMode = random.nextBoolean();
        Bukkit.getWorld("world").setTime(hardMode ? 18000L : 6000L);

        for (Player player : Bukkit.getOnlinePlayers().stream().filter(x -> x.getGameMode() == GameMode.SURVIVAL).collect(Collectors.toList())) {
            player.getInventory().clear();

            Location randomLocation = new Location(Bukkit.getWorld("world"), random.nextInt(150), 140.0D, -random.nextInt(150));
            player.teleport(randomLocation);

            if (hardMode) {
                player.setMaxHealth(60);
                player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            } else {
                player.setMaxHealth(40);
            }

            if (randomLocation.getBlock().getBiome().name().contains("OCEAN")) {
                player.getInventory().setItem(4, new ItemStack(Material.BOAT));
            }

            player.playSound(player.getLocation(), Sound.ENDERDRAGON_DEATH, 1f, 1f);
            player.setHealth(player.getMaxHealth());
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60 * 20, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120 * 20, 2));
            player.setCompassTarget(TheBorders.getSpawnLocation());

            player.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
            player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
            player.getInventory().addItem(new ItemStack(Material.COMPASS));

            TitleUtils.sendTitle(player, SettingHandler.getString("messages.started_game"), hardMode ? "§4黑夜模式" : "§7普通模式", 10, 60, 10);
        }

        task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(this.plugin, () -> {

            BorderGame.timer++;

            if (BorderGame.timer > 0) {
                if (BorderGame.timer < SettingHandler.getInt("timer.damage_protection_timer")) {
                    int i = SettingHandler.getInt("timer.damage_protection_timer") - BorderGame.timer;
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        ActionUtils.send(player, SettingHandler.getString("messages.title.damage_actionbar").replace("<time>", new SimpleDateFormat("mm:ss").format(i * 1000)));
                    }
                }
            }

            if (BorderGame.timer > SettingHandler.getInt("timer.damage_protection_timer")) {
                int i = SettingHandler.getInt("border.timer.start_pvp_timer") - BorderGame.timer;
                if (!BorderState.isState(BorderState.GAME_PVP)) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        ActionUtils.send(player, SettingHandler.getString("messages.title.pvp_actionbar").replace("<time>", new SimpleDateFormat("mm:ss").format(i * 1000)));
                    }
                }
            }

            if (BorderGame.timer == SettingHandler.getInt("timer.damage_protection_timer")) {
                BorderState.setState(BorderState.GAME);
                for (Player player : Bukkit.getOnlinePlayers()) {
                    TitleUtils.sendTitle(player, "", SettingHandler.getString("messages.title.damage_on"), 10, 60, 10);
                }
            }

            if (BorderGame.timer == SettingHandler.getInt("border.timer.start_decreasexz_timer")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    TitleUtils.sendTitle(player, SettingHandler.getString("messages.title.warningPrefix"), SettingHandler.getString("messages.title.borderXZ_decrease"), 10, 60, 10);
                }
            }

            if ((BorderGame.timer > SettingHandler.getInt("border.timer.start_decreasexz_timer")) && (BorderGame.timer > 0)) {
                if (!BorderHandler.startedX) {
                    BorderHandler.decreaseBorder();
                }
            }

            if (BorderGame.timer == SettingHandler.getInt("border.timer.start_pvp_timer")) {
                Bukkit.getWorld("world").setPVP(true);
                BorderState.setState(BorderState.GAME_PVP);

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.playSound(player.getLocation(), Sound.WITHER_SPAWN, 1f, 1f);
                    TitleUtils.sendTitle(player, "", SettingHandler.getString("messages.title.pvp_on"), 10, 60, 10);
                }
            }

            checkBorderY();
            checkGameMode();

            GameHandler.checkWin();
        }, 20L, 20L);
    }

    private void checkGameMode() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getGameMode() == GameMode.SPECTATOR) {
                ActionUtils.send(player, "§f你已被淘汰, 使用 §e/hub §f返回游戏大厅");
                TheBorders.getPlayerInGame().remove(player.getName());
            }
        }
    }

    private void checkBorderY() {
        if (SettingHandler.getBoolean("border.y_border_enable")) {
            if ((BorderHandler.getBorder() <= SettingHandler.getInt("border.timer.start_increase_y_timer")) && (BorderGame.timer > 0)) {
                if (!BorderHandler.startedY) {
                    BorderHandler.startedY = true;
                    Bukkit.getOnlinePlayers().forEach(player -> TitleUtils.sendTitle(player, SettingHandler.getString("messages.title.warningPrefix"), SettingHandler.getString("messages.title.borderY_increase"), 10, 60, 10));
                }

                if (BorderState.getState() == BorderState.GAME) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).forEach(player -> player.damage(TheBorders.getInstance().getConfig().getDouble("border.damage.y_border_damage")));
                        }
                    }.runTask(TheBorders.getInstance());
                }

                if (BorderHandler.y <= SettingHandler.getDouble("border.max_bordery_size")) {
                    BorderHandler.y += 1.0D;
                }

                Bukkit.getOnlinePlayers().forEach(player -> displayBoardY(player, BorderHandler.y));
            }
        }
    }

    private void displayBoardY(Player player, double y) {
        Location locationBase = player.getLocation().clone();
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                Location location = player.getLocation();
                location.setX(locationBase.getX() + i - 25);
                location.setZ(locationBase.getZ() + j - 25);
                location.setY(y);
                EffLib.FLAME.display(0, 0, 0, 0, 10, location, player);
            }
        }
    }

    // *********************************
    //
    //        Getter and Setter
    //
    // *********************************

    public static boolean isHardMode() {
        return hardMode;
    }
}
