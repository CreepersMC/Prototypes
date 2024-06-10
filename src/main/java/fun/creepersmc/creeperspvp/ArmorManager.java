package fun.creepersmc.creeperspvp;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import java.util.Arrays;
import static fun.creepersmc.creeperspvp.ItemManager.*;
public final class ArmorManager {
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
    public static final ItemStack[] selectors = new ItemStack[] {new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_CHESTPLATE)};
    public static final int[] selections = new int[] {MERCENARY_ARMOR, BATTLE_ROBE};
    public static final int[] upgrades = new int[] {RENEGADE_ARMOR, -1, SPLENDID_ROBE, -1};
    private static final EquipmentSlot[] armorSlots = new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.BODY};
    private ArmorManager() {}
    public static void init() {
        for(ItemStack armor : armor[MERCENARY_ARMOR]) {
            armor.editMeta(meta -> {
                meta.setUnbreakable(true);
                meta.setRarity(ItemRarity.COMMON);
                meta.itemName(Component.text("雇佣兵盔甲", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("雇佣兵盔甲，为那些想省点钱的人所青睐，", NamedTextColor.GRAY), Component.text("虽然并不高级，但却足以完成任务。", NamedTextColor.GRAY))));
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            });
        }
        armor[MERCENARY_ARMOR][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        });
        for(final int[] i = new int[] {0}; i[0] < 4; i[0]++) {
            armor[RENEGADE_ARMOR][i[0]].editMeta(meta -> {
                meta.setUnbreakable(true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("叛节之甲", NamedTextColor.AQUA));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[i[0]], "", 1.25, AttributeModifier.Operation.ADD_NUMBER, armorSlots[i[0]]));
                meta.lore(removeItalics(Arrays.asList(Component.text("对那些被雇来保护村民免受灾厄威胁的佣兵来说，这件盔甲很划算。", NamedTextColor.GRAY))));
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
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
        for(ItemStack armor : armor[BATTLE_ROBE]) {
            armor.editMeta(meta -> {
                meta.setUnbreakable(true);
                meta.setRarity(ItemRarity.COMMON);
                meta.itemName(Component.text("战袍", NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("战袍由奇厄教主法庭上杰出的唤魔者穿戴。", NamedTextColor.GRAY))));
                if(meta instanceof LeatherArmorMeta leatherArmorMeta) {
                    leatherArmorMeta.setColor(Color.BLACK);
                }
                ((ArmorMeta) meta).setTrim(new ArmorTrim(TrimMaterial.GOLD, TrimPattern.VEX));
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ARMOR_TRIM);
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
                meta.setUnbreakable(true);
                meta.setRarity(ItemRarity.RARE);
                meta.itemName(Component.text("华丽长袍", NamedTextColor.AQUA));
                meta.lore(removeItalics(Arrays.asList(Component.text("卓著的华丽长袍是保护奇厄教主的重型灾厄武士穿戴的装备。", NamedTextColor.GRAY))));
                if(meta instanceof LeatherArmorMeta leatherArmorMeta) {
                    leatherArmorMeta.setColor(Color.BLACK);
                }
                ((ArmorMeta) meta).setTrim(new ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.VEX));
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ARMOR_TRIM);
            });
        }
        armor[SPLENDID_ROBE][1].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[1], "", 0.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", 0.56, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
        });
        armor[SPLENDID_ROBE][2].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[2], "", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[2], "", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
        });
        selectors[MERCENARY_ARMOR].editMeta(meta -> {
            meta.setRarity(ItemRarity.COMMON);
            meta.itemName(Component.text("雇佣兵盔甲", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("雇佣兵盔甲，为那些想省点钱的人所青睐，", NamedTextColor.GRAY), Component.text("虽然并不高级，但却足以完成任务。", NamedTextColor.GRAY))));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 15, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_TOUGHNESS_UUIDS[1], "", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        });
        selectors[RENEGADE_ARMOR].editMeta(meta -> {
            meta.setRarity(ItemRarity.RARE);
            meta.itemName(Component.text("叛节之甲", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("对那些被雇来保护村民免受灾厄威胁的佣兵来说，这件盔甲很划算。", NamedTextColor.GRAY))));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 15, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        });
        selectors[BATTLE_ROBE].editMeta(meta -> {
            meta.setRarity(ItemRarity.COMMON);
            meta.itemName(Component.text("战袍", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("战袍由奇厄教主法庭上杰出的唤魔者穿戴。", NamedTextColor.GRAY))));
            ((LeatherArmorMeta) meta).setColor(Color.BLACK);
            ((ArmorMeta) meta).setTrim(new ArmorTrim(TrimMaterial.GOLD, TrimPattern.VEX));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 11, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
            meta.addItemFlags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ARMOR_TRIM);
        });
        selectors[SPLENDID_ROBE].editMeta(meta -> {
            meta.setRarity(ItemRarity.RARE);
            meta.itemName(Component.text("华丽长袍", NamedTextColor.DARK_PURPLE));
            meta.lore(removeItalics(Arrays.asList(Component.text("卓著的华丽长袍是保护奇厄教主的重型灾厄武士穿戴的装备。", NamedTextColor.GRAY))));
            ((LeatherArmorMeta) meta).setColor(Color.BLACK);
            ((ArmorMeta) meta).setTrim(new ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.VEX));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(ARMOR_ATTRIBUTE_UUIDS[1], "", 11, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_BONUS_ATTRIBUTE_UUID, "", 0.5, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
            meta.addItemFlags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ARMOR_TRIM);
        });
    }
}
