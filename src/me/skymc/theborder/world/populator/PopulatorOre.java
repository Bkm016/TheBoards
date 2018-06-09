package me.skymc.theborder.world.populator;

import me.skymc.theborder.handler.SettingHandler;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;


public class PopulatorOre extends BlockPopulator {

    private static final int[] iterations = {5, 10, 8, 4, 4, 2};
    private static final Material[] type = {Material.REDSTONE_ORE, Material.IRON_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE, Material.OBSIDIAN};
    private static final int[] maxHeight = {64, 64, 64, 64, 64, 64};
    private static int amounts = SettingHandler.getInt("blockGeneration.floor_blocks_amount");
    private static final int[] amount = {amounts, amounts, amounts, amounts, amounts, amounts};

    @Override
    public void populate(World paramWorld, Random paramRandom, Chunk paramChunk) {
        for (int i = 0; i < type.length; i++) {
            for (int j = 0; j < iterations[i]; j++) {
                makeOres(paramChunk, paramRandom, paramRandom.nextInt(16), paramRandom.nextInt(maxHeight[i]), paramRandom.nextInt(16), amount[i], type[i]);
            }
        }
    }

    private static void makeOres(Chunk paramChunk, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Material paramMaterial) {
        for (int i = 0; i < paramInt4; i++) {
            int j = paramInt1 + paramRandom.nextInt(paramInt4 / 2) - paramInt4 / 4;
            int k = paramInt2 + paramRandom.nextInt(paramInt4 / 4) - paramInt4 / 8;
            int m = paramInt3 + paramRandom.nextInt(paramInt4 / 2) - paramInt4 / 4;
            j &= 0xF;
            m &= 0xF;
            if ((k <= 127) && (k >= 0)) {

                Block localBlock = paramChunk.getBlock(j, k, m);
                if (localBlock.getType() == Material.STONE) {
                    localBlock.setType(paramMaterial, false);
                }
            }
        }
    }
}


/* Location:              C:\Users\sky\Desktop\TheBorders .jar!\net\gravenilvec\world\populator\PopulatorOre.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */