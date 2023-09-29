package water_is_dangerous.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

/**
 * @author 启梦
 */
@Mixin(ShearsItem.class)
public abstract class MixinShearsItem {
    @Inject(method = "interactLivingEntity", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/IForgeShearable;onSheared(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;I)Ljava/util/List;"))
    public void interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResultType> cir) {
        if ("哈哈，我是一只倒过来的彩虹羊！而且我身上还在着火！".equals(Objects.requireNonNull(entity.getCustomName()).getContents())) {

        }
    }
}
