package fun.creepersmc.creeperspvp;
import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import fun.creepersmc.creeperspvp.iui.IUIManager;
//import net.kyori.adventure.title.Title;
//import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.Material;
//import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
//import java.time.Duration;
public final class Listener implements org.bukkit.event.Listener {
    public static final Listener instance = new Listener();
    private Listener() {}
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDeath(PlayerDeathEvent event) {
        /* TODO
        @SuppressWarnings("all") DamageSource source = event.getDamageSource();
        if(event.deathMessage() != null) {
            event.getPlayer().sendTitlePart(TitlePart.SUBTITLE, event.deathMessage());
            event.getPlayer().sendTitlePart(TitlePart.TIMES, Title.Times.times(Duration.ofMillis(250), Duration.ofSeconds(4), Duration.ofMillis(750)));
            if(source.getCausingEntity() instanceof Player player) {
                player.sendTitlePart(TitlePart.SUBTITLE, event.deathMessage());
                player.sendTitlePart(TitlePart.TIMES, Title.Times.times(Duration.ofMillis(250), Duration.ofSeconds(4), Duration.ofMillis(750)));
            }
        }
        */
        Utils.playerInit(event.getEntity());
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Utils.playerInit(event.getPlayer());
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        final ItemStack item = event.getItem();
        if(item != null) {
            final ItemMeta meta = item.getItemMeta();
            final Player player = event.getPlayer();
            if(meta != null) {
                final PersistentDataContainer data = meta.getPersistentDataContainer();
                if(data.has(Utils.artifactIDKey, PersistentDataType.INTEGER) && item.getType() != Material.BARRIER) {
                    final int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.INTEGER);
                    if(ItemManager.artifactsUseEvent[artifactID] == ItemManager.INTERACT) {
                        if(ItemManager.artifactsUseCooldown[artifactID] != -1) {
                            player.setCooldown(event.getMaterial(), ItemManager.artifactsUseCooldown[artifactID]);
                        }
                        if(ItemManager.artifactsGainCooldown[artifactID] != -1) {
                            Inventory inv = player.getInventory();
                            final int itemOrdinal = data.getOrDefault(Utils.itemOrdinalKey, PersistentDataType.INTEGER, -1);
                            if(item.getAmount() == 1) {
                                inv.setItem(Utils.findItem(inv, itemOrdinal), item.withType(Material.BARRIER));
                            } else {
                                item.subtract();
                            }
                            Utils.scheduleGainArtifact(player, itemOrdinal, artifactID, item);
                        }
                    }
                }
                if(data.has(Utils.iuiIDKey, PersistentDataType.BYTE)) {
                    event.setCancelled(true);
                    IUIManager.getIUI(data.get(Utils.iuiIDKey, PersistentDataType.BYTE), data.get(Utils.iuiDataKey, PersistentDataType.TAG_CONTAINER)).open(player);
                }
                if(data.has(Utils.utilIDKey, PersistentDataType.BYTE)) {
                    event.setCancelled(true);
                    switch(data.get(Utils.utilIDKey, PersistentDataType.BYTE)) {
                        case Utils.UTIL_SPAWN -> Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> Utils.spawnPlayer(player));
                    }
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        final ItemStack item = event.getItem();
        final Player player = event.getPlayer();
        if(item.hasItemMeta()) {
            final PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
            if(data.has(Utils.artifactIDKey, PersistentDataType.INTEGER)) {
                final int artifactID = data.getOrDefault(Utils.artifactIDKey, PersistentDataType.INTEGER, -1);
                if(ItemManager.artifactsUseEvent[artifactID] == ItemManager.CONSUME) {
                    event.setReplacement(null);
                    if(ItemManager.artifactsUseCooldown[artifactID] != -1) {
                        player.setCooldown(item.getType(), ItemManager.artifactsUseCooldown[artifactID]);
                    }
                    if(ItemManager.artifactsGainCooldown[artifactID] != -1) {
                        final int itemOrdinal = data.getOrDefault(Utils.itemOrdinalKey, PersistentDataType.INTEGER, -1);
                        if(item.getAmount() == 1) {
                            event.setReplacement(item.withType(Material.BARRIER).asOne());
                        }
                        Utils.scheduleGainArtifact(player, itemOrdinal, artifactID, item);
                    }
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerProjectileLaunch(PlayerLaunchProjectileEvent event) {
        final ItemStack item = event.getItemStack();
        final Player player = event.getPlayer();
        if(item.hasItemMeta()) {
            final PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
            if(data.has(Utils.artifactIDKey, PersistentDataType.INTEGER)) {
                final int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.INTEGER);
                if(ItemManager.artifactsUseEvent[artifactID] == ItemManager.LAUNCH_PROJECTILE) {
                    if(ItemManager.artifactsUseCooldown[artifactID] != -1) {
                        player.setCooldown(item.getType(), ItemManager.artifactsUseCooldown[artifactID]);
                    }
                    if(ItemManager.artifactsGainCooldown[artifactID] != -1) {
                        final Inventory inv = player.getInventory();
                        final ItemStack clone = item.clone();
                        final int itemOrdinal = data.getOrDefault(Utils.itemOrdinalKey, PersistentDataType.INTEGER, -1);
                        if(item.getAmount() == 1) {
                            final ItemStack disabled = clone.withType(Material.BARRIER);
                            final int slot = Utils.findItem(inv, itemOrdinal);
                            player.getScheduler().run(CreepersPVP.instance, task -> inv.setItem(slot, disabled), null);
                        }
                        Utils.scheduleGainArtifact(player, itemOrdinal, artifactID, clone.asOne());
                    }
                }
            }
        }
    }
}
