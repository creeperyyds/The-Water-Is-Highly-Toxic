package water_is_dangerous;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author 启梦
 */
@Mod(Util.MOD_ID)
@SuppressWarnings("unuesd")
public class Main {
    private static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, "funny");
    public static final RegistryObject<Effect> RADIOACTIVITY = EFFECTS.register("radioactivity",
            () -> new RadioactivityEffect(EffectType.HARMFUL, 5149489));
    public Main() {
        EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
