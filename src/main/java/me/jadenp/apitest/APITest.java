package me.jadenp.apitest;


import me.jadenp.notbounties.bountyEvents.BountyClaimEvent;
import me.jadenp.notbounties.bountyEvents.BountySetEvent;
import me.jadenp.notbounties.utils.BountyManager;
import me.jadenp.notbounties.utils.Whitelist;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public final class APITest extends JavaPlugin implements Listener, CommandExecutor {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(Bukkit.getServer().getPluginCommand("apitest")).setExecutor(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onBountyClaim(BountyClaimEvent event) {
        if (event.getBounty().getName().equals("Not_Jaden")) {
            Bukkit.broadcastMessage("Bounties cannot be claimed on Not_Jaden");
            event.setCancelled(true);
            return;
        }
        Bukkit.broadcastMessage(event.getKiller().getName() + " Claimed the bounty on " + event.getBounty().getName());
    }

    @EventHandler
    public void onBountySet(BountySetEvent event) {
        Bukkit.broadcastMessage("Total Bounty: " + event.getBounty().getTotalBounty());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equals("apitest")) {
            if (!(sender instanceof Player))
                return true;
            BountyManager.addBounty((Player) sender, 10, new Whitelist(new ArrayList<>(), false));
        }
        return true;
    }


}
