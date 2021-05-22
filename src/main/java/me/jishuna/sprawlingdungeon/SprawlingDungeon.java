package me.jishuna.sprawlingdungeon;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

public class SprawlingDungeon extends JavaPlugin {

	private TemplateManager templateManager = new TemplateManager();

	@Override
	public void onEnable() {
		this.templateManager.loadTemplates(new File(this.getDataFolder() + File.separator + "templates"));
		
		getCommand("dungeon").setExecutor(new DungeonCommandExecutor(this));
	}

	public TemplateManager getTemplateManager() {
		return templateManager;
	}

}
