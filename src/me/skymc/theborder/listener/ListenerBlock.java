
package me.skymc.theborder.listener;


import me.skymc.theborder.TheBorders;
import me.skymc.theborder.game.BorderState;
import me.skymc.theborder.handler.SettingHandler;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;


public class ListenerBlock implements Listener {

    private Random random = new Random();

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType().equals(Material.TNT)) {
            e.setCancelled(true);

            TNTPrimed tntPrimed = Bukkit.getWorld("world").spawn(e.getBlock().getLocation().add(0.5, 0, 0.5), TNTPrimed.class);
            tntPrimed.setFuseTicks(40);

            if (!e.getPlayer().isSneaking()) {
                tntPrimed.setVelocity(new Vector(0, 1, 0));
            }

            if (e.getPlayer().getItemInHand().getAmount() == 1) {
                e.getPlayer().setItemInHand(null);
            } else {
                e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() - 1);
            }
        }
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        if (BorderState.isState(BorderState.WAIT)) {
            e.setCancelled(true);
            return;
        }

        if (e.getBlock().getType().equals(Material.STONE)) {
            e.getBlock().setData((byte) 0);
            return;
        }

        Location localLocation = e.getBlock().getLocation();
        String str1 = e.getBlock().getType().name();

        if (TheBorders.getInstance().getConfig().contains("customizableDrops." + str1 + ".drops")) {
            for (String str2 : SettingHandler.getList("customizableDrops." + str1 + ".drops")) {
                String[] arrayOfString1 = str2.split(",");
                String str3 = arrayOfString1[0];
                String str4 = arrayOfString1[1];
                short s = 0;

                if (str3.contains(":")) {
                    String[] arrayOfString2 = str3.split(":");
                    str3 = arrayOfString2[0];
                    s = Byte.valueOf(arrayOfString2[1]);
                }

                e.setCancelled(true);
                e.getBlock().setType(Material.AIR);

                ItemStack itemStack = new ItemStack(Material.getMaterial(str3), Integer.valueOf(str4), s);
                if (arrayOfString1.length > 2) {
                    if (random.nextDouble() > Double.valueOf(arrayOfString1[2])) {
                        return;
                    }
                }

                localLocation.getWorld().dropItemNaturally(localLocation, itemStack);
            }

        }

    }
}
