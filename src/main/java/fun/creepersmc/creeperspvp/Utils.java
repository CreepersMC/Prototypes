package fun.creepersmc.creeperspvp;
import fun.creepersmc.creeperspvp.iui.IUIManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
public final class Utils {
    private static final Location hub = new Location(Bukkit.getWorld("world"), 0, 197, 0);
    private static final Location spawn = new Location(Bukkit.getWorld("world"), 0, 134, 0);
    public static NamespacedKey iuiIDKey;
    public static NamespacedKey iuiDataKey;
    public static NamespacedKey utilIDKey;
    public static NamespacedKey utilDataKey;
    public static NamespacedKey playerDataKey;
    public static NamespacedKey armorKey;
    public static NamespacedKey weapon1Key;
    public static NamespacedKey weapon2Key;
    public static NamespacedKey artifact1Key;
    public static NamespacedKey artifact2Key;
    public static NamespacedKey artifact3Key;
    public static final byte UTIL_SPAWN = 0;
    private Utils() {}
    public static void init() {
        iuiIDKey = new NamespacedKey(CreepersPVP.instance, "iui-id");
        iuiDataKey = new NamespacedKey(CreepersPVP.instance, "iui-data");
        utilIDKey = new NamespacedKey(CreepersPVP.instance, "util");
        utilDataKey = new NamespacedKey(CreepersPVP.instance, "util-data");
        playerDataKey = new NamespacedKey(CreepersPVP.instance, "player-data");
        armorKey = new NamespacedKey(CreepersPVP.instance, "armor");
        weapon1Key = new NamespacedKey(CreepersPVP.instance, "weapon1");
        weapon2Key = new NamespacedKey(CreepersPVP.instance, "weapon2");
        artifact1Key = new NamespacedKey(CreepersPVP.instance, "artifact1");
        artifact2Key = new NamespacedKey(CreepersPVP.instance, "artifact2");
        artifact3Key = new NamespacedKey(CreepersPVP.instance, "artifact3");
        ItemManager.init();
        IUIManager.init();
        Bukkit.getPluginManager().registerEvents(Listener.instance, CreepersPVP.instance);
    }
    public static void playerInit(Player player) {
        final PlayerInventory inv = player.getInventory();
        inv.clear();
        inv.setItem(0, ItemManager.ARMOR_SELECTOR);
        inv.setItem(1, ItemManager.WEAPON1_SELECTOR);
        inv.setItem(2, ItemManager.WEAPON2_SELECTOR);
        inv.setItem(4, ItemManager.DEPLOY);
        inv.setItem(6, ItemManager.ARTIFACT1_SELECTOR);
        inv.setItem(7, ItemManager.ARTIFACT2_SELECTOR);
        inv.setItem(8, ItemManager.ARTIFACT3_SELECTOR);
        inv.setHeldItemSlot(4);
        player.getActivePotionEffects().clear();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.setExhaustion(Float.NEGATIVE_INFINITY);
        player.setGameMode(GameMode.ADVENTURE);
        player.setInvulnerable(true);
        player.teleport(hub);
    }
    public static void spawnPlayer(final Player player) {
        final PersistentDataContainer pdc = player.getPersistentDataContainer();
        PersistentDataContainer data = pdc.get(playerDataKey, PersistentDataType.TAG_CONTAINER);
        if(data == null) {
            pdc.set(playerDataKey, PersistentDataType.TAG_CONTAINER, pdc.getAdapterContext().newPersistentDataContainer());
            data = pdc.get(playerDataKey, PersistentDataType.TAG_CONTAINER);
        }
        final PlayerInventory inv = player.getInventory();
        inv.clear();
        final int armorId = data.getOrDefault(armorKey, PersistentDataType.INTEGER, ItemManager.MERCENARY_ARMOR);
        inv.setItem(EquipmentSlot.HEAD, ItemManager.armor[armorId][0]);
        inv.setItem(EquipmentSlot.CHEST, ItemManager.armor[armorId][1]);
        inv.setItem(EquipmentSlot.LEGS, ItemManager.armor[armorId][2]);
        inv.setItem(EquipmentSlot.FEET, ItemManager.armor[armorId][3]);
        inv.setItem(0, ItemManager.weapons[data.getOrDefault(weapon1Key, PersistentDataType.INTEGER, ItemManager.SWORD)]);
        inv.setItem(1, ItemManager.weapons[data.getOrDefault(weapon2Key, PersistentDataType.INTEGER, ItemManager.AXE)]);
        inv.setItem(2, ItemManager.artifacts[data.getOrDefault(artifact1Key, PersistentDataType.INTEGER, ItemManager.SHIELD)]);
        inv.setItem(3, ItemManager.artifacts[data.getOrDefault(artifact2Key, PersistentDataType.INTEGER, ItemManager.COOKED_BEEF)]);
        inv.setItem(4, ItemManager.artifacts[data.getOrDefault(artifact3Key, PersistentDataType.INTEGER, ItemManager.ENDER_PEARL)]);
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
