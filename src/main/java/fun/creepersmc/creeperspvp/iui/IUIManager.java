package fun.creepersmc.creeperspvp.iui;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.FastInvManager;
import fun.creepersmc.creeperspvp.CreepersPVP;
import fun.creepersmc.creeperspvp.ItemManager;
import fun.creepersmc.creeperspvp.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.util.function.Function;
public final class IUIManager {
    public static final byte ARMOR_SELECTOR = 1;
    public static final byte WEAPON_SELECTOR = 2;
    public static final byte ARTIFACT_SELECTOR = 3;
    private static final FastInv[] iuis = new FastInv[] {
        null,
        ArmorSelectorInv.instance,
        WeaponSelectorInv.instance,
        ArtifactSelectorInv.instance,
    };
    public static void init() {
        FastInvManager.register(CreepersPVP.instance);
    }
    public static FastInv getIUI(byte id, PersistentDataContainer data) {
        return iuis[id] instanceof DynamicInv dynamicInv ? dynamicInv.getInv(data) : iuis[id];
    }
    private interface DynamicInv {
        FastInv getInv(PersistentDataContainer data);
    }
    private static abstract class CreeperInv extends FastInv {
        protected CreeperInv(int size) {
            super(size);
        }
        protected CreeperInv(int size, String title) {
            super(size, title);
        }
        protected CreeperInv(InventoryType type) {
            super(type);
        }
        protected CreeperInv(InventoryType type, String title) {
            super(type, title);
        }
        protected CreeperInv(Function<InventoryHolder, Inventory> inventoryFunction) {
            super(inventoryFunction);
        }
        @Override
        protected final void onClick(InventoryClickEvent event) {
            event.setCancelled(true);
        }
    }
    public static class ArmorSelectorInv extends CreeperInv implements DynamicInv {
        private static final ArmorSelectorInv instance = new ArmorSelectorInv();
        private ArmorSelectorInv() {
            super(54, "选择盔甲");
            setItems(getBorders(), ItemManager.BORDER);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            int slot = 10;
            for(final int armorSelection : ItemManager.armorSelections) {
                setItem(slot, ItemManager.armorSelectors[armorSelection], event -> {
                    if(event.getWhoClicked() instanceof Player player) {
                        if(event.isLeftClick()) {
                            if(true) { //TODO
                                PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                                data.set(Utils.armorKey, PersistentDataType.INTEGER, armorSelection);
                                player.getPersistentDataContainer().set(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER, data);
                            }
                        }
                        //player.getPersistentDataContainer().set();
                    }
                });
                slot++;
                if(slot % 9 == 8) {
                    slot += 2;
                }
            }
        }
        @Override
        public ArmorSelectorInv getInv(PersistentDataContainer data) {
            return new ArmorSelectorInv();
        }
    }
    public static final class WeaponSelectorInv extends CreeperInv implements DynamicInv {
        private static final WeaponSelectorInv instance = new WeaponSelectorInv();
        private WeaponSelectorInv() {
            super(InventoryType.PLAYER);
        }
        private WeaponSelectorInv(final int weapon) {
            super(54, "选择武器#" + (weapon + 1));
            setItems(getBorders(), ItemManager.BORDER);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            int slot = 10;
            for(final int weaponSelection : ItemManager.weaponSelections) {
                setItem(slot, ItemManager.weapons[weaponSelection], event -> {
                    if(event.getWhoClicked() instanceof Player player) {
                        if(event.isLeftClick()) {
                            if(true) { //TODO
                                PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                                data.set(Utils.weaponKeys[weapon], PersistentDataType.INTEGER, weaponSelection);
                                player.getPersistentDataContainer().set(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER, data);
                            }
                        }
                        //player.getPersistentDataContainer().set();
                    }
                });
                slot++;
                if(slot % 9 == 8) {
                    slot += 2;
                }
            }
        }
        @Override
        public WeaponSelectorInv getInv(PersistentDataContainer data) {
            if(data.has(Utils.weaponSelectorIDKey, PersistentDataType.INTEGER)) {
                return new WeaponSelectorInv(data.get(Utils.weaponSelectorIDKey, PersistentDataType.INTEGER));
            }
            return new WeaponSelectorInv(0);
        }
    }
    public static final class ArtifactSelectorInv extends CreeperInv implements DynamicInv {
        private static final ArtifactSelectorInv instance = new ArtifactSelectorInv();
        private ArtifactSelectorInv() {
            super(InventoryType.PLAYER);
        }
        private ArtifactSelectorInv(final int artifact) {
            super(54, "选择法器#" + (artifact + 1));
            setItems(getBorders(), ItemManager.BORDER);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            int slot = 10;
            for(final int artifactSelection : ItemManager.artifactSelections) {
                setItem(slot, ItemManager.artifacts[artifactSelection], event -> {
                    if(event.getWhoClicked() instanceof Player player) {
                        if(event.isLeftClick()) {
                            if(true) { //TODO
                                PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                                data.set(Utils.artifactKeys[artifact], PersistentDataType.INTEGER, artifactSelection);
                                player.getPersistentDataContainer().set(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER, data);
                            }
                        }
                        //player.getPersistentDataContainer().set();
                    }
                });
                slot++;
                if(slot % 9 == 8) {
                    slot += 2;
                }
            }
        }
        @Override
        public ArtifactSelectorInv getInv(PersistentDataContainer data) {
            if(data.has(Utils.artifactSelectorIDKey, PersistentDataType.INTEGER)) {
                return new ArtifactSelectorInv(data.get(Utils.artifactSelectorIDKey, PersistentDataType.INTEGER));
            }
            return new ArtifactSelectorInv(0);
        }
    }
}
