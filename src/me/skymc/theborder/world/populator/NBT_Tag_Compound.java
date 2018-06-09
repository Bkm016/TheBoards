package me.skymc.theborder.world.populator;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.HashMap;
import java.util.Map;


class NBT_Tag_Compound extends NBT_Tag {

    public Map<String, NBT_Tag> payload;

    public NBT_Tag_Compound(String paramString) {
        super(10, paramString);
    }

    public Map<String, NBT_Tag> getPayload() {
        return this.payload;
    }

    public NBT_Tag_Compound(String paramString, Map<String, NBT_Tag> paramMap) {
        super(8, paramString);
        this.payload = paramMap;
    }

    public NBT_Tag_Compound(Map<String, NBT_Tag> paramMap) {
        super(8, "");
        this.payload = paramMap;
    }

    @Override
    public void readTagPayload(DataInput paramDataInput) {
        this.payload = new HashMap();

        NBT_Tag localObject;
        while ((localObject = readTag(paramDataInput)).id != 0) {
            this.payload.put(localObject.name, localObject);
        }
        this.payload.put("__end", new NBT_Tag_End("__end"));
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
        for (String str : this.payload.keySet()) {
            NBT_Tag localNBT_Tag = this.payload.get(str);
            localNBT_Tag.writeTag(paramDataOutput);
        }
    }
}


/* Location:              C:\Users\sky\Desktop\TheBorders .jar!\net\gravenilvec\world\populator\NBT_Tag_Compound.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */