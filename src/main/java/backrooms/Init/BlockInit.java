package backrooms.Init;

import backrooms.Level0FloorBlock;
import backrooms.Main;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS=DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);
    public static final RegistryObject<Block> Level0Block=BLOCKS.register("level0block", () -> new Level0FloorBlock(AbstractBlock.Properties.of(Material.STONE).strength(-1).sound(SoundType.WOOD)));
}
