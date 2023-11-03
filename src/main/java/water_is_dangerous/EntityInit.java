package water_is_dangerous;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author 启梦
 */
public final class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
            ForgeRegistries.ENTITIES,
            Util.MOD_ID);
    public static final RegistryObject<EntityType<AuthorSheepEntity>> AUTHOR_SHEEP_ENTITY = ENTITY_TYPES.register(
            AuthorSheepEntity.NAME,
            () -> EntityType.Builder.of(AuthorSheepEntity::new, EntityClassification.MISC)
                    .sized(0.9f, 1.3f)
                    .clientTrackingRange(Integer.MAX_VALUE)
                    .build(AuthorSheepEntity.NAME));
}
