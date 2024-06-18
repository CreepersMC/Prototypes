package re.imc.creeperspvp;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import re.imc.creeperspvp.items.ItemManager;
import re.imc.creeperspvp.iui.IUIManager;
import re.imc.creeperspvp.utils.DatabaseUtils;
import re.imc.creeperspvp.utils.Utils;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
public final class CreepersPVP extends JavaPlugin {
    public static CreepersPVP instance;
    private static Logger logger;
    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();
        Utils.init();
        ItemManager.init();
        IUIManager.init();
        try {
            DatabaseUtils.init();
        } catch(SQLException e) {
            CreepersPVP.logWarning("Error initializing database: " + e.getMessage());
        }
        Bukkit.getPluginManager().registerEvents(Listener.instance, instance);
        getLogger().log(Level.INFO, "CreepersPVP initialized.");
    }
    @Override
    public void onDisable() {
        try {
            DatabaseUtils.fina();
        } catch(SQLException e) {
            CreepersPVP.logWarning("Error finalizing database: " + e.getMessage());
        }
        Utils.fina();
        getLogger().log(Level.INFO, "CreepersPVP finalized.");
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
}
