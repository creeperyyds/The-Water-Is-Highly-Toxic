package water_is_dangerous.mixin;

import funny.RainTimeMapSavedData;
import funny.Util;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

/**
 * @author 启梦
 */
@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class MixinAbstractBlockState {
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTick(ServerWorld serverWorld, BlockPos pos, Random random, CallbackInfo ci) {
        RainTimeMapSavedData savedData = RainTimeMapSavedData.get(serverWorld);
        if (savedData.getPosSulfuric(pos) >= 10) {
            savedData.removePosSulfuric(pos);
            serverWorld.setBlock(pos, Blocks.AIR.defaultBlockState(), 18);
            ci.cancel();
        }
        Block block = ((AbstractBlock.AbstractBlockState) (Object) this).getBlock();
        if ((block != Blocks.VOID_AIR &&
                block != Blocks.AIR &&
                block != Blocks.CAVE_AIR &&
                block != Blocks.WATER &&
                block != Blocks.LAVA &&
                block != Blocks.GOLD_BLOCK &&
                block != Blocks.GOLD_ORE &&
                block != Blocks.NETHER_GOLD_ORE &&
                block != Blocks.BEDROCK &&
                block != Blocks.COMMAND_BLOCK &&
                block != Blocks.BARRIER) &&

                serverWorld.isRainingAt(pos) ||
                serverWorld.isRainingAt(pos.west()) ||
                serverWorld.isRainingAt(pos.north()) ||
                serverWorld.isRainingAt(pos.south()) ||
                serverWorld.isRainingAt(pos.east()) ||
                serverWorld.getBlockState(pos.below()).getBlock() == Blocks.WATER ||
                serverWorld.getBlockState(pos.above()).getBlock() == Blocks.WATER ||
                serverWorld.getBlockState(pos.west()).getBlock() == Blocks.WATER ||
                serverWorld.getBlockState(pos.east()).getBlock() == Blocks.WATER ||
                serverWorld.getBlockState(pos.north()).getBlock() == Blocks.WATER ||
                serverWorld.getBlockState(pos.south()).getBlock() == Blocks.WATER
        ) {
            savedData.addPosSulfuric(pos);
        }
    }

    @Inject(method = "onRemove", at = @At("HEAD"))
    public void onRemove(World world, BlockPos pos, BlockState state, boolean isNeedToUpdate, CallbackInfo ci) {
        if (!world.isClientSide) {
            RainTimeMapSavedData.get((ServerWorld) world).removePosSulfuric(pos);
        }
    }
}
