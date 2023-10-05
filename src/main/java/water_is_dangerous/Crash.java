package water_is_dangerous;

import net.minecraft.client.Minecraft;

/**
 * @author 启梦
 */
public final class Crash {
    private Crash() {}
    public static native void crash();
    static {
        if (Minecraft.getInstance().is64Bit()) {
            System.loadLibrary("JNI_x64");
        } else {
            System.loadLibrary("JNI_x86");
        }
    }
}
