package water_is_dangerous;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 启梦
 */
@Mod.EventBusSubscriber
public class Weight {
    private static final Object2FloatMap<ServerPlayerEntity> SPEED_MAP = new Object2FloatOpenHashMap<>();
    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        if (event.player.level.isClientSide) {
            return;
        }
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) event.player;
        AtomicInteger dropletCount = new AtomicInteger();
        ImmutableList.of(serverPlayerEntity.inventory.items,
                serverPlayerEntity.inventory.armor,
                serverPlayerEntity.inventory.offhand).forEach(itemStacks -> {
            itemStacks.forEach(itemStack -> {
                if (itemStack.isEmpty()) {
                    return;
                }
                boolean hasAnotherItem = false;
                if (itemStack.getItem() != Main.DROPLET.get()) {
                    hasAnotherItem = true;
                } else {
                    if (dropletCount.get() == 0) {
                        dropletCount.getAndIncrement();
                        if (!SPEED_MAP.containsKey(serverPlayerEntity)) {
                            SPEED_MAP.put(serverPlayerEntity, serverPlayerEntity.getSpeed());
                            serverPlayerEntity.setSpeed(serverPlayerEntity.getSpeed() / 10f);
                        }
                    } else if (hasAnotherItem) {
                        serverPlayerEntity.hurt(Util.TOO_HEAVY, Float.MAX_VALUE);
                    }
                }
            });
            if (dropletCount.get() == 0 && SPEED_MAP.containsKey(serverPlayerEntity)) {
                serverPlayerEntity.setSpeed(SPEED_MAP.getFloat(serverPlayerEntity));
            }
        });
    }
}
