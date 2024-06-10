package fun.creepersmc.creeperspvp;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import java.util.Arrays;
import static fun.creepersmc.creeperspvp.ItemManager.*;
public final class WeaponManager {
    public static final int RAPIER = 0;
    public static final int FREEZING_FOIL = 1;
    public static final int STICK = 2;
    public static final int BREEZE_ROD = 3;
    public static final int SICKLES = 4;
    public static final int NIGHTMARES_BITE = 5;
    public static final int SWORD = 6;
    public static final int DIAMOND_SWORD = 7;
    public static final int CLAYMORE = 8;
    public static final int FIREBRAND = 9;
    public static final int AXE = 10;
    public static final int HIGHLAND_AXE = 11;
    public static final int HEAVY_AXE = 12;
    public static final int CURSED_AXE = 13;
    public static final int PICKAXE = 14;
    public static final int DIAMOND_PICKAXE = 15;
    public static final int UNKNOWN = 16;
    public static final int UNKNOWN_UPGRADE = 17;
    public static final int MACE = 18;
    public static final int TEMPEST_MACE = 19;
    public static final int HARPOON = 20;
    public static final int TRIDENT = 22;
    public static final int BOW = 28;
    public static final int CROSSBOW = 30;
    public static final ItemStack[] weapons = new ItemStack[] {new ItemStack(Material.WOODEN_SWORD), new ItemStack(Material.GOLDEN_SWORD), new ItemStack(Material.STICK), new ItemStack(Material.BREEZE_ROD), new ItemStack(Material.IRON_HOE), new ItemStack(Material.WOODEN_HOE), new ItemStack(Material.IRON_SWORD), new ItemStack(Material.DIAMOND_SWORD), new ItemStack(Material.NETHERITE_SWORD), new ItemStack(Material.NETHERITE_SWORD), new ItemStack(Material.IRON_AXE), new ItemStack(Material.DIAMOND_AXE), new ItemStack(Material.NETHERITE_AXE), new ItemStack(Material.NETHERITE_AXE), new ItemStack(Material.IRON_PICKAXE), new ItemStack(Material.DIAMOND_PICKAXE), new ItemStack(Material.STRUCTURE_VOID), new ItemStack(Material.STRUCTURE_VOID), new ItemStack(Material.MACE), new ItemStack(Material.MACE), new ItemStack(Material.TRIDENT), new ItemStack(Material.TRIDENT), new ItemStack(Material.TRIDENT), new ItemStack(Material.TRIDENT), null, null, null, null, new ItemStack(Material.BOW), new ItemStack(Material.BOW), new ItemStack(Material.CROSSBOW), new ItemStack(Material.CROSSBOW)};
    public static final int[] selections = new int[] {RAPIER, STICK, SICKLES, SWORD, CLAYMORE, AXE, HEAVY_AXE, PICKAXE, MACE, HARPOON, TRIDENT, BOW, CROSSBOW};
    public static final int[] upgrades = new int[] {FREEZING_FOIL, -1, BREEZE_ROD, -1, NIGHTMARES_BITE, -1, DIAMOND_SWORD, -1, FIREBRAND, -1, HIGHLAND_AXE, -1, CURSED_AXE, -1, DIAMOND_PICKAXE, -1, UNKNOWN_UPGRADE, -1, TEMPEST_MACE, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    private WeaponManager() {}
    public static void init() {
        weapons[RAPIER].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.setRarity(ItemRarity.COMMON);
            meta.itemName(Component.text("轻剑", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("剑刃灵活狭长，出手又快又狠。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 4.5, 2048);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, RAPIER);
        });
        weapons[FREEZING_FOIL].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.setRarity(ItemRarity.RARE);
            meta.itemName(Component.text("霜冻花剑", NamedTextColor.DARK_AQUA));
            meta.lore(removeItalics(Arrays.asList(Component.text("这种针状的花剑触感冰凉，取敌首级如探囊取物。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 4.5, 10);
            meta.getPersistentDataContainer().set(Utils.attackEffectIDKey, PersistentDataType.BYTE, Utils.EFFECT_FREEZE);
            meta.getPersistentDataContainer().set(Utils.attackEffectDataKey, PersistentDataType.INTEGER, 139);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, FREEZING_FOIL);
        });
        weapons[STICK].editMeta(meta -> {
            meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
            meta.setRarity(ItemRarity.COMMON);
            meta.itemName(Component.text("木棒", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("这根手杖将目标压制得无法反抗分毫。", NamedTextColor.GRAY))));
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, STICK);
        });
        weapons[BREEZE_ROD].editMeta(meta -> {
            meta.addEnchant(Enchantment.KNOCKBACK, 3, true);
            meta.setRarity(ItemRarity.RARE);
            meta.itemName(Component.text("旋风棒", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("在一场毁天灭地的风暴中锻造而成，这根手杖能召唤狂风的力量。", NamedTextColor.GRAY))));
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, BREEZE_ROD);
        });
        weapons[SICKLES].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.setRarity(ItemRarity.COMMON);
            meta.itemName(Component.text("镰刀", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("这种仪式性的武器最早起源于沙漠地带。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 6, 2);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, SICKLES);
        });
        weapons[NIGHTMARES_BITE].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.setRarity(ItemRarity.RARE);
            meta.itemName(Component.text("梦魇之噬", NamedTextColor.DARK_GREEN));
            meta.lore(removeItalics(Arrays.asList(Component.text("刀刃上毒液的致命性可经时间流逝而仍保持效果。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 6, 2);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, NIGHTMARES_BITE);
        });
        weapons[SWORD].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.addEnchant(Enchantment.SWEEPING_EDGE, 3, true);
            meta.setRarity(ItemRarity.COMMON);
            meta.itemName(Component.text("剑", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("一把坚固且可靠的剑。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.setEnchantmentGlintOverride(false);
            editMeleeAttributes(meta, 7, 1.6);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, SWORD);
        });
        weapons[DIAMOND_SWORD].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.addEnchant(Enchantment.SWEEPING_EDGE, 3, true);
            meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            meta.setRarity(ItemRarity.RARE);
            meta.itemName(Component.text("钻石剑", NamedTextColor.AQUA));
            meta.lore(removeItalics(Arrays.asList(Component.text("拥有一把钻石剑是一个英雄或经验丰富的冒险家的标配。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 8, 1.6);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, DIAMOND_SWORD);
        });
        weapons[CLAYMORE].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            meta.setRarity(ItemRarity.COMMON);
            meta.itemName(Component.text("阔剑", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("这把沉重的巨剑可以轻松地劈开潜影贝的厚壳。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.setEnchantmentGlintOverride(false);
            editMeleeAttributes(meta, 9, 1.15);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, CLAYMORE);
        });
        weapons[FIREBRAND].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            meta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
            meta.setRarity(ItemRarity.RARE);
            meta.itemName(Component.text("烙火", NamedTextColor.RED));
            meta.lore(removeItalics(Arrays.asList(Component.text("铸造于烈焰锻造厂的最黑最深处，拥有烈焰的力量。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 9, 1.15);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, FIREBRAND);
        });
        weapons[AXE].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.setRarity(ItemRarity.COMMON);
            meta.itemName(Component.text("斧", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("斧是一把十分有效的武器，深受卫道士的喜爱。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 9, 1);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, AXE);
        });
        weapons[HIGHLAND_AXE].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.addEnchant(Enchantment.EFFICIENCY, 15, true);
            meta.setRarity(ItemRarity.RARE);
            meta.itemName(Component.text("高地斧", NamedTextColor.AQUA));
            meta.lore(removeItalics(Arrays.asList(Component.text("劈裂 III", NamedTextColor.GRAY), Component.text("高地之斧的制作工艺精湛，是一种光鲜的战争武器，也是一种大胆的反击手段。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 10, 1);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, HIGHLAND_AXE);
        });
        weapons[HEAVY_AXE].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.setRarity(ItemRarity.COMMON);
            meta.itemName(Component.text("重斧", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("一种适合野蛮战士的毁灭性武器。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 11, 0.7);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, HEAVY_AXE);
        });
        weapons[CURSED_AXE].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.setRarity(ItemRarity.RARE);
            meta.itemName(Component.text("诅咒之斧", NamedTextColor.GREEN));
            meta.lore(removeItalics(Arrays.asList(Component.text("爆炸 III", NamedTextColor.GRAY), Component.text("只需轻轻一划，这把含有诅咒和剧毒的斧头就能使受害者卧床多年。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 11, 0.7);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, CURSED_AXE);
        });
        weapons[PICKAXE].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.setRarity(ItemRarity.COMMON);
            meta.itemName(Component.text("镐", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("冒险家和英雄们必不可缺的工具。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 8, 1.2);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, PICKAXE);
        });
        weapons[DIAMOND_PICKAXE].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.setRarity(ItemRarity.RARE);
            meta.itemName(Component.text("钻石镐", NamedTextColor.AQUA));
            meta.lore(removeItalics(Arrays.asList(Component.text("钻石是最耐用的材料之一，用来做一把镐子再适合不过了。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 9, 1.2);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, DIAMOND_PICKAXE);
        });
        weapons[MACE].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.setRarity(ItemRarity.RARE);
            meta.itemName(Component.text("重锤", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 6, 0.6);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, MACE);
        });
        weapons[TEMPEST_MACE].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.addEnchant(Enchantment.WIND_BURST, 3, true);
            meta.setRarity(ItemRarity.EPIC);
            meta.itemName(Component.text("暴风锤", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 6, 0.6);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, TEMPEST_MACE);
        });
        weapons[HARPOON].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.addEnchant(Enchantment.LOYALTY, 1, true);
            meta.setRarity(ItemRarity.RARE);
            meta.itemName(Component.text("渔叉", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("可以迅速刺穿陆地或水下的敌人。", NamedTextColor.GRAY))));//
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.setEnchantmentGlintOverride(false);
            editMeleeAttributes(meta, 10, 0.9);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, HARPOON);
        });
        weapons[TRIDENT].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.addEnchant(Enchantment.LOYALTY, 3, true);
            meta.setRarity(ItemRarity.RARE);
            meta.itemName(Component.text("三叉戟", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.setEnchantmentGlintOverride(false);
            editMeleeAttributes(meta, 9, 1.1);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, TRIDENT);
        });
        weapons[BOW].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.setRarity(ItemRarity.COMMON);
            meta.itemName(Component.text("弓", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, BOW);
        });
        weapons[CROSSBOW].editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.setRarity(ItemRarity.COMMON);
            meta.itemName(Component.text("弩", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.INTEGER, CROSSBOW);
        });
    }
}
