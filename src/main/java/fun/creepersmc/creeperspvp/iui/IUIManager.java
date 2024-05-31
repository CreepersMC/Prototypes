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
    public static final byte WEAPON1_SELECTOR = 2;
    public static final byte WEAPON2_SELECTOR = 3;
    public static final byte ARTIFACT1_SELECTOR = 4;
    public static final byte ARTIFACT2_SELECTOR = 5;
    public static final byte ARTIFACT3_SELECTOR = 6;
    private static final FastInv[] iuis = new FastInv[] {
        null,
        ArmorSelectorInv.instance,
        Weapon1SelectorInv.instance,
        Weapon2SelectorInv.instance,
        Artifact1SelectorInv.instance,
        Artifact2SelectorInv.instance,
        Artifact3SelectorInv.instance
    };
    public static void init() {
        FastInvManager.register(CreepersPVP.instance);
    }
    public static FastInv getIUI(byte id, Object data) {
        return iuis[id] instanceof DynamicInv dynamicInv ? dynamicInv.getInv(data) : iuis[id];
    }
    private interface DynamicInv {
        FastInv getInv(Object data);
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
        public FastInv getInv(Object data) {
            return instance;
        }
    }
    public static class Weapon1SelectorInv extends CreeperInv implements DynamicInv {
        private static final Weapon1SelectorInv instance = new Weapon1SelectorInv();
        private Weapon1SelectorInv() {
            super(54, "选择武器#1");
            setItems(getBorders(), ItemManager.BORDER);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            int slot = 10;
            for(final int weaponSelection : ItemManager.weaponSelections) {
                setItem(slot, ItemManager.weapons[weaponSelection], event -> {
                    if(event.getWhoClicked() instanceof Player player) {
                        if(event.isLeftClick()) {
                            if(true) { //TODO
                                PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                                data.set(Utils.weapon1Key, PersistentDataType.INTEGER, weaponSelection);
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
        public FastInv getInv(Object data) {
            return instance;
        }
    }
    public static class Weapon2SelectorInv extends CreeperInv implements DynamicInv {
        private static final Weapon2SelectorInv instance = new Weapon2SelectorInv();
        private Weapon2SelectorInv() {
            super(54, "选择武器#2");
            setItems(getBorders(), ItemManager.BORDER);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            int slot = 10;
            for(final int weaponSelection : ItemManager.weaponSelections) {
                setItem(slot, ItemManager.weapons[weaponSelection], event -> {
                    if(event.getWhoClicked() instanceof Player player) {
                        if(event.isLeftClick()) {
                            if(true) { //TODO
                                PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                                data.set(Utils.weapon2Key, PersistentDataType.INTEGER, weaponSelection);
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
        public FastInv getInv(Object data) {
            return instance;
        }
    }
    public static class Artifact1SelectorInv extends CreeperInv implements DynamicInv {
        private static final Artifact1SelectorInv instance = new Artifact1SelectorInv();
        private Artifact1SelectorInv() {
            super(54, "选择法器#1");
            setItems(getBorders(), ItemManager.BORDER);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            int slot = 10;
            for(final int artifactSelection : ItemManager.artifactSelections) {
                setItem(slot, ItemManager.artifacts[artifactSelection], event -> {
                    if(event.getWhoClicked() instanceof Player player) {
                        if(event.isLeftClick()) {
                            if(true) { //TODO
                                PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                                data.set(Utils.artifact1Key, PersistentDataType.INTEGER, artifactSelection);
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
        public FastInv getInv(Object data) {
            return instance;
        }
    }
    public static class Artifact2SelectorInv extends CreeperInv implements DynamicInv {
        private static final Artifact2SelectorInv instance = new Artifact2SelectorInv();
        private Artifact2SelectorInv() {
            super(54, "选择法器#2");
            setItems(getBorders(), ItemManager.BORDER);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            int slot = 10;
            for(final int artifactSelection : ItemManager.artifactSelections) {
                setItem(slot, ItemManager.artifacts[artifactSelection], event -> {
                    if(event.getWhoClicked() instanceof Player player) {
                        if(event.isLeftClick()) {
                            if(true) { //TODO
                                PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                                data.set(Utils.artifact2Key, PersistentDataType.INTEGER, artifactSelection);
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
        public FastInv getInv(Object data) {
            return instance;
        }
    }
    public static class Artifact3SelectorInv extends CreeperInv implements DynamicInv {
        private static final Artifact3SelectorInv instance = new Artifact3SelectorInv();
        private Artifact3SelectorInv() {
            super(54, "选择法器#3");
            setItems(getBorders(), ItemManager.BORDER);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            int slot = 10;
            for(final int artifactSelection : ItemManager.artifactSelections) {
                setItem(slot, ItemManager.artifacts[artifactSelection], event -> {
                    if(event.getWhoClicked() instanceof Player player) {
                        if(event.isLeftClick()) {
                            if(true) { //TODO
                                PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                                data.set(Utils.artifact3Key, PersistentDataType.INTEGER, artifactSelection);
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
        public FastInv getInv(Object data) {
            return instance;
        }
    }
}
