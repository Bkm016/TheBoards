package me.skymc.theborder.world.populator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


class NBT_Tag_Byte_Array extends NBT_Tag {
    public int size;
    public byte[] payload;

    public NBT_Tag_Byte_Array(String paramString) {
        super(7, paramString);
    }

    public NBT_Tag_Byte_Array(String paramString, byte[] paramArrayOfByte) {
        super(8, paramString);
        this.payload = paramArrayOfByte;
    }

    @Override
    public void readTagPayload(DataInput paramDataInput) {
        int i = 0;
        try {
            i = paramDataInput.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.size = i;
        this.payload = new byte[i];

        try {
            paramDataInput.readFully(this.payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeTag(DataOutput paramDataOutput) {
        try {
            paramDataOutput.write(this.id);
            paramDataOutput.writeUTF(this.name);
            paramDataOutput.writeInt(this.size);
        } catch (Exception ignored) {
        }
        writePayload(paramDataOutput);
    }

    @Override
    public void writePayload(DataOutput paramDataOutput) {
        byte[] arrayOfByte;
        int k = (arrayOfByte = this.payload).length;
        for (int j = 0; j < k; j++) {
            int i = arrayOfByte[j];
            try {
                paramDataOutput.writeByte(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


/* Location:              C:\Users\sky\Desktop\TheBorders .jar!\net\gravenilvec\world\populator\NBT_Tag_Byte_Array.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */