package backrooms.event;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class Hurt {
    private static float health;
    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public static void onHurt(LivingHurtEvent event){
        LivingEntity living=(LivingEntity) event.getEntity();
        if (living.getTags().contains("supermode")) {
            event.setCanceled(true);
            return;
        }
        health=living.getHealth()+event.getAmount();
    }
    @SubscribeEvent(priority=EventPriority.LOWEST, receiveCanceled=true)
    public static void onHurt2(LivingHurtEvent event){
        if (event.getEntity() instanceof LivingEntity){
            LivingEntity living=(LivingEntity) event.getEntity();
            living.sendMessage(ITextComponent.nullToEmpty("Now health:"+living.getHealth()+",Hurt "+(health-living.getHealth())), living.getUUID());
        }
    }
}
