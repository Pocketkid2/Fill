package com.github.pocketkid2.fill.listeners;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.github.pocketkid2.fill.FillPlugin;
import com.github.pocketkid2.fill.utils.Messages;

public class FillListener implements Listener {

	private FillPlugin plugin;

	public FillListener(FillPlugin pl) {
		plugin = pl;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		// Check for block interaction
		if (event.hasBlock() && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			// Check for item
			if (event.hasItem()) {
				// Create first object
				ItemStack stack = event.getItem();

				// Check for wand name
				if (stack.getItemMeta().hasDisplayName() && stack.getItemMeta().getDisplayName().equals(plugin.getWandName())) {
					// Create second object
					Block block = event.getClickedBlock();

					// Check for container block
					if (block.getState() instanceof InventoryHolder) {
						// Fill container (item stack must be cloned to prevent
						// the one being held from changing)
						plugin.fill(block, stack.clone());

						// Cancel any actions and events
						event.setUseInteractedBlock(Event.Result.DENY);
						event.setUseItemInHand(Event.Result.DENY);
						event.setCancelled(true);

						// Send message and/or sound if enabled
						if (plugin.MESSAGE) {
							event.getPlayer().sendMessage(Messages.FILLED);
						}
						if (plugin.SOUND) {
							event.getPlayer().playSound(event.getClickedBlock().getLocation(), Sound.ITEM_PICKUP, 10, 1);
						}
					}
				}
			}
		}
	}
}
