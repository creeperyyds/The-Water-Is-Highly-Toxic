package backrooms;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.EnchantmentHelper;

public class BulletItem extends ArrowItem {
    public BulletItem(Properties properties){
        super(properties);

    }
    public boolean isInfinite(ItemStack bow){
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow) > 0 && this.getClass() == BulletItem.class;
    }

}
