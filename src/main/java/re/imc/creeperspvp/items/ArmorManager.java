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
    public static final int PHANTOM_ARMOR = 0;
    public static final int CLIMBING_GEAR = 2;
    public static final int GOAT_GEAR = 3;
    public static final int BATTLE_ROBE = 4;
    public static final int SPLENDID_ROBE = 5;
    public static final int THIEF_ARMOR = 6;
    public static final int SWIFT_ARMOR = 8;
    public static final int PRISMARINE_ARMOR = 10;
    public static final int GHAST_ARMOR = 14;
    public static final int SNOWY_ARMOR = 16;
    public static final int CREEPER_ARMOR = 18;
    public static final int MERCENARY_ARMOR = 20;
    public static final int RENEGADE_ARMOR = 21;
    public static final int TURTLE_ARMOR = 22;
    public static final int GUARDS_ARMOR = 24;
    public static final int SCALE_ARMOR = 26;
    public static final int SHULKER_ARMOR = 28;
    public static final ItemStack[][] armor = new ItemStack[][] {
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.ELYTRA), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.ELYTRA), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.DIAMOND_BOOTS)},
        {new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)},
        {new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.CHAINMAIL_BOOTS)},
        {new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.CHAINMAIL_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.CHAINMAIL_BOOTS)},
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.CHAINMAIL_BOOTS)},
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
        {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.NETHERITE_LEGGINGS), new ItemStack(Material.NETHERITE_BOOTS)},
        {new ItemStack(Material.DIAMOND_HELMET), new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.DIAMOND_BOOTS)},
        {new ItemStack(Material.NETHERITE_HELMET), new ItemStack(Material.NETHERITE_CHESTPLATE), new ItemStack(Material.NETHERITE_LEGGINGS), new ItemStack(Material.NETHERITE_BOOTS)},
    };
    public static final PotionEffect[][] effects = new PotionEffect[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {new PotionEffect(PotionEffectType.CONDUIT_POWER, -1, 0, false, false, true)}, {new PotionEffect(PotionEffectType.CONDUIT_POWER, -1, 0, false, false, true), new PotionEffect(PotionEffectType.DOLPHINS_GRACE, -1, 0, false, false, true)}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}};
    public static final ItemStack[][] selectors = new ItemStack[][] {{new ItemStack(Material.GRAY_DYE), new ItemStack(Material.ELYTRA)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.ELYTRA)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.IRON_BOOTS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.DIAMOND_BOOTS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.CHAINMAIL_BOOTS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.CHAINMAIL_BOOTS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.IRON_HELMET)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.IRON_HELMET)}, null, null, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.GOLDEN_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.GOLDEN_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_BOOTS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.LEATHER_BOOTS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.CREEPER_HEAD)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.CREEPER_HEAD)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.IRON_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.IRON_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.TURTLE_HELMET)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.TURTLE_HELMET)}, null, null, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.DIAMOND_LEGGINGS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.NETHERITE_LEGGINGS)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.DIAMOND_CHESTPLATE)}, {new ItemStack(Material.GRAY_DYE), new ItemStack(Material.NETHERITE_CHESTPLATE)}};
    public static final int[] selections = new int[] {PHANTOM_ARMOR, CLIMBING_GEAR, BATTLE_ROBE, THIEF_ARMOR, SWIFT_ARMOR, PRISMARINE_ARMOR, GHAST_ARMOR, SNOWY_ARMOR, CREEPER_ARMOR, MERCENARY_ARMOR, TURTLE_ARMOR, SCALE_ARMOR, SHULKER_ARMOR};
    public static final int[][] upgrades = new int[][] {{}, {}, {}, {}, {SPLENDID_ROBE}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {RENEGADE_ARMOR}, {}, {}, {}, {}, {}, {}, {}, {}, {}};
    public static final long[] prices = new long[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static final EquipmentSlot[] armorSlots = new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.BODY};
    private ArmorManager() {}
    static void init() {
        for(ItemStack armor : armor[PHANTOM_ARMOR]) {
            armor.editMeta(meta -> {
               meta.itemName(Component.text("幻翼盔甲", NamedTextColor.WHITE));
               meta.lore(removeItalics(Arrays.asList(Component.text("由幻翼膜制成的盔甲，给予穿戴者像蝠鲼一样滑翔的能力。", NamedTextColor.GRAY))));
            });
        }
        armor[PHANTOM_ARMOR][1].editMeta(meta -> meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST)));
        for(ItemStack armor : armor[CLIMBING_GEAR]) {
            armor.editMeta(meta -> {
                meta.itemName(Component.text("登山装", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件结实的护装非常适合对抗冰山上凛冽的寒风。", NamedTextColor.GRAY))));
                //((ArmorMeta) meta).setTrim(new ArmorTrim(TrimMaterial.LAPIS, TrimPattern.FLOW));
                meta.setEnchantmentGlintOverride(false);
            });
        }
        armor[CLIMBING_GEAR][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[1], "", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        });
        armor[CLIMBING_GEAR][3].editMeta(meta -> {
            meta.addEnchant(Enchantment.FEATHER_FALLING, 10, true);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[3], "", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[3], "", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
        });
        for(ItemStack armor : armor[BATTLE_ROBE]) {
            armor.editMeta(meta -> {
                meta.itemName(Component.text("战袍", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("战袍由奇厄教主法庭上杰出的唤魔者穿戴。", NamedTextColor.GRAY))));
                if(meta instanceof LeatherArmorMeta leatherArmorMeta) {
                    leatherArmorMeta.setColor(Color.BLACK);
                }
                ((ArmorMeta) meta).setTrim(new ArmorTrim(TrimMaterial.GOLD, TrimPattern.VEX));
            });
        }
        armor[BATTLE_ROBE][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[1], "", 0.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
        });
        armor[BATTLE_ROBE][2].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[2], "", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[2], "", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
        });
        for(ItemStack armor : armor[SPLENDID_ROBE]) {
            armor.editMeta(meta -> {
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("华丽长袍", NamedTextColor.AQUA));
                meta.lore(removeItalics(Arrays.asList(Component.text("卓著的华丽长袍是保护奇厄教主的重型灾厄武士穿戴的装备。", NamedTextColor.GRAY))));
                if(meta instanceof LeatherArmorMeta leatherArmorMeta) {
                    leatherArmorMeta.setColor(Color.BLACK);
                }
                ((ArmorMeta) meta).setTrim(new ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.VEX));
            });
        }
        armor[SPLENDID_ROBE][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[1], "", 0.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", 0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
        });
        armor[SPLENDID_ROBE][2].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[2], "", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[2], "", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
        });
        for(ItemStack armor : armor[THIEF_ARMOR]) {
            armor.editMeta(meta -> {
                meta.itemName(Component.text("窃贼盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这身轻便的盔甲闻起来有一股硫的味道。", NamedTextColor.GRAY))));
                if(meta instanceof LeatherArmorMeta leatherArmorMeta) {
                    leatherArmorMeta.setColor(Color.OLIVE);
                }
                ((ArmorMeta) meta).setTrim(new ArmorTrim(TrimMaterial.GOLD, TrimPattern.SENTRY));
            });
        }
        armor[THIEF_ARMOR][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[1], "", 0.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_BONUS_ATTRIBUTE_UUID, "", 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "", 0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
        });
        armor[THIEF_ARMOR][2].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[2], "", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[2], "", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
        });
        for(ItemStack armor : armor[SWIFT_ARMOR]) {
            armor.editMeta(meta -> {
                meta.itemName(Component.text("轻盈盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("只有懦夫才会穿这身盔甲——至少人们是这么说的。", NamedTextColor.GRAY))));
                ((ArmorMeta) meta).setTrim(new ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.WAYFINDER));
            });
        }
        armor[SWIFT_ARMOR][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[1], "", 1.7, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        });
        armor[SWIFT_ARMOR][3].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[2], "", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "", 0.33, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.FEET));
        });  
        });
        for(ItemStack armor : armor[PRISMARINE_ARMOR]) {
            armor.editMeta(ArmorMeta.class, meta -> {
                meta.itemName(Component.text("海晶盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
                meta.setTrim(new ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.TIDE));
            });
        }
        for(ItemStack armor : armor[MERCENARY_ARMOR]) {
            armor.editMeta(meta -> {
                meta.setRarity(ItemRarity.COMMON);
                meta.itemName(Component.text("雇佣兵盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("雇佣兵盔甲，为那些想省点钱的人所青睐，", NamedTextColor.GRAY), Component.text("虽然并不高级，但却足以完成任务。", NamedTextColor.GRAY))));
            });
        }
        armor[MERCENARY_ARMOR][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        });
        for(final int[] i = new int[] {0}; i[0] < 4; i[0]++) {
            armor[RENEGADE_ARMOR][i[0]].editMeta(meta -> {
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("叛节之甲", NamedTextColor.AQUA));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[i[0]], "", 1.25, AttributeModifier.Operation.ADD_NUMBER, armorSlots[i[0]]));
                meta.lore(removeItalics(Arrays.asList(Component.text("对那些被雇来保护村民免受灾厄威胁的佣兵来说，这件盔甲很划算。", NamedTextColor.GRAY))));
            });
        }
        armor[RENEGADE_ARMOR][0].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[0], "", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[0], "", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
        });
        armor[RENEGADE_ARMOR][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[1], "", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        });
        armor[RENEGADE_ARMOR][2].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[2], "", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[2], "", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
        });
        armor[RENEGADE_ARMOR][3].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[3], "", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[3], "", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
        });
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
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 9, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[1], "", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            });
            selectors[CLIMBING_GEAR][i].editMeta(meta -> {
                meta.addEnchant(Enchantment.FEATHER_FALLING, 10, true);
                meta.itemName(Component.text("登山装", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这件结实的护装非常适合对抗冰山上凛冽的寒风。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    //armorMeta.setTrim(new ArmorTrim(TrimMaterial.LAPIS, TrimPattern.FLOW));
                }
                meta.setEnchantmentGlintOverride(false);
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 9, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[1], "", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            });
            selectors[BATTLE_ROBE][i].editMeta(meta -> {
                meta.itemName(Component.text("战袍", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("战袍由奇厄教主法庭上杰出的唤魔者穿戴。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    ((LeatherArmorMeta) armorMeta).setColor(Color.BLACK);
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.GOLD, TrimPattern.VEX));
                }
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 11, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[1], "", 1.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            });
            selectors[SPLENDID_ROBE][i].editMeta(meta -> {
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("华丽长袍", NamedTextColor.DARK_PURPLE));
                meta.lore(removeItalics(Arrays.asList(Component.text("卓著的华丽长袍是保护奇厄教主的重型灾厄武士穿戴的装备。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    ((LeatherArmorMeta) armorMeta).setColor(Color.BLACK);
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.VEX));
                }
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 11, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[1], "", 1.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", 0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
            });
            selectors[THIEF_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("窃贼盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("这身轻便的盔甲闻起来有一股硫的味道。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    ((LeatherArmorMeta) armorMeta).setColor(Color.OLIVE);
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.GOLD, TrimPattern.SENTRY));
                }
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 11, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[1], "", 1.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_BONUS_ATTRIBUTE_UUID, "", 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "", 0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
            });
            selectors[SWIFT_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("轻盈盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("只有懦夫才会穿这身盔甲——至少人们是这么说的。", NamedTextColor.GRAY))));
                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.WAYFINDER));
                }
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 12, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[1], "", 1.7, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "", 0.33, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
                ;
            });
            selectors[MERCENARY_ARMOR][i].editMeta(meta -> {
                meta.itemName(Component.text("雇佣兵盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("雇佣兵盔甲，为那些想省点钱的人所青睐，", NamedTextColor.GRAY), Component.text("虽然并不高级，但却足以完成任务。", NamedTextColor.GRAY))));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 15, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[1], "", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            });
            selectors[RENEGADE_ARMOR][i].editMeta(meta -> {
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("叛节之甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("对那些被雇来保护村民免受灾厄威胁的佣兵来说，这件盔甲很划算。", NamedTextColor.GRAY))));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 15, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
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
