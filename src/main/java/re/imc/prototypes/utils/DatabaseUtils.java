package re.imc.prototypes.utils;
import org.bukkit.Bukkit;
import re.imc.prototypes.Prototypes;

import java.sql.*;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
public final class DatabaseUtils {
    private static PreparedStatement checkPlayerExistence;
    private static PreparedStatement registerPlayer;
    private static PreparedStatement fetchPlayerEmeralds;
    private static PreparedStatement addPlayerEmeralds;
    private static PreparedStatement fetchPlayerXp;
    private static PreparedStatement addPlayerXp;
    private static PreparedStatement fetchPlayerKills;
    private static PreparedStatement incrementPlayerKills;
    private static PreparedStatement fetchPlayerDeaths;
    private static PreparedStatement incrementPlayerDeaths;
    private static PreparedStatement fetchPlayerArmorStatus;
    private static PreparedStatement setPlayerArmorStatus;
    private static PreparedStatement fetchPlayerWeaponStatus;
    private static PreparedStatement setPlayerWeaponStatus;
    private static PreparedStatement fetchPlayerArtifactStatus;
    private static PreparedStatement setPlayerArtifactStatus;
    private static PreparedStatement fetchPlayerAttributeUpgrades;
    private static PreparedStatement setPlayerAttributeUpgrades;
    private static PreparedStatement fetchPlayerItemSettings;
    private static PreparedStatement setPlayerItemSettings;
    private static PreparedStatement fetchPlayerMiscSettings;
    private static PreparedStatement setPlayerMiscSettings;
    private static final ConcurrentHashMap<UUID, Object> playerLocks = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, AttributeUpgrades> playerAttributeUpgradesCache = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, MiscSettings> playerMiscSettingsCache = new ConcurrentHashMap<>();
    private static Connection connection = null;
    private static final String TABLE_NAME = "creeperspvp_player_data";
    private static final String databaseInit = """
        CREATE TABLE IF NOT EXISTS `creeperspvp_player_data` (
            `uuid` BINARY(16) NOT NULL,
            `emeralds` BIGINT NOT NULL DEFAULT 1000,
            `xp` INT NOT NULL DEFAULT 0,
            `kills` INT NOT NULL DEFAULT 0,
            `deaths` INT NOT NULL DEFAULT 0,
            `damage_dealt` DECIMAL(12,2) NOT NULL DEFAULT 0,
            `damage_taken` DECIMAL(12,2) NOT NULL DEFAULT 0,
            `armor_status` BINARY(6) NOT NULL DEFAULT b'000000000000000000000000100000000000000000000000',
            `weapon_status` BINARY(10) NOT NULL DEFAULT b'00000000001000000000000000000000000001000000000000000000000000000000000000000000',
            `artifact_status` BINARY(32) NOT NULL DEFAULT b'1000000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000',
            `attribute_upgrades` BINARY(16) NOT NULL DEFAULT b'0',
            `item_settings` BINARY(13) NOT NULL DEFAULT b'00011000000010100010011100000000010001001001011111001010000000000000000100000010000000110000010000101000',
            `misc_settings` BIT(16) NOT NULL DEFAULT b'1100000000000000',
            PRIMARY KEY(`uuid`)
        ) ENGINE = INNODB DEFAULT CHARSET = utf8mb4;
        """;
    private static final String refreshConnection = "SELECT `uuid` FROM `" + TABLE_NAME + "` WHERE `uuid` = NULL";
    private DatabaseUtils() {}
    public static void init() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            Prototypes.logSevere("Error loading database driver: " + e.getMessage());
        }
        connection = DriverManager.getConnection("jdbc:mysql://" + Prototypes.databaseAddress + ":" + Prototypes.databasePort + "/" + Prototypes.database + "?user=" + Prototypes.databaseUsername + "&password=" + Prototypes.databasePassword);
        connection.createStatement().execute(databaseInit);
        checkPlayerExistence = connection.prepareStatement("SELECT `uuid` FROM `" + TABLE_NAME + "` WHERE `uuid` = UUID_TO_BIN(?);");
        registerPlayer = connection.prepareStatement("INSERT INTO `" + TABLE_NAME + "` (`uuid`) VALUES (UUID_TO_BIN(?))");
        fetchPlayerEmeralds = connection.prepareStatement("SELECT `emeralds` FROM `" + TABLE_NAME + "` WHERE `uuid` = UUID_TO_BIN(?);");
        addPlayerEmeralds = connection.prepareStatement("UPDATE `" + TABLE_NAME + "` SET `emeralds` = `emeralds` + ? WHERE `uuid` = UUID_TO_BIN(?);");
        fetchPlayerXp = connection.prepareStatement("SELECT `xp` FROM `" + TABLE_NAME + "` WHERE `uuid` = UUID_TO_BIN(?);");
        addPlayerXp = connection.prepareStatement("UPDATE `" + TABLE_NAME + "` SET `xp` = `xp` + ? WHERE `uuid` = UUID_TO_BIN(?);");
        fetchPlayerKills = connection.prepareStatement("SELECT `kills` FROM `" + TABLE_NAME + "` WHERE `uuid` = UUID_TO_BIN(?);");
        incrementPlayerKills = connection.prepareStatement("UPDATE `" + TABLE_NAME + "` SET `kills` = `kills` + 1 WHERE `uuid` = UUID_TO_BIN(?);");
        fetchPlayerDeaths = connection.prepareStatement("SELECT `deaths` FROM `" + TABLE_NAME + "` WHERE `uuid` = UUID_TO_BIN(?);");
        incrementPlayerDeaths = connection.prepareStatement("UPDATE `" + TABLE_NAME + "` SET `deaths` = `deaths` + 1 WHERE `uuid` = UUID_TO_BIN(?);");
        fetchPlayerArmorStatus = connection.prepareStatement("SELECT `armor_status` FROM `" + TABLE_NAME + "` WHERE `uuid` = UUID_TO_BIN(?);");
        setPlayerArmorStatus = connection.prepareStatement("UPDATE `" + TABLE_NAME + "` SET `armor_status` = b? WHERE `uuid` = UUID_TO_BIN(?);");
        fetchPlayerWeaponStatus = connection.prepareStatement("SELECT `weapon_status` FROM `" + TABLE_NAME + "` WHERE `uuid` = UUID_TO_BIN(?);");
        setPlayerWeaponStatus = connection.prepareStatement("UPDATE `" + TABLE_NAME + "` SET `weapon_status` = b? WHERE `uuid` = UUID_TO_BIN(?);");
        fetchPlayerArtifactStatus = connection.prepareStatement("SELECT `artifact_status` FROM `" + TABLE_NAME + "` WHERE `uuid` = UUID_TO_BIN(?);");
        setPlayerArtifactStatus = connection.prepareStatement("UPDATE `" + TABLE_NAME + "` SET `artifact_status` = b? WHERE `uuid` = UUID_TO_BIN(?);");
        fetchPlayerAttributeUpgrades = connection.prepareStatement("SELECT `attribute_upgrades` FROM `" + TABLE_NAME + "` WHERE `uuid` = UUID_TO_BIN(?);");
        setPlayerAttributeUpgrades = connection.prepareStatement("UPDATE `" + TABLE_NAME + "` SET `attribute_upgrades` = b? WHERE `uuid` = UUID_TO_BIN(?);");
        fetchPlayerItemSettings = connection.prepareStatement("SELECT `item_settings` FROM `" + TABLE_NAME + "` WHERE `uuid` = UUID_TO_BIN(?);");
        setPlayerItemSettings = connection.prepareStatement("UPDATE `" + TABLE_NAME + "` SET `item_settings` = b? WHERE `uuid` = UUID_TO_BIN(?);");
        fetchPlayerMiscSettings = connection.prepareStatement("SELECT `misc_settings` FROM `" + TABLE_NAME + "` WHERE `uuid` = UUID_TO_BIN(?);");
        setPlayerMiscSettings = connection.prepareStatement("UPDATE `" + TABLE_NAME + "` SET `misc_settings` = b? WHERE `uuid` = UUID_TO_BIN(?);");
        Bukkit.getScheduler().runTaskTimerAsynchronously(Prototypes.instance, () -> {
            try {
                connection.createStatement().execute(refreshConnection);
            } catch(SQLException ignored) {}
        }, 1, 288007);
    }
    public static void fina() {
        tryClose(checkPlayerExistence);
        tryClose(registerPlayer);
        tryClose(fetchPlayerEmeralds);
        tryClose(addPlayerEmeralds);
        tryClose(fetchPlayerXp);
        tryClose(addPlayerXp);
        tryClose(fetchPlayerKills);
        tryClose(incrementPlayerKills);
        tryClose(fetchPlayerDeaths);
        tryClose(incrementPlayerDeaths);
        tryClose(fetchPlayerArmorStatus);
        tryClose(setPlayerArmorStatus);
        tryClose(fetchPlayerWeaponStatus);
        tryClose(setPlayerWeaponStatus);
        tryClose(fetchPlayerArtifactStatus);
        tryClose(setPlayerArtifactStatus);
        tryClose(fetchPlayerAttributeUpgrades);
        tryClose(setPlayerAttributeUpgrades);
        tryClose(fetchPlayerItemSettings);
        tryClose(setPlayerItemSettings);
        tryClose(fetchPlayerMiscSettings);
        tryClose(setPlayerMiscSettings);
        tryClose(connection);
    }
    public static void playerJoin(UUID uuid) {
        playerLocks.put(uuid, new Object());
        registerPlayerIfNotExists(uuid);
        playerAttributeUpgradesCache.put(uuid, fetchPlayerAttributeUpgrades(uuid));
        playerMiscSettingsCache.put(uuid, fetchPlayerMiscSettings(uuid));
    }
    public static void playerQuit(UUID uuid) {
        playerAttributeUpgradesCache.remove(uuid);
        playerMiscSettingsCache.remove(uuid);
        playerLocks.remove(uuid);
    }
    public static Object getPlayerLock(UUID uuid) {
        return playerLocks.get(uuid);
    }
    private static void registerPlayerIfNotExists(UUID uuid) {
        try {
            final ResultSet result;
            synchronized(checkPlayerExistence) {
                checkPlayerExistence.setString(1, uuid.toString());
                result = checkPlayerExistence.executeQuery();
            }
            if(!result.next()) {
                synchronized(registerPlayer) {
                    registerPlayer.setString(1, uuid.toString());
                    registerPlayer.executeUpdate();
                }
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database operation: " + e.getMessage());
        }
    }
    public static long fetchPlayerEmeralds(UUID uuid) {
        try {
            final ResultSet result;
            synchronized(fetchPlayerEmeralds) {
                fetchPlayerEmeralds.setString(1, uuid.toString());
                result = fetchPlayerEmeralds.executeQuery();
            }
            if(result.next()) {
                return result.getLong("emeralds");
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database query: " + e.getMessage());
        }
        return 0;
    }
    public static void addPlayerEmeralds(UUID uuid, long amount) {
        try {
            synchronized(addPlayerEmeralds) {
                addPlayerEmeralds.setLong(1, amount);
                addPlayerEmeralds.setString(2, uuid.toString());
                addPlayerEmeralds.executeUpdate();
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database update: " + e.getMessage());
        }
    }
    public static int fetchPlayerXp(UUID uuid) {
        try {
            final ResultSet result;
            synchronized(fetchPlayerXp) {
                fetchPlayerXp.setString(1, uuid.toString());
                result = fetchPlayerXp.executeQuery();
            }
            if(result.next()) {
                return result.getInt("xp");
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database query: " + e.getMessage());
        }
        return 0;
    }
    public static void addPlayerXp(UUID uuid, int amount) {
        try {
            synchronized(addPlayerXp) {
                addPlayerXp.setInt(1, amount);
                addPlayerXp.setString(2, uuid.toString());
                addPlayerXp.executeUpdate();
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database update: " + e.getMessage());
        }
    }
    public static int fetchPlayerKills(UUID uuid) {
        try {
            final ResultSet result;
            synchronized(fetchPlayerKills) {
                fetchPlayerKills.setString(1, uuid.toString());
                result = fetchPlayerKills.executeQuery();
            }
            if(result.next()) {
                return result.getInt("kills");
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database query: " + e.getMessage());
        }
        return 0;
    }
    public static void incrementPlayerKills(UUID uuid) {
        try {
            synchronized(incrementPlayerKills) {
                incrementPlayerKills.setString(1, uuid.toString());
                incrementPlayerKills.executeUpdate();
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database update: " + e.getMessage());
        }
    }
    public static int fetchPlayerDeaths(UUID uuid) {
        try {
            final ResultSet result;
            synchronized(fetchPlayerDeaths) {
                fetchPlayerDeaths.setString(1, uuid.toString());
                result = fetchPlayerDeaths.executeQuery();
            }
            if(result.next()) {
                return result.getInt("deaths");
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database query: " + e.getMessage());
        }
        return 0;
    }
    public static void incrementPlayerDeaths(UUID uuid) {
        try {
            synchronized(incrementPlayerDeaths) {
                incrementPlayerDeaths.setString(1, uuid.toString());
                incrementPlayerDeaths.executeUpdate();
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database update: " + e.getMessage());
        }
    }
    public static boolean[] fetchPlayerArmorStatus(UUID uuid) {
        try {
            final ResultSet result;
            synchronized(fetchPlayerArmorStatus) {
                fetchPlayerArmorStatus.setString(1, uuid.toString());
                result = fetchPlayerArmorStatus.executeQuery();
            }
            if(result.next()) {
                return bytesToBooleans(result.getBytes("armor_status"));
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database query: " + e.getMessage());
        }
        return new boolean[48];
    }
    public static void setPlayerArmorStatus(UUID uuid, boolean[] val) {
        try {
            synchronized(setPlayerArmorStatus) {
                setPlayerArmorStatus.setString(1, booleansToString(val));
                setPlayerArmorStatus.setString(2, uuid.toString());
                setPlayerArmorStatus.executeUpdate();
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database update: " + e.getMessage());
        }
    }
    public static boolean[] fetchPlayerWeaponStatus(UUID uuid) {
        try {
            final ResultSet result;
            synchronized(fetchPlayerWeaponStatus) {
                fetchPlayerWeaponStatus.setString(1, uuid.toString());
                result = fetchPlayerWeaponStatus.executeQuery();
            }
            if(result.next()) {
                return bytesToBooleans(result.getBytes("weapon_status"));
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database query: " + e.getMessage());
        }
        return new boolean[80];
    }
    public static void setPlayerWeaponStatus(UUID uuid, boolean[] val) {
        try {
            synchronized(setPlayerWeaponStatus) {
                setPlayerWeaponStatus.setString(1, booleansToString(val));
                setPlayerWeaponStatus.setString(2, uuid.toString());
                setPlayerWeaponStatus.executeUpdate();
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database update: " + e.getMessage());
        }
    }
    public static boolean[] fetchPlayerArtifactStatus(UUID uuid) {
        try {
            final ResultSet result;
            synchronized(fetchPlayerArtifactStatus) {
                fetchPlayerArtifactStatus.setString(1, uuid.toString());
                result = fetchPlayerArtifactStatus.executeQuery();
            }
            if(result.next()) {
                return bytesToBooleans(result.getBytes("artifact_status"));
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database query: " + e.getMessage());
        }
        return new boolean[256];
    }
    public static void setPlayerArtifactStatus(UUID uuid, boolean[] val) {
        try {
            synchronized(setPlayerArtifactStatus) {
                setPlayerArtifactStatus.setString(1, booleansToString(val));
                setPlayerArtifactStatus.setString(2, uuid.toString());
                setPlayerArtifactStatus.executeUpdate();
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database update: " + e.getMessage());
        }
    }
    public static AttributeUpgrades getPlayerAttributeUpgrades(UUID uuid) {
        if(playerAttributeUpgradesCache.containsKey(uuid)) {
            return playerAttributeUpgradesCache.get(uuid);
        }
        return fetchPlayerAttributeUpgrades(uuid);
    }
    private static AttributeUpgrades fetchPlayerAttributeUpgrades(UUID uuid) {
        try {
            final ResultSet result;
            synchronized(fetchPlayerAttributeUpgrades) {
                fetchPlayerAttributeUpgrades.setString(1, uuid.toString());
                result = fetchPlayerAttributeUpgrades.executeQuery();
            }
            if(result.next()) {
                final AttributeUpgrades upgrades = new AttributeUpgrades(result.getBytes("attribute_upgrades"));
                playerAttributeUpgradesCache.put(uuid, upgrades);
                return upgrades;
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database query: " + e.getMessage());
        }
        return null;
    }
    public static void setPlayerAttributeUpgrades(UUID uuid, AttributeUpgrades upgrades) {
        playerAttributeUpgradesCache.put(uuid, upgrades);
        Bukkit.getScheduler().runTaskAsynchronously(Prototypes.instance, () -> {
            try {
                synchronized(setPlayerAttributeUpgrades) {
                    setPlayerAttributeUpgrades.setString(1, booleansToString(bytesToBooleans(upgrades.asBytes())));
                    setPlayerAttributeUpgrades.setString(2, uuid.toString());
                    setPlayerAttributeUpgrades.executeUpdate();
                }
            } catch(SQLException e) {
                Prototypes.logWarning("Error executing database update: " + e.getMessage());
            }
        });
    }
    public static ItemSettings fetchPlayerItemSettings(UUID uuid) {
        try {
            final ResultSet result;
            synchronized(fetchPlayerItemSettings) {
                fetchPlayerItemSettings.setString(1, uuid.toString());
                result = fetchPlayerItemSettings.executeQuery();
            }
            if(result.next()) {
                return new ItemSettings(result.getBytes("item_settings"));
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database query: " + e.getMessage());
        }
        return null;
    }
    public static void setPlayerItemSettings(UUID uuid, ItemSettings settings) {
        try {
            synchronized(setPlayerItemSettings) {
                setPlayerItemSettings.setString(1, booleansToString(bytesToBooleans(settings.asBytes())));
                setPlayerItemSettings.setString(2, uuid.toString());
                setPlayerItemSettings.executeUpdate();
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database update: " + e.getMessage());
        }
    }
    public static MiscSettings getPlayerMiscSettings(UUID uuid) {
        if(playerMiscSettingsCache.containsKey(uuid)) {
            return playerMiscSettingsCache.get(uuid);
        }
        return fetchPlayerMiscSettings(uuid);
    }
    private static MiscSettings fetchPlayerMiscSettings(UUID uuid) {
        try {
            final ResultSet result;
            synchronized(fetchPlayerMiscSettings) {
                fetchPlayerMiscSettings.setString(1, uuid.toString());
                result = fetchPlayerMiscSettings.executeQuery();
            }
            if(result.next()) {
                final MiscSettings settings = new MiscSettings(result.getBytes("misc_settings"));
                playerMiscSettingsCache.put(uuid, settings);
                return settings;
            }
        } catch(SQLException e) {
            Prototypes.logWarning("Error executing database query: " + e.getMessage());
        }
        return null;
    }
    public static void setPlayerMiscSettings(UUID uuid, MiscSettings settings) {
        playerMiscSettingsCache.put(uuid, settings);
        Bukkit.getScheduler().runTaskAsynchronously(Prototypes.instance, () -> {
            try {
                synchronized(setPlayerMiscSettings) {
                    setPlayerMiscSettings.setString(1, booleansToString(settings.asBooleans()));
                    setPlayerMiscSettings.setString(2, uuid.toString());
                    setPlayerMiscSettings.executeUpdate();
                }
            } catch(SQLException e) {
                Prototypes.logWarning("Error executing database update: " + e.getMessage());
            }
        });
    }
    private static void tryClose(AutoCloseable closeable) {
        if(closeable != null) {
            try {
                closeable.close();
            } catch(Exception ignored) {}
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
    public enum TriState {
        FALSE, TRUE, DEFAULT;
        private static final int SIZE = 2;
        private static TriState fromBooleans(boolean b1, boolean b2) {
            return b1 ? b2 ? TRUE : FALSE : DEFAULT;
        }
        public boolean isDefault() {
            return this == DEFAULT;
        }
        public boolean isTrue() {
            return this == TRUE;
        }
        public TriState next() {
            return isDefault() ? FALSE : isTrue() ? DEFAULT : TRUE;
        }
        private boolean firstBoolean() {
            return this != DEFAULT;
        }
        private boolean secondBoolean() {
            return this == TRUE;
        }
    }
    public static final class AttributeUpgrades {
        private final Byte[] data = new Byte[16];
        public static final byte HEALTH_BONUS = 0;
        public static final byte PROTECTION_LEVEL = 1;
        public static final byte KNOCKBACK_RESISTANCE = 2;
        public static final byte SPEED_BONUS_LEVEL = 4;
        public static final byte JUMP_BONUS_LEVEL = 5;
        public static final byte COOLDOWN_LEVEL = 6;
        public static final byte EFFICIENCY_LEVEL = 7;
        public static final byte SHARPNESS_LEVEL = 8;
        public static final byte RAMPAGING_LEVEL = 9;
        public static final byte KNOCKBACK_LEVEL = 10;
        public static final byte POWER_LEVEL = 12;
        public static final byte RAPID_FIRE_LEVEL = 13;
        public static final byte PUNCH_LEVEL = 14;
        public static final long[][] prices = new long[][]{
            {400, 800, 1600, 3200},
            {400, 800, 1600, 3200, 6400},
            {200, 400, 800, 1600, 3200},
            {0, 0, 0, 0, 0},
            {500, 1000, 2000, 4000},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {600, 1200, 2400, 4800},
            {400, 800, 1600, 3200, 6400},
            {300, 600, 1200, 2400, 4800},
            {0, 0, 0, 0, 0},
            {600, 1200, 2400, 4800},
            {400, 800, 1600, 3200, 6400},
            {300, 600, 1200, 2400, 4800},
            {0, 0, 0, 0, 0},
        };
        private AttributeUpgrades(byte[] data) {
            if(data.length != 16) {
                throw new IllegalArgumentException("Data has to be 16 bytes long!");
            }
            for(int i = 0; i < 16; i++) {
                this.data[i] = data[i];
            }
        }
        public byte getData(byte upgrade) {
            return data[upgrade];
        }
        public AttributeUpgrades withIncrementedData(byte upgrade) {
            data[upgrade]++;
            return this;
        }
        public byte[] asBytes() {
            byte[] data = new byte[16];
            for(int i = 0; i < 16; i++) {
                data[i] = this.data[i];
            }
            return data;
        }
    }
    public static final class MiscSettings {
        private boolean showGuideBook;
        private boolean deployCooldown;
        private TriState rangedAttackIndicator;
        private MiscSettings(final byte[] bytes) {
            final boolean[] data = bytesToBooleans(bytes);
            showGuideBook = data[0];
            deployCooldown = data[1];
            rangedAttackIndicator = TriState.fromBooleans(data[8], data[9]);
        }
        public boolean shouldShowGuideBook() {
            return showGuideBook;
        }
        public boolean hasDeployCooldown() {
            return deployCooldown;
        }
        public TriState getRangedAttackIndicator() {
            return rangedAttackIndicator;
        }
        public MiscSettings withShowGuideBook(boolean showGuideBook) {
            this.showGuideBook = showGuideBook;
            return this;
        }
        public MiscSettings withDeployCooldown(boolean deployCooldown) {
            this.deployCooldown = deployCooldown;
            return this;
        }
        public MiscSettings withRangedAttackIndicator(TriState rangedAttackIndicator) {
            this.rangedAttackIndicator = rangedAttackIndicator;
            return this;
        }
        private boolean[] asBooleans() {
            boolean[] booleans = new boolean[16];
            booleans[0] = showGuideBook;
            booleans[1] = deployCooldown;
            booleans[8] = rangedAttackIndicator.firstBoolean();
            booleans[9] = rangedAttackIndicator.secondBoolean();
            return booleans;
        }
    }
    public static final class ItemSettings {
        private short armorSelection;
        private final short[] weaponSelections = new short[2];
        private final short[] artifactSelections = new short[4];
        private final byte[] weaponSlots = new byte[2];
        private final byte[] artifactSlots = new byte[4];
        private ItemSettings(final byte[] data) {
            if(data.length != 13) {
                throw new IllegalArgumentException("Data has to be 13 bytes long!");
            }
            armorSelection = (short) Byte.toUnsignedInt(data[0]);
            weaponSelections[0] = (short) Byte.toUnsignedInt(data[1]);
            weaponSelections[1] = (short) Byte.toUnsignedInt(data[2]);
            artifactSelections[0] = (short) Byte.toUnsignedInt(data[3]);
            artifactSelections[1] = (short) Byte.toUnsignedInt(data[4]);
            artifactSelections[2] = (short) Byte.toUnsignedInt(data[5]);
            artifactSelections[3] = (short) Byte.toUnsignedInt(data[6]);
            weaponSlots[0] = data[7];
            weaponSlots[1] = data[8];
            artifactSlots[0] = data[9];
            artifactSlots[1] = data[10];
            artifactSlots[2] = data[11];
            artifactSlots[3] = data[12];
        }
        public short getArmorSelection() {
            return armorSelection;
        }
        public short getWeaponSelection(int weapon) {
            return weaponSelections[weapon];
        }
        public short getArtifactSelection(int artifact) {
            return artifactSelections[artifact];
        }
        public byte getWeaponSlot(int weapon) {
            return weaponSlots[weapon];
        }
        public byte getArtifactSlot(int artifact) {
            return artifactSlots[artifact];
        }
        public ItemSettings withArmorSelection(short armorSelection) {
            this.armorSelection = armorSelection;
            return this;
        }
        public ItemSettings withWeaponSelection(int weapon, short weaponSelection) {
            this.weaponSelections[weapon] = weaponSelection;
            return this;
        }
        public ItemSettings withArtifactSelection(int artifact, short artifactSelection) {
            this.artifactSelections[artifact] = artifactSelection;
            return this;
        }
        public ItemSettings withWeaponSlot(int weapon, byte slot) {
            this.weaponSlots[weapon] = slot;
            return this;
        }
        public ItemSettings withArtifactSlot(int artifact, byte slot) {
            this.artifactSlots[artifact] = slot;
            return this;
        }
        private byte[] asBytes() {
            return new byte[] {(byte) armorSelection, (byte) weaponSelections[0], (byte) weaponSelections[1], (byte) artifactSelections[0], (byte) artifactSelections[1], (byte) artifactSelections[2], (byte) artifactSelections[3], weaponSlots[0], weaponSlots[1], artifactSlots[0], artifactSlots[1], artifactSlots[2], artifactSlots[3]};
        }
    }
}
