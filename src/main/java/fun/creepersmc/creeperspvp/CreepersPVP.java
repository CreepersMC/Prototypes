package fun.creepersmc.creeperspvp;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Level;
public final class CreepersPVP extends JavaPlugin {
    public static CreepersPVP instance;
    @Override
    public void onEnable() {
        instance = this;
        Utils.init();
        getLogger().log(Level.INFO, "CreepersPVP initialized.");
    }
    @Override
    public void onDisable() {
        instance = null;
    }
}
