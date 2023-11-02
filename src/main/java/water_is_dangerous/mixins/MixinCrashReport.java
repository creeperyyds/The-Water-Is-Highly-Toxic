package water_is_dangerous.mixins;

import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import water_is_dangerous.Util;

import java.util.concurrent.ThreadLocalRandom;

import static water_is_dangerous.Util.NEW_LINE;

/**
 * @author 启梦
 */
@Mixin(CrashReport.class)
public abstract class MixinCrashReport {
    @Inject(method = "getErrorComment", at = @At("HEAD"), cancellable = true)
    private static void getErrorComment(CallbackInfoReturnable<String> cir) {
        if (Util.isCrashByMod) {
            cir.setReturnValue("你~干~嘛");
        }
    }

    @Inject(method = "getFriendlyReport", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void getFriendlyReport(CallbackInfoReturnable<String> cir, StringBuilder stringbuilder) {
        if (Util.isCrashByMod) {
            int randomVal = ThreadLocalRandom.current().nextInt(6);
            for (int i = 0; i < randomVal; i++) {
                stringbuilder.append(NEW_LINE);
            }
            stringbuilder.append("是故意的");
        }
    }
}
