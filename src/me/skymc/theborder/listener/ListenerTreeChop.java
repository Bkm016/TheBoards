package me.skymc.theborder.listener;

import me.skymc.theborder.TheBorders;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;

public class ListenerTreeChop implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getBlock().getType() == Material.LOG || e.getBlock().getType() == Material.LOG_2) {
            handleModeOne(e);
        }
    }

    @SuppressWarnings("deprecation")
    private void handleModeTwo(BlockBreakEvent event) {
        for (Block b : getConnectedLogs(event.getBlock())) {
            b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
            b.setType(Material.AIR);
        }
    }

    private void handleModeOne(BlockBreakEvent event) {
        int counter = 0;
        for (Block b : getConnectedLogs(event.getBlock())) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(TheBorders.getInstance(), () -> {
                b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType());
                b.breakNaturally();
            }, counter * 2);
            counter++;
        }
    }

    private ArrayList<Block> getConnectedLogs(Block block) {
        World world = block.getWorld();
        Block[] adjacent = new Block[9];
        ArrayList<Block> logs = new ArrayList<>();

        adjacent[0] = world.getBlockAt(offset(block.getLocation(), 0, 0, -1));
        adjacent[1] = world.getBlockAt(offset(block.getLocation(), 0, 0, 1));
        adjacent[2] = world.getBlockAt(offset(block.getLocation(), 1, 0, 0));
        adjacent[3] = world.getBlockAt(offset(block.getLocation(), -1, 0, 0));
        adjacent[4] = world.getBlockAt(offset(block.getLocation(), 1, 0, -1));
        adjacent[5] = world.getBlockAt(offset(block.getLocation(), -1, 0, -1));
        adjacent[6] = world.getBlockAt(offset(block.getLocation(), 1, 0, 1));
        adjacent[7] = world.getBlockAt(offset(block.getLocation(), -1, 0, 1));
        adjacent[8] = world.getBlockAt(offset(block.getLocation(), 0, 1, 0));

        for (Block b : adjacent) {
            if (b.getType() == Material.LOG || b.getType() == Material.LOG_2) {
                Block curBlock = b;
                while (curBlock.getType() == Material.LOG || curBlock.getType() == Material.LOG_2) {
                    logs.add(curBlock);
                    curBlock = world.getBlockAt(offset(curBlock.getLocation(), 0, 1, 0));
                }
            }
        }
        return logs;
    }

    public static Location offset(Location original, double offx, double offy, double offz) {
        double newX = original.getX() + offx;
        double newY = original.getY() + offy;
        double newZ = original.getZ() + offz;
        if (newY > 255) {
            newY = 255;
        } else if (newY < 0) {
            newY = 0;
        }
        return new Location(original.getWorld(), newX, newY, newZ);
    }
}