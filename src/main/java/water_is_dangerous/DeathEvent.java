package water_is_dangerous;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 启梦
 */
@Mod.EventBusSubscriber
public class DeathEvent {
    private static final String VARIATION = "variation";
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    @SubscribeEvent
    public static void onEntityDie(LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        World world = livingEntity.level;
        if (world.isClientSide) {
            return;
        }
        int randInt = RANDOM.nextInt(100);
        if ((event.getSource() == Util.SULFURIC || event.getSource() == Util.RADIOACTIVITY)) {
            if (randInt < 15 && !livingEntity.getTags().contains(VARIATION)) {
                event.setCanceled(true);
                variation(livingEntity);
            } else if (randInt < 20 && livingEntity.getTags().contains(VARIATION)) {
                int entities = 0;
                Vector3d livingEntityPos = livingEntity.position();
                for (Entity entity : world.getEntities(livingEntity, new AxisAlignedBB(
                        new BlockPos(livingEntityPos.x - 2, livingEntityPos.y - 1, livingEntityPos.z - 2),
                        new BlockPos(livingEntityPos.x + 2, livingEntityPos.y + 1, livingEntityPos.z + 2)),
                        entity -> (!(entity instanceof PlayerEntity)) && entity instanceof LivingEntity)) {
                    if (++entities > 15) {
                        break;
                    }
                    variation((LivingEntity) entity);
                }
            }
        }
        if (!livingEntity.getTags().contains(VARIATION)) {
            return;
        }
        if (randInt < 30) {
            world.explode(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 5, Explosion.Mode.BREAK);
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        World world = livingEntity.level;
        DamageSource source = event.getSource();
        if (!livingEntity.getTags().contains(VARIATION)  || world.isClientSide) {
            return;
        }
        int randInt = RANDOM.nextInt(100);
        if (randInt < 5 ) {
            Entity entity = source.getEntity();
            if (entity != null) {
                entity.hurt(Util.ENTITY_THORN, 5);
            }
        } else if (randInt < 15) {
            for (int i = 0; i < 5; i++) {
                EntityType<? extends Entity> entityType = (EntityType<? extends Entity>) Util.DANGER_ENTITIES.toArray()[RANDOM.nextInt(Util.DANGER_ENTITIES.size())];
                entityType.spawn((ServerWorld) world, new ItemStack(Items.AIR), null, livingEntity.blockPosition(), SpawnReason.MOB_SUMMONED, false, false);
            }
        }
    }

    private static void variation(LivingEntity entity) {
        entity.addTag(VARIATION);
        entity.setHealth((float) RANDOM.nextDouble(100, 230));
    }
}
