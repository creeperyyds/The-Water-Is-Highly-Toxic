package backrooms.Init;

import backrooms.BulletItem;
import backrooms.Groups;
import backrooms.Main;
//import backrooms.Gun;
import net.minecraft.item.BlockItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> ITEM=DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);
    public static final RegistryObject<Item> Level0Block_Item=ITEM.register("level0block_item",() -> new BlockItem(BlockInit.Level0Block.get(),new Item.Properties().tab(Groups.backroomsGroup)));
    public static final RegistryObject<Item> Bullet=ITEM.register("bullet", () -> new BulletItem(new Item.Properties().tab(Groups.backroomsGroup).stacksTo(12)));
    //public static final RegistryObject<Item> Gun=ITEM.register("gun", () -> new Gun(new Item.Properties().tab(Groups.backroomsGroup)));
}
