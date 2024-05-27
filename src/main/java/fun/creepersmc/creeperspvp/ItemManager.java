package fun.creepersmc.creeperspvp;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
public final class ItemManager {
    public static final ItemStack SWORD = new ItemStack(Material.IRON_SWORD);
    public static final ItemStack AXE = new ItemStack(Material.IRON_AXE);
    public static final ItemStack HIGHLAND_AXE = new ItemStack(Material.DIAMOND_AXE);
    public static final ItemStack MERCENARY_ARMOR_SELECTOR = new ItemStack(Material.IRON_CHESTPLATE);
    public static final ItemStack BORDER = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
    public static final ItemStack CLOSE = new ItemStack(Material.RED_STAINED_GLASS_PANE);
    private static final UUID ATTACK_DAMAGE_ATTRIBUTE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    private static final UUID ATTACK_SPEED_ATTRIBUTE_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
    private ItemManager() {}
    public static void init() {
        SWORD.editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.itemName(Component.text("剑"));
            meta.lore(removeItalics(List.of(Component.text("一把坚固且可靠的剑。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 7, 1.6);
        });
        AXE.editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.itemName(Component.text("斧"));
            meta.lore(removeItalics(List.of(Component.text("斧是一把十分有效的武器，深受卫道士的喜爱。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 9, 1);
        });
        HIGHLAND_AXE.editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.itemName(Component.text("高地斧"));
            meta.addEnchant(Enchantment.EFFICIENCY, 15, false);
            meta.lore(removeItalics(List.of(Component.text("破盾 III", NamedTextColor.GRAY), Component.text("高地之斧的制作工艺精湛，是一种光鲜的战争武器，也是一种大胆的反击手段。", NamedTextColor.GRAY))));
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            editMeleeAttributes(meta, 9.5, 1);
        });
        MERCENARY_ARMOR_SELECTOR.editMeta(meta -> {
            meta.itemName(Component.text("雇佣兵盔甲"));
            meta.lore(removeItalics(List.of(Component.text("便宜，但好用", NamedTextColor.GRAY))));
            displayArmorAttributes(meta, 15, 0, 0);
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
        final List<Component> meleeAttributeLore = removeItalics(List.of(Component.empty(), Component.text("在主手时：", NamedTextColor.GRAY), Component.text(" " + damage + " 攻击伤害", NamedTextColor.DARK_GREEN), Component.text(" " + speed + " 攻击速度", NamedTextColor.DARK_GREEN)));
        if(meta.hasLore()) {
            final List<Component> lore = meta.lore();
            lore.addAll(meleeAttributeLore);
            meta.lore(lore);
        } else {
            meta.lore(meleeAttributeLore);
        }
    }
    private static void displayArmorAttributes(ItemMeta meta, int armor, int armorToughness, int knockBackResistance) {
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ARMOR_TRIM, ItemFlag.HIDE_DYE);
        final ArrayList<Component> armorAttributeLore = new ArrayList<>(3);
        Collections.addAll(armorAttributeLore ,Component.empty(), Component.text("穿戴时：", NamedTextColor.GRAY));
        if(armor > 0) {
            armorAttributeLore.add(Component.text(armor + " 护甲值", NamedTextColor.BLUE));
        }
        if(armorToughness > 0) {
            armorAttributeLore.add(Component.text(armorToughness + " 盔甲韧性", NamedTextColor.BLUE));
        }
        if(knockBackResistance > 0) {
            armorAttributeLore.add(Component.text(knockBackResistance + " 击退抗性", NamedTextColor.BLUE));
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
        for(Component line : lore) {
            line.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
        }
        return lore;
    }
}
