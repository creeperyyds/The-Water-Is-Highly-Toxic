package water_is_dangerous.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import water_is_dangerous.AuthorSheepEntity;
import water_is_dangerous.Util;

/**
 * @author 启梦
 */
@Mixin(Entity.class)
public abstract class MixinEntity {
    @ModifyConstant(method = "updateInWaterStateAndDoWaterCurrentPushing", constant = @Constant)
    public float modifyConstantUpdateInWaterStateAndDoWaterCurrentPushing(float origin) {
        return ((Entity) (Object) this).fallDistance > 6 ? ((Entity) (Object) this).fallDistance + 3 : ((Entity) (Object) this).fallDistance;
    }

    @Inject(method = "setCustomName", at = @At("HEAD"))
    @SuppressWarnings("all")
    public void setCustomName(ITextComponent textComponent, CallbackInfo ci) {
        if (Util.AUTHOR_SHEEP_NAME.equals(textComponent.getContents()) && !(((Object) this) instanceof AuthorSheepEntity)) {
            ((Entity) (Object) this).level.getServer().getPlayerList().broadcastMessage(
                    new TranslationTextComponent("message.author.name"),
                    ChatType.SYSTEM,
                    net.minecraft.util.Util.NIL_UUID);
        }
    }
}
