package water_is_dangerous;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * @author 启梦
 */
public class AuthorSheepEntity extends SheepEntity {
    private int shearCount = 0, hurtCount = 0;
    private boolean isCrashOrRestartAndShear = false;
    private final PlayerList PLAYER_LIST;
    public AuthorSheepEntity(EntityType<? extends SheepEntity> entityType, World world) {
        super(entityType, world);
        this.PLAYER_LIST = Objects.requireNonNull(world.getServer()).getPlayerList();
    }


    @Override
    public boolean isShearable(@Nonnull ItemStack item, World world, BlockPos pos) {
        switch (shearCount++) {
            case 0:
                PLAYER_LIST.broadcastMessage(new TranslationTextComponent("message.author.shear.first"),
                                ChatType.SYSTEM,
                                Util.NIL_UUID);
                break;
            case 1:
                PLAYER_LIST.broadcastMessage(new TranslationTextComponent("message.author.shear.second"),
                                ChatType.SYSTEM,
                                Util.NIL_UUID);
                break;
            case 2:
                PLAYER_LIST.broadcastMessage(new TranslationTextComponent("message.author.shear.third"),
                                ChatType.SYSTEM,
                                Util.NIL_UUID);
                break;
            case 3:
                PLAYER_LIST.broadcastMessage(new TranslationTextComponent("message.author.shear.last"),
                                ChatType.SYSTEM,
                                Util.NIL_UUID);
                Objects.requireNonNull(world.getServer()).close();

                break;
        }
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float volume) {
        Entity entity = source.getEntity();
        if (entity == null) {
            return false;
        }
        if (!(entity instanceof PlayerEntity)) {
            entity.kill();
        }
        switch (hurtCount++) {
            case 0:

        }
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {

    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        if ((isCrashOrRestartAndShear = nbt.contains("shearCount")) && (this.shearCount = nbt.getInt("shearCount")) > 0) {
            Logger logger = LogManager.getLogger();
            logger.info("既然你都看到这了，那我就跟你扯一些题外话⑧");
            logger.info("其实这个最开始就是想蹭三体，但一直没做，后来又看到了核污染水的信息，就又想起来了，就开始做。");
            logger.info("但是由于学业的问题一直没做好（悲");
            logger.info("好像到现在核污染水也没有造成大危害？真是幸运，但不代表以后就不会没事。");
            logger.info("对了，总之————");
            logger.info("——————————");
            logger.warn("剪你妈");
        }
    }
}
