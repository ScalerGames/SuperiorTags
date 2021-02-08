package me.scalergames.SuperiorTags;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.scalergames.SuperiorTags.files.Data;
import me.scalergames.SuperiorTags.files.Tags;
import org.bukkit.entity.Player;

public class Placeholders extends PlaceholderExpansion {

    private Main plugin;

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "ScalerGames";
    }

    @Override
    public String getIdentifier() {
        return "superiortags";
    }

    @Override
    public String getRequiredPlugin() {
        return null;
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        if (identifier == null) {
            return "";
        }

        if(identifier.equals("tag")) {
            String data = Data.getDataConfig().getString(player.getUniqueId() + ".tag");
            if (!Tags.getTagsConfig().getConfigurationSection("SuperiorTags.tags.").getKeys(false).contains(data)) {
                return "";
            } else {
                return Tags.getTagsConfig().getString("SuperiorTags.tags." + data + ".tag");
            }

        }

        return "";
    }

}
