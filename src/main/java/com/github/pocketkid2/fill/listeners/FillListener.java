package com.github.pocketkid2.fill.listeners;

import org.bukkit.Sound;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.pocketkid2.fill.FillPlugin;
import com.github.pocketkid2.fill.utils.Messages;

public class FillListener implements Listener {

	private FillPlugin plugin;

	public FillListener(FillPlugin pl) {
		plugin = pl;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		// Check for item and wand first
		if (event.hasItem() && event.getItem().hasItemMeta()) {

			// Get the item data
			ItemMeta meta = event.getItem().getItemMeta();

			// And check for item name
			if (meta.hasDisplayName() && meta.getDisplayName().equals(plugin.getWandName())) {

				// Check for permission to use the wand
				if (!event.getPlayer().hasPermission("fill.use")) {
					event.getPlayer().sendMessage(Messages.NO_PERM);
					return;
				}

				// Immediately cancel the event and prevent any default behavior from triggering
				event.setCancelled(true);
				event.setUseInteractedBlock(Event.Result.DENY);
				event.setUseItemInHand(Event.Result.DENY);

				// Then check for a block
				if (event.hasBlock() && event.getClickedBlock().getState() instanceof InventoryHolder) {
					// Check that it is enabled in this world
					if (!plugin.isWorldEnabled(event.getPlayer().getWorld().getName())) {
						event.getPlayer().sendMessage(Messages.WORLD_DISABLED);
						return;
					}

					// Pass in the event's items. We must clone the held item
					// to prevent the original item from losing it's wand name
					// when it is removed for the purpose of filling the inventory
					// holder.
					plugin.fill(event.getClickedBlock(), event.getItem().clone());

					// Optionally activate message and sound
					if (plugin.isMessageEnabled()) {
						event.getPlayer().sendMessage(Messages.FILLED);
					}
					if (plugin.isSoundEnabled()) {
						event.getPlayer().playSound(event.getClickedBlock().getLocation(), Sound.ENTITY_ITEM_PICKUP, 10,
								1);
					}
				}
			}
		}
	}
}
