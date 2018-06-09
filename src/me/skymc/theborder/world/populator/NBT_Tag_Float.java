package me.skymc.theborder.world.populator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


class NBT_Tag_Float
        extends NBT_Tag {
    public float payload;

    public NBT_Tag_Float(String paramString) {
        super(5, paramString);
    }

    public NBT_Tag_Float(String paramString, float paramFloat) {
        super(8, paramString);
        this.payload = paramFloat;
    }

    @Override
    public void readTagPayload(DataInput paramDataInput) {
        try {
            this.payload = paramDataInput.readFloat();
        } catch (IOException ignored) {
        }
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
            paramDataOutput.writeFloat(this.payload);
        } catch (IOException ignored) {
        }
    }
}
