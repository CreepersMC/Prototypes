package re.imc.creeperspvp;
import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import com.destroystokyo.paper.event.player.PlayerReadyArrowEvent;
import com.destroystokyo.paper.event.player.PlayerStopSpectatingEntityEvent;
import fr.mrmicky.fastinv.FastInv;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockSupport;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Fire;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.damage.DamageSource;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import re.imc.creeperspvp.items.ArmorManager;
import re.imc.creeperspvp.items.ArtifactManager;
import re.imc.creeperspvp.iui.IUIManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import re.imc.creeperspvp.utils.DatabaseUtils;
import re.imc.creeperspvp.utils.Utils;
import java.time.Duration;
import java.util.*;
public final class Listener implements org.bukkit.event.Listener {
    public static final Listener instance = new Listener();
    private static final Component elytraBoostWithExplosionWarning = Component.textOfChildren(Component.text("你正在使用带有烟火之星的烟花火箭加速滑翔！", NamedTextColor.YELLOW), Component.newline(), Component.text("请空手按 ", NamedTextColor.YELLOW), Component.keybind("key.attack", NamedTextColor.YELLOW), Component.text(" 进行加速！", NamedTextColor.YELLOW));
    private static final Title.Times spectateTitleTimes = Title.Times.times(Duration.ofMillis(500), Duration.ofMillis(2500), Duration.ofMillis(500));
    private static final Component leaveDeathSpectate = Component.textOfChildren(Component.text("按 "), Component.keybind("key.sneak"), Component.text(" 退出旁观"));
    public static final Title freeSpectateTitle = Title.title(Component.text("正在自由旁观"), Component.text("输入 /spawn 退出观战"), spectateTitleTimes);
    private static final BlockFace[] NEIGHBORS = new BlockFace[] {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};
    private Listener() {}
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDeath(PlayerDeathEvent event) {
        final Player player = event.getPlayer();
        final Player killer = player.getKiller();
        @SuppressWarnings("all")
        final DamageSource source = event.getDamageSource();
        final Component deathMessage = event.deathMessage();
        if(deathMessage != null) {
            player.sendActionBar(deathMessage);
            if(killer != null) {
                killer.sendActionBar(deathMessage);
            }
        }
        if(killer != null && !killer.getUniqueId().equals(player.getUniqueId())) {
            final double flag = Math.sqrt(1D * DatabaseUtils.fetchPlayerKills(player.getUniqueId()) / Math.max(DatabaseUtils.fetchPlayerDeaths(player.getUniqueId()), 1)) * Math.log1p(player.getLevel());
            DatabaseUtils.addPlayerEmeralds(killer.getUniqueId(), Math.max(Math.round(5 * flag), 1));
            DatabaseUtils.addPlayerXp(killer.getUniqueId(), Math.toIntExact(Math.max(Math.round(2 * flag), 1)));
            DatabaseUtils.incrementPlayerKills(killer.getUniqueId());
        }
        player.showTitle(killer == null ? freeSpectateTitle : Title.title(Component.text("正在旁观 ").append(killer.displayName()), leaveDeathSpectate, spectateTitleTimes));
        DatabaseUtils.incrementPlayerDeaths(player.getUniqueId());
        Utils.playerDeath(player);
        Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> {
            player.setGameMode(GameMode.SPECTATOR);
            player.setSpectatorTarget(killer);
        });
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerStopSpectatingEntity(PlayerStopSpectatingEntityEvent event) {
        if(!Utils.attemptStopSpectatingEntity(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        DatabaseUtils.playerJoin(event.getPlayer().getUniqueId());
        Utils.playerJoin(event.getPlayer());
        Utils.playerInit(event.getPlayer());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event) {
        DatabaseUtils.playerQuit(event.getPlayer().getUniqueId());
        Utils.playerQuit(event.getPlayer());
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onEntityPickUpItem(EntityPickupItemEvent event) {
        event.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPickUpArrow(PlayerPickupArrowEvent event) {
        event.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInventoryClick(InventoryClickEvent event) {
        if((event.isRightClick() && ((event.getCurrentItem() != null && event.getCurrentItem().getAmount() > 1) || event.getCursor().getAmount() > 1)) || event.getSlotType() == InventoryType.SlotType.ARMOR) {
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInventoryDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onLeavesDecay(LeavesDecayEvent event) {
        event.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockExplode(BlockExplodeEvent event) {
        event.blockList().clear();
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().clear();
        final PersistentDataContainer data = event.getEntity().getPersistentDataContainer();
        if(data.has(Utils.sourceEntityKey, Utils.UUID) && data.has(Utils.artifactIDKey, PersistentDataType.SHORT)) {
            final Player player = Bukkit.getPlayer(data.get(Utils.sourceEntityKey, Utils.UUID));
            if(player != null) {
                final int itemOrdinal = data.getOrDefault(Utils.itemOrdinalKey, PersistentDataType.INTEGER, -1);
                final PlayerInventory inv = player.getInventory();
                final int slot = Utils.findItem(inv, itemOrdinal);
                final ItemStack item = inv.getItem(slot);
                if(item != null && item.hasItemMeta()) {
                    final PersistentDataContainer itemData = item.getItemMeta().getPersistentDataContainer();
                    if(itemData.has(Utils.artifactIDKey, PersistentDataType.SHORT)) {
                        final int artifactID = itemData.get(Utils.artifactIDKey, PersistentDataType.SHORT);
                        if((ArtifactManager.useEvents[artifactID] & ArtifactManager.ENTITY_EXPLODE) > 0) {
                            if(ArtifactManager.gainCooldowns[artifactID] != -1) {
                                final ItemStack clone = item.clone();
                                if(item.getAmount() == 1) {
                                    final ItemStack unavailable = clone.withType(Material.BARRIER);
                                    player.getScheduler().run(CreepersPVP.instance, task -> inv.setItem(slot, unavailable), null);
                                }
                                Utils.scheduleGainArtifact(player, itemOrdinal, artifactID, clone.withType(ArtifactManager.artifacts[artifactID][1].getType()).asOne());
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryOpen(InventoryOpenEvent event) {
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE && !(event.getInventory() instanceof PlayerInventory || event.getInventory().getHolder() instanceof FastInv)) {
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE && event.getBlock().getType() != Material.FIRE && event.getBlock().getType() != Material.SOUL_FIRE) {
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        final Player player = event.getPlayer();
        final short armor = Utils.getArmor(player);
        if(armor == ArmorManager.GHOSTLY_ARMOR) {
            if(event.isSneaking()) {
                player.addPotionEffects(List.of(ArmorManager.effects[ArmorManager.GHOST_KINDLER]));
            } else {
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityShootBow(EntityShootBowEvent event) {
        ItemStack bow = event.getBow();
        if(bow != null) {
            bow.editMeta(meta -> {
                PersistentDataContainer data = meta.getPersistentDataContainer();
                data.remove(Utils.customItemStartUsingTimeKey);
                data.remove(Utils.customItemUsedTimeKey);
                data.copyTo(event.getProjectile().getPersistentDataContainer(), true);
                event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(data.getOrDefault(Utils.projectileVelocityKey, PersistentDataType.FLOAT, 1f)));
            });
        }
        if(event.getProjectile() instanceof AbstractArrow arrow) {
            arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
        }
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockFade(BlockFadeEvent event) {
        if(event.getBlock().getType() != Material.FIRE) {
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockForm(BlockFormEvent event) {
        final Material material = event.getNewState().getType();
        if(material == Material.COBBLESTONE || material == Material.STONE || material == Material.OBSIDIAN) {
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBurn(BlockBurnEvent event) {
        event.setCancelled(true);
        final Block ignitingBlock = event.getIgnitingBlock();
        if(ignitingBlock != null && ignitingBlock.getBlockData() instanceof Fire fire) {
            final BlockFace ignore = ignitingBlock.getFace(event.getBlock());
            if(ignore == BlockFace.DOWN && fire.getAge() == 15 && Utils.random.nextInt(4) == 0) {
                ignitingBlock.setType(Material.AIR);
            } else {
                boolean flag = true;
                for(final BlockFace face : NEIGHBORS) {
                    if(face != ignore && ignitingBlock.getRelative(face).getType().isFlammable()) {
                        flag = false;
                        break;
                    }
                }
                if(flag && ((ignore != BlockFace.DOWN && ignitingBlock.getRelative(BlockFace.DOWN).getBlockData().isFaceSturdy(BlockFace.UP, BlockSupport.FULL)) || fire.getAge() > 3)) {
                    ignitingBlock.setType(Material.AIR);
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockSpread(BlockSpreadEvent event) {
        if(event.getSource().getType() == Material.FIRE && Utils.random.nextDouble() >= 1) {
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockFromTo(BlockFromToEvent event) {
        transientBlockForm(event.getToBlock().getLocation().clone(), event.getBlock().getType(), event.getToBlock().getBlockData(), false, true);
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onProjectileHit(ProjectileHitEvent event) {
        PersistentDataContainer data = event.getEntity().getPersistentDataContainer();
        switch(data.getOrDefault(Utils.artifactIDKey, PersistentDataType.SHORT, (short) -1)) {
            case ArtifactManager.LIGHTNING_CHARGE -> {
                Location loc = event.getEntity().getLocation();
                loc.getWorld().strikeLightning(loc);
            }
            case ArtifactManager.ICE_CHARGE -> {
                for(Entity hitEntity : event.getEntity().getNearbyEntities(1, 1, 1)) {
                    hitEntity.setFreezeTicks(Math.max(hitEntity.getFreezeTicks(), 200));
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerReadyArrow(PlayerReadyArrowEvent event) {
        final Player player = event.getPlayer();
        if(player.getActiveItemUsedTime() > 0) {
            final ItemStack arrow = event.getArrow();
            if(arrow.hasItemMeta()) {
                final PersistentDataContainer data = arrow.getItemMeta().getPersistentDataContainer();
                if(data.has(Utils.artifactIDKey, PersistentDataType.SHORT)) {
                    final int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.SHORT);
                    if((ArtifactManager.useEvents[artifactID] & ArtifactManager.READY_ARROW) > 0) {
                        if(ArtifactManager.useCooldowns[artifactID] != -1) {
                            player.setCooldown(arrow.getType(), ArtifactManager.useCooldowns[artifactID]);
                        }
                        if(ArtifactManager.gainCooldowns[artifactID] != -1) {
                            final PlayerInventory inv = player.getInventory();
                            final ItemStack clone = arrow.clone();
                            final int itemOrdinal = data.getOrDefault(Utils.itemOrdinalKey, PersistentDataType.INTEGER, -1);
                            if(arrow.getAmount() == 1) {
                                final ItemStack unavailable = clone.withType(Material.BARRIER);
                                final int slot = Utils.findItem(inv, itemOrdinal);
                                Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> inv.setItem(slot, unavailable));
                            }
                            Utils.scheduleGainArtifact(player, itemOrdinal, artifactID, clone.asOne());
                        }
                    }
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerElytraBoost(PlayerElytraBoostEvent event) {
        final ItemStack item = event.getItemStack();
        final Player player = event.getPlayer();
        if(item.hasItemMeta()) {
            if(((FireworkMeta) item.getItemMeta()).hasEffects()) {
                player.sendActionBar(elytraBoostWithExplosionWarning);
            }
            final PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
            if(data.has(Utils.artifactIDKey, PersistentDataType.SHORT)) {
                final int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.SHORT);
                if((ArtifactManager.useEvents[artifactID] & ArtifactManager.ELYTRA_BOOST) > 0) {
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
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = player.getInventory().getItem(event.getHand());
        event.setItemStack(item.withType(Material.BUCKET));
        if(item.hasItemMeta()) {
            final PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
            if(data.has(Utils.artifactIDKey, PersistentDataType.SHORT)) {
                final int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.SHORT);
                if((ArtifactManager.useEvents[artifactID] & ArtifactManager.EMPTY_BUCKET) > 0) {
                    final Block block = event.getBlock();
                    transientBlockForm(block.getLocation().clone(), item.getType(), block.getBlockData(), false, true);
                    if(ArtifactManager.useCooldowns[artifactID] != -1) {
                        player.setCooldown(item.getType(), ArtifactManager.useCooldowns[artifactID]);
                    }
                    if(ArtifactManager.gainCooldowns[artifactID] != -1) {
                        Utils.scheduleGainArtifact(player, data.getOrDefault(Utils.itemOrdinalKey, PersistentDataType.INTEGER, -1), artifactID, item.clone());
                    }
                }
            } else {
                if(player.getGameMode() != GameMode.CREATIVE) {
                    event.setCancelled(true);
                }
            }
        } else {
            if(player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        final ItemStack item = event.getItemInHand();
        if(item.getType() == Material.BARRIER) {
            event.setCancelled(true);
            return;
        }
        final Player player = event.getPlayer();
        if(item.hasItemMeta()) {
            final PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
            if(data.has(Utils.artifactIDKey, PersistentDataType.SHORT)) {
                final int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.SHORT);
                if((ArtifactManager.useEvents[artifactID] & ArtifactManager.PLACE_BLOCK) > 0) {
                    final Block placedBlock = event.getBlockPlaced();
                    final Location location = placedBlock.getLocation().clone();
                    switch(artifactID) {
                        case ArtifactManager.TNT:
                            event.setCancelled(true);
                            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> item.subtract());
                            Location tntLocation = location.toCenterLocation();
                            tntLocation.getWorld().spawn(tntLocation, TNTPrimed.class, tnt -> {
                                tnt.setFuseTicks(80);
                                tnt.setSource(event.getPlayer());
                            });
                            break;
                        case ArtifactManager.SWEET_BERRIES:
                            placedBlock.applyBoneMeal(BlockFace.UP);
                        default:
                            transientBlockForm(location, placedBlock.getType(), event.getBlockReplacedState().getBlockData(), true, true);
                    }
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
            } else {
                if(player.getGameMode() != GameMode.CREATIVE) {
                    event.setCancelled(true);
                }
            }
        } else {
            if(player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityPlace(EntityPlaceEvent event) {
        final Player player = event.getPlayer();
        final PlayerInventory inv = player.getInventory();
        final ItemStack item = inv.getItem(event.getHand());
        final PersistentDataContainer entityData = event.getEntity().getPersistentDataContainer();
        entityData.set(Utils.sourceEntityKey, Utils.UUID, player.getUniqueId());
        if(item.hasItemMeta()) {
            final PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
            final int itemOrdinal = data.getOrDefault(Utils.itemOrdinalKey, PersistentDataType.INTEGER, -1);
            data.copyTo(entityData, false);
            if(data.has(Utils.artifactIDKey, PersistentDataType.SHORT)) {
                final int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.SHORT);
                if((ArtifactManager.useEvents[artifactID] & ArtifactManager.PLACE_ENTITY) > 0) {
                    if(ArtifactManager.useCooldowns[artifactID] != -1) {
                        player.setCooldown(item.getType(), ArtifactManager.useCooldowns[artifactID]);
                    }
                    if(ArtifactManager.gainCooldowns[artifactID] != -1) {
                        final ItemStack clone = item.clone();
                        if(item.getAmount() == 1) {
                            final ItemStack unavailable = clone.withType(Material.BARRIER);
                            final int slot = Utils.findItem(inv, itemOrdinal);
                            player.getScheduler().run(CreepersPVP.instance, task -> inv.setItem(slot, unavailable), null);
                        }
                        Utils.scheduleGainArtifact(player, itemOrdinal, artifactID, clone.asOne());
                    }
                } else if((ArtifactManager.useEvents[artifactID] & ArtifactManager.AFTER_PLACE_ENTITY) > 0) {
                    if(ArtifactManager.useCooldowns[artifactID] != -1) {
                        player.setCooldown(item.getType(), ArtifactManager.useCooldowns[artifactID]);
                    }
                    if(ArtifactManager.gainCooldowns[artifactID] != -1) {
                        final ItemStack clone = item.clone();
                        if(item.getAmount() == 1) {
                            final ItemStack inUse = clone.withType(Material.BARRIER);
                            final int slot = Utils.findItem(inv, itemOrdinal);
                            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> inv.setItem(slot, inUse));
                        }
                    }
                }
            } else {
                if(player.getGameMode() != GameMode.CREATIVE) {
                    event.setCancelled(true);
                }
            }
        } else {
            if(player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            event.setDamage(event.getDamage() * 0.7);
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        switch(event.getCause()) {
            case ENTITY_ATTACK, ENTITY_SWEEP_ATTACK -> {
                if(event.getDamageSource().getCausingEntity() instanceof final LivingEntity damager) {
                    final EntityEquipment equipment = damager.getEquipment();
                    if(equipment != null) {
                        final ItemStack weapon = equipment.getItemInMainHand();
                        if(weapon.hasItemMeta()) {
                            final PersistentDataContainer data = weapon.getItemMeta().getPersistentDataContainer();
                            switch(data.getOrDefault(Utils.meleeAttackEffectIDKey, PersistentDataType.BYTE, (byte) -1)) {
                                case Utils.MELEE_EFFECT_CHANNELING -> {
                                }
                                case Utils.MELEE_EFFECT_EXPLOSION -> {
                                    Location loc1 = event.getEntity().getLocation();
                                    Location loc2 = damager.getLocation();
                                    loc1.add(loc2.subtract(loc1).multiply(0.25)).createExplosion(damager, data.getOrDefault(Utils.meleeAttackEffectDataKey, PersistentDataType.FLOAT, 0f) * (damager instanceof HumanEntity humanDamager ? humanDamager.getAttackCooldown() : 1), false, false);
                                }
                                case Utils.MELEE_EFFECT_FREEZE -> {
                                    if(event.getFinalDamage() > 0 && equipment.getBoots().getType() != Material.LEATHER_BOOTS) { //TODO FIX
                                        event.getEntity().setFreezeTicks(Math.max(event.getEntity().getFreezeTicks(), data.getOrDefault(Utils.meleeAttackEffectDataKey, PersistentDataType.INTEGER, 0)));
                                    }
                                }
                                case Utils.MELEE_EFFECT_POISON -> {
                                    if(event.getEntity() instanceof LivingEntity entity && event.getFinalDamage() > 0) {
                                        entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, data.getOrDefault(Utils.meleeAttackEffectDataKey, PersistentDataType.INTEGER, 0), 0));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            case PROJECTILE -> {
                if(event.getDamageSource().getDirectEntity() instanceof AbstractArrow arrow) {
                    final PersistentDataContainer data = arrow.getPersistentDataContainer();
                    switch(data.getOrDefault(Utils.rangedAttackEffectIDKey, PersistentDataType.BYTE, (byte) -1)) {
                        case Utils.RANGED_EFFECT_CHANNELING -> {
                            final Location loc = event.getEntity().getLocation();
                            final double chance = Utils.getChannellingChance(loc);
                            if((!(arrow instanceof Trident) || !arrow.getItemStack().hasItemMeta() || !arrow.getItemStack().getItemMeta().hasEnchant(Enchantment.CHANNELING) || chance < 1) && Utils.random.nextDouble() < chance) {
                                loc.getWorld().strikeLightning(loc);
                            }
                        }
                        case Utils.RANGED_EFFECT_FREEZE -> {
                            if(event.getFinalDamage() > 0) { //TODO
                                event.getEntity().setFreezeTicks(Math.max(event.getEntity().getFreezeTicks(), data.getOrDefault(Utils.rangedAttackEffectDataKey, PersistentDataType.INTEGER, 0)));
                            }
                        }
                        case Utils.RANGED_EFFECT_WIND -> Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> {
                            final Entity entity = event.getEntity();
                            entity.setVelocity(entity.getVelocity().add(new Vector(0, arrow.getVelocity().length() * data.getOrDefault(Utils.rangedAttackEffectDataKey, PersistentDataType.DOUBLE, 0d) * (entity instanceof LivingEntity livingEntity ? livingEntity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE) != null ? 1 - livingEntity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getValue() : 1 : 1), 0)));
                        });
                    }
                }
            }
        }
        if(event.getDamageSource().getCausingEntity() instanceof final LivingEntity damager) {
            final EntityEquipment equipment = damager.getEquipment();
            if(equipment != null) {
                for(ItemStack armor : equipment.getArmorContents()) {
                    if(armor != null && armor.hasItemMeta()) {
                        final PersistentDataContainer data = armor.getItemMeta().getPersistentDataContainer();
                        switch(data.getOrDefault(Utils.armorAuraIDKey, PersistentDataType.BYTE, (byte) -1)) {
                            case Utils.ARMOR_AURA_LIFESTEAL -> damager.heal(event.getFinalDamage() * data.getOrDefault(Utils.armorAuraDataKey, PersistentDataType.FLOAT, 1f), EntityRegainHealthEvent.RegainReason.WITHER);
                        }
                    }
                }
            }
        }
        if(event.getEntity() instanceof final LivingEntity damagee) {
            final EntityEquipment equipment = damagee.getEquipment();
            if(equipment != null) {
                for(ItemStack armor : equipment.getArmorContents()) {
                    if(armor != null && armor.hasItemMeta()) {
                        final PersistentDataContainer data = armor.getItemMeta().getPersistentDataContainer();
                        switch(data.getOrDefault(Utils.armorAuraIDKey, PersistentDataType.BYTE, (byte) -1)) {
                            case Utils.ARMOR_AURA_FREEZE -> {
                                final Entity damager = event.getDamager();
                                final EntityEquipment damagerEquipment = damager instanceof LivingEntity livingDamager ? livingDamager.getEquipment() : null;
                                if(event.getFinalDamage() > 0 && (damagerEquipment == null || damagerEquipment.getBoots().getType() != Material.LEATHER_BOOTS)) {
                                    damager.setFreezeTicks(Math.max(damager.getFreezeTicks(), data.getOrDefault(Utils.armorAuraDataKey, PersistentDataType.INTEGER, 0)));
                                }
                            }
                            case Utils.ARMOR_AURA_SHULKING -> {
                                if(damagee.getHealth() - event.getFinalDamage() < damagee.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * 0.5) {
                                    damagee.teleport(Utils.shulk(damagee.getLocation()));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityResurrect(EntityResurrectEvent event) {
        if(!event.isCancelled() && event.getEntity() instanceof final Player player) {
            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> {
                final short armor = Utils.getArmor(player);
                if(armor != -1) {
                    player.addPotionEffects(List.of(ArmorManager.effects[armor]));
                }
            });
            final PlayerInventory inv = player.getInventory();
            final ItemStack item = inv.getItem(event.getHand());
            if(item.hasItemMeta()) {
                final PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
                if(data.has(Utils.artifactIDKey, PersistentDataType.SHORT)) {
                    final int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.SHORT);
                    if((ArtifactManager.useEvents[artifactID] & ArtifactManager.RESURRECT) > 0) {
                        if(ArtifactManager.useCooldowns[artifactID] != -1) {
                            player.setCooldown(item.getType(), ArtifactManager.useCooldowns[artifactID]);
                        }
                        if(ArtifactManager.gainCooldowns[artifactID] != -1) {
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
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        final ItemStack item = event.getItem();
        final Player player = event.getPlayer();
        if(item != null && !item.isEmpty()) {
            final ItemMeta meta = item.getItemMeta();
            if(meta != null) {
                final PersistentDataContainer data = meta.getPersistentDataContainer();
                if(data.has(Utils.artifactIDKey, PersistentDataType.SHORT) && item.getType() != Material.BARRIER) {
                    final int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.SHORT);
                    if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        Class<? extends Projectile> clazz = null;
                        switch(artifactID) {
                            case ArtifactManager.FIRE_CHARGE, ArtifactManager.LIGHTNING_CHARGE -> clazz = SmallFireball.class;
                            case ArtifactManager.EXPLOSION_CHARGE, ArtifactManager.ICE_CHARGE -> clazz = LargeFireball.class;
                            case ArtifactManager.DRAGON_CHARGE -> clazz = DragonFireball.class;
                            case ArtifactManager.WITHER_CHARGE -> clazz = WitherSkull.class;
                            case ArtifactManager.SHULKER_BULLET -> clazz = ShulkerBullet.class;
                        }
                        if(clazz != null) {
                            player.launchProjectile(clazz, player.getEyeLocation().getDirection(), projectile -> {
                                if(!new PlayerLaunchProjectileEvent(player, item, projectile).callEvent()) {
                                    projectile.remove();
                                } else {
                                    event.setCancelled(true);
                                    if(projectile instanceof SizedFireball fireball) {
                                        fireball.setDisplayItem(item);
                                    }
                                    if(projectile instanceof ShulkerBullet bullet) {
                                        final ArrayList<LivingEntity> targets = new ArrayList<>(player.getLocation().getNearbyLivingEntities(24, entity -> !player.getUniqueId().equals(entity.getUniqueId())));
                                        if(targets.isEmpty()) {
                                            bullet.setTarget(null);
                                        } else {
                                            final Location location = player.getEyeLocation();
                                            targets.sort(Comparator.comparingDouble(entity -> location.distance(entity.getLocation())));
                                            bullet.setTarget(targets.get(0));
                                        }
                                    }
                                }
                            });
                        }
                    }
                    if((ArtifactManager.useEvents[artifactID] & ArtifactManager.INTERACT) > 0) {
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
        } else {
            if(player.isGliding()) {
                player.fireworkBoost(Utils.fireworkBooster);
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        final ItemStack item = event.getItem();
        final Player player = event.getPlayer();
        switch(item.getType()) {
            case POPPED_CHORUS_FRUIT -> {
                Location location = player.getLocation().clone();
                event.setReplacement(item.clone().subtract());
                event.setItem(ArtifactManager.artifacts[ArtifactManager.CHORUS_FRUIT][1]);
                location.createExplosion(player, 1.5f, false, false);
            }
            case MILK_BUCKET -> Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> {
                final short armor = Utils.getArmor(player);
                if(armor != -1) {
                    player.addPotionEffects(List.of(ArmorManager.effects[armor]));
                }
            });
        }
        if(item.hasItemMeta()) {
            final PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
            if(data.has(Utils.artifactIDKey, PersistentDataType.SHORT)) {
                final int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.SHORT);
                if((ArtifactManager.useEvents[artifactID] & ArtifactManager.CONSUME) > 0) {
                    final Material replacementMaterial;
                    switch(item.getType()) {
                        case BEETROOT_SOUP, MUSHROOM_STEW, RABBIT_STEW, SUSPICIOUS_STEW -> replacementMaterial = Material.BOWL;
                        case MILK_BUCKET -> replacementMaterial = Material.BUCKET;
                        case HONEY_BOTTLE, OMINOUS_BOTTLE, POTION -> replacementMaterial = Material.GLASS_BOTTLE;
                        default -> replacementMaterial = Material.BARRIER;
                    }
                    if(replacementMaterial != Material.BARRIER) {
                        Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> player.getInventory().remove(new ItemStack(replacementMaterial)));
                    }
                    if(ArtifactManager.useCooldowns[artifactID] != -1) {
                        player.setCooldown(item.getType(), ArtifactManager.useCooldowns[artifactID]);
                    }
                    if(ArtifactManager.gainCooldowns[artifactID] != -1) {
                        final int itemOrdinal = data.getOrDefault(Utils.itemOrdinalKey, PersistentDataType.INTEGER, -1);
                        if(item.getAmount() == 1) {
                            final ItemStack replacement = item.withType(replacementMaterial);
                            replacement.editMeta(meta -> meta.setFood(null));
                            event.setReplacement(replacement);
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
            item.editMeta(meta -> {
                data.copyTo(event.getProjectile().getPersistentDataContainer(), true);
                event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(data.getOrDefault(Utils.projectileVelocityKey, PersistentDataType.FLOAT, 1f)));
            });
            if(data.has(Utils.artifactIDKey, PersistentDataType.SHORT)) {
                final int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.SHORT);
                if((ArtifactManager.useEvents[artifactID] & ArtifactManager.LAUNCH_PROJECTILE) > 0) {
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
                if((ArtifactManager.useEvents[artifactID] & ArtifactManager.CUSTOM) > 0) {
                    item.subtract();
                }
            }
        }
    }
    private static void transientBlockForm(Location location, Material material, BlockData data, boolean fakeDamage, boolean flag) {
        final int sourceEID = Utils.fakeBlockDamageSourceEID(location);
        final int persistenceTime = ArtifactManager.getPersistenceTime(material);
        final Utils.TransientBlockData transientBlockData = new Utils.TransientBlockData(data, Bukkit.getCurrentTick() + persistenceTime);
        if(Utils.isTransient(location)) {
            Bukkit.getOnlinePlayers().forEach(player -> player.sendBlockDamage(location, 0, sourceEID));
        }
        Utils.putTransientBlockData(location, transientBlockData);
        if(fakeDamage) {
            final int[] timer = {0};
            Bukkit.getRegionScheduler().runAtFixedRate(CreepersPVP.instance, location, task -> {
                if(timer[0] < 10) {
                    if(Utils.getTransientBlockData(location) == transientBlockData) {
                        Bukkit.getOnlinePlayers().forEach(player -> player.sendBlockDamage(location, timer[0] * 0.1f, sourceEID));
                    }
                    timer[0]++;
                } else {
                    if(Utils.getTransientBlockData(location) == transientBlockData) {
                        location.getBlock().setBlockData(data);
                        Bukkit.getOnlinePlayers().forEach(player -> player.sendBlockDamage(location, 0, sourceEID));
                        Utils.removeTransientBlockData(location);
                    }
                    task.cancel();
                }
            }, 1, persistenceTime / 10);
        } else {
            Bukkit.getRegionScheduler().runDelayed(CreepersPVP.instance, location, task -> {
                if(Utils.getTransientBlockData(location) == transientBlockData) {
                    location.getBlock().setBlockData(data);
                    Utils.removeTransientBlockData(location);
                }
            }, persistenceTime);
        }
        if(flag && data instanceof Bisected bisected && !(data instanceof Stairs || data instanceof TrapDoor)) {
            final Block block = location.getBlock().getRelative(bisected.getHalf() == Bisected.Half.TOP ? BlockFace.DOWN : BlockFace.UP);
            transientBlockForm(block.getLocation(), material, block.getBlockData(), false, false);
        }
    }
}
