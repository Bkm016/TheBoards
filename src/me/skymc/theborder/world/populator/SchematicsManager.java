package me.skymc.theborder.world.populator;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class SchematicsManager {
    private byte[] blocks;
    private byte[] metadata;
    private short width;
    private short height;
    private short length;

    public void loadSchematic(DataInput paramDataInput) {
        NBT_Tag localNBT_Tag = NBT_Tag.readTag(paramDataInput);
        NBT_Tag_Compound localNBT_Tag_Compound = (NBT_Tag_Compound) localNBT_Tag;

        this.width = ((NBT_Tag_Short) localNBT_Tag_Compound.payload.get("Width")).payload;
        this.height = ((NBT_Tag_Short) localNBT_Tag_Compound.payload.get("Height")).payload;
        this.length = ((NBT_Tag_Short) localNBT_Tag_Compound.payload.get("Length")).payload;

        this.blocks = ((NBT_Tag_Byte_Array) localNBT_Tag_Compound.payload.get("Blocks")).payload;
        this.metadata = ((NBT_Tag_Byte_Array) localNBT_Tag_Compound.payload.get("Data")).payload;
    }

    public void writeUncompressedSchematic(DataOutput paramDataOutput) {
        NBT_Tag_Compound localNBT_Tag_Compound = new NBT_Tag_Compound("Schematic");
        localNBT_Tag_Compound.payload.put("Width", new NBT_Tag_Short("Width", this.width));
        localNBT_Tag_Compound.payload.put("Height", new NBT_Tag_Short("Height", this.height));
        localNBT_Tag_Compound.payload.put("Length", new NBT_Tag_Short("Length", this.length));

        localNBT_Tag_Compound.payload.put("Blocks", new NBT_Tag_Byte_Array("Blocks", this.blocks));
        localNBT_Tag_Compound.payload.put("Data", new NBT_Tag_Byte_Array("Data", this.metadata));
        localNBT_Tag_Compound.writeTag(paramDataOutput);
    }

    public void writeUncompressedSchematic(File paramFile) throws IOException {
        DataOutputStream localDataOutputStream = new DataOutputStream(new FileOutputStream(paramFile));
        writeUncompressedSchematic(localDataOutputStream);
        localDataOutputStream.close();
    }

    public void writeGzipedSchematic(File paramFile) throws IOException {
        DataOutputStream localDataOutputStream = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(paramFile)));
        writeUncompressedSchematic(localDataOutputStream);
        localDataOutputStream.close();
    }

    public void loadGzipedSchematic(InputStream paramInputStream) throws IOException {
        DataInputStream localDataInputStream = new DataInputStream(new GZIPInputStream(paramInputStream));
        loadSchematic(localDataInputStream);
        localDataInputStream.close();
    }

    public void loadUncompressedSchematic(InputStream paramInputStream) throws IOException {
        DataInputStream localDataInputStream = new DataInputStream(paramInputStream);
        loadSchematic(localDataInputStream);
        localDataInputStream.close();
    }

    private int getBlockOffset(int paramInt1, int paramInt2, int paramInt3) {
        return paramInt2 * this.width * this.length + paramInt3 * this.width + paramInt1;
    }

    public byte getBlockIdAt(int paramInt1, int paramInt2, int paramInt3) {
        int i = getBlockOffset(paramInt1, paramInt2, paramInt3);
        if ((i < this.blocks.length) && (i >= 0)) {
            return this.blocks[i];
        }
        return -1;
    }

    public void setBlockIdAt(int paramInt1, int paramInt2, int paramInt3, byte paramByte) {
        int i = getBlockOffset(paramInt1, paramInt2, paramInt3);
        if ((i < this.blocks.length) && (i >= 0)) {
            this.blocks[i] = paramByte;
        }
    }

    public byte getMetadataAt(int paramInt1, int paramInt2, int paramInt3) {
        int i = getBlockOffset(paramInt1, paramInt2, paramInt3);
        if ((i < this.metadata.length) && (i >= 0)) {
            return this.metadata[i];
        }
        return 0;
    }

    public void setMetadataIdAt(int paramInt1, int paramInt2, int paramInt3, byte paramByte) {
        int i = getBlockOffset(paramInt1, paramInt2, paramInt3);
        if ((i < this.metadata.length) && (i >= 0)) {
            this.metadata[i] = paramByte;
        }
    }

    public short getWidth() {
        return this.width;
    }

    public void setWidth(short paramShort) {
        this.width = paramShort;
    }

    public short getHeight() {
        return this.height;
    }

    public void setHeight(short paramShort) {
        this.height = paramShort;
    }

    public short getLength() {
        return this.length;
    }

    public void setLength(short paramShort) {
        this.length = paramShort;
    }

    public byte[] getBlocks() {
        return this.blocks;
    }

    public void setBlocks(byte[] paramArrayOfByte) {
        this.blocks = paramArrayOfByte;
    }

    public byte[] getMetadata() {
        return this.metadata;
    }

    public void setMetadata(byte[] paramArrayOfByte) {
        this.metadata = paramArrayOfByte;
    }
}


/* Location:              C:\Users\sky\Desktop\TheBorders .jar!\net\gravenilvec\world\populator\SchematicsManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */