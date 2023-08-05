package water_is_dangerous;

import jdk.nashorn.internal.ir.Block;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 启梦
 */
public class RainTimeMapSavedData extends WorldSavedData {
    public static final String NAME = "rain_time";
    private final HashMap<BlockPos, Integer> RAIN_TICK_TIME_MAP = new HashMap<>();
    public RainTimeMapSavedData() {
        super(NAME);
    }

    public void addPosSulfuric(BlockPos pos) {
        this.RAIN_TICK_TIME_MAP.put(pos, this.RAIN_TICK_TIME_MAP.getOrDefault(pos, 0));
        this.setDirty();
    }
    public int getPosSulfuric(BlockPos pos) {
        return this.RAIN_TICK_TIME_MAP.getOrDefault(pos, 0);
    }
    public void removePosSulfuric(BlockPos pos) {
        this.RAIN_TICK_TIME_MAP.remove(pos);
    }

    @Override
    public void load(CompoundNBT nbt) {
        ListNBT entriesNbt = nbt.getList("sulfuric_entries", 10); //getList的第一个参数是键值，第二个是元素类型，10是CompoundNBT的id
        for (INBT iEntryNbt : entriesNbt) {
            ListNBT entryNbt = (ListNBT) iEntryNbt;
            CompoundNBT posNbt = entryNbt.getCompound(0);
            Util.RAIN_TICK_TIME_MAP.put(new BlockPos(
                    posNbt.getInt("x"),
                    posNbt.getInt("y"),
                    posNbt.getInt("z")),
                    entryNbt.getCompound(0).getInt("sulfuric"));
        }
    }

    @Nonnull
    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        ListNBT entriesNbt = new ListNBT();
        for (Map.Entry<BlockPos, Integer> entry : Util.RAIN_TICK_TIME_MAP.entrySet()) {
            ListNBT entryNbt = new ListNBT();
            BlockPos key = entry.getKey();
            CompoundNBT posNbt = new CompoundNBT(), sulfuricNbt = new CompoundNBT();

            posNbt.putInt("x", key.getX());
            posNbt.putInt("y", key.getY());
            posNbt.putInt("z", key.getZ());
            sulfuricNbt.putInt("sulfuric", entry.getValue());
            entryNbt.add(posNbt);
            entryNbt.add(sulfuricNbt);
            entriesNbt.add(entryNbt);
        }
        nbt.put("sulfuric_entries", entriesNbt);
        return nbt;
    }

    public static RainTimeMapSavedData get(ServerWorld world) {
        return world.getDataStorage().computeIfAbsent(RainTimeMapSavedData::new, NAME);
    }
}
