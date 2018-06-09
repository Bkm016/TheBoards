package me.skymc.theborder.world.populator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


class NBT_Tag_Double extends NBT_Tag {
    public double payload;

    public NBT_Tag_Double(String paramString) {
        super(6, paramString);
    }

    public NBT_Tag_Double(String paramString, double paramDouble) {
        super(8, paramString);
        this.payload = paramDouble;
    }

    @Override
    public void readTagPayload(DataInput paramDataInput) {
        try {
            this.payload = paramDataInput.readDouble();
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
            paramDataOutput.writeDouble(this.payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


/* Location:              C:\Users\sky\Desktop\TheBorders .jar!\net\gravenilvec\world\populator\NBT_Tag_Double.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */