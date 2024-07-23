package re.imc.creeperspvp.utils;
import fr.mrmicky.fastinv.ItemBuilder;
import io.papermc.paper.scoreboard.numbers.NumberFormat;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackRequest;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;
import re.imc.creeperspvp.*;
import re.imc.creeperspvp.items.ArmorManager;
import re.imc.creeperspvp.items.ArtifactManager;
import re.imc.creeperspvp.items.ItemManager;
import re.imc.creeperspvp.items.WeaponManager;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.net.URI;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
public final class Utils {
    public static final Random random = new Random();
    public static final ItemStack fireworkBooster = new ItemStack(Material.FIREWORK_ROCKET);
    private static final Location hub = new Location(Bukkit.getWorld("world"), 0, 197, 0);
    private static final Location spawn = new Location(Bukkit.getWorld("world"), 0, 134, 0);
    public static NamespacedKey armorBonusKey;
    public static NamespacedKey attributeBonusKey;
    public static NamespacedKey customItemUsageKey;
    public static NamespacedKey customItemUsedTimeKey;
    public static NamespacedKey customItemStartUsingTimeKey;
    public static NamespacedKey projectileVelocityKey;
    public static NamespacedKey rangedAttackSpeedKey;
    public static NamespacedKey armorAuraIDKey;
    public static NamespacedKey armorAuraDataKey;
    public static NamespacedKey meleeAttackEffectIDKey;
    public static NamespacedKey meleeAttackEffectDataKey;
    public static NamespacedKey rangedAttackEffectIDKey;
    public static NamespacedKey rangedAttackEffectDataKey;
    public static NamespacedKey sourceEntityKey;
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
    public static PersistentDataType<byte[], UUID> UUID = new UUIDDataType();
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
    private static final Block skyLightGetter = new Location(Bukkit.getWorld("world"), 0, 256, 0).getBlock();
    private static ArmorStand dummy;
    private static final ConcurrentHashMap<Location, Deque<TransientBlockData>> transientBlockData = new ConcurrentHashMap<>(256);
    private static final ConcurrentHashMap<Location, BlockData> transientBlockOriginals = new ConcurrentHashMap<>(256);
    private static final ConcurrentHashMap<UUID, Integer> deathSpectatingPlayers = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, Short> armors = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, ScheduledTask> armorTasks = new ConcurrentHashMap<>();
    @SuppressWarnings("unchecked")
    private static final ConcurrentHashMap<UUID, ScheduledTask>[] gainArtifactSchedulers = new ConcurrentHashMap[] {new ConcurrentHashMap<UUID, ScheduledTask>(64), new ConcurrentHashMap<UUID, ScheduledTask>(64), new ConcurrentHashMap<UUID, ScheduledTask>(64), new ConcurrentHashMap<UUID, ScheduledTask>(64)};
    private static final Component emeralds = Component.text("绿宝石：", NamedTextColor.GREEN);
    private static final Component kills = Component.text("击杀数：", NamedTextColor.GOLD);
    private static final Component kdr = Component.text("KDR：", NamedTextColor.AQUA);
    private static final UUID resourcePackUUID = java.util.UUID.fromString("da90aa72-957f-4ad7-baea-727a3dc5d447"); //使用五郎的UUID
    private static final ResourcePackRequest resourcePackRequest = ResourcePackRequest.resourcePackRequest().packs(ResourcePackInfo.resourcePackInfo(resourcePackUUID, URI.create(""), "")).prompt(Component.text("WIP")).required(false).replace(true).build();
    private Utils() {}
    public static void init() {
        fireworkBooster.editMeta(FireworkMeta.class, meta -> meta.setPower(1));
        armorBonusKey = new NamespacedKey(CreepersPVP.instance, "armor_bonus");
        attributeBonusKey = new NamespacedKey(CreepersPVP.instance, "attribute_bonus");
        customItemUsageKey = new NamespacedKey(CreepersPVP.instance, "custom-item-usage");
        customItemStartUsingTimeKey = new NamespacedKey(CreepersPVP.instance, "custom-item-start-using-time");
        customItemUsedTimeKey = new NamespacedKey(CreepersPVP.instance, "custom-item-used-time");
        projectileVelocityKey = new NamespacedKey(CreepersPVP.instance, "projectile-velocity");
        rangedAttackSpeedKey = new NamespacedKey(CreepersPVP.instance, "ranged-attack-speed");
        armorAuraIDKey = new NamespacedKey(CreepersPVP.instance, "armor-aura-id");
        armorAuraDataKey = new NamespacedKey(CreepersPVP.instance, "armor-aura-data");
        meleeAttackEffectIDKey = new NamespacedKey(CreepersPVP.instance, "melee-attack-effect-id");
        meleeAttackEffectDataKey = new NamespacedKey(CreepersPVP.instance, "melee-attack-effect-data");
        rangedAttackEffectIDKey = new NamespacedKey(CreepersPVP.instance, "ranged-attack-effect-id");
        rangedAttackEffectDataKey = new NamespacedKey(CreepersPVP.instance, "ranged-attack-effect-data");
        sourceEntityKey = new NamespacedKey(CreepersPVP.instance, "source-entity");
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
        for(final World world : Bukkit.getWorlds()) {
            world.setDifficulty(Difficulty.NORMAL);
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, true);
            world.setGameRule(GameRule.BLOCK_EXPLOSION_DROP_DECAY, true);
            world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false);
            world.setGameRule(GameRule.DISABLE_ELYTRA_MOVEMENT_CHECK, false);
            world.setGameRule(GameRule.COMMAND_MODIFICATION_BLOCK_LIMIT, 0);
            world.setGameRule(GameRule.DISABLE_RAIDS, true);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.DO_ENTITY_DROPS, false);
            world.setGameRule(GameRule.DO_FIRE_TICK, true);
            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            world.setGameRule(GameRule.DO_INSOMNIA, false);
            world.setGameRule(GameRule.DO_LIMITED_CRAFTING, true);
            world.setGameRule(GameRule.DO_MOB_LOOT, false);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
            world.setGameRule(GameRule.DO_TILE_DROPS, false);
            world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
            world.setGameRule(GameRule.DO_VINES_SPREAD, false);
            world.setGameRule(GameRule.DO_WARDEN_SPAWNING, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, true);
            world.setGameRule(GameRule.DROWNING_DAMAGE, true);
            world.setGameRule(GameRule.ENDER_PEARLS_VANISH_ON_DEATH, true);
            world.setGameRule(GameRule.FALL_DAMAGE, true);
            world.setGameRule(GameRule.FIRE_DAMAGE, true);
            world.setGameRule(GameRule.FORGIVE_DEAD_PLAYERS, true);
            world.setGameRule(GameRule.FREEZE_DAMAGE, true);
            world.setGameRule(GameRule.GLOBAL_SOUND_EVENTS, true);
            world.setGameRule(GameRule.KEEP_INVENTORY, true);
            world.setGameRule(GameRule.LAVA_SOURCE_CONVERSION, false);
            world.setGameRule(GameRule.LOG_ADMIN_COMMANDS, true);
            world.setGameRule(GameRule.MAX_COMMAND_CHAIN_LENGTH, 65536);
            world.setGameRule(GameRule.MAX_COMMAND_FORK_COUNT, 65536);
            world.setGameRule(GameRule.MAX_ENTITY_CRAMMING, 24);
            world.setGameRule(GameRule.MOB_EXPLOSION_DROP_DECAY, true);
            world.setGameRule(GameRule.MOB_GRIEFING, false);
            world.setGameRule(GameRule.NATURAL_REGENERATION, true);
            world.setGameRule(GameRule.PLAYERS_NETHER_PORTAL_CREATIVE_DELAY, 1);
            world.setGameRule(GameRule.PLAYERS_NETHER_PORTAL_DEFAULT_DELAY, 1);
            world.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 101);
            world.setGameRule(GameRule.PROJECTILES_CAN_BREAK_BLOCKS, false);
            world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
            world.setGameRule(GameRule.REDUCED_DEBUG_INFO, true);
            world.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, true);
            world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, true);
            world.setGameRule(GameRule.SNOW_ACCUMULATION_HEIGHT, 1);
            world.setGameRule(GameRule.SPAWN_CHUNK_RADIUS, 1);
            world.setGameRule(GameRule.SPAWN_RADIUS, 0);
            world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
            world.setGameRule(GameRule.TNT_EXPLOSION_DROP_DECAY, false);
            world.setGameRule(GameRule.UNIVERSAL_ANGER, false);
            world.setGameRule(GameRule.WATER_SOURCE_CONVERSION, false);
        }
        Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), 0, 0, 0), EntityType.ARMOR_STAND, CreatureSpawnEvent.SpawnReason.CUSTOM, armorStand -> {
            armorStand.setInvisible(true);
            armorStand.setInvulnerable(true);
            armorStand.setNoPhysics(true);
            dummy = (ArmorStand) armorStand;
        });
    }
    public static void fina() {
        transientBlockOriginals.forEach((location, data) -> location.getBlock().setBlockData(data));
        dummy.remove();
    }
    public static void playerJoin(Player player) {
        //CAPES http://textures.minecraft.net/texture/698d1de2662e2c71859d097158113d1d2f7af59587847c57720764c722d4a239
        //player.sendResourcePacks(resourcePackRequest);
        final UUID uuid = player.getUniqueId();
        final DatabaseUtils.MiscSettings miscSettings = DatabaseUtils.getPlayerMiscSettings(uuid);
        if(miscSettings.shouldShowGuideBook()) {
            player.openBook(ItemManager.GUIDEBOOK);
        }
        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        final Objective health = scoreboard.registerNewObjective("health", Criteria.HEALTH, Component.text("HP", NamedTextColor.RED), RenderType.HEARTS);
        health.setDisplaySlot(DisplaySlot.BELOW_NAME);
        final Objective level = scoreboard.registerNewObjective("xp", Criteria.LEVEL, Component.text("LV", NamedTextColor.YELLOW), RenderType.INTEGER);
        level.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        final Objective infoBoard = scoreboard.registerNewObjective("info-board", Criteria.DUMMY, Component.text("CreepersPVP：FFA", NamedTextColor.GREEN), RenderType.INTEGER);
        infoBoard.setDisplaySlot(DisplaySlot.SIDEBAR);
        infoBoard.numberFormat(NumberFormat.blank());
        final String[] info = new String[]{"", "", ""};
        player.setScoreboard(scoreboard);
        player.getScheduler().runAtFixedRate(CreepersPVP.instance, task -> {
            for(final String string : info) {
                infoBoard.getScore(string).resetScore();
            }
            info[2] = LegacyComponentSerializer.legacySection().serialize(emeralds.append(Component.text(Math.toIntExact(DatabaseUtils.fetchPlayerEmeralds(uuid)))));
            final int kills = DatabaseUtils.fetchPlayerKills(uuid);
            info[1] = LegacyComponentSerializer.legacySection().serialize(Utils.kills.append(Component.text(kills)));
            final int deaths = DatabaseUtils.fetchPlayerDeaths(uuid);
            info[0] = LegacyComponentSerializer.legacySection().serialize(kdr.append(Component.text(deaths == 0 ? String.valueOf(kills) : String.format("%.2f", 1f * kills / deaths))));
            for(int i = 0; i < info.length; i++) {
                infoBoard.getScore(info[i]).setScore(i);
            }
            player.setExperienceLevelAndProgress(DatabaseUtils.fetchPlayerXp(uuid));
        }, null, 1, 19);
        final Inventory inv = player.getInventory();
        player.getScheduler().runAtFixedRate(CreepersPVP.instance, task -> {
            if(player.hasActiveItem()) {
                final ItemStack item = player.getActiveItem();
                item.editMeta(meta -> {
                    PersistentDataContainer data = meta.getPersistentDataContainer();
                    if(data.has(customItemUsageKey)) {
                        final int currentTime = Bukkit.getCurrentTick();
                        final int vanillaMaxUseDuration = getVanillaMaxUseDuration(item, player);
                        if(data.has(customItemStartUsingTimeKey, PersistentDataType.INTEGER)) {
                            final int startTime = data.get(customItemStartUsingTimeKey, PersistentDataType.INTEGER);
                            final int customMaxUseTime = getCustomMaxUseDuration(item, player);
                            int usedTime = data.get(customItemUsedTimeKey, PersistentDataType.INTEGER);
                            if(miscSettings.getRangedAttackIndicator().isTrue() || (miscSettings.getRangedAttackIndicator().isDefault() && !player.hasResourcePack())) {
                                final Duration duration = Duration.ofMillis(Math.max(1, customMaxUseTime / ItemManager.PROGRESS_BARS.length) * 50L);
                                player.showTitle(Title.title(Component.empty(), ItemManager.PROGRESS_BARS[Math.min(usedTime * ItemManager.PROGRESS_BARS.length / customMaxUseTime, ItemManager.PROGRESS_BARS.length - 1)], Title.Times.times(Duration.ZERO, duration, duration)));
                            }
                            if(startTime + usedTime + 1 == currentTime && usedTime < customMaxUseTime) {
                                data.set(customItemUsedTimeKey, PersistentDataType.INTEGER, ++usedTime);
                                player.setActiveItemRemainingTime(item.getMaxItemUseDuration(player) - usedTime * vanillaMaxUseDuration / customMaxUseTime);
                                if(player.hasResourcePack()) {
                                    item.getItemMeta().setCustomModelData(getCustomModelData(item, player, usedTime));
                                }
                            } else if(usedTime != customMaxUseTime) {
                                data.remove(customItemStartUsingTimeKey);
                                data.remove(customItemUsedTimeKey);
                            }
                        } else {
                            int usedTime = player.getActiveItemUsedTime();
                            data.set(customItemStartUsingTimeKey, PersistentDataType.INTEGER, currentTime - usedTime);
                            data.set(customItemUsedTimeKey, PersistentDataType.INTEGER, usedTime);
                            player.setActiveItemRemainingTime(item.getMaxItemUseDuration(player) - usedTime * vanillaMaxUseDuration / getCustomMaxUseDuration(item, player));
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
        armors.remove(player.getUniqueId());
        deathSpectatingPlayers.remove(player.getUniqueId());
        player.removeResourcePack(resourcePackUUID);
    }
    public static void playerInit(Player player) {
        armors.remove(player.getUniqueId());
        removeGainArtifactSchedulers(player.getUniqueId());
        final PlayerInventory inv = player.getInventory();
        inv.clear();
        inv.setItem(0, ItemManager.SELECT_ARMOR);
        inv.setItem(1, ItemManager.SELECT_WEAPONS);
        inv.setItem(2, ItemManager.SELECT_ARTIFACTS);
        inv.setItem(4, ItemManager.DEPLOY);
        inv.setItem(6, ItemManager.GUIDEBOOK);
        inv.setItem(7, ItemBuilder.copyOf(ItemManager.PROFILE).meta(SkullMeta.class, meta -> {
            meta.setOwningPlayer(player);
        }).build());
        inv.setItem(8, ItemManager.SERVERS);
        inv.setHeldItemSlot(4);
        player.clearActivePotionEffects();
        player.setArrowsInBody(0);
        player.setRemainingAir(300);
        player.setFireTicks(-20);
        player.setFreezeTicks(0);
        if(DatabaseUtils.getPlayerMiscSettings(player.getUniqueId()).hasDeployCooldown()) {
            player.setCooldown(Material.COMPASS, 50);
        }
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.setExhaustion(Float.NEGATIVE_INFINITY);
        player.setGameMode(GameMode.ADVENTURE);
        player.setInvulnerable(true);
        player.teleport(hub);
    }
    public static void spawnPlayer(final Player player) {
        final DatabaseUtils.ItemSettings itemSettings = DatabaseUtils.fetchPlayerItemSettings(player.getUniqueId());
        final PlayerInventory inv = player.getInventory();
        inv.clear();
        final short armorID = itemSettings == null ? ArmorManager.MERCENARY_ARMOR : itemSettings.getArmorSelection();
        inv.setItem(EquipmentSlot.HEAD, ArmorManager.armor[armorID][0]);
        inv.setItem(EquipmentSlot.CHEST, ArmorManager.armor[armorID][1]);
        inv.setItem(EquipmentSlot.LEGS, ArmorManager.armor[armorID][2]);
        inv.setItem(EquipmentSlot.FEET, ArmorManager.armor[armorID][3]);
        final ItemStack weapon1 = ItemBuilder.copyOf(WeaponManager.weapons[itemSettings == null ? WeaponManager.SWORD : itemSettings.getWeaponSelection(0)][1]).edit(item -> item.editMeta(meta -> meta.getPersistentDataContainer().set(itemOrdinalKey, PersistentDataType.INTEGER, 0))).build();
        inv.setItem(itemSettings == null ? 0 : itemSettings.getWeaponSlot(0), weapon1);
        if(weapon1.hasItemMeta()) {
            PersistentDataContainer weapon1Data = weapon1.getItemMeta().getPersistentDataContainer();
            if(weapon1Data.has(multiItemKey, PersistentDataType.INTEGER)) {
                final int multiItems = weapon1Data.get(multiItemKey, PersistentDataType.INTEGER);
                for(int i = 1; i < multiItems; i++) {
                    inv.setItem((itemSettings == null ? 0 : itemSettings.getWeaponSlot(0)) + 36 - i * 9, weapon1);
                }
            }
        }
        final ItemStack weapon2 = ItemBuilder.copyOf(WeaponManager.weapons[itemSettings == null ? WeaponManager.BOW : itemSettings.getWeaponSelection(1)][1]).edit(item -> item.editMeta(meta -> meta.getPersistentDataContainer().set(itemOrdinalKey, PersistentDataType.INTEGER, 1))).build();
        inv.setItem(itemSettings == null ? 1 : itemSettings.getWeaponSlot(1), weapon2);
        if(weapon2.hasItemMeta()) {
            PersistentDataContainer weapon2Data = weapon2.getItemMeta().getPersistentDataContainer();
            if(weapon2Data.has(multiItemKey, PersistentDataType.INTEGER)) {
                final int multiItems = weapon2Data.get(multiItemKey, PersistentDataType.INTEGER);
                for(int i = 1; i < multiItems; i++) {
                    inv.setItem((itemSettings == null ? 1 : itemSettings.getWeaponSlot(1)) + 36 - i * 9, weapon2);
                }
            }
        }
        inv.setItem(itemSettings == null ? 2 : itemSettings.getArtifactSlot(0), ItemBuilder.copyOf(ArtifactManager.artifacts[itemSettings == null ? ArtifactManager.SNOWBALL : itemSettings.getArtifactSelection(0)][1]).edit(item -> item.editMeta(meta -> meta.getPersistentDataContainer().set(itemOrdinalKey, PersistentDataType.INTEGER, 2))).build());
        inv.setItem(itemSettings == null ? 3 : itemSettings.getArtifactSlot(1), ItemBuilder.copyOf(ArtifactManager.artifacts[itemSettings == null ? ArtifactManager.BREAD : itemSettings.getArtifactSelection(1)][1]).edit(item -> item.editMeta(meta -> meta.getPersistentDataContainer().set(itemOrdinalKey, PersistentDataType.INTEGER, 3))).build());
        inv.setItem(itemSettings == null ? 4 : itemSettings.getArtifactSlot(2), ItemBuilder.copyOf(ArtifactManager.artifacts[itemSettings == null ? ArtifactManager.WATER_BUCKET : itemSettings.getArtifactSelection(2)][1]).edit(item -> item.editMeta(meta -> meta.getPersistentDataContainer().set(itemOrdinalKey, PersistentDataType.INTEGER, 4))).build());
        inv.setItem(itemSettings == null ? 40 : itemSettings.getArtifactSlot(3), ItemBuilder.copyOf(ArtifactManager.artifacts[itemSettings == null ? ArtifactManager.SHIELD : itemSettings.getArtifactSelection(3)][1]).edit(item -> item.editMeta(meta -> meta.getPersistentDataContainer().set(itemOrdinalKey, PersistentDataType.INTEGER, 5))).build());
        inv.setItem(17, new ItemStack(Material.ARROW, 64));
        inv.setHeldItemSlot(0);
        final DatabaseUtils.AttributeUpgrades upgrades = DatabaseUtils.getPlayerAttributeUpgrades(player.getUniqueId());
        player.registerAttribute(Attribute.GENERIC_MAX_HEALTH);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(new AttributeModifier(attributeBonusKey, upgrades.getData(DatabaseUtils.AttributeUpgrades.HEALTH_BONUS), AttributeModifier.Operation.ADD_NUMBER));
        inv.getItem(EquipmentSlot.CHEST).editMeta(meta -> meta.addEnchant(Enchantment.PROTECTION, upgrades.getData(DatabaseUtils.AttributeUpgrades.PROTECTION_LEVEL), true));
        player.registerAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
        player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).addModifier(new AttributeModifier(attributeBonusKey, upgrades.getData(DatabaseUtils.AttributeUpgrades.KNOCKBACK_RESISTANCE) * 0.05, AttributeModifier.Operation.ADD_NUMBER));
        player.clearActivePotionEffects();
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
        if(ArmorManager.tasks[armorID] != null) {
            player.getScheduler().runAtFixedRate(CreepersPVP.instance, task -> ArmorManager.tasks[armorID].accept(player), null, 1, 200);
        }
        armors.put(player.getUniqueId(), armorID);
        player.teleport(spawn);
    }
    public static short getArmor(Player player) {
        return armors.containsKey(player.getUniqueId()) ? armors.get(player.getUniqueId()) : -1;
    }
    public static void playerDeath(Player player) {
        deathSpectatingPlayers.put(player.getUniqueId(), Bukkit.getCurrentTick());
    }
    public static int fakeBlockDamageSourceEID(Location location) {
        return (int) -((((long) location.getBlockX() << 38) + ((long) location.getBlockY() << 26) + location.getBlockZ()) % 2147483648L) - 1;
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
    public static double getChannellingChance(Location location) {
        if(location.getBlock().getLightFromSky() == skyLightGetter.getLightFromSky()) {
            if(location.getWorld().isThundering()) {
                return 1;
            } else if(location.getWorld().hasStorm()) {
                return 0.75;
            } else {
                return 0.5;
            }
        }
        return 0;
    }
    public static boolean isTransient(Location location) {
        return transientBlockData.containsKey(location);
    }
    public static TransientBlockData getTransientBlockData(Location location) {
        return transientBlockData.containsKey(location) ? transientBlockData.get(location).peek() : null;
    }
    public static void removeTransientBlockData(Location location) {
        if(transientBlockOriginals.containsKey(location) && transientBlockData.containsKey(location)) {
            transientBlockData.get(location).remove();
            if(transientBlockData.get(location).isEmpty()) {
                transientBlockData.remove(location);
                location.getBlock().setBlockData(transientBlockOriginals.remove(location));
            }
        }
    }
    public static void putTransientBlockData(Location location, TransientBlockData data) {
        if(!transientBlockData.containsKey(location)) {
            transientBlockData.put(location, new ArrayDeque<>());
            transientBlockOriginals.put(location, data.data());
        }
        final Deque<TransientBlockData> stack = transientBlockData.get(location);
        while(!stack.isEmpty() && stack.peek().expires() <= data.expires()) {
            stack.remove();
        }
        stack.push(data);
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
                        if(currentItem.getType() == Material.BARRIER || currentItem.getType() == Material.BUCKET || currentItem.getType() == Material.GLASS_BOTTLE || currentItem.getType() == Material.BOWL) {
                            if(slot == -1) {
                                player.setItemOnCursor(item);
                            } else {
                                inv.setItem(slot, item);
                            }
                            if(ArtifactManager.artifacts[artifactID][1].getAmount() == 1) {
                                task.cancel();
                            }
                        } else {
                            if(currentItem.getAmount() < ArtifactManager.artifacts[artifactID][1].getAmount()) {
                                currentItem.add();
                            }
                            if(currentItem.getAmount() >= ArtifactManager.artifacts[artifactID][1].getAmount()) {
                                task.cancel();
                            }
                        }
                    } else {
                        ItemStack itemInHand = player.getInventory().getItemInMainHand();
                        if((currentItem.getType() == Material.BARRIER || currentItem.getType() == Material.BUCKET || currentItem.getType() == Material.GLASS_BOTTLE || currentItem.getType() == Material.BOWL) && (itemInHand.hasItemMeta() && itemInHand.getItemMeta().getPersistentDataContainer().getOrDefault(Utils.itemOrdinalKey, PersistentDataType.INTEGER, -1) == itemOrdinal)) {
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
    private static int getVanillaMaxUseDuration(ItemStack item, LivingEntity entity) {
        switch(item.getType()) {
            case BOW -> {
                return 20;
            }
        }
        return item.getMaxItemUseDuration(entity);
    }
    private static int getCustomMaxUseDuration(ItemStack item, LivingEntity entity) {
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
        return item.getMaxItemUseDuration(entity);
    }
    private static int getCustomModelData(ItemStack item, LivingEntity entity, int usedDuration) {
        return usedDuration * 3 / getCustomMaxUseDuration(item, entity);
    }
    public static final class TransientBlockData {
        private final BlockData data;
        private int expires;
        public TransientBlockData(BlockData data, int expires) {
            this.data = data;
            this.expires = expires;
        }
        public BlockData data() {
            return data;
        }
        public int expires() {
            return expires;
        }
        @Override
        public String toString() {
            return "TransientBlockData{data=" + data.getAsString() + ",expires=" + expires + "}";
        }
    }
    public static final class UUIDDataType implements PersistentDataType<byte[], UUID> {
        private UUIDDataType() {}
        @Override
        public @NotNull Class<byte[]> getPrimitiveType() {
            return byte[].class;
        }
        @Override
        public @NotNull Class<UUID> getComplexType() {
            return UUID.class;
        }
        @Override
        public byte @NotNull [] toPrimitive(UUID complex, @NotNull PersistentDataAdapterContext context) {
            final ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
            buffer.putLong(complex.getMostSignificantBits());
            buffer.putLong(complex.getLeastSignificantBits());
            return buffer.array();
        }
        @Override
        public @NotNull UUID fromPrimitive(byte[] primitive, @NotNull PersistentDataAdapterContext context) {
            final ByteBuffer buffer = ByteBuffer.wrap(primitive);
            return new UUID(buffer.getLong(), buffer.getLong());
        }
    }
}
