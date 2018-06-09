package me.skymc.theborder.world.populator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


class NBT_Tag_Long
        extends NBT_Tag {
    public long payload;

    public NBT_Tag_Long(String paramString) {
        super(4, paramString);
    }

    public NBT_Tag_Long(String paramString, Long paramLong) {
        super(8, paramString);
        this.payload = paramLong.longValue();
    }

    @Override
    public void readTagPayload(DataInput paramDataInput) {
        try {
            this.payload = paramDataInput.readLong();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeTag(DataOutput paramDataOutput) {
        try {
            paramDataOutput.write(this.id);
            paramDataOutput.writeUTF(this.name);
            writePayload(paramDataOutput);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void writePayload(DataOutput paramDataOutput) {
        try {
            paramDataOutput.writeLong(this.payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


/* Location:              C:\Users\sky\Desktop\TheBorders .jar!\net\gravenilvec\world\populator\NBT_Tag_Long.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */