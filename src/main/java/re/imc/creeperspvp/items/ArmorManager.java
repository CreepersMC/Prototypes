package re.imc.creeperspvp.items;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.Arrays;
import java.util.UUID;
import static re.imc.creeperspvp.items.ItemManager.*;
public final class ArmorManager {
    public static final int GHOSTLY_ARMOR = 0;
    public static final int GHOST_KINDLER = 1;
    public static final int PHANTOM_ARMOR = 2;
    public static final int DRAGON_ARMOR = 3;
    public static final int CLIMBING_GEAR = 4;
    public static final int GOAT_GEAR = 5;
    public static final int BATTLE_ROBE = 6;
    public static final int SPLENDID_ROBE = 7;
    public static final int THIEF_ARMOR = 8;
    public static final int SPIDER_ARMOR = 9;
    public static final int SWIFT_ARMOR = 10;
    public static final int EMBER_ROBES = 11;
    public static final int PRISMARINE_ARMOR = 12;
    public static final int NAUTILUS_ARMOR = 13;
    public static final int GRIM_ARMOR = 14;
    public static final int WITHER_ARMOR = 15;
    public static final int RED_MOOSHROOM_ARMOR = 16;
    public static final int BROWN_MOOSHROOM_ARMOR = 17;
    public static final int GHAST_ARMOR = 18;
    public static final int HUNGRY_HORROR = 19;
    public static final int SNOW_ARMOR = 20;
    public static final int FROST_ARMOR = 21;
    public static final int CREEPER_ARMOR = 22;
    public static final int CREEPY_ARMOR = 23;
    public static final int MERCENARY_ARMOR = 24;
    public static final int RENEGADE_ARMOR = 25;
    public static final int TURTLE_ARMOR = 26;
    public static final int NIMBLE_TURTLE_ARMOR = 27;
    public static final int GUARDS_ARMOR = 28;
    public static final int CURIOUS_ARMOR = 29;
    public static final int SCALE_ARMOR = 30;
    public static final int HIGHLAND_ARMOR = 31;
    public static final int DARK_ARMOR = 32;
    public static final int TITANS_SHROUD = 33;
    public static final int SHULKER_ARMOR = 34;
    public static final int STURDY_SHULKER_ARMOR = 35;
    public static final ItemStack[][] armor = new ItemStack[][] {
        {null, null, null, null},
        {null, null, null, null},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.ELYTRA), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.DRAGON_HEAD), new ItemStack(Material.ELYTRA), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.DIAMOND_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.CHAINMAIL_BOOTS)},
        {new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.CHAINMAIL_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.CHAINMAIL_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.CHAINMAIL_BOOTS)},
        {new ItemStack(Material.SKELETON_SKULL), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.WITHER_SKELETON_SKULL), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {null, null, null, null},
        {null, null, null, null},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.GOLDEN_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.GOLDEN_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS)},
        {new ItemStack(Material.CREEPER_HEAD), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.CREEPER_HEAD), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.TURTLE_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.TURTLE_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {null, null, null, null},
        {null, null, null, null},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.DIAMOND_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.DIAMOND_BOOTS)},
        {new ItemStack(Material.NETHERITE_HELMET), new ItemStack(Material.NETHERITE_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.NETHERITE_HELMET), new ItemStack(Material.NETHERITE_CHESTPLATE), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.DIAMOND_LEGGINGS)},
        {new ItemStack(Material.DIAMOND_HELMET), new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.DIAMOND_BOOTS)},
        {new ItemStack(Material.NETHERITE_HELMET), new ItemStack(Material.NETHERITE_CHESTPLATE), new ItemStack(Material.NETHERITE_LEGGINGS), new ItemStack(Material.NETHERITE_BOOTS)}
    };
    public static final PotionEffect[][] effects = new PotionEffect[][] {{new PotionEffect(PotionEffectType.INVISIBILITY, -1, 0, false, false, true)}, {new PotionEffect(PotionEffectType.INVISIBILITY, -1, 0, false, false, true)}, {}, {}, {}, {new PotionEffect(PotionEffectType.JUMP_BOOST, -1, 1, false, false, true)}, {}, {}, {}, {}, {}, {}, {new PotionEffect(PotionEffectType.CONDUIT_POWER, -1, 0, false, false, true)}, {new PotionEffect(PotionEffectType.CONDUIT_POWER, -1, 0, false, false, true), new PotionEffect(PotionEffectType.DOLPHINS_GRACE, -1, 0, false, false, true)}, {}, {}, {}, {}, {new PotionEffect(PotionEffectType.SLOW_FALLING, -1, 0, false, false, true)}, {new PotionEffect(PotionEffectType.SLOW_FALLING, -1, 0, false, false, true)}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}};
    public static final ItemStack[][] selectors = new ItemStack[][] {{new ItemStack(Material.GRAY_DYE), new ItemStack(Material.STRUCTURE_VOID)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.STRUCTURE_VOID)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.ELYTRA)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.ELYTRA)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.IRON_BOOTS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.IRON_BOOTS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.CHAINMAIL_BOOTS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.CHAINMAIL_BOOTS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.IRON_HELMET)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.IRON_HELMET)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.SKELETON_SKULL)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.WITHER_SKELETON_SKULL)}, null, null, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.GOLDEN_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.GOLDEN_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_BOOTS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_BOOTS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.CREEPER_HEAD)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.CREEPER_HEAD)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.IRON_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.IRON_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.TURTLE_HELMET)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.TURTLE_HELMET)}, null, null, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.DIAMOND_LEGGINGS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.NETHERITE_LEGGINGS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.NETHERITE_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.NETHERITE_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.DIAMOND_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.NETHERITE_CHESTPLATE)}};
    public static final int[] selections = new int[] {GHOSTLY_ARMOR, PHANTOM_ARMOR, CLIMBING_GEAR, BATTLE_ROBE, THIEF_ARMOR, SWIFT_ARMOR, PRISMARINE_ARMOR, GRIM_ARMOR, GHAST_ARMOR, SNOW_ARMOR, CREEPER_ARMOR, MERCENARY_ARMOR, TURTLE_ARMOR, SCALE_ARMOR, DARK_ARMOR, SHULKER_ARMOR};
    public static final int[][] upgrades = new int[][] {{GHOST_KINDLER}, {}, {DRAGON_ARMOR}, {}, {GOAT_GEAR}, {}, {SPLENDID_ROBE}, {}, {SPIDER_ARMOR}, {}, {EMBER_ROBES}, {}, {NAUTILUS_ARMOR}, {}, {WITHER_ARMOR}, {}, {BROWN_MOOSHROOM_ARMOR}, {}, {HUNGRY_HORROR}, {}, {FROST_ARMOR}, {}, {CREEPY_ARMOR}, {}, {RENEGADE_ARMOR}, {}, {NIMBLE_TURTLE_ARMOR}, {}, {CURIOUS_ARMOR}, {}, {HIGHLAND_ARMOR}, {}, {TITANS_SHROUD}, {}, {STURDY_SHULKER_ARMOR}, {}};
    public static final long[] prices = new long[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private ArmorManager() {}
    static void init() {
        for(ItemStack armor : armor[PHANTOM_ARMOR]) {
            armor.editMeta(meta -> {
               meta.itemName(Component.text("幻翼盔甲", NamedTextColor.WHITE));
               meta.lore(removeItalics(Arrays.asList(Component.text("由幻翼膜制成的盔甲，给予穿戴者像蝠鲼一样滑翔的能力。", NamedTextColor.GRAY))));
            });
        }
        addArmorAttributes(armor[PHANTOM_ARMOR], new double[] {2, 0, 5, 2}, new double[] {0, 1, 0, 0}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[CLIMBING_GEAR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("登山装", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件结实的护装非常适合对抗冰山上凛冽的寒风。", NamedTextColor.GRAY))));
                //meta.setTrim(new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.FLOW));
            });
        }
        armor[CLIMBING_GEAR][3].editMeta(meta -> {
            meta.addEnchant(Enchantment.FEATHER_FALLING, 10, true);
            meta.setEnchantmentGlintOverride(false);
        });
        addArmorAttributes(armor[CLIMBING_GEAR], new double[] {2, 3, 2, 2}, new double[] {0, 1, 0, 0}, new double[] {0.2, 0.3, 0.3, 0.2});
        for(ItemStack armor : armor[GOAT_GEAR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("山羊装", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("用山羊的毛皮制作而成，穿戴后会感觉到跳跃感犹如可以从一个山峰蹦到另一个山峰一样轻浮。", NamedTextColor.GRAY))));
                //meta.setTrim(new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.FLOW));
            });
        }
        armor[GOAT_GEAR][3].editMeta(meta -> {
            meta.addEnchant(Enchantment.FEATHER_FALLING, 10, true);
            meta.setEnchantmentGlintOverride(false);
        });
        addArmorAttributes(armor[GOAT_GEAR], new double[] {2, 3, 2, 2}, new double[] {0, 1, 0, 0}, new double[] {0.2, 0.3, 0.3, 0.2});
        for(ItemStack armor : armor[BATTLE_ROBE]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("战袍", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("战袍由奇厄教主法庭上杰出的唤魔者穿戴。", NamedTextColor.GRAY))));
                if(meta instanceof LeatherArmorMeta leatherArmorMeta) {
                    leatherArmorMeta.setColor(Color.BLACK);
                }
                meta.setTrim(new ArmorTrim(TrimMaterial.GOLD, TrimPattern.VEX));
            });
        }
        armor[BATTLE_ROBE][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
        });
        addArmorAttributes(armor[BATTLE_ROBE], new double[] {1, 3, 5, 2}, new double[] {0, 0.5, 1, 0}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[SPLENDID_ROBE]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("华丽长袍", NamedTextColor.AQUA));
                meta.lore(removeItalics(Arrays.asList(Component.text("卓著的华丽长袍是保护奇厄教主的重型灾厄武士穿戴的装备。", NamedTextColor.GRAY))));
                if(meta instanceof LeatherArmorMeta leatherArmorMeta) {
                    leatherArmorMeta.setColor(Color.BLACK);
                }
                meta.setTrim(new ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.VEX));
            });
        }
        armor[SPLENDID_ROBE][1].editMeta(meta -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", 0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST)));
        addArmorAttributes(armor[SPLENDID_ROBE], new double[] {1, 3, 5, 2}, new double[] {0, 0.5, 1, 0}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[THIEF_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("窃贼盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这身轻便的盔甲闻起来有一股硫的味道。", NamedTextColor.GRAY))));
                if(meta instanceof LeatherArmorMeta leatherArmorMeta) {
                    leatherArmorMeta.setColor(Color.OLIVE);
                }
                meta.setTrim(new ArmorTrim(TrimMaterial.GOLD, TrimPattern.SENTRY));
            });
        }
        armor[THIEF_ARMOR][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_BONUS_ATTRIBUTE_UUID, "", 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "", 0.08, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
        });
        addArmorAttributes(armor[THIEF_ARMOR], new double[] {1, 3, 5, 2}, new double[] {0, 0.5, 1, 0}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[SWIFT_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("轻盈盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("只有懦夫才会穿这身盔甲——至少人们是这么说的。", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.WAYFINDER));
            });
        }
        armor[SWIFT_ARMOR][2].editMeta(meta -> {
            meta.addEnchant(Enchantment.SWIFT_SNEAK, 3, true);
            meta.setEnchantmentGlintOverride(false);
        });
        armor[SWIFT_ARMOR][3].editMeta(meta -> meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "", 0.33, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.FEET)));
        for(ItemStack armor : armor[EMBER_ROBES]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("余烬长袍", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("余烬长袍是唤魔者为了区别于其他灾厄村民而创造的。", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.COPPER, TrimPattern.WAYFINDER));
            });
        }
        armor[EMBER_ROBES][2].editMeta(meta -> {
            meta.addEnchant(Enchantment.SWIFT_SNEAK, 3, true);
            meta.addEnchant(Enchantment.FIRE_PROTECTION, 10, true);
            meta.setEnchantmentGlintOverride(false);
        });
        armor[EMBER_ROBES][3].editMeta(meta -> meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "", 0.33, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.FEET)));
        addArmorAttributes(armor[SWIFT_ARMOR], new double[] {2, 5, 4, 1}, new double[] {0, 0.8, 0.8, 0}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[PRISMARINE_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("海晶盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.TIDE));
            });
        }
        armor[PRISMARINE_ARMOR][3].editMeta(meta -> {
            meta.addEnchant(Enchantment.DEPTH_STRIDER, 3, true);
            meta.setEnchantmentGlintOverride(false);
        });
        addArmorAttributes(armor[PRISMARINE_ARMOR], new double[] {2, 6, 4, 1}, new double[] {0, 1, 0.8, 0}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[GHAST_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("恶魂盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("穿上这件披甲的人会感觉到空气中充满尖叫声。但这个声音是敌人的，还是你的？", NamedTextColor.GRAY))));
                //meta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.BOLT));
            });
        }
        armor[GHAST_ARMOR][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(ATTACK_SPEED_BONUS_ATTRIBUTE_UUID, "", -0.67, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
        });
        addArmorAttributes(armor[GHAST_ARMOR], new double[] {2, 5, 5, 2}, new double[] {0, 0.7, 1, 0}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[SNOW_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("冰雪盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("一件经过融雪淬炼的盔甲，可保护穿戴者免受冰原的严寒。", NamedTextColor.GRAY))));
                //meta.setTrim(new ArmorTrim(TrimMaterial.IRON, TrimPattern.));
            });
        }
        armor[SNOW_ARMOR][3].editMeta(LeatherArmorMeta.class, meta -> {
            meta.addEnchant(Enchantment.FROST_WALKER, 1, true);
            meta.setColor(Color.fromRGB(109, 155, 195));
        });
        addArmorAttributes(armor[SNOW_ARMOR], new double[] {2, 6, 5, 1}, new double[] {0, 1, 1, 0}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[CREEPER_ARMOR]) {
            armor.editMeta(meta -> {
                meta.itemName(Component.text("苦力怕盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件盔甲以充满悲伤的目光凝视着敌人。", NamedTextColor.GRAY))));
            });
        }
        armor[CREEPER_ARMOR][0].editMeta(meta -> {
            meta.addEnchant(Enchantment.BLAST_PROTECTION, 10, true);
            meta.setEnchantmentGlintOverride(false);
        });
        addArmorAttributes(armor[CREEPER_ARMOR], new double[] {1, 6, 5, 2}, new double[] {0, 1, 1, 0}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[MERCENARY_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.setRarity(ItemRarity.COMMON);
                meta.itemName(Component.text("雇佣兵盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("雇佣兵盔甲，为那些想省点钱的人所青睐，", NamedTextColor.GRAY), Component.text("虽然并不高级，但却足以完成任务。", NamedTextColor.GRAY))));
            });
        }
        addArmorAttributes(armor[MERCENARY_ARMOR], new double[] {2, 6, 5, 2}, new double[] {0, 2, 2, 0}, new double[] {0, 0, 0, 0});
        for(final int[] i = new int[] {0}; i[0] < 4; i[0]++) {
            armor[RENEGADE_ARMOR][i[0]].editMeta(ArmorMeta.class, meta -> {
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("叛节之甲", NamedTextColor.AQUA));
                meta.lore(removeItalics(Arrays.asList(Component.text("对那些被雇来保护村民免受灾厄威胁的佣兵来说，这件盔甲很划算。", NamedTextColor.GRAY))));
            });
        }
        addArmorAttributes(armor[RENEGADE_ARMOR], new double[] {2, 6, 5, 2}, new double[] {1, 2, 1, 1}, new double[] {0, 0.1, 0, 0});
        for(ItemStack armor : armor[TURTLE_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("龟甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("龟甲的灵感来自于乌龟坚韧且坚硬的外壳。", NamedTextColor.GRAY))));
            });
        }
        armor[TURTLE_ARMOR][0].editMeta(meta -> meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(ATTACK_SPEED_BONUS_ATTRIBUTE_UUID, "", -0.15, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HEAD)));
        addArmorAttributes(armor[TURTLE_ARMOR], new double[] {3, 6, 5, 2}, new double[] {1, 1, 1, 0}, new double[] {0.3, 0.1, 0.1, 0});
        for(ItemStack armor : armor[NIMBLE_TURTLE_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("轻灵龟甲", NamedTextColor.GREEN));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件盔甲是用生活在不定深度的海龟的结实（但出乎意料地轻）外壳制成的。", NamedTextColor.GRAY))));
            });
        }
        armor[NIMBLE_TURTLE_ARMOR][0].editMeta(meta -> {
            meta.addEnchant(Enchantment.RESPIRATION, 1, true);
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(ATTACK_SPEED_BONUS_ATTRIBUTE_UUID, "", -0.15, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HEAD));
        });
        armor[NIMBLE_TURTLE_ARMOR][3].editMeta(meta -> {
            meta.addEnchant(Enchantment.DEPTH_STRIDER, 3, true);
            meta.setEnchantmentGlintOverride(false);
        });
        addArmorAttributes(armor[NIMBLE_TURTLE_ARMOR], new double[] {3, 6, 5, 2}, new double[] {1, 1, 1, 0}, new double[] {0.3, 0.1, 0.1, 0});
        for(ItemStack armor : armor[SCALE_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("鳞甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件护甲的灵感来自于鱼鳞，在大海边制作完成。", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.LAPIS, TrimPattern.COAST));
            });
        }
        armor[SCALE_ARMOR][2].editMeta(meta -> meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(ATTACK_SPEED_BONUS_ATTRIBUTE_UUID, "", -0.2, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.LEGS)));
        addArmorAttributes(armor[SCALE_ARMOR], new double[] {2, 6, 7, 2}, new double[] {0, 1, 3, 2}, new double[] {0, 0, 0.2, 0.1});
        for(ItemStack armor : armor[HIGHLAND_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.addEnchant(Enchantment.THORNS, 1, true);
                meta.itemName(Component.text("高地护甲", NamedTextColor.DARK_GREEN));
                meta.lore(removeItalics(Arrays.asList(Component.text("据说这件盔甲是由龙鳞制作的。", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.EMERALD, TrimPattern.COAST));
            });
        }
        armor[HIGHLAND_ARMOR][2].editMeta(meta -> meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(ATTACK_SPEED_BONUS_ATTRIBUTE_UUID, "", -0.2, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.LEGS)));
        addArmorAttributes(armor[HIGHLAND_ARMOR], new double[] {2, 6, 7, 2}, new double[] {0, 1, 3, 2}, new double[] {0, 0, 0.2, 0.1});
        for(ItemStack armor : armor[DARK_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("暗黑盔甲", NamedTextColor.DARK_GREEN));
                meta.lore(removeItalics(Arrays.asList(Component.text("在锻造厂最黑暗的深处制造而成，是灾厄村民精锐部队的盔甲。", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.HOST));
            });
        }
        armor[DARK_ARMOR][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_ATTRIBUTE_UUID, "", -0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "", -0.6, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
        });
        addArmorAttributes(armor[DARK_ARMOR], new double[] {3, 8, 5, 2}, new double[] {3, 3, 1, 0}, new double[] {0.2, 0.2, 0, 0});
        for(ItemStack armor : armor[SHULKER_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("潜影贝盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("潜影贝盔甲的硬度可以媲美坚硬的钻石。", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.SPIRE));
            });
        }
        armor[SHULKER_ARMOR][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(ATTACK_SPEED_BONUS_ATTRIBUTE_UUID, "", -0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", -0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
        });
        addArmorAttributes(armor[SHULKER_ARMOR], new double[] {3, 8, 7, 2}, new double[] {2, 2, 2, 2}, new double[] {0.1, 0.2, 0.1, 0.1});
        for(ItemStack armor : armor[STURDY_SHULKER_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("坚固潜影贝盔甲", NamedTextColor.DARK_PURPLE));
                meta.lore(removeItalics(Arrays.asList(Component.text("他们说进攻就是最好的防守，但对于这个垒壳，大概只能祝你好运了。", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.SPIRE));
            });
        }
        armor[STURDY_SHULKER_ARMOR][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(ATTACK_SPEED_BONUS_ATTRIBUTE_UUID, "", -0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", -0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
        });
        addArmorAttributes(armor[STURDY_SHULKER_ARMOR], new double[] {3, 8, 7, 2}, new double[] {3, 3, 3, 3}, new double[] {0.1, 0.2, 0.2, 0.1});
        for(ItemStack[] armor : armor) {
            for(ItemStack armorPiece : armor) {
                if(armorPiece != null) {
                    armorPiece.editMeta(meta -> {
                        meta.setUnbreakable(true);
                        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ARMOR_TRIM);
                        if(!meta.hasRarity()) {
                            meta.setRarity(ItemRarity.COMMON);
                        }
                    });
                }
            }
        }
        for(int i = 0; i < 2; i++) {
            selectors[PHANTOM_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("幻翼盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("由幻翼膜制成的盔甲，给予穿戴者像蝠鲼一样滑翔的能力。", NamedTextColor.GRAY))));
                addArmorAttributes(meta, 9, 1, 0);
            });
            selectors[CLIMBING_GEAR][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.FEATHER_FALLING, 10, true);
                meta.itemName(Component.text("登山装", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件结实的护装非常适合对抗冰山上凛冽的寒风。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    //armorMeta.setTrim(new ArmorTrim(TrimMaterial.LAPIS, TrimPattern.FLOW));
                }
                meta.setEnchantmentGlintOverride(false);
                addArmorAttributes(meta, 9, 1, 1);
            });
            selectors[BATTLE_ROBE][i].editMeta(meta -> {
                meta.itemName(Component.text("战袍", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("战袍由奇厄教主法庭上杰出的唤魔者穿戴。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    ((LeatherArmorMeta) armorMeta).setColor(Color.BLACK);
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.GOLD, TrimPattern.VEX));
                }
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
                addArmorAttributes(meta, 11, 1.5, 0);
            });
            selectors[SPLENDID_ROBE][i].editMeta(meta -> {
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("华丽长袍", NamedTextColor.DARK_PURPLE));
                meta.lore(removeItalics(Arrays.asList(Component.text("卓著的华丽长袍是保护奇厄教主的重型灾厄武士穿戴的装备。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    ((LeatherArmorMeta) armorMeta).setColor(Color.BLACK);
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.VEX));
                }
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", 0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
                addArmorAttributes(meta, 11, 1.5, 0);
            });
            selectors[THIEF_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("窃贼盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这身轻便的盔甲闻起来有一股硫的味道。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    ((LeatherArmorMeta) armorMeta).setColor(Color.OLIVE);
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.GOLD, TrimPattern.SENTRY));
                }
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_BONUS_ATTRIBUTE_UUID, "", 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "", 0.08, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
                addArmorAttributes(meta, 11, 1.5, 0);
            });
            selectors[SWIFT_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("轻盈盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("只有懦夫才会穿这身盔甲——至少人们是这么说的。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.WAYFINDER));
                }
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "", 0.33, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
                addArmorAttributes(meta, 12, 1.6, 0);
            });
            selectors[PRISMARINE_ARMOR][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.DEPTH_STRIDER, 3, true);
                meta.itemName(Component.text("海晶盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("潮涌能量I", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.TIDE));
                }
                meta.setEnchantmentGlintOverride(false);
                addArmorAttributes(meta, 13, 1.8, 0);
            });
            selectors[GHAST_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("恶魂盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("穿上这件披甲的人会感觉到空气中充满尖叫声。但这个声音是敌人的，还是你的？", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    //armorMeta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.BOLT));
                }
                addArmorAttributes(meta, 14, 1.7, 0);
            });
            selectors[SNOW_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("冰雪盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("一件经过融雪淬炼的盔甲，可保护穿戴者免受冰原的严寒。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    if(meta instanceof LeatherArmorMeta leatherArmorMeta) {
                        leatherArmorMeta.setColor(Color.fromRGB(109, 155, 195));
                    }
                }
                addArmorAttributes(meta, 14, 2, 0);
            });
            selectors[CREEPER_ARMOR][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.BLAST_PROTECTION, 10, true);
                meta.setEnchantmentGlintOverride(false);
                meta.itemName(Component.text("苦力怕盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件盔甲以充满悲伤的目光凝视着敌人。", NamedTextColor.GRAY))));
                addArmorAttributes(meta, 14, 2, 0);
            });
            selectors[MERCENARY_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("雇佣兵盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("雇佣兵盔甲，为那些想省点钱的人所青睐，", NamedTextColor.GRAY), Component.text("虽然并不高级，但却足以完成任务。", NamedTextColor.GRAY))));
                addArmorAttributes(meta, 15, 2, 0);
            });
            selectors[RENEGADE_ARMOR][i].editMeta(meta -> {
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("叛节之甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("对那些被雇来保护村民免受灾厄威胁的佣兵来说，这件盔甲很划算。", NamedTextColor.GRAY))));
                addArmorAttributes(meta, 15, 5, 0.1);
            });
            selectors[TURTLE_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("龟甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("龟甲的灵感来自于乌龟坚韧且坚硬的外壳。", NamedTextColor.GRAY))));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(ATTACK_SPEED_BONUS_ATTRIBUTE_UUID, "", -0.15, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
                addArmorAttributes(meta, 16, 5, 0.5);
            });
            selectors[SCALE_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("鳞甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件护甲的灵感来自于鱼鳞，在大海边制作完成。", NamedTextColor.GRAY))));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(ATTACK_SPEED_BONUS_ATTRIBUTE_UUID, "", -0.2, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.LAPIS, TrimPattern.COAST));
                }
                addArmorAttributes(meta, 17, 6, 0.3);
            });
            selectors[DARK_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("暗黑盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("在锻造厂最黑暗的深处制造而成，是灾厄村民精锐部队的盔甲。", NamedTextColor.GRAY))));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_ATTRIBUTE_UUID, "", -0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "", -0.6, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.HOST));
                }
                addArmorAttributes(meta, 18, 7, 0.4);
            });
            selectors[SHULKER_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("潜影贝盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("潜影贝盔甲的硬度可以媲美坚硬的钻石。", NamedTextColor.GRAY))));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(ATTACK_SPEED_BONUS_ATTRIBUTE_UUID, "", -0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", -0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.SPIRE));
                }
                addArmorAttributes(meta, 20, 8, 0.5);
            });
            for(ItemStack[] selectors : selectors) {
                if(selectors != null) {
                    selectors[i].editMeta(meta -> {
                        meta.addItemFlags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ARMOR_TRIM);
                        if(!meta.hasRarity()) {
                            meta.setRarity(ItemRarity.COMMON);
                        }
                    });
                }
            }
        }
    }
}
