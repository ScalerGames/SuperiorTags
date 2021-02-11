package me.scalergames.SuperiorTags;

import me.scalergames.SuperiorTags.commands.TagsCMD;
import me.scalergames.SuperiorTags.commands.TagsCompleter;
import me.scalergames.SuperiorTags.commands.test;
import me.scalergames.SuperiorTags.files.Data;
import me.scalergames.SuperiorTags.files.Tags;
import me.scalergames.SuperiorTags.files.Gui;
import me.scalergames.SuperiorTags.listener.MenuListener;
import me.scalergames.SuperiorTags.listener.OnJoin;
import me.scalergames.SuperiorTags.menu.InvListener;
import me.scalergames.SuperiorTags.menu.MenuHandler;
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
    public Gui gui;


    @Override
    public void onEnable() {
        plugin = this;
        this.tags = new Tags(this);
        this.data = new Data(this);
        this.gui = new Gui(this);
        Tags.saveDefaultTags();
        Data.saveDefaultData();
        Gui.saveDefaultGui();
        enableConfig();
        tagsCount();
        enableCommands();
        enableListeners();
        pluginCheck();
        enablelogger();
        MenuHandler.guiCheck();
    }

    //--- Enable Commands ---

    public void enableCommands() {
        getCommand("tags").setExecutor((CommandExecutor)new TagsCMD());
        getCommand("tags").setTabCompleter((TabCompleter)new TagsCompleter());
        getCommand("test").setExecutor((CommandExecutor)new test());
    }

    //-----------------------

    //--- Plugin Check ---

    public void pluginCheck() {
        //PlaceholderAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getPluginManager().registerEvents(this, this);
            new Placeholders().register();
            getLogger().info(Color.format("&2Successfully hooked into PlaceholderAPI"));
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is highly recommended!");
        }

        //Head DataBase
        if (Bukkit.getPluginManager().getPlugin("HeadDatabase") != null) {
            Bukkit.getPluginManager().registerEvents(this, this);
            getLogger().info(Color.format("&2Successfully hooked into HeadDatabase"));
        } else {
            getLogger().info(Color.format("&6HeadDatabase was not found"));
        }
    }

    //---------------------

    //--- Enable The Start Up Message ---

    public void enablelogger() {
        getLogger().info(Color.format("&m==============================="));
        String a = getServer().getClass().getPackage().getName();
        String version = a.substring(a.lastIndexOf('.') + 1);
        getLogger().info(Color.format("&2Enabled SuperiorTags Version: " + getDescription().getVersion() + " On Minecraft Version: "
                + version));
        getLogger().info(Color.format("&m==============================="));
    }

    //------------------------------------

    //--- Enable Listeners ---

    public void enableListeners() {
        Bukkit.getPluginManager().registerEvents(new OnJoin(), this);
        //Bukkit.getPluginManager().registerEvents(new InvListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
    }

    //-----------------------

    @Override
    public void onDisable() {

    }

    //--- Tag Count ---

    public void tagsCount() {

        int amount = 0;

        ConfigurationSection tagssection = Tags.getTagsConfig().getConfigurationSection("SuperiorTags.tags");
        for (String tags : tagssection.getKeys(false)) {
            amount++;
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
