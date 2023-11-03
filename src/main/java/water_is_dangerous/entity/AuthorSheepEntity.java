package water_is_dangerous.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * @author 启梦
 */
public class AuthorSheepEntity extends SheepEntity {
    public static final String NAME = "author_sheep";
    public AuthorSheepEntity(EntityType<? extends SheepEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    public boolean isShearable(@Nonnull ItemStack item, World world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float volume) {
        return false;
    }
}
