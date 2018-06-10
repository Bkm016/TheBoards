package me.skymc.theborder.handler;

import me.skymc.theborder.TheBorders;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;


public class BorderHandler {

    public static double y = 0.0D;
    public static boolean startedX;
    public static boolean startedY;
    public static boolean startedSanctions;
    public static boolean forceStart;

    public static void decreaseBorder() {
        startedX = true;

        WorldBorder localWorldBorder = Bukkit.getWorld("world").getWorldBorder();
        localWorldBorder.setWarningDistance(10);

        new BukkitRunnable() {

            @Override
            public void run() {
                if (localWorldBorder.getSize() > SettingHandler.getDouble("border.min_border_xz_size")) {
                    localWorldBorder.setSize(localWorldBorder.getSize() - 0.025D);
                }
            }
        }.runTaskTimerAsynchronously(TheBorders.getInstance(), 0, 1);
    }

    public static double getBorder() {
        return Math.ceil(Bukkit.getWorld("world").getWorldBorder().getSize());
    }

    public static void setBorder(double paramDouble) {
        for (World localWorld : Bukkit.getWorlds()) {
            WorldBorder localWorldBorder = localWorld.getWorldBorder();
            localWorldBorder.setCenter(0.0D, 0.0D);
            localWorldBorder.setSize(paramDouble);
            localWorldBorder.setWarningDistance(SettingHandler.getInt("border.damage.xz_warning_distance"));
            localWorldBorder.setDamageAmount(SettingHandler.getDouble("border.damage.xz_border_damage"));
        }
    }
}