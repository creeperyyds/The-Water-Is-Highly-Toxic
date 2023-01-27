package backrooms.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.Explosion;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.ArrayList;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class Boom {
    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public static void send(ServerChatEvent event){
        if(event.getMessage().equals("114514"))
        {
            event.setCanceled(true);
            PlayerEntity player = event.getPlayer();
            ArrayList<Entity> list=new ArrayList<Entity>();
            int i=0;
            player.sendMessage(ITextComponent.nullToEmpty("Begin count"), player.getUUID());
            for (Entity entity : player.level.getPartEntities()){
                if ((!(entity instanceof PlayerEntity)) && Math.abs(entity.getX() - player.getX())<=30
                && Math.abs(entity.getY() - player.getY())<=15 && Math.abs(entity.getZ() - player.getZ())<=30){
                    list.add(entity);
                }
            }
            player.sendMessage(ITextComponent.nullToEmpty("End count\n"+(list==null)+"\n"+(list.size())), player.getUUID());
            for (Entity entity : list){
                player.sendMessage(ITextComponent.nullToEmpty(String.valueOf(entity.getCustomName()==null)), player.getUUID());
            }
            for (Entity entity : list){
                entity.level.explode(entity, entity.getX(), entity.getY(), entity.getZ(), 5F,Explosion.Mode.NONE);
            }
        }
    }
}
