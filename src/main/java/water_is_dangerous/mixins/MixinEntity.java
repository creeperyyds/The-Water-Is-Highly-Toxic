package water_is_dangerous.mixins;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * @author 启梦
 */
@Mixin(Entity.class)
public abstract class MixinEntity {
    @ModifyConstant(method = "updateInWaterStateAndDoWaterCurrentPushing", constant = @Constant)
    public float modifyConstantUpdateInWaterStateAndDoWaterCurrentPushing(float origin) {
        return ((Entity) (Object) this).fallDistance + 10;
    }
}
