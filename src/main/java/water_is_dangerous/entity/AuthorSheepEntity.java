package water_is_dangerous.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 启梦
 */
public class AuthorSheepEntity extends SheepEntity {
    public static final String NAME = "author_sheep";
    private static final ImmutableList<EntityType<?>> FRIENDLY_ENTITY_TYPES;
    public AuthorSheepEntity(EntityType<? extends SheepEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason reason, @Nullable ILivingEntityData data, @Nullable CompoundNBT nbt) {

        return super.finalizeSpawn(iServerWorld, difficultyInstance, reason, data, nbt);
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, World world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float volume) {
        return false;
    }

    static {
        ImmutableList.Builder<EntityType<?>> builder = ImmutableList.builder();
        try {
            for (Field field : EntityType.class.getFields()) {
                if (field.getType() != EntityType.class &&
                        !((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName().startsWith("net.minecraft.entity.passive")) {
                    continue;
                }
                builder.add((EntityType<?>) field.get(null));
            }
        } catch (Throwable throwable) {
            LogManager.getLogger().fatal(throwable);
        }
        FRIENDLY_ENTITY_TYPES = builder.build();
    }
}
