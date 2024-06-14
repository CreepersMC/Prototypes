package re.imc.creeperspvp;
import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import re.imc.creeperspvp.iui.IUIManager;
//import net.kyori.adventure.title.Title;
//import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
//import java.time.Duration;
public final class Listener implements org.bukkit.event.Listener {
    public static final Listener instance = new Listener();
    private Listener() {}
    @EventHandler(priority = EventPriority.NORMAL)
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
        if(event.getDamageSource().getCausingEntity() instanceof Player player) {
            Utils.addPlayerEmeralds(player.getUniqueId(), 5);
        }
        Utils.playerInit(event.getEntity());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Utils.playerJoin(event.getPlayer());
        Utils.playerInit(event.getPlayer());
        event.getPlayer().openBook(ItemManager.welcome);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Utils.playerQuit(event.getPlayer());
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInventoryClick(InventoryClickEvent event) {
        if(event.isRightClick() && ((event.getCurrentItem() != null && event.getCurrentItem().getAmount() > 1) || event.getCursor().getAmount() > 1)) {
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInventoryDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockExplode(BlockExplodeEvent event) {
        event.blockList().clear();
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onEnttyExplode(EntityExplodeEvent event) {
        event.blockList().clear();
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onEntityShootBow(EntityShootBowEvent event) {
        if(event.getEntity() instanceof Player && event.getProjectile() instanceof Arrow arrow && arrow.getBasePotionType() == null) {
            arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
        }
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockPlace(BlockPlaceEvent event) {
        if(!event.isCancelled()) {
            switch(event.getBlockPlaced().getType()) {
                case TNT -> {
                    event.setCancelled(true);
                    Location loc = event.getBlockPlaced().getLocation().toCenterLocation();
                    loc.getWorld().spawn(loc, TNTPrimed.class, tnt -> {
                        tnt.setFuseTicks(80);
                        tnt.setSource(event.getPlayer());
                    });
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        switch(event.getCause()) {
            case ENTITY_ATTACK, ENTITY_SWEEP_ATTACK -> {
                if(event.getDamager() instanceof final Player player) {
                    final ItemStack weapon = player.getInventory().getItemInMainHand();
                    if(weapon.hasItemMeta()) {
                        final PersistentDataContainer data = weapon.getItemMeta().getPersistentDataContainer();
                        switch(data.getOrDefault(Utils.attackEffectIDKey, PersistentDataType.BYTE, (byte) -1)) {
                            case Utils.EFFECT_CHANNELING -> {}
                            case Utils.EFFECT_EXPLOSION -> {
                                Location loc1 = event.getEntity().getLocation();
                                Location loc2 = event.getDamager().getLocation();
                                loc1.add(loc2.subtract(loc1).multiply(0.25)).createExplosion(event.getDamager(), data.getOrDefault(Utils.attackEffectDataKey, PersistentDataType.FLOAT, 0f), false, false);
                            }
                            case Utils.EFFECT_FREEZE -> {
                                if(!event.isCancelled()) {
                                    event.getEntity().setFreezeTicks(Math.max(event.getEntity().getFreezeTicks(), data.getOrDefault(Utils.attackEffectDataKey, PersistentDataType.INTEGER, 0)));
                                }
                            }
                            case Utils.EFFECT_POISON -> {
                                if(event.getEntity() instanceof LivingEntity entity && !event.isCancelled()) {
                                    entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, data.getOrDefault(Utils.attackEffectDataKey, PersistentDataType.INTEGER, 0), 0));
                                }
                            }
                        }
                    }
                }
            }
        }
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
                    if(ArtifactManager.useEvents[artifactID] == ArtifactManager.INTERACT) {
                        if(ArtifactManager.useCooldowns[artifactID] != -1) {
                            player.setCooldown(event.getMaterial(), ArtifactManager.useCooldowns[artifactID]);
                        }
                        if(ArtifactManager.gainCooldowns[artifactID] != -1) {
                            final PlayerInventory inv = player.getInventory();
                            final int itemOrdinal = data.getOrDefault(Utils.itemOrdinalKey, PersistentDataType.INTEGER, -1);
                            if(item.getAmount() == 1) {
                                final int slot = Utils.findItem(inv, itemOrdinal);
                                final ItemStack unavailable = item.withType(Material.BARRIER);
                                if(slot == -1) {
                                    player.setItemOnCursor(unavailable);
                                } else {
                                    inv.setItem(slot, unavailable);
                                }
                            } else {
                                item.subtract();
                            }
                            Utils.scheduleGainArtifact(player, itemOrdinal, artifactID, item);
                        }
                    }
                }
                if(data.has(Utils.iuiIDKey, PersistentDataType.BYTE)) {
                    event.setCancelled(true);
                    IUIManager.getIUI(data.get(Utils.iuiIDKey, PersistentDataType.BYTE), player.getUniqueId(), data.get(Utils.iuiDataKey, PersistentDataType.TAG_CONTAINER)).open(player);
                }
                if(data.has(Utils.utilIDKey, PersistentDataType.BYTE) && player.getCooldown(item.getType()) == 0) {
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
                if(ArtifactManager.useEvents[artifactID] == ArtifactManager.CONSUME) {
                    event.setReplacement(null);
                    if(ArtifactManager.useCooldowns[artifactID] != -1) {
                        player.setCooldown(item.getType(), ArtifactManager.useCooldowns[artifactID]);
                    }
                    if(ArtifactManager.gainCooldowns[artifactID] != -1) {
                        final int itemOrdinal = data.getOrDefault(Utils.itemOrdinalKey, PersistentDataType.INTEGER, -1);
                        if(item.getAmount() == 1) {
                            event.setReplacement(item.withType(Material.BARRIER).asOne());
                        }
                        Utils.scheduleGainArtifact(player, itemOrdinal, artifactID, item.asOne());
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
                if(ArtifactManager.useEvents[artifactID] == ArtifactManager.LAUNCH_PROJECTILE) {
                    if(ArtifactManager.useCooldowns[artifactID] != -1) {
                        player.setCooldown(item.getType(), ArtifactManager.useCooldowns[artifactID]);
                    }
                    if(ArtifactManager.gainCooldowns[artifactID] != -1) {
                        final PlayerInventory inv = player.getInventory();
                        final ItemStack clone = item.clone();
                        final int itemOrdinal = data.getOrDefault(Utils.itemOrdinalKey, PersistentDataType.INTEGER, -1);
                        if(item.getAmount() == 1) {
                            final ItemStack unavailable = clone.withType(Material.BARRIER);
                            final int slot = Utils.findItem(inv, itemOrdinal);
                            player.getScheduler().run(CreepersPVP.instance, task -> inv.setItem(slot, unavailable), null);
                        }
                        Utils.scheduleGainArtifact(player, itemOrdinal, artifactID, clone.asOne());
                    }
                }
            }
        }
    }
}
