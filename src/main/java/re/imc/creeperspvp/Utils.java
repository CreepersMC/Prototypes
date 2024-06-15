package re.imc.creeperspvp;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.*;
import re.imc.creeperspvp.iui.IUIManager;
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
import java.io.IOException;
import java.sql.*;
import java.util.ListIterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
public final class Utils {
    private static final Location hub = new Location(Bukkit.getWorld("world"), 0, 197, 0);
    private static final Location spawn = new Location(Bukkit.getWorld("world"), 0, 134, 0);
    public static NamespacedKey attackEffectIDKey;
    public static NamespacedKey attackEffectDataKey;
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
    public static final byte EFFECT_CHANNELING = 0;
    public static final byte EFFECT_EXPLOSION = 1;
    public static final byte EFFECT_FREEZE = 2;
    public static final byte EFFECT_POISON = 3;
    private static final ConcurrentHashMap<UUID, Object> playerLocks = new ConcurrentHashMap<>();
    @SuppressWarnings("unchecked")
    private static final ConcurrentHashMap<UUID, ScheduledTask>[] gainArtifactSchedulers = new ConcurrentHashMap[] {new ConcurrentHashMap<UUID, ScheduledTask>(), new ConcurrentHashMap<UUID, ScheduledTask>(), new ConcurrentHashMap<UUID, ScheduledTask>()};
    private static final Component emeralds = Component.text("绿宝石：", NamedTextColor.GREEN);
    private static final Component kills = Component.text("击杀数：", NamedTextColor.GOLD);
    private static final Component kdr = Component.text("KDR%：", NamedTextColor.AQUA);
    private static final String databaseAddress = "127.0.0.1";
    private static final String databasePort = "3306";
    private static final String databaseName = "creepersmc"; //TODO
    private static final String databaseUser = "root";
    private static final String databasePassword = "1145141919810";
    private static Connection connection = null;
    private static Statement statement = null;
    private static final String databaseConnectionURL = "jdbc:mysql://" + databaseAddress + ":" + databasePort + "/" + databaseName + "?user=" + databaseUser + "&password=" + databasePassword;
    private static final String databaseInit = """
        CREATE TABLE IF NOT EXISTS `player_data`(
            `uuid` BINARY(16) NOT NULL,
            `emeralds` BIGINT NOT NULL DEFAULT 0,
            `kills` INT NOT NULL DEFAULT 0,
            `deaths` INT NOT NULL DEFAULT 0,
            `damage_dealt` DECIMAL(12,2) NOT NULL DEFAULT 0,
            `damage_taken` DECIMAL(12,2) NOT NULL DEFAULT 0,
            `armor_status` BINARY(8) NOT NULL DEFAULT b'0000000000000000000010000000000000000000000000000000000000000000',
            `weapon_status` BINARY(8) NOT NULL DEFAULT b'1000000000000000000000100000000000000000000000000000000000',
            `artifact_status` BINARY(32) NOT NULL DEFAULT b'0',
            PRIMARY KEY(`uuid`)
        ) ENGINE = INNODB DEFAULT CHARSET = utf8mb4;
        """;
    private Utils() {}
    public static void init() {
        attackEffectIDKey = new NamespacedKey(CreepersPVP.instance, "attack-effect-id");
        attackEffectDataKey = new NamespacedKey(CreepersPVP.instance, "attack-effect-data");
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
        ItemManager.init();
        IUIManager.init();
        Bukkit.getPluginManager().registerEvents(Listener.instance, CreepersPVP.instance);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            CreepersPVP.logWarning("Error loading MySQL driver: " + e.getMessage());
        }
        try {
            getStatement().execute(databaseInit);
        } catch(SQLException e) {
            CreepersPVP.logWarning("Error initializing database: " + e.getMessage());
        }
    }
    public static void fina() {
        if(statement != null) {
            try {
                statement.close();
            } catch(SQLException e) {
                CreepersPVP.logWarning("Error closing database statement: " + e.getMessage());
            }
        }
        if(connection != null) {
            try {
                connection.close();
            } catch(SQLException e) {
                CreepersPVP.logWarning("Error closing database connection: " + e.getMessage());
            }
        }
    }
    public static void playerJoin(Player player) {
        UUID uuid = player.getUniqueId();
        playerLocks.put(uuid, new Object());
        try(final ResultSet result = getStatement().executeQuery("SELECT `uuid` FROM `player_data` WHERE `uuid` = UUID_TO_BIN('" + uuid + "');")) {
            if(!result.next()) {
                getStatement().execute("INSERT INTO `player_data` (`uuid`) VALUES (UUID_TO_BIN('" + uuid + "'))");
            }
        } catch(SQLException e) {
            CreepersPVP.logWarning("Error executing sql query: " + e.getMessage());
        }
        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        final Objective infoBoard = scoreboard.registerNewObjective("info-board", Criteria.DUMMY, Component.text("CreepersPVP：FFA", NamedTextColor.GREEN), RenderType.INTEGER);
        infoBoard.setDisplaySlot(DisplaySlot.SIDEBAR);
        infoBoard.getScore(".emeralds").setScore(0);
        infoBoard.getScore(".kills").setScore(1);
        infoBoard.getScore(".kdr").setScore(2);
        player.setScoreboard(scoreboard);
        player.getScheduler().runAtFixedRate(CreepersPVP.instance, task -> {
            infoBoard.getScore(".emeralds").customName(emeralds.append(Component.text(Math.toIntExact(fetchPlayerEmeralds(uuid)))));
            final int kills = fetchPlayerKills(uuid);
            infoBoard.getScore(".kills").customName(Utils.kills.append(Component.text(kills)));
            int deaths = fetchPlayerDeaths(uuid);
            if(deaths == 0) {
                deaths = 1;
            }
            infoBoard.getScore(".kdr").customName(kdr.append(Component.text(Math.round(100f * kills / deaths))));
        }, null, 1, 19);
        final Inventory inv = player.getInventory();
        player.getScheduler().runAtFixedRate(CreepersPVP.instance, task -> {
            int pos = inv.first(Material.ARROW);
            if(pos != -1) {
                inv.getItem(pos).setAmount(64);
            }
        }, null, 61, 61);
    }
    public static void playerQuit(Player player) {
        playerLocks.remove(player.getUniqueId());
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
        if(armorID == ArmorManager.GHAST_ARMOR) {
            player.setAllowFlight(true);
        }
        player.teleport(spawn);
    }
    public static long fetchPlayerEmeralds(UUID uuid) {
        try(final ResultSet result = getStatement().executeQuery("SELECT `emeralds` FROM `player_data` WHERE `uuid` = UUID_TO_BIN('" + uuid + "');")) {
            if(result.next()) {
                return result.getLong("emeralds");
            }
        } catch(SQLException e) {
            CreepersPVP.logWarning("Error executing SQL query: " + e.getMessage());
        }
        return 0;
    }
    public static void addPlayerEmeralds(UUID uuid, long amount) {
        try {
            getStatement().execute("UPDATE `player_data` SET `emeralds` = `emeralds` + " + amount + " WHERE `uuid` = UUID_TO_BIN('" + uuid + "');");
        } catch(SQLException e) {
            CreepersPVP.logWarning("Error executing SQL update: " + e.getMessage());
        }
    }
    public static int fetchPlayerKills(UUID uuid) {
        try(final ResultSet result = getStatement().executeQuery("SELECT `kills` FROM `player_data` WHERE `uuid` = UUID_TO_BIN('" + uuid + "');")) {
            if(result.next()) {
                return result.getInt("kills");
            }
        } catch(SQLException e) {
            CreepersPVP.logWarning("Error executing SQL query: " + e.getMessage());
        }
        return 0;
    }
    public static void incrementPlayerKills(UUID uuid) {
        try {
            getStatement().execute("UPDATE `player_data` SET `kills` = `kills` + 1 WHERE `uuid` = UUID_TO_BIN('" + uuid + "');");
        } catch(SQLException e) {
            CreepersPVP.logWarning("Error executing SQL update: " + e.getMessage());
        }
    }
    public static int fetchPlayerDeaths(UUID uuid) {
        try(final ResultSet result = getStatement().executeQuery("SELECT `deaths` FROM `player_data` WHERE `uuid` = UUID_TO_BIN('" + uuid + "');")) {
            if(result.next()) {
                return result.getInt("deaths");
            }
        } catch(SQLException e) {
            CreepersPVP.logWarning("Error executing SQL query: " + e.getMessage());
        }
        return 0;
    }
    public static void incrementPlayerDeaths(UUID uuid) {
        try {
            getStatement().execute("UPDATE `player_data` SET `deaths` = `deaths` + 1 WHERE `uuid` = UUID_TO_BIN('" + uuid + "');");
        } catch(SQLException e) {
            CreepersPVP.logWarning("Error executing SQL update: " + e.getMessage());
        }
    }
    public static boolean[] fetchPlayerArmorStatus(UUID uuid) {
        try(final ResultSet result = getStatement().executeQuery("SELECT `armor_status` FROM `player_data` WHERE `uuid` = UUID_TO_BIN('" + uuid + "');")) {
            if(result.next()) {
                return bytesToBooleans(result.getBinaryStream("armor_status").readAllBytes());
            }
        } catch(SQLException | IOException e) {
            CreepersPVP.logWarning("Error executing SQL query: " + e.getMessage());
        }
        return new boolean[64];
    }
    public static void setPlayerArmorStatus(UUID uuid, boolean[] val) {
        try {
            getStatement().execute("UPDATE `player_data` SET `armor_status` = b'" + booleansToString(val) + "' WHERE `uuid` = UUID_TO_BIN('" + uuid + "');");
        } catch(SQLException e) {
            CreepersPVP.logWarning("Error executing SQL update: " + e.getMessage());
        }
    }
    public static boolean[] fetchPlayerWeaponsStatus(UUID uuid) {
        try(final ResultSet result = getStatement().executeQuery("SELECT `weapon_status` FROM `player_data` WHERE `uuid` = UUID_TO_BIN('" + uuid + "');")) {
            if(result.next()) {
                return bytesToBooleans(result.getBinaryStream("weapon_status").readAllBytes());
            }
        } catch(SQLException | IOException e) {
            CreepersPVP.logWarning("Error executing SQL query: " + e.getMessage());
        }
        return new boolean[64];
    }
    public static void setPlayerWeaponStatus(UUID uuid, boolean[] val) {
        try {
            getStatement().execute("UPDATE `player_data` SET `weapon_status` = b'" + booleansToString(val) + "' WHERE `uuid` = UUID_TO_BIN('" + uuid + "');");
        } catch(SQLException e) {
            CreepersPVP.logWarning("Error executing SQL update: " + e.getMessage());
        }
    }
    public static boolean[] fetchPlayerArtifactStatus(UUID uuid) {
        try(final ResultSet result = getStatement().executeQuery("SELECT `artifact_status` FROM `player_data` WHERE `uuid` = UUID_TO_BIN('" + uuid + "');")) {
            if(result.next()) {
                return bytesToBooleans(result.getBinaryStream("artifact_status").readAllBytes());
            }
        } catch(SQLException | IOException e) {
            CreepersPVP.logWarning("Error executing SQL query: " + e.getMessage());
        }
        return new boolean[256];
    }
    public static void setPlayerArtifactStatus(UUID uuid, boolean[] val) {
        try {
            getStatement().execute("UPDATE `player_data` SET `artifact_status` = b'" + booleansToString(val) + "' WHERE `uuid` = UUID_TO_BIN('" + uuid + "');");
        } catch(SQLException e) {
            CreepersPVP.logWarning("Error executing SQL update: " + e.getMessage());
        }
    }
    public static Object getPlayerLock(UUID uuid) {
        return playerLocks.get(uuid);
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
    private static boolean[] bytesToBooleans(byte[] bytes) {
        final boolean[] booleans = new boolean[bytes.length * Byte.SIZE];
        for(int i = 0; i < bytes.length; i++) {
            for(byte j = Byte.SIZE - 1; j >= 0; j--, bytes[i] >>= 1) {
                booleans[i * Byte.SIZE + j] = bytes[i] % 2 != 0;
            }
        }
        return booleans;
    }
    private static String booleansToString(boolean[] booleans) {
        final StringBuilder builder = new StringBuilder();
        for(final boolean b : booleans) {
            builder.append(b ? '1' : '0');
        }
        return builder.toString();
    }
    private static Connection getConnection() {
        try {
            if(connection == null || connection.isClosed()) {
                return connection = DriverManager.getConnection(databaseConnectionURL);
            }
        } catch(SQLException e) {
            CreepersPVP.logWarning("Error creating database connection: " + e.getMessage());
        }
        return connection;
    }
    private static Statement getStatement() {
        try {
            if(statement == null || statement.isClosed()) {
                return statement = getConnection().createStatement();
            }
        } catch(SQLException | NullPointerException e) {
            CreepersPVP.logWarning("Error accessing database connection: " + e.getMessage());
        }
        return statement;
    }
}
