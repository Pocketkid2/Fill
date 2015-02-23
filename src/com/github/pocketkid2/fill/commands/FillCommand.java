package com.github.pocketkid2.fill.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.pocketkid2.fill.FillPlugin;

public class FillCommand implements CommandExecutor {
	
	// Main class reference
	public FillPlugin plugin;
	
	// Constructor
	public FillCommand(FillPlugin pl) {
		plugin = pl;
	}

	// Command method
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Check for player
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You have to be a player!");
			return true;
		}
		
		// Check for permission
		if (!(sender.hasPermission("ifill.command"))) {
			sender.sendMessage(ChatColor.RED + "You can't do that!");
			return true;
		}
		
		// Create player object
		Player player = (Player) sender;
		
		// Check for item
		if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
			player.sendMessage(ChatColor.RED + "You don't have an item!");
			return true;
		}
		
		// Create item objects
		ItemStack stack = player.getItemInHand();
		ItemMeta meta = stack.getItemMeta();
		
		// Check for wand
		if (meta.hasDisplayName() && meta.getDisplayName().equalsIgnoreCase(plugin.getWandName())) {
			// remove it
			meta.setDisplayName("");
			player.sendMessage(ChatColor.AQUA + "Removed wand!");
		} else {
			// make it
			meta.setDisplayName(plugin.getWandName());
			player.sendMessage(ChatColor.AQUA + "Created wand!");
		}
		
		// Clean things up
		stack.setItemMeta(meta);
		player.setItemInHand(stack);
		
		return true;
	}

}
