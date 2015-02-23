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

	public void onEnable() {
		// Register command
		this.getCommand("fill").setExecutor(new FillCommand(this));
		
		// Register Listener
		this.getServer().getPluginManager().registerEvents(new FillListener(this), this);
		
		// Log status
		this.getLogger().info("done!");
	}
	
	public void onDisable() {
		// Log status
		this.getLogger().info("done!");
	}
	
	public String getWandName() {
		// Return the name for all wands
		return ChatColor.GREEN + "Fill Wand (right-click a container)";
	}

	public void fill(Block block, ItemStack item) {
		// Remove item name
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("");
		item.setItemMeta(meta);
		
		// Get container inventory
		Inventory inv = ((InventoryHolder)block.getState()).getInventory();
		
		// Loop through every slot
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, item);
		}
	}
}
