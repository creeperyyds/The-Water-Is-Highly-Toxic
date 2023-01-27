package backrooms.Init;


import backrooms.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundInit {
    public static final SoundEvent gun_shoot=new SoundEvent(new ResourceLocation(Main.MOD_ID, "gun.shoot")).setRegistryName("gun.shoot");
    public static final SoundEvent bullet_hit_ground=new SoundEvent(new ResourceLocation(Main.MOD_ID, "bullet.hit.ground")).setRegistryName("bullet.hit.ground");
}
