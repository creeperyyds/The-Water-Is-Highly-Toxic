package water_is_dangerous.mixins;

import water_is_dangerous.EffectAppender;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

/**
 * @author 启梦
 */
@Mixin(EffectInstance.class)
public abstract class MixinEffectInstance {
    @Shadow @Final
    private Effect effect;

    @Shadow private int duration;

    @Inject(method = "<init>(Lnet/minecraft/potion/Effect;I)V", at = @At("RETURN"))
    public void init(Effect effect, int duration, CallbackInfo ci) {
        if (effect instanceof EffectAppender) {
            ((EffectAppender) effect).setDuration(duration);
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/potion/EffectInstance;tickDownDuration()I"))
    public void tick(LivingEntity entity, Runnable runnable, CallbackInfoReturnable<Boolean> cir) {
        if (this.effect instanceof EffectAppender && !entity.level.isClientSide) {
            Predicate<Integer> predicate = ((EffectAppender) this.effect).getPredicate();
            if (predicate != null && predicate.test(this.duration)) {
                ((EffectAppender) this.effect).applyTick(entity, this.duration);
            }
        }
    }

    @Inject(method = "update", at = @At(value = "FIELD", target = "Lnet/minecraft/potion/EffectInstance;duration:I", ordinal = 6))
    public void update(EffectInstance instance, CallbackInfoReturnable<Boolean> cir) {
        if (this.effect instanceof EffectAppender) {
            ((EffectAppender) this.effect).setDuration(instance.getDuration());
        }
    }
}
