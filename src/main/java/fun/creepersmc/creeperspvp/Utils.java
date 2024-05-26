package fun.creepersmc.creeperspvp;
import fun.creepersmc.creeperspvp.iui.IUIManager;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
public final class Utils {
    private static final Location hub = new Location(Bukkit.getWorld("world"), 0, 192, 0);
    private static final Location spawn = new Location(Bukkit.getWorld("world"), 0, 136, 0);
    private static final ItemStack armorSelector = new ItemStack(Material.IRON_CHESTPLATE);
    private static final ItemStack weapon1Selector = new ItemStack(Material.IRON_SWORD);
    private static final ItemStack weapon2Selector = new ItemStack(Material.IRON_AXE);
    private static final ItemStack deploy = new ItemStack(Material.COMPASS);
    private static final ItemStack artifact1Selector = new ItemStack(Material.GLASS_BOTTLE);
    private static final ItemStack artifact2Selector = new ItemStack(Material.GLASS_BOTTLE);
    private static final ItemStack artifact3Selector = new ItemStack(Material.GLASS_BOTTLE);
    public static final NamespacedKey utilKey = new NamespacedKey(CreepersPVP.instance, "util");
    public static final NamespacedKey utilData = new NamespacedKey(CreepersPVP.instance, "util-data");
    public static final byte UTIL_SPAWN = 0;
    private Utils() {}
    public static void init() {
        ItemManager.init();
        IUIManager.init();
        ItemMeta meta;
        meta = armorSelector.getItemMeta();
        meta.displayName(Component.text("选择盔甲"));
        meta.getPersistentDataContainer().set(IUIManager.idKey, PersistentDataType.BYTE, IUIManager.ARMOR_SELECTOR);
        armorSelector.setItemMeta(meta);
        meta = weapon1Selector.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "选择武器#1");
        meta.getPersistentDataContainer().set(IUIManager.idKey, PersistentDataType.BYTE, IUIManager.WEAPON_SELECTOR);
        weapon1Selector.setItemMeta(meta);
        meta = deploy.getItemMeta();
        meta.setDisplayName("" + ChatColor.RESET + ChatColor.GREEN + "进入战斗");
        meta.getPersistentDataContainer().set(utilKey, PersistentDataType.BYTE, UTIL_SPAWN);
        deploy.setItemMeta(meta);
        meta = artifact1Selector.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "选择法器#1");
        meta.getPersistentDataContainer().set(IUIManager.idKey, PersistentDataType.BYTE, IUIManager.ARTIFACT_SELECTOR);
        artifact1Selector.setItemMeta(meta);
        meta = artifact2Selector.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "选择法器#2");
        meta.getPersistentDataContainer().set(IUIManager.idKey, PersistentDataType.BYTE, IUIManager.ARTIFACT_SELECTOR);
        artifact2Selector.setItemMeta(meta);
        meta = artifact3Selector.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "选择法器#3");
        meta.getPersistentDataContainer().set(IUIManager.idKey, PersistentDataType.BYTE, IUIManager.ARTIFACT_SELECTOR);
        artifact3Selector.setItemMeta(meta);
        Bukkit.getPluginManager().registerEvents(Listener.instance, CreepersPVP.instance);
    }
    public static void playerInit(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(0, armorSelector);
        player.getInventory().setItem(1, weapon1Selector);
        player.getInventory().setItem(2, weapon2Selector);
        player.getInventory().setItem(4, deploy);
        player.getInventory().setItem(6, artifact1Selector);
        player.getInventory().setItem(7, artifact2Selector);
        player.getInventory().setItem(8, artifact3Selector);
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
        player.getInventory().setItem(0, ItemManager.SWORD);
        player.getInventory().setItem(1, ItemManager.AXE);
        player.getInventory().setItem(2, new ItemStack(Material.SHIELD));
        player.getInventory().setItem(3, new ItemStack(Material.COOKED_BEEF, 64));
        player.getInventory().setItem(4, new ItemStack(Material.ENDER_PEARL, 4));
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
