package me.scalergames.SuperiorTags.files;

import me.scalergames.SuperiorTags.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class Config {

    static FileConfiguration config;
    static File cFile;

    public static void enableConfig() {

        config = Main.getInstance().getConfig();
        //Add Default
        config.addDefault("Prefix", "&8&l[&3&lSuperiorTags&8&l] ");
        config.addDefault("Title", "Tags Menu");
        config.addDefault("TitleColor", "&3&l");
        config.addDefault("Identifier", "Tag: ");
        config.addDefault("IdentifierColor", "&3");
        config.addDefault("DefaultItem", "PAPER");
        //End Default
        Main.getInstance().saveDefaultConfig();
        cFile = new File(Main.getInstance().getDataFolder(), "config.yml");
        config.options().copyDefaults(true);

    }


}
