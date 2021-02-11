package me.scalergames.SuperiorTags.files;

import me.scalergames.SuperiorTags.Main;
import me.scalergames.SuperiorTags.utils.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Tags {

    private Main plugin;
    private static FileConfiguration tagsConfig = null;
    private static File tagsFile = null;

    public Tags(Main plugin) {
        this.plugin = plugin;
        saveDefaultTags();
    }

    public static void reloadTags() {
        if (tagsFile == null)
            tagsFile = new File(Main.getInstance().getDataFolder(), "tags.yml");

        tagsConfig = YamlConfiguration.loadConfiguration(tagsFile);

        InputStream defaultStream = Main.getInstance().getResource("tags.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            tagsConfig.setDefaults(defaultConfig);
        }
    }

    public static FileConfiguration getTagsConfig() {
        if (tagsConfig == null)
            reloadTags();

        return tagsConfig;
    }

    public static void saveTags() {
        if (tagsConfig == null || tagsFile == null)
            return;

        try {
            getTagsConfig().save(tagsFile);
        } catch (IOException ex) {
            Main.getInstance().getLogger().warning("Could not save config to " + tagsFile);
        }
    }

    public static void saveDefaultTags() {
        if (tagsFile == null)
            tagsFile = new File(Main.getInstance().getDataFolder(), "tags.yml");

        if (!tagsFile.exists()) {
            Main.getInstance().saveResource("tags.yml", false);
            Tags.getTagsConfig().set("SuperiorTags.tags.superiortags.tag", "&8&l[&3&lSuperiorTags&8&l]");
            String[] list = {"&3This is the official Superior Tag", "&9superiormc.co.uk"};
            Tags.getTagsConfig().set("SuperiorTags.tags.superiortags.lore", list);
            Tags.getTagsConfig().set("SuperiorTags.tags.superiortags.permission", "superiortags.tag.superiortags");
            Tags.getTagsConfig().set("SuperiorTags.tags.superiortags.item", "PAPER");
            Tags.getTagsConfig().set("SuperiorTags.tags.superiortags.effects", "none");
            String[] enchants = {  "DIG_SPEED:1:HIDE_ENCHANTS"};
            Tags.getTagsConfig().set("SuperiorTags.tags.superiortags.enchantments", enchants);
            Tags.saveTags();
        }
    }

}
