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
    private int startCountDownTime, startDuration;

    @Inject(method = "<init>(Lnet/minecraft/potion/Effect;I)V", at = @At("RETURN"))
    public void init(Effect effect, int duration, CallbackInfo ci) {
        if (effect instanceof EffectAppender) {
            this.startCountDownTime = this.startDuration = duration;
            ((EffectAppender) effect).setDuration(duration);
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/potion/EffectInstance;tickDownDuration()I"))
    public void tick(LivingEntity entity, Runnable runnable, CallbackInfoReturnable<Boolean> cir) {
        if (startCountDownTime - this.duration == 20 && entity.isInWaterRainOrBubble()) {
            this.duration += 40;
            this.startCountDownTime = this.duration;
        }
        if (this.effect instanceof EffectAppender && !entity.level.isClientSide) {
            EffectAppender effectAppender = (EffectAppender) this.effect;
            if (this.duration == 0) {
                effectAppender.onEnd(entity);
            } else if (this.startDuration - this.duration == 1) {
                effectAppender.onStart(entity);
            }
            Predicate<Integer> predicate = effectAppender.getPredicate();
            if (predicate != null && predicate.test(this.duration)) {
                effectAppender.applyTick(entity, this.duration);
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
