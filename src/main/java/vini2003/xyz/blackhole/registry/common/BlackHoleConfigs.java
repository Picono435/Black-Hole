package vini2003.xyz.blackhole.registry.common;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.util.ActionResult;
import vini2003.xyz.blackhole.common.config.BlackHoleConfig;

public class BlackHoleConfigs {
	public static void initialize() {
		AutoConfig.register(BlackHoleConfig.class, GsonConfigSerializer::new);
		
		AutoConfig.getConfigHolder(BlackHoleConfig.class).registerSaveListener((manager, newCache) -> {
			BlackHoleConfig.cache = newCache;
			return ActionResult.SUCCESS;
		});
		
		BlackHoleConfig.refresh();
	}
}
