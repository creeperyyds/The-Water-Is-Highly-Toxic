package water_is_dangerous.init;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import water_is_dangerous.entity.AuthorSheepEntity;
import water_is_dangerous.Util;

/**
 * @author 启梦
 */
public final class EntityInit {
    private EntityInit() {}
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
