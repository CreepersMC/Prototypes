package re.imc.creeperspvp.iui;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.FastInvManager;
import fr.mrmicky.fastinv.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
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
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
public final class IUIManager {
    public static final byte TEAM_SELECTION = 0;
    public static final byte DEPLOY = 1;
    public static final byte ARMOR_SELECTOR = 2;
    public static final byte WEAPON_SELECTOR = 3;
    public static final byte ARTIFACT_SELECTOR = 4;
    public static final byte PROFILE = 5;
    public static final byte SERVERS = 6;
    private static final FastInv[] iuis = new FastInv[] {
        TeamSelectionInv.instance,
        null,
        ArmorSelectorInv.instance,
        WeaponSelectorInv.instance,
        ArtifactSelectorInv.instance,
        ProfileInv.instance,
        ServersInv.instance
    };
    private static final Component onInteraction = Component.text(">>> ", NamedTextColor.WHITE).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    private static final Component priceTag = Component.text("价格：", NamedTextColor.WHITE).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    private static final Component joinOnClick = onInteraction.append(Component.text("点击加入", NamedTextColor.GREEN));
    private static final Component teamFull = Component.text("队伍已满", NamedTextColor.RED);
    private static final Component buyOnLeftClick = onInteraction.append(Component.text("左键购买", NamedTextColor.YELLOW));
    private static final Component equipOnLeftClick = onInteraction.append(Component.text("左键装备", NamedTextColor.GREEN));
    private static final Component upgradeOnRightClick = onInteraction.append(Component.text("右键升级", NamedTextColor.AQUA));
    private static final Component unlockOnClick = onInteraction.append(Component.text("点击解锁", NamedTextColor.YELLOW));
    private static final Component upgradeOnClick = onInteraction.append(Component.text("点击升级", NamedTextColor.GOLD));
    private static final Component maxLevelReached = Component.text("已到达最高等级", NamedTextColor.RED);
    private static final int[][] upgradeSlots = {{}, {13}, {12, 14}, {11, 13, 15}, {10, 12, 14, 16}, {11, 12, 13, 14, 15}, {10, 11, 12, 14, 15, 16}, {10, 11, 12, 13, 14, 15, 16}};
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
        protected void onClick(InventoryClickEvent event) {
            event.setCancelled(true);
        }
    }
    public static final class TeamSelectionInv extends CreeperInv {
        private static final TeamSelectionInv instance = new TeamSelectionInv();
        private TeamSelectionInv() {
            super(27, "选择队伍");
            setItems(getBorders(), ItemManager.BORDER);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            refreshItems();
        }
        private void refreshItems() {
            setItem(12, getItemStack(0));
            setItem(14, getItemStack(1));
        }
        private static ItemStack getItemStack(int team) {
            return ItemBuilder.copyOf(Utils.teamFlags[team]).meta(meta -> {
                final List<Component> lore = meta.hasLore() ? new ArrayList<>(meta.lore()) : new ArrayList<>();
                for(final String entry : Utils.teams[team].getEntries()) {
                    lore.add(Component.text(entry));
                }
                //lore.add(joinOnClick);
                meta.lore(lore);
            }).build();
        }
        @Override
        protected void onClick(InventoryClickEvent event) {
            super.onClick(event);
            switch(event.getSlot()) {
                case 12 -> {
                    if(Utils.teams[0].getSize() < Math.ceil(Bukkit.getOnlinePlayers().size() * 0.5f)) {
                        Utils.teams[0].addEntity(event.getWhoClicked());
                        if(event.getWhoClicked() instanceof final Player player) {
                            Utils.updateNames(player);
                        }
                        refreshItems();
                    }
                }
                case 14 -> {
                    if(Utils.teams[1].getSize() < Math.ceil(Bukkit.getOnlinePlayers().size() * 0.5f)) {
                        Utils.teams[1].addEntity(event.getWhoClicked());
                        if(event.getWhoClicked() instanceof final Player player) {
                            Utils.updateNames(player);
                        }
                        refreshItems();
                    }
                }
            }
        }
    }
    public static final class ArmorSelectorInv extends CreeperInv implements DynamicInv {
        private static final ArmorSelectorInv instance = new ArmorSelectorInv();
        private ArmorSelectorInv() {
            super(InventoryType.PLAYER);
        }
        private ArmorSelectorInv(final UUID uuid) {
            super(54, "选择盔甲");
            setItems(getBorders(), ItemManager.BORDER);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            setItem(49, ItemManager.ARMOR_SELECTOR);
            final boolean[] armorStatus = DatabaseUtils.fetchPlayerArmorStatus(uuid);
            final Object lock = DatabaseUtils.getPlayerLock(uuid);
            int slot = 10;
            for(final short armorSelection : ArmorManager.selections) {
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
                                synchronized(lock) {
                                    DatabaseUtils.setPlayerItemSettings(uuid, DatabaseUtils.fetchPlayerItemSettings(uuid).withArmorSelection(armorSelection));
                                }
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
            super(27, "盔甲升级：" + LegacyComponentSerializer.legacySection().serialize(ArmorManager.selectors[armorID][1].displayName()));
            setItems(getBorders(), ItemManager.BORDER);
            setItem(0, ItemManager.BACK, event -> new ArmorSelectorInv(uuid).open(Bukkit.getPlayer(uuid)));
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            setItem(22, ItemManager.ARMOR_SELECTOR);
            final Object lock = DatabaseUtils.getPlayerLock(uuid);
            for(int i = 0; i < ArmorManager.upgrades[armorID].length; i++) {
                final short armorUpgrade = ArmorManager.upgrades[armorID][i];
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
                                synchronized(lock) {
                                    DatabaseUtils.setPlayerItemSettings(uuid, DatabaseUtils.fetchPlayerItemSettings(uuid).withArmorSelection(armorUpgrade));
                                }
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
            super(54, "选择武器");
            setItems(getBorders(), ItemManager.BORDER);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            setItem(48, ItemBuilder.copyOf(ItemManager.WEAPON_SELECTORS[0]).edit(item -> item.editMeta(meta -> meta.setEnchantmentGlintOverride(weapon == 0))).build(), event -> {
                if(weapon != 0 && event.getWhoClicked() instanceof Player player) {
                    Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new WeaponSelectorInv(uuid, 0).open(player));
                }
            });
            setItem(50, ItemBuilder.copyOf(ItemManager.WEAPON_SELECTORS[1]).edit(item -> item.editMeta(meta -> meta.setEnchantmentGlintOverride(weapon == 1))).build(), event -> {
                if(weapon != 1 && event.getWhoClicked() instanceof Player player) {
                    Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new WeaponSelectorInv(uuid, 1).open(player));
                }
            });
            final boolean[] weaponStatus = DatabaseUtils.fetchPlayerWeaponStatus(uuid);
            final Object lock = DatabaseUtils.getPlayerLock(uuid);
            int slot = 10;
            for(final short weaponSelection : WeaponManager.selections) {
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
                                synchronized(lock) {
                                    DatabaseUtils.setPlayerItemSettings(uuid, DatabaseUtils.fetchPlayerItemSettings(uuid).withWeaponSelection(weapon, weaponSelection));
                                }
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
            return new WeaponSelectorInv(uuid, 0);
        }
    }
    private static final class WeaponUpgradeInv extends CreeperInv {
        private WeaponUpgradeInv(final UUID uuid, final int weapon, final int weaponID, final boolean[] weaponStatus) {
            super(27, "武器升级：" + LegacyComponentSerializer.legacySection().serialize(WeaponManager.weapons[weaponID][0].displayName()));
            setItems(getBorders(), ItemManager.BORDER);
            setItem(0, ItemManager.BACK, event -> new WeaponSelectorInv(uuid, weapon).open(Bukkit.getPlayer(uuid)));
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            setItem(21, ItemBuilder.copyOf(ItemManager.WEAPON_SELECTORS[0]).edit(item -> item.editMeta(meta -> meta.setEnchantmentGlintOverride(weapon == 0))).build(), event -> {
                if(weapon != 0 && event.getWhoClicked() instanceof Player player) {
                    Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new WeaponUpgradeInv(uuid, 0, weaponID, weaponStatus).open(player));
                }
            });
            setItem(23, ItemBuilder.copyOf(ItemManager.WEAPON_SELECTORS[1]).edit(item -> item.editMeta(meta -> meta.setEnchantmentGlintOverride(weapon == 1))).build(), event -> {
                if(weapon != 1 && event.getWhoClicked() instanceof Player player) {
                    Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new WeaponUpgradeInv(uuid, 1, weaponID, weaponStatus).open(player));
                }
            });
            final Object lock = DatabaseUtils.getPlayerLock(uuid);
            for(int i = 0; i < WeaponManager.upgrades[weaponID].length; i++) {
                final short weaponUpgrade = WeaponManager.upgrades[weaponID][i];
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
                                synchronized(lock) {
                                    DatabaseUtils.setPlayerItemSettings(uuid, DatabaseUtils.fetchPlayerItemSettings(uuid).withWeaponSelection(weapon, weaponUpgrade));
                                }
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
            setItem(47, ItemBuilder.copyOf(ItemManager.ARTIFACT_SELECTORS[0]).edit(item -> item.editMeta(meta -> meta.setEnchantmentGlintOverride(artifact == 0))).build(), event -> {
                if(artifact != 0 && event.getWhoClicked() instanceof Player player) {
                    Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArtifactSelectorInv(uuid, 0, category).open(player));
                }
            });
            setItem(48, ItemBuilder.copyOf(ItemManager.ARTIFACT_SELECTORS[1]).edit(item -> item.editMeta(meta -> meta.setEnchantmentGlintOverride(artifact == 1))).build(), event -> {
                if(artifact != 1 && event.getWhoClicked() instanceof Player player) {
                    Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArtifactSelectorInv(uuid, 1, category).open(player));
                }
            });
            setItem(50, ItemBuilder.copyOf(ItemManager.ARTIFACT_SELECTORS[2]).edit(item -> item.editMeta(meta -> meta.setEnchantmentGlintOverride(artifact == 2))).build(), event -> {
                if(artifact != 2 && event.getWhoClicked() instanceof Player player) {
                    Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArtifactSelectorInv(uuid, 2, category).open(player));
                }
            });
            setItem(51, ItemBuilder.copyOf(ItemManager.ARTIFACT_SELECTORS[3]).edit(item -> item.editMeta(meta -> meta.setEnchantmentGlintOverride(artifact == 3))).build(), event -> {
                if(artifact != 3 && event.getWhoClicked() instanceof Player player) {
                    Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArtifactSelectorInv(uuid, 3, category).open(player));
                }
            });
            int slot = 10;
            final boolean[] artifactStatus = DatabaseUtils.fetchPlayerArtifactStatus(uuid);
            final Object lock = DatabaseUtils.getPlayerLock(uuid);
            for(final short artifactSelection : ArtifactManager.selections[category]) {
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
                                synchronized(lock) {
                                    DatabaseUtils.setPlayerItemSettings(uuid, DatabaseUtils.fetchPlayerItemSettings(uuid).withArtifactSelection(artifact, artifactSelection));
                                }
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
            return new ArtifactSelectorInv(uuid, 0, 0);
        }
    }
    private static final class ArtifactUpgradeInv extends CreeperInv {
        private ArtifactUpgradeInv(final UUID uuid, final int artifact, final int artifactID, final int category, final boolean[] artifactStatus) {
            super(27, "法器升级：" + LegacyComponentSerializer.legacySection().serialize(ArtifactManager.artifacts[artifactID][0].displayName()));
            setItems(getBorders(), ItemManager.BORDER);
            setItem(0, ItemManager.BACK, event -> new ArtifactSelectorInv(uuid, artifact, category).open(Bukkit.getPlayer(uuid)));
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            setItem(20, ItemBuilder.copyOf(ItemManager.ARTIFACT_SELECTORS[0]).edit(item -> item.editMeta(meta -> meta.setEnchantmentGlintOverride(artifact == 0))).build(), event -> {
                if(artifact != 0 && event.getWhoClicked() instanceof Player player) {
                    Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArtifactUpgradeInv(uuid, 0, artifactID, category, artifactStatus).open(player));
                }
            });
            setItem(21, ItemBuilder.copyOf(ItemManager.ARTIFACT_SELECTORS[1]).edit(item -> item.editMeta(meta -> meta.setEnchantmentGlintOverride(artifact == 1))).build(), event -> {
                if(artifact != 1 && event.getWhoClicked() instanceof Player player) {
                    Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArtifactUpgradeInv(uuid, 1, artifactID, category, artifactStatus).open(player));
                }
            });
            setItem(23, ItemBuilder.copyOf(ItemManager.ARTIFACT_SELECTORS[2]).edit(item -> item.editMeta(meta -> meta.setEnchantmentGlintOverride(artifact == 2))).build(), event -> {
                if(artifact != 2 && event.getWhoClicked() instanceof Player player) {
                    Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArtifactUpgradeInv(uuid, 2, artifactID, category, artifactStatus).open(player));
                }
            });
            setItem(24, ItemBuilder.copyOf(ItemManager.ARTIFACT_SELECTORS[3]).edit(item -> item.editMeta(meta -> meta.setEnchantmentGlintOverride(artifact == 3))).build(), event -> {
                if(artifact != 3 && event.getWhoClicked() instanceof Player player) {
                    Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> new ArtifactUpgradeInv(uuid, 3, artifactID, category, artifactStatus).open(player));
                }
            });
            final Object lock = DatabaseUtils.getPlayerLock(uuid);
            for(int i = 0; i < ArtifactManager.upgrades[artifactID].length; i++) {
                final short artifactUpgrade = ArtifactManager.upgrades[artifactID][i];
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
                                synchronized(lock) {
                                    DatabaseUtils.setPlayerItemSettings(uuid, DatabaseUtils.fetchPlayerItemSettings(uuid).withArtifactSelection(artifact, artifactUpgrade));
                                }
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
    private static final class ProfileInv extends CreeperInv {
        private static final ProfileInv instance = new ProfileInv();
        private ProfileInv() {
            super(27, "个人主页");
            setItems(getBorders(), ItemManager.BORDER);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            setItem(12, ItemManager.ATTRIBUTE_UPGRADES, event -> {
                if(event.getWhoClicked() instanceof Player player) {
                    new AttributeUpgradesInv(player.getUniqueId()).open(player);
                }
            });
            setItem(14, ItemManager.SETTINGS, event -> {
                if(event.getWhoClicked() instanceof Player player) {
                    new SettingsInv(player.getUniqueId()).open(player);
                }
            });
        }
    }
    private static final class AttributeUpgradesInv extends CreeperInv implements DynamicInv {
        private final UUID uuid;
        private final DatabaseUtils.AttributeUpgrades upgrades;
        private AttributeUpgradesInv(UUID uuid) {
            super(54, "天赋");
            this.uuid = uuid;
            setItems(getBorders(), ItemManager.BORDER);
            setItem(0, ItemManager.BACK, event -> ProfileInv.instance.open(Bukkit.getPlayer(uuid)));
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            upgrades = DatabaseUtils.getPlayerAttributeUpgrades(uuid);
            setItem(22, getItemStack(DatabaseUtils.AttributeUpgrades.HEALTH_BONUS));
        }
        @Override
        protected void onClick(InventoryClickEvent event) {
            super.onClick(event);
            switch(event.getSlot()) {
                case 22 -> onClick(22, DatabaseUtils.AttributeUpgrades.HEALTH_BONUS);
            }
        }
        @Override
        public FastInv getInv(UUID uuid, PersistentDataContainer data) {
            return new AttributeUpgradesInv(uuid);
        }
        private void onClick(int slot, byte upgrade) {
            if(upgrades.getData(upgrade) < DatabaseUtils.AttributeUpgrades.prices[upgrade].length && DatabaseUtils.fetchPlayerEmeralds(uuid) >= DatabaseUtils.AttributeUpgrades.prices[upgrade][upgrades.getData(upgrade)]) {
                DatabaseUtils.addPlayerEmeralds(uuid, -DatabaseUtils.AttributeUpgrades.prices[upgrade][upgrades.getData(upgrade)]);
                DatabaseUtils.setPlayerAttributeUpgrades(uuid, upgrades.withIncrementedData(upgrade));
                setItem(slot, getItemStack(DatabaseUtils.AttributeUpgrades.HEALTH_BONUS));
            }
        }
        private ItemStack getItemStack(byte upgrade) {
            return ItemBuilder.copyOf(upgrades.getData(upgrade) == 0 ? ItemManager.ATTRIBUTE_UPGRADE_ITEMS[upgrade].withType(Material.GRAY_DYE) : ItemManager.ATTRIBUTE_UPGRADE_ITEMS[upgrade].asQuantity(upgrades.getData(upgrade))).meta(meta -> {
                final List<Component> lore = meta.lore();
                if(upgrades.getData(upgrade) == 0) {
                    lore.add(priceTag.append(Component.text(DatabaseUtils.AttributeUpgrades.prices[upgrade][upgrades.getData(upgrade)])));
                    lore.add(unlockOnClick);
                } else if(upgrades.getData(upgrade) == DatabaseUtils.AttributeUpgrades.prices[upgrade].length) {
                    lore.add(maxLevelReached);
                } else {
                    lore.add(priceTag.append(Component.text(DatabaseUtils.AttributeUpgrades.prices[upgrade][upgrades.getData(upgrade)])));
                    lore.add(upgradeOnClick);
                }
                meta.lore(lore);
            }).build();
        }
    }
    private static final class SettingsInv extends CreeperInv {
        private final UUID uuid;
        private final DatabaseUtils.MiscSettings miscSettings;
        private SettingsInv(UUID uuid) {
            super(54, "设置");
            this.uuid = uuid;
            setItems(getBorders(), ItemManager.BORDER);
            setItem(0, ItemManager.BACK, event -> ProfileInv.instance.open(Bukkit.getPlayer(uuid)));
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            miscSettings = DatabaseUtils.getPlayerMiscSettings(uuid);
            setItem(10, ItemManager.SHOW_GUIDEBOOK);
            setItem(12, ItemManager.DEPLOY_COOLDOWN);
            setItem(14, ItemManager.RANGED_ATTACK_INDICATOR);
            setItem(19, miscSettings.shouldShowGuideBook() ? ItemManager.ENABLED : ItemManager.DISABLED);
            setItem(21, miscSettings.hasDeployCooldown() ? ItemManager.ENABLED : ItemManager.DISABLED);
            setItem(23, miscSettings.getRangedAttackIndicator().isDefault() ? ItemManager.DEFAULT : miscSettings.getRangedAttackIndicator().isTrue() ? ItemManager.ENABLED : ItemManager.DISABLED);
            setItem(16, ItemManager.ITEM_SETTING);
            setItem(25, ItemManager.SUB_SETTING, event -> {
                if(event.getWhoClicked() instanceof final Player player) {
                    new ItemSettingsInv(uuid).open(player);
                }
            });
        }
        @Override
        protected void onClick(InventoryClickEvent event) {
            super.onClick(event);
            switch(event.getSlot()) {
                case 10, 19 -> {
                    DatabaseUtils.setPlayerMiscSettings(uuid, miscSettings.withShowGuideBook(!miscSettings.shouldShowGuideBook()));
                    setItem(19, miscSettings.shouldShowGuideBook() ? ItemManager.ENABLED : ItemManager.DISABLED);
                }
                case 12, 21 -> {
                    DatabaseUtils.setPlayerMiscSettings(uuid, miscSettings.withDeployCooldown(!miscSettings.hasDeployCooldown()));
                    setItem(21, miscSettings.hasDeployCooldown() ? ItemManager.ENABLED : ItemManager.DISABLED);
                }
                case 14, 23 -> {
                    DatabaseUtils.setPlayerMiscSettings(uuid, miscSettings.withRangedAttackIndicator(miscSettings.getRangedAttackIndicator().next()));
                    setItem(23, miscSettings.getRangedAttackIndicator().isDefault() ? ItemManager.DEFAULT : miscSettings.getRangedAttackIndicator().isTrue() ? ItemManager.ENABLED : ItemManager.DISABLED);
                }
            }
        }
    }
    private static final class ItemSettingsInv extends CreeperInv {
        private ItemSettingsInv(UUID uuid) {
            super(54, "物品布局设置");
            setItems(getBorders(), ItemManager.BORDER);
            setItem(14, ItemManager.BORDER);
            setItem(27, null);
            setItem(35, null);
            setItems(19, 25, ItemManager.BORDER);
            setItem(0, ItemManager.BACK, event -> new SettingsInv(uuid).open(Bukkit.getPlayer(uuid)));
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            setItems(36, 44, ItemManager.HOTBAR_SLOT);
            setItem(15, ItemManager.OFFHAND_SLOT);
            setItem(10, ItemManager.SET_HELMET_SLOT);
            setItem(11, ItemManager.SET_CHESTPLATE_SLOT);
            setItem(12, ItemManager.SET_LEGGINGS_SLOT);
            setItem(13, ItemManager.SET_BOOTS_SLOT);
            final DatabaseUtils.ItemSettings itemSettings = DatabaseUtils.fetchPlayerItemSettings(uuid);
            for(int i = 0; i < 2; i++) {
                setItem(slot(itemSettings.getWeaponSlot(i)), ItemManager.SET_WEAPON_SLOTS[i]);
            }
            for(int i = 0; i < 4; i++) {
                setItem(slot(itemSettings.getArtifactSlot(i)), ItemManager.SET_ARTIFACT_SLOTS[i]);
            }
            setItem(49, ItemManager.APPLY, event -> {
                byte[] flags = new byte[] {0, -1, -1, -1, -1, -1, -1};
                for(int i = 0; i < 9; i++) {
                    final ItemStack item = getInventory().getItem(i + 27);
                    if(item != null) {
                        flags[item.getPersistentDataContainer().getOrDefault(Utils.itemOrdinalKey, PersistentDataType.INTEGER, -1) + 1] = (byte) i;
                    }
                }
                final ItemStack offhand = getInventory().getItem(16);
                if(offhand != null) {
                    flags[offhand.getPersistentDataContainer().getOrDefault(Utils.itemOrdinalKey, PersistentDataType.INTEGER, -1) + 1] = 40;
                }
                boolean isValid = true;
                for(int i = 1; i <= 6; i++) {
                    if(flags[i] == -1) {
                        isValid = false;
                        break;
                    }
                }
                if(isValid) {
                    DatabaseUtils.setPlayerItemSettings(uuid, itemSettings.withWeaponSlot(0, flags[1]).withWeaponSlot(1, flags[2]).withArtifactSlot(0, flags[3]).withArtifactSlot(1, flags[4]).withArtifactSlot(2, flags[5]).withArtifactSlot(3, flags[6]));
                } else {
                    if(event.getWhoClicked() instanceof final Player player) {
                        new InformationDialogue("错误：设置存在冲突，请重新设置", () -> open(player)).open(player);
                    }
                }
            });
        }
        private static int slot(int slot) {
            if(slot == 40) {
                return 16;
            }
            return slot + 27;
        }
        @Override
        public void onClick(InventoryClickEvent event) {
            super.onClick(event);
            if(event.getClickedInventory() != null && event.getClickedInventory().getHolder() instanceof ItemSettingsInv) {
                final int slot = event.getSlot();
                event.setCancelled(!((slot >= 27 && slot < 36) || slot == 16));
            }
        }
    }
    private static final class ServersInv extends CreeperInv {
        private static final ServersInv instance = new ServersInv();
        private ServersInv() {
            super(27, "切换服务器");
            setItems(getBorders(), ItemManager.BORDER);
            setItem(8, ItemManager.CLOSE, event -> event.getWhoClicked().closeInventory());
            setItem(10, ItemManager.FFA);
            setItem(11, ItemManager.TDM);
            setItem(12, ItemManager.CTF);
            setItem(13, ItemManager.CQT);
            setItem(16, ItemManager.LOBBY, event -> {
                if(event.getWhoClicked() instanceof Player player) {
                    player.chat("/hub");
                }
            });
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
    private static final class InformationDialogue extends CreeperInv {
        private InformationDialogue(String message, Runnable okay) {
            super(InventoryType.HOPPER, message);
            setItem(2, ItemManager.OKAY, event -> okay.run());
        }
    }
}
