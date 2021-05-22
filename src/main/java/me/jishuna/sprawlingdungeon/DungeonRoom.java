package me.jishuna.sprawlingdungeon;

import org.bukkit.util.BoundingBox;

public class DungeonRoom {

	private final BoundingBox boundingBox;

	public DungeonRoom(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

}
