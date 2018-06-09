package me.skymc.theborder.world.populator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


class NBT_Tag_Int_Array extends NBT_Tag {
    public int size;
    public int[] payload;

    public NBT_Tag_Int_Array(String paramString) {
        super(11, paramString);
    }

    public NBT_Tag_Int_Array(String paramString, int[] paramArrayOfInt) {
        super(8, paramString);
        this.payload = paramArrayOfInt;
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
        this.payload = new int[i];

        for (int j = 0; j < i; j++) {
            try {
                this.payload[j] = paramDataInput.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        int[] arrayOfInt;
        int k = (arrayOfInt = this.payload).length;
        for (int j = 0; j < k; j++) {
            int i = arrayOfInt[j];
            try {
                paramDataOutput.writeInt(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


/* Location:              C:\Users\sky\Desktop\TheBorders .jar!\net\gravenilvec\world\populator\NBT_Tag_Int_Array.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */