package fun.creepersmc.creeperspvp;
import fun.creepersmc.creeperspvp.iui.IUIManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import java.util.*;
public final class ItemManager {
    public static final int SWORD = 0;
    public static final int DIAMOND_SWORD = 1;
    public static final int CLAYMORE = 2;
    public static final int BROADSWORD = 3;
    public static final int AXE = 4;
    public static final int HIGHLAND_AXE = 5;
    public static final ItemStack[] weapons = new ItemStack[] {new ItemStack(Material.IRON_SWORD), new ItemStack(Material.DIAMOND_SWORD), new ItemStack(Material.NETHERITE_SWORD), new ItemStack(Material.NETHERITE_SWORD), new ItemStack(Material.IRON_AXE), new ItemStack(Material.DIAMOND_AXE)};
    public static final int[] weaponUpgrades = new int[] {DIAMOND_SWORD, -1, BROADSWORD, -1, HIGHLAND_AXE, -1};
    public static final ItemStack MERCENARY_ARMOR_SELECTOR = new ItemStack(Material.IRON_CHESTPLATE);
    public static final ItemStack ARMOR_SELECTOR = new ItemStack(Material.IRON_CHESTPLATE);
    public static final ItemStack WEAPON1_SELECTOR = new ItemStack(Material.IRON_AXE);
    public static final ItemStack WEAPON2_SELECTOR = new ItemStack(Material.BOW);
    public static final ItemStack ARTIFACT1_SELECTOR = new ItemStack(Material.GLASS_BOTTLE);
    public static final ItemStack ARTIFACT2_SELECTOR = new ItemStack(Material.GLASS_BOTTLE);
    public static final ItemStack ARTIFACT3_SELECTOR = new ItemStack(Material.GLASS_BOTTLE);
    public static final ItemStack DEPLOY = new ItemStack(Material.COMPASS);
    public static final ItemStack BORDER = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
    public static final ItemStack CLOSE = new ItemStack(Material.RED_STAINED_GLASS_PANE);
    private static final AttributeModifier EMPTY_ATTRIBUTE_MODIFIER = new AttributeModifier("", 0, AttributeModifier.Operation.ADD_NUMBER);
    private static final UUID ATTACK_DAMAGE_ATTRIBUTE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    private static final UUID ATTACK_SPEED_ATTRIBUTE_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
    private static final UUID HELMET_ATTRIBUTE_UUID = UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150");
    private static final UUID CHESTPLATE_ATTRIBUTE_UUID = UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E");
    private static final UUID LEGGINGS_ATTRIBUTE_UUID = UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D");
    private static final UUID BOOTS_ATTRIBUTE_UUID = UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B");
    private ItemManager() {}
    public static void init() {
        weapons[SWORD].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.itemName(Component.text("剑"));
            meta.lore(removeItalics(Arrays.asList(Component.text("一把坚固且可靠的剑。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 7, 1.6);
        });
        weapons[DIAMOND_SWORD].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.addEnchant(Enchantment.SHARPNESS, 3, false);
            meta.setRarity(ItemRarity.RARE);
            meta.itemName(Component.text("钻石剑"));
            meta.lore(removeItalics(Arrays.asList(Component.text("拥有一把钻石剑是一个英雄或经验丰富的冒险家的标配。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 7, 1.6);
        });
        weapons[CLAYMORE].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.itemName(Component.text("阔剑"));
            meta.lore(removeItalics(Arrays.asList(Component.text("这把沉重的巨剑可以轻松地劈开潜影贝的厚壳。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 10, 1);
        });
        weapons[AXE].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.itemName(Component.text("斧"));
            meta.lore(removeItalics(Arrays.asList(Component.text("斧是一把十分有效的武器，深受卫道士的喜爱。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 9, 1);
        });
        weapons[HIGHLAND_AXE].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.itemName(Component.text("高地斧"));
            meta.setRarity(ItemRarity.RARE);
            meta.addEnchant(Enchantment.EFFICIENCY, 15, false);
            meta.lore(removeItalics(Arrays.asList(Component.text("劈裂 III", NamedTextColor.GRAY), Component.text("高地之斧的制作工艺精湛，是一种光鲜的战争武器，也是一种大胆的反击手段。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 10, 1);
        });
        MERCENARY_ARMOR_SELECTOR.editMeta(meta -> {
            meta.itemName(Component.text("雇佣兵盔甲"));
            meta.lore(removeItalics(Arrays.asList(Component.text("便宜，但好用", NamedTextColor.GRAY))));
            displayArmorAttributes(meta, 15, 0, 0);
        });
        ARMOR_SELECTOR.editMeta(meta -> {
            meta.itemName(Component.text("选择盔甲"));                  
            meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.ARMOR_SELECTOR);
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, EMPTY_ATTRIBUTE_MODIFIER);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        });
        WEAPON1_SELECTOR.editMeta(meta -> {
            meta.itemName(Component.text("选择武器#1"));
            meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.WEAPON_SELECTOR);
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, EMPTY_ATTRIBUTE_MODIFIER);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, EMPTY_ATTRIBUTE_MODIFIER);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        });
        WEAPON2_SELECTOR.editMeta(meta -> {
            meta.itemName(Component.text("选择武器#2"));
            meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.WEAPON_SELECTOR);
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
        });
        ARTIFACT1_SELECTOR.editMeta(meta -> {
            meta.itemName(Component.text("选择法器#1"));
            meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.ARTIFACT_SELECTOR);
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
        });
        ARTIFACT2_SELECTOR.editMeta(meta -> {
            meta.itemName(Component.text("选择法器#2"));
            meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.ARTIFACT_SELECTOR);
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
        });
        ARTIFACT3_SELECTOR.editMeta(meta -> {
            meta.itemName(Component.text("选择法器#3"));
            meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.ARTIFACT_SELECTOR);
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
        });
        DEPLOY.editMeta(meta -> {
            meta.itemName(Component.text("进入战斗", NamedTextColor.GREEN));
            meta.getPersistentDataContainer().set(Utils.utilIDKey, PersistentDataType.BYTE, Utils.UTIL_SPAWN);
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
        });
        BORDER.editMeta(meta -> meta.itemName(Component.empty()));
        CLOSE.editMeta(meta -> {
            meta.itemName(Component.text("✖ 关闭", NamedTextColor.RED));//◀ Back
        });
    }
    private static void editMeleeAttributes(ItemMeta meta, double damage, double speed) {
        meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE);
        meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_ATTRIBUTE_UUID, "", damage - 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_ATTRIBUTE_UUID, "", speed - 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        final List<Component> meleeAttributeLore = removeItalics(Arrays.asList(Component.empty(), Component.text("在主手时：", NamedTextColor.GRAY), Component.text(" " + (damage + (meta.hasEnchant(Enchantment.SHARPNESS) ? meta.getEnchantLevel(Enchantment.SHARPNESS) * 0.5 + 0.5 : 0)) + " 攻击伤害", NamedTextColor.DARK_GREEN), Component.text(" " + speed + " 攻击速度", NamedTextColor.DARK_GREEN)));
        if(meta.hasLore()) {
            final List<Component> lore = meta.lore();
            lore.addAll(meleeAttributeLore);
            meta.lore(lore);
        } else {
            meta.lore(meleeAttributeLore);
        }
    }
    private static void displayArmorAttributes(ItemMeta meta, int armor, int armorToughness, int knockBackResistance) {
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, EMPTY_ATTRIBUTE_MODIFIER);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ARMOR_TRIM);
        final ArrayList<Component> armorAttributeLore = new ArrayList<>(3);
        Collections.addAll(armorAttributeLore, Component.empty(), Component.text("穿戴时：", NamedTextColor.GRAY));
        if(armor > 0) {
            armorAttributeLore.add(Component.text("+" + armor + " 护甲值", NamedTextColor.BLUE));
        }
        if(armorToughness > 0) {
            armorAttributeLore.add(Component.text("+" + armorToughness + " 盔甲韧性", NamedTextColor.BLUE));
        }
        if(knockBackResistance > 0) {
            armorAttributeLore.add(Component.text("+" + knockBackResistance + " 击退抗性", NamedTextColor.BLUE));
        }
        removeItalics(armorAttributeLore);
        if(meta.hasLore()) {
            final List<Component> lore = meta.lore();
            lore.addAll(armorAttributeLore);
            meta.lore(lore);
        } else {
            meta.lore(armorAttributeLore);
        }
    }
    private static List<Component> removeItalics(final List<Component> lore) {
        lore.replaceAll(component -> component.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        return lore;
    }
}
