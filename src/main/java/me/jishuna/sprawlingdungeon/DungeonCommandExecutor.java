package me.jishuna.sprawlingdungeon;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DungeonCommandExecutor implements CommandExecutor {

	private final SprawlingDungeon plugin;

	public DungeonCommandExecutor(SprawlingDungeon plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

		Player player = (Player) sender;

		new Dungeon(player.getLocation(), this.plugin, Integer.parseInt(args[0]));
		return true;
	}

}
