package water_is_dangerous;

import com.google.common.collect.ImmutableList;
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
import java.util.regex.Pattern;

/**
 * @author 启梦
 */
public final class Util {
    private Util() {}
    public static final String MOD_ID = "water_is_dangerous",
            AUTHOR_SHEEP_NAME = "哈哈，我是一只倒过来的彩虹羊！而且我身上还在着火！",
            AUTHOR_SHEEP_PASSENGERS_TAG = "author_passenger";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final HashSet<Entity> GLOW_GREEN_ENTITIES = new HashSet<>();
    public static final ImmutableList<CompoundNBT> DANGER_ENTITIES;
    public static final ArrayList<EntityType<?>> FRIENDLY_ENTITY_TYPES = new ArrayList<>();
    public static final DamageSource TOO_HEAVY = new DamageSource("too_heavy");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\\\d+(\\\\.\\\\d+)?");

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

    private static CompoundNBT getAuthorSheepNbt() {
        CompoundNBT authorSheepNbt = new CompoundNBT();
        authorSheepNbt.putString("id", EntityType.SHEEP.toString());
        authorSheepNbt.putString("CustomName", AUTHOR_SHEEP_NAME);
        authorSheepNbt.putBoolean("CustomNameVisible", false);
        authorSheepNbt.putBoolean("HasVisualFire", true);
        authorSheepNbt.putInt("PortalCooldown", 2147483647);
        authorSheepNbt.put("Passengers",
                getAuthorSheepPassengers(ThreadLocalRandom.current().nextInt(30)));
        return authorSheepNbt;
    }

    private static CompoundNBT getAuthorSheepPassengers(int sum) {
        CompoundNBT nbt = new CompoundNBT();
        final CompoundNBT first = nbt;
        int colorsLength = Color.values().length;
        for (int i = 0; i < sum; i++) {
            nbt.putString("id", randomChooseFriendlyEntity());
            nbt.putString("CustomName", "消消乐第" + i + "层");
            nbt.putBoolean("HasVisualFire", true);
            nbt.putBoolean("NoGravity", true);
            ListNBT tag = new ListNBT();
            tag.add(StringNBT.valueOf(AUTHOR_SHEEP_PASSENGERS_TAG));
            tag.add(StringNBT.valueOf(i % colorsLength + ""));
            nbt.put("Tags", tag);
            if (i != sum - 1) {
                nbt.put("Passengers", nbt = new CompoundNBT());
            }
        }
        return first;
    }

    public static boolean isNumber(String str) {
        return NUMBER_PATTERN.matcher(str).matches();
    }

    static {
        initFriendlyEntityTypes();
        ImmutableList.Builder<CompoundNBT> builder = ImmutableList.<CompoundNBT>builder().add(getAuthorSheepNbt());
        DANGER_ENTITIES = builder.build();
    }
}
