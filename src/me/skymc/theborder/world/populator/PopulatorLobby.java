package me.skymc.theborder.world.populator;

import me.skymc.theborder.TheBorders;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.generator.BlockPopulator;

import java.io.InputStream;
import java.util.Random;

public class PopulatorLobby extends BlockPopulator {

    public String filename = "Lobby.schematic";

    @Override
    public void populate(World paramWorld, Random paramRandom, Chunk paramChunk) {
        if ((paramChunk.getX() == 0) && (paramChunk.getZ() == 0)) {
            try {
                InputStream localInputStream = TheBorders.getInstance().getClass().getClassLoader().getResourceAsStream(this.filename);
                SchematicsManager localSchematicsManager = new SchematicsManager();
                localSchematicsManager.loadGzipedSchematic(localInputStream);

                int i = localSchematicsManager.getWidth();
                int j = localSchematicsManager.getHeight();
                int k = localSchematicsManager.getLength();

                int m = 139;
                int n = m + j;

                for (int i1 = 0; i1 < i; i1++) {
                    for (int i2 = 0; i2 < k; i2++) {
                        int i3 = i1 + paramChunk.getX() * 16;
                        int i4 = i2 + paramChunk.getZ() * 16;

                        for (int i5 = m; (i5 <= n) && (i5 < 255); i5++) {
                            int i6 = i5 - m;
                            int i7 = localSchematicsManager.getBlockIdAt(i1, i6, i2);
                            byte b = localSchematicsManager.getMetadataAt(i1, i6, i2);

                            if ((i7 == -82) && (paramWorld.getBlockAt(i3, i5, i4) != null)) {
                                paramWorld.getBlockAt(i3, i5, i4).setTypeIdAndData(174, b, true);
                            }

                            if ((i7 == -90) && (paramWorld.getBlockAt(i3, i5, i4) != null)) {
                                paramWorld.getBlockAt(i3, i5, i4).setTypeIdAndData(166, b, true);
                            }

                            if ((i7 == -112) && (paramWorld.getBlockAt(i3, i5, i4) != null)) {
                                paramWorld.getBlockAt(i3, i5, i4).setTypeIdAndData(144, b, true);
                            }

                            if ((i7 > -1) && (paramWorld.getBlockAt(i3, i5, i4) != null)) {
                                paramWorld.getBlockAt(i3, i5, i4).setTypeIdAndData(i7, b, true);
                            }
                        }
                    }
                }


                if (paramWorld.getBlockAt(17, 175, 11).getType() == Material.AIR) {
                    paramWorld.getBlockAt(17, 175, 11).setType(Material.LADDER);
                }
                if (paramWorld.getBlockAt(17, 176, 11).getType() == Material.AIR) {
                    paramWorld.getBlockAt(17, 176, 11).setType(Material.LADDER);
                }
                if (paramWorld.getBlockAt(17, 177, 11).getType() == Material.AIR) {
                    paramWorld.getBlockAt(17, 177, 11).setType(Material.LADDER);
                }
                if (paramWorld.getBlockAt(17, 178, 11).getType() == Material.AIR) {
                    paramWorld.getBlockAt(17, 178, 11).setType(Material.LADDER);
                }
                Location localLocation1 = new Location(paramWorld, 0.0D, 130.0D, 0.0D);
                Location localLocation2 = new Location(paramWorld, 30.0D, 190.0D, 44.0D);
                int i3 = Math.min(localLocation1.getBlockX(), localLocation2.getBlockX());
                int i4 = Math.min(localLocation1.getBlockY(), localLocation2.getBlockY());
                int i5 = Math.min(localLocation1.getBlockZ(), localLocation2.getBlockZ());
                int i6 = Math.max(localLocation1.getBlockX(), localLocation2.getBlockX());
                int i7 = Math.max(localLocation1.getBlockY(), localLocation2.getBlockY());
                int i8 = Math.max(localLocation1.getBlockZ(), localLocation2.getBlockZ());

                for (int i9 = i3; i9 <= i6; i9++) {
                    for (int i10 = i4; i10 <= i7; i10++) {
                        for (int i11 = i5; i11 <= i8; i11++) {
                            for (Entity localEntity : paramWorld.getEntities()) {
                                if ((localEntity instanceof Item)) {
                                    localEntity.remove();
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


/* Location:              C:\Users\sky\Desktop\TheBorders .jar!\net\gravenilvec\world\populator\PopulatorLobby.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */