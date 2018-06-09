package me.skymc.theborder.handler;

import me.skymc.theborder.TheBorders;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;


public class SettingHandler {

    public static FileConfiguration config = TheBorders.getInstance().getConfig();

    public static int getInt(String paramString) {
        return config.getInt(paramString);
    }

    public static String getString(String paramString) {
        return config.getString(paramString).replace("&", "§");
    }

    public static boolean getBoolean(String paramString) {
        return config.getBoolean(paramString);
    }

    public static double getDouble(String paramString) {
        return config.getDouble(paramString);
    }

    public static List<String> getList(String paramString) {
        return config.getStringList(paramString);
    }
}
