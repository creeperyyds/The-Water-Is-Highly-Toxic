package backrooms;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
/*
public class BulletRender extends EntityRenderer<Entity> {
    EntityModel bulletModel=new BulletModel();
    public BulletRender(EntityRendererManager manager){
        super(manager);
    }
    public ResourceLocation getTextureLocation(Entity entity){
        return new ResourceLocation(Main.MOD_ID, "textures/entity/bullet.png");
    }
    public RenderType getRenderType(BulletEntity entity, boolean bool1) {
        ResourceLocation resourcelocation = this.getTextureLocation(entity);
        if (bool1) {
            return RenderType.entityTranslucent(resourcelocation, false);}
        else {
            return bool1 ? RenderType.entityCutoutNoCull(resourcelocation, false) : null;
        }
    }
    @Override
    public void render(Entity entity, float entityY, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        super.render(entity, entityY, partialTick, matrixStack, buffer, packedLight);
        matrixStack.pushPose();
        matrixStack.mulPose(Vector3f.YN.rotationDegrees(45));
        IVertexBuilder ivertexBuilder=buffer.getBuffer(this.bulletModel.renderType(this.getTextureLocation(entity)));
        this.bulletModel.renderToBuffer(matrixStack, ivertexBuilder, packedLight, packedLight, packedLight, entityY, partialTick, packedLight);
        matrixStack.popPose();
    }
}
*/