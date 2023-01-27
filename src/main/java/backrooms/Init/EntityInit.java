package backrooms.Init;

//import backrooms.BulletEntity;
import backrooms.Main;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITY=DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MOD_ID);
    //public static final EntityType<BulletEntity> BULLET_TYPE=EntityType.Builder.<BulletEntity>of(BulletEntity::new, EntityClassification.MISC)
    //        .sized(0.5F, 0.5F)
    //        .build("bullet");
    //public static final RegistryObject<EntityType<BulletEntity>> BULLET=ENTITY.register("bullet", () -> BULLET_TYPE);
}
