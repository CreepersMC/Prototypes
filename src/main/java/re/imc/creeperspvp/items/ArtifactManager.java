package re.imc.creeperspvp.items;
import de.tr7zw.nbtapi.NBT;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import re.imc.creeperspvp.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static re.imc.creeperspvp.items.ItemManager.*;
public final class ArtifactManager {
    public static final int CATEGORY_PROJECTILES = 0;
    public static final int CATEGORY_EXPLOSIVES_AND_TRAPS = 64;
    public static final int CATEGORY_FOOD = 128;
    public static final int CATEGORY_DEFENSES_AND_BUFFS = 192;
    public static final ItemStack[] artifactCategorySelectors = new ItemStack[] {new ItemStack(Material.ARROW), new ItemStack(Material.TNT), new ItemStack(Material.APPLE), new ItemStack(Material.SHIELD)};
    public static final int SNOWBALL = CATEGORY_PROJECTILES;
    public static final int EGG = CATEGORY_PROJECTILES + 1;
    public static final int ENDER_PEARL = CATEGORY_PROJECTILES + 2;
    public static final int EYE_OF_ENDER = CATEGORY_PROJECTILES + 3;
    public static final int FIRE_CHARGE = CATEGORY_PROJECTILES + 4;
    public static final int EXPLOSIVE_FIRE_CHARGE = CATEGORY_PROJECTILES + 5;
    public static final int ICE_BOMB = CATEGORY_PROJECTILES + 7;
    public static final int WIND_CHARGE = CATEGORY_PROJECTILES + 8;
    public static final int DRAGON_CHARGE = CATEGORY_PROJECTILES + 9;
    public static final int WITHER_CHARGE = CATEGORY_PROJECTILES + 10;
    public static final int SHULKER_BULLET = CATEGORY_PROJECTILES + 12;
    public static final int SPLASH_POTION_OF_HASTE = CATEGORY_PROJECTILES + 13;
    public static final int SPLASH_POTION_OF_LEAPING = CATEGORY_PROJECTILES + 14;
    public static final int SPLASH_POTION_OF_SWIFTNESS = CATEGORY_PROJECTILES + 15;
    public static final int SPLASH_POTION_OF_HEALING = CATEGORY_PROJECTILES + 16;
    public static final int SPLASH_POTION_OF_REGENERATION = CATEGORY_PROJECTILES + 17;
    public static final int SPLASH_POTION_OF_SATURATION = CATEGORY_PROJECTILES + 18;
    public static final int SPLASH_POTION_OF_LEVITATION = CATEGORY_PROJECTILES + 20;
    public static final int SPLASH_POTION_OF_SLOW_FALLING = CATEGORY_PROJECTILES + 21;
    public static final int SPLASH_POTION_OF_ATTACK_FATIGUE = CATEGORY_PROJECTILES + 22;
    public static final int SPLASH_POTION_OF_BLINDNESS = CATEGORY_PROJECTILES + 23;
    public static final int SPLASH_POTION_OF_SLOWNESS = CATEGORY_PROJECTILES + 24;
    public static final int SPLASH_POTION_OF_HARMING = CATEGORY_PROJECTILES + 25;
    public static final int SPLASH_POTION_OF_POISON = CATEGORY_PROJECTILES + 26;
    public static final int SPLASH_POTION_OF_WEAKNESS = CATEGORY_PROJECTILES + 27;
    public static final int TNT = CATEGORY_EXPLOSIVES_AND_TRAPS;
    public static final int END_CRYSTAL = CATEGORY_EXPLOSIVES_AND_TRAPS + 2;
    public static final int FISHING_ROD = CATEGORY_EXPLOSIVES_AND_TRAPS + 3;
    public static final int CARROT = CATEGORY_FOOD;
    public static final int GOLDEN_CARROT = CATEGORY_FOOD + 1;
    public static final int APPLE = CATEGORY_FOOD + 2;
    public static final int GOLDEN_APPLE = CATEGORY_FOOD + 3;
    public static final int ENCHANTED_GOLDEN_APPLE = CATEGORY_FOOD + 4;
    public static final int MELON_SLICE = CATEGORY_FOOD + 5;
    public static final int BEEF = CATEGORY_FOOD + 7;
    public static final int COOKED_BEEF = CATEGORY_FOOD + 8;
    public static final int PORKCHOP = CATEGORY_FOOD + 9;
    public static final int COOKED_PORKCHOP = CATEGORY_FOOD + 10;
    public static final int MUTTON = CATEGORY_FOOD + 11;
    public static final int COOKED_MUTTON = CATEGORY_FOOD + 12;
    public static final int SALMON = CATEGORY_FOOD + 13;
    public static final int COOKED_SALMON = CATEGORY_FOOD + 14;
    public static final int RABBIT_STEW = CATEGORY_FOOD + 15;
    public static final int BEETROOT_SOUP = CATEGORY_FOOD + 16;
    public static final int CHICKEN = CATEGORY_FOOD + 17;
    public static final int COOKED_CHICKEN = CATEGORY_FOOD + 18;
    public static final int MUSHROOM_STEW = CATEGORY_FOOD + 19;
    public static final int SUSPICIOUS_STEW = CATEGORY_FOOD + 20;
    public static final int POTATO = CATEGORY_FOOD + 21;
    public static final int BAKED_POTATO = CATEGORY_FOOD + 22;
    public static final int BREAD = CATEGORY_FOOD + 23;
    public static final int COD = CATEGORY_FOOD + 24;
    public static final int COOKED_COD = CATEGORY_FOOD + 25;
    public static final int RABBIT = CATEGORY_FOOD + 26;
    public static final int COOKED_RABBIT = CATEGORY_FOOD + 27;
    public static final int BEETROOT = CATEGORY_FOOD + 28;
    public static final int PUMPKIN_PIE = CATEGORY_FOOD + 29;
    public static final int CHORUS_FRUIT = CATEGORY_FOOD + 30;
    public static final int DRIED_KELP = CATEGORY_FOOD + 31;
    public static final int HONEY_BOTTLE = CATEGORY_FOOD + 32;
    public static final int COOKIE = CATEGORY_FOOD + 33;
    public static final int SWEET_BERRIES = CATEGORY_FOOD + 34;
    public static final int GLOW_BERRIES = CATEGORY_FOOD + 35;
    public static final int MILK_BUKET = CATEGORY_FOOD + 36;
    public static final int SHIELD = CATEGORY_DEFENSES_AND_BUFFS;
    public static final int TOTEM_OF_UNDYING = CATEGORY_DEFENSES_AND_BUFFS + 1;
    public static final ItemStack[] artifacts = new ItemStack[] {
        new ItemStack(Material.SNOWBALL, 16), new ItemStack(Material.EGG, 16), new ItemStack(Material.ENDER_PEARL, 4), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        new ItemStack(Material.TNT, 4), null, new ItemStack(Material.END_CRYSTAL), new ItemStack(Material.FISHING_ROD), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        new ItemStack(Material.CARROT, 4), new ItemStack(Material.GOLDEN_CARROT, 1), new ItemStack(Material.APPLE, 8), new ItemStack(Material.GOLDEN_APPLE, 1), new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1), new ItemStack(Material.MELON_SLICE, 8), null, new ItemStack(Material.BEEF, 8), new ItemStack(Material.COOKED_BEEF, 2), new ItemStack(Material.PORKCHOP, 8), new ItemStack(Material.COOKED_PORKCHOP, 2), new ItemStack(Material.MUTTON, 8), new ItemStack(Material.COOKED_MUTTON, 2), new ItemStack(Material.SALMON, 16), new ItemStack(Material.COOKED_SALMON, 2), new ItemStack(Material.RABBIT_STEW, 1), new ItemStack(Material.BEETROOT_SOUP, 1), new ItemStack(Material.CHICKEN, 8), new ItemStack(Material.COOKED_CHICKEN, 4), new ItemStack(Material.MUSHROOM_STEW, 1), new ItemStack(Material.SUSPICIOUS_STEW, 1), new ItemStack(Material.POTATO, 8), new ItemStack(Material.BAKED_POTATO, 4), new ItemStack(Material.BREAD, 4), new ItemStack(Material.COD, 16), new ItemStack(Material.COOKED_COD, 4), new ItemStack(Material.RABBIT, 8), new ItemStack(Material.COOKED_RABBIT, 4), new ItemStack(Material.BEETROOT, 4), new ItemStack(Material.PUMPKIN_PIE, 8), new ItemStack(Material.CHORUS_FRUIT, 8), new ItemStack(Material.DRIED_KELP, 8), new ItemStack(Material.HONEY_BOTTLE, 16), new ItemStack(Material.COOKIE, 16), new ItemStack(Material.SWEET_BERRIES, 8), new ItemStack(Material.GLOW_BERRIES, 16), new ItemStack(Material.MILK_BUCKET, 1), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        new ItemStack(Material.SHIELD), new ItemStack(Material.TOTEM_OF_UNDYING), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
    };
    public static final int[] useCooldowns = new int[] {
        4, 4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
    };
    public static final int CUSTOM = 1;
    public static final int INTERACT = 2;
    public static final int CONSUME = 4;
    public static final int LAUNCH_PROJECTILE = 8;
    public static final int[] useEvents = new int[] {
        LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, -1, -1, -1, -1, LAUNCH_PROJECTILE, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
    };
    public static final int[] gainCooldowns = new int[] {
        20, 40, 300, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        120, -1, 200, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        90, 180, 120, 360, 1080, 60, -1, 90, 240, 90, 240, 60, 180, 60, 180, 300, 180, 60, 180, 180, 180, 30, 150, 150, 60, 150, 90, 150, 30, 240, 240, 30, 180, 60, 60, 60, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, 480, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
    };
    public static final int[][] selections = new int[][] {{SNOWBALL, EGG, ENDER_PEARL}, {TNT, END_CRYSTAL, FISHING_ROD}, {CARROT, APPLE, MELON_SLICE, BEEF, PORKCHOP, MUTTON, SALMON, RABBIT_STEW, BEETROOT_SOUP, CHICKEN, MUSHROOM_STEW, POTATO, BREAD, COD, RABBIT, BEETROOT, PUMPKIN_PIE, CHORUS_FRUIT, DRIED_KELP, HONEY_BOTTLE, COOKIE, SWEET_BERRIES, GLOW_BERRIES, MILK_BUKET}, {SHIELD}};
    public static final int[][] upgrades = new int[][] {
        {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {},
        {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {},
        {GOLDEN_CARROT}, {}, {GOLDEN_APPLE}, {}, {}, {}, {}, {COOKED_BEEF}, {}, {COOKED_PORKCHOP}, {}, {COOKED_MUTTON}, {}, {COOKED_SALMON}, {}, {}, {}, {COOKED_CHICKEN}, {}, {}, {}, {BAKED_POTATO}, {}, {}, {COOKED_COD}, {}, {COOKED_RABBIT}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {},
        {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}
    };
    public static final long[] prices = new long[] {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    };
    private static final Component enterOnClick = Component.text(">>> ", NamedTextColor.WHITE).append(Component.text("点击进入", NamedTextColor.GREEN)).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    private ArtifactManager() {}
    static void init() {
        artifactCategorySelectors[CATEGORY_PROJECTILES / 64].editMeta(meta -> {
            meta.itemName(Component.text("弹射物", NamedTextColor.YELLOW));
            meta.lore(removeItalics(Arrays.asList(Component.text("可以扔出或使用远程武器射出的物品", NamedTextColor.GOLD), enterOnClick)));
        });
        artifactCategorySelectors[CATEGORY_EXPLOSIVES_AND_TRAPS / 64].editMeta(meta -> {
            meta.itemName(Component.text("爆炸物与陷阱", NamedTextColor.RED));
            meta.lore(removeItalics(Arrays.asList(Component.text("可以爆炸或困住敌人的物品", NamedTextColor.DARK_RED), enterOnClick)));
        });
        artifactCategorySelectors[CATEGORY_FOOD / 64].editMeta(meta -> {
            meta.itemName(Component.text("食物", NamedTextColor.GREEN));
            meta.lore(removeItalics(Arrays.asList(Component.text("可以吃的物品", NamedTextColor.DARK_GREEN), enterOnClick)));
        });
        artifactCategorySelectors[CATEGORY_DEFENSES_AND_BUFFS / 64].editMeta(meta -> {
            meta.itemName(Component.text("防御与增益", NamedTextColor.BLUE));
            meta.lore(removeItalics(Arrays.asList(Component.text("可以用于防御或提供增益效果的物品", NamedTextColor.DARK_AQUA), enterOnClick)));
        });
        artifacts[SNOWBALL].editMeta(meta -> meta.itemName(Component.text("雪球", NamedTextColor.WHITE)));
        artifacts[EGG].editMeta(meta -> meta.itemName(Component.text("鸡蛋", NamedTextColor.WHITE)));
        artifacts[ENDER_PEARL].editMeta(meta -> meta.itemName(Component.text("末影珍珠", NamedTextColor.WHITE)));
        NBT.modify(artifacts[TNT], nbt -> {
            nbt.getOrCreateCompound("can_place_on");
        });
        NBT.modify(artifacts[END_CRYSTAL], nbt -> {
            nbt.getOrCreateCompound("can_place_on");
        });
        artifacts[TNT].editMeta(meta -> {
            meta.itemName(Component.text("TNT", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("来啊，把它点着！还能出什么事不成？", NamedTextColor.GRAY))));
        });
        artifacts[END_CRYSTAL].editMeta(meta -> {
            meta.itemName(Component.text("末地水晶", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("末地水晶的力量将令敌人终生难忘。", NamedTextColor.GRAY))));
        });
        artifacts[FISHING_ROD].editMeta(meta -> {
            meta.itemName(Component.text("钓鱼竿", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("真正的冒险家都知道钓鱼竿是个好东西，它的作用可不仅仅是钓鱼。", NamedTextColor.GRAY))));
        });
        artifacts[CARROT].editMeta(meta -> meta.itemName(Component.text("胡萝卜", NamedTextColor.WHITE)));
        artifacts[GOLDEN_CARROT].editMeta(meta -> meta.itemName(Component.text("金胡萝卜", NamedTextColor.WHITE)));
        artifacts[APPLE].editMeta(meta -> meta.itemName(Component.text("苹果", NamedTextColor.WHITE)));
        artifacts[GOLDEN_APPLE].editMeta(meta -> {
            meta.setRarity(ItemRarity.UNCOMMON);
            meta.itemName(Component.text("金苹果", NamedTextColor.YELLOW));
        });
        artifacts[ENCHANTED_GOLDEN_APPLE].editMeta(meta -> meta.itemName(Component.text("附魔金苹果", NamedTextColor.LIGHT_PURPLE)));
        artifacts[MELON_SLICE].editMeta(meta -> meta.itemName(Component.text("西瓜片", NamedTextColor.WHITE)));
        artifacts[BEEF].editMeta(meta -> meta.itemName(Component.text("生牛肉", NamedTextColor.WHITE)));
        artifacts[COOKED_BEEF].editMeta(meta -> meta.itemName(Component.text("牛排", NamedTextColor.WHITE)));
        artifacts[PORKCHOP].editMeta(meta -> meta.itemName(Component.text("生猪排", NamedTextColor.WHITE)));
        artifacts[COOKED_PORKCHOP].editMeta(meta -> meta.itemName(Component.text("熟猪排", NamedTextColor.WHITE)));
        artifacts[MUTTON].editMeta(meta -> meta.itemName(Component.text("生羊肉", NamedTextColor.WHITE)));
        artifacts[COOKED_MUTTON].editMeta(meta -> meta.itemName(Component.text("熟羊肉", NamedTextColor.WHITE)));
        artifacts[SALMON].editMeta(meta -> meta.itemName(Component.text("生鲑鱼", NamedTextColor.WHITE)));
        artifacts[COOKED_SALMON].editMeta(meta -> meta.itemName(Component.text("熟鲑鱼", NamedTextColor.WHITE)));
        artifacts[RABBIT_STEW].editMeta(meta -> meta.itemName(Component.text("兔肉煲", NamedTextColor.WHITE)));
        artifacts[BEETROOT_SOUP].editMeta(meta -> meta.itemName(Component.text("甜菜汤", NamedTextColor.WHITE)));
        artifacts[CHICKEN].editMeta(meta -> meta.itemName(Component.text("生鸡肉", NamedTextColor.WHITE)));
        artifacts[COOKED_CHICKEN].editMeta(meta -> meta.itemName(Component.text("熟鸡肉", NamedTextColor.WHITE)));
        artifacts[MUSHROOM_STEW].editMeta(meta -> meta.itemName(Component.text("蘑菇煲", NamedTextColor.WHITE)));
        artifacts[SUSPICIOUS_STEW].editMeta(meta -> meta.itemName(Component.text("迷之炖菜", NamedTextColor.WHITE)));
        artifacts[POTATO].editMeta(meta -> meta.itemName(Component.text("马铃薯", NamedTextColor.WHITE)));
        artifacts[BAKED_POTATO].editMeta(meta -> meta.itemName(Component.text("烤马铃薯", NamedTextColor.WHITE)));
        artifacts[BREAD].editMeta(meta -> meta.itemName(Component.text("面包", NamedTextColor.WHITE)));
        artifacts[COD].editMeta(meta -> meta.itemName(Component.text("生鳕鱼", NamedTextColor.WHITE)));
        artifacts[COOKED_COD].editMeta(meta -> meta.itemName(Component.text("熟鳕鱼", NamedTextColor.WHITE)));
        artifacts[RABBIT].editMeta(meta -> meta.itemName(Component.text("生兔肉", NamedTextColor.WHITE)));
        artifacts[COOKED_RABBIT].editMeta(meta -> meta.itemName(Component.text("熟兔肉", NamedTextColor.WHITE)));
        artifacts[BEETROOT].editMeta(meta -> meta.itemName(Component.text("甜菜根", NamedTextColor.WHITE)));
        artifacts[PUMPKIN_PIE].editMeta(meta -> meta.itemName(Component.text("南瓜派", NamedTextColor.WHITE)));
        artifacts[CHORUS_FRUIT].editMeta(meta -> meta.itemName(Component.text("紫颂果", NamedTextColor.WHITE)));
        artifacts[DRIED_KELP].editMeta(meta -> meta.itemName(Component.text("干海带", NamedTextColor.WHITE)));
        artifacts[HONEY_BOTTLE].editMeta(meta -> meta.itemName(Component.text("蜂蜜瓶", NamedTextColor.WHITE)));
        artifacts[COOKIE].editMeta(meta -> meta.itemName(Component.text("曲奇", NamedTextColor.WHITE)));
        artifacts[SWEET_BERRIES].editMeta(meta -> meta.itemName(Component.text("甜浆果", NamedTextColor.WHITE)));
        artifacts[GLOW_BERRIES].editMeta(meta -> meta.itemName(Component.text("发光浆果", NamedTextColor.WHITE)));
        artifacts[MILK_BUKET].editMeta(meta -> meta.itemName(Component.text("奶桶", NamedTextColor.WHITE)));
        artifacts[SHIELD].editMeta(meta -> meta.itemName(Component.text("盾牌", NamedTextColor.WHITE)));
        artifacts[TOTEM_OF_UNDYING].editMeta(meta -> meta.itemName(Component.text("不死图腾", NamedTextColor.YELLOW)));
        for(final int[] i = new int[] {0}; i[0] < artifacts.length; i[0]++) {
            if(artifacts[i[0]] != null) {
                artifacts[i[0]].editMeta(meta -> {
                    meta.setUnbreakable(true);
                    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    if(!meta.hasRarity()) {
                        meta.setRarity(ItemRarity.COMMON);
                    }
                    if(gainCooldowns[i[0]] != -1) {
                        Component gainCooldownDescription = Component.text("冷却时长：" + gainCooldowns[i[0]] / 20, NamedTextColor.DARK_GREEN);
                        if(meta.hasLore()) {
                            List<Component> lore = new ArrayList<>(meta.lore());
                            lore.add(gainCooldownDescription);
                            meta.lore(removeItalics(lore));
                        } else {
                            meta.lore(removeItalics(Arrays.asList(gainCooldownDescription)));
                        }
                    }
                    meta.getPersistentDataContainer().set(Utils.artifactIDKey, PersistentDataType.INTEGER, i[0]);
                });
            }
        }
    }
}
