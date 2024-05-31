package fun.creepersmc.creeperspvp;
import fun.creepersmc.creeperspvp.iui.IUIManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.persistence.PersistentDataType;
import java.util.*;
public final class ItemManager {
    public static final int MERCENARY_ARMOR = 0;
    public static final int RENEGADE_ARMOR = 1;
    public static final int BATTLE_ROBE = 2;
    public static final int SPLENDID_ROBE = 3;
    public static final ItemStack[][] armor = new ItemStack[][] {
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)}
    };
    public static final ItemStack[] armorSelectors = new ItemStack[] {new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_CHESTPLATE)};
    public static final int[] armorSelections = new int[] {MERCENARY_ARMOR, BATTLE_ROBE};
    public static final int[] armorUpgrades = new int[] {RENEGADE_ARMOR, -1, SPLENDID_ROBE, -1};
    public static final int SWORD = 0;
    public static final int DIAMOND_SWORD = 1;
    public static final int CLAYMORE = 2;
    public static final int FIREBRAND = 3;
    public static final int AXE = 4;
    public static final int HIGHLAND_AXE = 5;
    public static final ItemStack[] weapons = new ItemStack[] {new ItemStack(Material.IRON_SWORD), new ItemStack(Material.DIAMOND_SWORD), new ItemStack(Material.NETHERITE_SWORD), new ItemStack(Material.NETHERITE_SWORD), new ItemStack(Material.IRON_AXE), new ItemStack(Material.DIAMOND_AXE)};
    public static final int[] weaponSelections = new int[] {SWORD, CLAYMORE, AXE};
    public static final int[] weaponUpgrades = new int[] {DIAMOND_SWORD, -1, FIREBRAND, -1, HIGHLAND_AXE, -1};
    public static final int SHIELD = 0;
    public static final int COOKED_BEEF = 1;
    public static final int ENDER_PEARL = 2;
    public static final ItemStack[] artifacts = new ItemStack[] {new ItemStack(Material.SHIELD), new ItemStack(Material.COOKED_BEEF, 64), new ItemStack(Material.ENDER_PEARL, 4)};
    public static final int[] artifactSelections = new int[] {SHIELD, COOKED_BEEF, ENDER_PEARL};
    public static final int[] artifactUpgrades = new int[] {-1, -1, -1};
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
    private static final UUID ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID = UUID.fromString("46E84BED-7B0E-0685-11D5-5BB372F68C4C");
    private static final UUID ATTACK_SPEED_BONUS_ATTRIBUTE_UUID = UUID.fromString("FA778358-DA02-0464-3236-EFDD9BB1FD75");
    private static final UUID[] ARMOR_ATTRIBUTE_UUIDS = new UUID[] {UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B")};
    private static final EquipmentSlot[] armorSlots = new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.BODY};
    private ItemManager() {}
    public static void init() {
        for(ItemStack armor : armor[MERCENARY_ARMOR]) {
            armor.editMeta(meta -> {
                meta.setUnbreakable(true);
                meta.itemName(Component.text("雇佣兵盔甲"));
                meta.lore(removeItalics(Arrays.asList(Component.text("雇佣兵盔甲，为那些想省点钱的人所青睐，", NamedTextColor.GRAY), Component.text("虽然并不高级，但却足以完成任务。", NamedTextColor.GRAY))));
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            });
        }
        for(final int[] i = new int[] {0}; i[0] < 4; i[0]++) {
            armor[RENEGADE_ARMOR][i[0]].editMeta(meta -> {
                meta.setUnbreakable(true);
                meta.itemName(Component.text("叛节之甲"));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[i[0]], "", 1.25, AttributeModifier.Operation.ADD_NUMBER, armorSlots[i[0]]));
                meta.lore(removeItalics(Arrays.asList(Component.text("对那些被雇来保护村民免受灾厄威胁的佣兵来说，这件盔甲很划算。", NamedTextColor.GRAY))));
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            });
        }
        for(ItemStack armor : armor[BATTLE_ROBE]) {
            armor.editMeta(meta -> {
                meta.setUnbreakable(true);
                meta.itemName(Component.text("战袍"));
                meta.lore(removeItalics(Arrays.asList(Component.text("战袍由奇厄教主法庭上杰出的唤魔者穿戴。", NamedTextColor.GRAY))));
                if(meta instanceof LeatherArmorMeta leatherArmorMeta) {
                    leatherArmorMeta.setColor(Color.BLACK);
                }
                ((ArmorMeta) meta).setTrim(new ArmorTrim(TrimMaterial.GOLD, TrimPattern.VEX));
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ARMOR_TRIM);
            });
        }
        armor[BATTLE_ROBE][1].editMeta(meta -> meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST)));
        armor[BATTLE_ROBE][1].editMeta(meta -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", 0.33, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST)));
        armorSelectors[MERCENARY_ARMOR].editMeta(meta -> {
            meta.itemName(Component.text("雇佣兵盔甲"));
            meta.lore(removeItalics(Arrays.asList(Component.text("雇佣兵盔甲，为那些想省点钱的人所青睐，", NamedTextColor.GRAY), Component.text("虽然并不高级，但却足以完成任务。", NamedTextColor.GRAY))));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 15, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        });
        armorSelectors[RENEGADE_ARMOR].editMeta(meta -> {
            meta.itemName(Component.text("叛节之甲"));
            meta.lore(removeItalics(Arrays.asList(Component.text("对那些被雇来保护村民免受灾厄威胁的佣兵来说，这件盔甲很划算。", NamedTextColor.GRAY))));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 15, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        });
        armorSelectors[BATTLE_ROBE].editMeta(meta -> {
            meta.itemName(Component.text("战袍"));
            meta.lore(removeItalics(Arrays.asList(Component.text("战袍由奇厄教主法庭上杰出的唤魔者穿戴。", NamedTextColor.GRAY))));
            ((LeatherArmorMeta) meta).setColor(Color.BLACK);
            ((ArmorMeta) meta).setTrim(new ArmorTrim(TrimMaterial.GOLD, TrimPattern.VEX));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 11, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", 0.33, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
            meta.addItemFlags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ARMOR_TRIM);
        });
        weapons[SWORD].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.itemName(Component.text("剑"));
            meta.lore(removeItalics(Arrays.asList(Component.text("一把坚固且可靠的剑。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 7, 1.6);
        });
        weapons[DIAMOND_SWORD].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.addEnchant(Enchantment.SHARPNESS, 3, true);
            meta.setRarity(ItemRarity.RARE);
            meta.itemName(Component.text("钻石剑"));
            meta.lore(removeItalics(Arrays.asList(Component.text("拥有一把钻石剑是一个英雄或经验丰富的冒险家的标配。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 7, 1.6);
        });
        weapons[CLAYMORE].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            meta.itemName(Component.text("阔剑"));
            meta.lore(removeItalics(Arrays.asList(Component.text("这把沉重的巨剑可以轻松地劈开潜影贝的厚壳。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.setEnchantmentGlintOverride(false);
            editMeleeAttributes(meta, 9, 1.15);
        });
        weapons[FIREBRAND].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            meta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
            meta.setRarity(ItemRarity.RARE);
            meta.itemName(Component.text("烙火"));
            meta.lore(removeItalics(Arrays.asList(Component.text("铸造于烈焰锻造厂的最黑最深处，拥有烈焰的力量。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 9, 1.15);
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
            meta.addEnchant(Enchantment.EFFICIENCY, 15, true);
            meta.lore(removeItalics(Arrays.asList(Component.text("劈裂 III", NamedTextColor.GRAY), Component.text("高地之斧的制作工艺精湛，是一种光鲜的战争武器，也是一种大胆的反击手段。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 10, 1);
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
            meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.WEAPON1_SELECTOR);
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, EMPTY_ATTRIBUTE_MODIFIER);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, EMPTY_ATTRIBUTE_MODIFIER);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        });
        WEAPON2_SELECTOR.editMeta(meta -> {
            meta.itemName(Component.text("选择武器#2"));
            meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.WEAPON2_SELECTOR);
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
        });
        ARTIFACT1_SELECTOR.editMeta(meta -> {
            meta.itemName(Component.text("选择法器#1"));
            meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.ARTIFACT1_SELECTOR);
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
        });
        ARTIFACT2_SELECTOR.editMeta(meta -> {
            meta.itemName(Component.text("选择法器#2"));
            meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.ARTIFACT2_SELECTOR);
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
        });
        ARTIFACT3_SELECTOR.editMeta(meta -> {
            meta.itemName(Component.text("选择法器#3"));
            meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.ARTIFACT3_SELECTOR);
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
    private static List<Component> removeItalics(final List<Component> lore) {
        lore.replaceAll(component -> component.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        return lore;
    }
}
