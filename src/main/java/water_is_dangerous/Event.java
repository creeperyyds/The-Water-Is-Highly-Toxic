package water_is_dangerous;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.minecart.TNTMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.DamageSource;
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

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 启梦
 */
@Mod.EventBusSubscriber
public class Event {
    public static final String VARIATION = "variation", GOLEM = "golem";
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    @SubscribeEvent
    public static void onEntityDie(LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        World world = livingEntity.level;
        if (world.isClientSide || livingEntity instanceof PlayerEntity) {
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
        } else if (randInt < 40) {
            Entity entity = event.getSource().getEntity();
            if (entity != null) {
                CompoundNBT entityNbt = new CompoundNBT();
                entityNbt.putString("id", "minecraft:falling_block");
                entityNbt.put("BlockState", NBTUtil.writeBlockState(Blocks.ANVIL.defaultBlockState()));
                entityNbt.putInt("FallHurtMax", 15);
                entityNbt.putInt("Time", 1);
                entityNbt.putFloat("FallHurtAmount", 3);
                entityNbt.putBoolean("HurtEntities", true);
                entityNbt.putBoolean("DropItems", true);
                double summonHeight = entity.getY() + 6;
                for (int i = -2; i < 3; i++) {
                    for (int j = -2; j < 3; j++) {
                        int finalI = i, finalJ = j;
                        ((ServerWorld) world).tryAddFreshEntityWithPassengers(Objects.requireNonNull(
                                EntityType.loadEntityRecursive(entityNbt, world, entity1 -> {
                                    entity1.moveTo(finalI, summonHeight, finalJ, 0, 0);
                                    return entity1;
                        })));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        Entity livingEntity = event.getSource().getEntity();
        if (livingEntity == null) {
            return;
        }
        World world = livingEntity.level;
        DamageSource source = event.getSource();
        if (!livingEntity.getTags().contains(VARIATION)  || world.isClientSide || livingEntity instanceof PlayerEntity) {
            return;
        }
        if (!livingEntity.getTags().contains(GOLEM)) {
            event.setCanceled(true);
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
                Objects.requireNonNull(entityType.spawn(
                        (ServerWorld) world,
                        new ItemStack(Items.AIR),
                        null,
                        livingEntity.blockPosition(),
                        SpawnReason.MOB_SUMMONED, false, false)).addTag(GOLEM);
            }
        }
    }

    private static void variation(LivingEntity entity) {
        entity.addTag(VARIATION);
        entity.setHealth((float) RANDOM.nextDouble(100, 230));
        entity.removeEffect(Main.RADIOACTIVITY.get());
    }
}
