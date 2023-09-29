package water_is_dangerous;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;

/**
 * @author 启梦
 */
public final class Util {
    public static final String MOD_ID = "water_is_dangerous", AUTHOR_SHEEP_NAME = "哈哈，我是一只倒过来的彩虹羊！而且我身上还在着火！";
    public static final HashSet<Entity> GLOW_GREEN_ENTITIES = new HashSet<>();
    public static final HashSet<BiConsumer<ServerWorld, BlockPos>> DANGER_ENTITIES = new HashSet<>();
    public static final HashSet<EntityType<?>> FRIENDLY_ENTITY_TYPES = new HashSet<>();
    public static final HashMap<BlockPos, Integer> RAIN_TICK_TIME_MAP = new HashMap<>();
    public static final DamageSource TOO_HEAVY = new DamageSource("too_heavy");
    public static boolean isExtendsFrom(Class<?> query, Class<?> superclass) {
        return query != superclass && (superclass == Object.class || query.getSuperclass() == superclass);
    }
    public static boolean isImplementsFrom(Class<?> query, Class<?> interfaces) {
        List<Class<?>> list;
        if (query != interfaces && interfaces.isInterface()) {
            return false;
        }
        if ((list = Arrays.asList(query.getInterfaces())).contains(interfaces)) {
            return true;
        }
        for (Class<?> clazz : list) {
            if (isImplementsFrom(clazz, interfaces)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAquatic(Entity entity) {
        return entity instanceof WaterMobEntity || entity instanceof GuardianEntity || entity instanceof TurtleEntity || entity instanceof DrownedEntity;
    }

    static {
        try {
            for (Field field : EntityType.class.getFields()) {
                if (field.getType() != EntityType.class) {
                    continue;
                }
                if (((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName().startsWith("net.minecraft.entity.passive")) {
                    FRIENDLY_ENTITY_TYPES.add((EntityType<?>) field.get(null));
                }
            }
        } catch (Exception ignored) {}
        DANGER_ENTITIES.add((serverWorld, blockPos) -> {
            CompoundNBT authorSheepNbt = new CompoundNBT();
            authorSheepNbt.putString("id", EntityType.SHEEP.toString());
            authorSheepNbt.putString("CustomName", AUTHOR_SHEEP_NAME);
            authorSheepNbt.putBoolean("CustomNameVisible", false);
            authorSheepNbt.putBoolean("HasVisualFire", true);
            authorSheepNbt.putInt("PortalCooldown", 2147483647);
            int randomVal = ThreadLocalRandom.current().nextInt(6);
            CompoundNBT lastEntity = authorSheepNbt;
            for (int i = 0; i < randomVal; i++) {
                CompoundNBT nowEntity = new CompoundNBT();
                nowEntity.putString("CustomName", "消消乐第" + i + "层");
                nowEntity.putBoolean("HasVisualFire", true);
                nowEntity.putBoolean("NoGravity", true);
            }
        });
    }
}
