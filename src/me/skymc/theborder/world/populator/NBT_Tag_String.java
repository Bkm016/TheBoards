package me.skymc.theborder.world.populator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


class NBT_Tag_String
        extends NBT_Tag {
    public String payload;

    public NBT_Tag_String(String paramString) {
        super(8, paramString);
    }

    public NBT_Tag_String(String paramString1, String paramString2) {
        super(8, paramString1);
        this.payload = paramString2;
    }

    @Override
    public void readTagPayload(DataInput paramDataInput) {
        try {
            this.payload = paramDataInput.readUTF();
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
            paramDataOutput.writeUTF(this.payload);
        } catch (IOException ignored) {
        }
    }

    public String getPayload() {
        return this.payload;
    }
}


/* Location:              C:\Users\sky\Desktop\TheBorders .jar!\net\gravenilvec\world\populator\NBT_Tag_String.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */