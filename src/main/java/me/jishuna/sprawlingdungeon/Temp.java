package me.jishuna.sprawlingdungeon;

import java.util.Random;

import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_16_R3.DefinedStructure;
import net.minecraft.server.v1_16_R3.DefinedStructureInfo;
import net.minecraft.server.v1_16_R3.EnumBlockMirror;
import net.minecraft.server.v1_16_R3.EnumBlockRotation;
import net.minecraft.server.v1_16_R3.WorldServer;

public class Temp {

	private final WorldServer world;
	private final BlockPosition pos;
	private final DefinedStructure structure;
	private final DungeonRoom room;

	public Temp(WorldServer world, BlockPosition pos, DefinedStructure structure, DungeonRoom room) {
		this.world = world;
		this.pos = pos;
		this.structure = structure;
		this.room = room;
	}

	public void execute() {
		DefinedStructureInfo structInfo = new DefinedStructureInfo().a(EnumBlockMirror.NONE).a(EnumBlockRotation.NONE)
				.a(false).a((ChunkCoordIntPair) null).c(false).a(new Random());

		this.structure.a(this.world, this.pos, structInfo, new Random());
	}

	public DungeonRoom getRoom() {
		return room;
	}
}
