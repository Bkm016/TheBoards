package me.skymc.theborder.world.populator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class NBT_Tag_List
        extends NBT_Tag {
    public byte tag_type;
    public int size;
    public List<NBT_Tag> payload;

    public NBT_Tag_List(String paramString) {
        super(9, paramString);
    }

    public NBT_Tag_List(String paramString, List<NBT_Tag> paramList) {
        super(8, paramString);
        this.payload = paramList;
    }

    @Override
    public void readTagPayload(DataInput paramDataInput) {
        try {
            this.tag_type = paramDataInput.readByte();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = 0;
        try {
            i = paramDataInput.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.size = i;
        this.payload = new ArrayList();

        for (int j = 0; j < i; j++) {
            NBT_Tag localNBT_Tag = NBT_Tag.getNewTag(this.tag_type, "");
            localNBT_Tag.readTagPayload(paramDataInput);
            this.payload.add(localNBT_Tag);
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
        for (NBT_Tag localNBT_Tag : this.payload) {
            localNBT_Tag.writePayload(paramDataOutput);
        }
    }
}


/* Location:              C:\Users\sky\Desktop\TheBorders .jar!\net\gravenilvec\world\populator\NBT_Tag_List.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */