package org.yawaflua.sexplugin;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.yawaflua.sexplugin.Utils.Parser;


public class Sex extends JavaPlugin {
    public void onEnable() {
        this.saveDefaultConfig();
        this.getCommand("sex").setExecutor(this);
        this.getLogger().info("§7| ");
        this.getLogger().info("§7| §bSEX???");
        this.getLogger().info("§7| §fDeveloper: §byawaflua");
        this.getLogger().info("§7| §fVersion: §b1.0");
        this.getLogger().info("§7| ");
    }

    public void onDisable() {
        this.getLogger().info("§7| ");
        this.getLogger().info("§7| §bSEX???");
        this.getLogger().info("§7| §fDeveloper: §byawaflua");
        this.getLogger().info("§7| §fVersion: §b1.0");
        this.getLogger().info("§7| §fThnx: §bMellsher & 5OPKA");
        this.getLogger().info("§7| ");
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            String consoleUse = this.getConfig().getString("messages.consoleUse");
            sender.sendMessage(Parser.hex(consoleUse));
            return true;
        } else {
            Player player = (Player)sender;
            String targetName;
            if (args.length != 1) {
                targetName = this.getConfig().getString("messages.use");
                player.sendMessage(Parser.hex(targetName));
                return true;
            } else {
                targetName = args[0];
                Player target = Bukkit.getPlayer(targetName);
                if (target == null) {
                    String notFound = this.getConfig().getString("messages.notFound");
                    player.sendMessage(Parser.hex(notFound));
                    return true;
                } else {
                    double distance = player.getLocation().distance(target.getLocation());
                    String titlePlayer;
                    if (distance > this.getConfig().getDouble("settings.distance")) {
                        titlePlayer = this.getConfig().getString("messages.far").replace("%target%", target.getName());
                        player.sendMessage("Игрок " + target.getName() + " слишком далеко!");
                        return true;
                    } else {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 0));
                        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 0));
                        player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1.0F, 1.0F);
                        target.playSound(target.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1.0F, 1.0F);
                        titlePlayer = this.getConfig().getString("settings.title.player.title").replace("%target%", target.getName());
                        String subtitlePlayer = this.getConfig().getString("settings.title.player.subtitle").replace("%target%", target.getName());
                        player.sendTitle(Parser.hex(titlePlayer), Parser.hex(subtitlePlayer), 0, 40, 0);
                        String titleTarget = this.getConfig().getString("settings.title.target.title").replace("%player%", player.getName());
                        String subtitleTarget = this.getConfig().getString("settings.title.target.subtitle").replace("%player%", player.getName());
                        target.sendTitle(Parser.hex(titleTarget), Parser.hex(subtitleTarget), 0, 40, 0);
                        return true;
                    }
                }
            }
        }
    }
}
