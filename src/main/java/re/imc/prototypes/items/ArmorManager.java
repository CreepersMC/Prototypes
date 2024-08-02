package re.imc.prototypes.items;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import re.imc.prototypes.utils.Utils;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import static re.imc.prototypes.items.ItemManager.*;
//TODO find armor trims for creeper armor, ghast armor, snow armor, turtle armor
public final class ArmorManager {
    public static final short GHOSTLY_ARMOR = 0;
    public static final short GHOST_KINDLER = 1;
    public static final short PHANTOM_ARMOR = 2;
    public static final short DRAGON_ARMOR = 3;
    public static final short CLIMBING_GEAR = 4;
    public static final short GOAT_GEAR = 5;
    public static final short BATTLE_ROBE = 6;
    public static final short SPLENDID_ROBE = 7;
    public static final short THIEF_ARMOR = 8;
    public static final short SPIDER_ARMOR = 9;
    public static final short SWIFT_ARMOR = 10;
    public static final short EMBER_ROBES = 11;
    public static final short PRISMARINE_ARMOR = 12;
    public static final short NAUTILUS_ARMOR = 13;
    public static final short GRIM_ARMOR = 14;
    public static final short WITHER_ARMOR = 15;
    public static final short CREEPER_ARMOR = 16;
    public static final short CREEPY_ARMOR = 17;
    public static final short RED_MOOSHROOM_ARMOR = 18;
    public static final short BROWN_MOOSHROOM_ARMOR = 19;
    public static final short GHAST_ARMOR = 20;
    public static final short HUNGRY_HORROR = 21;
    public static final short SNOW_ARMOR = 22;
    public static final short FROST_ARMOR = 23;
    public static final short MERCENARY_ARMOR = 24;
    public static final short RENEGADE_ARMOR = 25;
    public static final short TURTLE_ARMOR = 26;
    public static final short NIMBLE_TURTLE_ARMOR = 27;
    public static final short GUARDS_ARMOR = 28;
    public static final short CURIOUS_ARMOR = 29;
    public static final short SCALE_ARMOR = 30;
    public static final short HIGHLAND_ARMOR = 31;
    public static final short DARK_ARMOR = 32;
    public static final short TITANS_SHROUD = 33;
    public static final short SHULKER_ARMOR = 34;
    public static final short STURDY_SHULKER_ARMOR = 35;
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
        {new ItemStack(Material.CREEPER_HEAD), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.CREEPER_HEAD), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.DIAMOND_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.DIAMOND_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.GOLDEN_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.GOLDEN_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.TURTLE_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.TURTLE_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.NETHERITE_CHESTPLATE), new ItemStack(Material.NETHERITE_LEGGINGS), new ItemStack(Material.CHAINMAIL_BOOTS)},
        {new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.NETHERITE_CHESTPLATE), new ItemStack(Material.NETHERITE_LEGGINGS), new ItemStack(Material.CHAINMAIL_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.DIAMOND_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.DIAMOND_BOOTS)},
        {new ItemStack(Material.NETHERITE_HELMET), new ItemStack(Material.NETHERITE_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.NETHERITE_HELMET), new ItemStack(Material.NETHERITE_CHESTPLATE), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.DIAMOND_BOOTS)},
        {new ItemStack(Material.DIAMOND_HELMET), new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.DIAMOND_BOOTS)},
        {new ItemStack(Material.NETHERITE_HELMET), new ItemStack(Material.NETHERITE_CHESTPLATE), new ItemStack(Material.NETHERITE_LEGGINGS), new ItemStack(Material.NETHERITE_BOOTS)}
    };
    public static final PotionEffect[][] effects = new PotionEffect[][] {{}, {new PotionEffect(PotionEffectType.INVISIBILITY, -1, 0, false, true, true)}, {}, {}, {}, {new PotionEffect(PotionEffectType.JUMP_BOOST, -1, 1, false, false, true)}, {}, {}, {}, {}, {}, {}, {new PotionEffect(PotionEffectType.CONDUIT_POWER, -1, 0, false, false, true)}, {new PotionEffect(PotionEffectType.CONDUIT_POWER, -1, 0, false, false, true), new PotionEffect(PotionEffectType.DOLPHINS_GRACE, -1, 0, false, false, true)}, {}, {}, {}, {new PotionEffect(PotionEffectType.INVISIBILITY, -1, 0, false, false, true)}, {}, {}, {new PotionEffect(PotionEffectType.SLOW_FALLING, -1, 0, false, false, true)}, {new PotionEffect(PotionEffectType.SLOW_FALLING, -1, 0, false, false, true)}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}};
    @SuppressWarnings("unchecked")
    public static final Consumer<Player>[] tasks = new Consumer[] {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, object -> {
        ((Player) object).setExhaustion(Float.NEGATIVE_INFINITY);
    }, object -> {//TODO sound effect
        Player player = (Player) object;
        player.setExhaustion(Float.NEGATIVE_INFINITY);
        if(player.getHealth() <= 5 && !player.hasPotionEffect(PotionEffectType.REGENERATION)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 160, 0));
        } else if(player.getFireTicks() > 40 && !player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 80, 0));
        } else {
            final Entity target = player.getTargetEntity(16, true);
            if(target != null && Math.abs(target.getY() - player.getY()) >= 1.5 && !player.hasPotionEffect(PotionEffectType.JUMP_BOOST)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 120, 0));
            } else {
                player.addPotionEffect(Utils.random.nextBoolean() ? new PotionEffect(PotionEffectType.SATURATION, 7, 0) : new PotionEffect(PotionEffectType.NIGHT_VISION, 100, 0));
            }
        }
    }, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
    public static final ItemStack[][] selectors = new ItemStack[][] {{new ItemStack(Material.GRAY_DYE), new ItemStack(Material.STRUCTURE_VOID)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.STRUCTURE_VOID)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.ELYTRA)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.ELYTRA)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.IRON_BOOTS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.IRON_BOOTS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.CHAINMAIL_BOOTS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.CHAINMAIL_LEGGINGS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.IRON_HELMET)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.IRON_HELMET)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.SKELETON_SKULL)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.WITHER_SKELETON_SKULL)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.CREEPER_HEAD)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.CREEPER_HEAD)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.GOLDEN_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.GOLDEN_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_BOOTS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_BOOTS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.IRON_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.IRON_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.TURTLE_HELMET)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.TURTLE_HELMET)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.NETHERITE_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.NETHERITE_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.DIAMOND_LEGGINGS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.NETHERITE_LEGGINGS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.NETHERITE_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.NETHERITE_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.DIAMOND_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.NETHERITE_CHESTPLATE)}};
    public static final short[] selections = new short[] {GHOSTLY_ARMOR, PHANTOM_ARMOR, CLIMBING_GEAR, BATTLE_ROBE, THIEF_ARMOR, SWIFT_ARMOR, PRISMARINE_ARMOR, GRIM_ARMOR, CREEPER_ARMOR, RED_MOOSHROOM_ARMOR, SNOW_ARMOR, MERCENARY_ARMOR, TURTLE_ARMOR, SCALE_ARMOR, GUARDS_ARMOR, DARK_ARMOR, SHULKER_ARMOR};
    public static final short[][] upgrades = new short[][] {{GHOST_KINDLER}, {}, {DRAGON_ARMOR}, {}, {GOAT_GEAR}, {}, {SPLENDID_ROBE}, {}, {SPIDER_ARMOR}, {}, {EMBER_ROBES}, {}, {NAUTILUS_ARMOR}, {}, {WITHER_ARMOR}, {}, {CREEPY_ARMOR}, {} , {BROWN_MOOSHROOM_ARMOR}, {}, {}, {}, {FROST_ARMOR}, {}, {RENEGADE_ARMOR}, {}, {NIMBLE_TURTLE_ARMOR}, {}, {CURIOUS_ARMOR}, {}, {HIGHLAND_ARMOR}, {}, {TITANS_SHROUD}, {}, {STURDY_SHULKER_ARMOR}, {}};
    public static final long[] prices = new long[] {2000, 4800, 2400, 4200, 1200, 3600, 1600, 3600, 1400, 4200, 1800, 3000, 1200, 3000, 2000, 4200, 2400, 3000, 2000, 4000, 0, 0, 1400, 3600, 0, 2000, 1800, 3600, 2100, 4000, 1900, 4000, 3000, 7000, 2000, 4000};
    private ArmorManager() {}
    static void init() {
        for(ItemStack armor : armor[PHANTOM_ARMOR]) {
            armor.editMeta(meta -> {
                meta.itemName(Component.text("幻翼盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("由幻翼膜制成的盔甲，给予穿戴者像蝠鲼一样滑翔的能力。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.LAPIS, TrimPattern.EYE));
                }
            });
        }
        addArmorAttributes(armor[PHANTOM_ARMOR], new double[] {2, 0, 5, 2}, new double[] {0, 0, 1, 0}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[DRAGON_ARMOR]) {
            armor.editMeta(meta -> {
                meta.itemName(Component.text("末影龙甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("防护一流？那是当然。精致华丽？本该如此。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.EYE));
                }
            });
        }
        addArmorAttributes(armor[DRAGON_ARMOR], new double[] {3, 0, 7, 2}, new double[] {0, 0, 2, 1}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[CLIMBING_GEAR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("登山装", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件结实的护装非常适合对抗冰山上凛冽的寒风。", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.FLOW));
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
                meta.setTrim(new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.FLOW));
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
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(Utils.armorBonusKey, 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
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
        armor[SPLENDID_ROBE][1].editMeta(meta -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(Utils.armorBonusKey, 0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST)));
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
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(Utils.armorBonusKey, 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, 0.08, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
        });
        addArmorAttributes(armor[THIEF_ARMOR], new double[] {1, 3, 5, 2}, new double[] {0, 0.5, 1, 0}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[SPIDER_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("蜘蛛盔甲", NamedTextColor.RED));
                meta.lore(removeItalics(Arrays.asList(Component.text("由顶级盗贼所作，灵感来源于敏捷的蜘蛛。", NamedTextColor.GRAY))));
                if(meta instanceof LeatherArmorMeta leatherArmorMeta) {
                    leatherArmorMeta.setColor(Color.BLACK);
                }
                meta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.SENTRY));
            });
        }
        armor[SPIDER_ARMOR][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(Utils.armorBonusKey, 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, 0.08, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
            List<Component> lore = meta.lore();
            lore.add(0, Component.text("25% 吸血光环", NamedTextColor.BLUE));
            meta.lore(removeItalics(lore));
            meta.getPersistentDataContainer().set(Utils.armorAuraIDKey, PersistentDataType.BYTE, Utils.ARMOR_AURA_LIFESTEAL);
            meta.getPersistentDataContainer().set(Utils.armorAuraDataKey, PersistentDataType.FLOAT, 0.25f);
        });
        addArmorAttributes(armor[SPIDER_ARMOR], new double[] {1, 3, 5, 2}, new double[] {0, 0.5, 1, 0}, new double[] {0, 0, 0, 0});
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
        armor[SWIFT_ARMOR][3].editMeta(meta -> meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, 0.33, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.FEET)));
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
        armor[EMBER_ROBES][3].editMeta(meta -> meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, 0.33, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.FEET)));
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
        for(ItemStack armor : armor[NAUTILUS_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("鹦鹉螺盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.TIDE));
            });
        }
        armor[NAUTILUS_ARMOR][3].editMeta(meta -> {
            meta.addEnchant(Enchantment.DEPTH_STRIDER, 3, true);
            meta.setEnchantmentGlintOverride(false);
        });
        addArmorAttributes(armor[NAUTILUS_ARMOR], new double[] {2, 6, 4, 1}, new double[] {0, 1, 0.8, 0}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[GRIM_ARMOR]) {
            armor.editMeta(meta -> {
                meta.itemName(Component.text("冷酷战甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("冷酷战甲为装备它的人和在战斗中面对它的人带来一种恐惧感。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.QUARTZ, TrimPattern.RIB));
                }
            });
        }
        armor[GRIM_ARMOR][0].editMeta(meta -> {
            List<Component> lore = meta.lore();
            lore.add(0, Component.text("25% 吸血光环", NamedTextColor.BLUE));
            meta.lore(removeItalics(lore));
            meta.getPersistentDataContainer().set(Utils.armorAuraIDKey, PersistentDataType.BYTE, Utils.ARMOR_AURA_LIFESTEAL);
            meta.getPersistentDataContainer().set(Utils.armorAuraDataKey, PersistentDataType.FLOAT, 0.25f);
        });
        addArmorAttributes(armor[GRIM_ARMOR], new double[] {0, 6, 5, 2}, new double[] {0, 1, 1, 0}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[WITHER_ARMOR]) {
            armor.editMeta(meta -> {
                meta.itemName(Component.text("凋零盔甲", NamedTextColor.DARK_RED));
                meta.lore(removeItalics(Arrays.asList(Component.text("用击杀敌人的肢体所制成的盔甲，可以有效震慑穿戴者的敌人。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.RIB));
                }
            });
        }
        armor[WITHER_ARMOR][0].editMeta(meta -> {
            List<Component> lore = meta.lore();
            lore.add(0, Component.text("40% 吸血光环", NamedTextColor.BLUE));
            meta.lore(removeItalics(lore));
            meta.getPersistentDataContainer().set(Utils.armorAuraIDKey, PersistentDataType.BYTE, Utils.ARMOR_AURA_LIFESTEAL);
            meta.getPersistentDataContainer().set(Utils.armorAuraDataKey, PersistentDataType.FLOAT, 0.4f);
        });
        addArmorAttributes(armor[WITHER_ARMOR], new double[] {0, 6, 5, 2}, new double[] {0, 1, 1, 0}, new double[] {0, 0, 0, 0});
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
        addArmorAttributes(armor[CREEPER_ARMOR], new double[] {0, 6, 5, 2}, new double[] {0, 1, 1, 0}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[CREEPY_ARMOR]) {
            armor.editMeta(meta -> {
                meta.itemName(Component.text("暗影行者", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("静音", NamedTextColor.GRAY), Component.text("穿上这身盔甲时，你会觉得敌人远被你甩在身后只能追着你的阴影。", NamedTextColor.GRAY))));
            });
        }
        armor[CREEPY_ARMOR][0].editMeta(meta -> {
            meta.addEnchant(Enchantment.BLAST_PROTECTION, 10, true);
            meta.setEnchantmentGlintOverride(false);
        });
        addArmorAttributes(armor[CREEPY_ARMOR], new double[] {0, 6, 5, 2}, new double[] {0, 1, 1, 0}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[RED_MOOSHROOM_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("哞菇盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件盔甲有的吃不完的零食！", NamedTextColor.GRAY), Component.text("哞？", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.QUARTZ, TrimPattern.RAISER));
            });
        }
        armor[RED_MOOSHROOM_ARMOR][1].editMeta(LeatherArmorMeta.class, meta -> meta.setColor(Color.RED));
        addArmorAttributes(armor[RED_MOOSHROOM_ARMOR], new double[] {3, 3, 5, 2}, new double[] {2, 0, 0, 0}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[BROWN_MOOSHROOM_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("棕色哞菇盔甲", NamedTextColor.GOLD));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件盔甲一直都有你需要的药水！（好吧，至少是它认为你需要的，不过这还是很有帮助。）", NamedTextColor.GRAY), Component.text("哞！", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.COPPER, TrimPattern.RAISER));
            });
        }
        armor[BROWN_MOOSHROOM_ARMOR][1].editMeta(LeatherArmorMeta.class, meta -> meta.setColor(Color.fromRGB(9859408)));
        addArmorAttributes(armor[BROWN_MOOSHROOM_ARMOR], new double[] {3, 3, 5, 2}, new double[] {2, 0, 0, 0}, new double[] {0, 0, 0, 0});
        for(ItemStack armor : armor[GHAST_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("恶魂盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("穿上这件披甲的人会感觉到空气中充满尖叫声。但这个声音是敌人的，还是你的？", NamedTextColor.GRAY))));
                //meta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.BOLT));
            });
        }
        armor[GHAST_ARMOR][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.67, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_SCALE, new AttributeModifier(Utils.armorBonusKey, 1, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlotGroup.CHEST));
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
        for(ItemStack armor : armor[FROST_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("冰霜盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件传奇盔甲由永不融化的冰块锻造而成，让穿戴者感觉仿佛与冬天融为一体。", NamedTextColor.GRAY))));
                //meta.setTrim(new ArmorTrim(TrimMaterial.IRON, TrimPattern.));
            });
        }
        armor[FROST_ARMOR][3].editMeta(LeatherArmorMeta.class, meta -> {
            meta.addEnchant(Enchantment.FROST_WALKER, 3, true);
            meta.setColor(Color.fromRGB(109, 155, 195));
            List<Component> lore = meta.lore();
            lore.add(0, Component.text("霜冻光环", NamedTextColor.BLUE));
            meta.lore(removeItalics(lore));
            meta.getPersistentDataContainer().set(Utils.armorAuraIDKey, PersistentDataType.BYTE, Utils.ARMOR_AURA_FREEZE);
            meta.getPersistentDataContainer().set(Utils.armorAuraDataKey, PersistentDataType.INTEGER, 80);
        });
        addArmorAttributes(armor[FROST_ARMOR], new double[] {2, 6, 5, 1}, new double[] {0, 1, 1, 0}, new double[] {0, 0, 0, 0});
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
        armor[TURTLE_ARMOR][0].editMeta(meta -> meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.15, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.HEAD)));
        addArmorAttributes(armor[TURTLE_ARMOR], new double[] {3, 6, 5, 2}, new double[] {1, 1, 1, 0}, new double[] {0.3, 0.1, 0.1, 0});
        for(ItemStack armor : armor[NIMBLE_TURTLE_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("轻灵龟甲", NamedTextColor.GREEN));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件盔甲是用生活在不定深度的海龟的结实（但出乎意料地轻）外壳制成的。", NamedTextColor.GRAY))));
            });
        }
        armor[NIMBLE_TURTLE_ARMOR][0].editMeta(meta -> {
            meta.addEnchant(Enchantment.RESPIRATION, 1, true);
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.15, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.HEAD));
        });
        armor[NIMBLE_TURTLE_ARMOR][3].editMeta(meta -> {
            meta.addEnchant(Enchantment.DEPTH_STRIDER, 3, true);
            meta.setEnchantmentGlintOverride(false);
        });
        addArmorAttributes(armor[NIMBLE_TURTLE_ARMOR], new double[] {3, 6, 5, 2}, new double[] {1, 1, 1, 0}, new double[] {0.3, 0.1, 0.1, 0});
        for(ItemStack armor : armor[GUARDS_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("守卫之甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("授予给世界守护者的盔甲。", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.IRON, TrimPattern.WARD));
            });
        }
        armor[GUARDS_ARMOR][1].editMeta(meta -> {
            meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 8, true);
            meta.setEnchantmentGlintOverride(false);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(Utils.armorBonusKey, -0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
        });
        addArmorAttributes(armor[GUARDS_ARMOR], new double[] {1, 8, 7, 1}, new double[] {0, 3, 3, 0}, new double[] {0, 0.2, 0.2, 0});
        for(ItemStack armor : armor[CURIOUS_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("奇特盔甲", NamedTextColor.DARK_PURPLE));
                meta.lore(removeItalics(Arrays.asList(Component.text("没有人知道这种奇怪的盔甲来自何方，但你似乎对它有种亲切感。", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.WARD));
            });
        }
        armor[CURIOUS_ARMOR][1].editMeta(meta -> {
            meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 8, true);
            meta.setEnchantmentGlintOverride(false);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(Utils.armorBonusKey, -0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
            List<Component> lore = meta.lore();
            lore.add(0, Component.text("潜影光环", NamedTextColor.BLUE));
            meta.lore(removeItalics(lore));
            meta.getPersistentDataContainer().set(Utils.armorAuraIDKey, PersistentDataType.BYTE, Utils.ARMOR_AURA_SHULKING);
        });
        addArmorAttributes(armor[CURIOUS_ARMOR], new double[] {1, 8, 7, 1}, new double[] {0, 3, 3, 0}, new double[] {0, 0.2, 0.2, 0});
        for(ItemStack armor : armor[SCALE_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("鳞甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件护甲的灵感来自于鱼鳞，在大海边制作完成。", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.LAPIS, TrimPattern.COAST));
            });
        }
        armor[SCALE_ARMOR][2].editMeta(meta -> meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.2, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.LEGS)));
        addArmorAttributes(armor[SCALE_ARMOR], new double[] {2, 6, 7, 2}, new double[] {0, 1, 3, 2}, new double[] {0, 0, 0.2, 0.1});
        for(ItemStack armor : armor[HIGHLAND_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.addEnchant(Enchantment.THORNS, 1, true);
                meta.itemName(Component.text("高地护甲", NamedTextColor.DARK_GREEN));
                meta.lore(removeItalics(Arrays.asList(Component.text("据说这件盔甲是由龙鳞制作的。", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.EMERALD, TrimPattern.COAST));
            });
        }
        armor[HIGHLAND_ARMOR][2].editMeta(meta -> meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.2, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.LEGS)));
        addArmorAttributes(armor[HIGHLAND_ARMOR], new double[] {2, 6, 7, 2}, new double[] {0, 1, 3, 2}, new double[] {0, 0, 0.2, 0.1});
        for(ItemStack armor : armor[DARK_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("暗黑盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("在锻造厂最黑暗的深处制造而成，是灾厄村民精锐部队的盔甲。", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.HOST));
            });
        }
        armor[DARK_ARMOR][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(Utils.armorBonusKey, 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.2, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.6, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
        });
        addArmorAttributes(armor[DARK_ARMOR], new double[] {3, 8, 5, 2}, new double[] {3, 3, 1, 0}, new double[] {0.2, 0.2, 0, 0});
        for(ItemStack armor : armor[TITANS_SHROUD]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("泰坦战甲", NamedTextColor.AQUA));
                meta.lore(removeItalics(Arrays.asList(Component.text("关于泰坦战甲的励志故事，已经在南瓜牧场上流传了好几代。", NamedTextColor.GRAY))));
            });
        }
        armor[TITANS_SHROUD][0].editMeta(ArmorMeta.class, meta -> meta.setTrim(new ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.HOST)));
        armor[TITANS_SHROUD][1].editMeta(ArmorMeta.class, meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(Utils.armorBonusKey, 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.2, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.7, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
            meta.setTrim(new ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.HOST));
        });
        armor[TITANS_SHROUD][2].editMeta(ArmorMeta.class, meta -> meta.setTrim(new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.HOST)));
        armor[TITANS_SHROUD][3].editMeta(ArmorMeta.class, meta -> meta.setTrim(new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.HOST)));
        addArmorAttributes(armor[TITANS_SHROUD], new double[] {3, 8, 7, 2}, new double[] {3, 3, 2, 2}, new double[] {0.2, 0.2, 0.1, 0.1});
        for(ItemStack armor : armor[SHULKER_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("潜影贝盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("潜影贝盔甲的硬度可以媲美坚硬的钻石。", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.SPIRE));
            });
        }
        armor[SHULKER_ARMOR][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
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
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
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
            selectors[GHOSTLY_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("幽灵盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("隐身(潜行时)", NamedTextColor.GRAY), Component.text("身着幽灵盔甲的人有时会感觉到自己的身体与物理世界短暂隔绝，但很快就被唤回了现实。", NamedTextColor.GRAY))));
            });
            selectors[GHOST_KINDLER][i].editMeta(meta -> {
                meta.itemName(Component.text("幽灵亲和者", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("隐身", NamedTextColor.GRAY), Component.text("这件诡异的斗篷会随穿戴者身形而贴身变化。", NamedTextColor.GRAY))));
            });
            selectors[PHANTOM_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("幻翼盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("由幻翼膜制成的盔甲，给予穿戴者像蝠鲼一样滑翔的能力。", NamedTextColor.GRAY))));
                addArmorAttributes(meta, 9, 1, 0);
            });
            selectors[DRAGON_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("末影龙甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("防护一流？那是当然。精致华丽？本该如此。", NamedTextColor.GRAY))));
                addArmorAttributes(meta, 12, 4, 0);
            });
            selectors[CLIMBING_GEAR][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.FEATHER_FALLING, 10, true);
                meta.setEnchantmentGlintOverride(false);
                meta.itemName(Component.text("登山装", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件结实的护装非常适合对抗冰山上凛冽的寒风。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.FLOW));
                }
                addArmorAttributes(meta, 9, 1, 1);
            });
            selectors[GOAT_GEAR][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.FEATHER_FALLING, 10, true);
                meta.setEnchantmentGlintOverride(false);
                meta.itemName(Component.text("山羊装", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("用山羊的毛皮制作而成，穿戴后会感觉到跳跃感犹如可以从一个山峰蹦到另一个山峰一样轻浮。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.FLOW));
                }
                addArmorAttributes(meta, 9, 1, 1);
            });
            selectors[BATTLE_ROBE][i].editMeta(meta -> {
                meta.itemName(Component.text("战袍", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("战袍由奇厄教主法庭上杰出的唤魔者穿戴。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    ((LeatherArmorMeta) armorMeta).setColor(Color.BLACK);
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.GOLD, TrimPattern.VEX));
                }
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(Utils.armorBonusKey, 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
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
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(Utils.armorBonusKey, 0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                addArmorAttributes(meta, 11, 1.5, 0);
            });
            selectors[THIEF_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("窃贼盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这身轻便的盔甲闻起来有一股硫的味道。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    ((LeatherArmorMeta) armorMeta).setColor(Color.OLIVE);
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.GOLD, TrimPattern.SENTRY));
                }
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(Utils.armorBonusKey, 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, 0.08, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                addArmorAttributes(meta, 11, 1.5, 0);
            });
            selectors[SPIDER_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("蜘蛛盔甲", NamedTextColor.RED));
                meta.lore(removeItalics(Arrays.asList(Component.text("25% 吸血光环", NamedTextColor.BLUE), Component.text("由顶级盗贼所作，灵感来源于敏捷的蜘蛛。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    ((LeatherArmorMeta) armorMeta).setColor(Color.BLACK);
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.SENTRY));
                }
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(Utils.armorBonusKey, 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, 0.08, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                addArmorAttributes(meta, 11, 1.5, 0);
            });
            selectors[SWIFT_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("轻盈盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("只有懦夫才会穿这身盔甲——至少人们是这么说的。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.WAYFINDER));
                }
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, 0.33, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                addArmorAttributes(meta, 12, 1.6, 0);
            });
            selectors[EMBER_ROBES][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.FIRE_PROTECTION, 10, true);
                meta.setEnchantmentGlintOverride(false);
                meta.itemName(Component.text("余烬长袍", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.WAYFINDER));
                }
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, 0.33, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                addArmorAttributes(meta, 12, 1.6, 0);
            });
            selectors[PRISMARINE_ARMOR][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.DEPTH_STRIDER, 3, true);
                meta.itemName(Component.text("海晶盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("潮涌能量", NamedTextColor.GRAY), Component.text("", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.TIDE));
                }
                meta.setEnchantmentGlintOverride(false);
                addArmorAttributes(meta, 13, 1.8, 0);
            });
            selectors[NAUTILUS_ARMOR][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.DEPTH_STRIDER, 3, true);
                meta.itemName(Component.text("鹦鹉螺盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("潮涌能量", NamedTextColor.GRAY), Component.text("海豚的恩惠", NamedTextColor.GRAY), Component.text("", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.TIDE));
                }
                meta.setEnchantmentGlintOverride(false);
                addArmorAttributes(meta, 13, 1.8, 0);
            });
            selectors[GRIM_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("冷酷战甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("25% 吸血光环", NamedTextColor.BLUE), Component.text("冷酷战甲为装备它的人和在战斗中面对它的人带来一种恐惧感。", NamedTextColor.GRAY))));
                addArmorAttributes(meta, 13, 2, 0);
            });
            selectors[WITHER_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("凋零盔甲", NamedTextColor.DARK_RED));
                meta.lore(removeItalics(Arrays.asList(Component.text("40% 吸血光环", NamedTextColor.BLUE), Component.text("用击杀敌人的肢体所制成的盔甲，可以有效震慑穿戴者的敌人。", NamedTextColor.GRAY))));
                addArmorAttributes(meta, 13, 2, 0);
            });
            selectors[CREEPER_ARMOR][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.BLAST_PROTECTION, 10, true);
                meta.setEnchantmentGlintOverride(false);
                meta.itemName(Component.text("苦力怕盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件盔甲以充满悲伤的目光凝视着敌人。", NamedTextColor.GRAY))));
                addArmorAttributes(meta, 13, 2, 0);
            });
            selectors[CREEPY_ARMOR][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.BLAST_PROTECTION, 10, true);
                meta.setEnchantmentGlintOverride(false);
                meta.itemName(Component.text("暗影行者", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("隐身", NamedTextColor.GRAY), Component.text("静音", NamedTextColor.GRAY), Component.text("当你穿上这身盔甲时，你会觉得敌人远被你甩在身后只能追着你的阴影。", NamedTextColor.GRAY))));
                addArmorAttributes(meta, 13, 2, 0);
            });
            selectors[RED_MOOSHROOM_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("哞菇盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件盔甲有的吃不完的零食！", NamedTextColor.GRAY), Component.text("哞？", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.QUARTZ, TrimPattern.RAISER));
                    if(meta instanceof LeatherArmorMeta leatherArmorMeta) {
                        leatherArmorMeta.setColor(Color.RED);
                    }
                }
                addArmorAttributes(meta, 13, 2, 0);
            });
            selectors[BROWN_MOOSHROOM_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("棕色哞菇盔甲", NamedTextColor.GOLD));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件盔甲一直都有你需要的药水！（好吧，至少是它认为你需要的，不过这还是很有帮助。）", NamedTextColor.GRAY), Component.text("哞！", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.COPPER, TrimPattern.RAISER));
                    if(meta instanceof LeatherArmorMeta leatherArmorMeta) {
                        leatherArmorMeta.setColor(Color.fromRGB(9859408));
                    }
                }
                addArmorAttributes(meta, 13, 2, 0);
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
                meta.addEnchant(Enchantment.FROST_WALKER, 1, true);
                meta.setEnchantmentGlintOverride(false);
                meta.itemName(Component.text("冰雪盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("一件经过融雪淬炼的盔甲，可保护穿戴者免受冰原的严寒。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    if(meta instanceof LeatherArmorMeta leatherArmorMeta) {
                        leatherArmorMeta.setColor(Color.fromRGB(109, 155, 195));
                    }
                }
                addArmorAttributes(meta, 14, 2, 0);
            });
            selectors[FROST_ARMOR][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.FROST_WALKER, 3, true);
                meta.setEnchantmentGlintOverride(false);
                meta.itemName(Component.text("冰霜盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("霜冻光环", NamedTextColor.BLUE), Component.text("这件传奇盔甲由永不融化的冰块锻造而成，让穿戴者感觉仿佛与冬天融为一体。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    if(meta instanceof LeatherArmorMeta leatherArmorMeta) {
                        leatherArmorMeta.setColor(Color.fromRGB(109, 155, 195));
                    }
                }
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
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.15, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                addArmorAttributes(meta, 16, 5, 0.5);
            });
            selectors[NIMBLE_TURTLE_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("轻灵龟甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件盔甲是用生活在不定深度的海龟的结实（但出乎意料地轻）外壳制成的。", NamedTextColor.GRAY))));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.15, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                addArmorAttributes(meta, 16, 5, 0.5);
            });
            selectors[SCALE_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("鳞甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件护甲的灵感来自于鱼鳞，在大海边制作完成。", NamedTextColor.GRAY))));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.2, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.LAPIS, TrimPattern.COAST));
                }
                addArmorAttributes(meta, 17, 6, 0.3);
            });
            selectors[HIGHLAND_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("高地护甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("荆棘I x4", NamedTextColor.GRAY), Component.text("据说这件盔甲是由龙鳞制作的。", NamedTextColor.GRAY))));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.2, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.EMERALD, TrimPattern.COAST));
                }
                addArmorAttributes(meta, 17, 6, 0.3);
            });
            selectors[GUARDS_ARMOR][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 8, true);
                meta.setEnchantmentGlintOverride(false);
                meta.itemName(Component.text("守卫之甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("授予给世界守护者的盔甲。", NamedTextColor.GRAY))));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(Utils.armorBonusKey, -0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.IRON, TrimPattern.WARD));
                }
                addArmorAttributes(meta, 17, 6, 0.4);
            });
            selectors[CURIOUS_ARMOR][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 8, true);
                meta.setEnchantmentGlintOverride(false);
                meta.itemName(Component.text("奇特盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("潜影光环", NamedTextColor.BLUE), Component.text("没有人知道这种奇怪的盔甲来自何方，但你似乎对它有种亲切感。", NamedTextColor.GRAY))));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(Utils.armorBonusKey, -0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.WARD));
                }
                addArmorAttributes(meta, 17, 6, 0.4);
            });
            selectors[DARK_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("暗黑盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("在锻造厂最黑暗的深处制造而成，是灾厄村民精锐部队的盔甲。", NamedTextColor.GRAY))));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(Utils.armorBonusKey, 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.2, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.6, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.HOST));
                }
                addArmorAttributes(meta, 18, 7, 0.4);
            });
            selectors[TITANS_SHROUD][i].editMeta(meta -> {
                meta.itemName(Component.text("泰坦战甲", NamedTextColor.AQUA));
                meta.lore(removeItalics(Arrays.asList(Component.text("关于泰坦战甲的励志故事，已经在南瓜牧场上流传了好几代。", NamedTextColor.GRAY))));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(Utils.armorBonusKey, 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.2, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.7, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.HOST));
                }
                addArmorAttributes(meta, 20, 10, 0.6);
            });
            selectors[SHULKER_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("潜影贝盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("潜影贝盔甲的硬度可以媲美坚硬的钻石。", NamedTextColor.GRAY))));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.SPIRE));
                }
                addArmorAttributes(meta, 20, 8, 0.5);
            });
            selectors[STURDY_SHULKER_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("坚固潜影贝盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("他们说进攻就是最好的防守，但对于这个垒壳，大概只能祝你好运了。", NamedTextColor.GRAY))));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(Utils.armorBonusKey, -0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.CHEST));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.SPIRE));
                }
                addArmorAttributes(meta, 20, 12, 0.5);
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
