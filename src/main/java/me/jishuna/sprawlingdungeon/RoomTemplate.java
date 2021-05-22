package me.jishuna.sprawlingdungeon;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

import org.bukkit.util.Vector;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.server.v1_16_R3.DefinedStructure;
import net.minecraft.server.v1_16_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;

public class RoomTemplate {

	private final DefinedStructure structure = new DefinedStructure();
	private int length;
	private int width;
	private int height;

	private Multimap<Direction, Vector> exits = ArrayListMultimap.create();

	public RoomTemplate(File file) {
		NBTTagCompound component = null;
		try (FileInputStream stream = new FileInputStream(file)) {
			component = NBTCompressedStreamTools.a(stream);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		if (component != null) {
			NBTTagList size = component.getList("size", 3);
			this.length = size.e(0);
			this.height = size.e(1);
			this.width = size.e(2);
			this.structure.b(component);

			NBTTagList exits = component.getList("exits", 10);
			for (int i = 0; i < exits.size(); i++) {
				NBTTagCompound exitCompound = exits.getCompound(i);

				Direction direction = Direction.valueOf(exitCompound.getString("direction").toUpperCase());

				NBTTagList offset = exitCompound.getList("pos", 3);
				this.exits.put(direction, new Vector(offset.e(0), offset.e(1), offset.e(2)));
			}

		}
	}

	public Collection<Vector> getExits(Direction direction) {
		return this.exits.get(direction);
	}

	public Multimap<Direction, Vector> getExits() {
		return exits;
	}

	public int getLength() {
		return length;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public DefinedStructure getStructure() {
		return structure;
	}

}
