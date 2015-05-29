package com.github.pocketkid2.fill.utils;

import org.bukkit.ChatColor;

public interface Messages {

	String MUST_BE_PLAYER = ChatColor.RED + "You must be a player!";
	String NO_PERM = ChatColor.RED + "You don't have permission for that!";
	String NOTHING_IN_HAND = ChatColor.RED + "You aren't holding anything in your hand!";
	String REMOVED_WAND = ChatColor.AQUA + "Removed wand!";
	String CREATED_WAND = ChatColor.AQUA + "Created wand!";
	String NUMBER_FORMAT_ERROR = ChatColor.RED + "Number format error; Please make sure you typed whole numbers correctly";
	String FILLED = ChatColor.AQUA + "Filled container!";

}
