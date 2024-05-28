package fun.creepersmc.creeperspvp;
import fun.creepersmc.creeperspvp.iui.IUIManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
public final class Utils {
    private static final Location hub = new Location(Bukkit.getWorld("world"), 0, 197, 0);
    private static final Location spawn = new Location(Bukkit.getWorld("world"), 0, 134, 0);
    public static NamespacedKey iuiIDKey;
    public static NamespacedKey iuiDataKey;
    public static NamespacedKey utilIDKey;
    public static NamespacedKey utilDataKey;
    public static final byte UTIL_SPAWN = 0;
    private Utils() {}
    public static void init() {
        iuiIDKey = new NamespacedKey(CreepersPVP.instance, "iui-id");
        iuiDataKey = new NamespacedKey(CreepersPVP.instance, "iui-data");
        utilIDKey = new NamespacedKey(CreepersPVP.instance, "util");
        utilDataKey = new NamespacedKey(CreepersPVP.instance, "util-data");
        ItemManager.init();
        IUIManager.init();
        Bukkit.getPluginManager().registerEvents(Listener.instance, CreepersPVP.instance);
    }
    public static void playerInit(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(0, ItemManager.ARMOR_SELECTOR);
        player.getInventory().setItem(1, ItemManager.WEAPON1_SELECTOR);
        player.getInventory().setItem(2, ItemManager.WEAPON2_SELECTOR);
        player.getInventory().setItem(4, ItemManager.DEPLOY);
        player.getInventory().setItem(6, ItemManager.ARTIFACT1_SELECTOR);
        player.getInventory().setItem(7, ItemManager.ARTIFACT2_SELECTOR);
        player.getInventory().setItem(8, ItemManager.ARTIFACT3_SELECTOR);
        player.getInventory().setHeldItemSlot(4);
        player.getActivePotionEffects().clear();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.setExhaustion(Float.NEGATIVE_INFINITY);
        player.setGameMode(GameMode.ADVENTURE);
        player.setInvulnerable(true);
        player.teleport(hub);
    }
    public static void spawnPlayer(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(EquipmentSlot.HEAD, new ItemStack(Material.IRON_HELMET));
        player.getInventory().setItem(EquipmentSlot.CHEST, new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setItem(EquipmentSlot.LEGS, new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setItem(EquipmentSlot.FEET, new ItemStack(Material.IRON_BOOTS));
        player.getInventory().setItem(0, ItemManager.weapons[ItemManager.SWORD]);
        player.getInventory().setItem(1, ItemManager.weapons[ItemManager.AXE]);
        player.getInventory().setItem(2, new ItemStack(Material.SHIELD));
        player.getInventory().setItem(3, new ItemStack(Material.COOKED_BEEF, 64));
        player.getInventory().setItem(4, new ItemStack(Material.ENDER_PEARL, 4));
        player.getInventory().setItem(5, new ItemStack(Material.BOW));
        player.getInventory().setItem(9, new ItemStack(Material.ARROW, 114514));
        player.getActivePotionEffects().clear();
        player.setHealth(20); //TODO get max health
        player.setFoodLevel(20);
        player.setSaturation(5);
        player.setExhaustion(0);
        player.setGameMode(GameMode.ADVENTURE);
        player.setInvulnerable(false);
        player.setFallDistance(0);
        player.teleport(spawn);
    }
}
