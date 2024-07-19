package re.imc.creeperspvp.items;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import re.imc.creeperspvp.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static re.imc.creeperspvp.items.ItemManager.*;
public final class ArtifactManager {
    public static final short CATEGORY_PROJECTILES = 0;
    public static final short CATEGORY_EXPLOSIVES_AND_TRAPS = 64;
    public static final short CATEGORY_FOOD = 128;
    public static final short CATEGORY_DEFENSES_AND_BUFFS = 192;
    public static final ItemStack[] artifactCategorySelectors = new ItemStack[] {new ItemStack(Material.ARROW), new ItemStack(Material.TNT), new ItemStack(Material.APPLE), new ItemStack(Material.SHIELD)};
    public static final short SNOWBALL = CATEGORY_PROJECTILES;
    public static final short EGG = CATEGORY_PROJECTILES + 1;
    public static final short ENDER_PEARL = CATEGORY_PROJECTILES + 2;
    public static final short EYE_OF_ENDER = CATEGORY_PROJECTILES + 3;
    public static final short FIRE_CHARGE = CATEGORY_PROJECTILES + 4;
    public static final short LIGHTNING_CHARGE = CATEGORY_PROJECTILES + 5;
    public static final short EXPLOSION_CHARGE = CATEGORY_PROJECTILES + 6;
    public static final short ICE_CHARGE = CATEGORY_PROJECTILES + 7;
    public static final short WIND_CHARGE = CATEGORY_PROJECTILES + 8;
    public static final short DRAGON_CHARGE = CATEGORY_PROJECTILES + 9;
    public static final short WITHER_CHARGE = CATEGORY_PROJECTILES + 10;
    public static final short SHULKER_BULLET = CATEGORY_PROJECTILES + 12;
    public static final short MUNDANE_SPLASH_POTION = CATEGORY_PROJECTILES + 13;
    public static final short SPLASH_POTION_OF_HASTE = CATEGORY_PROJECTILES + 14;
    public static final short SPLASH_POTION_OF_LEAPING = CATEGORY_PROJECTILES + 15;
    public static final short SPLASH_POTION_OF_SWIFTNESS = CATEGORY_PROJECTILES + 16;
    public static final short SPLASH_POTION_OF_HEALING = CATEGORY_PROJECTILES + 17;
    public static final short SPLASH_POTION_OF_REGENERATION = CATEGORY_PROJECTILES + 18;
    public static final short SPLASH_POTION_OF_STRENGTH = CATEGORY_PROJECTILES + 19;
    public static final short AWKWARD_SPLASH_POTION = CATEGORY_PROJECTILES + 20;
    public static final short SPLASH_POTION_OF_LEVITATION = CATEGORY_PROJECTILES + 21;
    public static final short SPLASH_POTION_OF_SLOW_FALLING = CATEGORY_PROJECTILES + 22;
    public static final short THICK_SPLASH_POTION = CATEGORY_PROJECTILES + 23;
    public static final short SPLASH_POTION_OF_ATTACK_FATIGUE = CATEGORY_PROJECTILES + 24;
    public static final short SPLASH_POTION_OF_BLINDNESS = CATEGORY_PROJECTILES + 25;
    public static final short SPLASH_POTION_OF_SLOWNESS = CATEGORY_PROJECTILES + 26;
    public static final short SPLASH_POTION_OF_HARMING = CATEGORY_PROJECTILES + 27;
    public static final short SPLASH_POTION_OF_POISON = CATEGORY_PROJECTILES + 28;
    public static final short SPLASH_POTION_OF_WEAKNESS = CATEGORY_PROJECTILES + 29;
    public static final short AWKWARD_LINGERING_POTION = CATEGORY_PROJECTILES + 30;
    public static final short LINGERING_POTION_OF_LEVITATION = CATEGORY_PROJECTILES + 31;
    public static final short LINGERING_POTION_OF_SLOW_FALLING = CATEGORY_PROJECTILES + 32;
    public static final short THICK_LINGERING_POTION = CATEGORY_PROJECTILES + 33;
    public static final short LINGERING_POTION_OF_ATTACK_FATIGUE = CATEGORY_PROJECTILES + 34;
    public static final short LINGERING_POTION_OF_BLINDNESS = CATEGORY_PROJECTILES + 35;
    public static final short LINGERING_POTION_OF_SLOWNESS = CATEGORY_PROJECTILES + 36;
    public static final short LINGERING_POTION_OF_HARMING = CATEGORY_PROJECTILES + 37;
    public static final short LINGERING_POTION_OF_POISON = CATEGORY_PROJECTILES + 38;
    public static final short LINGERING_POTION_OF_WEAKNESS = CATEGORY_PROJECTILES + 39;
    public static final short SPECTRAL_ARROW = CATEGORY_PROJECTILES + 40;
    public static final short SEEKER_ARROW = CATEGORY_PROJECTILES + 41;
    public static final short AWKWARD_TIPPED_ARROW = CATEGORY_PROJECTILES + 42;
    public static final short TIPPED_ARROW_OF_LEVITATION = CATEGORY_PROJECTILES + 43;
    public static final short TIPPED_ARROW_OF_SLOW_FALLING = CATEGORY_PROJECTILES + 44;
    public static final short THICK_TIPPED_ARROW = CATEGORY_PROJECTILES + 45;
    public static final short TIPPED_ARROW_OF_ATTACK_FATIGUE = CATEGORY_PROJECTILES + 46;
    public static final short TIPPED_ARROW_OF_BLINDNESS = CATEGORY_PROJECTILES + 47;
    public static final short TIPPED_ARROW_OF_SLOWNESS = CATEGORY_PROJECTILES + 48;
    public static final short TIPPED_ARROW_OF_HARMING = CATEGORY_PROJECTILES + 49;
    public static final short TIPPED_ARROW_OF_POISON = CATEGORY_PROJECTILES + 50;
    public static final short TIPPED_ARROW_OF_WEAKNESS = CATEGORY_PROJECTILES + 51;
    public static final short FIREWORK_ROCKET = CATEGORY_PROJECTILES + 52;
    public static final short FIREWORK_ROCKET_SHADOW = CATEGORY_PROJECTILES + 53;
    public static final short FIREWORK_ROCKET_TWINKLE = CATEGORY_PROJECTILES + 54;
    public static final short FIREWORK_ROCKET_CREEPER = CATEGORY_PROJECTILES + 55;
    public static final short FIREWORK_ROCKET_HELLFIRE = CATEGORY_PROJECTILES + 56;
    public static final short FIREWORK_ROCKET_HEAVY = CATEGORY_PROJECTILES + 57;
    public static final short TNT = CATEGORY_EXPLOSIVES_AND_TRAPS;
    public static final short END_CRYSTAL = CATEGORY_EXPLOSIVES_AND_TRAPS + 2;
    public static final short FISHING_ROD = CATEGORY_EXPLOSIVES_AND_TRAPS + 3;
    public static final short WATER_BUCKET = CATEGORY_EXPLOSIVES_AND_TRAPS + 4;
    public static final short BUCKET_OF_PUFFERFISH = CATEGORY_EXPLOSIVES_AND_TRAPS + 5;
    public static final short BUCKET_OF_AXOLTOL = CATEGORY_EXPLOSIVES_AND_TRAPS + 6;
    public static final short LAVA_BUCKET = CATEGORY_EXPLOSIVES_AND_TRAPS + 7;
    public static final short POWDER_SNOW_BUCKET = CATEGORY_EXPLOSIVES_AND_TRAPS + 8;
    public static final short COBWEB = CATEGORY_EXPLOSIVES_AND_TRAPS + 9;
    public static final short CARROT = CATEGORY_FOOD;
    public static final short GOLDEN_CARROT = CATEGORY_FOOD + 1;
    public static final short APPLE = CATEGORY_FOOD + 2;
    public static final short GOLDEN_APPLE = CATEGORY_FOOD + 3;
    public static final short ENCHANTED_GOLDEN_APPLE = CATEGORY_FOOD + 4;
    public static final short MELON_SLICE = CATEGORY_FOOD + 5;
    public static final short GLISTERING_MELON_SLICE = CATEGORY_FOOD + 6;
    public static final short BEEF = CATEGORY_FOOD + 7;
    public static final short COOKED_BEEF = CATEGORY_FOOD + 8;
    public static final short PORKCHOP = CATEGORY_FOOD + 9;
    public static final short COOKED_PORKCHOP = CATEGORY_FOOD + 10;
    public static final short MUTTON = CATEGORY_FOOD + 11;
    public static final short COOKED_MUTTON = CATEGORY_FOOD + 12;
    public static final short SALMON = CATEGORY_FOOD + 13;
    public static final short COOKED_SALMON = CATEGORY_FOOD + 14;
    public static final short RABBIT_STEW = CATEGORY_FOOD + 15;
    public static final short BEETROOT_SOUP = CATEGORY_FOOD + 16;
    public static final short CHICKEN = CATEGORY_FOOD + 17;
    public static final short COOKED_CHICKEN = CATEGORY_FOOD + 18;
    public static final short MUSHROOM_STEW = CATEGORY_FOOD + 19;
    public static final short SUSPICIOUS_STEW = CATEGORY_FOOD + 20;
    public static final short POTATO = CATEGORY_FOOD + 21;
    public static final short BAKED_POTATO = CATEGORY_FOOD + 22;
    public static final short BREAD = CATEGORY_FOOD + 23;
    public static final short COD = CATEGORY_FOOD + 24;
    public static final short COOKED_COD = CATEGORY_FOOD + 25;
    public static final short RABBIT = CATEGORY_FOOD + 26;
    public static final short COOKED_RABBIT = CATEGORY_FOOD + 27;
    public static final short BEETROOT = CATEGORY_FOOD + 28;
    public static final short PUMPKIN_PIE = CATEGORY_FOOD + 29;
    public static final short CHORUS_FRUIT = CATEGORY_FOOD + 30;
    public static final short POPPED_CHORUS_FRUIT = CATEGORY_FOOD + 31;
    public static final short DRIED_KELP = CATEGORY_FOOD + 32;
    public static final short HONEY_BOTTLE = CATEGORY_FOOD + 33;
    public static final short COOKIE = CATEGORY_FOOD + 34;
    public static final short SWEET_BERRIES = CATEGORY_FOOD + 35;
    public static final short GLOW_BERRIES = CATEGORY_FOOD + 36;
    public static final short MILK_BUKET = CATEGORY_FOOD + 37;
    public static final short MUNDANE_POTION = CATEGORY_DEFENSES_AND_BUFFS;
    public static final short POTION_OF_HASTE = CATEGORY_DEFENSES_AND_BUFFS + 1;
    public static final short POTION_OF_LEAPING = CATEGORY_DEFENSES_AND_BUFFS + 2;
    public static final short POTION_OF_SWIFTNESS = CATEGORY_DEFENSES_AND_BUFFS + 3;
    public static final short POTION_OF_HEALING = CATEGORY_DEFENSES_AND_BUFFS + 4;
    public static final short POTION_OF_REGENERATION = CATEGORY_DEFENSES_AND_BUFFS + 5;
    public static final short POTION_OF_STRENGTH = CATEGORY_DEFENSES_AND_BUFFS + 6;
    public static final short AWKWARD_POTION = CATEGORY_DEFENSES_AND_BUFFS + 7;
    public static final short POTION_OF_LEVITATION = CATEGORY_DEFENSES_AND_BUFFS + 8;
    public static final short POTION_OF_SLOW_FALLING = CATEGORY_DEFENSES_AND_BUFFS + 9;
    public static final short SHIELD = CATEGORY_DEFENSES_AND_BUFFS + 10;
    public static final short TOTEM_OF_UNDYING = CATEGORY_DEFENSES_AND_BUFFS + 11;
    public static final ItemStack[][] artifacts = new ItemStack[256][2];
    public static final short CUSTOM = 1;
    public static final short INTERACT = 2;
    public static final short CONSUME = 4;
    public static final short RESURRECT = 8;
    public static final short LAUNCH_PROJECTILE = 16;
    public static final short READY_ARROW = 32;
    public static final short ELYTRA_BOOST = 64;
    public static final short EMPTY_BUCKET = 128;
    public static final short PLACE_BLOCK = 256;
    public static final short PLACE_ENTITY = 512;
    public static final short ENTITY_EXPLODE = 1024;
    public static final short ENTITY_DEATH = 2048;
    public static final short AFTER_PLACE_ENTITY = ENTITY_EXPLODE | ENTITY_DEATH;
    public static final short[] useEvents = new short[] {
        LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE | CUSTOM, LAUNCH_PROJECTILE | CUSTOM, LAUNCH_PROJECTILE | CUSTOM, LAUNCH_PROJECTILE | CUSTOM, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE | CUSTOM, LAUNCH_PROJECTILE | CUSTOM, 0, LAUNCH_PROJECTILE | CUSTOM, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, LAUNCH_PROJECTILE, READY_ARROW, READY_ARROW, READY_ARROW, READY_ARROW, READY_ARROW, READY_ARROW, READY_ARROW, READY_ARROW, READY_ARROW, READY_ARROW, READY_ARROW, READY_ARROW | ELYTRA_BOOST, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        PLACE_BLOCK, 0, ENTITY_EXPLODE, 0, EMPTY_BUCKET, 0, 0, EMPTY_BUCKET, PLACE_BLOCK, PLACE_BLOCK, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME | PLACE_BLOCK, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME,
        CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, CONSUME, 0, RESURRECT, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };
    public static final int[] useCooldowns = new int[] {
        4, 4, -1, -1, 4, 4, 4, 4, -1, -1, 4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
    };
    public static final int[] gainCooldowns = new int[] {
        20, 40, 300, -1, 80, 80, 60, 60, 100, 720, 360, -1, 420, 600, 600, 600, 600, 600, 600, 600, 600, 600, 600, 600, 600, 600, 600, 600, 600, 600, 720, 720, 720, 720, 720, 720, 720, 720, 720, 720, 80, 80, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 180, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        120, -1, 240, -1, 200, 200, 200, 200, 200, 240, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        90, 180, 120, 360, 1440, 60, 180, 90, 240, 90, 240, 60, 180, 60, 180, 300, 180, 60, 180, 180, 180, 30, 150, 150, 60, 150, 90, 150, 30, 240, 240, 240, 30, 180, 60, 60, 60, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        480, 480, 480, 480, 480, 480, 480, 480, 480, 480, -1, 600, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
    };
    public static final short[][] selections = new short[][] {{SNOWBALL, EGG, ENDER_PEARL, FIRE_CHARGE, EXPLOSION_CHARGE, WIND_CHARGE, DRAGON_CHARGE, WITHER_CHARGE, SHULKER_BULLET, MUNDANE_SPLASH_POTION, AWKWARD_SPLASH_POTION, THICK_SPLASH_POTION, AWKWARD_LINGERING_POTION, THICK_LINGERING_POTION, SPECTRAL_ARROW, AWKWARD_TIPPED_ARROW, THICK_TIPPED_ARROW, FIREWORK_ROCKET}, {TNT, END_CRYSTAL, FISHING_ROD, WATER_BUCKET, LAVA_BUCKET, POWDER_SNOW_BUCKET, COBWEB}, {CARROT, APPLE, MELON_SLICE, BEEF, PORKCHOP, MUTTON, SALMON, RABBIT_STEW, BEETROOT_SOUP, CHICKEN, MUSHROOM_STEW, POTATO, BREAD, COD, RABBIT, BEETROOT, PUMPKIN_PIE, CHORUS_FRUIT, DRIED_KELP, HONEY_BOTTLE, COOKIE, SWEET_BERRIES, GLOW_BERRIES, MILK_BUKET}, {MUNDANE_POTION, AWKWARD_POTION, SHIELD, TOTEM_OF_UNDYING}};
    public static final short[][] upgrades = new short[][] {
        {}, {}, {}, {}, {LIGHTNING_CHARGE}, {}, {ICE_CHARGE}, {}, {}, {}, {}, {}, {}, {SPLASH_POTION_OF_HASTE, SPLASH_POTION_OF_LEAPING, SPLASH_POTION_OF_SWIFTNESS, SPLASH_POTION_OF_HEALING, SPLASH_POTION_OF_REGENERATION, SPLASH_POTION_OF_STRENGTH}, {}, {}, {}, {}, {}, {}, {SPLASH_POTION_OF_LEVITATION, SPLASH_POTION_OF_SLOW_FALLING}, {}, {}, {SPLASH_POTION_OF_ATTACK_FATIGUE, SPLASH_POTION_OF_BLINDNESS, SPLASH_POTION_OF_SLOWNESS, SPLASH_POTION_OF_HARMING, SPLASH_POTION_OF_POISON, SPLASH_POTION_OF_WEAKNESS}, {}, {}, {}, {}, {}, {}, {LINGERING_POTION_OF_LEVITATION, LINGERING_POTION_OF_SLOW_FALLING}, {}, {}, {LINGERING_POTION_OF_ATTACK_FATIGUE, LINGERING_POTION_OF_BLINDNESS, LINGERING_POTION_OF_SLOWNESS, LINGERING_POTION_OF_HARMING, LINGERING_POTION_OF_POISON, LINGERING_POTION_OF_WEAKNESS}, {}, {}, {}, {}, {}, {}, {}, {}, {TIPPED_ARROW_OF_LEVITATION, TIPPED_ARROW_OF_SLOW_FALLING}, {}, {}, {TIPPED_ARROW_OF_ATTACK_FATIGUE, TIPPED_ARROW_OF_BLINDNESS, TIPPED_ARROW_OF_SLOWNESS, TIPPED_ARROW_OF_HARMING, TIPPED_ARROW_OF_POISON, TIPPED_ARROW_OF_WEAKNESS}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {},
        {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {},
        {GOLDEN_CARROT}, {}, {GOLDEN_APPLE}, {}, {}, {GLISTERING_MELON_SLICE}, {}, {COOKED_BEEF}, {}, {COOKED_PORKCHOP}, {}, {COOKED_MUTTON}, {}, {COOKED_SALMON}, {}, {}, {}, {COOKED_CHICKEN}, {}, {}, {}, {BAKED_POTATO}, {}, {}, {COOKED_COD}, {}, {COOKED_RABBIT}, {}, {}, {}, {POPPED_CHORUS_FRUIT}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {},
        {POTION_OF_HASTE, POTION_OF_LEAPING, POTION_OF_SWIFTNESS, POTION_OF_HEALING, POTION_OF_REGENERATION, POTION_OF_STRENGTH}, {}, {}, {}, {}, {}, {}, {POTION_OF_LEVITATION, POTION_OF_SLOW_FALLING}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}
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
            meta.lore(removeItalics(Arrays.asList(Component.text("可以提供饥饿值和饱和度的物品", NamedTextColor.DARK_GREEN), enterOnClick)));
        });
        artifactCategorySelectors[CATEGORY_DEFENSES_AND_BUFFS / 64].editMeta(meta -> {
            meta.itemName(Component.text("防御与增益", NamedTextColor.BLUE));
            meta.lore(removeItalics(Arrays.asList(Component.text("可以用于防御或提供增益效果的物品", NamedTextColor.DARK_AQUA), enterOnClick)));
        });
        final ItemStack[] artifacts0 = new ItemStack[] {
            new ItemStack(Material.SNOWBALL, 16), new ItemStack(Material.EGG, 16), new ItemStack(Material.ENDER_PEARL, 4), new ItemStack(Material.ENDER_EYE, 4), new ItemStack(Material.FIRE_CHARGE, 3), new ItemStack(Material.FIRE_CHARGE, 3), new ItemStack(Material.FIRE_CHARGE, 2), new ItemStack(Material.FIRE_CHARGE, 2), new ItemStack(Material.WIND_CHARGE, 4), new ItemStack(Material.FIRE_CHARGE, 1), new ItemStack(Material.WITHER_SKELETON_SKULL, 3), null, new ItemStack(Material.SHULKER_SHELL, 1), new ItemStack(Material.SPLASH_POTION), new ItemStack(Material.SPLASH_POTION), new ItemStack(Material.SPLASH_POTION), new ItemStack(Material.SPLASH_POTION), new ItemStack(Material.SPLASH_POTION), new ItemStack(Material.SPLASH_POTION), new ItemStack(Material.SPLASH_POTION), new ItemStack(Material.SPLASH_POTION), new ItemStack(Material.SPLASH_POTION), new ItemStack(Material.SPLASH_POTION), new ItemStack(Material.SPLASH_POTION), new ItemStack(Material.SPLASH_POTION), new ItemStack(Material.SPLASH_POTION), new ItemStack(Material.SPLASH_POTION), new ItemStack(Material.SPLASH_POTION), new ItemStack(Material.SPLASH_POTION), new ItemStack(Material.SPLASH_POTION), new ItemStack(Material.LINGERING_POTION), new ItemStack(Material.LINGERING_POTION), new ItemStack(Material.LINGERING_POTION), new ItemStack(Material.LINGERING_POTION), new ItemStack(Material.LINGERING_POTION), new ItemStack(Material.LINGERING_POTION), new ItemStack(Material.LINGERING_POTION), new ItemStack(Material.LINGERING_POTION), new ItemStack(Material.LINGERING_POTION), new ItemStack(Material.LINGERING_POTION), new ItemStack(Material.SPECTRAL_ARROW, 8), new ItemStack(Material.SPECTRAL_ARROW, 8), new ItemStack(Material.TIPPED_ARROW, 8), new ItemStack(Material.TIPPED_ARROW, 8), new ItemStack(Material.TIPPED_ARROW, 8), new ItemStack(Material.TIPPED_ARROW, 8), new ItemStack(Material.TIPPED_ARROW, 8), new ItemStack(Material.TIPPED_ARROW, 8), new ItemStack(Material.TIPPED_ARROW, 8), new ItemStack(Material.TIPPED_ARROW, 8), new ItemStack(Material.TIPPED_ARROW, 8), new ItemStack(Material.TIPPED_ARROW, 8), new ItemStack(Material.FIREWORK_ROCKET, 3), null, null, null, null, null, null, null, null, null, null, null,
            new ItemStack(Material.TNT, 4), null, new ItemStack(Material.END_CRYSTAL), new ItemStack(Material.FISHING_ROD), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.PUFFERFISH_BUCKET), new ItemStack(Material.AXOLOTL_BUCKET), new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.POWDER_SNOW_BUCKET), new ItemStack(Material.COBWEB, 2), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            new ItemStack(Material.CARROT, 4), new ItemStack(Material.GOLDEN_CARROT, 1), new ItemStack(Material.APPLE, 8), new ItemStack(Material.GOLDEN_APPLE, 1), new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1), new ItemStack(Material.MELON_SLICE, 8), new ItemStack(Material.GLISTERING_MELON_SLICE, 1), new ItemStack(Material.BEEF, 8), new ItemStack(Material.COOKED_BEEF, 2), new ItemStack(Material.PORKCHOP, 8), new ItemStack(Material.COOKED_PORKCHOP, 2), new ItemStack(Material.MUTTON, 8), new ItemStack(Material.COOKED_MUTTON, 2), new ItemStack(Material.SALMON, 16), new ItemStack(Material.COOKED_SALMON, 2), new ItemStack(Material.RABBIT_STEW, 1), new ItemStack(Material.BEETROOT_SOUP, 1), new ItemStack(Material.CHICKEN, 8), new ItemStack(Material.COOKED_CHICKEN, 4), new ItemStack(Material.MUSHROOM_STEW, 1), new ItemStack(Material.SUSPICIOUS_STEW, 1), new ItemStack(Material.POTATO, 8), new ItemStack(Material.BAKED_POTATO, 4), new ItemStack(Material.BREAD, 4), new ItemStack(Material.COD, 16), new ItemStack(Material.COOKED_COD, 4), new ItemStack(Material.RABBIT, 8), new ItemStack(Material.COOKED_RABBIT, 4), new ItemStack(Material.BEETROOT, 4), new ItemStack(Material.PUMPKIN_PIE, 8), new ItemStack(Material.CHORUS_FRUIT, 8), new ItemStack(Material.POPPED_CHORUS_FRUIT, 8), new ItemStack(Material.DRIED_KELP, 8), new ItemStack(Material.HONEY_BOTTLE, 16), new ItemStack(Material.COOKIE, 16), new ItemStack(Material.SWEET_BERRIES, 8), new ItemStack(Material.GLOW_BERRIES, 16), new ItemStack(Material.MILK_BUCKET, 1), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            new ItemStack(Material.POTION), new ItemStack(Material.POTION), new ItemStack(Material.POTION), new ItemStack(Material.POTION), new ItemStack(Material.POTION), new ItemStack(Material.POTION), new ItemStack(Material.POTION), new ItemStack(Material.POTION), new ItemStack(Material.POTION), new ItemStack(Material.POTION), new ItemStack(Material.SHIELD), new ItemStack(Material.TOTEM_OF_UNDYING), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        };
        //for(short i = 0; i < 256; i++) {
        //    System.out.print(i + " ");
        //    System.out.println(artifacts0[i] == null ? "null" : artifacts0[i].getType());
        //    System.out.println(gainCooldowns[i]);
        //}
        for(int i = 0; i < artifacts0.length; i++) {
            artifacts[i][0] = new ItemStack(Material.GRAY_DYE);
            artifacts[i][1] = artifacts0[i];
        }
        for(int i = 0; i < 2; i++) {
            artifacts[SNOWBALL][i].editMeta(meta -> meta.itemName(Component.text("雪球", NamedTextColor.WHITE)));
            artifacts[EGG][i].editMeta(meta -> meta.itemName(Component.text("鸡蛋", NamedTextColor.WHITE)));
            artifacts[ENDER_PEARL][i].editMeta(meta -> meta.itemName(Component.text("末影珍珠", NamedTextColor.WHITE)));
            artifacts[FIRE_CHARGE][i].editMeta(meta -> meta.itemName(Component.text("火焰弹", NamedTextColor.WHITE)));
            artifacts[LIGHTNING_CHARGE][i].editMeta(meta -> {
                meta.itemName(Component.text("闪电弹", NamedTextColor.AQUA));
                meta.setCustomModelData(1);
            });
            artifacts[EXPLOSION_CHARGE][i].editMeta(meta -> {
                meta.itemName(Component.text("爆破弹", NamedTextColor.WHITE));
                //meta.setCustomModelData(2);
            });
            artifacts[ICE_CHARGE][i].editMeta(meta -> {
                meta.itemName(Component.text("冰弹", NamedTextColor.BLUE));
                meta.setCustomModelData(3);
            });
            artifacts[DRAGON_CHARGE][i].editMeta(meta -> {
                meta.itemName(Component.text("龙息弹", NamedTextColor.DARK_PURPLE));
                meta.setCustomModelData(4);
            });
            artifacts[WITHER_CHARGE][i].editMeta(meta -> meta.itemName(Component.text("凋零之首", NamedTextColor.DARK_GRAY)));
            artifacts[SHULKER_BULLET][i].editMeta(meta -> meta.itemName(Component.text("潜影导弹", NamedTextColor.YELLOW)));
            artifacts[WIND_CHARGE][i].editMeta(meta -> meta.itemName(Component.text("风弹", NamedTextColor.WHITE)));
            artifacts[MUNDANE_SPLASH_POTION][i].editMeta(meta -> {
                meta.itemName(Component.text("喷溅型平凡的药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.setBasePotionType(PotionType.MUNDANE);
                }
            });
            artifacts[SPLASH_POTION_OF_HASTE][i].editMeta(meta -> {
                meta.itemName(Component.text("喷溅型急迫药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.HASTE, 900, 2), false);
                    potionMeta.setColor(Color.fromRGB(14270531));
                }
            });
            artifacts[SPLASH_POTION_OF_LEAPING][i].editMeta(meta -> {
                meta.itemName(Component.text("喷溅型跳跃药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 900, 1), false);
                    potionMeta.setColor(Color.fromRGB(16646020));
                }
            });
            artifacts[SPLASH_POTION_OF_SWIFTNESS][i].editMeta(meta -> {
                meta.itemName(Component.text("喷溅型迅捷药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 900, 1), false);
                    potionMeta.setColor(Color.fromRGB(3402751));
                }
            });
            artifacts[SPLASH_POTION_OF_HEALING][i].editMeta(meta -> {
                meta.itemName(Component.text("喷溅型治疗药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, 1, 1), false);
                    potionMeta.setColor(Color.fromRGB(16262179));
                }
            });
            artifacts[SPLASH_POTION_OF_REGENERATION][i].editMeta(meta -> {
                meta.itemName(Component.text("喷溅型恢复药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 225, 1), false);
                    potionMeta.setColor(Color.fromRGB(13458603));
                }
            });
            artifacts[SPLASH_POTION_OF_STRENGTH][i].editMeta(meta -> {
                meta.itemName(Component.text("喷溅型力量药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.STRENGTH, 450, 0), false);
                    potionMeta.setColor(Color.fromRGB(16762624));
                }
            });
            artifacts[AWKWARD_SPLASH_POTION][i].editMeta(meta -> {
                meta.itemName(Component.text("喷溅型粗制的药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.setBasePotionType(PotionType.AWKWARD);
                }
            });
            artifacts[SPLASH_POTION_OF_LEVITATION][i].editMeta(meta -> {
                meta.itemName(Component.text("喷溅型漂浮药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 1), false);
                    potionMeta.setColor(Color.fromRGB(13565951));
                }
            });
            artifacts[SPLASH_POTION_OF_SLOW_FALLING][i].editMeta(meta -> {
                meta.itemName(Component.text("喷溅型缓降药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 900, 0), false);
                    potionMeta.setColor(Color.fromRGB(15978425));
                }
            });
            artifacts[THICK_SPLASH_POTION][i].editMeta(meta -> {
                meta.itemName(Component.text("喷溅型浓稠的药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.setBasePotionType(PotionType.THICK);
                }
            });
            artifacts[SPLASH_POTION_OF_ATTACK_FATIGUE][i].editMeta(meta -> {
                meta.itemName(Component.text("喷溅型攻击疲劳药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 900, 2), false);
                    potionMeta.setColor(Color.fromRGB(4866583));
                }
            });
            artifacts[SPLASH_POTION_OF_BLINDNESS][i].editMeta(meta -> {
                meta.itemName(Component.text("喷溅型失明药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 900, 0), false);
                    potionMeta.setColor(Color.fromRGB(2039587));
                }
            });
            artifacts[SPLASH_POTION_OF_SLOWNESS][i].editMeta(meta -> {
                meta.itemName(Component.text("喷溅型迟缓药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOWNESS, 900, 1), false);
                    potionMeta.setColor(Color.fromRGB(9154528));
                }
            });
            artifacts[SPLASH_POTION_OF_HARMING][i].editMeta(meta -> {
                meta.itemName(Component.text("喷溅型伤害药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 1, 1), false);
                    potionMeta.setColor(Color.fromRGB(11101546));
                }
            });
            artifacts[SPLASH_POTION_OF_POISON][i].editMeta(meta -> {
                meta.itemName(Component.text("喷溅型剧毒药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 225, 1), false);
                    potionMeta.setColor(Color.fromRGB(8889187));
                }
            });
            artifacts[SPLASH_POTION_OF_WEAKNESS][i].editMeta(meta -> {
                meta.itemName(Component.text("喷溅型虚弱药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.WEAKNESS, 450, 0), false);
                    potionMeta.setColor(Color.fromRGB(4738376));
                }
            });
            artifacts[AWKWARD_LINGERING_POTION][i].editMeta(meta -> {
                meta.itemName(Component.text("滞留型粗制的药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.setBasePotionType(PotionType.AWKWARD);
                }
            });
            artifacts[LINGERING_POTION_OF_LEVITATION][i].editMeta(meta -> {
                meta.itemName(Component.text("滞留型漂浮药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 1), false);
                    potionMeta.setColor(Color.fromRGB(13565951));
                }
            });
            artifacts[LINGERING_POTION_OF_SLOW_FALLING][i].editMeta(meta -> {
                meta.itemName(Component.text("滞留型缓降药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 900, 0), false);
                    potionMeta.setColor(Color.fromRGB(15978425));
                }
            });
            artifacts[THICK_LINGERING_POTION][i].editMeta(meta -> {
                meta.itemName(Component.text("滞留型浓稠的药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.setBasePotionType(PotionType.THICK);
                }
            });
            artifacts[LINGERING_POTION_OF_ATTACK_FATIGUE][i].editMeta(meta -> {
                meta.itemName(Component.text("滞留型攻击疲劳药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 900, 2), false);
                    potionMeta.setColor(Color.fromRGB(4866583));
                }
            });
            artifacts[LINGERING_POTION_OF_BLINDNESS][i].editMeta(meta -> {
                meta.itemName(Component.text("滞留型失明药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 900, 0), false);
                    potionMeta.setColor(Color.fromRGB(2039587));
                }
            });
            artifacts[LINGERING_POTION_OF_SLOWNESS][i].editMeta(meta -> {
                meta.itemName(Component.text("滞留型迟缓药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOWNESS, 900, 1), false);
                    potionMeta.setColor(Color.fromRGB(9154528));
                }
            });
            artifacts[LINGERING_POTION_OF_HARMING][i].editMeta(meta -> {
                meta.itemName(Component.text("滞留型伤害药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 1, 1), false);
                    potionMeta.setColor(Color.fromRGB(11101546));
                }
            });
            artifacts[LINGERING_POTION_OF_POISON][i].editMeta(meta -> {
                meta.itemName(Component.text("滞留型剧毒药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 225, 1), false);
                    potionMeta.setColor(Color.fromRGB(8889187));
                }
            });
            artifacts[LINGERING_POTION_OF_WEAKNESS][i].editMeta(meta -> {
                meta.itemName(Component.text("滞留型虚弱药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.WEAKNESS, 450, 0), false);
                    potionMeta.setColor(Color.fromRGB(4738376));
                }
            });
            artifacts[SPECTRAL_ARROW][i].editMeta(meta -> {
                meta.itemName(Component.text("光灵箭", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            });
            artifacts[SEEKER_ARROW][i].editMeta(meta -> {
                meta.itemName(Component.text("溯魂箭", NamedTextColor.YELLOW));
                meta.lore(removeItalics(Arrays.asList(Component.text("被束缚在箭头上的灵魂将箭矢引向目标，使其微微发光。", NamedTextColor.GRAY))));
            });
            artifacts[AWKWARD_TIPPED_ARROW][i].editMeta(meta -> {
                meta.itemName(Component.text("粗制之箭", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.setBasePotionType(PotionType.AWKWARD);
                }
            });
            artifacts[TIPPED_ARROW_OF_LEVITATION][i].editMeta(meta -> {
                meta.itemName(Component.text("漂浮之箭", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 1), false);
                    potionMeta.setColor(Color.fromRGB(13565951));
                }
            });
            artifacts[TIPPED_ARROW_OF_SLOW_FALLING][i].editMeta(meta -> {
                meta.itemName(Component.text("缓降之箭", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 900, 0), false);
                    potionMeta.setColor(Color.fromRGB(15978425));
                }
            });
            artifacts[THICK_TIPPED_ARROW][i].editMeta(meta -> {
                meta.itemName(Component.text("浓稠之箭", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.setBasePotionType(PotionType.THICK);
                }
            });
            artifacts[TIPPED_ARROW_OF_ATTACK_FATIGUE][i].editMeta(meta -> {
                meta.itemName(Component.text("攻击疲劳之箭", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 900, 2), false);
                    potionMeta.setColor(Color.fromRGB(4866583));
                }
            });
            artifacts[TIPPED_ARROW_OF_BLINDNESS][i].editMeta(meta -> {
                meta.itemName(Component.text("失明之箭", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 900, 0), false);
                    potionMeta.setColor(Color.fromRGB(2039587));
                }
            });
            artifacts[TIPPED_ARROW_OF_SLOWNESS][i].editMeta(meta -> {
                meta.itemName(Component.text("迟缓之箭", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOWNESS, 900, 1), false);
                    potionMeta.setColor(Color.fromRGB(9154528));
                }
            });
            artifacts[TIPPED_ARROW_OF_HARMING][i].editMeta(meta -> {
                meta.itemName(Component.text("伤害之箭", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 1, 1), false);
                    potionMeta.setColor(Color.fromRGB(11101546));
                }
            });
            artifacts[TIPPED_ARROW_OF_POISON][i].editMeta(meta -> {
                meta.itemName(Component.text("剧毒之箭", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 225, 1), false);
                    potionMeta.setColor(Color.fromRGB(8889187));
                }
            });
            artifacts[TIPPED_ARROW_OF_WEAKNESS][i].editMeta(meta -> {
                meta.itemName(Component.text("虚弱之箭", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.WEAKNESS, 450, 0), false);
                    potionMeta.setColor(Color.fromRGB(4738376));
                }
            });
            artifacts[FIREWORK_ROCKET][i].editMeta(meta -> {
                meta.itemName(Component.text("烟花火箭", NamedTextColor.WHITE));
                if(meta instanceof FireworkMeta fireworkMeta) {
                    fireworkMeta.setPower(0);
                    fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL).withColor(Color.WHITE).build());
                    fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL).withColor(Color.WHITE).build());
                }
            });
            /*
            artifacts[FIREWORK_ROCKET_SHADOW][i].editMeta(meta -> {
                meta.itemName(Component.text("烟花火箭", NamedTextColor.DARK_PURPLE));
                if(meta instanceof FireworkMeta fireworkMeta) {
                    fireworkMeta.setPower(0);
                    fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL).withColor(Color.PURPLE).withFade(Color.RED).build());
                    fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL).withColor(Color.PURPLE).withFade(Color.BLUE).build());
                }
            });
            artifacts[FIREWORK_ROCKET_TWINKLE][i].editMeta(meta -> {
                meta.itemName(Component.text("烟花火箭", NamedTextColor.YELLOW));
                if(meta instanceof FireworkMeta fireworkMeta) {
                    fireworkMeta.setPower(0);
                    fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL).withColor(Color.PURPLE).withFade(Color.RED).build());
                    fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL).withColor(Color.PURPLE).withFade(Color.BLUE).build());
                }
            });
            artifacts[FIREWORK_ROCKET_SHADOW][i].editMeta(meta -> {
                meta.itemName(Component.text("烟花火箭", NamedTextColor.DARK_PURPLE));
                if(meta instanceof FireworkMeta fireworkMeta) {
                    fireworkMeta.setPower(0);
                    fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL).withColor(Color.PURPLE).withFade(Color.RED).build());
                    fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL).withColor(Color.PURPLE).withFade(Color.BLUE).build());
                }
            });
            artifacts[FIREWORK_ROCKET_SHADOW][i].editMeta(meta -> {
                meta.itemName(Component.text("烟花火箭", NamedTextColor.DARK_PURPLE));
                if(meta instanceof FireworkMeta fireworkMeta) {
                    fireworkMeta.setPower(0);
                    fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL).withColor(Color.PURPLE).withFade(Color.RED).build());
                    fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL).withColor(Color.PURPLE).withFade(Color.BLUE).build());
                }
            });
            artifacts[FIREWORK_ROCKET_HEAVY][i].editMeta(meta -> {
                meta.itemName(Component.text("烟花火箭", NamedTextColor.BLUE));
                if(meta instanceof FireworkMeta fireworkMeta) {
                    fireworkMeta.setPower(0);
                    fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.BLUE).withFade(Color.RED).build());
                    fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.BLUE).withFade(Color.PURPLE).build());
                    fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.BLUE).withFade(Color.RED).build());
                    fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.BLUE).withFade(Color.PURPLE).build());
                    fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.BLUE).withFade(Color.BLUE).build());
                    fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.BLUE).withFade(Color.BLUE).build());
                    fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.BLUE).withFade(Color.BLUE).build());
                }
            });
             */
            artifacts[TNT][i].editMeta(meta -> {
                meta.itemName(Component.text("TNT", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("来啊，把它点着！还能出什么事不成？", NamedTextColor.GRAY))));
            });
            artifacts[TNT][i] = Utils.modifyItem(artifacts[TNT][i], "[{function:\"set_components\", components:{can_place_on:{}}}]");
            artifacts[END_CRYSTAL][i].editMeta(meta -> {
                meta.itemName(Component.text("末地水晶", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("末地水晶的力量将令敌人终生难忘。", NamedTextColor.GRAY))));
            });
            artifacts[END_CRYSTAL][i] = Utils.modifyItem(artifacts[END_CRYSTAL][i], "[{function:\"set_components\", components:{can_place_on:{}}}]");
            artifacts[FISHING_ROD][i].editMeta(meta -> {
                meta.itemName(Component.text("钓鱼竿", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("真正的冒险家都知道钓鱼竿是个好东西，它的作用可不仅仅是钓鱼。", NamedTextColor.GRAY))));
            });
            artifacts[WATER_BUCKET][i].editMeta(meta -> {
                meta.itemName(Component.text("水桶", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            });
            artifacts[WATER_BUCKET][i] = Utils.modifyItem(artifacts[WATER_BUCKET][i], "[{function:\"set_components\", components:{can_place_on:{}}}]");
            artifacts[LAVA_BUCKET][i].editMeta(meta -> {
                meta.itemName(Component.text("熔岩桶", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            });
            artifacts[LAVA_BUCKET][i] = Utils.modifyItem(artifacts[LAVA_BUCKET][i], "[{function:\"set_components\", components:{can_place_on:{}}}]");
            artifacts[POWDER_SNOW_BUCKET][i].editMeta(meta -> {
                meta.itemName(Component.text("细雪桶", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            });
            artifacts[POWDER_SNOW_BUCKET][i] = Utils.modifyItem(artifacts[POWDER_SNOW_BUCKET][i], "[{function:\"set_components\", components:{can_place_on:{}}}]");
            artifacts[COBWEB][i].editMeta(meta -> {
                meta.itemName(Component.text("蜘蛛网", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            });
            artifacts[COBWEB][i] = Utils.modifyItem(artifacts[COBWEB][i], "[{function:\"set_components\", components:{can_place_on:{}}}]");
            artifacts[CARROT][i].editMeta(meta -> meta.itemName(Component.text("胡萝卜", NamedTextColor.WHITE)));
            artifacts[GOLDEN_CARROT][i].editMeta(meta -> meta.itemName(Component.text("金胡萝卜", NamedTextColor.WHITE)));
            artifacts[APPLE][i].editMeta(meta -> meta.itemName(Component.text("苹果", NamedTextColor.WHITE)));
            artifacts[GOLDEN_APPLE][i].editMeta(meta -> {
                meta.setRarity(ItemRarity.UNCOMMON);
                meta.itemName(Component.text("金苹果", NamedTextColor.YELLOW));
            });
            artifacts[ENCHANTED_GOLDEN_APPLE][i].editMeta(meta -> meta.itemName(Component.text("附魔金苹果", NamedTextColor.LIGHT_PURPLE)));
            artifacts[MELON_SLICE][i].editMeta(meta -> meta.itemName(Component.text("西瓜片", NamedTextColor.WHITE)));
            artifacts[GLISTERING_MELON_SLICE][i].editMeta(meta -> {
                meta.setRarity(ItemRarity.UNCOMMON);
                meta.itemName(Component.text("闪烁的西瓜片", NamedTextColor.YELLOW));
                final FoodComponent food = meta.getFood();
                food.addEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 0), 1f);
                food.setCanAlwaysEat(true);
                food.setEatSeconds(1.6f);
                food.setNutrition(2);
                food.setSaturation(4.8f);
                food.setUsingConvertsTo(null);
                meta.setFood(food);
            });
            artifacts[BEEF][i].editMeta(meta -> meta.itemName(Component.text("生牛肉", NamedTextColor.WHITE)));
            artifacts[COOKED_BEEF][i].editMeta(meta -> meta.itemName(Component.text("牛排", NamedTextColor.WHITE)));
            artifacts[PORKCHOP][i].editMeta(meta -> meta.itemName(Component.text("生猪排", NamedTextColor.WHITE)));
            artifacts[COOKED_PORKCHOP][i].editMeta(meta -> meta.itemName(Component.text("熟猪排", NamedTextColor.WHITE)));
            artifacts[MUTTON][i].editMeta(meta -> meta.itemName(Component.text("生羊肉", NamedTextColor.WHITE)));
            artifacts[COOKED_MUTTON][i].editMeta(meta -> meta.itemName(Component.text("熟羊肉", NamedTextColor.WHITE)));
            artifacts[SALMON][i].editMeta(meta -> meta.itemName(Component.text("生鲑鱼", NamedTextColor.WHITE)));
            artifacts[COOKED_SALMON][i].editMeta(meta -> meta.itemName(Component.text("熟鲑鱼", NamedTextColor.WHITE)));
            artifacts[RABBIT_STEW][i].editMeta(meta -> {
                meta.setMaxStackSize(4);
                meta.itemName(Component.text("兔肉煲", NamedTextColor.WHITE));
            });
            artifacts[RABBIT_STEW][i].setAmount(4);
            artifacts[BEETROOT_SOUP][i].editMeta(meta -> {
                meta.setMaxStackSize(4);
                meta.itemName(Component.text("甜菜汤", NamedTextColor.WHITE));
            });
            artifacts[BEETROOT_SOUP][i].setAmount(4);
            artifacts[CHICKEN][i].editMeta(meta -> meta.itemName(Component.text("生鸡肉", NamedTextColor.WHITE)));
            artifacts[COOKED_CHICKEN][i].editMeta(meta -> meta.itemName(Component.text("熟鸡肉", NamedTextColor.WHITE)));
            artifacts[MUSHROOM_STEW][i].editMeta(meta -> {
                meta.setMaxStackSize(4);
                meta.itemName(Component.text("蘑菇煲", NamedTextColor.WHITE));
            });
            artifacts[MUSHROOM_STEW][i].setAmount(4);
            artifacts[SUSPICIOUS_STEW][i].editMeta(meta -> {
                meta.setMaxStackSize(4);
                meta.itemName(Component.text("迷之炖菜", NamedTextColor.WHITE));
            });
            artifacts[SUSPICIOUS_STEW][i].setAmount(4);
            artifacts[POTATO][i].editMeta(meta -> meta.itemName(Component.text("马铃薯", NamedTextColor.WHITE)));
            artifacts[BAKED_POTATO][i].editMeta(meta -> meta.itemName(Component.text("烤马铃薯", NamedTextColor.WHITE)));
            artifacts[BREAD][i].editMeta(meta -> meta.itemName(Component.text("面包", NamedTextColor.WHITE)));
            artifacts[COD][i].editMeta(meta -> meta.itemName(Component.text("生鳕鱼", NamedTextColor.WHITE)));
            artifacts[COOKED_COD][i].editMeta(meta -> meta.itemName(Component.text("熟鳕鱼", NamedTextColor.WHITE)));
            artifacts[RABBIT][i].editMeta(meta -> meta.itemName(Component.text("生兔肉", NamedTextColor.WHITE)));
            artifacts[COOKED_RABBIT][i].editMeta(meta -> meta.itemName(Component.text("熟兔肉", NamedTextColor.WHITE)));
            artifacts[BEETROOT][i].editMeta(meta -> meta.itemName(Component.text("甜菜根", NamedTextColor.WHITE)));
            artifacts[PUMPKIN_PIE][i].editMeta(meta -> meta.itemName(Component.text("南瓜派", NamedTextColor.WHITE)));
            artifacts[CHORUS_FRUIT][i].editMeta(meta -> meta.itemName(Component.text("紫颂果", NamedTextColor.WHITE)));
            artifacts[POPPED_CHORUS_FRUIT][i].editMeta(meta -> {
                meta.setRarity(ItemRarity.UNCOMMON);
                meta.itemName(Component.text("爆裂紫颂果", NamedTextColor.DARK_PURPLE));
                final FoodComponent food = meta.getFood();
                food.setCanAlwaysEat(true);
                food.setEatSeconds(1.6f);
                food.setNutrition(4);
                food.setSaturation(2.4f);
                food.setUsingConvertsTo(null);
                meta.setFood(food);
            });
            artifacts[DRIED_KELP][i].editMeta(meta -> meta.itemName(Component.text("干海带", NamedTextColor.WHITE)));
            artifacts[HONEY_BOTTLE][i].editMeta(meta -> meta.itemName(Component.text("蜂蜜瓶", NamedTextColor.WHITE)));
            artifacts[COOKIE][i].editMeta(meta -> meta.itemName(Component.text("曲奇", NamedTextColor.WHITE)));
            artifacts[SWEET_BERRIES][i].editMeta(meta -> meta.itemName(Component.text("甜浆果", NamedTextColor.WHITE)));
            artifacts[SWEET_BERRIES][i] = Utils.modifyItem(artifacts[SWEET_BERRIES][i], "[{function:\"set_components\", components:{can_place_on:{}}}]");
            artifacts[GLOW_BERRIES][i].editMeta(meta -> meta.itemName(Component.text("发光浆果", NamedTextColor.WHITE)));
            artifacts[MILK_BUKET][i].editMeta(meta -> meta.itemName(Component.text("奶桶", NamedTextColor.WHITE)));
            artifacts[MUNDANE_POTION][i].editMeta(meta -> {
                meta.itemName(Component.text("平凡的药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.setBasePotionType(PotionType.MUNDANE);
                }
            });
            artifacts[POTION_OF_HASTE][i].editMeta(meta -> {
                meta.itemName(Component.text("急迫药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.HASTE, 900, 2), false);
                    potionMeta.setColor(Color.fromRGB(14270531));
                }
            });
            artifacts[POTION_OF_LEAPING][i].editMeta(meta -> {
                meta.itemName(Component.text("跳跃药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 900, 1), false);
                    potionMeta.setColor(Color.fromRGB(16646020));
                }
            });
            artifacts[POTION_OF_SWIFTNESS][i].editMeta(meta -> {
                meta.itemName(Component.text("迅捷药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 900, 1), false);
                    potionMeta.setColor(Color.fromRGB(3402751));
                }
            });
            artifacts[POTION_OF_HEALING][i].editMeta(meta -> {
                meta.itemName(Component.text("治疗药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, 1, 1), false);
                    potionMeta.setColor(Color.fromRGB(16262179));
                }
            });
            artifacts[POTION_OF_REGENERATION][i].editMeta(meta -> {
                meta.itemName(Component.text("恢复药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 225, 1), false);
                    potionMeta.setColor(Color.fromRGB(13458603));
                }
            });
            artifacts[POTION_OF_STRENGTH][i].editMeta(meta -> {
                meta.itemName(Component.text("力量药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.STRENGTH, 450, 0), false);
                    potionMeta.setColor(Color.fromRGB(16762624));
                }
            });
            artifacts[AWKWARD_POTION][i].editMeta(meta -> {
                meta.itemName(Component.text("粗制的药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.setBasePotionType(PotionType.AWKWARD);
                }
            });
            artifacts[POTION_OF_LEVITATION][i].editMeta(meta -> {
                meta.itemName(Component.text("漂浮药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 1), false);
                    potionMeta.setColor(Color.fromRGB(13565951));
                }
            });
            artifacts[POTION_OF_SLOW_FALLING][i].editMeta(meta -> {
                meta.itemName(Component.text("缓降药水", NamedTextColor.WHITE));
                if(meta instanceof PotionMeta potionMeta) {
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 900, 0), false);
                    potionMeta.setColor(Color.fromRGB(15978425));
                }
            });
            artifacts[SHIELD][i].editMeta(meta -> meta.itemName(Component.text("盾牌", NamedTextColor.WHITE)));
            artifacts[TOTEM_OF_UNDYING][i].editMeta(meta -> meta.itemName(Component.text("不死图腾", NamedTextColor.YELLOW)));
            for(final short[] j = new short[]{0}; j[0] < artifacts.length; j[0]++) {
                if(artifacts[j[0]][i] != null) {
                    artifacts[j[0]][i].editMeta(meta -> {
                        meta.setUnbreakable(true);
                        meta.addItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE);
                        if(!meta.hasRarity()) {
                            meta.setRarity(ItemRarity.COMMON);
                        }
                        if(gainCooldowns[j[0]] != -1) {
                            Component gainCooldownDescription = Component.text("冷却时长：" + gainCooldowns[j[0]] / 20, NamedTextColor.DARK_GREEN);
                            if(meta.hasLore()) {
                                List<Component> lore = new ArrayList<>(meta.lore());
                                lore.add(gainCooldownDescription);
                                meta.lore(removeItalics(lore));
                            } else {
                                meta.lore(removeItalics(Arrays.asList(gainCooldownDescription)));
                            }
                        }
                        meta.getPersistentDataContainer().set(Utils.artifactIDKey, PersistentDataType.SHORT, j[0]);
                    });
                }
            }
        }
    }
    public static int getPersistenceTime(Material material) {
        switch(material) {
            case WATER, WATER_BUCKET, PUFFERFISH_BUCKET -> {
                return 200;
            }
            case LAVA, LAVA_BUCKET -> {
                return 120;
            }
            case POWDER_SNOW, POWDER_SNOW_BUCKET -> {
                return 280;
            }
            case COBWEB -> {
                return 250;
            }
            case SWEET_BERRY_BUSH, SWEET_BERRIES -> {
                return 100;
            }
        }
        return 0;
    }
}
