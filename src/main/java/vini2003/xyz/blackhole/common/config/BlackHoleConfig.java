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
	
	@ConfigEntry.Gui.Excluded
	public static final int defaultLimit = 64;
	
	public int limit = 64;
	
	@ConfigEntry.Gui.Excluded
	public static final double defaultFollow = 0.00125D;
	
	public boolean follow = true;
	
	public double followSpeed = 0.00125D;
	
	@ConfigEntry.Gui.Excluded
	public static final double defaultGrow = 0.00003851D;
	
	public boolean grow = true;
	
	public double growthSpeed = 0.00003851D;
	
	@ConfigEntry.Gui.Excluded
	public static final double defaultPull = 0.5D;
	
	public boolean pull = true;
	
	public double pullSpeed = 0.5D;
}
