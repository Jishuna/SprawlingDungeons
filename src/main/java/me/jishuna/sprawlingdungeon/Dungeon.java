package me.jishuna.sprawlingdungeon;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.WorldServer;

public class Dungeon {
	private final Random rand = new Random();

	private final Set<DungeonRoom> rooms = new HashSet<>();
	private final Queue<Temp> queue = new ArrayDeque<>();
	
	private int size;

	public Dungeon(Location location, SprawlingDungeon plugin, int size) {
		this.size = size;
		placeRoomsRecursivly(location, plugin, 0, Direction.NONE);

		Bukkit.broadcastMessage("Placing " + rooms.size() + " rooms.");

		Bukkit.getScheduler().runTaskTimer(plugin, task -> {
			if (this.queue.isEmpty()) {
				Bukkit.broadcastMessage("Placement complete.");
				task.cancel();
				return;
			}

			Temp temp = this.queue.poll();

			temp.execute();

		}, 0, 4);
	}

	private void placeRoomsRecursivly(Location location, SprawlingDungeon plugin, int depth, Direction dir) {
		WorldServer world = ((CraftWorld) location.getWorld()).getHandle();

		Direction opposite = Direction.getOpposite(dir);

		RoomTemplate template = null;
		boolean valid = false;
		int tries = 0;
		BoundingBox box = null;
		Location newLocation = null;

		do {
			tries++;
			template = plugin.getTemplateManager().getRandomTemplate();

			valid = opposite == Direction.NONE
					|| (template.getExits(opposite) != null && !template.getExits(opposite).isEmpty());

			if (!valid)
				continue;

			newLocation = location.clone();

			if (opposite != Direction.NONE) {
				List<Vector> exits = new ArrayList<Vector>(template.getExits(opposite));

				newLocation.subtract(exits.get(rand.nextInt(exits.size())));
			}

			Location oppositeLocation = newLocation.clone().add(template.getLength(), template.getHeight(),
					template.getWidth());

			box = BoundingBox.of(newLocation, oppositeLocation);

			for (DungeonRoom room : this.rooms) {
				if (box.overlaps(room.getBoundingBox())) {
					valid = false;
					break;
				}
			}

		} while (tries < 3 && !valid);

		if (!valid)
			return;

		DungeonRoom room = new DungeonRoom(box);

		this.rooms.add(room);

		queue.add(new Temp(world,
				new BlockPosition(newLocation.getBlockX(), newLocation.getBlockY(), newLocation.getBlockZ()),
				template.getStructure(), room));

		if (depth < size) {

			final int newDepth = depth + 1;

			for (Entry<Direction, Vector> exit : template.getExits().entries()) {
				Direction direction = exit.getKey();

				if (direction == Direction.getOpposite(dir))
					continue;

				Vector offset = exit.getValue();

				switch (direction) {
				case EAST:
					placeRoomsRecursivly(newLocation.clone().add(offset), plugin, newDepth, direction);
					break;
				case NORTH:
					placeRoomsRecursivly(newLocation.clone().add(offset), plugin, newDepth, direction);
					break;
				case SOUTH:
					placeRoomsRecursivly(newLocation.clone().add(offset), plugin, newDepth, direction);
					break;
				case WEST:
					placeRoomsRecursivly(newLocation.clone().add(offset), plugin, newDepth, direction);
					break;
				default:
					break;
				}
			}
		}
	}

}
