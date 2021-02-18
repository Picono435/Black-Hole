package vini2003.xyz.blackhole.common.damage;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class BlackHoleDamageSource extends DamageSource {
	public BlackHoleDamageSource() {
		super("blackhole");
	}
	
	@Override
	public boolean bypassesArmor() {
		return true;
	}
	
	@Override
	public Text getDeathMessage(LivingEntity entity) {
		return new TranslatableText("death.attack.blackhole." + entity.world.random.nextInt(8), entity.getDisplayName());
	}
}
