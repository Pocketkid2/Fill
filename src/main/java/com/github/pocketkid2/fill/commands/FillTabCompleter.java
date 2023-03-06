package com.github.pocketkid2.fill.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class FillTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		
		// If we're typing the first argument
		if (args.length == 1) {
			
			// Look for a match
			List<String> materials = Arrays.asList(Material.values()).stream().map((m) -> m.name()).collect(Collectors.toList());
			List<String> matches = new ArrayList<String>();
			StringUtil.copyPartialMatches(args[0], materials, matches);
			Collections.sort(matches);
			return matches;
		} else {
			return null;
		}
	}

}
