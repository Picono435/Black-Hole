package vini2003.xyz.blackhole.common.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "blackhole")
public class BlackHoleConfig implements ConfigData {
	@ConfigEntry.Gui.Excluded
	public static BlackHoleConfig cache;
	
	public static void refresh() {
		cache = AutoConfig.getConfigHolder(BlackHoleConfig.class).getConfig();
	}
	
	public int limit = 64;
	
	public boolean follow = true;
	
	public double followSpeed = 0.00125D;
	
	public boolean grow = true;
	
	public double growthSpeed = 0.00003851D;
	
	public boolean pull = true;
	
	public double pullSpeed = 1.25D;
	
	public boolean damage = true;
	
	public double damageStrength = 2.5D;
}
