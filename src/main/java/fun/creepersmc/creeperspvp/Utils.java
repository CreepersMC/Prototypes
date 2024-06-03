package fun.creepersmc.creeperspvp;
import fun.creepersmc.creeperspvp.iui.IUIManager;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.util.ListIterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
public final class Utils {
    private static final Location hub = new Location(Bukkit.getWorld("world"), 0, 197, 0);
    private static final Location spawn = new Location(Bukkit.getWorld("world"), 0, 134, 0);
    public static NamespacedKey itemKey;
    public static NamespacedKey artifactIDKey;
    public static NamespacedKey iuiIDKey;
    public static NamespacedKey iuiDataKey;
    public static NamespacedKey weaponSelectorIDKey;
    public static NamespacedKey artifactSelectorIDKey;
    public static NamespacedKey utilIDKey;
    public static NamespacedKey utilDataKey;
    public static NamespacedKey playerDataKey;
    public static NamespacedKey armorKey;
    public static final NamespacedKey[] weaponKeys = new NamespacedKey[2];
    public static final NamespacedKey[] artifactKeys = new NamespacedKey[3];
    public static final byte UTIL_SPAWN = 0;
    @SuppressWarnings("unchecked")
    private static final ConcurrentHashMap<UUID, ScheduledTask>[] gainArtifactSchedulers = new ConcurrentHashMap[] {new ConcurrentHashMap<UUID, ScheduledTask>(), new ConcurrentHashMap<UUID, ScheduledTask>(), new ConcurrentHashMap<UUID, ScheduledTask>()};
    private Utils() {}
    public static void init() {
        itemKey = new NamespacedKey(CreepersPVP.instance, "item");
        artifactIDKey = new NamespacedKey(CreepersPVP.instance, "artifact-id");
        //artifactDisabledKey = new NamespacedKey(CreepersPVP.instance, "artifact-disabled");
        iuiIDKey = new NamespacedKey(CreepersPVP.instance, "iui-id");
        iuiDataKey = new NamespacedKey(CreepersPVP.instance, "iui-data");
        weaponSelectorIDKey = new NamespacedKey(CreepersPVP.instance, "weapon");
        artifactSelectorIDKey = new NamespacedKey(CreepersPVP.instance, "artifact");
        utilIDKey = new NamespacedKey(CreepersPVP.instance, "util");
        utilDataKey = new NamespacedKey(CreepersPVP.instance, "util-data");
        playerDataKey = new NamespacedKey(CreepersPVP.instance, "player-data");
        armorKey = new NamespacedKey(CreepersPVP.instance, "armor");
        for(int i = 0; i < weaponKeys.length; i++) {
            weaponKeys[i] = new NamespacedKey(CreepersPVP.instance, "weapon" + i);
        }
        for(int i = 0; i < artifactKeys.length; i++) {
            artifactKeys[i] = new NamespacedKey(CreepersPVP.instance, "artifact" + i);
        }
        ItemManager.init();
        IUIManager.init();
        Bukkit.getPluginManager().registerEvents(Listener.instance, CreepersPVP.instance);
    }
    public static void playerInit(Player player) {
        removeGainArtifactSchedulers(player.getUniqueId());
        final PersistentDataContainer pdc = player.getPersistentDataContainer();
        PersistentDataContainer data = pdc.get(playerDataKey, PersistentDataType.TAG_CONTAINER);
        if(data == null) {
            pdc.set(playerDataKey, PersistentDataType.TAG_CONTAINER, pdc.getAdapterContext().newPersistentDataContainer());
        }
        final PlayerInventory inv = player.getInventory();
        inv.clear();
        inv.setItem(0, ItemManager.ARMOR_SELECTOR);
        inv.setItem(1, ItemManager.WEAPON_SELECTORS[0]);
        inv.setItem(2, ItemManager.WEAPON_SELECTORS[1]);
        inv.setItem(4, ItemManager.DEPLOY);
        inv.setItem(6, ItemManager.ARTIFACT_SELECTORS[0]);
        inv.setItem(7, ItemManager.ARTIFACT_SELECTORS[1]);
        inv.setItem(8, ItemManager.ARTIFACT_SELECTORS[2]);
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
        final PersistentDataContainer data = player.getPersistentDataContainer().get(playerDataKey, PersistentDataType.TAG_CONTAINER);
        final PlayerInventory inv = player.getInventory();
        inv.clear();
        final int armorID = data.getOrDefault(armorKey, PersistentDataType.INTEGER, ItemManager.MERCENARY_ARMOR);
        inv.setItem(EquipmentSlot.HEAD, ItemManager.armor[armorID][0]);
        inv.setItem(EquipmentSlot.CHEST, ItemManager.armor[armorID][1]);
        inv.setItem(EquipmentSlot.LEGS, ItemManager.armor[armorID][2]);
        inv.setItem(EquipmentSlot.FEET, ItemManager.armor[armorID][3]);
        inv.setItem(0, ItemManager.weapons[data.getOrDefault(weaponKeys[0], PersistentDataType.INTEGER, ItemManager.SWORD)]);
        inv.setItem(1, ItemManager.weapons[data.getOrDefault(weaponKeys[1], PersistentDataType.INTEGER, ItemManager.AXE)]);//TODO
        final int[] artifactIDs = new int[3];
        for(int i = 0; i < artifactIDs.length; i++) {
            artifactIDs[i] = data.getOrDefault(artifactKeys[i], PersistentDataType.INTEGER, new int[]{ItemManager.SHIELD, ItemManager.COOKED_BEEF, ItemManager.ENDER_PEARL}[i]);
        }
        inv.setItem(2, ItemManager.artifacts[artifactIDs[0]]);
        inv.setItem(3, ItemManager.artifacts[artifactIDs[1]]);
        inv.setItem(4, ItemManager.artifacts[artifactIDs[2]]);
        for(int i = 0; i < 5; i++) {
            int finalI = i;
            inv.getItem(i).editMeta(meta -> {
                meta.getPersistentDataContainer().set(itemKey, PersistentDataType.INTEGER, finalI);
            });
        }
        inv.setItem(9, new ItemStack(Material.ARROW, 64));
        player.getActivePotionEffects().clear();
        player.setHealth(20); //TODO get max health
        player.setFoodLevel(20);
        player.setSaturation(5);
        player.setExhaustion(0);
        player.setFallDistance(0);
        player.setGameMode(GameMode.ADVENTURE);
        player.setInvulnerable(false);
        player.teleport(spawn);
    }
    public static boolean hasGainArtifactScheduler(int artifact, UUID uuid) {
        return gainArtifactSchedulers[artifact].containsKey(uuid) && !gainArtifactSchedulers[artifact].get(uuid).isCancelled();
    }
    public static void scheduleGainArtifact(int artifact, UUID uuid, ScheduledTask task) {
        gainArtifactSchedulers[artifact].put(uuid, task);
    }
    private static void removeGainArtifactSchedulers(UUID uuid) {
        for(ConcurrentHashMap<UUID, ScheduledTask> gainArtifactScheduler : gainArtifactSchedulers) {
            if(gainArtifactScheduler.containsKey(uuid)) {
                gainArtifactScheduler.get(uuid).cancel();
                gainArtifactScheduler.remove(uuid);
            }
        }
    }
    public static int findItem(Inventory inv, int item) {
        ListIterator<ItemStack> i = inv.iterator();
        while(i.hasNext()) {
            ItemStack next = i.next();
            if(next != null) {
                if(item == next.getItemMeta().getPersistentDataContainer().getOrDefault(itemKey, PersistentDataType.INTEGER, -1)) {
                    return i.previousIndex();
                }
            }
        }
        return i.nextIndex();
    }
}
