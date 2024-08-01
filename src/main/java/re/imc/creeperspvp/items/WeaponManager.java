package re.imc.creeperspvp.items;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import re.imc.creeperspvp.utils.Utils;
import java.util.Arrays;
import static re.imc.creeperspvp.items.ItemManager.*;
public final class WeaponManager {
    public static final short RAPIER = 0;
    public static final short FREEZING_FOIL = 1;
    public static final short STICK = 3;
    public static final short BREEZE_ROD = 4;
    public static final short BLAZE_ROD = 5;
    public static final short TERROR_ROD = 6;
    public static final short SICKLES = 7;
    public static final short THE_LAST_LAUGH = 8;
    public static final short NIGHTMARES_BITE = 9;
    public static final short SWORD = 10;
    public static final short DIAMOND_SWORD = 11;
    public static final short CLAYMORE = 13;
    public static final short FIREBRAND = 14;
    public static final short AXE = 16;
    public static final short HIGHLAND_AXE = 17;
    public static final short HEAVY_AXE = 19;
    public static final short CURSED_AXE = 20;
    public static final short PICKAXE = 22;
    public static final short DIAMOND_PICKAXE = 23;
    public static final short UNKNOWN = 24;
    public static final short UNKNOWN_UPGRADE = 25;
    public static final short MACE = 26;
    public static final short TEMPEST_MACE = 27;
    public static final short HARPOON = 30;
    public static final short TIDE_REVERSER = 31;
    public static final short TRIDENT = 32;
    public static final short AZURE_STORM = 33;
    public static final short SHORTBOW = 34;
    public static final short MECHANICAL_SHORTBOW = 35;
    public static final short PURPLE_STORM = 36;
    public static final short BOW = 37;
    public static final short BONE_BOW = 38;
    public static final short SNOW_BOW = 40;
    public static final short WINTERS_TOUCH = 41;
    public static final short WIND_BOW = 42;
    public static final short ECHO_OF_THE_VALLEY = 43;
    public static final short LONGBOW = 44;
    public static final short RED_SNAKE = 45;
    public static final short GUARDIAN_BOW = 46;
    public static final short POWER_BOW = 47;
    public static final short ELITE_POWER_BOW = 48;
    public static final short SABREWING = 49;
    public static final short RAPID_CROSSBOW = 50;
    public static final short AUTO_CROSSBOW = 51;
    public static final short BUTTERFLY_CROSSBOW = 52;
    public static final short CROSSBOW = 53;
    public static final short THE_SLICER = 54;
    public static final short AZURE_SEEKER = 55;
    public static final short SCATTER_CROSSBOW = 56;
    public static final short HARP_CROSSBOW = 57;
    public static final short LIGHTNING_HARP_CROSSBOW = 58;
    public static final short DUAL_CROSSBOWS = 59;
    public static final short HEAVY_CROSSBOW = 62;
    public static final short DOOM_CROSSBOW = 63;
    public static final short SLAYER_CROSSBOW = 64;
    public static final short COG_CROSSBOW = 65;
    public static final short PIGLINS_PRIDE = 66;
    public static final ItemStack[][] weapons = new ItemStack[80][2];
    public static final short[] selections = new short[] {RAPIER, STICK, SICKLES, SWORD, CLAYMORE, AXE, HEAVY_AXE, PICKAXE, MACE, HARPOON, TRIDENT, SHORTBOW, BOW, SNOW_BOW, WIND_BOW, LONGBOW, POWER_BOW, RAPID_CROSSBOW, CROSSBOW, SCATTER_CROSSBOW, DUAL_CROSSBOWS, HEAVY_CROSSBOW, COG_CROSSBOW};
    public static final short[][] upgrades = new short[][] {{FREEZING_FOIL}, {}, {}, {BREEZE_ROD, BLAZE_ROD, TERROR_ROD}, {}, {}, {}, {THE_LAST_LAUGH, NIGHTMARES_BITE}, {}, {}, {DIAMOND_SWORD}, {}, {}, {FIREBRAND}, {}, {}, {HIGHLAND_AXE}, {}, {}, {CURSED_AXE}, {}, {}, {DIAMOND_PICKAXE}, {}, {}, {}, {TEMPEST_MACE}, {}, {}, {}, {TIDE_REVERSER}, {}, {AZURE_STORM}, {}, {MECHANICAL_SHORTBOW}, {}, {}, {BONE_BOW}, {}, {}, {WINTERS_TOUCH}, {}, {ECHO_OF_THE_VALLEY}, {}, {RED_SNAKE, GUARDIAN_BOW}, {}, {}, {ELITE_POWER_BOW, SABREWING}, {}, {}, {AUTO_CROSSBOW}, {}, {}, {THE_SLICER, AZURE_SEEKER}, {}, {}, {HARP_CROSSBOW, LIGHTNING_HARP_CROSSBOW}, {}, {}, {}, {}, {}, {DOOM_CROSSBOW, SLAYER_CROSSBOW}, {}, {}, {PIGLINS_PRIDE}, {}};
    public static final long[] prices = new long[] {2000, 4800, 0, 1200, 3000, 1800, 2000, 800, 1600, 1700, 0, 1000, 0, 1600, 4000, 0, 1800, 4000, 0, 2400, 5000, 0, 1000, 2000, 0, 0, 0, 0, 2000, 4800, 1000, 5000, 1400, 3000, 1200, 2000, 0, 0, 1000, 0, 1400, 3000, 1600, 3200, 1600, 3400, 2800, 2000, 4200, 3600, 1400, 3000, 800, 1200, 1400, 1000, 1200, 2600, 3200, 1600, 0, 0, 1800, 3500, 4500, 2000, 4500};
    private WeaponManager() {}
    static void init() {
        final ItemStack[] weapons0 = new ItemStack[] {new ItemStack(Material.WOODEN_SWORD), new ItemStack(Material.GOLDEN_SWORD), null, new ItemStack(Material.STICK), new ItemStack(Material.BREEZE_ROD), new ItemStack(Material.BLAZE_ROD), new ItemStack(Material.STICK), new ItemStack(Material.IRON_HOE), new ItemStack(Material.DIAMOND_HOE), new ItemStack(Material.WOODEN_HOE), new ItemStack(Material.IRON_SWORD), new ItemStack(Material.DIAMOND_SWORD), null, new ItemStack(Material.NETHERITE_SWORD), new ItemStack(Material.NETHERITE_SWORD), new ItemStack(Material.NETHERITE_SWORD), new ItemStack(Material.IRON_AXE), new ItemStack(Material.DIAMOND_AXE), null, new ItemStack(Material.NETHERITE_AXE), new ItemStack(Material.NETHERITE_AXE), new ItemStack(Material.NETHERITE_AXE), new ItemStack(Material.IRON_PICKAXE), new ItemStack(Material.DIAMOND_PICKAXE), null, null, new ItemStack(Material.MACE), new ItemStack(Material.MACE), new ItemStack(Material.MACE), new ItemStack(Material.MACE), new ItemStack(Material.TRIDENT), new ItemStack(Material.TRIDENT), new ItemStack(Material.TRIDENT), new ItemStack(Material.TRIDENT), new ItemStack(Material.BOW), new ItemStack(Material.BOW), new ItemStack(Material.BOW), new ItemStack(Material.BOW), new ItemStack(Material.BOW), new ItemStack(Material.BOW), new ItemStack(Material.BOW), new ItemStack(Material.BOW), new ItemStack(Material.BOW), new ItemStack(Material.BOW), new ItemStack(Material.BOW), new ItemStack(Material.BOW), new ItemStack(Material.BOW), new ItemStack(Material.BOW), new ItemStack(Material.BOW), new ItemStack(Material.BOW), new ItemStack(Material.CROSSBOW), new ItemStack(Material.CROSSBOW), new ItemStack(Material.CROSSBOW), new ItemStack(Material.CROSSBOW), new ItemStack(Material.CROSSBOW), new ItemStack(Material.CROSSBOW), new ItemStack(Material.CROSSBOW), new ItemStack(Material.CROSSBOW), new ItemStack(Material.CROSSBOW), new ItemStack(Material.CROSSBOW), new ItemStack(Material.CROSSBOW), new ItemStack(Material.CROSSBOW), new ItemStack(Material.CROSSBOW), new ItemStack(Material.CROSSBOW), new ItemStack(Material.CROSSBOW), new ItemStack(Material.CROSSBOW), new ItemStack(Material.CROSSBOW)};
        for(int i = 0; i < weapons0.length; i++) {
            weapons[i][0] = new ItemStack(Material.GRAY_DYE);
            weapons[i][1] = weapons0[i];
        }
        for(int i = 0; i < 2; i++) {
            weapons[RAPIER][i].editMeta(meta -> {
                meta.itemName(Component.text("轻剑", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("剑刃灵活狭长，出手又快又狠。", NamedTextColor.GRAY))));
                editMeleeAttributes(meta, 4.5, 2048);
            });
            weapons[FREEZING_FOIL][i].editMeta(meta -> {
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("霜冻花剑", NamedTextColor.DARK_AQUA));
                meta.lore(removeItalics(Arrays.asList(Component.text("霜冻附加 II", NamedTextColor.GRAY), Component.text("这种针状的花剑触感冰凉，取敌首级如探囊取物。", NamedTextColor.GRAY))));
                editMeleeAttributes(meta, 4.5, 2048);
                meta.getPersistentDataContainer().set(Utils.meleeAttackEffectIDKey, PersistentDataType.BYTE, Utils.MELEE_EFFECT_FREEZE);
                meta.getPersistentDataContainer().set(Utils.meleeAttackEffectDataKey, PersistentDataType.INTEGER, 139);
            });
            weapons[STICK][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
                meta.itemName(Component.text("木棒", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这根手杖将目标压制得无法反抗分毫。", NamedTextColor.GRAY))));
                meta.setEnchantmentGlintOverride(false);
            });
            weapons[BREEZE_ROD][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.KNOCKBACK, 4, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("旋风棒", NamedTextColor.BLUE));
                meta.lore(removeItalics(Arrays.asList(Component.text("在一场毁天灭地的风暴中锻造而成，这根手杖能召唤狂风的力量。", NamedTextColor.GRAY))));
            });
            weapons[BLAZE_ROD][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
                meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("烈焰棒", NamedTextColor.RED));
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            });
            weapons[TERROR_ROD][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("惧恐棒", NamedTextColor.GREEN));
                meta.lore(removeItalics(Arrays.asList(Component.text("爆炸 I", NamedTextColor.GRAY), Component.text("", NamedTextColor.GRAY))));
                meta.getPersistentDataContainer().set(Utils.meleeAttackEffectIDKey, PersistentDataType.BYTE, Utils.MELEE_EFFECT_EXPLOSION);
                meta.getPersistentDataContainer().set(Utils.meleeAttackEffectDataKey, PersistentDataType.FLOAT, 1f);
            });
            weapons[SICKLES][i].editMeta(meta -> {
                meta.itemName(Component.text("镰刀", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这种仪式性的武器最早起源于沙漠地带。", NamedTextColor.GRAY))));
                editMeleeAttributes(meta, 6, 2);
            });
            weapons[THE_LAST_LAUGH][i].editMeta(meta -> {
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("笑到最后", NamedTextColor.YELLOW));
                meta.lore(removeItalics(Arrays.asList(Component.text("这把镰刀似乎时不时会发出奇怪而扭曲的笑声。", NamedTextColor.GRAY))));
                editMeleeAttributes(meta, 6, 3.2);
            });
            weapons[NIGHTMARES_BITE][i].editMeta(meta -> {
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("梦魇之噬", NamedTextColor.DARK_GREEN));
                meta.lore(removeItalics(Arrays.asList(Component.text("剧毒 II", NamedTextColor.GRAY), Component.text("刀刃上毒液的致命性可经时间流逝而仍保持效果。", NamedTextColor.GRAY))));
                editMeleeAttributes(meta, 6, 2);
                meta.getPersistentDataContainer().set(Utils.meleeAttackEffectIDKey, PersistentDataType.BYTE, Utils.MELEE_EFFECT_POISON);
                meta.getPersistentDataContainer().set(Utils.meleeAttackEffectDataKey, PersistentDataType.INTEGER, 150);
            });
            weapons[SWORD][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.SWEEPING_EDGE, 3, true);
                meta.itemName(Component.text("剑", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("一把坚固且可靠的剑。", NamedTextColor.GRAY))));
                meta.setEnchantmentGlintOverride(false);
                editMeleeAttributes(meta, 7, 1.6);
            });
            weapons[DIAMOND_SWORD][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.SWEEPING_EDGE, 3, true);
                meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("钻石剑", NamedTextColor.AQUA));
                meta.lore(removeItalics(Arrays.asList(Component.text("拥有一把钻石剑是一个英雄或经验丰富的冒险家的标配。", NamedTextColor.GRAY))));
                editMeleeAttributes(meta, 8, 1.6);
            });
            weapons[CLAYMORE][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                meta.itemName(Component.text("阔剑", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这把沉重的巨剑可以轻松地劈开潜影贝的厚壳。", NamedTextColor.GRAY))));
                meta.setEnchantmentGlintOverride(false);
                editMeleeAttributes(meta, 9, 1.15);
            });
            weapons[FIREBRAND][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                meta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("烙火", NamedTextColor.RED));
                meta.lore(removeItalics(Arrays.asList(Component.text("铸造于烈焰锻造厂的最黑最深处，拥有烈焰的力量。", NamedTextColor.GRAY))));
                editMeleeAttributes(meta, 9, 1.15);
            });
            weapons[AXE][i].editMeta(meta -> {
                meta.itemName(Component.text("斧", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("斧是一把十分有效的武器，深受卫道士的喜爱。", NamedTextColor.GRAY))));
                editMeleeAttributes(meta, 9, 1);
            });
            weapons[HIGHLAND_AXE][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.EFFICIENCY, 15, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("高地斧", NamedTextColor.AQUA));
                meta.lore(removeItalics(Arrays.asList(Component.text("劈裂 III", NamedTextColor.GRAY), Component.text("高地之斧的制作工艺精湛，是一种光鲜的战争武器，也是一种大胆的反击手段。", NamedTextColor.GRAY))));
                editMeleeAttributes(meta, 10, 1);
            });
            weapons[HEAVY_AXE][i].editMeta(meta -> {
                meta.setRarity(ItemRarity.COMMON);
                meta.itemName(Component.text("重斧", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("一种适合野蛮战士的毁灭性武器。", NamedTextColor.GRAY))));
                editMeleeAttributes(meta, 11, 0.7);
            });
            weapons[CURSED_AXE][i].editMeta(meta -> {
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("诅咒之斧", NamedTextColor.GREEN));
                meta.lore(removeItalics(Arrays.asList(Component.text("爆炸 I", NamedTextColor.GRAY), Component.text("只需轻轻一划，这把含有诅咒和剧毒的斧头就能使受害者卧床多年。", NamedTextColor.GRAY))));
                editMeleeAttributes(meta, 11, 0.7);
                meta.getPersistentDataContainer().set(Utils.meleeAttackEffectIDKey, PersistentDataType.BYTE, Utils.MELEE_EFFECT_EXPLOSION);
                meta.getPersistentDataContainer().set(Utils.meleeAttackEffectDataKey, PersistentDataType.FLOAT, 1f);
            });
            weapons[PICKAXE][i].editMeta(meta -> {
                meta.itemName(Component.text("镐", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("冒险家和英雄们必不可缺的工具。", NamedTextColor.GRAY))));
                editMeleeAttributes(meta, 8, 1.2);
            });
            weapons[DIAMOND_PICKAXE][i].editMeta(meta -> {
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("钻石镐", NamedTextColor.AQUA));
                meta.lore(removeItalics(Arrays.asList(Component.text("钻石是最耐用的材料之一，用来做一把镐子再适合不过了。", NamedTextColor.GRAY))));
                editMeleeAttributes(meta, 9, 1.2);
            });
            weapons[MACE][i].editMeta(meta -> {
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("重锤", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
                editMeleeAttributes(meta, 6, 0.6);
            });
            weapons[TEMPEST_MACE][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.WIND_BURST, 3, true);
                meta.setRarity(ItemRarity.EPIC);
                meta.itemName(Component.text("暴风锤", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
                editMeleeAttributes(meta, 6, 0.6);
            });
            weapons[HARPOON][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.LOYALTY, 1, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("渔叉", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("可以迅速刺穿陆地或水下的敌人。", NamedTextColor.GRAY))));
                meta.setEnchantmentGlintOverride(false);
                editMeleeAttributes(meta, 10, 0.9);
            });
            weapons[TIDE_REVERSER][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.RIPTIDE, 3, true);
                meta.setRarity(ItemRarity.EPIC);
                meta.itemName(Component.text("逆浪者", NamedTextColor.AQUA));
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
                editMeleeAttributes(meta, 10, 0.9);
            });
            weapons[TRIDENT][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.LOYALTY, 3, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("三叉戟", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
                meta.setEnchantmentGlintOverride(false);
                editMeleeAttributes(meta, 9, 1.1);
            });
            weapons[AZURE_STORM][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.LOYALTY, 5, true);
                meta.addEnchant(Enchantment.CHANNELING, 1, true);
                meta.setRarity(ItemRarity.EPIC);
                meta.itemName(Component.text("蓝色风暴", NamedTextColor.BLUE));
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
                editMeleeAttributes(meta, 9, 1.1);
                meta.getPersistentDataContainer().set(Utils.rangedAttackEffectIDKey, PersistentDataType.BYTE, Utils.RANGED_EFFECT_CHANNELING);
            });
            weapons[SHORTBOW][i].editMeta(meta -> {
                meta.itemName(Component.text("短弓", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("窃贼和强盗的最爱，能在短距离内中灵活地打出致命的伤害。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 0.7f, 1.6f);
            });
            weapons[MECHANICAL_SHORTBOW][i].editMeta(meta -> {
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("机械短弓", NamedTextColor.GRAY));
                meta.lore(removeItalics(Arrays.asList(Component.text("快速射击技术的全新发展，给这把弓带来了极快的射速。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 0.7f, 2.2f);
            });
            weapons[BOW][i].editMeta(meta -> {
                meta.itemName(Component.text("弓", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("一件简单而万能的武器。南瓜牧场的猎人说，弓永远不会让你失望，不像其他的武器。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1, 1);
            });
            weapons[BONE_BOW][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.PUNCH, 1, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("骨弓", NamedTextColor.GOLD));
                meta.lore(removeItalics(Arrays.asList(Component.text("骨弓虽然由城墙内一个不起眼的村庄制作而成，但却成了南瓜牧场的骄傲。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1.15f, 1);
            });
            weapons[SNOW_BOW][i].editMeta(meta -> {
                meta.itemName(Component.text("冰雪之弓", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("冻矢", NamedTextColor.GRAY), Component.text("在战斗中与冰雪之弓交战的人还必须做好面对凛冽寒风的准备。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1, 0.85f);
                meta.getPersistentDataContainer().set(Utils.rangedAttackEffectIDKey, PersistentDataType.BYTE, Utils.RANGED_EFFECT_FREEZE);
                meta.getPersistentDataContainer().set(Utils.rangedAttackEffectDataKey, PersistentDataType.INTEGER, 139);
            });
            weapons[WINTERS_TOUCH][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.MULTISHOT, 1, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("凛冬之触", NamedTextColor.BLUE));
                meta.lore(removeItalics(Arrays.asList(Component.text("冻矢", NamedTextColor.GRAY), Component.text("据说由这把传奇的弓发射的箭带有寒冷冬风的力量。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1, 0.85f);
                meta.getPersistentDataContainer().set(Utils.rangedAttackEffectIDKey, PersistentDataType.BYTE, Utils.RANGED_EFFECT_FREEZE);
                meta.getPersistentDataContainer().set(Utils.rangedAttackEffectDataKey, PersistentDataType.INTEGER, 139);
            });
            weapons[WIND_BOW][i].editMeta(meta -> {
                meta.itemName(Component.text("狂风之弓", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("击飞", NamedTextColor.GRAY), Component.text("一把令人着迷的弓，捕捉狂风的能量来发射强大的风之箭矢。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1.1f, 0.75f);
                meta.getPersistentDataContainer().set(Utils.rangedAttackEffectIDKey, PersistentDataType.BYTE, Utils.RANGED_EFFECT_WIND);
                meta.getPersistentDataContainer().set(Utils.rangedAttackEffectDataKey, PersistentDataType.DOUBLE, 0.2d);
            });
            weapons[ECHO_OF_THE_VALLEY][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.PIERCING, 4, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("空谷回声", NamedTextColor.DARK_GREEN));
                meta.lore(removeItalics(Arrays.asList(Component.text("击飞", NamedTextColor.GRAY), Component.text("这把弓会与最初上弦时隐藏在山谷之中的扭曲之风交相呼应。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1.1f, 0.75f);
                meta.getPersistentDataContainer().set(Utils.rangedAttackEffectIDKey, PersistentDataType.BYTE, Utils.RANGED_EFFECT_WIND);
                meta.getPersistentDataContainer().set(Utils.rangedAttackEffectDataKey, PersistentDataType.DOUBLE, 0.2d);
            });
            weapons[LONGBOW][i].editMeta(meta -> {
                meta.itemName(Component.text("长弓", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("长弓是为打猎而设计的，但用在战斗中同样是不错的武器。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1.4f, 0.6f);
            });
            weapons[RED_SNAKE][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.FLAME, 1, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("红蛇", NamedTextColor.RED));
                meta.lore(removeItalics(Arrays.asList(Component.text("红蛇无时无刻都在释放爆炸性的能量，沙漠中致命的火灾极有可能来源于此。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1.4f, 0.6f);
            });
            weapons[GUARDIAN_BOW][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.PUNCH, 1, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("守卫者之弓", NamedTextColor.DARK_AQUA));
                meta.lore(removeItalics(Arrays.asList(Component.text("由石化珊瑚锻造而成的守卫者之弓是失落岁月中沦落文明的遗留物。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1.6f, 0.6f);
            });
            weapons[POWER_BOW][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.POWER, 3, true);
                meta.setEnchantmentGlintOverride(false);
                meta.itemName(Component.text("力量弓", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这把弓如此强大的原因一直都在困扰着人们。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1.4f, 0.3f);
            });
            weapons[ELITE_POWER_BOW][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.POWER, 4, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("精英力量弓", NamedTextColor.GOLD));
                meta.lore(removeItalics(Arrays.asList(Component.text("关于精英力量弓构造的秘密早已消失的无影无踪。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1.4f, 0.3f);
            });
            weapons[SABREWING][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.POWER, 3, true);
                meta.addEnchant(Enchantment.PUNCH, 2, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("刀翅蜂鸟", NamedTextColor.YELLOW));
                meta.lore(removeItalics(Arrays.asList(Component.text("这把弓是为一位失落已久的冠军打造的。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1.4f, 0.3f);
            });
            weapons[RAPID_CROSSBOW][i].editMeta(meta -> {
                meta.itemName(Component.text("快弩", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("新的装填技术比以前更有效地帮助了弩的射击。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 0.6f, 2f);
            });
            weapons[AUTO_CROSSBOW][i].editMeta(meta -> {
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("自动弩", NamedTextColor.GRAY));
                meta.lore(removeItalics(Arrays.asList(Component.text("奇厄教主说，这把强大的弩的设计是他在幻象中见到的。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 0.6f, 3.5f);
            });
            weapons[CROSSBOW][i].editMeta(meta -> {
                meta.itemName(Component.text("弩", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("弩是灾厄村民使用的远程武器，常见于掠夺者之中。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1, 0.8f);
            });
            weapons[AZURE_SEEKER][i].editMeta(meta -> {
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("蔚蓝探索者", NamedTextColor.BLUE));
                meta.lore(removeItalics(Arrays.asList(Component.text("如果听到声音时看到一抹蓝，那一定是蔚蓝探索者在射击。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1, 1.06f);
            });
            weapons[THE_SLICER][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.PIERCING, 4, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("穿刺者", NamedTextColor.DARK_GREEN));
                meta.lore(removeItalics(Arrays.asList(Component.text("穿刺者是喜欢搞恶作剧的灾厄工程师的巅峰之作，设计上甚至能击穿最厚实的盔甲。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1, 0.8f);
            });
            weapons[SCATTER_CROSSBOW][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.MULTISHOT, 1, true);
                meta.setEnchantmentGlintOverride(false);
                meta.itemName(Component.text("散射弩", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这种弩经过了改装，可以装载并发射多发箭矢，同时也算是半件乐器。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1, 0.7f);
            });
            weapons[HARP_CROSSBOW][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.MULTISHOT, 2, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("竖琴弩", NamedTextColor.YELLOW));
                meta.lore(removeItalics(Arrays.asList(Component.text("艺术与战争的完美结合，是派对上不可或缺的东西。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1, 0.7f);
            });
            weapons[LIGHTNING_HARP_CROSSBOW][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.MULTISHOT, 1, true);
                meta.addEnchant(Enchantment.CHANNELING, 1, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("雷电琴弩", NamedTextColor.AQUA));
                meta.lore(removeItalics(Arrays.asList(Component.text("闪电的力量极大地改变了这张弩发射时的声音。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1, 0.7f);
                meta.getPersistentDataContainer().set(Utils.rangedAttackEffectIDKey, PersistentDataType.BYTE, Utils.RANGED_EFFECT_CHANNELING);
            });
            weapons[DUAL_CROSSBOWS][i].editMeta(meta -> {
                meta.itemName(Component.text("双持弩", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("双持弩是需要应对快节奏战斗的战士能够迅速反应的完美解决方案。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 0.67f, 1.2f);
                addMultiItemAttribute(meta, 2);
            });
            weapons[HEAVY_CROSSBOW][i].editMeta(meta -> {
                meta.itemName(Component.text("重弩", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这把加重过的弩对于敌人而言是一种真正的威胁，能在远距离上打出极高的伤害。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1.6f, 0.5f);
            });
            weapons[DOOM_CROSSBOW][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("末日弩", NamedTextColor.BLUE));
                meta.lore(removeItalics(Arrays.asList(Component.text("许多人认为末日弩只是一段传说，但这次传说是真的。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1.6f, 0.5f);
            });
            weapons[SLAYER_CROSSBOW][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.PIERCING, 4, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("杀戮之弩", NamedTextColor.DARK_PURPLE));
                meta.lore(removeItalics(Arrays.asList(Component.text("杀戮之弩是许多传奇猎人珍贵的传家宝。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1.6f, 0.5f);
            });
            weapons[COG_CROSSBOW][i].editMeta(meta -> {
                meta.itemName(Component.text("齿轮弩", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这把古老的齿轮弩至今仍能平滑转动，依然是把可靠的武器。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1f, 0.75f);
                addMultiItemAttribute(meta, 3);
            });
            weapons[PIGLINS_PRIDE][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.PIERCING, 4, true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("猪灵之傲", NamedTextColor.GOLD));
                meta.lore(removeItalics(Arrays.asList(Component.text("一把既有年头又凶残的武器，发现于下界的最远端。", NamedTextColor.GRAY))));
                editRangedAttributes(meta, 1f, 0.75f);
                addMultiItemAttribute(meta, 3);
            });
            for(final short[] j = new short[]{0}; j[0] < weapons.length; j[0]++) {
                if(weapons[j[0]][i] != null) {
                    weapons[j[0]][i] = Utils.modifyItem(weapons[j[0]][i], "[{function:\"set_components\", components:{can_break:{predicates:[{blocks:\"#minecraft:fire\"}]}}}]");
                    weapons[j[0]][i].editMeta(meta -> {
                        meta.setUnbreakable(true);
                        meta.addItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE);
                        if(!meta.hasRarity()) {
                            meta.setRarity(ItemRarity.COMMON);
                        }
                        meta.getPersistentDataContainer().set(Utils.weaponIDKey, PersistentDataType.SHORT, j[0]);
                    });
                }
            }
        }
        for(final short[] i = new short[]{0}; i[0] < weapons.length; i[0]++) {
            if(weapons[i[0]][1] != null) {
                weapons[i[0]][1].editMeta(meta -> meta.setCustomModelData(Utils.getCustomModelData(weapons[i[0]][1], null, -1)));
            }
        }
    }
}
