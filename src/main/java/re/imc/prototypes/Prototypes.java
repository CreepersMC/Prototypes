package re.imc.prototypes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import re.imc.prototypes.items.ItemManager;
import re.imc.prototypes.iui.IUIManager;
import re.imc.prototypes.utils.DatabaseUtils;
import re.imc.prototypes.utils.Utils;
import java.sql.SQLException;
import java.util.logging.Logger;
public final class Prototypes extends JavaPlugin {
    public static Prototypes instance;
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
            Prototypes.logWarning("Error initializing database: " + e.getMessage());
        }
        Bukkit.getPluginManager().registerEvents(Listener.instance, instance);
        logInfo("Prototypes initialized.");
        logInfo("Game mode: " + mode);
    }
    @Override
    public void onDisable() {
        DatabaseUtils.fina();
        Utils.fina();
        logInfo("Prototypes finalized.");
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
        FFA(Component.text("Free For All", NamedTextColor.BLUE), Component.text("自由战斗", NamedTextColor.DARK_AQUA)),
        TDM(Component.text("Team Deathmatch", NamedTextColor.RED), Component.text("组队战斗，率先拿到30击杀以取得胜利", NamedTextColor.DARK_RED)),
        CTF(Component.text("Capture the Flag", NamedTextColor.GREEN), Component.text("夺取旗帜来取得胜利", NamedTextColor.DARK_GREEN)),
        CQT(Component.text("Conquest", NamedTextColor.YELLOW), Component.text("占领据点来取得胜利", NamedTextColor.GOLD)),
        DTM(Component.text("Destroy the Matrix", NamedTextColor.LIGHT_PURPLE), Component.text("摧毁敌方队伍的母体来取得胜利", NamedTextColor.DARK_PURPLE));
        public final Component name, description;
        GameMode(final Component name, final Component description) {
            this.name = name;
            this.description = description;
        }
    }
    public enum GameStage {
        WAITING, PREPARATION, MAIN, ENDED
    }
}
