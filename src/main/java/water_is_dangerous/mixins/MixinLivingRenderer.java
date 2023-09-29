package water_is_dangerous.mixins;

import net.minecraft.client.renderer.entity.LivingRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import water_is_dangerous.Util;

/**
 * @author 启梦
 */
@Mixin(LivingRenderer.class)
public abstract class MixinLivingRenderer {
    @Redirect(method = "setupRotations", at = @At(value = "INVOKE", target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z", ordinal = 1))
    public boolean redirectEquals(String instance, Object o) {
        return instance.equals(o) || Util.AUTHOR_SHEEP_NAME.equals(o);
    }
}
