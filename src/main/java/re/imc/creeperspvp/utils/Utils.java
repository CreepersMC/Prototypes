package re.imc.creeperspvp.utils;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackRequest;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.*;
import re.imc.creeperspvp.*;
import re.imc.creeperspvp.items.ArmorManager;
import re.imc.creeperspvp.items.ArtifactManager;
import re.imc.creeperspvp.items.ItemManager;
import re.imc.creeperspvp.items.WeaponManager;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
public final class Utils {
    private static final Location hub = new Location(Bukkit.getWorld("world"), 0, 197, 0);
    private static final Location spawn = new Location(Bukkit.getWorld("world"), 0, 134, 0);
    public static NamespacedKey customItemUsageKey;
    public static NamespacedKey customItemUsedTimeKey;
    public static NamespacedKey customItemStartUsingTimeKey;
    public static NamespacedKey rangedArrowVelocityKey;
    public static NamespacedKey rangedAttackSpeedKey;
    public static NamespacedKey armorAuraIDKey;
    public static NamespacedKey armorAuraDataKey;
    public static NamespacedKey meleeAttackEffectIDKey;
    public static NamespacedKey meleeAttackEffectDataKey;
    public static NamespacedKey rangedAttackEffectIDKey;
    public static NamespacedKey rangedAttackEffectDataKey;
    public static NamespacedKey itemOrdinalKey;
    public static NamespacedKey weaponIDKey;
    public static NamespacedKey artifactIDKey;
    public static NamespacedKey multiItemKey;
    public static NamespacedKey iuiIDKey;
    public static NamespacedKey iuiDataKey;
    public static NamespacedKey weaponSelectorIDKey;
    public static NamespacedKey artifactSelectorIDKey;
    public static NamespacedKey utilIDKey;
    public static NamespacedKey utilDataKey;
    public static NamespacedKey playerDataKey;
    public static NamespacedKey armorKey;
    public static final NamespacedKey[] weaponKeys = new NamespacedKey[2];
    public static final NamespacedKey[] artifactKeys = new NamespacedKey[3];
    public static final byte UTIL_SPAWN = 0;
    public static final byte ARMOR_AURA_FREEZE = 0;
    public static final byte ARMOR_AURA_LIFESTEAL = 1;
    public static final byte ARMOR_AURA_SHULKING = 2;
    public static final byte MELEE_EFFECT_CHANNELING = 0;
    public static final byte MELEE_EFFECT_EXPLOSION = 1;
    public static final byte MELEE_EFFECT_FREEZE = 2;
    public static final byte MELEE_EFFECT_POISON = 3;
    public static final byte RANGED_EFFECT_CHANNELING = 0;
    public static final byte RANGED_EFFECT_FREEZE = 1;
    public static final byte RANGED_EFFECT_WIND = 2;
    private static final int DEATH_SPECTATE_TIME = 60;
    private static ArmorStand dummy;
    private static final ConcurrentHashMap<UUID, Integer> deathSpectatingPlayers = new ConcurrentHashMap<>();
    @SuppressWarnings("unchecked")
    private static final ConcurrentHashMap<UUID, ScheduledTask>[] gainArtifactSchedulers = new ConcurrentHashMap[] {new ConcurrentHashMap<UUID, ScheduledTask>(), new ConcurrentHashMap<UUID, ScheduledTask>(), new ConcurrentHashMap<UUID, ScheduledTask>()};
    private static final Component emeralds = Component.text("绿宝石：", NamedTextColor.GREEN);
    private static final Component kills = Component.text("击杀数：", NamedTextColor.GOLD);
    private static final Component kdr = Component.text("KDR：", NamedTextColor.AQUA);
    private static final UUID resourcePackUUID = UUID.fromString("da90aa72-957f-4ad7-baea-727a3dc5d447"); //使用五郎的UUID
    private static final ResourcePackRequest resourcePackRequest = ResourcePackRequest.resourcePackRequest().packs(ResourcePackInfo.resourcePackInfo(resourcePackUUID, URI.create(""), "")).prompt(Component.text("WIP")).required(false).replace(true).build();
    private Utils() {}
    public static void init() {
        customItemUsageKey = new NamespacedKey(CreepersPVP.instance, "custom-item-usage");
        customItemStartUsingTimeKey = new NamespacedKey(CreepersPVP.instance, "custom-item-start-using-time");
        customItemUsedTimeKey = new NamespacedKey(CreepersPVP.instance, "custom-item-used-time");
        rangedArrowVelocityKey = new NamespacedKey(CreepersPVP.instance, "ranged-arrow-velocity");
        rangedAttackSpeedKey = new NamespacedKey(CreepersPVP.instance, "ranged-attack-speed");
        armorAuraIDKey = new NamespacedKey(CreepersPVP.instance, "armor-aura-id");
        armorAuraDataKey = new NamespacedKey(CreepersPVP.instance, "armor-aura-data");
        meleeAttackEffectIDKey = new NamespacedKey(CreepersPVP.instance, "melee-attack-effect-id");
        meleeAttackEffectDataKey = new NamespacedKey(CreepersPVP.instance, "melee-attack-effect-data");
        rangedAttackEffectIDKey = new NamespacedKey(CreepersPVP.instance, "ranged-attack-effect-id");
        rangedAttackEffectDataKey = new NamespacedKey(CreepersPVP.instance, "ranged-attack-effect-data");
        itemOrdinalKey = new NamespacedKey(CreepersPVP.instance, "item-ordinal");
        weaponIDKey = new NamespacedKey(CreepersPVP.instance, "weapon-id");
        artifactIDKey = new NamespacedKey(CreepersPVP.instance, "artifact-id");
        multiItemKey = new NamespacedKey(CreepersPVP.instance, "multi-item");
        iuiIDKey = new NamespacedKey(CreepersPVP.instance, "iui-id");
        iuiDataKey = new NamespacedKey(CreepersPVP.instance, "iui-data");
        weaponSelectorIDKey = new NamespacedKey(CreepersPVP.instance, "weapon");
        artifactSelectorIDKey = new NamespacedKey(CreepersPVP.instance, "artifact");
        utilIDKey = new NamespacedKey(CreepersPVP.instance, "util");
        utilDataKey = new NamespacedKey(CreepersPVP.instance, "util-data");
        playerDataKey = new NamespacedKey(CreepersPVP.instance, "player-data");
        armorKey = new NamespacedKey(CreepersPVP.instance, "armor");
        for(int i = 0; i < weaponKeys.length; i++) {
            weaponKeys[i] = new NamespacedKey(CreepersPVP.instance, "weapon" + i);
        }
        for(int i = 0; i < artifactKeys.length; i++) {
            artifactKeys[i] = new NamespacedKey(CreepersPVP.instance, "artifact" + i);
        }
        Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), 0, 0, 0), EntityType.ARMOR_STAND, CreatureSpawnEvent.SpawnReason.CUSTOM, armorStand -> {
            armorStand.setInvisible(true);
            armorStand.setInvulnerable(true);
            armorStand.setNoPhysics(true);
            dummy = (ArmorStand) armorStand;
        });
    }
    public static void fina() {
        dummy.remove();
    }
    public static void playerJoin(Player player) {
        //player.sendResourcePacks(resourcePackRequest);
        UUID uuid = player.getUniqueId();
        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        final Objective infoBoard = scoreboard.registerNewObjective("info-board", Criteria.DUMMY, Component.text("CreepersPVP：FFA", NamedTextColor.GREEN), RenderType.INTEGER);
        infoBoard.setDisplaySlot(DisplaySlot.SIDEBAR);
        infoBoard.getScore(".emeralds").setScore(2);
        infoBoard.getScore(".kills").setScore(1);
        infoBoard.getScore(".kdr").setScore(0);
        player.setScoreboard(scoreboard);
        player.getScheduler().runAtFixedRate(CreepersPVP.instance, task -> {
            infoBoard.getScore(".emeralds").customName(emeralds.append(Component.text(Math.toIntExact(DatabaseUtils.fetchPlayerEmeralds(uuid)))));
            final int kills = DatabaseUtils.fetchPlayerKills(uuid);
            infoBoard.getScore(".kills").customName(Utils.kills.append(Component.text(kills)));
            final int deaths = DatabaseUtils.fetchPlayerDeaths(uuid);
            infoBoard.getScore(".kdr").customName(kdr.append(Component.text(deaths == 0 ? String.valueOf(kills) : String.format("%.2f", 1f * kills / deaths))));
        }, null, 1, 19);
        final Inventory inv = player.getInventory();
        player.getScheduler().runAtFixedRate(CreepersPVP.instance, task -> {
            if(player.hasActiveItem()) {
                final ItemStack item = player.getActiveItem();
                item.editMeta(meta -> {
                    PersistentDataContainer data = meta.getPersistentDataContainer();
                    if(data.has(customItemUsageKey)) {
                        final int currentTime = Bukkit.getCurrentTick();
                        final int vanillaMaxUseDuration = getVanillaMaxUseDuration(item);
                        if(data.has(customItemStartUsingTimeKey, PersistentDataType.INTEGER)) {
                            final int startTime = data.get(customItemStartUsingTimeKey, PersistentDataType.INTEGER);
                            final int customMaxUseTime = getCustomMaxUseDuration(item);
                            int usedTime = data.get(customItemUsedTimeKey, PersistentDataType.INTEGER);
                            final Duration duration = Duration.ofMillis(Math.max(1, customMaxUseTime / ItemManager.PROGRESS_BARS.length) * 50L);
                            player.sendTitlePart(TitlePart.TIMES, Title.Times.times(Duration.ZERO, duration, duration));
                            player.sendTitlePart(TitlePart.SUBTITLE, ItemManager.PROGRESS_BARS[Math.min(usedTime * ItemManager.PROGRESS_BARS.length / customMaxUseTime, ItemManager.PROGRESS_BARS.length - 1)]);
                            player.sendTitlePart(TitlePart.TITLE, Component.empty());
                            if(startTime + usedTime + 1 == currentTime && usedTime < customMaxUseTime) {
                                data.set(customItemUsedTimeKey, PersistentDataType.INTEGER, ++usedTime);
                                player.setActiveItemRemainingTime(item.getMaxItemUseDuration() - usedTime * vanillaMaxUseDuration / customMaxUseTime);
                                if(player.hasResourcePack()) {
                                    item.getItemMeta().setCustomModelData(getCustomModelData(item, usedTime));
                                }
                            } else if(usedTime != customMaxUseTime) {
                                data.remove(customItemStartUsingTimeKey);
                                data.remove(customItemUsedTimeKey);
                            }
                        } else {
                            int usedTime = player.getActiveItemUsedTime();
                            data.set(customItemStartUsingTimeKey, PersistentDataType.INTEGER, currentTime - usedTime);
                            data.set(customItemUsedTimeKey, PersistentDataType.INTEGER, usedTime);
                            player.setActiveItemRemainingTime(item.getMaxItemUseDuration() - usedTime * vanillaMaxUseDuration / getCustomMaxUseDuration(item));
                        }
                    }
                });
            }
        }, null, 1, 1);
        player.getScheduler().runAtFixedRate(CreepersPVP.instance, task -> {
            int pos = inv.first(Material.ARROW);
            if(pos != -1) {
                inv.getItem(pos).setAmount(64);
            }
        }, null, 61, 61);
    }
    public static void playerQuit(Player player) {
        deathSpectatingPlayers.remove(player.getUniqueId());
        player.removeResourcePack(resourcePackUUID);
    }
    public static void playerInit(Player player) {
        removeGainArtifactSchedulers(player.getUniqueId());
        final PersistentDataContainer pdc = player.getPersistentDataContainer();
        PersistentDataContainer data = pdc.get(playerDataKey, PersistentDataType.TAG_CONTAINER);
        if(data == null) {
            pdc.set(playerDataKey, PersistentDataType.TAG_CONTAINER, pdc.getAdapterContext().newPersistentDataContainer());
        }
        final PlayerInventory inv = player.getInventory();
        inv.clear();
        inv.setItem(0, ItemManager.ARMOR_SELECTOR);
        inv.setItem(1, ItemManager.WEAPON_SELECTORS[0]);
        inv.setItem(2, ItemManager.WEAPON_SELECTORS[1]);
        inv.setItem(4, ItemManager.DEPLOY);
        inv.setItem(6, ItemManager.ARTIFACT_SELECTORS[0]);
        inv.setItem(7, ItemManager.ARTIFACT_SELECTORS[1]);
        inv.setItem(8, ItemManager.ARTIFACT_SELECTORS[2]);
        inv.setHeldItemSlot(4);
        player.clearActivePotionEffects();
        player.setArrowsInBody(0);
        player.setRemainingAir(300);
        player.setFireTicks(-20);
        player.setFreezeTicks(0);
        player.setCooldown(Material.COMPASS, 50);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.setExhaustion(Float.NEGATIVE_INFINITY);
        player.setGameMode(GameMode.ADVENTURE);
        player.setInvulnerable(true);
        player.teleport(hub);
    }
    public static void spawnPlayer(final Player player) {
        final PersistentDataContainer data = player.getPersistentDataContainer().get(playerDataKey, PersistentDataType.TAG_CONTAINER);
        final PlayerInventory inv = player.getInventory();
        inv.clear();
        final int armorID = data.getOrDefault(armorKey, PersistentDataType.INTEGER, ArmorManager.MERCENARY_ARMOR);
        inv.setItem(EquipmentSlot.HEAD, ArmorManager.armor[armorID][0]);
        inv.setItem(EquipmentSlot.CHEST, ArmorManager.armor[armorID][1]);
        inv.setItem(EquipmentSlot.LEGS, ArmorManager.armor[armorID][2]);
        inv.setItem(EquipmentSlot.FEET, ArmorManager.armor[armorID][3]);
        final ItemStack weapon1 = WeaponManager.weapons[data.getOrDefault(weaponKeys[0], PersistentDataType.INTEGER, WeaponManager.SWORD)];
        inv.setItem(0, weapon1);
        if(weapon1.hasItemMeta()) {
            PersistentDataContainer weapon1Data = weapon1.getItemMeta().getPersistentDataContainer();
            if(weapon1Data.has(multiItemKey, PersistentDataType.INTEGER)) {
                final int multiItems = weapon1Data.get(multiItemKey, PersistentDataType.INTEGER);
                for(int i = 1; i < multiItems; i++) {
                    inv.setItem(36 - i * 9, weapon1);
                }
            }
        }
        final ItemStack weapon2 = WeaponManager.weapons[data.getOrDefault(weaponKeys[1], PersistentDataType.INTEGER, WeaponManager.BOW)];
        inv.setItem(1, weapon2);
        if(weapon2.hasItemMeta()) {
            PersistentDataContainer weapon2Data = weapon2.getItemMeta().getPersistentDataContainer();
            if(weapon2Data.has(multiItemKey, PersistentDataType.INTEGER)) {
                final int multiItems = weapon2Data.get(multiItemKey, PersistentDataType.INTEGER);
                for(int i = 1; i < multiItems; i++) {
                    inv.setItem(37 - i * 9, weapon2);
                }
            }
        }
        inv.setItem(2, ArtifactManager.artifacts[data.getOrDefault(artifactKeys[0], PersistentDataType.INTEGER, ArtifactManager.SNOWBALL)]);
        inv.setItem(3, ArtifactManager.artifacts[data.getOrDefault(artifactKeys[1], PersistentDataType.INTEGER, ArtifactManager.BREAD)]);
        inv.setItem(4, ArtifactManager.artifacts[data.getOrDefault(artifactKeys[2], PersistentDataType.INTEGER, ArtifactManager.SHIELD)]);
        //TODO
        for(int i = 0; i < 5; i++) {
            int finalI = i;
            inv.getItem(i).editMeta(meta -> {
                meta.getPersistentDataContainer().set(itemOrdinalKey, PersistentDataType.INTEGER, finalI);
            });
        }
        inv.setItem(9, new ItemStack(Material.ARROW, 64));
        player.getActivePotionEffects().clear();
        final AttributeInstance maxHealthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        player.setHealth(maxHealthAttribute == null ? 20 : maxHealthAttribute.getValue());
        player.setFoodLevel(20);
        player.setSaturation(5);
        player.setExhaustion(0);
        player.setFallDistance(0);
        player.setGameMode(GameMode.ADVENTURE);
        player.setInvulnerable(false);
        player.setAllowFlight(armorID == ArmorManager.GHAST_ARMOR);
        player.setSilent(armorID == ArmorManager.GHOST_KINDLER || armorID == ArmorManager.CREEPY_ARMOR);
        player.addPotionEffects(List.of(ArmorManager.effects[armorID]));
        player.teleport(spawn);
    }
    public static void playerDeath(Player player) {
        deathSpectatingPlayers.put(player.getUniqueId(), Bukkit.getCurrentTick());
    }
    public static boolean attemptStopSpectatingEntity(Player player) {
        final UUID uuid = player.getUniqueId();
        if(deathSpectatingPlayers.containsKey(uuid)) {
            if(Bukkit.getCurrentTick() - deathSpectatingPlayers.get(uuid) >= DEATH_SPECTATE_TIME) {
                deathSpectatingPlayers.remove(uuid);
                Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> playerInit(player));
                return true;
            }
            return false;
        }
        return true;
    }
    public static Location shulk(Location location) {
        dummy.teleport(location);
        dummy.setItem(EquipmentSlot.HAND, new ItemStack(Material.CHORUS_FRUIT));
        dummy.startUsingItem(EquipmentSlot.HAND);
        dummy.completeUsingActiveItem();
        dummy.setItem(EquipmentSlot.HAND, null);
        return dummy.getLocation();
    }
    /**
     * Paper API is stupid
     * @return item with the modifier applied
     */
    public static ItemStack modifyItem(ItemStack item, String modifier) {
        dummy.setItem(EquipmentSlot.HAND, item);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "item modify entity " + dummy.getUniqueId() + " weapon.mainhand " + modifier);
        return dummy.getItem(EquipmentSlot.HAND);
    }
    public static int findItem(PlayerInventory inv, int itemOrdinal) {
        final ListIterator<ItemStack> i = inv.iterator();
        while(i.hasNext()) {
            final ItemStack next = i.next();
            if(next != null && next.hasItemMeta()) {
                if(itemOrdinal == next.getItemMeta().getPersistentDataContainer().getOrDefault(itemOrdinalKey, PersistentDataType.INTEGER, -1)) {
                    return i.previousIndex();
                }
            }
        }
        final ItemStack cursorItem = inv.getHolder().getItemOnCursor();
        if(cursorItem.hasItemMeta()) {
            if(itemOrdinal == cursorItem.getItemMeta().getPersistentDataContainer().getOrDefault(itemOrdinalKey, PersistentDataType.INTEGER, -1)) {
                return -1;
            }
        }
        return i.nextIndex();
    }
    public static void scheduleGainArtifact(Player player, int itemOrdinal, int artifactID, ItemStack item) {
        if(!hasGainArtifactScheduler(itemOrdinal, player.getUniqueId())) {
            final PlayerInventory inv = player.getInventory();
            final int[] timer = {0};
            scheduleGainArtifact(itemOrdinal, player.getUniqueId(), player.getScheduler().runAtFixedRate(CreepersPVP.instance, task -> {
                timer[0]++;
                timer[0] %= ItemManager.PROGRESS_BARS.length;
                final int slot = Utils.findItem(inv, itemOrdinal);
                final ItemStack currentItem = slot == -1 ? player.getItemOnCursor() : inv.getItem(slot);
                if(currentItem != null) {
                    if(timer[0] == 0) {
                        if(currentItem.getType() == Material.BARRIER) {
                            if(slot == -1) {
                                player.setItemOnCursor(item);
                            } else {
                                inv.setItem(slot, item);
                            }
                            if(ArtifactManager.artifacts[artifactID].getAmount() == 1) {
                                task.cancel();
                            }
                        } else {
                            if(currentItem.getAmount() < ArtifactManager.artifacts[artifactID].getAmount()) {
                                currentItem.add();
                            }
                            if(currentItem.getAmount() >= ArtifactManager.artifacts[artifactID].getAmount()) {
                                task.cancel();
                            }
                        }
                    } else {
                        ItemStack itemInHand = player.getInventory().getItemInMainHand();
                        if(currentItem.getType() == Material.BARRIER && (itemInHand.hasItemMeta() && itemInHand.getItemMeta().getPersistentDataContainer().getOrDefault(Utils.itemOrdinalKey, PersistentDataType.INTEGER, -1) == itemOrdinal)) {
                            player.sendTitlePart(TitlePart.TIMES, Title.Times.times(Duration.ZERO, Duration.ofMillis(ArtifactManager.gainCooldowns[artifactID] / ItemManager.PROGRESS_BARS.length * 50L), Duration.ofMillis(ArtifactManager.gainCooldowns[artifactID] / ItemManager.PROGRESS_BARS.length * 50L)));
                            player.sendTitlePart(TitlePart.SUBTITLE, ItemManager.PROGRESS_BARS[timer[0]]);
                            player.sendTitlePart(TitlePart.TITLE, Component.empty());
                        }
                    }
                }
            }, null, ArtifactManager.gainCooldowns[artifactID] / ItemManager.PROGRESS_BARS.length, ArtifactManager.gainCooldowns[artifactID] / ItemManager.PROGRESS_BARS.length));
        }
    }
    private static boolean hasGainArtifactScheduler(int itemOrdinal, UUID uuid) {
        return gainArtifactSchedulers[itemOrdinal - 2].containsKey(uuid) && !gainArtifactSchedulers[itemOrdinal - 2].get(uuid).isCancelled();
    }
    private static void scheduleGainArtifact(int itemOrdinal, UUID uuid, ScheduledTask task) {
        gainArtifactSchedulers[itemOrdinal - 2].put(uuid, task);
    }
    private static void removeGainArtifactSchedulers(UUID uuid) {
        for(ConcurrentHashMap<UUID, ScheduledTask> gainArtifactScheduler : gainArtifactSchedulers) {
            if(gainArtifactScheduler.containsKey(uuid)) {
                gainArtifactScheduler.get(uuid).cancel();
                gainArtifactScheduler.remove(uuid);
            }
        }
    }
    private static int getVanillaMaxUseDuration(ItemStack item) {
        switch(item.getType()) {
            case BOW -> {
                return 20;
            }
        }
        return item.getMaxItemUseDuration();
    }
    private static int getCustomMaxUseDuration(ItemStack item) {
        switch(item.getType()) {
            case BOW, CROSSBOW, TRIDENT -> {
                if(item.hasItemMeta()) {
                    PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
                    if(data.has(rangedAttackSpeedKey, PersistentDataType.FLOAT)) {
                        return Math.round(20 / data.get(rangedAttackSpeedKey, PersistentDataType.FLOAT));
                    }
                }
            }
        }
        return item.getMaxItemUseDuration();
    }
    private static int getCustomModelData(ItemStack item, int usedDuration) {
        return usedDuration * 3 / getCustomMaxUseDuration(item);
    }
}
