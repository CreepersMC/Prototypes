package re.imc.creeperspvp;
import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import re.imc.creeperspvp.utils.Utils;
@SuppressWarnings("all")
public final class Bootstrapper implements PluginBootstrap {
    @Override
    public void bootstrap(@NotNull BootstrapContext bootstrapContext) {
        LifecycleEventManager<BootstrapContext> manager = bootstrapContext.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(Commands.literal("spawn").requires(source -> source.getExecutor() instanceof final Player player && player.getGameMode() == GameMode.SPECTATOR).executes(source -> {
                final Player player = (Player) source.getSource().getExecutor();
                if(player.getSpectatorTarget() == null) {
                    Utils.playerInit(player);
                    source.getSource().getSender().sendPlainMessage("已返回");
                    return Command.SINGLE_SUCCESS;
                } else {
                    if(Utils.attemptStopSpectatingEntity(player)) {
                        source.getSource().getSender().sendPlainMessage("已返回");
                        return Command.SINGLE_SUCCESS;
                    }
                }
                return 0;
            }).build(), "Returns to spawn in spectator.");
        });
    }
}
