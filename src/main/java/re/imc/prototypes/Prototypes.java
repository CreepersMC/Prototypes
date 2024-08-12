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
    public static JoinAfterStartHandling joinAfterStartHandling;
    public static boolean forgetPlayers;
    public static final Component version = Component.text("1.0.0-Pre3", NamedTextColor.DARK_GREEN);
    public static String databaseAddress;
    public static String databasePort;
    public static String database;
    public static String databaseUsername;
    public static String databasePassword;
    private static Logger logger;
    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        mode = GameMode.valueOf(config.getString("mode", GameMode.FFA.name()));
        stage = mode.startupPlayerCount > 0 ? GameStage.WAITING : GameStage.MAIN;
        joinAfterStartHandling = JoinAfterStartHandling.valueOf(config.getString("join-after-start", JoinAfterStartHandling.SPECTATE.name()));
        forgetPlayers = config.getBoolean("forget-players", false);
        databaseAddress = config.getString("database.address", "127.0.0.1");
        databasePort = config.getString("database.port", "3306");
        database = config.getString("database.database", "creepersmc");
        databaseUsername = config.getString("database.username", "root");
        databasePassword = config.getString("database.password", "password");
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
        FFA(0, 0, 0, Component.text("大乱斗", NamedTextColor.DARK_AQUA), Component.text("自由战斗", NamedTextColor.BLUE)),
        TDM(4, 2, 8, Component.text("团队死斗", NamedTextColor.RED), Component.text("组队战斗，击杀敌方玩家得分", NamedTextColor.DARK_RED)),
        DTM(6, 2, 6, Component.text("摧毁母体", NamedTextColor.LIGHT_PURPLE), Component.text("组队战斗，击杀敌方母体取得胜利", NamedTextColor.DARK_PURPLE)),
        CTF(4, 2, 6, Component.text("夺旗", NamedTextColor.GREEN), Component.text("组队战斗，夺取旗帜得分", NamedTextColor.DARK_GREEN)),
        CQT(4, 2, 8, Component.text("占点", NamedTextColor.YELLOW), Component.text("组队战斗，占领据点得分", NamedTextColor.GOLD));
        public final int startupPlayerCount, teamCount, teamSize;
        public final Component name, description;
        GameMode(final int startupPlayerCount, final int teamCount, final int teamSize, final Component name, final Component description) {
            this.startupPlayerCount = startupPlayerCount;
            this.teamCount = teamCount;
            this.teamSize = teamSize;
            this.name = name;
            this.description = description;
        }
    }
    public enum GameStage {
        WAITING, PREPARATION, MAIN, ENDED
    }
    public enum JoinAfterStartHandling {
        DISALLOW, SPECTATE, ALLOW
    }
}
