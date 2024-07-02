package re.imc.creeperspvp.items;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import re.imc.creeperspvp.iui.IUIManager;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import re.imc.creeperspvp.utils.Utils;
import java.util.*;
public final class ItemManager {
    public static final Book welcome = Book.book(Component.text("欢迎来到CreepersPVP：FFA", NamedTextColor.WHITE), Component.text("CreepyCreeperSSS", NamedTextColor.GREEN), Component.text("""
        欢迎来到CreepersPVP：FFA！
        本游戏还处于开发阶段，所以有很多功能还不稳定
        玩家数据也有可能随时清空
        
        祝大家玩得开心XD
        不要吐槽远程武器装填动画！会改的！下次一定！
        1.21！1.21！1.21！重锤，风弹和旋风棒可以用了！
        往后翻查看更新日志
        """), Component.text("""
        ALPHA 0.5.0
        ·新增-更多法器！
        ·功能-经验系统！等级沿用原版
        ·功能-玩家生命值现在会在玩家名下方显示
        ·功能-使用鞘翅滑翔时可以空手左键来推进
        ·功能-火焰弹现在可以投掷为小火球，烈焰弹投掷为火球
        ·功能-重新启用原版火焰所有机制，但是方块现在不会烧毁
        """), Component.text("""
        ·功能-火球可以像小火球那样直接点燃实体
        ·功能-特殊箭矢不再能够被捡起
        ·修复-玩家不再能够捡起掉落物
        ·修复-弩在未购买时物品数据丢失
        """), Component.text("""
        ALPHA 0.4.1
        ·功能-指令/spectate
        ·调整-引雷不再在非露天环境下生效，匹配原版
        ·调整-引雷在晴天有50%几率生效，雨天75%，雷暴100%
        ·修复-自杀不再计入击杀数和绿宝石
        """), Component.text("""
        ALPHA 0.4.0
        ·新增-扩展MC远程武器系统，加入了很多远程武器！
        ·新增-完善盔甲，添加了一些盔甲和很多升级(没完！后面还会有更多)
        ·削弱-苦力怕盔甲护甲值14 -> 13
        ·功能-死亡消息现在会在死亡者和击杀者的动作栏中显示
        ·功能-死亡后自动观战击杀者，潜行或/spawn返回
        ·功能-盔甲可以绑定状态效果和物品
        """), Component.text("""
        ·功能-加入了一些盔甲效果(吸血，霜冻，潜影)
        ·功能-物品现在可以拥有多个升级
        ·功能-引雷附魔现在可以在非雷雨天以及远程武器上生效
        ·修复-法器冷却进度现在被显示在副标题中而非物品名中，不再会出现在死亡消息中
        ·修复-间接击杀不计入击杀数和绿宝石
        ·修复-盔甲不再能够被脱下
        """), Component.text("""
        ALPHA 0.3.1
        ·功能-TNT和末影水晶现在可以放置
        """), Component.text("""
        ALPHA 0.3.0
        ·地图-调整森林群系，树木更加稀疏，过渡更加平滑
        ·地图-在平原群系一角新增村庄
        ·新增-更多盔甲(后续会完善)
        ·功能-经济系统！
        ·功能-新UI！
        ·功能-计分板！你的击杀和KDR会被记录
        """), Component.text("""
        ·功能-实装剧毒和爆炸效果，微调冰冻效果
        ·功能-复活后指南针有冷却，防止误操作
        ·功能-复活后重置着火，冰冻，溺水效果
        ·功能-复活后插在玩家身上的箭矢会被移除
        ·修复-霜冻花剑攻击被格挡时不再造成冰冻，与火焰附加保持一致
        """), Component.text("""
        ALPHA 0.2.0
        ·地图-初步完成中心岛地表部分的建造
        ·新增-更多法器！
        ·增强-剑&钻石剑 现在带有 横扫之刃III 附魔
        ·增强-鱼叉 攻击速度0.8 -> 0.9
        ·削弱-战袍 伤害提升33% -> 25%
        ·功能-法器冷却被显示在物品描述中
        ·功能-忠诚三叉戟能从虚空中返回
        """), Component.text(
        """
        ·功能-雪球造成1点伤害并带有击退
        ·功能-虚空伤害可以瞬间杀死玩家
        ·功能-玩家通过间接伤害杀死实体的时限更久了
        ·功能-所有爆炸都不再破坏方块
        ·功能-TNT放置后自动点燃
        ·修复-在物品栏中被拿起在鼠标上的法器无法冷却
        ·修复-一个法器可以被分成两堆物品从而实现复制
        """), Component.text("""
        ALPHA 0.1.0
        第一个版本！从现在开始记录更新日志。
        """));
    public static final Component[] PROGRESS_BARS = new Component[20];
    public static final ItemStack ARMOR_SELECTOR = new ItemStack(Material.IRON_CHESTPLATE);
    public static final ItemStack[] WEAPON_SELECTORS = new ItemStack[] {new ItemStack(Material.IRON_SWORD), new ItemStack(Material.IRON_SWORD)};
    public static final ItemStack[] ARTIFACT_SELECTORS = new ItemStack[] {new ItemStack(Material.GLASS_BOTTLE), new ItemStack(Material.GLASS_BOTTLE), new ItemStack(Material.GLASS_BOTTLE)};
    public static final ItemStack DEPLOY = new ItemStack(Material.COMPASS);
    public static final ItemStack BORDER = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
    public static final ItemStack CLOSE = new ItemStack(Material.RED_STAINED_GLASS_PANE);
    public static final ItemStack BACK = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
    public static final ItemStack CONFIRM = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
    public static final ItemStack CANCEL = new ItemStack(Material.RED_STAINED_GLASS_PANE);
    static final NamespacedKey[] armorKeys = new NamespacedKey[] {NamespacedKey.minecraft("armor.helmet"), NamespacedKey.minecraft("armor.chestplate"), NamespacedKey.minecraft("armor.leggings"), NamespacedKey.minecraft("armor.boots")};
    static final NamespacedKey entityArmorKey = NamespacedKey.minecraft("armor.body");
    static final NamespacedKey baseAttackDamageKey = NamespacedKey.minecraft("base_attack_damage");
    static final NamespacedKey baseAttackSpeedKey = NamespacedKey.minecraft("base_attack_speed");
    private static final AttributeModifier EMPTY_ATTRIBUTE_MODIFIER = new AttributeModifier(NamespacedKey.minecraft("empty"), 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
    static final EquipmentSlotGroup[] armorSlotGroups = new EquipmentSlotGroup[] {EquipmentSlotGroup.HEAD, EquipmentSlotGroup.CHEST, EquipmentSlotGroup.LEGS, EquipmentSlotGroup.FEET, EquipmentSlotGroup.BODY};
    private ItemManager() {}
    public static void init() {
        ArmorManager.init();
        WeaponManager.init();
        ArtifactManager.init();
        ARMOR_SELECTOR.editMeta(meta -> {
            meta.itemName(Component.text("选择盔甲"));                  
            meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.ARMOR_SELECTOR);
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, EMPTY_ATTRIBUTE_MODIFIER);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        });
        for(final int[] i = new int[] {0}; i[0] < WEAPON_SELECTORS.length; i[0]++) {
            WEAPON_SELECTORS[i[0]].editMeta(meta -> {
                meta.itemName(Component.text("选择武器#" + (i[0] + 1)));
                meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.WEAPON_SELECTOR);
                PersistentDataContainer iuiData = meta.getPersistentDataContainer().getAdapterContext().newPersistentDataContainer();
                iuiData.set(Utils.weaponSelectorIDKey, PersistentDataType.INTEGER, i[0]);
                meta.getPersistentDataContainer().set(Utils.iuiDataKey, PersistentDataType.TAG_CONTAINER, iuiData);
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            });
        }
        WEAPON_SELECTORS[0].editMeta(meta -> {
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, EMPTY_ATTRIBUTE_MODIFIER);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, EMPTY_ATTRIBUTE_MODIFIER);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        });
        for(final int[] i = new int[] {0}; i[0] < ARTIFACT_SELECTORS.length; i[0]++) {
            ARTIFACT_SELECTORS[i[0]].editMeta(meta -> {
                meta.itemName(Component.text("选择法器#" + (i[0] + 1)));
                meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.ARTIFACT_SELECTOR);
                PersistentDataContainer iuiData = meta.getPersistentDataContainer().getAdapterContext().newPersistentDataContainer();
                iuiData.set(Utils.artifactSelectorIDKey, PersistentDataType.INTEGER, i[0]);
                meta.getPersistentDataContainer().set(Utils.iuiDataKey, PersistentDataType.TAG_CONTAINER, iuiData);
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            });
        }
        for(int i = 0; i < PROGRESS_BARS.length; i++) {
            TextComponent.Builder nameBuilder = Component.text();
            for(int j = 0; j < PROGRESS_BARS.length; j++) {
                nameBuilder.append(Component.text(".", i > j ? NamedTextColor.GREEN : NamedTextColor.RED));
            }
            PROGRESS_BARS[i] = nameBuilder.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE).build();
        }
        DEPLOY.editMeta(meta -> {
            meta.itemName(Component.text("进入战斗", NamedTextColor.GREEN));
            meta.getPersistentDataContainer().set(Utils.utilIDKey, PersistentDataType.BYTE, Utils.UTIL_SPAWN);
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
        });
        BORDER.editMeta(meta -> meta.itemName(Component.empty()));
        CLOSE.editMeta(meta -> meta.itemName(Component.text("✖ 关闭", NamedTextColor.RED)));
        BACK.editMeta(meta -> meta.itemName(Component.text("◀ 返回", NamedTextColor.YELLOW)));
        CONFIRM.editMeta(meta -> meta.itemName(Component.text("✔ 确认", NamedTextColor.GREEN)));
        CANCEL.editMeta(meta -> meta.itemName(Component.text("✖ 取消", NamedTextColor.RED)));
    }
    static void editMeleeAttributes(ItemMeta meta, double damage, double speed) {
        meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE);
        meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(baseAttackDamageKey, damage - 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(baseAttackSpeedKey, speed - 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND));
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
    static void editRangedAttributes(ItemMeta meta, float velocity, float speed) {
        final PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(Utils.customItemUsageKey, PersistentDataType.TAG_CONTAINER, data.getAdapterContext().newPersistentDataContainer());
        data.set(Utils.projectileVelocityKey, PersistentDataType.FLOAT, velocity);
        data.set(Utils.rangedAttackSpeedKey, PersistentDataType.FLOAT, speed);
        final List<Component> meleeAttributeLore = removeItalics(Arrays.asList(Component.empty(), Component.text("当使用时：", NamedTextColor.GRAY), Component.text(" " + velocity + " 箭矢速度", NamedTextColor.DARK_GREEN), Component.text(" " + speed + " 蓄力速度", NamedTextColor.DARK_GREEN)));
        if(meta.hasLore()) {
            final List<Component> lore = meta.lore();
            lore.addAll(meleeAttributeLore);
            meta.lore(lore);
        } else {
            meta.lore(meleeAttributeLore);
        }
    }
    static void addArmorAttributes(ItemMeta meta, double armor, double toughness, double knockbackResistance) {
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(armorKeys[1], armor, AttributeModifier.Operation.ADD_NUMBER, armorSlotGroups[1]));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(armorKeys[1], toughness, AttributeModifier.Operation.ADD_NUMBER, armorSlotGroups[1]));
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(armorKeys[1], knockbackResistance, AttributeModifier.Operation.ADD_NUMBER, armorSlotGroups[1]));
    }
    static void addArmorAttributes(ItemStack[] items, double[] armor, double[] toughness, double[] knockbackResistance) {
        for(final int[] i = new int[] {0}; i[0] < 4; i[0]++) {
            items[i[0]].editMeta(meta -> {
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(armorKeys[i[0]], armor[i[0]], AttributeModifier.Operation.ADD_NUMBER, armorSlotGroups[i[0]]));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(armorKeys[i[0]], toughness[i[0]], AttributeModifier.Operation.ADD_NUMBER, armorSlotGroups[i[0]]));
                meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(armorKeys[i[0]], knockbackResistance[i[0]], AttributeModifier.Operation.ADD_NUMBER, armorSlotGroups[i[0]]));
            });
        }
    }
    /**
     * Only works on weapons!
     */
    static void addMultiItemAttribute(ItemMeta meta, int multiItems) {
        final PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(Utils.multiItemKey, PersistentDataType.INTEGER, multiItems);
        final List<Component> meleeAttributeLore = removeItalics(Arrays.asList(Component.empty(), Component.text(multiItems + " 物品数量", NamedTextColor.DARK_GREEN)));
        if(meta.hasLore()) {
            final List<Component> lore = meta.lore();
            lore.addAll(meleeAttributeLore);
            meta.lore(lore);
        } else {
            meta.lore(meleeAttributeLore);
        }
    }
    static List<Component> removeItalics(final List<Component> lore) {
        lore.replaceAll(component -> component.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        return lore;
    }
}
