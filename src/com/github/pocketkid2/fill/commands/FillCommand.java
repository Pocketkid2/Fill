package com.github.pocketkid2.fill.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import com.github.pocketkid2.fill.FillPlugin;

public class FillCommand implements CommandExecutor {

	// Main class reference
	public FillPlugin plugin;

	// Extra helper string
	private String item_format_error = ChatColor.RED + "You have to be a player!";

	// Constructor
	public FillCommand(FillPlugin pl) {
		plugin = pl;
	}

	// Command method
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Check for player
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You have to be a player!");
			return true;
		}

		// Check for permission
		if (!(sender.hasPermission("ifill.command"))) {
			sender.sendMessage(item_format_error);
			return true;
		}

		// Create player object
		Player player = (Player) sender;

		// If we have some arguments, parse them into an item
		if (args.length > 0) {
			// Item ID
			int id;
			// Amount/Quantity
			int qty;
			// Data Value
			byte dv;

			// Parse first argument into item ID
			try {
				id = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				player.sendMessage(item_format_error);
				return false;
			}

			// Create item stack
			ItemStack stack = new ItemStack(id);

			// Check for second argument
			if (args.length < 2) {
				// If none use default stack size
				qty = stack.getMaxStackSize();
			} else {
				// Otherwise parse amount
				try {
					qty = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					player.sendMessage(item_format_error);
					return false;
				}
			}
			// Now set the amount
			stack.setAmount(qty);

			// Check for third argument
			if (args.length < 3) {
				// Use default data value of 0
				dv = 0;
			} else {
				// Otherwise parse argument
				try {
					dv = Byte.parseByte(args[2]);
				} catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Illegal item format!");
					return false;
				}
			}
			// Set the data value
			stack.setData(new MaterialData(dv));

			// Give a wand of that
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(plugin.getWandName());
			stack.setItemMeta(meta);
			player.getInventory().addItem(stack);
			player.sendMessage(ChatColor.AQUA + "Gave you a wand!");
		}

		// If we have no command arguments then we are using the wand
		if (args.length == 0) {
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
		}

		return true;
	}

}
