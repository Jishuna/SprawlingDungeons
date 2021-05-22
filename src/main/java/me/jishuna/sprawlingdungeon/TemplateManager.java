package me.jishuna.sprawlingdungeon;

import java.io.File;

import me.jishuna.commonlib.WeightedRandom;

public class TemplateManager {

	private final WeightedRandom<RoomTemplate> templates = new WeightedRandom<>();

	public void loadTemplates(File folder) {
		for (File file : folder.listFiles()) {
			System.out.println(file.getName());
			templates.add(1, new RoomTemplate(file));
		}
	}

	public RoomTemplate getRandomTemplate() {
		return this.templates.poll();
	}

}
