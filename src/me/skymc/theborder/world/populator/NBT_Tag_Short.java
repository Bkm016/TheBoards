package me.skymc.theborder.world.populator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


class NBT_Tag_Short
        extends NBT_Tag {
    public short payload;

    public NBT_Tag_Short(String paramString) {
        super(2, paramString);
    }

    public NBT_Tag_Short(String paramString, short paramShort) {
        super(8, paramString);
        this.payload = paramShort;
    }

    @Override
    public void readTagPayload(DataInput paramDataInput) {
        try {
            this.payload = paramDataInput.readShort();
        } catch (IOException ignored) {
        }
    }

    @Override
    public void writeTag(DataOutput paramDataOutput) {
        try {
            paramDataOutput.write(this.id);
        } catch (IOException ignored) {
        }
        try {
            paramDataOutput.writeUTF(this.name);
        } catch (IOException ignored) {
        }
        writePayload(paramDataOutput);
    }

    @Override
    public void writePayload(DataOutput paramDataOutput) {
        try {
            paramDataOutput.writeShort(this.payload);
        } catch (IOException ignored) {
        }
    }
}


/* Location:              C:\Users\sky\Desktop\TheBorders .jar!\net\gravenilvec\world\populator\NBT_Tag_Short.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */