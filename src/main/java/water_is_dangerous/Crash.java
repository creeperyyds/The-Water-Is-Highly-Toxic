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
            System.loadLibrary("Crash_x64");
        } else {
            System.loadLibrary("Crash_x86");
        }
    }
}
