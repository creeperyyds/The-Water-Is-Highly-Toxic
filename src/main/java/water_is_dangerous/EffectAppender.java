package water_is_dangerous;

import net.minecraft.entity.LivingEntity;

import java.util.function.Predicate;

/**
 * @author 启梦
 */
public interface EffectAppender {
    void applyTick(LivingEntity entity, int duration);
    void setDuration(int duration);
    void addDuration(int addVal);
    default Predicate<Integer> getPredicate() {
        return null;
    }
}
