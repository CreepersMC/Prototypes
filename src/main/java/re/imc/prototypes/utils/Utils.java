package re.imc.prototypes.utils;
import fr.mrmicky.fastinv.ItemBuilder;
import io.papermc.paper.scoreboard.numbers.NumberFormat;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackRequest;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;
import re.imc.prototypes.*;
import re.imc.prototypes.items.ArmorManager;
import re.imc.prototypes.items.ArtifactManager;
import re.imc.prototypes.items.ItemManager;
import re.imc.prototypes.items.WeaponManager;
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
    private static final ItemStack shulker = new ItemStack(Material.CHORUS_FRUIT);
    private static final Location nowhere = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    private static final Location hub = new Location(Bukkit.getWorld("world"), 0, 197, 0);
    public static NamespacedKey armorBonusKey;
    public static NamespacedKey attributeBonusKey;
    public static NamespacedKey gameProgressBarKey;
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
    private static TextDisplay totalKillsLeaderboard;
    private static TextDisplay maxKillStreakLeaderboard;
    private static TextDisplay totalKDRLeaderboard;
    private static final List<UUID> players = new ArrayList<>();
    private static final ConcurrentHashMap<Location, Deque<TransientBlockData>> transientBlockData = new ConcurrentHashMap<>(256);
    private static final ConcurrentHashMap<Location, BlockData> transientBlockOriginals = new ConcurrentHashMap<>(256);
    private static final ConcurrentHashMap<UUID, Integer> currentKills = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, Integer> currentKillStreaks = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, Integer> currentDeaths = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, Integer> deathSpectatingPlayers = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, Short> armors = new ConcurrentHashMap<>();
    @SuppressWarnings("unchecked")
    private static final ConcurrentHashMap<UUID, ScheduledTask>[] gainArtifactSchedulers = new ConcurrentHashMap[] {new ConcurrentHashMap<UUID, ScheduledTask>(64), new ConcurrentHashMap<UUID, ScheduledTask>(64), new ConcurrentHashMap<UUID, ScheduledTask>(64), new ConcurrentHashMap<UUID, ScheduledTask>(64)};
    private static final Component separator = Component.text(" | ", NamedTextColor.WHITE).decoration(TextDecoration.BOLD, TextDecoration.State.FALSE);
    private static final Component emeralds = Component.text("绿宝石: ", NamedTextColor.GREEN);
    private static final Component kills = Component.text("击杀数: ", NamedTextColor.RED);
    private static final Component killStreak = Component.text("连杀数: ", NamedTextColor.GOLD);
    private static final Component kdr = Component.text("K/D: ", NamedTextColor.AQUA);
    private static final Component mode = Component.text("模式: ", NamedTextColor.WHITE);
    private static final Component version = Component.text("版本: ", NamedTextColor.WHITE);
    private static final Component imc = Component.text("IMC.RE", NamedTextColor.GOLD);
    private static final Component winRewards = Component.text("+%emeralds%绿宝石", NamedTextColor.GREEN).append(Component.text(" +%xp%经验", NamedTextColor.YELLOW));
    private static final Component leaderboardEntry = Component.text("#%placement% %name% %score%", NamedTextColor.YELLOW);
    private static final TextReplacementConfig.Builder replaceEmeralds = TextReplacementConfig.builder().matchLiteral("%emeralds%");
    private static final TextReplacementConfig.Builder replaceXp = TextReplacementConfig.builder().matchLiteral("%xp%");
    private static final TextReplacementConfig.Builder replaceTeam = TextReplacementConfig.builder().matchLiteral("%team%");
    private static final TextReplacementConfig.Builder replacePlacement = TextReplacementConfig.builder().matchLiteral("%placement%");
    private static final TextReplacementConfig.Builder replaceName = TextReplacementConfig.builder().matchLiteral("%name%");
    private static final TextReplacementConfig.Builder replaceScore = TextReplacementConfig.builder().matchLiteral("%score%");
    private static final Title.Times defaultTitleTimes = Title.Times.times(Duration.ofMillis(500), Duration.ofMillis(3500), Duration.ofMillis(1000));
    private static final UUID resourcePackUUID = java.util.UUID.fromString("da90aa72-957f-4ad7-baea-727a3dc5d447");//使用五郎的UUID
    private static final ResourcePackRequest resourcePackRequest = ResourcePackRequest.resourcePackRequest().packs(ResourcePackInfo.resourcePackInfo(resourcePackUUID, URI.create("https://creepersmc.github.io/main.zip"), "20303109bc864b6a50dd272f57daf168e962f48b")).prompt(Component.text("仅支持1.14+")).required(false).replace(true).build();
    private static BossBar gameProgressBar;
    private static final String[] teamNames = new String[]{"red", "blue"};
    public static final ItemStack[] teamFlags = new ItemStack[]{new ItemStack(Material.RED_BANNER), new ItemStack(Material.BLUE_BANNER)};
    private static final Component[][] teamLore = new Component[][]{{Component.text("", NamedTextColor.GRAY)},{Component.text("", NamedTextColor.GRAY)}};
    private static final Component[] teamDisplayNames = new Component[]{Component.text("红", NamedTextColor.RED), Component.text("蓝", NamedTextColor.BLUE)};
    private static final Component[] teamPrefixes = new Component[]{Component.text("[红]", NamedTextColor.RED), Component.text("[蓝]", NamedTextColor.BLUE)};
    private static final Component[] teamSuffixes = new Component[]{Component.empty(), Component.empty()};
    private static final NamedTextColor[] teamColors = new NamedTextColor[]{NamedTextColor.RED, NamedTextColor.BLUE};
    private static final BossBar.Color[] teamProgressBarColors = new BossBar.Color[]{BossBar.Color.RED, BossBar.Color.BLUE};
    public static int targetScore;
    public static final int[] teamScores = new int[]{0, 0};
    public static final BossBar[] teamProgressBars = new BossBar[2];
    public static final Team[] teams = new Team[2];
    private Utils() {}
    public static void init() {
        fireworkBooster.editMeta(FireworkMeta.class, meta -> meta.setPower(1));
        armorBonusKey = new NamespacedKey(Prototypes.instance, "armor_bonus");
        attributeBonusKey = new NamespacedKey(Prototypes.instance, "attribute_bonus");
        gameProgressBarKey = new NamespacedKey(Prototypes.instance, "game-progress");
        customItemUsageKey = new NamespacedKey(Prototypes.instance, "custom-item-usage");
        customItemStartUsingTimeKey = new NamespacedKey(Prototypes.instance, "custom-item-start-using-time");
        customItemUsedTimeKey = new NamespacedKey(Prototypes.instance, "custom-item-used-time");
        projectileVelocityKey = new NamespacedKey(Prototypes.instance, "projectile-velocity");
        rangedAttackSpeedKey = new NamespacedKey(Prototypes.instance, "ranged-attack-speed");
        armorAuraIDKey = new NamespacedKey(Prototypes.instance, "armor-aura-id");
        armorAuraDataKey = new NamespacedKey(Prototypes.instance, "armor-aura-data");
        meleeAttackEffectIDKey = new NamespacedKey(Prototypes.instance, "melee-attack-effect-id");
        meleeAttackEffectDataKey = new NamespacedKey(Prototypes.instance, "melee-attack-effect-data");
        rangedAttackEffectIDKey = new NamespacedKey(Prototypes.instance, "ranged-attack-effect-id");
        rangedAttackEffectDataKey = new NamespacedKey(Prototypes.instance, "ranged-attack-effect-data");
        sourceEntityKey = new NamespacedKey(Prototypes.instance, "source-entity");
        itemOrdinalKey = new NamespacedKey(Prototypes.instance, "item-ordinal");
        weaponIDKey = new NamespacedKey(Prototypes.instance, "weapon-id");
        artifactIDKey = new NamespacedKey(Prototypes.instance, "artifact-id");
        multiItemKey = new NamespacedKey(Prototypes.instance, "multi-item");
        iuiIDKey = new NamespacedKey(Prototypes.instance, "iui-id");
        iuiDataKey = new NamespacedKey(Prototypes.instance, "iui-data");
        weaponSelectorIDKey = new NamespacedKey(Prototypes.instance, "weapon");
        artifactSelectorIDKey = new NamespacedKey(Prototypes.instance, "artifact");
        utilIDKey = new NamespacedKey(Prototypes.instance, "util");
        utilDataKey = new NamespacedKey(Prototypes.instance, "util-data");
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
        gameProgressBar = BossBar.bossBar(Component.empty(), 0f, BossBar.Color.GREEN, BossBar.Overlay.PROGRESS);
        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        for(final int[] i = new int[]{0}; i[0] < teams.length; i[0]++) {
            try {
                scoreboard.getTeam(teamNames[i[0]]).unregister();
            } catch(NullPointerException ignored) {}
            teams[i[0]] = scoreboard.registerNewTeam(teamNames[i[0]]);
            teamFlags[i[0]].editMeta(meta -> {
                meta.itemName(teamDisplayNames[i[0]]);
                meta.lore(ItemManager.removeItalics(Arrays.asList(teamLore[i[0]])));
            });
            teams[i[0]].color(teamColors[i[0]]);
            teams[i[0]].displayName(teamDisplayNames[i[0]]);
            teams[i[0]].prefix(teamPrefixes[i[0]]);
            teams[i[0]].suffix(teamSuffixes[i[0]]);
            teams[i[0]].setAllowFriendlyFire(false);
            teams[i[0]].setCanSeeFriendlyInvisibles(false);
            teams[i[0]].setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
            teams[i[0]].setOption(Team.Option.DEATH_MESSAGE_VISIBILITY, Team.OptionStatus.ALWAYS);
            teams[i[0]].setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
            teamProgressBars[i[0]] = BossBar.bossBar(Component.empty(), 1f, teamProgressBarColors[i[0]], BossBar.Overlay.PROGRESS);
        }
        Bukkit.getWorld("world").spawnEntity(nowhere, EntityType.ARMOR_STAND, CreatureSpawnEvent.SpawnReason.CUSTOM, armorStand -> {
            armorStand.setInvisible(true);
            armorStand.setInvulnerable(true);
            armorStand.setGravity(false);
            dummy = (ArmorStand) armorStand;
        });
        Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -5.25, 197.5, -6.75), EntityType.TEXT_DISPLAY, CreatureSpawnEvent.SpawnReason.CUSTOM, textDisplay -> {
            totalKillsLeaderboard = (TextDisplay) textDisplay;
        });
        Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), 0, 197.5, -6.75), EntityType.TEXT_DISPLAY, CreatureSpawnEvent.SpawnReason.CUSTOM, textDisplay -> {
            maxKillStreakLeaderboard = (TextDisplay) textDisplay;
        });
        Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), 5.25, 197.5, -6.75), EntityType.TEXT_DISPLAY, CreatureSpawnEvent.SpawnReason.CUSTOM, textDisplay -> {
            totalKDRLeaderboard = (TextDisplay) textDisplay;
        });
        Bukkit.getScheduler().runTaskTimer(Prototypes.instance, () -> {
            final List<DatabaseUtils.Score<Integer>> topPlayerKills = DatabaseUtils.fetchTopPlayerKills();
            final List<DatabaseUtils.Score<Integer>> topPlayerKillStreak = DatabaseUtils.fetchTopPlayerKillStreak();
            final List<DatabaseUtils.Score<Float>> topPlayerKDR = DatabaseUtils.fetchTopPlayerKDR();
            Component text = Component.text("累计击杀榜", NamedTextColor.RED);
            for(int i = 0; i < topPlayerKills.size(); i++) {
                text = text.appendNewline().append(leaderboardEntry.replaceText(replacePlacement.replacement(Component.text(i + 1, NamedTextColor.YELLOW)).build()).replaceText(replaceName.replacement(Component.text(topPlayerKills.get(i).name(), NamedTextColor.GREEN)).build()).replaceText(replaceScore.replacement(Component.text(topPlayerKills.get(i).value(), NamedTextColor.RED)).build()));
            }
            totalKillsLeaderboard.text(text);
            text = Component.text("最高连杀榜", NamedTextColor.GOLD);
            for(int i = 0; i < topPlayerKillStreak.size(); i++) {
                text = text.appendNewline().append(leaderboardEntry.replaceText(replacePlacement.replacement(Component.text(i + 1, NamedTextColor.YELLOW)).build()).replaceText(replaceName.replacement(Component.text(topPlayerKillStreak.get(i).name(), NamedTextColor.GREEN)).build()).replaceText(replaceScore.replacement(Component.text(topPlayerKillStreak.get(i).value(), NamedTextColor.GOLD)).build()));
            }
            maxKillStreakLeaderboard.text(text);
            text = Component.text("累计K/D榜", NamedTextColor.AQUA);
            for(int i = 0; i < topPlayerKDR.size(); i++) {
                text = text.appendNewline().append(leaderboardEntry.replaceText(replacePlacement.replacement(Component.text(i + 1, NamedTextColor.YELLOW)).build()).replaceText(replaceName.replacement(Component.text(topPlayerKDR.get(i).name(), NamedTextColor.GREEN)).build()).replaceText(replaceScore.replacement(Component.text(String.format("%.2f", topPlayerKDR.get(i).value()), NamedTextColor.AQUA)).build()));
            }
            totalKDRLeaderboard.text(text);
        }, 1, 307);
        gameProgressBar.progress(Float.NaN);
        if(Prototypes.stage == Prototypes.GameStage.WAITING) {
            final float[] progress = new float[]{0f};
            gameProgressBar.progress(0f);
            gameProgressBar.name(Component.text("等待玩家加入", NamedTextColor.YELLOW));
            gameProgressBar.addViewer(Bukkit.getServer());
            Bukkit.getScheduler().runTaskTimer(Prototypes.instance, task -> {
                switch(Prototypes.stage) {
                    case WAITING -> {
                        if(progress[0] > 1f) {
                            gameProgressBar.progress(progress[0] = 1f);
                            gameProgressBar.name(Component.text("游戏准备开始", NamedTextColor.GREEN));
                            Prototypes.stage = Prototypes.GameStage.PREPARATION;
                            for(final Player player : Bukkit.getOnlinePlayers()) {
                                final PlayerInventory inv = player.getInventory();
                                if(inv.getItem(4) == null || inv.getItem(4).isEmpty()) {
                                    inv.setItem(4, ItemManager.SELECT_TEAM);
                                } else {
                                    inv.addItem(ItemManager.SELECT_TEAM);
                                }
                            }
                        } else {
                            gameProgressBar.progress(progress[0] += (1.001f * Bukkit.getOnlinePlayers().size() / Prototypes.mode.startupPlayerCount - progress[0]) * 0.5f);
                        }
                    }
                    case PREPARATION -> {
                        if(progress[0] > 1f) {
                            gameProgressBar.progress(progress[0] = 1f);
                            gameProgressBar.name(Component.text("等待玩家加入", NamedTextColor.YELLOW));
                            Prototypes.stage = Prototypes.GameStage.WAITING;
                            for(final Player player : Bukkit.getOnlinePlayers()) {
                                player.closeInventory();
                                player.getInventory().removeItem(ItemManager.SELECT_TEAM);
                                for(final Team team : teams) {
                                    team.removeEntries(team.getEntries());
                                }
                            }
                        } else if(progress[0] < 0f) {
                            gameProgressBar.progress(progress[0] = Float.NaN);
                            gameProgressBar.name(Component.empty());
                            gameProgressBar.removeViewer(Bukkit.getServer());
                            Prototypes.stage = Prototypes.GameStage.MAIN;
                            for(final Player player : Bukkit.getOnlinePlayers()) {
                                players.add(player.getUniqueId());
                                if(!teams[0].hasEntity(player) && !teams[1].hasEntity(player)) {
                                    if(teams[0].getSize() > teams[1].getSize()) {
                                        teams[1].addPlayer(player);
                                    } else {
                                        teams[0].addPlayer(player);
                                    }
                                }
                                player.closeInventory();
                                final PlayerInventory inv = player.getInventory();
                                player.getInventory().removeItem(ItemManager.SELECT_TEAM);
                                if(inv.getItem(4) == null || inv.getItem(4).isEmpty()) {
                                    inv.setItem(4, ItemManager.DEPLOY);
                                } else {
                                    inv.addItem(ItemManager.DEPLOY);
                                }
                            }
                            switch(Prototypes.mode) {
                                case TDM -> {
                                    targetScore = Bukkit.getOnlinePlayers().size() * 5;
                                    Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(Component.text("本局目标分数：%score%", NamedTextColor.WHITE).replaceText(replaceScore.replacement(Component.text(targetScore, NamedTextColor.RED)).build())));
                                }
                            }
                            for(int i = 0; i < teams.length; i++) {
                                teamProgressBars[i].addViewer(teams[i]);
                            }
                            Bukkit.getServer().showTitle(Title.title(Prototypes.mode.name, Prototypes.mode.description, defaultTitleTimes));
                        } else {
                            final int players = Bukkit.getOnlinePlayers().size();
                            gameProgressBar.progress(progress[0] -= (players != 3 ? players >= 3 ? 0.001f : 0f : -0.001f));
                        }
                    }
                    case MAIN -> {
                        int onlyTeamLeft = -1;
                        for(final Player player : Bukkit.getOnlinePlayers()) {
                            int t = getPlayerTeam(player);
                            if(onlyTeamLeft == -1) {
                                onlyTeamLeft = t;
                            } else if(onlyTeamLeft != t) {
                                onlyTeamLeft = -1;
                                break;
                            }
                        }
                        for(int i = 0; i < Prototypes.mode.teamCount; i++) {
                            teamProgressBars[i].name(teamProgressBarName(i));
                            teamProgressBars[i].progress(teamProgressBars[i].progress() + (getTeamProgress(i) - teamProgressBars[i].progress()) * 0.5f);
                            if(hasWon(i) || (onlyTeamLeft != -1 && onlyTeamLeft == i)) {
                                endGame(i);
                            }
                        }
                    }
                }
            }, 1, 1);
        }
    }
    public static void fina() {
        transientBlockOriginals.forEach((location, data) -> location.getBlock().setBlockData(data));
        dummy.remove();
        totalKillsLeaderboard.remove();
        maxKillStreakLeaderboard.remove();
        totalKDRLeaderboard.remove();
        for(final Team team : teams) {
            team.unregister();
        }
    }
    private static float getTeamProgress(int team) {
        switch(Prototypes.mode) {
            default -> {
                return (float) teamScores[team] / targetScore;
            }
        }
    }
    private static boolean hasWon(int team) {
        switch(Prototypes.mode) {
            default -> {
                return teamScores[team] >= targetScore;
            }
        }
    }
    private static void endGame(int team) {
        Prototypes.stage = Prototypes.GameStage.ENDED;
        for(final Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().removeItem(ItemManager.DEPLOY);
            player.setInvulnerable(true);
        }
        for(int j = 0; j < teams.length; j++) {
            teamProgressBars[j].removeViewer(teams[j]);
        }
        //Using binary search decreases the time complexity of the following operation from O(n^2) to O(nlogn) though im not sure why im doing this then n is no bigger than 64
        //信 奥 后 遗 症
        players.sort(UUIDComparator.instance);
        for(final Player player : Bukkit.getOnlinePlayers()) {
            if(Collections.binarySearch(players, player.getUniqueId(), UUIDComparator.instance) >= 0) {
                if(team == -1) {
                    final int score = (int) Math.round(Arrays.stream(teamScores).limit(Prototypes.mode.teamCount).average().orElse(0));
                    final int emeralds = winEmeraldsReward(score) / 2, xp = winXpReward(score) / 2;
                    player.showTitle(Title.title(Component.text("平局", NamedTextColor.GOLD), winRewards.replaceText(replaceEmeralds.replacement(Component.text(emeralds)).build()).replaceText(replaceXp.replacement(Component.text(xp)).build()), defaultTitleTimes));
                    DatabaseUtils.addPlayerEmeralds(player.getUniqueId(), emeralds);
                    DatabaseUtils.addPlayerXp(player.getUniqueId(), xp);
                } else {
                    if(getPlayerTeam(player) == team) {
                        final int emeralds = winEmeraldsReward(teamScores[team]), xp = winXpReward(teamScores[team]);
                        player.showTitle(Title.title(Component.text("胜利", NamedTextColor.GOLD), winRewards.replaceText(replaceEmeralds.replacement(Component.text(emeralds)).build()).replaceText(replaceXp.replacement(Component.text(xp)).build()), defaultTitleTimes));
                        DatabaseUtils.addPlayerEmeralds(player.getUniqueId(), emeralds);
                        DatabaseUtils.addPlayerXp(player.getUniqueId(), xp);
                    } else {
                        player.showTitle(Title.title(Component.text("失败", NamedTextColor.GRAY), Component.text("%team% 胜利", NamedTextColor.WHITE).replaceText(replaceTeam.replacement(teams[team].displayName()).build()), defaultTitleTimes));
                    }
                }
            } else {
                player.showTitle(Title.title(Component.text("游戏结束", NamedTextColor.WHITE), Component.text("%team% 胜利", NamedTextColor.WHITE).replaceText(replaceTeam.replacement(teams[team].displayName()).build()), defaultTitleTimes));
            }
        }
        Bukkit.getScheduler().runTaskLater(Prototypes.instance, Bukkit::shutdown, 150);
    }
    public static int killStreak(UUID uuid) {
        final int killStreak = currentKillStreaks.get(uuid) + 1;
        currentKillStreaks.put(uuid, killStreak);
        return killStreak;
    }
    public static void playerLogin(PlayerLoginEvent event) {
        if(Prototypes.mode.startupPlayerCount > 0) {
            final Player player = event.getPlayer();
            switch(Prototypes.stage) {
                case WAITING, PREPARATION -> {
                    if(Prototypes.mode.teamCount > 0 && Bukkit.getOnlinePlayers().size() >= Prototypes.mode.teamCount * Prototypes.mode.teamCount && !player.hasPermission("prototypes.bypass_player_limit")) {
                        event.setResult(PlayerLoginEvent.Result.KICK_FULL);
                    }
                }
                case MAIN -> {
                    if(!players.contains(player.getUniqueId())) {
                        switch(Prototypes.joinAfterStartHandling) {
                            case ALLOW -> {
                                players.add(player.getUniqueId());
                                if(Prototypes.mode.teamCount > 0) {
                                    if(!Utils.teams[0].hasEntity(player) && !Utils.teams[1].hasEntity(player)) {
                                        if(Utils.teams[0].getSize() > Utils.teams[1].getSize()) {
                                            Utils.teams[1].addPlayer(player);
                                        } else {
                                            Utils.teams[0].addPlayer(player);
                                        }
                                    }
                                }
                            }
                            case DISALLOW -> {
                                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("游戏已经开始！", NamedTextColor.RED));
                            }
                        }
                    }
                }
                case ENDED -> {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("游戏已经结束！", NamedTextColor.RED));
                }
            }
        }
    }
    public static void playerJoin(Player player) {
        //CAPES http://textures.minecraft.net/texture/698d1de2662e2c71859d097158113d1d2f7af59587847c57720764c722d4a239
        //Bukkit.advancementIterator().forEachRemaining(advancement -> {
        //
        //});
        final UUID uuid = player.getUniqueId();
        final DatabaseUtils.MiscSettings miscSettings = DatabaseUtils.getPlayerMiscSettings(uuid);
        if(miscSettings.shouldShowGuideBook()) {
            player.openBook(ItemManager.GUIDEBOOK);
        }
        player.sendResourcePacks(resourcePackRequest);
        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        for(final Team team : teams) {
            final Team playerTeam = scoreboard.registerNewTeam(team.getName());
            playerTeam.color(NamedTextColor.nearestTo(team.color()));
            playerTeam.displayName(team.displayName());
            playerTeam.prefix(team.prefix());
            playerTeam.suffix(team.suffix());
            playerTeam.setAllowFriendlyFire(team.allowFriendlyFire());
            playerTeam.setCanSeeFriendlyInvisibles(team.canSeeFriendlyInvisibles());
            for(final Team.Option option : Team.Option.values()) {
                playerTeam.setOption(option, team.getOption(option));
            }
        }
        final Objective health = scoreboard.registerNewObjective("health", Criteria.HEALTH, Component.text("HP", NamedTextColor.RED), RenderType.HEARTS);
        health.setDisplaySlot(DisplaySlot.BELOW_NAME);
        final Objective level = scoreboard.registerNewObjective("xp", Criteria.LEVEL, Component.text("LV", NamedTextColor.YELLOW), RenderType.INTEGER);
        level.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        final Objective infoBoard = scoreboard.registerNewObjective("info-board", Criteria.DUMMY, Component.text("原型之战", NamedTextColor.GREEN, TextDecoration.BOLD), RenderType.INTEGER);
        infoBoard.setDisplaySlot(DisplaySlot.SIDEBAR);
        infoBoard.numberFormat(NumberFormat.blank());
        final String[] info = new String[]{"", "", "", "", "", "", "", "", ""};
        player.setScoreboard(scoreboard);
        player.getScheduler().runAtFixedRate(Prototypes.instance, task -> {
            for(final Team team : teams) {
                final Team playerTeam = scoreboard.getTeam(team.getName());
                if(playerTeam != null) {
                    playerTeam.removeEntries(playerTeam.getEntries());
                    playerTeam.addEntries(team.getEntries());
                }
            }
            for(final String string : info) {
                infoBoard.getScore(string).resetScore();
            }
            info[8] = LegacyComponentSerializer.legacySection().serialize(emeralds.append(Component.text(Math.toIntExact(DatabaseUtils.fetchPlayerEmeralds(uuid)))));
            final int kills = DatabaseUtils.fetchPlayerKills(uuid);
            info[7] = LegacyComponentSerializer.legacySection().serialize(Utils.kills.append(Component.text(kills)));
            info[6] = LegacyComponentSerializer.legacySection().serialize(Utils.killStreak.append(Component.text(Objects.requireNonNullElse(currentKillStreaks.get(uuid), 0))));
            final int deaths = DatabaseUtils.fetchPlayerDeaths(uuid);
            info[5] = LegacyComponentSerializer.legacySection().serialize(kdr.append(Component.text(deaths == 0 ? String.valueOf(kills) : String.format("%.2f", 1f * kills / deaths))));
            info[4] = " ";
            info[3] = LegacyComponentSerializer.legacySection().serialize(mode.append(Prototypes.mode.name));
            info[2] = LegacyComponentSerializer.legacySection().serialize(version.append(Prototypes.version));
            info[1] = "";
            info[0] = LegacyComponentSerializer.legacySection().serialize(imc);
            for(int i = 0; i < info.length; i++) {
                infoBoard.getScore(info[i]).setScore(i);
            }
            player.setExperienceLevelAndProgress(DatabaseUtils.fetchPlayerXp(uuid));
        }, null, 1, 19);
        if(!Float.isNaN(gameProgressBar.progress())) {
            gameProgressBar.addViewer(player);
        }
        final Inventory inv = player.getInventory();
        player.getScheduler().runAtFixedRate(Prototypes.instance, task -> {
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
                                meta.setCustomModelData(getCustomModelData(item, player, usedTime));
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
        player.getScheduler().runAtFixedRate(Prototypes.instance, task -> {
            int pos = inv.first(Material.ARROW);
            if(pos != -1) {
                inv.getItem(pos).setAmount(64);
            }
        }, null, 61, 61);
    }
    public static void playerQuit(Player player) {
        final Team team = Bukkit.getScoreboardManager().getMainScoreboard().getEntityTeam(player);
        if(team != null) {
            team.removeEntity(player);
        }
        if(Prototypes.mode.startupPlayerCount > 0 && Prototypes.forgetPlayers) {
            players.remove(player.getUniqueId());
        }
        armors.remove(player.getUniqueId());
        deathSpectatingPlayers.remove(player.getUniqueId());
        player.removeResourcePack(resourcePackUUID);
    }
    public static void playerInit(Player player) {
        armors.remove(player.getUniqueId());
        currentKillStreaks.remove(player.getUniqueId());
        removeGainArtifactSchedulers(player.getUniqueId());
        for(final Attribute attribute : Attribute.values()) {
            final AttributeInstance attributeInstance = player.getAttribute(attribute);
            if(attributeInstance != null) {
                attributeInstance.removeModifier(attributeBonusKey);
            }
        }
        final PlayerInventory inv = player.getInventory();
        inv.clear();
        inv.setItem(0, ItemManager.SELECT_ARMOR);
        inv.setItem(1, ItemManager.SELECT_WEAPONS);
        inv.setItem(2, ItemManager.SELECT_ARTIFACTS);
        inv.setItem(4, Prototypes.stage == Prototypes.GameStage.PREPARATION ? ItemManager.SELECT_TEAM : Prototypes.stage == Prototypes.GameStage.MAIN ? Prototypes.mode.startupPlayerCount == 0 || players.contains(player.getUniqueId()) ? ItemManager.DEPLOY : null : null);
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
    public static void spawnPlayer(final Player player, final Location spawn) {
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
        final ItemStack weapon2 = ItemBuilder.copyOf(WeaponManager.weapons[itemSettings == null ? WeaponManager.BOW : itemSettings.getWeaponSelection(1)][1]).edit(item -> item.editMeta(meta -> meta.getPersistentDataContainer().set(itemOrdinalKey, PersistentDataType.INTEGER, 1))).build();
        inv.setItem(itemSettings == null ? 1 : itemSettings.getWeaponSlot(1), weapon2);
        inv.setItem(itemSettings == null ? 2 : itemSettings.getArtifactSlot(0), ItemBuilder.copyOf(ArtifactManager.artifacts[itemSettings == null ? ArtifactManager.SNOWBALL : itemSettings.getArtifactSelection(0)][1]).edit(item -> item.editMeta(meta -> meta.getPersistentDataContainer().set(itemOrdinalKey, PersistentDataType.INTEGER, 2))).build());
        inv.setItem(itemSettings == null ? 3 : itemSettings.getArtifactSlot(1), ItemBuilder.copyOf(ArtifactManager.artifacts[itemSettings == null ? ArtifactManager.BREAD : itemSettings.getArtifactSelection(1)][1]).edit(item -> item.editMeta(meta -> meta.getPersistentDataContainer().set(itemOrdinalKey, PersistentDataType.INTEGER, 3))).build());
        inv.setItem(itemSettings == null ? 4 : itemSettings.getArtifactSlot(2), ItemBuilder.copyOf(ArtifactManager.artifacts[itemSettings == null ? ArtifactManager.WATER_BUCKET : itemSettings.getArtifactSelection(2)][1]).edit(item -> item.editMeta(meta -> meta.getPersistentDataContainer().set(itemOrdinalKey, PersistentDataType.INTEGER, 4))).build());
        inv.setItem(itemSettings == null ? 40 : itemSettings.getArtifactSlot(3), ItemBuilder.copyOf(ArtifactManager.artifacts[itemSettings == null ? ArtifactManager.SHIELD : itemSettings.getArtifactSelection(3)][1]).edit(item -> item.editMeta(meta -> meta.getPersistentDataContainer().set(itemOrdinalKey, PersistentDataType.INTEGER, 5))).build());
        inv.setItem(17, new ItemStack(Material.ARROW, 64));
        if(weapon1.hasItemMeta()) {
            PersistentDataContainer weapon1Data = weapon1.getItemMeta().getPersistentDataContainer();
            if(weapon1Data.has(multiItemKey, PersistentDataType.INTEGER)) {
                final int extraItems = weapon1Data.get(multiItemKey, PersistentDataType.INTEGER) - 1;
                for(int i = 0; i < extraItems; i++) {
                    inv.addItem(weapon1);
                }
            }
        }
        if(weapon2.hasItemMeta()) {
            PersistentDataContainer weapon2Data = weapon2.getItemMeta().getPersistentDataContainer();
            if(weapon2Data.has(multiItemKey, PersistentDataType.INTEGER)) {
                final int extraItems = weapon2Data.get(multiItemKey, PersistentDataType.INTEGER) - 1;
                for(int i = 0; i < extraItems; i++) {
                    inv.addItem(weapon2);
                }
            }
        }
        inv.setHeldItemSlot(0);
        final DatabaseUtils.AttributeUpgrades upgrades = DatabaseUtils.getPlayerAttributeUpgrades(player.getUniqueId());
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).addTransientModifier(new AttributeModifier(attributeBonusKey, upgrades.getData(DatabaseUtils.AttributeUpgrades.HEALTH_BONUS), AttributeModifier.Operation.ADD_NUMBER));
        inv.getItem(EquipmentSlot.CHEST).editMeta(meta -> meta.addEnchant(Enchantment.PROTECTION, upgrades.getData(DatabaseUtils.AttributeUpgrades.PROTECTION_LEVEL), true));
        player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).addTransientModifier(new AttributeModifier(attributeBonusKey, upgrades.getData(DatabaseUtils.AttributeUpgrades.KNOCKBACK_RESISTANCE) * 0.07, AttributeModifier.Operation.ADD_NUMBER));
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addTransientModifier(new AttributeModifier(attributeBonusKey, upgrades.getData(DatabaseUtils.AttributeUpgrades.SPEED_BONUS_LEVEL) * 0.06, AttributeModifier.Operation.MULTIPLY_SCALAR_1));;
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addTransientModifier(new AttributeModifier(attributeBonusKey, upgrades.getData(DatabaseUtils.AttributeUpgrades.SHARPNESS_LEVEL) * 0.05, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addTransientModifier(new AttributeModifier(attributeBonusKey, upgrades.getData(DatabaseUtils.AttributeUpgrades.RAMPAGING_LEVEL) * 0.05, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
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
            player.getScheduler().runAtFixedRate(Prototypes.instance, task -> ArmorManager.tasks[armorID].accept(player), null, 1, 200);
        }
        armors.put(player.getUniqueId(), armorID);
        currentKillStreaks.put(player.getUniqueId(), 0);
        player.teleport(spawn);
    }
    public static short getArmor(Player player) {
        return armors.containsKey(player.getUniqueId()) ? armors.get(player.getUniqueId()) : -1;
    }
    public static void playerDeath(Player player) {
        currentKillStreaks.remove(player.getUniqueId());
        deathSpectatingPlayers.put(player.getUniqueId(), Bukkit.getCurrentTick());
    }
    private static Component teamProgressBarName(int team) {
        Component name = Component.text(teamScores[0], teamColors[0]).decoration(TextDecoration.BOLD, team == 0);
        for(int i = 1; i < teams.length; i++) {
            name = name.append(separator).append(Component.text(teamScores[i], teamColors[i]).decoration(TextDecoration.BOLD, team == i));
        }
        return name;
    }
    public static int fakeBlockDamageSourceEID(Location location) {
        return (int) -((((long) location.getBlockX() << 38) + ((long) location.getBlockY() << 26) + location.getBlockZ()) % 1000000007) - 1;
    }
    public static int getPlayerTeam(OfflinePlayer player) {
        final Team team = Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(player);
        if(team != null) {
            switch(team.getName()) {
                case "red" -> {
                    return 0;
                }
                case "blue" -> {
                    return 1;
                }
            }
        }
        return -1;
    }
    private static int winEmeraldsReward(int score) {
        switch(Prototypes.mode) {
            case TDM -> {
                return Math.round(score * 0.75f);
            }
        }
        return 0;
    }
    private static int winXpReward(int score) {
        switch(Prototypes.mode) {
            case TDM -> {
                return Math.round(score * 0.5f);
            }
        }
        return 0;
    }
    public static boolean attemptStopSpectatingEntity(Player player) {
        final UUID uuid = player.getUniqueId();
        if(deathSpectatingPlayers.containsKey(uuid)) {
            if(Bukkit.getCurrentTick() - deathSpectatingPlayers.get(uuid) >= DEATH_SPECTATE_TIME) {
                deathSpectatingPlayers.remove(uuid);
                Bukkit.getScheduler().runTask(Prototypes.instance, () -> playerInit(player));
                return true;
            }
            return false;
        }
        return true;
    }
    public static Location shulk(final Location from, final Pose pose) {
        final Location to;
        synchronized(dummy) {
            dummy.teleport(from);
            dummy.setPose(pose);
            dummy.setItem(EquipmentSlot.HAND, shulker);
            dummy.startUsingItem(EquipmentSlot.HAND);
            dummy.completeUsingActiveItem();
            dummy.setItem(EquipmentSlot.HAND, null);
            to = dummy.getLocation().clone();
            dummy.teleport(nowhere);
        }
        return to;
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
            scheduleGainArtifact(itemOrdinal, player.getUniqueId(), player.getScheduler().runAtFixedRate(Prototypes.instance, task -> {
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
                        return Math.round(20 / data.get(rangedAttackSpeedKey, PersistentDataType.FLOAT) / (1 + DatabaseUtils.getPlayerAttributeUpgrades(entity.getUniqueId()).getData(DatabaseUtils.AttributeUpgrades.RAPID_FIRE_LEVEL) * 0.05f));
                    }
                }
            }
        }
        return item.getMaxItemUseDuration(entity);
    }
    public static int getCustomModelData(ItemStack item, LivingEntity entity, int usedDuration) {
        switch(item.getType()) {
            case BOW, TRIDENT -> {
                if(item.hasItemMeta()) {
                    PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
                    if(data.has(weaponIDKey, PersistentDataType.SHORT)) {
                        if(usedDuration == -1) {
                            return 2048 + (data.get(weaponIDKey, PersistentDataType.SHORT) << 4);
                        } else {
                            float progress = (float) usedDuration / getCustomMaxUseDuration(item, entity);
                            return 2048 + (data.get(weaponIDKey, PersistentDataType.SHORT) << 4) + (progress >= 0.9 ? 3 : progress >= 0.65 ? 2 : 1);
                        }
                    }
                }
            }
            case CROSSBOW -> {
                if(item.hasItemMeta()) {
                    final CrossbowMeta meta = (CrossbowMeta) item.getItemMeta();
                    PersistentDataContainer data = meta.getPersistentDataContainer();
                    if(data.has(weaponIDKey, PersistentDataType.SHORT)) {
                        if(meta.hasChargedProjectiles()) {
                            return 2048 + (data.get(weaponIDKey, PersistentDataType.SHORT) << 4) + (meta.getChargedProjectiles().get(0).getType() == Material.FIREWORK_ROCKET ? 5 : 4);
                        } else {
                            if(usedDuration == -1) {
                                return 2048 + (data.get(weaponIDKey, PersistentDataType.SHORT) << 4);
                            } else {
                                float progress = (float) usedDuration / getCustomMaxUseDuration(item, entity);
                                return 2048 + (data.get(weaponIDKey, PersistentDataType.SHORT) << 4) + (progress >= 1.0 ? 3 : progress >= 0.58 ? 2 : 1);
                            }
                        }
                    }
                }
            }
        }
        return 0;
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
    public static final class UUIDComparator implements Comparator<UUID> {
        public static final UUIDComparator instance = new UUIDComparator();
        private UUIDComparator() {}
        @Override
        public int compare(UUID uuid1, UUID uuid2) {
            return uuid1.getMostSignificantBits() == uuid2.getMostSignificantBits() ? Long.compare(uuid1.getLeastSignificantBits(), uuid2.getLeastSignificantBits()) : Long.compare(uuid1.getMostSignificantBits(), uuid2.getMostSignificantBits());
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
