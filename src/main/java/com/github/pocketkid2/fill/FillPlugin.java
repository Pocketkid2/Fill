package com.github.pocketkid2.fill;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.pocketkid2.fill.commands.FillCommand;
import com.github.pocketkid2.fill.listeners.FillListener;

public class FillPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		// Register command
		getCommand("fill").setExecutor(new FillCommand(this));

		// Register listener
		getServer().getPluginManager().registerEvents(new FillListener(this), this);

		// Log status
		getLogger().info("Done!");
	}

	@Override
	public void onDisable() {
		// Log status
		getLogger().info("Done!");
	}

	public String getWandName() {
		return ChatColor.GREEN + "Fill Wand (Right click to use)";
	}

	public void fill(Block block, ItemStack stack) {
		// Strip wand name
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("");
		stack.setItemMeta(meta);

		// Fill all slots
		Inventory inv = ((InventoryHolder) block.getState()).getInventory();
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, stack);
		}
	}
}
