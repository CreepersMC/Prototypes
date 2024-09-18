package re.imc.prototypes;
import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.registry.event.RegistryEvents;
import io.papermc.paper.registry.keys.EnchantmentKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import re.imc.prototypes.utils.Utils;
import java.util.List;
@SuppressWarnings("all")
public final class Bootstrapper implements PluginBootstrap {
    private static final Title remainStationary = Title.title(Component.text("传送即将开始..."), Component.text("请不要在传送过程移动"), Title.DEFAULT_TIMES);
    private static final Component teleportFailMessage = Component.text("传送失败！请不要在传送过程中移动", NamedTextColor.RED);
    private static final TextReplacementConfig.Builder replacePlayer = TextReplacementConfig.builder().matchLiteral("%player%");
    @Override
    public void bootstrap(@NotNull BootstrapContext bootstrapContext) {
        LifecycleEventManager<BootstrapContext> manager = bootstrapContext.getLifecycleManager();
        manager.registerEventHandler(RegistryEvents.ENCHANTMENT.entryAdd().newHandler(event -> {
            event.builder().maxLevel(2);
        }).filter(EnchantmentKeys.MULTISHOT));
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(Commands.literal("spawn").requires(source -> source.getExecutor() instanceof final Player player && (!player.isInvulnerable() || player.getGameMode().isInvulnerable())).executes(context -> {
                final Player player = (Player) context.getSource().getExecutor();
                if(player.getGameMode() == GameMode.SPECTATOR) {
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
                } else {
                    if(Utils.runOnStationaryPlayer(player, player0 -> {
                        Utils.playerInit(player0);
                        context.getSource().getSender().sendPlainMessage("已返回");
                    }, player0 -> {
                        player0.sendActionBar(teleportFailMessage);
                    })) {
                        context.getSource().getSender().sendPlainMessage("即将返回...");
                        player.showTitle(remainStationary);
                        return Command.SINGLE_SUCCESS;
                    } else {
                        context.getSource().getSender().sendMessage(Component.text("命令冷却中", NamedTextColor.RED));
                    }
                }
                return 0;
            }).build(), "Returns to spawn.");
            commands.register(Commands.literal("spectate").requires(source -> source.getExecutor() instanceof final Player player && player.isInvulnerable()).executes(context -> {
                final Player player = (Player) context.getSource().getExecutor(), target;
                player.setGameMode(GameMode.SPECTATOR);
                player.sendPlainMessage("正在自由旁观");
                player.showTitle(Listener.freeSpectateTitle);
                return Command.SINGLE_SUCCESS;
            }).then(Commands.argument("target", ArgumentTypes.player()).executes(context -> {
                final Player player = (Player) context.getSource().getExecutor(), target = context.getArgument("target", PlayerSelectorArgumentResolver.class).resolve(context.getSource()).getFirst();
                if(target != null) {
                    if(target.getUniqueId().equals(player.getUniqueId())) {
                        player.sendMessage(Component.text("不能旁观自己", NamedTextColor.RED));
                        return 0;
                    }
                    player.setGameMode(GameMode.SPECTATOR);
                    player.setSpectatorTarget(target);
                    player.sendMessage(Component.text("正在旁观 %player%").replaceText(replacePlayer.replacement(target.displayName()).build()));
                    player.showTitle(Title.title(Component.text("正在旁观 %player%").replaceText(replacePlayer.replacement(target.displayName()).build()), Component.text("输入 /spawn 退出观战"), Listener.spectateTitleTimes));
                    return Command.SINGLE_SUCCESS;
                }
                return 0;
            })).build(), "", List.of("spec"));
        });
    }
}
