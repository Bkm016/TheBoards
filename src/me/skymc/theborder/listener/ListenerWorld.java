package me.skymc.theborder.listener;

import me.skymc.theborder.handler.SettingHandler;
import me.skymc.theborder.world.populator.PopulatorLobby;
import me.skymc.theborder.world.populator.PopulatorNether;
import me.skymc.theborder.world.populator.PopulatorOre;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;

public class ListenerWorld implements org.bukkit.event.Listener {

    @EventHandler
    public void onPortalCreate(PortalCreateEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void loadWorld(WorldLoadEvent e) {
        World world = e.getWorld();
        world.setPVP(false);
        world.setGameRuleValue("naturalRegeneration", "false");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setTime(6000L);
        world.setThundering(false);

        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setCenter(0.0D, 0.0D);
        worldBorder.setSize(SettingHandler.getDouble("border.default_border_xz_size"));
        worldBorder.setWarningDistance(SettingHandler.getInt("border.damage.xz_warning_distance"));
        worldBorder.setDamageAmount(SettingHandler.getDouble("border.damage.xz_border_damage"));
    }

    @EventHandler
    public void onWorldInit(WorldInitEvent e) {
        for (org.bukkit.generator.BlockPopulator localBlockPopulator : e.getWorld().getPopulators()) {
            if ((localBlockPopulator instanceof PopulatorOre)) {
                return;
            }
            if ((localBlockPopulator instanceof PopulatorNether)) {
                return;
            }
            if ((localBlockPopulator instanceof PopulatorLobby)) {
                return;
            }
        }
        if (e.getWorld().getEnvironment() == World.Environment.NORMAL) {
            e.getWorld().getPopulators().add(new PopulatorOre());
            e.getWorld().getPopulators().add(new PopulatorNether());
            e.getWorld().getPopulators().add(new PopulatorLobby());
        }
    }
}