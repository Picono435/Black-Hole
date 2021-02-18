package vini2003.xyz.blackhole.registry.common;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;
import vini2003.xyz.blackhole.BlackHole;
import vini2003.xyz.blackhole.common.entity.BlackHoleEntity;

public class BlackHoleEntityTypes {
	public static final EntityType<BlackHoleEntity> BLACK_HOLE = Registry.register(
			Registry.ENTITY_TYPE,
			BlackHole.identifier("blackhole"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, BlackHoleEntity::new)
					.trackRangeBlocks(1024)
					.trackedUpdateRate(3)
					.dimensions(EntityDimensions.fixed(1, 1))
					.build());
	
	public static void initialize() {
	
	}
}
