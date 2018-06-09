package me.skymc.theborder.listener;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ListenerRecipe implements Listener {

    @EventHandler
    public void changeCraft(PrepareItemCraftEvent e) {
        if ((e.getInventory() instanceof CraftingInventory)) {
            CraftingInventory localCraftingInventory = e.getInventory();

            Material localMaterial = localCraftingInventory.getResult().getType();

            if ((localMaterial == Material.WOOD_AXE) || (localMaterial == Material.STONE_AXE)) {
                localCraftingInventory.setResult(createItemStack(Material.IRON_AXE, Enchantment.DIG_SPEED, 3));
            }

            if ((localMaterial == Material.WOOD_PICKAXE) || (localMaterial == Material.STONE_PICKAXE)) {
                localCraftingInventory.setResult(createItemStack(Material.IRON_PICKAXE, Enchantment.DIG_SPEED, 3));
            }

            if ((localMaterial == Material.WOOD_SPADE) || (localMaterial == Material.STONE_SPADE)) {
                localCraftingInventory.setResult(createItemStack(Material.IRON_SPADE, Enchantment.DIG_SPEED, 3));
            }

            if (localMaterial == Material.IRON_AXE) {
                localCraftingInventory.setResult(createItemStack(Material.DIAMOND_AXE, Enchantment.DIG_SPEED, 5));
            }

            if (localMaterial == Material.IRON_PICKAXE) {
                localCraftingInventory.setResult(createItemStack(Material.DIAMOND_PICKAXE, Enchantment.DIG_SPEED, 5));
            }

            if (localMaterial == Material.IRON_SPADE) {
                localCraftingInventory.setResult(createItemStack(Material.DIAMOND_SPADE, Enchantment.DIG_SPEED, 5));
            }
        }
    }

    private ItemStack createItemStack(org.bukkit.Material material, Enchantment enchant, int amount) {
        ItemStack localItemStack = new ItemStack(material, 1);
        ItemMeta localItemMeta = localItemStack.getItemMeta();
        localItemMeta.addEnchant(enchant, amount, true);
        localItemMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        localItemStack.setItemMeta(localItemMeta);
        return localItemStack;
    }
}
