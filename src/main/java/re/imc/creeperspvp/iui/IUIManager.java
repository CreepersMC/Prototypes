package re.imc.creeperspvp.iui;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.FastInvManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import re.imc.creeperspvp.*;
import re.imc.creeperspvp.items.ArmorManager;
import re.imc.creeperspvp.items.ArtifactManager;
import re.imc.creeperspvp.items.ItemManager;
import re.imc.creeperspvp.items.WeaponManager;
import re.imc.creeperspvp.utils.DatabaseUtils;
import re.imc.creeperspvp.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
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
    private static final Component onInteraction = Component.text(">>> ", NamedTextColor.WHITE).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    private static final Component priceTag = Component.text("价格：", NamedTextColor.WHITE).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    private static final Component buyOnLeftClick = onInteraction.append(Component.text("左键购买", NamedTextColor.YELLOW));
    private static final Component equipOnLeftClick = onInteraction.append(Component.text("左键装备", NamedTextColor.GREEN));
    private static final Component upgradeOnRightClick = onInteraction.append(Component.text("右键升级", NamedTextColor.AQUA));
    private static final int[][] upgradeSlots = {{}, {13}, {12, 14}, {11, 13, 15}, {10, 12, 14, 16}, {11, 12, 13, 14, 15}, {10, 11, 12, 13, 14 ,15}, {10, 11, 12, 13, 14, 15, 16}};
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
        private ArmorSelectorInv(final UUID uuid) {
            super(54, "选择盔甲");
            setItems(getBorders(), ItemManager.BORDER);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            final boolean[] armorStatus = DatabaseUtils.fetchPlayerArmorStatus(uuid);
            final Object lock = DatabaseUtils.getPlayerLock(uuid);
            int slot = 10;
            for(final int armorSelection : ArmorManager.selections) {
                ItemStack item = ArmorManager.selectors[armorSelection][armorStatus[armorSelection] ? 1 : 0].clone();
                List<Component> lore = item.getItemMeta().hasLore() ? new ArrayList<>(item.lore()) : new ArrayList<>();
                if(armorStatus[armorSelection]) {
                    lore.add(equipOnLeftClick);
                    if(ArmorManager.upgrades[armorSelection].length > 0) {
                        lore.add(upgradeOnRightClick);
                    }
                } else {
                    lore.add(priceTag.append(Component.text(ArmorManager.prices[armorSelection])));
                    lore.add(buyOnLeftClick);
                }
                item.lore(lore);
                setItem(slot, item, event -> {
                    if(event.getWhoClicked() instanceof Player player) {
                        if(event.isLeftClick()) {
                            if(armorStatus[armorSelection]) {
                                PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                                data.set(Utils.armorKey, PersistentDataType.INTEGER, armorSelection);
                                player.getPersistentDataContainer().set(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER, data);
                            } else {
                                ItemStack itemPreview = ArmorManager.selectors[armorSelection][1].clone();
                                itemPreview.lore(lore);
                                Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ConfirmPurchaseDialogue(itemPreview, () -> {
                                    synchronized(lock) {
                                        if(!armorStatus[armorSelection] && DatabaseUtils.fetchPlayerEmeralds(uuid) >= ArmorManager.prices[armorSelection]) {
                                            DatabaseUtils.addPlayerEmeralds(uuid, -ArmorManager.prices[armorSelection]);
                                            armorStatus[armorSelection] = true;
                                            DatabaseUtils.setPlayerArmorStatus(uuid, armorStatus);
                                            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArmorSelectorInv(uuid).open(player));
                                        }
                                    }
                                }, () -> open(player)).open(player));
                            }
                        }
                        if(event.isRightClick() && armorStatus[armorSelection] && ArmorManager.upgrades[armorSelection].length > 0) {
                            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArmorUpgradeInv(uuid, armorSelection, armorStatus).open(player));
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
            return new ArmorSelectorInv(uuid);
        }
    }
    private static final class ArmorUpgradeInv extends CreeperInv {
        private ArmorUpgradeInv(final UUID uuid, final int armorID, final boolean[] armorStatus) {
            super(27, "盔甲升级：" + PlainTextComponentSerializer.plainText().serialize(ArmorManager.selectors[armorID][1].displayName()));
            setItems(getBorders(), ItemManager.BORDER);
            setItem(0, ItemManager.BACK, event -> new ArmorSelectorInv(uuid).open(Bukkit.getPlayer(uuid)));
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            final Object lock = DatabaseUtils.getPlayerLock(uuid);
            for(int i = 0; i < ArmorManager.upgrades[armorID].length; i++) {
                final int armorUpgrade = ArmorManager.upgrades[armorID][i];
                ItemStack item = ArmorManager.selectors[armorUpgrade][armorStatus[armorUpgrade] ? 1 : 0].clone();
                List<Component> lore = item.getItemMeta().hasLore() ? new ArrayList<>(item.lore()) : new ArrayList<>();
                if(armorStatus[armorUpgrade]) {
                    lore.add(equipOnLeftClick);
                    if(ArmorManager.upgrades[armorUpgrade].length > 0) {
                        lore.add(upgradeOnRightClick);
                    }
                } else {
                    lore.add(priceTag.append(Component.text(ArmorManager.prices[armorUpgrade])));
                    lore.add(buyOnLeftClick);
                }
                item.lore(lore);
                setItem(upgradeSlots[ArmorManager.upgrades[armorID].length][i], item, event -> {
                    if(event.getWhoClicked() instanceof Player player) {
                        if(event.isLeftClick()) {
                            if(armorStatus[armorUpgrade]) {
                                PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                                data.set(Utils.armorKey, PersistentDataType.INTEGER, armorUpgrade);
                                player.getPersistentDataContainer().set(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER, data);
                            } else {
                                ItemStack itemPreview = ArmorManager.selectors[armorUpgrade][1].clone();
                                itemPreview.lore(lore);
                                Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ConfirmPurchaseDialogue(itemPreview, () -> {
                                    synchronized(lock) {
                                        if(!armorStatus[armorUpgrade] && DatabaseUtils.fetchPlayerEmeralds(uuid) >= ArmorManager.prices[armorUpgrade]) {
                                            DatabaseUtils.addPlayerEmeralds(uuid, -ArmorManager.prices[armorUpgrade]);
                                            armorStatus[armorUpgrade] = true;
                                            DatabaseUtils.setPlayerArmorStatus(uuid, armorStatus);
                                            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArmorUpgradeInv(uuid, armorID, armorStatus).open(player));
                                        }
                                    }
                                }, () -> open(player)).open(player));
                            }
                        }
                        if(event.isRightClick() && armorStatus[armorUpgrade] && ArmorManager.upgrades[armorUpgrade].length > 0) {
                            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArmorUpgradeInv(uuid, armorUpgrade, armorStatus).open(player));
                        }
                    }
                });
            }
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
            final boolean[] weaponStatus = DatabaseUtils.fetchPlayerWeaponStatus(uuid);
            final Object lock = DatabaseUtils.getPlayerLock(uuid);
            int slot = 10;
            for(final int weaponSelection : WeaponManager.selections) {
                ItemStack[] items = new ItemStack[] {WeaponManager.weapons[weaponSelection][0].clone(), WeaponManager.weapons[weaponSelection][1].clone()};
                for(final ItemStack item : items) {
                    List<Component> lore = item.getItemMeta().hasLore() ? new ArrayList<>(item.lore()) : new ArrayList<>();
                    if(weaponStatus[weaponSelection]) {
                        lore.add(equipOnLeftClick);
                        if(WeaponManager.upgrades[weaponSelection].length > 0) {
                            lore.add(upgradeOnRightClick);
                        }
                    } else {
                        lore.add(priceTag.append(Component.text(WeaponManager.prices[weaponSelection])));
                        lore.add(buyOnLeftClick);
                    }
                    item.lore(lore);
                }
                setItem(slot, items[weaponStatus[weaponSelection] ? 1 : 0], event -> {
                    if(event.getWhoClicked() instanceof Player player) {
                        if(event.isLeftClick()) {
                            if(weaponStatus[weaponSelection]) {
                                PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                                data.set(Utils.weaponKeys[weapon], PersistentDataType.INTEGER, weaponSelection);
                                player.getPersistentDataContainer().set(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER, data);
                            } else {
                                Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ConfirmPurchaseDialogue(items[1], () -> {
                                    synchronized(lock) {
                                        if(!weaponStatus[weaponSelection] && DatabaseUtils.fetchPlayerEmeralds(uuid) >= WeaponManager.prices[weaponSelection]) {
                                            DatabaseUtils.addPlayerEmeralds(uuid, -WeaponManager.prices[weaponSelection]);
                                            weaponStatus[weaponSelection] = true;
                                            DatabaseUtils.setPlayerWeaponStatus(uuid, weaponStatus);
                                            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new WeaponSelectorInv(uuid, weapon).open(player));
                                        }
                                    }
                                }, () -> open(player)).open(player));
                            }
                        }
                        if(event.isRightClick() && weaponStatus[weaponSelection] && WeaponManager.upgrades[weaponSelection].length > 0) {
                            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new WeaponUpgradeInv(uuid, weapon, weaponSelection, weaponStatus).open(player));
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
    private static final class WeaponUpgradeInv extends CreeperInv {
        private WeaponUpgradeInv(final UUID uuid, final int weapon, final int weaponID, final boolean[] weaponStatus) {
            super(27, "武器升级：" + PlainTextComponentSerializer.plainText().serialize(WeaponManager.weapons[weaponID][0].displayName()));
            setItems(getBorders(), ItemManager.BORDER);
            setItem(0, ItemManager.BACK, event -> new WeaponSelectorInv(uuid, weapon).open(Bukkit.getPlayer(uuid)));
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            final Object lock = DatabaseUtils.getPlayerLock(uuid);
            for(int i = 0; i < WeaponManager.upgrades[weaponID].length; i++) {
                final int weaponUpgrade = WeaponManager.upgrades[weaponID][i];
                ItemStack[] items = new ItemStack[] {WeaponManager.weapons[weaponUpgrade][0].clone(), WeaponManager.weapons[weaponUpgrade][1].clone()};
                for(final ItemStack item : items) {
                    List<Component> lore = item.getItemMeta().hasLore() ? new ArrayList<>(item.lore()) : new ArrayList<>();
                    if(weaponStatus[weaponUpgrade]) {
                        lore.add(equipOnLeftClick);
                        if(WeaponManager.upgrades[weaponUpgrade].length > 0) {
                            lore.add(upgradeOnRightClick);
                        }
                    } else {
                        lore.add(priceTag.append(Component.text(WeaponManager.prices[weaponUpgrade])));
                        lore.add(buyOnLeftClick);
                    }
                    item.lore(lore);
                }
                setItem(upgradeSlots[WeaponManager.upgrades[weaponID].length][i], items[weaponStatus[weaponUpgrade] ? 1 : 0], event -> {
                    if(event.getWhoClicked() instanceof Player player) {
                        if(event.isLeftClick()) {
                            if(weaponStatus[weaponUpgrade]) {
                                PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                                data.set(Utils.weaponKeys[weapon], PersistentDataType.INTEGER, weaponUpgrade);
                                player.getPersistentDataContainer().set(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER, data);
                            } else {
                                Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ConfirmPurchaseDialogue(items[1], () -> {
                                    synchronized(lock) {
                                        if(!weaponStatus[weaponUpgrade] && DatabaseUtils.fetchPlayerEmeralds(uuid) >= WeaponManager.prices[weaponUpgrade]) {
                                            DatabaseUtils.addPlayerEmeralds(uuid, -WeaponManager.prices[weaponUpgrade]);
                                            weaponStatus[weaponUpgrade] = true;
                                            DatabaseUtils.setPlayerWeaponStatus(uuid, weaponStatus);
                                            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new WeaponUpgradeInv(uuid, weapon, weaponID, weaponStatus).open(player));
                                        }
                                    }
                                }, () -> open(player)).open(player));
                            }
                        }
                        if(event.isRightClick() && weaponStatus[weaponUpgrade] && WeaponManager.upgrades[weaponUpgrade].length > 0) {
                            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new WeaponUpgradeInv(uuid, weapon, weaponUpgrade, weaponStatus).open(player));
                        }
                    }
                });
            }
        }
    }
    public static final class ArtifactSelectorInv extends CreeperInv implements DynamicInv {
        private static final ArtifactSelectorInv instance = new ArtifactSelectorInv();
        private ArtifactSelectorInv() {
            super(InventoryType.PLAYER);
        }
        private ArtifactSelectorInv(final UUID uuid, final int artifact, final int category) {
            super(54, "选择法器#" + (artifact + 1));
            setItems(getBorders(), ItemManager.BORDER);
            for(int i = 0; i < ArtifactManager.artifactCategorySelectors.length; i++) {
                int finalI = i;
                setItem(i * 2 + 1, ArtifactManager.artifactCategorySelectors[i], i == category ? null : event -> new ArtifactSelectorInv(uuid, artifact, finalI).open((Player) event.getWhoClicked()));
            }
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            int slot = 10;
            final boolean[] artifactStatus = DatabaseUtils.fetchPlayerArtifactStatus(uuid);
            final Object lock = DatabaseUtils.getPlayerLock(uuid);
            for(final int artifactSelection : ArtifactManager.selections[category]) {
                ItemStack[] items = {ArtifactManager.artifacts[artifactSelection][0].clone(), ArtifactManager.artifacts[artifactSelection][1].clone()};
                for(final ItemStack item : items) {
                    List<Component> lore = item.getItemMeta().hasLore() ? new ArrayList<>(item.lore()) : new ArrayList<>();
                    if(artifactStatus[artifactSelection]) {
                        lore.add(equipOnLeftClick);
                        if(ArtifactManager.upgrades[artifactSelection].length > 0) {
                            lore.add(upgradeOnRightClick);
                        }
                    } else {
                        lore.add(priceTag.append(Component.text(ArtifactManager.prices[artifactSelection])));
                        lore.add(buyOnLeftClick);
                    }
                    item.lore(lore);
                }
                setItem(slot, items[artifactStatus[artifactSelection] ? 1 : 0], event -> {
                    if(event.getWhoClicked() instanceof Player player) {
                        if(event.isLeftClick()) {
                            if(artifactStatus[artifactSelection]) {
                                PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                                data.set(Utils.artifactKeys[artifact], PersistentDataType.INTEGER, artifactSelection);
                                player.getPersistentDataContainer().set(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER, data);
                            } else {
                                Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ConfirmPurchaseDialogue(items[1], () -> {
                                    synchronized(lock) {
                                        if(!artifactStatus[artifactSelection] && DatabaseUtils.fetchPlayerEmeralds(uuid) >= ArtifactManager.prices[artifactSelection]) {
                                            DatabaseUtils.addPlayerEmeralds(uuid, -ArtifactManager.prices[artifactSelection]);
                                            artifactStatus[artifactSelection] = true;
                                            DatabaseUtils.setPlayerArtifactStatus(uuid, artifactStatus);
                                            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArtifactSelectorInv(uuid, artifact, category).open(player));
                                        }
                                    }
                                }, () -> open(player)).open(player));
                            }
                        }
                        if(event.isRightClick() && artifactStatus[artifactSelection] && ArtifactManager.upgrades[artifactSelection].length > 0) {
                            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArtifactUpgradeInv(uuid, artifact, artifactSelection, category, artifactStatus).open(player));
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
                return new ArtifactSelectorInv(uuid, data.get(Utils.artifactSelectorIDKey, PersistentDataType.INTEGER), 0);
            }
            return instance;
        }
    }
    private static final class ArtifactUpgradeInv extends CreeperInv {
        private ArtifactUpgradeInv(final UUID uuid, final int artifact, final int artifactID, final int category, final boolean[] artifactStatus) {
            super(27, "法器升级：" + PlainTextComponentSerializer.plainText().serialize(ArtifactManager.artifacts[artifactID][0].displayName()));
            setItems(getBorders(), ItemManager.BORDER);
            setItem(0, ItemManager.BACK, event -> new ArtifactSelectorInv(uuid, artifact, category).open(Bukkit.getPlayer(uuid)));
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            final Object lock = DatabaseUtils.getPlayerLock(uuid);
            for(int i = 0; i < ArtifactManager.upgrades[artifactID].length; i++) {
                final int artifactUpgrade = ArtifactManager.upgrades[artifactID][i];
                ItemStack[] items = {ArtifactManager.artifacts[artifactUpgrade][0].clone(), ArtifactManager.artifacts[artifactUpgrade][1].clone()};
                for(final ItemStack item : items) {
                    List<Component> lore = item.getItemMeta().hasLore() ? new ArrayList<>(item.lore()) : new ArrayList<>();
                    if(artifactStatus[artifactUpgrade]) {
                        lore.add(equipOnLeftClick);
                        if(ArtifactManager.upgrades[artifactUpgrade].length > 0) {
                            lore.add(upgradeOnRightClick);
                        }
                    } else {
                        lore.add(priceTag.append(Component.text(ArtifactManager.prices[artifactUpgrade])));
                        lore.add(buyOnLeftClick);
                    }
                    item.lore(lore);
                }
                setItem(upgradeSlots[ArtifactManager.upgrades[artifactID].length][i], items[artifactStatus[artifactUpgrade] ? 1 : 0], event -> {
                    if(event.getWhoClicked() instanceof Player player) {
                        if(event.isLeftClick()) {
                            if(artifactStatus[artifactUpgrade]) {
                                PersistentDataContainer data = player.getPersistentDataContainer().get(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER);
                                data.set(Utils.artifactKeys[artifact], PersistentDataType.INTEGER, artifactUpgrade);
                                player.getPersistentDataContainer().set(Utils.playerDataKey, PersistentDataType.TAG_CONTAINER, data);
                            } else {
                                Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ConfirmPurchaseDialogue(items[1], () -> {
                                    synchronized(lock) {
                                        if(!artifactStatus[artifactUpgrade] && DatabaseUtils.fetchPlayerEmeralds(uuid) >= ArtifactManager.prices[artifactUpgrade]) {
                                            DatabaseUtils.addPlayerEmeralds(uuid, -ArtifactManager.prices[artifactUpgrade]);
                                            artifactStatus[artifactUpgrade] = true;
                                            DatabaseUtils.setPlayerArtifactStatus(uuid, artifactStatus);
                                            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArtifactUpgradeInv(uuid, artifact, artifactID, category, artifactStatus).open(player));
                                        }
                                    }
                                }, () -> open(player)).open(player));
                            }
                        }
                        if(event.isRightClick() && artifactStatus[artifactUpgrade] && ArtifactManager.upgrades[artifactUpgrade].length > 0) {
                            Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArtifactUpgradeInv(uuid, artifact, artifactUpgrade, category, artifactStatus).open(player));
                        }
                    }
                });
            }
        }
    }
    private static final class ConfirmPurchaseDialogue extends CreeperInv {
        private ConfirmPurchaseDialogue(ItemStack item, Runnable confirm, Runnable cancel) {
            super(InventoryType.HOPPER, "确认购买？");
            setItem(2, item);
            setItem(0, ItemManager.CONFIRM, event -> confirm.run());
            setItem(4, ItemManager.CANCEL, event -> cancel.run());
        }
    }
}
