package re.imc.creeperspvp.items;
import org.bukkit.DyeColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BookMeta;
import re.imc.creeperspvp.CreepersPVP;
import re.imc.creeperspvp.iui.IUIManager;
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
    public static final Component[] PROGRESS_BARS = new Component[20];
    public static final ItemStack SELECT_ARMOR = new ItemStack(Material.IRON_CHESTPLATE);
    public static final ItemStack SELECT_WEAPONS = new ItemStack(Material.IRON_SWORD);
    public static final ItemStack SELECT_ARTIFACTS = new ItemStack(Material.GLASS_BOTTLE);
    public static final ItemStack DEPLOY = new ItemStack(Material.COMPASS);
    public static final ItemStack GUIDEBOOK = new ItemStack(Material.WRITTEN_BOOK);
    public static final ItemStack SETTINGS = new ItemStack(Material.COMPARATOR);
    public static final ItemStack SERVERS = new ItemStack(Material.ENDER_EYE);
    public static final ItemStack BORDER = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
    public static final ItemStack CLOSE = new ItemStack(Material.RED_STAINED_GLASS_PANE);
    public static final ItemStack BACK = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
    public static final ItemStack APPLY = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
    public static final ItemStack CONFIRM = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
    public static final ItemStack CANCEL = new ItemStack(Material.RED_STAINED_GLASS_PANE);
    public static final ItemStack OKAY = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
    public static final ItemStack ENABLED = new ItemStack(Material.LIME_DYE);
    public static final ItemStack DEFAULT = new ItemStack(Material.PURPLE_DYE);
    public static final ItemStack DISABLED = new ItemStack(Material.GRAY_DYE);
    public static final ItemStack SUB_SETTING = new ItemStack(Material.REPEATER);
    public static final ItemStack ARMOR_SELECTOR = new ItemStack(Material.IRON_CHESTPLATE);
    public static final ItemStack[] WEAPON_SELECTORS = new ItemStack[] {new ItemStack(Material.IRON_SWORD), new ItemStack(Material.IRON_SWORD)};
    public static final ItemStack[] ARTIFACT_SELECTORS = new ItemStack[] {new ItemStack(Material.GLASS_BOTTLE), new ItemStack(Material.GLASS_BOTTLE), new ItemStack(Material.GLASS_BOTTLE)};
    public static final ItemStack SHOW_GUIDEBOOK = new ItemStack(Material.BOOK);
    public static final ItemStack DEPLOY_COOLDOWN = new ItemStack(Material.COMPASS);
    public static final ItemStack RANGED_ATTACK_INDICATOR = new ItemStack(Material.BOW);
    public static final ItemStack ITEM_SETTING = new ItemStack(Material.ARMOR_STAND);
    public static final ItemStack HOTBAR_SLOT = new ItemStack(Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
    public static final ItemStack OFFHAND_SLOT = new ItemStack(Material.WHITE_BANNER);
    public static final ItemStack SET_HELMET_SLOT = new ItemStack(Material.IRON_HELMET);
    public static final ItemStack SET_CHESTPLATE_SLOT = new ItemStack(Material.IRON_CHESTPLATE);
    public static final ItemStack SET_LEGGINGS_SLOT = new ItemStack(Material.IRON_LEGGINGS);
    public static final ItemStack SET_BOOTS_SLOT = new ItemStack(Material.IRON_BOOTS);
    public static final ItemStack[] SET_WEAPON_SLOTS = new ItemStack[] {new ItemStack(Material.IRON_SWORD), new ItemStack(Material.IRON_SWORD)};
    public static final ItemStack[] SET_ARTIFACT_SLOTS = new ItemStack[] {new ItemStack(Material.GLASS_BOTTLE), new ItemStack(Material.GLASS_BOTTLE), new ItemStack(Material.GLASS_BOTTLE)};
    public static final ItemStack FFA = new ItemStack(Material.IRON_SWORD);
    public static final ItemStack TDM = new ItemStack(Material.PLAYER_HEAD);
    public static final ItemStack CTF = new ItemStack(Material.GREEN_BANNER);
    public static final ItemStack CQT = new ItemStack(Material.BEACON);
    public static final ItemStack LOBBY = new ItemStack(Material.BIRCH_DOOR);
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
        for(int i = 0; i < PROGRESS_BARS.length; i++) {
            TextComponent.Builder nameBuilder = Component.text();
            for(int j = 0; j < PROGRESS_BARS.length; j++) {
                nameBuilder.append(Component.text(".", i > j ? NamedTextColor.GREEN : NamedTextColor.RED));
            }
            PROGRESS_BARS[i] = nameBuilder.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE).build();
        }
        SELECT_ARMOR.editMeta(meta -> {
            meta.itemName(Component.text("选择盔甲"));
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            meta.addAttributeModifier(Attribute.GENERIC_LUCK, EMPTY_ATTRIBUTE_MODIFIER);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.ARMOR_SELECTOR);
        });
        SELECT_WEAPONS.editMeta(meta -> {
            meta.itemName(Component.text("选择武器"));
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            meta.addAttributeModifier(Attribute.GENERIC_LUCK, EMPTY_ATTRIBUTE_MODIFIER);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.WEAPON_SELECTOR);
        });
        SELECT_ARTIFACTS.editMeta(meta -> {
            meta.itemName(Component.text("选择法器"));
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.ARTIFACT_SELECTOR);
        });
        DEPLOY.editMeta(meta -> {
            meta.itemName(Component.text("进入战斗", NamedTextColor.GREEN));
            meta.getPersistentDataContainer().set(Utils.utilIDKey, PersistentDataType.BYTE, Utils.UTIL_SPAWN);
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
        });
        SETTINGS.editMeta(meta -> {
            meta.itemName(Component.text("设置", NamedTextColor.RED));
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.SETTINGS);
        });
        SERVERS.editMeta(meta -> {
            meta.itemName(Component.text("切换服务器", NamedTextColor.DARK_PURPLE));
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            meta.getPersistentDataContainer().set(Utils.iuiIDKey, PersistentDataType.BYTE, IUIManager.SERVERS);
        });
        GUIDEBOOK.editMeta(BookMeta.class, meta -> {
            meta.setGeneration(BookMeta.Generation.COPY_OF_ORIGINAL);
            meta.author(Component.text("IMC.RE", NamedTextColor.GOLD));
            meta.title(Component.text("CreepersPVP指南", NamedTextColor.YELLOW));
            meta.pages(List.of(Component.text("""
            欢迎来到CreepersPVP：FFA！
            本游戏还处于开发阶段，所以有很多功能还不稳定
            玩家数据也有可能随时清空
            
            祝大家玩得开心XD
            不要吐槽远程武器装填动画！会改的！下次一定！
            往后翻查看更新日志
            """), Component.text("""
            BETA 0.6.0
            ·新增-更多法器！
            ·新增-哞菇盔甲
            ·增强-甜浆果从放置后会自动成长至age=1
            ·功能-流体现在也支持放置后一段时间消失了
            ·功能-食物类法器使用后产生的剩余物品(若有)会被用于代表其冷却而非屏障
            ·功能-爆裂紫颂果可以食用并传送+爆炸；闪烁的西瓜片食用提供伤害吸收
            """), Component.text("""
            ·削弱-幽灵盔甲现在在潜行时才具有隐身效果
            ·削弱-凋零之首现在发射时有冷却
            ·调整-重新平衡部分远程武器的射速
            ·调整-爆炸现在只造成原先70%的伤害
            ·修复-方块破坏动画现在正确地显示
            ·修复-方块类法器放置后的位置能放置新方块了
            ·修复-服务器重启时被放置的方块类法器现在会消失
            """), Component.text("""
            ·修复-使用盾牌反弹的箭矢不再能够被捡起
            ·修复-喝牛奶不再清除盔甲绑定的状态效果
            ·修复-不死图腾发动时不再清除盔甲绑定的状态效果
            ·修复-喝蜂蜜瓶不再产生玻璃瓶
            """), Component.text("""
            ALPHA 0.5.1
            ·功能-玩家经验等级会被显示在玩家列表中
            ·功能-甜浆果现在可以放置
            ·功能-末地水晶现在在爆炸后开始冷却而非放置后
            ·功能-武器现在均可以挖掘火
            ·功能-火尝试烧毁方块时，方块会“假装被烧毁了”导致火可能熄灭，匹配原版
            ·功能-使用带有烟火之星的烟花火箭加速滑翔会弹出警告
            """), Component.text("""
            ·调整-恶魂盔甲现在会增加穿戴者的体积
            ·调整-重新平衡新增的弹射物的冷却
            ·修复-烟花火箭现在会正确地冷却
            ·修复-药箭和光灵箭现在会正确地冷却
            ·修复-设置中的进入战斗冷却不实际生效
            ·修复-登山装的盔甲纹饰不一致
            """), Component.text("""
            ALPHA 0.5.0
            ·新增-更多法器！
            ·增强-冷酷战甲、凋零盔甲、蜘蛛盔甲的吸血率
            ·削弱-末地水晶冷却10 -> 12
            ·功能-新UI！选择武器的两个UI和选择法器的三个UI被合并，新增设置和切换服务器
            ·功能-经验系统！等级沿用原版
            ·功能-自定义物品栏布局
            """), Component.text("""
            ·功能-玩家生命值现在会在玩家名下方显示
            ·功能-使用鞘翅滑翔时可以空手左键来推进
            ·功能-方块类法器放置后会逐渐消失
            ·功能-火焰弹现在可以投掷为小火球，爆破弹投掷为火球
            ·功能-重新启用原版火焰所有机制，但是方块现在不会烧毁
            ·功能-特殊箭矢不再能够被捡起
            """), Component.text("""
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
            """), Component.text("""
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
            """)));
        });
        BORDER.editMeta(meta -> meta.itemName(Component.empty()));
        CLOSE.editMeta(meta -> meta.itemName(Component.text("✖ 关闭", NamedTextColor.RED)));
        BACK.editMeta(meta -> meta.itemName(Component.text("◀ 返回", NamedTextColor.YELLOW)));
        APPLY.editMeta(meta -> meta.itemName(Component.text("✔ 应用", NamedTextColor.BLUE)));
        CONFIRM.editMeta(meta -> meta.itemName(Component.text("✔ 确认", NamedTextColor.GREEN)));
        CANCEL.editMeta(meta -> meta.itemName(Component.text("✖ 取消", NamedTextColor.RED)));
        OKAY.editMeta(meta -> meta.itemName(Component.text("✔ 确定", NamedTextColor.GREEN)));
        ENABLED.editMeta(meta -> meta.itemName(Component.text("开启", NamedTextColor.GREEN)));
        DEFAULT.editMeta(meta -> meta.itemName(Component.text("自动", NamedTextColor.DARK_PURPLE)));
        DISABLED.editMeta(meta -> meta.itemName(Component.text("关闭", NamedTextColor.GRAY)));
        SHOW_GUIDEBOOK.editMeta(meta -> {
            meta.itemName(Component.text("自动显示指南", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("是否在加入游戏时自动弹出CreepersPVP指南", NamedTextColor.GRAY))));
        });
        DEPLOY_COOLDOWN.editMeta(meta -> {
            meta.itemName(Component.text("进入战斗冷却", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("“进入战斗”是否在死亡后有一定冷却时间，防止误触", NamedTextColor.GRAY))));
        });
        RANGED_ATTACK_INDICATOR.editMeta(meta -> {
            meta.itemName(Component.text("远程攻击指示器", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("是否在准星下方显示远程武器蓄力进度", NamedTextColor.GRAY), Component.text("不适用资源包时，你将无法实时看到远程武器蓄力进度", NamedTextColor.GRAY), Component.text("自动：未安装资源包时不显示", NamedTextColor.DARK_PURPLE))));
        });
        SUB_SETTING.editMeta(meta -> meta.itemName(Component.text("进入设置", NamedTextColor.RED)));
        HOTBAR_SLOT.editMeta(meta -> meta.itemName(Component.text("快捷栏槽位 ▲", NamedTextColor.WHITE)));
        OFFHAND_SLOT.editMeta(meta -> meta.itemName(Component.text("副手槽位 ▶", NamedTextColor.WHITE)));
        ARMOR_SELECTOR.editMeta(meta -> {
            meta.itemName(Component.text("选择盔甲", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            meta.addAttributeModifier(Attribute.GENERIC_LUCK, EMPTY_ATTRIBUTE_MODIFIER);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.setEnchantmentGlintOverride(true);
        });
        for(final int[] i = new int[] {0}; i[0] < WEAPON_SELECTORS.length; i[0]++) {
            WEAPON_SELECTORS[i[0]].editMeta(meta -> {
                meta.itemName(Component.text("选择武器#" + (i[0] + 1), NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
                meta.addAttributeModifier(Attribute.GENERIC_LUCK, EMPTY_ATTRIBUTE_MODIFIER);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            });
        }
        for(final int[] i = new int[] {0}; i[0] < ARTIFACT_SELECTORS.length; i[0]++) {
            ARTIFACT_SELECTORS[i[0]].editMeta(meta -> {
                meta.itemName(Component.text("选择法器#" + (i[0] + 1), NamedTextColor.WHITE));
                meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            });
        }
        ITEM_SETTING.editMeta(meta -> {
            meta.itemName(Component.text("物品布局设置", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("自定义你的装备在物品栏中的位置", NamedTextColor.GRAY))));
        });
        SET_HELMET_SLOT.editMeta(meta -> meta.itemName(Component.text("头盔", NamedTextColor.WHITE)));
        SET_CHESTPLATE_SLOT.editMeta(meta -> meta.itemName(Component.text("胸甲", NamedTextColor.WHITE)));
        SET_LEGGINGS_SLOT.editMeta(meta -> meta.itemName(Component.text("护腿", NamedTextColor.WHITE)));
        SET_BOOTS_SLOT.editMeta(meta -> meta.itemName(Component.text("靴子", NamedTextColor.WHITE)));
        for(final int[] i = new int[] {0}; i[0] < 2; i[0]++) {
            SET_WEAPON_SLOTS[i[0]].editMeta(meta -> {
                meta.itemName(Component.text("武器#" + (i[0] + 1), NamedTextColor.WHITE));
                meta.getPersistentDataContainer().set(Utils.itemOrdinalKey, PersistentDataType.INTEGER, i[0]);
            });
        }
        for(final int[] i = new int[] {0}; i[0] < 3; i[0]++) {
            SET_ARTIFACT_SLOTS[i[0]].editMeta(meta -> {
                meta.itemName(Component.text("法器#" + (i[0] + 1), NamedTextColor.WHITE));
                meta.getPersistentDataContainer().set(Utils.itemOrdinalKey, PersistentDataType.INTEGER, i[0] + 2);
            });
        }
        FFA.editMeta(meta -> {
            meta.itemName(Component.text("进入FFA", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("你在这里", NamedTextColor.GRAY))));
            meta.setEnchantmentGlintOverride(CreepersPVP.mode == CreepersPVP.GameMode.FFA);
        });
        TDM.editMeta(meta -> {
            meta.itemName(Component.text("进入TDM", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("暂未开放", NamedTextColor.GRAY))));
            meta.setEnchantmentGlintOverride(CreepersPVP.mode == CreepersPVP.GameMode.TDM);
        });
        CTF.editMeta(BannerMeta.class, meta -> {
            meta.itemName(Component.text("进入CTF", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("暂未开放", NamedTextColor.GRAY))));
            meta.setEnchantmentGlintOverride(CreepersPVP.mode == CreepersPVP.GameMode.CTF);
            meta.addPattern(new Pattern(DyeColor.LIME, PatternType.GRADIENT));
            meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.BORDER));
            meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.CREEPER));
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        });
        CQT.editMeta(meta -> {
            meta.itemName(Component.text("进入CQT", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("暂未开放", NamedTextColor.GRAY))));
            meta.setEnchantmentGlintOverride(CreepersPVP.mode == CreepersPVP.GameMode.CQT);
        });
        LOBBY.editMeta(meta -> {
            meta.itemName(Component.text("返回大厅", NamedTextColor.WHITE));
            meta.lore(removeItalics(Arrays.asList(Component.text("", NamedTextColor.GRAY))));
            meta.setEnchantmentGlintOverride(false);
        });
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
