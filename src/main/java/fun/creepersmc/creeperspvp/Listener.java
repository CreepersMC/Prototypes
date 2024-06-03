package fun.creepersmc.creeperspvp;
import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import fun.creepersmc.creeperspvp.iui.IUIManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
public final class Listener implements org.bukkit.event.Listener {
    public static final Listener instance = new Listener();
    private Listener() {}
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Utils.playerInit(event.getEntity());
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Utils.playerInit(event.getPlayer());
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        final ItemStack item = event.getItem();
        if(item != null) {
            final ItemMeta meta = item.getItemMeta();
            final Player player = event.getPlayer();
            if(meta != null) {
                PersistentDataContainer data = meta.getPersistentDataContainer();
                if(data.has(Utils.artifactIDKey, PersistentDataType.INTEGER) && item.getType() != Material.BARRIER) {
                    int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.INTEGER);
                    if(ItemManager.artifactsUseEvent[artifactID] == ItemManager.INTERACT) {
                        if(ItemManager.artifactsUseCooldown[artifactID] != -1) {
                            player.setCooldown(event.getMaterial(), ItemManager.artifactsUseCooldown[artifactID]);
                        }
                        if(ItemManager.artifactsGainCooldown[artifactID] != -1) {
                            Inventory inv = player.getInventory();
                            if(item.getAmount() == 1) {
                                inv.remove(item);//TODO
                                player.getInventory().setItem(Utils.findItem(inv, item.getItemMeta().getPersistentDataContainer().getOrDefault(Utils.itemKey, PersistentDataType.INTEGER, -1)), item.withType(Material.BARRIER));
                            } else {
                                item.subtract();
                            }
                            if(!Utils.hasGainArtifactScheduler(data.get(Utils.itemKey, PersistentDataType.INTEGER) - 2, player.getUniqueId())) {
                                Utils.scheduleGainArtifact(data.get(Utils.itemKey, PersistentDataType.INTEGER) - 2, player.getUniqueId(), player.getScheduler().runAtFixedRate(CreepersPVP.instance, task -> {
                                    int slot = Utils.findItem(inv, item.getItemMeta().getPersistentDataContainer().getOrDefault(Utils.itemKey, PersistentDataType.INTEGER, -1));
                                    if(inv.getItem(slot).getType() == Material.BARRIER) {
                                        player.getInventory().setItem(slot, item);
                                    } else {
                                        if(item.getAmount() < ItemManager.artifacts[artifactID].getAmount()) {
                                            item.add();
                                        } else {
                                            task.cancel();
                                        }
                                    }
                                }, null, ItemManager.artifactsGainCooldown[artifactID], ItemManager.artifactsGainCooldown[artifactID]));
                            }
                        }
                    }
                }
                if(data.has(Utils.iuiIDKey, PersistentDataType.BYTE)) {
                    event.setCancelled(true);
                    IUIManager.getIUI(data.get(Utils.iuiIDKey, PersistentDataType.BYTE), data.get(Utils.iuiDataKey, PersistentDataType.TAG_CONTAINER)).open(player);
                }
                if(data.has(Utils.utilIDKey, PersistentDataType.BYTE)) {
                    event.setCancelled(true);
                    switch(data.get(Utils.utilIDKey, PersistentDataType.BYTE)) {
                        case Utils.UTIL_SPAWN -> Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> Utils.spawnPlayer(player));
                    }
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        System.out.println("Item consumed: " + event.getItem() + event.getItem().getAmount());
        final ItemStack item = event.getItem();
        final ItemMeta meta = item.getItemMeta();
        final Player player = event.getPlayer();
        if(meta != null) {
            PersistentDataContainer data = meta.getPersistentDataContainer();
            if(data.has(Utils.artifactIDKey, PersistentDataType.INTEGER) && item.getType() != Material.BARRIER) {
                int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.INTEGER);
                if(ItemManager.artifactsUseEvent[artifactID] == ItemManager.CONSUME) {
                    event.setReplacement(null);
                    if(ItemManager.artifactsUseCooldown[artifactID] != -1) {
                        player.setCooldown(item.getType(), ItemManager.artifactsUseCooldown[artifactID]);
                    }
                    if(ItemManager.artifactsGainCooldown[artifactID] != -1) {
                        Inventory inv = player.getInventory();
                        if(item.getAmount() == 1) {
                            event.setReplacement(item.withType(Material.BARRIER).asOne());
                        }
                        if(!Utils.hasGainArtifactScheduler(data.get(Utils.itemKey, PersistentDataType.INTEGER) - 2, player.getUniqueId())) {
                            Utils.scheduleGainArtifact(data.get(Utils.itemKey, PersistentDataType.INTEGER) - 2, player.getUniqueId(), player.getScheduler().runAtFixedRate(CreepersPVP.instance, task -> {
                                int slot = Utils.findItem(inv, item.getItemMeta().getPersistentDataContainer().getOrDefault(Utils.itemKey, PersistentDataType.INTEGER, -1));
                                ItemStack currentItem = inv.getItem(slot);
                                if(currentItem.getType() == Material.BARRIER) {
                                    player.getInventory().setItem(slot, item);
                                } else {
                                    if(currentItem.getAmount() < ItemManager.artifacts[artifactID].getAmount()) {
                                        currentItem.add();
                                    } else {
                                        task.cancel();
                                    }
                                }
                            }, null, ItemManager.artifactsGainCooldown[artifactID], ItemManager.artifactsGainCooldown[artifactID]));
                        }
                    }
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerProjectileLaunch(PlayerLaunchProjectileEvent event) {
        final ItemStack item = event.getItemStack();
        final ItemMeta meta = item.getItemMeta();
        final Player player = event.getPlayer();
        if(meta != null) {
            PersistentDataContainer data = meta.getPersistentDataContainer();
            if(data.has(Utils.artifactIDKey, PersistentDataType.INTEGER) && item.getType() != Material.BARRIER) {
                int artifactID = data.get(Utils.artifactIDKey, PersistentDataType.INTEGER);
                if(ItemManager.artifactsUseEvent[artifactID] == ItemManager.LAUNCH_PROJECTILE) {
                    if(ItemManager.artifactsUseCooldown[artifactID] != -1) {
                        player.setCooldown(item.getType(), ItemManager.artifactsUseCooldown[artifactID]);
                    }
                    if(ItemManager.artifactsGainCooldown[artifactID] != -1) {
                        Inventory inv = player.getInventory();
                        ItemStack cloned = item.clone();
                        int tmp = item.getItemMeta().getPersistentDataContainer().getOrDefault(Utils.itemKey, PersistentDataType.INTEGER, -1);
                        if(item.getAmount() == 1) {
                            final ItemStack disabled = cloned.withType(Material.BARRIER);
                            int slot = Utils.findItem(inv, tmp);
                            inv.setItem(slot, disabled);
                            player.getScheduler().run(CreepersPVP.instance, task -> inv.setItem(slot, disabled), null);
                        }
                        if(!Utils.hasGainArtifactScheduler(data.get(Utils.itemKey, PersistentDataType.INTEGER) - 2, player.getUniqueId())) {
                            Utils.scheduleGainArtifact(data.get(Utils.itemKey, PersistentDataType.INTEGER) - 2, player.getUniqueId(), player.getScheduler().runAtFixedRate(CreepersPVP.instance, task -> {
                                int slot = Utils.findItem(inv, tmp);
                                ItemStack currentItem = inv.getItem(slot);
                                if(currentItem.getType() == Material.BARRIER) {
                                    player.getInventory().setItem(slot, cloned.asOne());
                                } else {
                                    if(currentItem.getAmount() < ItemManager.artifacts[artifactID].getAmount()) {
                                        currentItem.add();
                                    } else {
                                        task.cancel();
                                    }
                                }
                            }, null, ItemManager.artifactsGainCooldown[artifactID], ItemManager.artifactsGainCooldown[artifactID]));
                        }
                    }
                }
            }
        }
    }
}
