package me.scalergames.SuperiorTags.files;

import me.scalergames.SuperiorTags.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Gui {

    private Main plugin;
    private static FileConfiguration guiConfig = null;
    private static File guiFile = null;

    public Gui(Main plugin) {
        this.plugin = plugin;
        saveDefaultGui();
    }

    public static void reloadGui() {
        if (guiFile == null)
            guiFile = new File(Main.getInstance().getDataFolder(), "gui.yml");

        guiConfig = YamlConfiguration.loadConfiguration(guiFile);

        InputStream defaultGuiStream = Main.getInstance().getResource("gui.yml");
        if (defaultGuiStream != null) {
            YamlConfiguration defaultGuiConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultGuiStream));
            guiConfig.setDefaults(defaultGuiConfig);
        }
    }

    public static FileConfiguration getGuiConfig() {
        if (guiConfig == null)
            reloadGui();

        return guiConfig;
    }

    public static void saveGui() {
        if (guiConfig == null || guiFile == null)
            return;

        try {
            getGuiConfig().save(guiFile);
        } catch (IOException ex) {
            Main.getInstance().getLogger().warning("Could not save config to " + guiFile);
        }
    }

    public static void saveDefaultGui() {
        if (guiFile == null)
            guiFile = new File(Main.getInstance().getDataFolder(), "gui.yml");

        if (!guiFile.exists()) {
            Main.getInstance().saveResource("gui.yml", false);
            getGuiConfig().set("GUI.Title", "&3&lTags Menu");
            getGuiConfig().set("GUI.Tags-Layout.Name", "&3Tag: &r{identifier}");
            getGuiConfig().set("GUI.Tags-Layout.Tag", "{tag}");
            getGuiConfig().set("GUI.Tags-Layout.Equipped", "&aEquipped");
            getGuiConfig().set("GUI.Tags-Layout.Non-Equipped", "&fClick to equip this tag");
            saveGui();
        }
    }

}
