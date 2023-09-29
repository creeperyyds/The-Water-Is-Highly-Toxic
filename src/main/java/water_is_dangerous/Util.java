package water_is_dangerous;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;

/**
 * @author 启梦
 */
public final class Util {
    private Util() {}
    public static final String MOD_ID = "water_is_dangerous", AUTHOR_SHEEP_NAME = "哈哈，我是一只倒过来的彩虹羊！而且我身上还在着火！";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final HashSet<Entity> GLOW_GREEN_ENTITIES = new HashSet<>();
    public static final ArrayList<BiConsumer<ServerWorld, BlockPos>> DANGER_ENTITIES = new ArrayList<>();
    public static final ArrayList<EntityType<?>> FRIENDLY_ENTITY_TYPES = new ArrayList<>();
    public static final DamageSource TOO_HEAVY = new DamageSource("too_heavy");

    public static boolean isAquatic(Entity entity) {
        return entity instanceof WaterMobEntity || entity instanceof GuardianEntity || entity instanceof TurtleEntity || entity instanceof DrownedEntity;
    }

    public static String randomChooseFriendlyEntity() {
        return FRIENDLY_ENTITY_TYPES.get(ThreadLocalRandom.current().nextInt(FRIENDLY_ENTITY_TYPES.size())).toString();
    }

    private static void initFriendlyEntityTypes() {
        try {
            for (Field field : EntityType.class.getFields()) {
                if (field.getType() != EntityType.class) {
                    continue;
                }
                if (((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName().startsWith("net.minecraft.entity.passive")) {
                    FRIENDLY_ENTITY_TYPES.add((EntityType<?>) field.get(null));
                }
            }
        } catch (Throwable ex) {
            LOGGER.fatal(ex);
            System.exit(0);
        }
    }

    private static CompoundNBT getAuthorSheepConsumer() {
        CompoundNBT authorSheepNbt = new CompoundNBT();
        authorSheepNbt.putString("id", EntityType.SHEEP.toString());
        authorSheepNbt.putString("CustomName", AUTHOR_SHEEP_NAME);
        authorSheepNbt.putBoolean("CustomNameVisible", false);
        authorSheepNbt.putBoolean("HasVisualFire", true);
        authorSheepNbt.putInt("PortalCooldown", 2147483647);
        authorSheepNbt.put("Passengers",
                getAuthorSheepPassengers(ThreadLocalRandom.current().nextInt(6)));
        return authorSheepNbt;
    }

    private static CompoundNBT getAuthorSheepPassengers(int sum) {
        CompoundNBT nbt = new CompoundNBT();
        final CompoundNBT first = nbt;
        for (int i = 0; i < sum; i++) {
            nbt.putString("id", randomChooseFriendlyEntity());
            nbt.putString("CustomName", "消消乐第" + i + "层");
            nbt.putBoolean("HasVisualFire", true);
            nbt.putBoolean("NoGravity", true);
            ListNBT list = new ListNBT();
            if (i != sum - 1) {
                nbt.put("Passengers", nbt = new CompoundNBT());
            }
        }
        return first;
    }

    static {
        initFriendlyEntityTypes();
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
                nowEntity.putString("id", randomChooseFriendlyEntity());
                nowEntity.putString("CustomName", "消消乐第" + i + "层");
                nowEntity.putBoolean("HasVisualFire", true);
                nowEntity.putBoolean("NoGravity", true);
                lastEntity.put("Passengers", nowEntity);
                lastEntity = nowEntity;
            }
        });
    }
}
