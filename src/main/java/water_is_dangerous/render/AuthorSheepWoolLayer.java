package water_is_dangerous.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.SheepModel;
import net.minecraft.client.renderer.entity.model.SheepWoolModel;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import water_is_dangerous.entity.AuthorSheepEntity;

/**
 * @author 启梦
 */
@OnlyIn(Dist.CLIENT)
public class AuthorSheepWoolLayer extends LayerRenderer<AuthorSheepEntity, SheepModel<AuthorSheepEntity>> {
    private static final ResourceLocation SHEEP_FUR_LOCATION = new ResourceLocation("minecraft", "textures/entity/sheep/sheep_fur.png");
    private final SheepWoolModel<AuthorSheepEntity> model = new SheepWoolModel<>();
    public AuthorSheepWoolLayer(IEntityRenderer<AuthorSheepEntity, SheepModel<AuthorSheepEntity>> renderer) {
        super(renderer);
    }

    @Override
    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, AuthorSheepEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        int i = p_225628_4_.tickCount / 25 + p_225628_4_.getId();
        int j = DyeColor.values().length;
        int k = i % j;
        int l = (i + 1) % j;
        float f3 = ((float)(p_225628_4_.tickCount % 25) + p_225628_7_) / 25.0F;
        float[] afloat1 = SheepEntity.getColorArray(DyeColor.byId(k));
        float[] afloat2 = SheepEntity.getColorArray(DyeColor.byId(l));
        float f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
        float f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
        float f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
        coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, SHEEP_FUR_LOCATION, p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_, p_225628_7_, f, f1, f2);
    }
}
