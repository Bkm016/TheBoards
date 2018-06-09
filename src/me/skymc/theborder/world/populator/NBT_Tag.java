package me.skymc.theborder.world.populator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


abstract class NBT_Tag {
    byte id;
    String name;

    public static NBT_Tag getNewTag(int paramInt, String paramString) {
        switch (paramInt) {
            case 0:
                return new NBT_Tag_End("");
            case 1:
                return new NBT_Tag_Byte(paramString);
            case 2:
                return new NBT_Tag_Short(paramString);
            case 3:
                return new NBT_Tag_Int(paramString);
            case 4:
                return new NBT_Tag_Long(paramString);
            case 5:
                return new NBT_Tag_Float(paramString);
            case 6:
                return new NBT_Tag_Double(paramString);
            case 7:
                return new NBT_Tag_Byte_Array(paramString);
            case 8:
                return new NBT_Tag_String(paramString);
            case 9:
                return new NBT_Tag_List(paramString);
            case 10:
                return new NBT_Tag_Compound(paramString);
            case 11:
                return new NBT_Tag_Int_Array(paramString);
            default:
        }
        return null;
    }

    public static NBT_Tag readTag(DataInput paramDataInput) {
        int i = 0;
        try {
            i = paramDataInput.readByte();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (i == 0) {
            return new NBT_Tag_End("");
        }
        String str = null;
        try {
            str = paramDataInput.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NBT_Tag localNBT_Tag = getNewTag(i, str);
        localNBT_Tag.readTagPayload(paramDataInput);
        return localNBT_Tag;
    }


    public NBT_Tag(String paramString) {
        this.id = 0;
        this.name = paramString;
    }

    protected NBT_Tag(int paramInt, String paramString) {
        this.id = ((byte) paramInt);
        this.name = paramString;
    }

    public abstract void readTagPayload(DataInput paramDataInput);

    public abstract void writeTag(DataOutput paramDataOutput);

    public abstract void writePayload(DataOutput paramDataOutput);
}


/* Location:              C:\Users\sky\Desktop\TheBorders .jar!\net\gravenilvec\world\populator\NBT_Tag.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */