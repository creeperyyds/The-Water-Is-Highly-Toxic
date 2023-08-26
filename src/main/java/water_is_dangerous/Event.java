package water_is_dangerous;

import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static net.minecraft.enchantment.Enchantments.*;
import static net.minecraft.item.Items.*;

/**
 * @author 启梦
 */
@Mod.EventBusSubscriber
public class Event {
    public static final String VARIATION = "variation", GOLEM = "golem";
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    private static final FloatNBT NAN_FLOAT_NBT = FloatNBT.valueOf(Float.NaN);
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
        Entity hurtEntity = event.getSource().getEntity();
        LivingEntity getDamageEntity = event.getEntityLiving();
        if (hurtEntity == null) {
            return;
        }
        World world = hurtEntity.level;
        if (!getDamageEntity.getTags().contains(VARIATION)  || world.isClientSide || hurtEntity instanceof PlayerEntity) {
            return;
        }
        if (!hurtEntity.getTags().contains(GOLEM)) {
            event.setCanceled(true);
            return;
        }
        int randInt = RANDOM.nextInt(100);
        if (randInt < 5 ) {
            hurtEntity.hurt(Util.ENTITY_THORN, 5);
        } else if (randInt < 15) {
            for (int i = 0; i < 5; i++) {
                EntityType.loadEntityRecursive(getOpEntity(hurtEntity), world, entity -> {
                    entity.moveTo(
                            hurtEntity.getX() + RANDOM.nextDouble(5, -5),
                            hurtEntity.getY(),
                            hurtEntity.getZ() + RANDOM.nextDouble(5, -5));
                    return entity;
                });
            }
        }
    }

    private static CompoundNBT getOpEntity(Entity hurtEntity) {
        ListNBT armorDropChances = new ListNBT(), armorItems = new ListNBT(), handDropChances = new ListNBT();
        for (int i = 0; i < 4; i++) {
            armorDropChances.add(NAN_FLOAT_NBT);
        }
        handDropChances.add(NAN_FLOAT_NBT);
        handDropChances.add(NAN_FLOAT_NBT);
        armorItems.add(nbtOfOpArmor(NETHERITE_HELMET));
        armorItems.add(nbtOfOpArmor(NETHERITE_CHESTPLATE));
        armorItems.add(nbtOfOpArmor(NETHERITE_LEGGINGS));
        armorItems.add(nbtOfOpArmor(NETHERITE_BOOTS));
        CompoundNBT lootTable = new CompoundNBT();
        lootTable.putString("DeathLootTable", "empty");
        CompoundNBT entityNbt = new CompoundNBT();
        String id = EntityType.getKey((EntityType<? extends Entity>)
                Util.DANGER_ENTITIES.toArray()[RANDOM.nextInt(Util.DANGER_ENTITIES.size())]).toString();
        entityNbt.putString("id", id);
        entityNbt.putBoolean("PersistenceRequired", true);
        entityNbt.putString("CustomName", "{'text':'为了§m恶心玩家§r让游戏更困难而搞出来的东西'}");
        entityNbt.put("ArmorDropChances", armorDropChances);
        entityNbt.put("ArmorItems", armorItems);
        entityNbt.put("HandDropChances", handDropChances);
        entityNbt.put("HandItems", nbtOfEntityHand(id));
        entityNbt.putBoolean("CanPickUpLoot", false);
        entityNbt.putBoolean("Silent", true);

        switch (id) {
            case "minecraft:tnt":
            case "minecraft:tnt_minecart":
                entityNbt.putInt("Fuse", 10);
                break;
            case "minecraft:creeper":
                entityNbt.putByte("ExplosionRadius", (byte) 20);
                entityNbt.putBoolean("powered", true);
                break;
            case "minecraft:bee":
            case "minecraft:iron_golem":
                entityNbt.putInt("AngerTime", Integer.MAX_VALUE);
                entityNbt.putUUID("AngerAt", hurtEntity.getUUID());
                break;
            case "minecraft:zombie":
            case "minecraft:husk":
            case "minecraft:drowned":
                entityNbt.putBoolean("CanBreakDoor", true);
                entityNbt.putBoolean("IsBaby", true);
                break;
            case "minecraft:ghast":
                entityNbt.putByte("ExplosionPower", (byte) 20);
                break;
            case "minecraft:evoker":
            case "minecraft:illusioner":
                entityNbt.putInt("SpellTicks", 0);
                break;
            case "minecraft:slime":
            case "minecraft:magma_cube":
            case "minecraft:phantom":
                entityNbt.putInt("Size", 30);
                break;
            case "minecraft:piglin":
            case "minecraft:piglin_brute":
                entityNbt.putBoolean("IsImmuneToZombification", true);
        }
        if (RANDOM.nextInt(0, 100) < 15) {
            entityNbt.put("Passengers", getOpEntity(hurtEntity));
        }
        return entityNbt;
    }

    private static void variation(LivingEntity entity) {
        entity.addTag(VARIATION);
        entity.setHealth((float) RANDOM.nextDouble(100, 230));
        entity.removeEffect(Main.RADIOACTIVITY.get());
    }

    private static CompoundNBT nbtOfOpArmor(Item item) {
        return enchantItem(item,
                UNBREAKING,
                ALL_DAMAGE_PROTECTION,
                PROJECTILE_PROTECTION,
                BLAST_PROTECTION,
                FIRE_PROTECTION,
                FALL_PROTECTION,
                THORNS,
                DEPTH_STRIDER,
                RESPIRATION);
    }

    private static ListNBT nbtOfEntityHand(String id) {
        switch (id) {
            case "minecraft:husk":
            case "minecraft:zombie":
            case "minecraft:vex":
            case "minecraft:wither_skeleton":
            case "minecraft:zombie_villager":
            case "minecraft:zombified_piglin":
                return getOpItemInHand(NETHERITE_SWORD,
                        SHARPNESS,
                        UNBREAKING,
                        KNOCKBACK,
                        SMITE,
                        BANE_OF_ARTHROPODS,
                        FIRE_ASPECT,
                        SWEEPING_EDGE);
            case "minecraft:drowned":
                return getOpItemInHand(TRIDENT,
                        LOYALTY,
                        CHANNELING,
                        RIPTIDE,
                        IMPALING,
                        UNBREAKING);
            case "minecraft:skeleton":
            case "minecraft:stray":
            case "minecraft:illusioner":
                return getOpItemInHand(BOW,
                        POWER_ARROWS,
                        PUNCH_ARROWS,
                        FLAMING_ARROWS,
                        INFINITY_ARROWS,
                        UNBREAKING);
            case "minecraft:pillager":
                return getOpItemInHand(CROSSBOW,
                        QUICK_CHARGE,
                        MULTISHOT,
                        PIERCING,
                        UNBREAKING);
            case "minecraft:vindicator":
            case "minecraft:piglin_brute":
                return getOpItemInHand(NETHERITE_AXE,
                        UNBREAKING,
                        SHARPNESS,
                        SMITE,
                        BANE_OF_ARTHROPODS);
            case "minecraft:piglin":
                return nbtOfEntityHand(RANDOM.nextBoolean() ? "minecraft:pillager" : "minecraft:husk");
            default:
                return new ListNBT();
        }
    }

    private static CompoundNBT enchantItem(Item item, Enchantment ...enchantments) {
        assert enchantments.length > 0 : "附魔数量不能为零！";
        ItemStack itemStack = new ItemStack(item);
        for (Enchantment enchantment : enchantments) {
            itemStack.enchant(enchantment, Short.MAX_VALUE);
        }
        ListNBT lore = new ListNBT();
        itemStack.setHoverName(ITextComponent.nullToEmpty("所以说？你是怎么看到这条消息的？如果有bug的话，请告知作者，谢谢！"))
                .getOrCreateTagElement("display").put("Lore", lore);
        lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(ITextComponent.nullToEmpty("不过，说不定，你只是一个§f§m§n§o干净的白客§r§j§n§o肮脏的黑客呢§r呢？"))));
        return itemStack.save(new CompoundNBT());
    }

    private static ListNBT getOpItemInHand(Item item, Enchantment ...enchantments) {
        ListNBT handsNbt = new ListNBT();
        CompoundNBT stackNbt = enchantItem(item, enchantments);
        handsNbt.add(stackNbt);
        handsNbt.add(stackNbt);
        return handsNbt;
    }
}
