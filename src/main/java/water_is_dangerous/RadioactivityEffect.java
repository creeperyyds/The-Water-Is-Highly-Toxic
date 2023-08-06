package water_is_dangerous;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import java.util.function.Predicate;

/**
 * @author 启梦
 */
public class RadioactivityEffect extends Effect implements EffectAppender {
    private int startDuration;
    public RadioactivityEffect(EffectType type, int color) {
        super(type, color);
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public void applyTick(LivingEntity entity, int duration) {
        int time = startDuration - duration;
        if (time > 100) {
            entity.hurt(Util.RADIOACTIVITY, time / 100f);
        }
    }

    @Override
    public void setDuration(int duration) {
        this.startDuration = duration;
    }

    @Override
    public void addDuration(int addVal) {
        this.startDuration += addVal;
    }

    @Override
    public Predicate<Integer> getPredicate() {
        return duration -> (startDuration - duration) % 50 == 0;
    }

    @Override
    public void onStart(Entity entity) {
        Util.GLOW_GREEN_ENTITIES.add(entity);
    }

    @Override
    public void onEnd(Entity entity) {
        Util.GLOW_GREEN_ENTITIES.remove(entity);
    }
}
