package re.imc.creeperspvp;
import org.bukkit.plugin.java.JavaPlugin;
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
        getLogger().log(Level.INFO, "CreepersPVP initialized.");
    }
    @Override
    public void onDisable() {
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
