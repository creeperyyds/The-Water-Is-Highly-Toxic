package water_is_dangerous;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author 启梦
 */
@Mod(Util.MOD_ID)
@SuppressWarnings("unuesd")
public class Main {
    public Main() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

    }
}
