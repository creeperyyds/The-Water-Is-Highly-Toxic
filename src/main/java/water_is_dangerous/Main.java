package water_is_dangerous;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
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
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister
            .create(ForgeRegistries.SOUND_EVENTS, Util.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Util.MOD_ID);
    public static final RegistryObject<Item> DROPLET = ITEMS.register("droplet",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_COMBAT).fireResistant().stacksTo(1)));
    public Main() {
        IEventBus ibus = FMLJavaModLoadingContext.get().getModEventBus();
        EntityInit.ENTITY_TYPES.register(ibus);
    }
}
