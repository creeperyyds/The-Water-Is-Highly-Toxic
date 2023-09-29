package water_is_dangerous;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

/**
 * @author 启梦
 */
public class DropletEntity extends ProjectileItemEntity {
    public DropletEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }
}
