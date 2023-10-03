package water_is_dangerous.mixins;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.OutlineLayerBuffer;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import water_is_dangerous.Color;
import water_is_dangerous.Util;

/**
 * @author 启梦
 */
@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer {
    @Shadow @Final private RenderTypeBuffers renderBuffers;

    private OutlineLayerBuffer getOutlineLayerBuffer(int red, int green, int blue, int alpha) {
        OutlineLayerBuffer layerBuffer = this.renderBuffers.outlineBufferSource();
        layerBuffer.setColor(red, green, blue, alpha);
        return layerBuffer;
    }
    @ModifyArg(method = "renderEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRendererManager;render(Lnet/minecraft/entity/Entity;DDDFFLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;I)V"))
    private IRenderTypeBuffer modifyRenderArg(Entity p_229084_1_, double p_229084_2_, double p_229084_4_, double p_229084_6_, float p_229084_8_, float p_229084_9_, MatrixStack p_229084_10_, IRenderTypeBuffer p_229084_11_, int p_229084_12_) {
        if (Util.GLOW_GREEN_ENTITIES.contains(p_229084_1_)) {
            return getOutlineLayerBuffer(85, 255, 85, 255);
        }
        if (p_229084_1_.getTags().contains(Util.AUTHOR_SHEEP_PASSENGERS_TAG)) {
            for (String str : p_229084_1_.getTags()) {
                if (Util.isNumber(str)) {
                    Color color = Color.values()[Integer.parseInt(str)];
                    return getOutlineLayerBuffer(color.R, color.G, color.B, 30);
                }
            }
        }
        return p_229084_11_;
    }
}
