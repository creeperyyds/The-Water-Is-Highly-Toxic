package water_is_dangerous.mixin;

import funny.Util;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author 启梦
 */
@Mixin(CreatureEntity.class)
public abstract class MixinCreatureEntity {
    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(EntityType<? extends CreatureEntity> type, World world, CallbackInfo ci) {
        CreatureEntity creatureThis = (CreatureEntity) (Object) this;
        if (!Util.isAquatic((LivingEntity) (Object) this)) { //没有远古守卫者是因为它直接继承于GuardianEntity
            creatureThis.goalSelector.addGoal(0, new WaterAvoidingRandomWalkingGoal(creatureThis, Double.MIN_VALUE));
        }
    }
}
