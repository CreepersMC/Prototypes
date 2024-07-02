package re.imc.creeperspvp;
import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.registry.event.RegistryEvents;
import io.papermc.paper.registry.keys.EnchantmentKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import re.imc.creeperspvp.utils.Utils;
import java.util.List;
@SuppressWarnings("all")
public final class Bootstrapper implements PluginBootstrap {
    @Override
    public void bootstrap(@NotNull BootstrapContext bootstrapContext) {
        LifecycleEventManager<BootstrapContext> manager = bootstrapContext.getLifecycleManager();
        manager.registerEventHandler(RegistryEvents.ENCHANTMENT.entryAdd().newHandler(event -> {
            event.builder().maxLevel(2);
        }).filter(EnchantmentKeys.MULTISHOT));
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(Commands.literal("spawn").requires(source -> source.getExecutor() instanceof final Player player && player.getGameMode() == GameMode.SPECTATOR).executes(context -> {
                final Player player = (Player) context.getSource().getExecutor();
                if(player.getSpectatorTarget() == null) {
                    Utils.playerInit(player);
                    context.getSource().getSender().sendPlainMessage("已返回");
                    return Command.SINGLE_SUCCESS;
                } else {
                    if(Utils.attemptStopSpectatingEntity(player)) {
                        context.getSource().getSender().sendPlainMessage("已返回");
                        return Command.SINGLE_SUCCESS;
                    }
                }
                context.getSource().getSender().sendMessage(Component.text("命令冷却中", NamedTextColor.RED));
                return 0;
            }).build(), "Returns to spawn in spectator.");
            commands.register(Commands.literal("spectate").requires(source -> source.getExecutor() instanceof final Player player && player.isInvulnerable()).executes(context -> {
                final Player player = (Player) context.getSource().getExecutor();
                player.setGameMode(GameMode.SPECTATOR);
                player.sendPlainMessage("正在自由旁观");
                player.showTitle(Listener.freeSpectateTitle);
                return Command.SINGLE_SUCCESS;
            })/*.then(Commands.argument("target", ArgumentTypes.player())).executes(context -> {
                final Player player = (Player) context.getSource().getExecutor(), target = context.getArgument("target", Player.class);
                if(target != null) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.setSpectatorTarget(target);
                    return Command.SINGLE_SUCCESS;
                }
                return 0;
            })*/.build(), "", List.of("spec")); //TODO fix
        });
    }
}
