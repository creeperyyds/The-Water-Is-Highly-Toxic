package water_is_dangerous.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import water_is_dangerous.Util;

/**
 * @author 启梦
 */
public final class ItemInit {
    private ItemInit() {}
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Util.MOD_ID);
    public static final RegistryObject<Item> DROPLET = ITEMS.register("droplet",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_COMBAT).fireResistant()
                    .stacksTo(1)));
}
