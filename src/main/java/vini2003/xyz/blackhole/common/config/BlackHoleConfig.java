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
	public static final float defaultFollow = 0.00125F;
	
	public boolean follow = true;
	
	public float followSpeed = 0.00125F;
	
	@ConfigEntry.Gui.Excluded
	public static final float defaultGrow = 0.00003851F;
	
	public boolean grow = true;
	
	public float growSpeed = 0.00003851F;
	
	@ConfigEntry.Gui.Excluded
	public static final float defaultPull = 0.125F;
	
	public boolean pull = true;
	
	public float pullSpeed = 0.125F;
	
	public boolean damage = true;
}
