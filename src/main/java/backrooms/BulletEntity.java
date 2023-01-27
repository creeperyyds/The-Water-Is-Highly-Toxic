package backrooms;

import backrooms.Init.ItemInit;
import backrooms.Init.EntityInit;
import backrooms.Init.SoundInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
/*
public class BulletEntity extends AbstractArrowEntity {
    public BulletEntity(EntityType<? extends AbstractArrowEntity> entityType, World world){
        super(entityType, world);
    }
    public BulletEntity(World world, LivingEntity entity){
        super(EntityInit.BULLET_TYPE, entity, world);
    }
    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ItemInit.Bullet.get());
    }
    public void tick() {
        super.tick();
        if (this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600) {
            this.level.broadcastEntityEvent(this, (byte)0);
        }
    }
    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundInit.bullet_hit_ground;
    }
}
*/