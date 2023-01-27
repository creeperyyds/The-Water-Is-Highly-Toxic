// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports
package backrooms;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BulletModel extends EntityModel<Entity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelRenderer main;

	public BulletModel() {
		texHeight = 16;
		texWidth = 16;
		main = new ModelRenderer(this);
		main.setPos(0F, 24F, 0F);
		main.texOffs(0, 10).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 1.0F, 4.0F, false);
		main.texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 5.0F, 1.0F, 4.0F, false);
		main.texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 5.0F, 1.0F, 4.0F, false);
	}

	@Override
	public void setupAnim(Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}