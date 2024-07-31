package re.imc.creeperspvp;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import re.imc.creeperspvp.items.ItemManager;
import re.imc.creeperspvp.iui.IUIManager;
import re.imc.creeperspvp.utils.DatabaseUtils;
import re.imc.creeperspvp.utils.Utils;
import java.sql.SQLException;
import java.util.logging.Logger;
public final class CreepersPVP extends JavaPlugin {
    public static CreepersPVP instance;
    public static GameMode mode;
    public static GameStage stage;
    public static String databaseAddress = "127.0.0.1";
    public static String databasePort = "3306";
    public static String database = "creepersmc";
    public static String databaseUsername = "root";
    public static String databasePassword = "password";
    private static Logger logger;
    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        try {
            mode = GameMode.valueOf(config.getString("mode"));
        } catch(IllegalArgumentException e) {
            mode = GameMode.FFA;
        }
        stage = mode == GameMode.FFA ? GameStage.MAIN : GameStage.WAITING;
        databaseAddress = config.getString("database.address");
        databasePort = config.getString("database.port");
        database = config.getString("database.database");
        databaseUsername = config.getString("database.username");
        databasePassword = config.getString("database.password");
        Utils.init();
        ItemManager.init();
        IUIManager.init();
        try {
            DatabaseUtils.init();
        } catch(SQLException e) {
            CreepersPVP.logWarning("Error initializing database: " + e.getMessage());
        }
        Bukkit.getPluginManager().registerEvents(Listener.instance, instance);
        logInfo("CreepersPVP initialized.");
        logInfo("Game mode: " + mode);
    }
    @Override
    public void onDisable() {
        DatabaseUtils.fina();
        Utils.fina();
        logInfo("CreepersPVP finalized.");
        logger = null;
        instance = null;
    }
    public static void logInfo(final String info) {
        logger.info(info);
    }
    public static void logWarning(final String warning) {
        logger.warning(warning);
    }
    public static void logSevere(final String severe) {
        logger.severe(severe);
    }
    public enum GameMode {
        FFA, TDM, CTF, CQT
    }
    public enum GameStage {
        WAITING, PREPARATION, MAIN, ENDED
    }
}
