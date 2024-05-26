package fun.creepersmc.creeperspvp.iui;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.FastInvManager;
import fun.creepersmc.creeperspvp.CreepersPVP;
import fun.creepersmc.creeperspvp.ItemManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.function.Function;
public final class IUIManager {
    public static NamespacedKey idKey;
    public static NamespacedKey dataKey;
    public static final byte ARMOR_SELECTOR = 1;
    public static final byte WEAPON_SELECTOR = 2;
    public static final byte ARTIFACT_SELECTOR = 3;
    private static final FastInv[] iuis = new FastInv[] {
        null,
        ArmorSelectorInv.instance,

    };
    public static void init() {
        FastInvManager.register(CreepersPVP.instance);
        idKey = new NamespacedKey(CreepersPVP.instance, "iui-id");
        dataKey = new NamespacedKey(CreepersPVP.instance, "iui-data");
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
            setItem(8, ItemManager.CLOSE, e -> e.getWhoClicked().closeInventory());
            ItemStack mercenaryArmor = new ItemStack(Material.IRON_CHESTPLATE);
            final ItemMeta mercenaryArmorMeta = mercenaryArmor.getItemMeta();
            mercenaryArmorMeta.displayName(Component.text("雇佣兵盔甲"));
            mercenaryArmorMeta.setAttributeModifiers(null);
            mercenaryArmor.setItemMeta(mercenaryArmorMeta);
            setItem(10, mercenaryArmor, e -> {
                if(e.getWhoClicked() instanceof Player player) {
                    if(e.isLeftClick()) {
                        if(e.isShiftClick()) {

                        }
                    }
                    //player.getPersistentDataContainer().set();
                }
            });
        }
        @Override
        public FastInv getInv(Object data) {
            return instance;
        }
    }
}
