package backrooms.Init;

import backrooms.Main;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeInit {
    public static final DeferredRegister<Biome> BIOME=DeferredRegister.create(ForgeRegistries.BIOMES, Main.MOD_ID);
    public static final RegistryKey<Biome> level0=RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Main.MOD_ID, "level0"));
    static{
        Biome.Builder biomeBuilder=new Biome.Builder();
        MobSpawnInfo.Builder spawnBuilder=new MobSpawnInfo.Builder().addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.SHEEP, 2, 1, 4));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(level0, 5));
        DefaultBiomeFeatures.commonSpawns(spawnBuilder);
    }
}
