package backrooms;

import backrooms.Init.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class BackroomsItemGroup extends ItemGroup {
    public BackroomsItemGroup() {
        super("backrooms_group");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemInit.Level0Block_Item.get());
    }
}
