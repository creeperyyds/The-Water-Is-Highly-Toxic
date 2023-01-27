package backrooms;

import backrooms.Init.ItemInit;
import backrooms.Init.SoundInit;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.function.Predicate;
/*
public class Gun extends ShootableItem {
    public Gun(Properties properties){
        super(properties);
    }
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack gun=player.getItemInHand(hand);
        ItemStack projectile=player.getProjectile(gun);
        player.getCooldowns().addCooldown(this, 5);
        boolean canContinue=player.abilities.instabuild || projectile.isEmpty();
        if (!world.isClientSide){
            if (player.abilities.instabuild || !projectile.isEmpty()){
                AbstractArrowEntity abstractBullet=new BulletEntity(world, player);
                abstractBullet.shootFromRotation(player, player.xRot, player.yRot, 0F, 10F, 1F);
                if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, gun)>0){
                    abstractBullet.setBaseDamage(abstractBullet.getBaseDamage()+
                            (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, gun)+1)*0.5);
                    abstractBullet.setKnockback(3);
                    gun.hurtAndBreak(1, player, (playerEntity) -> playerEntity.broadcastBreakEvent(player.getUsedItemHand()));
                    if (canContinue || player.abilities.instabuild){
                        abstractBullet.pickup=AbstractArrowEntity.PickupStatus.ALLOWED;
                    }
                }
                world.addFreshEntity(abstractBullet);
                projectile.shrink(1);
            } else {
                return ActionResult.consume(gun);
            }
        }
        world.playSound(player,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundInit.gun_shoot,
                SoundCategory.PLAYERS,
                2F, 1F / (random.nextFloat() * 0.4F + 1.2F) + 5 * 0.5F);
        if (canContinue && !player.abilities.instabuild){
            projectile.shrink(1);
            if (gun.isEmpty()){
                player.inventory.removeItem(projectile);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        return ActionResult.success(gun);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return itemStack -> {return itemStack.getItem()== ItemInit.Bullet.get();};
    }

    @Override
    public int getDefaultProjectileRange() {
        return 12;
    }
}
*/
