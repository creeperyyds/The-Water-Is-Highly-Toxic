package water_is_dangerous;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sun.istack.internal.NotNull;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.SheepModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

/**
 * @author 启梦
 */
public class AuthorSheepRenderer extends MobRenderer<AuthorSheepEntity, SheepModel<AuthorSheepEntity>> {
    private static final ResourceLocation SHEEP_LOCATION = new ResourceLocation("textures/entity/sheep/sheep.png");

    public AuthorSheepRenderer(EntityRendererManager p_i50961_1_, SheepModel<AuthorSheepEntity> p_i50961_2_, float p_i50961_3_) {
        super(p_i50961_1_, new SheepModel<>(), 0.7f);
        this.addLayer(new AuthorSheepWoolLayer(this));
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(AuthorSheepEntity entity) {
        return SHEEP_LOCATION;
    }

    @Override
    protected void setupRotations(AuthorSheepEntity p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
        super.setupRotations(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
        p_225621_2_.translate(0, p_225621_1_.getBbHeight() + 0.1, 0);
        p_225621_2_.mulPose(Vector3f.ZP.rotationDegrees(180));
    }
}
