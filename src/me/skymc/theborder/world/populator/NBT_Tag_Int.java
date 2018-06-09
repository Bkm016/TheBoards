package me.skymc.theborder.world.populator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


class NBT_Tag_Int
        extends NBT_Tag {
    public int payload;

    public NBT_Tag_Int(String paramString) {
        super(3, paramString);
    }

    public int getPayload() {
        return this.payload;
    }

    @Override
    public void readTagPayload(DataInput paramDataInput) {
        try {
            this.payload = paramDataInput.readInt();
        } catch (IOException ignored) {
        }
    }

    public NBT_Tag_Int(String paramString, int paramInt) {
        super(8, paramString);
        this.payload = paramInt;
    }

    @Override
    public void writeTag(DataOutput paramDataOutput) {
        try {
            paramDataOutput.write(this.id);
            paramDataOutput.writeUTF(this.name);
        } catch (Exception ignored) {
        }
        writePayload(paramDataOutput);
    }

    @Override
    public void writePayload(DataOutput paramDataOutput) {
        try {
            paramDataOutput.writeInt(this.payload);
        } catch (IOException ignored) {
        }
    }
}


/* Location:              C:\Users\sky\Desktop\TheBorders .jar!\net\gravenilvec\world\populator\NBT_Tag_Int.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */