package water_is_dangerous;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

/**
 * @author 启梦
 */
public final class Util {
    private Util() {}
    public static boolean isCrashByMod = false;
    public static final String MOD_ID = "water_is_dangerous",
            AUTHOR_SHEEP_NAME = "哈哈，我是一只倒过来的彩虹羊！而且我身上还在着火！",
            AUTHOR_SHEEP_PASSENGERS_TAG = "author_passenger",
            NEW_LINE = System.lineSeparator();
    public static final Logger LOGGER = LogManager.getLogger();
    public static final HashSet<Entity> GLOW_GREEN_ENTITIES = new HashSet<>();
    public static final ImmutableList<EntityType<?>> SPAWN_ENTITIES;
    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\\\d+(\\\\.\\\\d+)?");

    public static boolean isAquatic(Entity entity) {
        return entity instanceof WaterMobEntity || entity instanceof GuardianEntity || entity instanceof TurtleEntity || entity instanceof DrownedEntity;
    }

    public static boolean isNumber(String str) {
        return NUMBER_PATTERN.matcher(str).matches();
    }

    static {
        //TODO 往这里加添加召唤实体的代码
        ImmutableList.Builder<EntityType<?>> builder = ImmutableList.builder();
        SPAWN_ENTITIES = builder.build();
    }
}
