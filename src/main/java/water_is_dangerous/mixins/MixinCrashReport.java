package water_is_dangerous.mixins;

import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import water_is_dangerous.Util;

/**
 * @author 启梦
 */
@Mixin(CrashReport.class)
public abstract class MixinCrashReport {
    @Inject(method = "getErrorComment", at = @At("HEAD"), cancellable = true)
    private static void getErrorComment(CallbackInfoReturnable<String> cir) {
        if (Util.isCrashByAuthorSheep) {
            cir.setReturnValue("你~干~嘛");
        }
    }
}
