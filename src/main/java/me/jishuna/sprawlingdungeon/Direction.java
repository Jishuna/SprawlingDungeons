package me.jishuna.sprawlingdungeon;

public enum Direction {
	NORTH, EAST, SOUTH, WEST, NONE;

	public static Direction getOpposite(Direction dir) {
		switch (dir) {
		case EAST:
			return WEST;
		case NONE:
			return NONE;
		case NORTH:
			return SOUTH;
		case SOUTH:
			return NORTH;
		case WEST:
			return EAST;
		}
		return dir;
	}
}
