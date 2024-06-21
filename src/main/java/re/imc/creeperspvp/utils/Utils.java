package re.imc.creeperspvp.utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
public final class Utils {
    private static final Location hub = new Location(Bukkit.getWorld("world"), 0, 197, 0);
    private static final Location spawn = new Location(Bukkit.getWorld("world"), 0, 134, 0);
    public static NamespacedKey rangedArrowVelocityKey;
    public static NamespacedKey rangedAttackSpeedKey;
    public static NamespacedKey armorAuraIDKey;
    public static NamespacedKey armorAuraDataKey;
    public static NamespacedKey meleeAttackEffectIDKey;
    public static NamespacedKey meleeAttackEffectDataKey;
    public static NamespacedKey rangedAttackEffectIDKey;
    public static NamespacedKey rangedAttackEffectDataKey;
    public static NamespacedKey itemOrdinalKey;
    public static NamespacedKey artifactIDKey;
    public static NamespacedKey weaponIDKey;
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
    public static final byte MELEE_EFFECT_CHANNELING = 0;
    public static final byte MELEE_EFFECT_EXPLOSION = 1;
    public static final byte MELEE_EFFECT_FREEZE = 2;
    public static final byte MELEE_EFFECT_POISON = 3;
    public static final byte RANGED_EFFECT_FREEZE = 0;
    private static ArmorStand dummy;
    @SuppressWarnings("unchecked")
    private static final ConcurrentHashMap<UUID, ScheduledTask>[] gainArtifactSchedulers = new ConcurrentHashMap[] {new ConcurrentHashMap<UUID, ScheduledTask>(), new ConcurrentHashMap<UUID, ScheduledTask>(), new ConcurrentHashMap<UUID, ScheduledTask>()};
    private static final Component emeralds = Component.text("绿宝石：", NamedTextColor.GREEN);
    private static final Component kills = Component.text("击杀数：", NamedTextColor.GOLD);
    private static final Component kdr = Component.text("KDR：", NamedTextColor.AQUA);
    private Utils() {}
    public static void init() {
        rangedArrowVelocityKey = new NamespacedKey(CreepersPVP.instance, "ranged-arrow-velocity");
        rangedAttackSpeedKey = new NamespacedKey(CreepersPVP.instance, "ranged-attack-speed");
        armorAuraIDKey = new NamespacedKey(CreepersPVP.instance, "armor-aura-id");
        armorAuraDataKey = new NamespacedKey(CreepersPVP.instance, "armor-aura-data");
        meleeAttackEffectIDKey = new NamespacedKey(CreepersPVP.instance, "melee-attack-effect-id");
        meleeAttackEffectDataKey = new NamespacedKey(CreepersPVP.instance, "melee-attack-effect-data");
        rangedAttackEffectIDKey = new NamespacedKey(CreepersPVP.instance, "ranged-attack-effect-id");
        rangedAttackEffectDataKey = new NamespacedKey(CreepersPVP.instance, "ranged-attack-effect-data");
        itemOrdinalKey = new NamespacedKey(CreepersPVP.instance, "item-ordinal");
        artifactIDKey = new NamespacedKey(CreepersPVP.instance, "artifact-id");
        weaponIDKey = new NamespacedKey(CreepersPVP.instance, "weapon-id");
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
            int pos = inv.first(Material.ARROW);
            if(pos != -1) {
                inv.getItem(pos).setAmount(64);
            }
        }, null, 61, 61);
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
        inv.setItem(0, WeaponManager.weapons[data.getOrDefault(weaponKeys[0], PersistentDataType.INTEGER, WeaponManager.SWORD)]);
        inv.setItem(1, WeaponManager.weapons[data.getOrDefault(weaponKeys[1], PersistentDataType.INTEGER, WeaponManager.BOW)]);
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
        player.addPotionEffects(List.of(ArmorManager.effects[armorID]));
        player.teleport(spawn);
    }
    /**
     * Paper API is stupid
     * @return item with the modifier applied
     */
    public static ItemStack modifyItem(ItemStack item, String modifier) {
        dummy.setItem(EquipmentSlot.HAND, item);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "item modify entity " + Utils.dummy.getUniqueId() + " weapon.mainhand " + modifier);
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
                timer[0] %= ItemManager.ARTIFACT_UNAVAILABLE_DISPLAY_NAMES.length;
                final int slot = Utils.findItem(inv, itemOrdinal);
                final ItemStack currentItem = slot == -1 ? player.getItemOnCursor() : inv.getItem(slot);
                if(currentItem != null) {
                    if(timer[0] == 0) {
                        if(currentItem.getType() == Material.BARRIER) {
                            item.editMeta(meta -> meta.displayName(null));
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
                        if(currentItem.getType() == Material.BARRIER) {
                            currentItem.editMeta(meta -> meta.displayName(ItemManager.ARTIFACT_UNAVAILABLE_DISPLAY_NAMES[timer[0]]));
                        }
                    }
                }
            }, null, ArtifactManager.gainCooldowns[artifactID] / ItemManager.ARTIFACT_UNAVAILABLE_DISPLAY_NAMES.length, ArtifactManager.gainCooldowns[artifactID] / ItemManager.ARTIFACT_UNAVAILABLE_DISPLAY_NAMES.length));
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
}
