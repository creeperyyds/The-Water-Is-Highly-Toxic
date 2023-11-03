package water_is_dangerous.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import water_is_dangerous.init.EntityInit;
import water_is_dangerous.render.AuthorSheepRenderer;

/**
 * @author 启梦
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.AUTHOR_SHEEP_ENTITY.get(), AuthorSheepRenderer::new);
    }
}
