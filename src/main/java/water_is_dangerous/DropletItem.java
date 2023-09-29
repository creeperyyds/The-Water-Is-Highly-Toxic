package water_is_dangerous;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/**
 * @author 启梦
 */
public class DropletItem extends Item {
    public DropletItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        world.playSound(null,
                player.getX(),
                player.getY(),
                player.getZ(),
                Main.DROPLET_DROP.get(),
                SoundCategory.NEUTRAL, 0.5f, 0.4f);
        return ActionResult.sidedSuccess(stack, world.isClientSide);
    }
}
