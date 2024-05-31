package fun.creepersmc.creeperspvp;
import fun.creepersmc.creeperspvp.iui.IUIManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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
        //if(event.useInteractedBlock() == Event.Result.ALLOW || event.useItemInHand() == Event.Result.ALLOW) {
        //    return;
        //}
        if(event.getItem() != null) {
            ItemMeta meta = event.getItem().getItemMeta();
            if(meta != null) {
                PersistentDataContainer data = meta.getPersistentDataContainer();
                if(data.has(Utils.iuiIDKey, PersistentDataType.BYTE)) {
                    event.setCancelled(true);
                    IUIManager.getIUI(data.get(Utils.iuiIDKey, PersistentDataType.BYTE), data.get(Utils.iuiDataKey, PersistentDataType.TAG_CONTAINER)).open(event.getPlayer());
                }
                if(data.has(Utils.utilIDKey, PersistentDataType.BYTE)) {
                    event.setCancelled(true);
                    switch(data.get(Utils.utilIDKey, PersistentDataType.BYTE)) {
                        case Utils.UTIL_SPAWN -> Bukkit.getScheduler().runTask(CreepersPVP.instance, () -> Utils.spawnPlayer(event.getPlayer()));
                    }
                }
            }
        }
    }
}
