package water_is_dangerous.mixins;

import water_is_dangerous.Main;
import water_is_dangerous.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 启梦
 */
@Mixin(Entity.class)
public abstract class MixinEntity {
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    @Inject(method = "tick", at = @At("RETURN"))
    public void tick(CallbackInfo ci) {
        Entity entityThis = (Entity) (Object) this;
        if (entityThis.level.isClientSide || !entityThis.isInWaterRainOrBubble() || Util.isAquatic(entityThis)) {
            return;
        }
        if (!(entityThis instanceof LivingEntity)) {
            entityThis.hurt(Util.SULFURIC, 4);
            return;
        }
        @SuppressWarnings("all")
        LivingEntity livingThis = (LivingEntity) entityThis;
        int armorValue = 1;
        HashMap<EquipmentSlotType, ItemStack> stacks = new HashMap<>();
        for (EquipmentSlotType type : EquipmentSlotType.values()) {
            if (type != EquipmentSlotType.MAINHAND &&
                    type != EquipmentSlotType.OFFHAND &&
                    livingThis.hasItemInSlot(type)) {
                stacks.put(type, livingThis.getItemBySlot(type));
            }
        }
        for (Map.Entry<EquipmentSlotType, ItemStack> stack : stacks.entrySet()) {
            ItemStack getStack = stack.getValue();
            if (!getStack.isDamageableItem()) {
                getStack.setCount(0);
                armorValue++;
                continue;
            }
            if (getStack.getItem() instanceof ArmorItem) {
                ArmorItem armor = (ArmorItem) getStack.getItem();
                armorValue += armor.getMaterial().getDurabilityForSlot(stack.getKey());
            }
            getStack.hurtAndBreak(RANDOM.nextInt(1, 5), livingThis, entity -> entity.broadcastBreakEvent(stack.getKey()));
        }
        entityThis.hurt(Util.SULFURIC, 2f / armorValue);
        if (livingThis.hasEffect(Main.RADIOACTIVITY.get())) {
            livingThis.addEffect(new EffectInstance(Main.RADIOACTIVITY.get(),
                    Objects.requireNonNull(livingThis.getEffect(Main.RADIOACTIVITY.get())).getDuration() + 40));
            //ci.cancel();
            return;
        }
        livingThis.addEffect(new EffectInstance(Main.RADIOACTIVITY.get(), 40));
    }
}
