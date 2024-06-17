package re.imc.creeperspvp.utils;
import re.imc.creeperspvp.CreepersPVP;
import java.sql.*;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
public final class DatabaseUtils {
    private static PreparedStatement checkPlayerExistence;
    private static PreparedStatement registerPlayer;
    private static PreparedStatement fetchPlayerEmeralds;
    private static PreparedStatement addPlayerEmeralds;
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
    private static final ConcurrentHashMap<UUID, Object> playerLocks = new ConcurrentHashMap<>();
    private static final String databaseAddress = "127.0.0.1"; //TODO
    private static final String databasePort = "3306";
    private static final String databaseName = "creepersmc";
    private static final String databaseUser = "root";
    private static final String databasePassword = "1145141919810";
    private static Connection connection = null;
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
            `artifact_status` BINARY(32) NOT NULL DEFAULT b'1000000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000001000000000000000000000000000000000000000000000000000000000000000',
            PRIMARY KEY(`uuid`)
        ) ENGINE = INNODB DEFAULT CHARSET = utf8mb4;
        """;
    private DatabaseUtils() {}
    public static void init() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            CreepersPVP.logSevere("Error loading database driver: " + e.getMessage());
        }
        connection = DriverManager.getConnection(databaseConnectionURL);
        connection.createStatement().execute(databaseInit);
        checkPlayerExistence = connection.prepareStatement("SELECT `uuid` FROM `player_data` WHERE `uuid` = UUID_TO_BIN(?);");
        registerPlayer = connection.prepareStatement("INSERT INTO `player_data` (`uuid`) VALUES (UUID_TO_BIN(?))");
        fetchPlayerEmeralds = connection.prepareStatement("SELECT `emeralds` FROM `player_data` WHERE `uuid` = UUID_TO_BIN(?);");
        addPlayerEmeralds = connection.prepareStatement("UPDATE `player_data` SET `emeralds` = `emeralds` + ? WHERE `uuid` = UUID_TO_BIN(?);");
        fetchPlayerKills = connection.prepareStatement("SELECT `kills` FROM `player_data` WHERE `uuid` = UUID_TO_BIN(?);");
        incrementPlayerKills = connection.prepareStatement("UPDATE `player_data` SET `kills` = `kills` + 1 WHERE `uuid` = UUID_TO_BIN(?);");
        fetchPlayerDeaths = connection.prepareStatement("SELECT `deaths` FROM `player_data` WHERE `uuid` = UUID_TO_BIN(?);");
        incrementPlayerDeaths = connection.prepareStatement("UPDATE `player_data` SET `deaths` = `deaths` + 1 WHERE `uuid` = UUID_TO_BIN(?);");
        fetchPlayerArmorStatus = connection.prepareStatement("SELECT `armor_status` FROM `player_data` WHERE `uuid` = UUID_TO_BIN(?);");
        setPlayerArmorStatus = connection.prepareStatement("UPDATE `player_data` SET `armor_status` = b? WHERE `uuid` = UUID_TO_BIN(?);");
        fetchPlayerWeaponStatus = connection.prepareStatement("SELECT `weapon_status` FROM `player_data` WHERE `uuid` = UUID_TO_BIN(?);");
        setPlayerWeaponStatus = connection.prepareStatement("UPDATE `player_data` SET `weapon_status` = b? WHERE `uuid` = UUID_TO_BIN(?);");
        fetchPlayerArtifactStatus = connection.prepareStatement("SELECT `artifact_status` FROM `player_data` WHERE `uuid` = UUID_TO_BIN(?);");
        setPlayerArtifactStatus = connection.prepareStatement("UPDATE `player_data` SET `artifact_status` = b? WHERE `uuid` = UUID_TO_BIN(?);");
    }
    public static void fina() throws SQLException {
        fetchPlayerEmeralds.close();
        connection.close();
    }
    public static void playerJoin(UUID uuid) {
        playerLocks.put(uuid, new Object());
        DatabaseUtils.registerPlayerIfNotExists(uuid);
    }
    public static void playerQuit(UUID uuid) {
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
            CreepersPVP.logWarning("Error executing database operation: " + e.getMessage());
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
            CreepersPVP.logWarning("Error executing database query: " + e.getMessage());
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
            CreepersPVP.logWarning("Error executing database update: " + e.getMessage());
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
            CreepersPVP.logWarning("Error executing database query: " + e.getMessage());
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
            CreepersPVP.logWarning("Error executing database update: " + e.getMessage());
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
            CreepersPVP.logWarning("Error executing database query: " + e.getMessage());
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
            CreepersPVP.logWarning("Error executing database update: " + e.getMessage());
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
            CreepersPVP.logWarning("Error executing database query: " + e.getMessage());
        }
        return new boolean[64];
    }
    public static void setPlayerArmorStatus(UUID uuid, boolean[] val) {
        try {
            synchronized(setPlayerArmorStatus) {
                setPlayerArmorStatus.setString(1, booleansToString(val));
                setPlayerArmorStatus.setString(2, uuid.toString());
                setPlayerArmorStatus.executeUpdate();
            }
        } catch(SQLException e) {
            CreepersPVP.logWarning("Error executing database update: " + e.getMessage());
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
            CreepersPVP.logWarning("Error executing database query: " + e.getMessage());
        }
        return new boolean[64];
    }
    public static void setPlayerWeaponStatus(UUID uuid, boolean[] val) {
        try {
            synchronized(setPlayerWeaponStatus) {
                setPlayerWeaponStatus.setString(1, booleansToString(val));
                setPlayerWeaponStatus.setString(2, uuid.toString());
                setPlayerWeaponStatus.executeUpdate();
            }
        } catch(SQLException e) {
            CreepersPVP.logWarning("Error executing database update: " + e.getMessage());
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
            CreepersPVP.logWarning("Error executing database query: " + e.getMessage());
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
            CreepersPVP.logWarning("Error executing database update: " + e.getMessage());
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
}
