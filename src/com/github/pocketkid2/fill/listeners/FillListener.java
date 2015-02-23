package com.github.pocketkid2.fill.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.github.pocketkid2.fill.FillPlugin;

public class FillListener implements Listener {

	// Main class reference
	public FillPlugin plugin;

	// Constructor
	public FillListener(FillPlugin pl) {
		plugin = pl;
	}

	// Event method
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		// Check for right-click block
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			// Check that an item and block exist
			if (event.hasBlock() && event.hasItem()) {
				// Create item and block objects
				Block block = event.getClickedBlock();
				ItemStack item = event.getItem();
				Player player = event.getPlayer();

				// Return if the player's hand is empty
				if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
					return;
				}

				// Check for wand
				if (item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(plugin.getWandName())) {
					// Check for container block
					if (block.getState() instanceof InventoryHolder) {
						// pass block to main class method
						plugin.fill(block, item.clone());
						// tell the player we did it
						player.sendMessage(ChatColor.AQUA + "Filled container!");
						// Prevent us from opening the inventory
						event.setCancelled(true);
					}
				}
			}
		}
	}
}
