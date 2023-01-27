package backrooms;

import backrooms.Init.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("backrooms")
public class Main
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID="backrooms";
    public Main() {
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("BackroomsMod event load complete!");
        BlockInit.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        LOGGER.info("BackroomsMod block load complete!");
        ItemInit.ITEM.register(FMLJavaModLoadingContext.get().getModEventBus());
        LOGGER.info("BackroomsMod item load complete!");
        BiomeInit.BIOME.register(FMLJavaModLoadingContext.get().getModEventBus());
        LOGGER.info("BackroomsMod biome load complete!");
        EntityInit.ENTITY.register(FMLJavaModLoadingContext.get().getModEventBus());
        LOGGER.info("BackroomsMod entity load complete!");
        ForgeRegistries.SOUND_EVENTS.register(SoundInit.gun_shoot);
        LOGGER.info("BackroomsMod sound load complete!");
    }

}
