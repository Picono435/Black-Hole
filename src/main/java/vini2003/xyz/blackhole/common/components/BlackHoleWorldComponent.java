package vini2003.xyz.blackhole.common.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.World;
import vini2003.xyz.blackhole.registry.common.BlackHoleComponents;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlackHoleWorldComponent implements AutoSyncedComponent {
	private final List<BlackHoleComponent> blackHoles = new ArrayList<>();
	
	private final World world;
	
	private int lastId = 0;
	
	public BlackHoleWorldComponent(World world) {
		this.world = world;
	}
	
	public void tick() {
		blackHoles.forEach(BlackHoleComponent::tick);
		
		BlackHoleComponents.BLACK_HOLES.sync(world);
	}
	
	@Override
	public void readFromNbt(CompoundTag compoundTag) {
		ListTag list = compoundTag.getList("BlackHoles", 10);
		
		list.forEach(blackHoleCompoundTag -> {
			int id = ((CompoundTag) blackHoleCompoundTag).getInt("Id");
			
			Optional<BlackHoleComponent> blackHole = blackHoles
					.stream()
					.filter(existingBlackHole -> existingBlackHole.getId() == id)
					.findFirst();
			
			if (blackHole.isPresent()) {
				blackHole.get().readFromNbt((CompoundTag) blackHoleCompoundTag);
			} else {
				BlackHoleComponent blackHoleComponent = new BlackHoleComponent(world);
				
				blackHoleComponent.readFromNbt((CompoundTag) blackHoleCompoundTag);
				
				blackHoles.add(blackHoleComponent);
			}
		});
		
		lastId = compoundTag.getInt("LastId");
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
		
		compoundTag.putInt("LastId", lastId);
	}
	
	public int nextId() {
		int id = lastId;
		++lastId;
		return id;
	}
	
	// Getters and Setters //
	public List<BlackHoleComponent> getBlackHoles() {
		return blackHoles;
	}
}
