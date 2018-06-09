package me.skymc.theborder.world.populator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


class NBT_Tag_Byte extends NBT_Tag {

    public byte payload;

    public NBT_Tag_Byte(String paramString) {
        super(1, paramString);
    }

    public NBT_Tag_Byte(String paramString, byte paramByte) {
        super(8, paramString);
        this.payload = paramByte;
    }

    @Override
    public void readTagPayload(DataInput paramDataInput) {
        try {
            this.payload = paramDataInput.readByte();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeTag(DataOutput paramDataOutput) {
        try {
            paramDataOutput.write(this.id);
            paramDataOutput.writeUTF(this.name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        writePayload(paramDataOutput);
    }

    @Override
    public void writePayload(DataOutput paramDataOutput) {
        try {
            paramDataOutput.write(this.payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


/* Location:              C:\Users\sky\Desktop\TheBorders .jar!\net\gravenilvec\world\populator\NBT_Tag_Byte.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */