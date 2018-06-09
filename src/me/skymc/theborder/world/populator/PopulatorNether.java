package me.skymc.theborder.world.populator;

import me.skymc.theborder.TheBorders;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.io.InputStream;
import java.util.Random;

public class PopulatorNether extends BlockPopulator {

    public String filename = "Nether.schematic";

    @Override
    public void populate(World paramWorld, Random paramRandom, Chunk paramChunk) {
        if ((paramChunk.getX() % 20 == 0) && (paramChunk.getZ() % 20 == 0)) {
            try {
                InputStream localInputStream = TheBorders.getInstance().getClass().getClassLoader().getResourceAsStream(this.filename);
                SchematicsManager localSchematicsManager = new SchematicsManager();
                localSchematicsManager.loadGzipedSchematic(localInputStream);

                int i = localSchematicsManager.getWidth();
                int j = localSchematicsManager.getHeight();
                int k = localSchematicsManager.getLength();

                int m = 10;
                int n = m + j;

                int i1 = 0;
                int i2 = 0;
                int i3 = 0;
                int i4 = 0;
                int i5 = 0;
                int i6 = 0;

                for (int i7 = 0; i7 < i; i7++) {
                    for (int i8 = 0; i8 < k; i8++) {
                        int i9 = i7 + paramChunk.getX() * 16;
                        int i10 = i8 + paramChunk.getZ() * 16;

                        for (int i11 = m; (i11 <= n) && (i11 < 255); i11++) {
                            int i12 = i11 - m;
                            int i13 = localSchematicsManager.getBlockIdAt(i7, i12, i8);
                            byte b = localSchematicsManager.getMetadataAt(i7, i12, i8);

                            if (i13 == -103) {
                                paramWorld.getBlockAt(i9, i11, i10).setTypeIdAndData(153, b, true);
                            }
                            if ((i13 > -1) && (paramWorld.getBlockAt(i9, i11, i10) != null)) {
                                paramWorld.getBlockAt(i9, i11, i10).setTypeIdAndData(i13, b, true);
                            }
                            BlockState localBlockState;
                            if (paramWorld.getBlockAt(i9, i11, i10).getType() == Material.MOB_SPAWNER) {
                                localBlockState = paramWorld.getBlockAt(i9, i11, i10).getState();
                                if ((localBlockState instanceof CreatureSpawner)) {
                                    ((CreatureSpawner) localBlockState).setSpawnedType(EntityType.BLAZE);
                                }
                            } else if (paramWorld.getBlockAt(i9, i11, i10).getType() == Material.CHEST) {
                                localBlockState = paramWorld.getBlockAt(i9, i11, i10).getState();
                                if ((localBlockState instanceof Chest)) {
                                    Inventory localInventory = ((Chest) localBlockState).getInventory();
                                    if (i1 == 0) {
                                        localInventory.setItem(7, new ItemStack(Material.DIAMOND, 2));
                                        localInventory.setItem(10, new ItemStack(Material.SUGAR_CANE, 6));
                                        ItemStack localItemStack = new ItemStack(Material.ENCHANTED_BOOK);
                                        EnchantmentStorageMeta localEnchantmentStorageMeta = (EnchantmentStorageMeta) localItemStack.getItemMeta();
                                        localEnchantmentStorageMeta.addStoredEnchant(Enchantment.THORNS, 3, false);
                                        localItemStack.setItemMeta(localEnchantmentStorageMeta);
                                        localInventory.setItem(24, localItemStack);
                                        i1 = 1;
                                    } else if ((i1 != 0) && (i2 == 0)) {
                                        localInventory.setItem(1, new ItemStack(Material.GOLD_INGOT, 3));
                                        localInventory.setItem(14, new ItemStack(Material.IRON_BARDING, 1));
                                        localInventory.setItem(20, new ItemStack(Material.NETHER_STALK, 6));

                                        i2 = 1;
                                    } else if ((i1 != 0) && (i2 != 0) && (i3 == 0)) {
                                        localInventory.setItem(3, new ItemStack(Material.FLINT_AND_STEEL, 1));
                                        localInventory.setItem(15, new ItemStack(Material.SADDLE, 1));
                                        localInventory.setItem(20, new ItemStack(Material.DIAMOND_BARDING, 1));
                                        i3 = 1;
                                    } else if ((i1 != 0) && (i2 != 0) && (i3 != 0) && (i4 == 0)) {
                                        localInventory.setItem(1, new ItemStack(Material.OBSIDIAN, 1));
                                        localInventory.setItem(21, new ItemStack(Material.GOLD_BARDING, 1));
                                        localInventory.setItem(26, new ItemStack(Material.DIAMOND, 1));
                                        i4 = 1;
                                    } else if ((i1 != 0) && (i2 != 0) && (i3 != 0) && (i4 != 0) && (i5 == 0)) {
                                        localInventory.setItem(16, new ItemStack(Material.IRON_INGOT, 8));
                                        localInventory.setItem(20, new ItemStack(Material.GOLD_INGOT, 5));
                                        i5 = 1;
                                    } else if ((i1 != 0) && (i2 != 0) && (i3 != 0) && (i4 != 0) && (i5 != 0) && (i6 == 0)) {
                                        localInventory.setItem(13, new ItemStack(Material.ENDER_PEARL, 1));
                                        i6 = 1;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception localException) {
                System.out.println("Could not read the schematic file");
                localException.printStackTrace();
            }
        }
    }
}


/* Location:              C:\Users\sky\Desktop\TheBorders .jar!\net\gravenilvec\world\populator\PopulatorNether.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */