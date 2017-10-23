package com.github.pocketkid2.fill.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.pocketkid2.fill.FillPlugin;
import com.github.pocketkid2.fill.utils.Messages;

public class FillCommand implements CommandExecutor {

	private FillPlugin plugin;

	public FillCommand(FillPlugin pl) {
		plugin = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Check for player
		if (!(sender instanceof Player)) {
			sender.sendMessage(Messages.MUST_BE_PLAYER);
			return true;
		}

		// Check for permission
		if (!(sender.hasPermission("ifill.command.fill"))) {
			sender.sendMessage(Messages.NO_PERM);
			return true;
		}

		// Create objects
		Player player = (Player) sender;

		// If there are no arguments, toggle the item in hand
		if (args.length < 1) {
			toggleItemInHand(player);
		} else {
			giveWand(player, args);
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	private void giveWand(Player player, String[] args) {
		try {
			// Create id value from argument
			int id = Integer.parseInt(args[0]);

			// Create object
			ItemStack stack = new ItemStack(id);

			// Create quantity from argument
			int quantity = stack.getMaxStackSize();
			if (args.length > 1) {
				quantity = Integer.parseInt(args[1]);
			}

			// Create datavalue from argument
			Byte datavalue = 0;
			if (args.length > 2) {
				datavalue = Byte.parseByte(args[2]);
			}

			// Re-create stack with new values
			stack = new ItemStack(id, quantity, (short) 0, datavalue);

			// Give name
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(plugin.getWandName());
			stack.setItemMeta(meta);

			// Give to player
			player.getInventory().addItem(stack);
		} catch (NumberFormatException e) {
			player.sendMessage(Messages.NUMBER_FORMAT_ERROR);
			return;
		}
	}

	private void toggleItemInHand(Player player) {
		// Create the object
		ItemStack stack = player.getInventory().getItemInMainHand();

		// Check for item in hand
		if (stack.getType() == Material.AIR) {
			player.sendMessage(Messages.NOTHING_IN_HAND);
			return;
		}

		// Get the item meta
		ItemMeta meta = stack.getItemMeta();

		// Check for custom name
		if (meta.hasDisplayName() && meta.getDisplayName().equals(plugin.getWandName())) {
			// Remove this wand
			meta.setDisplayName("");
			player.sendMessage(Messages.REMOVED_WAND);
		} else {
			// Set this wand
			meta.setDisplayName(plugin.getWandName());
			player.sendMessage(Messages.CREATED_WAND);
		}

		// Put the item meta back in, and put the stack back in the player's
		// hand
		stack.setItemMeta(meta);
		player.getInventory().setItemInMainHand(stack);
	}

}
