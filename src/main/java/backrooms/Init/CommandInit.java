package backrooms.Init;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.UUID;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class CommandInit {
    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher=event.getDispatcher();
        dispatcher.register(Commands.literal("test")
                .requires(commandSource -> {return commandSource.hasPermission(0);})
                .then(
                        Commands.literal("test1")
                                .executes(new Command<CommandSource>() {
                                    @Override
                                    public int run(CommandContext<CommandSource> context) {
                                        context.getSource().sendFailure(ITextComponent.nullToEmpty("你好"));
                                        return 0;
                                    }
                                })));
        dispatcher.register(Commands.literal("supermode").requires(commandSource -> {return commandSource.hasPermission(2);}).then(
                        Commands.literal("open")
                                .executes(context -> {return setMode(ImmutableList.of(context.getSource().getEntityOrException()), true);})
                                .then(
                                        Commands.argument("targets", EntityArgument.entities())
                                                .executes(context -> {return setMode(EntityArgument.getEntities(context, "targets"), true);})))
                .then(
                        Commands.literal("close")
                                .executes(context -> {return setMode(ImmutableList.of(context.getSource().getEntityOrException()), false);})
                                .then(
                                        Commands.argument("targets", EntityArgument.entities())
                                                .executes(context -> {return setMode(EntityArgument.getEntities(context, "targets"), false);})
                                )
                ));
        dispatcher.register(Commands.literal("damage")
                .requires(commandSource -> {return commandSource.hasPermission(2);})
                .then(
                        Commands.argument("from", EntityArgument.entities())
                                .then(
                                        Commands.argument("to", EntityArgument.entities())
                                                .then(
                                                        Commands.argument("damage", FloatArgumentType.floatArg())
                                                                .executes(context -> {return damage(
                                                                        EntityArgument.getEntities(context, "from"),
                                                                        EntityArgument.getEntities(context, "to"),
                                                                        FloatArgumentType.getFloat(context, "damage"), context);})))));
        dispatcher.register(Commands.literal("removeentity")
                .requires(commandSource -> {return commandSource.hasPermission(2);})
                .then(
                        Commands.argument("entities", EntityArgument.entities())
                                .executes(context -> {return remove(EntityArgument.getEntities(context, "entities"));})
                ));
    }
    private static int setMode(Collection<? extends Entity> entities, boolean open){
        for (Entity entity : entities){
            try{
                if (open){
                    entity.getTags().add("supermode");
                } else {
                    entity.getTags().remove("supermode");
                }
            } catch (Exception ex){}
        }
        return 1;
    }
    private static int damage(Collection<? extends Entity> from, Collection<? extends Entity> to, float damage, CommandContext<CommandSource> context){
        int damageMob=0;
        for(Entity fromEntity : from){
            for (Entity toEntity : to){
                if (toEntity.hurt(DamageSource.mobAttack((LivingEntity) fromEntity), damage)){
                        damageMob++;
                }
            }
        }
        if (damageMob==to.size()){
            context.getSource().sendSuccess(ITextComponent.nullToEmpty(damageMob+" entity damage."), true);
        } else {
            context.getSource().sendFailure(ITextComponent.nullToEmpty(damageMob+" entity damage,"+(to.size()-damageMob)+" Not hurt"));
        }
        return damageMob>0 ? 1 : 0;
    }
    private static int remove(Collection<? extends Entity> entities){
        for (Entity entity : entities){
            entity.remove();
        }
        return 1;
    }
}
