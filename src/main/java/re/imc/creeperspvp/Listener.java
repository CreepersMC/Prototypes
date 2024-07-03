package re.imc.creeperspvp;
import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import com.destroystokyo.paper.event.player.PlayerReadyArrowEvent;
import com.destroystokyo.paper.event.player.PlayerStopSpectatingEntityEvent;
import fr.mrmicky.fastinv.FastInv;
import io.papermc.paper.event.entity.EntityKnockbackEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.damage.DamageSource;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import re.imc.creeperspvp.items.ArtifactManager;
import re.imc.creeperspvp.items.ItemManager;
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
public final class Listener implements org.bukkit.event.Listener {
    public static final Listener instance = new Listener();
    private static final Title.Times spectateTitleTimes = Title.Times.times(Duration.ofMillis(500), Duration.ofMillis(2500), Duration.ofMillis(500));
    private static final Component leaveDeathSpectate = Component.textOfChildren(Component.text("按 "), Component.keybind("key.sneak"), Component.text(" 退出旁观"));
    public static final Title freeSpectateTitle = Title.title(Component.text("正在自由旁观"), Component.text("输入 /spawn 退出观战"), spectateTitleTimes);
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
        event.getPlayer().openBook(ItemManager.GUIDEBOOK);
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
    public void onBlockExplode(BlockExplodeEvent event) {
        event.blockList().clear();
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().clear();
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryOpen(InventoryOpenEvent event) {
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE && !(event.getInventory() instanceof PlayerInventory || event.getInventory().getHolder() instanceof FastInv)) {
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.LOW)
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
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBurn(BlockBurnEvent event) {
        event.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onProjectileHit(ProjectileHitEvent event) {
        if(event.getEntity() instanceof final LargeFireball fireball) {
            for(final Entity target: fireball.getNearbyEntities(0.0625, 0.0625, 0.0625)) {
                target.setFireTicks((int) Math.max(target.getFireTicks(), (target instanceof Attributable attributable && attributable.getAttribute(Attribute.GENERIC_BURNING_TIME) != null ? attributable.getAttribute(Attribute.GENERIC_BURNING_TIME).getValue() : 1) * 120));
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerReadyArrow(PlayerReadyArrowEvent event) {
        final ItemStack arrow = event.getArrow();
        final Player player = event.getPlayer();
        if(arrow.hasItemMeta()) {
            final PersistentDataContainer data = arrow.getItemMeta().getPersistentDataContainer();
            if(data.has(Utils.artifactIDKey, PersistentDataType.INTEGER)) {
                final int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.INTEGER);
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
            if(data.has(Utils.artifactIDKey, PersistentDataType.INTEGER)) {
                final int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.INTEGER);
                if((ArtifactManager.useEvents[artifactID] & ArtifactManager.PLACE_BLOCK) > 0) {
                    switch(artifactID) {
                        case ArtifactManager.TNT -> {
                            event.setCancelled(true);
                            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> item.subtract());
                            Location loc = event.getBlockPlaced().getLocation().toCenterLocation();
                            loc.getWorld().spawn(loc, TNTPrimed.class, tnt -> {
                                tnt.setFuseTicks(80);
                                tnt.setSource(event.getPlayer());
                            });
                        }
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
                event.setCancelled(player.getGameMode() == GameMode.CREATIVE);
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityPlace(EntityPlaceEvent event) {
        final Player player = event.getPlayer();
        final PlayerInventory inv = player.getInventory();
        final ItemStack item = inv.getItem(event.getHand());
        if(item.hasItemMeta()) {
            final PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
            if(data.has(Utils.artifactIDKey, PersistentDataType.INTEGER)) {
                final int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.INTEGER);
                if((ArtifactManager.useEvents[artifactID] & ArtifactManager.PLACE_ENTITY) > 0) {
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
            } else {
                event.setCancelled(player.getGameMode() == GameMode.CREATIVE);
            }
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
                        case Utils.RANGED_EFFECT_WIND -> {
                            EntityKnockbackEvent knockback = new EntityKnockbackEvent(event.getEntity(), EntityKnockbackEvent.Cause.PUSH, new Vector(0, data.getOrDefault(Utils.rangedAttackEffectDataKey, PersistentDataType.DOUBLE, 0d), 0));
                            //if(knockback.callEvent()) {
                                event.getEntity().getVelocity().add(new Vector(0, data.getOrDefault(Utils.rangedAttackEffectDataKey, PersistentDataType.DOUBLE, 0d), 0));
                            //}
                            //if(event.getEntity() instanceof LivingEntity livingEntity) {
                            //    livingEntity.knockback(data.getOrDefault(Utils.rangedAttackEffectDataKey, PersistentDataType.DOUBLE, 0d), livingEntity.getX(), livingEntity.getZ());
                            // }
                        }
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
    public void onPlayerInteract(PlayerInteractEvent event) {
        final ItemStack item = event.getItem();
        final Player player = event.getPlayer();
        if(item != null && !item.isEmpty()) {
            final ItemMeta meta = item.getItemMeta();
            if(meta != null) {
                final PersistentDataContainer data = meta.getPersistentDataContainer();
                if(data.has(Utils.artifactIDKey, PersistentDataType.INTEGER) && item.getType() != Material.BARRIER) {
                    final int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.INTEGER);
                    if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        Class<? extends Projectile> clazz = null;
                        switch(artifactID) {
                            case ArtifactManager.FIRE_CHARGE -> clazz = SmallFireball.class;
                            case ArtifactManager.EXPLOSIVE_FIRE_CHARGE -> clazz = LargeFireball.class;
                        }
                        if(clazz != null) {
                            player.launchProjectile(clazz, player.getEyeLocation().getDirection(), projectile -> {
                                if(!new PlayerLaunchProjectileEvent(player, item, projectile).callEvent()) {
                                    projectile.remove();
                                } else {
                                    event.setCancelled(true);
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
        if(item.hasItemMeta()) {
            final PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
            if(data.has(Utils.artifactIDKey, PersistentDataType.INTEGER)) {
                final int artifactID = data.getOrDefault(Utils.artifactIDKey, PersistentDataType.INTEGER, -1);
                if((ArtifactManager.useEvents[artifactID] & ArtifactManager.CONSUME) > 0) {
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
            item.editMeta(meta -> {
                data.copyTo(event.getProjectile().getPersistentDataContainer(), true);
                event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(data.getOrDefault(Utils.projectileVelocityKey, PersistentDataType.FLOAT, 1f)));
            });
            if(data.has(Utils.artifactIDKey, PersistentDataType.INTEGER)) {
                final int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.INTEGER);
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
}
