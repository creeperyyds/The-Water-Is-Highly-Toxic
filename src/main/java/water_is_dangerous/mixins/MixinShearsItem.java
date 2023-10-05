package water_is_dangerous.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import water_is_dangerous.Crash;

import java.util.Objects;

/**
 * @author 启梦
 */
@Mixin(ShearsItem.class)
public abstract class MixinShearsItem {
    @Inject(method = "interactLivingEntity", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/IForgeShearable;onSheared(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;I)Ljava/util/List;"))
    public void interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResultType> cir) {
        if ("哈哈，我是一只倒过来的彩虹羊！而且我身上还在着火！".equals(Objects.requireNonNull(entity.getCustomName()).getContents())) {
            switch (water_is_dangerous.Util.shearCount) {
                case 0:
                    Objects.requireNonNull(playerIn.level.getServer()).getPlayerList().broadcastMessage(
                            ITextComponent.nullToEmpty("別剪我"),
                            ChatType.SYSTEM,
                            Util.NIL_UUID);
                    break;
                case 1:
                    Objects.requireNonNull(playerIn.level.getServer()).getPlayerList().broadcastMessage(
                            ITextComponent.nullToEmpty("你再剪？"),
                            ChatType.SYSTEM,
                            Util.NIL_UUID);
                    break;
                case 2:
                    Objects.requireNonNull(playerIn.level.getServer()).getPlayerList().broadcastMessage(
                            ITextComponent.nullToEmpty("我警告你你不要再剪了"),
                            ChatType.SYSTEM,
                            Util.NIL_UUID);
                    break;
                case 3:
                    Objects.requireNonNull(playerIn.level.getServer()).getPlayerList().broadcastMessage(
                            ITextComponent.nullToEmpty("最后一次警告！"),
                            ChatType.SYSTEM,
                            Util.NIL_UUID);
                    break;
                case 4:
                    Objects.requireNonNull(playerIn.level.getServer()).getPlayerList().broadcastMessage(
                            ITextComponent.nullToEmpty("好好好"),
                            ChatType.SYSTEM,
                            Util.NIL_UUID);
                    Crash.crash();
                    break;
            }
            water_is_dangerous.Util.shearCount++;
        }
    }
}
