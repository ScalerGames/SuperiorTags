package me.scalergames.SuperiorTags;

import me.scalergames.SuperiorTags.commands.TagsCMD;
import me.scalergames.SuperiorTags.commands.TagsCompleter;
import me.scalergames.SuperiorTags.files.Config;
import me.scalergames.SuperiorTags.files.Data;
import me.scalergames.SuperiorTags.files.Tags;
import me.scalergames.SuperiorTags.listener.OnJoin;
import me.scalergames.SuperiorTags.menu.InvListener;
import me.scalergames.SuperiorTags.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import static me.scalergames.SuperiorTags.files.Config.enableConfig;


public class Main extends JavaPlugin implements Listener {

    private static Main plugin;

    public Tags tags;
    public Data data;


    @Override
    public void onEnable() {
        plugin = this;
        enableConfig();
        getCommand("tags").setExecutor((CommandExecutor)new TagsCMD());
        getCommand("tags").setTabCompleter((TabCompleter)new TagsCompleter());
        this.tags = new Tags(this);
        this.data = new Data(this);
        Tags.saveDefaultTags();
        Data.saveDefaultData();
        getLogger().info(Color.format("&m==============================="));
        String a = getServer().getClass().getPackage().getName();
        String version = a.substring(a.lastIndexOf('.') + 1);
        getLogger().info(Color.format("&2Enabled SuperiorTags Version: " + getDescription().getVersion() + " On Minecraft Version: "
                + version));
        Bukkit.getPluginManager().registerEvents(new OnJoin(), this);
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getPluginManager().registerEvents(this, this);
            new Placeholders().register();
            getLogger().info(Color.format("&2Successfully hooked into PlaceholderAPI"));
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is highly recommended!");
        }
        Bukkit.getPluginManager().registerEvents(new InvListener(), this);
        enableConfig();
        tagsCount();
        getLogger().info(Color.format("&m==============================="));
    }

    @Override
    public void onDisable() {

    }

    public void tagsCount() {

        int amount = 0;

        ConfigurationSection tagssection = Tags.getTagsConfig().getConfigurationSection("SuperiorTags.tags");
        for (String tags : tagssection.getKeys(false)) {
            amount++;
            Data.getDataConfig().set("TagsCount", amount);
            Data.saveData();
        }

        if (amount == 0) {
            getLogger().info(Color.format("&cCould not find any tags."));
        }
        else if (amount == 1) {
            getLogger().info(Color.format("&2Successfully loaded " + amount + " tag"));
        }
        else if (amount >= 2) {
            getLogger().info(Color.format("&2Successfully loaded " + amount + " tags"));
        }

    }

    public static Main getInstance() {
        return plugin;
    }
}
