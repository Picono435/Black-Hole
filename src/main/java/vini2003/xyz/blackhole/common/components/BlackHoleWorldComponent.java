package vini2003.xyz.blackhole.common.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.World;
import vini2003.xyz.blackhole.registry.common.BlackHoleComponents;

import java.util.ArrayList;
import java.util.List;

public class BlackHoleWorldComponent implements AutoSyncedComponent {
	private final List<BlackHoleComponent> blackHoles = new ArrayList<>();
	
	private final World world;
	
	public BlackHoleWorldComponent(World world) {
		this.world = world;
	}
	
	public void tick() {
		blackHoles.forEach(BlackHoleComponent::tick);
		
		BlackHoleComponents.BLACK_HOLES.sync(world);
	}
	
	@Override
	public void readFromNbt(CompoundTag compoundTag) {
		blackHoles.clear();
		
		ListTag list = compoundTag.getList("BlackHoles", 10);
		
		list.forEach(blackHoleCompoundTag -> {
			BlackHoleComponent blackHoleComponent = new BlackHoleComponent(world);
			
			blackHoleComponent.readFromNbt((CompoundTag) blackHoleCompoundTag);
			
			blackHoles.add(blackHoleComponent);
		});
	}
	
	@Override
	public void writeToNbt(CompoundTag compoundTag) {
		ListTag list = new ListTag();
		
		blackHoles.forEach(blackHoleComponent -> {
			CompoundTag blackHoleCompoundTag = new CompoundTag();
			
			blackHoleComponent.writeToNbt(blackHoleCompoundTag);
			
			list.add(blackHoleCompoundTag);
		});
		
		compoundTag.put("BlackHoles", list);
	}
	
	// Getters and Setters //
	public List<BlackHoleComponent> getBlackHoles() {
		return blackHoles;
	}
}
