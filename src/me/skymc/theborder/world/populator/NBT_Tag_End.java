package me.skymc.theborder.world.populator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


class NBT_Tag_End extends NBT_Tag {
    public NBT_Tag_End(String paramString) {
        super(0, "");
    }

    public NBT_Tag_End(String paramString, int paramInt) {
        super(8, paramString);
    }

    @Override
    public void readTagPayload(DataInput paramDataInput) {
        System.out.println("An error has occoured. An named binary tree tag 'end' has had it's payload read. It doesn't have a payload. Fix your code :D");
    }

    @Override
    public void writeTag(DataOutput paramDataOutput) {
        try {
            paramDataOutput.write(this.id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writePayload(DataOutput paramDataOutput) {
    }
}


/* Location:              C:\Users\sky\Desktop\TheBorders .jar!\net\gravenilvec\world\populator\NBT_Tag_End.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */