package water_is_dangerous;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @author 启梦
 */
@SuppressWarnings("unused")
public final class Util {
    public static final String MOD_ID = "water_is_dangerous";
    public static final HashSet<Entity> GLOW_GREEN_ENTITIES = new HashSet<>();
    public static final HashSet<EntityType<? extends Entity>> DANGER_ENTITIES = new HashSet<>();
    public static final HashMap<BlockPos, Integer> RAIN_TICK_TIME_MAP = new HashMap<>();
    public static final DamageSource SULFURIC = new DamageSource("Sulfuric").setScalesWithDifficulty().bypassArmor();
    public static final DamageSource RADIOACTIVITY = new DamageSource("Radioactivity").setScalesWithDifficulty();
    public static final DamageSource ENTITY_THORN = new DamageSource("Boom").setScalesWithDifficulty();
    public static final HashSet<DamageSource> DAMAGE_SOURCES = new HashSet<>();
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
        DANGER_ENTITIES.add(EntityType.TNT);
        DANGER_ENTITIES.add(EntityType.TNT_MINECART);
        DANGER_ENTITIES.add(EntityType.LIGHTNING_BOLT);
        DANGER_ENTITIES.add(EntityType.EVOKER_FANGS);
        for (Field field : EntityType.class.getFields()) {
            if (field.getType() != EntityType.class) {
                continue;
            }
            Class<?> parameterizedClass;
            try {
                parameterizedClass = Class.forName(((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (isExtendsFrom(parameterizedClass, MonsterEntity.class)
                    || isImplementsFrom(parameterizedClass, IRangedAttackMob.class)
                    || isExtendsFrom(parameterizedClass, ProjectileEntity.class)) {
                try {
                    EntityType<? extends Entity> entityType = (EntityType<? extends Entity>) field.get(null);
                    if (entityType != EntityType.WITHER && entityType != EntityType.GIANT) {
                        DANGER_ENTITIES.add();
                    }
                } catch (IllegalAccessException ignored) {} //不会发生
            }
        }
        for (Field field : DamageSource.class.getFields()) {
            if (field.getType() != DamageSource.class) {
                continue;
            }
            try {
                DAMAGE_SOURCES.add((DamageSource) field.get(null));
            } catch (Exception ignored) {} //不会发生
        }
    }
}
