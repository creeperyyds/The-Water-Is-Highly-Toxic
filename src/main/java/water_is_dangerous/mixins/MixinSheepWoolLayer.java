package water_is_dangerous.mixins;

import net.minecraft.client.renderer.entity.layers.SheepWoolLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import water_is_dangerous.Util;

/**
 * @author 启梦
 */
@Mixin(SheepWoolLayer.class)
public abstract class MixinSheepWoolLayer {
    @Redirect(
            method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/entity/passive/SheepEntity;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z"))
    public boolean redirectEquals(String instance, Object o) {
        return instance.equals(o) || Util.AUTHOR_SHEEP_NAME.equals(o);
    }
}
