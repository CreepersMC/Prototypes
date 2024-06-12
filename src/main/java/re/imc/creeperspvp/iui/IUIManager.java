package re.imc.creeperspvp.iui;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.FastInvManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import re.imc.creeperspvp.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
    private static final Component onInteraction = Component.text(">>> ", NamedTextColor.WHITE);
    private static final Component buyOnLeftClick = onInteraction.append(Component.text("左键购买", NamedTextColor.YELLOW)).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    private static final Component equipOnLeftClick = onInteraction.append(Component.text("左键装备", NamedTextColor.GREEN)).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    private static final Component upgradeOnRightClick = onInteraction.append(Component.text("右键升级", NamedTextColor.AQUA)).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    public static void init() {
        FastInvManager.register(CreepersPVP.instance);
    }
    public static FastInv getIUI(byte id, UUID uuid, PersistentDataContainer data) {
        return iuis[id] instanceof DynamicInv dynamicInv ? dynamicInv.getInv(uuid, data) : iuis[id];
    }
    private interface DynamicInv {
        FastInv getInv(UUID uuid, PersistentDataContainer data);
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
            super(InventoryType.PLAYER);
        }
        private ArmorSelectorInv(Object obj) {
            super(54, "选择盔甲");
            setItems(getBorders(), ItemManager.BORDER);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            int slot = 10;
            for(final int armorSelection : ArmorManager.selections) {
                ItemStack item = ArmorManager.selectors[armorSelection].clone();
                List<Component> lore = item.getItemMeta().hasLore() ? new ArrayList<>(item.lore()) : new ArrayList<>();
                lore.add(equipOnLeftClick);
                if(ArmorManager.upgrades[armorSelection] != -1) {
                    lore.add(upgradeOnRightClick);
                }
                item.lore(lore);
                setItem(slot, item, event -> {
                    if(event.getWhoClicked() instanceof Player player) {
                        if(event.isLeftClick()) {
                            if(true) { //TODO
                                PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                                data.set(Utils.armorKey, PersistentDataType.INTEGER, armorSelection);
                                player.getPersistentDataContainer().set(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER, data);
                            }
                        }
                    }
                });
                slot++;
                if(slot % 9 == 8) {
                    slot += 2;
                }
            }
        }
        @Override
        public ArmorSelectorInv getInv(UUID uuid, PersistentDataContainer data) {
            return new ArmorSelectorInv(null);
            //return instance;
        }
    }
    public static final class WeaponSelectorInv extends CreeperInv implements DynamicInv {
        private static final WeaponSelectorInv instance = new WeaponSelectorInv();
        private WeaponSelectorInv() {
            super(InventoryType.PLAYER);
        }
        private WeaponSelectorInv(final UUID uuid, final int weapon) {
            super(54, "选择武器#" + (weapon + 1));
            setItems(getBorders(), ItemManager.BORDER);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            final boolean[] weaponsStatus = Utils.fetchPlayerWeaponsStatus(uuid);
            final Object lock = Utils.getPlayerLock(uuid);
            int slot = 10;
            for(final int weaponSelection : WeaponManager.selections) {
                ItemStack item = WeaponManager.weapons[weaponSelection].clone();
                List<Component> lore = item.getItemMeta().hasLore() ? new ArrayList<>(item.lore()) : new ArrayList<>();
                if(weaponsStatus[weaponSelection]) {
                    lore.add(equipOnLeftClick);
                    if(WeaponManager.upgrades[weaponSelection] != -1) {
                        lore.add(upgradeOnRightClick);
                    }
                } else {
                    lore.add(buyOnLeftClick);
                }
                item.lore(lore);
                setItem(slot, item, event -> {
                    if(event.getWhoClicked() instanceof Player player) {
                        if(event.isLeftClick()) {
                            if(weaponsStatus[weaponSelection]) {
                                PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                                data.set(Utils.weaponKeys[weapon], PersistentDataType.INTEGER, weaponSelection);
                                player.getPersistentDataContainer().set(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER, data);
                            } else {
                                synchronized(lock) {
                                    if(!weaponsStatus[weaponSelection] && Utils.fetchPlayerEmeralds(uuid) >= WeaponManager.prices[weaponSelection]) {
                                        Utils.addPlayerEmeralds(uuid, -WeaponManager.prices[weaponSelection]);
                                        weaponsStatus[weaponSelection] = true;
                                        Utils.setPlayerWeaponStatus(uuid, weaponsStatus);
                                        Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new WeaponSelectorInv(uuid, weapon).open(player));
                                    }
                                }
                            }
                        }
                        if(event.isRightClick() && weaponsStatus[weaponSelection] && WeaponManager.upgrades[weaponSelection] != -1) {
                            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new WeaponUpgradeInv(weapon, weaponSelection).open(player));
                        }
                    }
                });
                slot++;
                if(slot % 9 == 8) {
                    slot += 2;
                }
            }
        }
        @Override
        public WeaponSelectorInv getInv(UUID uuid, PersistentDataContainer data) {
            if(data.has(Utils.weaponSelectorIDKey, PersistentDataType.INTEGER)) {
                return new WeaponSelectorInv(uuid, data.get(Utils.weaponSelectorIDKey, PersistentDataType.INTEGER));
            }
            return instance;
        }
    }
    public static final class WeaponUpgradeInv extends CreeperInv {
        private WeaponUpgradeInv(final int weapon, final int weaponID) {
            super(27, "武器升级：" + PlainTextComponentSerializer.plainText().serialize(WeaponManager.weapons[weaponID].displayName()));
            setItems(getBorders(), ItemManager.BORDER);
            setItem(0, ItemManager.BACK);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            setItem(13, WeaponManager.weapons[WeaponManager.upgrades[weaponID]], event -> {
                if(event.getWhoClicked() instanceof Player player) {
                    if(event.isLeftClick()) {
                        if(true) { //TODO
                            PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                            data.set(Utils.weaponKeys[weapon], PersistentDataType.INTEGER, WeaponManager.upgrades[weaponID]);
                            player.getPersistentDataContainer().set(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER, data);
                        }
                    }
                    if(event.isRightClick() && WeaponManager.upgrades[weaponID] != -1) {
                        Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new WeaponUpgradeInv(weapon, weaponID).open(player));
                    }
                }
            });
        }
    }
    public static final class ArtifactSelectorInv extends CreeperInv implements DynamicInv {
        private static final ArtifactSelectorInv instance = new ArtifactSelectorInv();
        private ArtifactSelectorInv() {
            super(InventoryType.PLAYER);
        }
        private ArtifactSelectorInv(final int artifact, final int category) {
            super(54, "选择法器#" + (artifact + 1));
            setItems(getBorders(), ItemManager.BORDER);
            for(int i = 0; i < ArtifactManager.artifactCategorySelectors.length; i++) {
                int finalI = i;
                setItem(i * 2 + 1, ArtifactManager.artifactCategorySelectors[i], i == category ? null : event -> new ArtifactSelectorInv(artifact, finalI).open((Player) event.getWhoClicked()));
            }
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            int slot = 10;
            for(final int artifactSelection : ArtifactManager.selections[category]) {
                ItemStack item = ArtifactManager.artifacts[artifactSelection].clone();
                List<Component> lore = item.getItemMeta().hasLore() ? new ArrayList<>(item.lore()) : new ArrayList<>();
                lore.add(equipOnLeftClick);
                if(ArtifactManager.upgrades[artifactSelection] != -1) {
                    lore.add(upgradeOnRightClick);
                }
                item.lore(lore);
                setItem(slot, item, event -> {
                    if(event.getWhoClicked() instanceof Player player) {
                        if(event.isLeftClick()) {
                            if(true) { //TODO
                                PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                                data.set(Utils.artifactKeys[artifact], PersistentDataType.INTEGER, artifactSelection);
                                player.getPersistentDataContainer().set(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER, data);
                            }
                        }
                        if(event.isRightClick() && ArtifactManager.upgrades[artifactSelection] != -1) {
                            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArtifactUpgradeInv(artifact, artifactSelection).open(player));
                        }
                    }
                });
                slot++;
                if(slot % 9 == 8) {
                    slot += 2;
                }
            }
        }
        @Override
        public ArtifactSelectorInv getInv(UUID uuid, PersistentDataContainer data) {
            if(data.has(Utils.artifactSelectorIDKey, PersistentDataType.INTEGER)) {
                return new ArtifactSelectorInv(data.get(Utils.artifactSelectorIDKey, PersistentDataType.INTEGER), 0);
            }
            return instance;
        }
    }
    public static final class ArtifactUpgradeInv extends CreeperInv {
        private ArtifactUpgradeInv(final int artifact, final int artifactID) {
            super(27, "法器升级：" + PlainTextComponentSerializer.plainText().serialize(ArtifactManager.artifacts[artifactID].displayName()));
            setItems(getBorders(), ItemManager.BORDER);
            setItem(0, ItemManager.BACK);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            setItem(13, ArtifactManager.artifacts[ArtifactManager.upgrades[artifactID]], event -> {
                if(event.getWhoClicked() instanceof Player player) {
                    if(event.isLeftClick()) {
                        if(true) { //TODO
                            PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                            data.set(Utils.artifactKeys[artifact], PersistentDataType.INTEGER, ArtifactManager.upgrades[artifactID]);
                            player.getPersistentDataContainer().set(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER, data);
                        }
                    }
                    if(event.isRightClick() && ArtifactManager.upgrades[artifactID] != -1) {
                        Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArtifactUpgradeInv(artifact, artifactID).open(player));
                    }
                }
            });
        }
    }
}
