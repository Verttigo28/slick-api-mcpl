package ch.verttigo.sapi.listeners;

import ch.verttigo.sapi.cache.lCache;
import ch.verttigo.sapi.manager.user.SUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginQuitEvent implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        e.getPlayer().sendMessage("Loading cache...");
        lCache.getUser(e.getPlayer().getUniqueId());

        SUser su = new SUser(e.getPlayer().getUniqueId());

        e.getPlayer().sendMessage("Cache Loaded");
        e.getPlayer().sendMessage("Test 1 : " + su.getCoins());
        e.getPlayer().sendMessage("Test 2 : " + su.getNickname());
        e.getPlayer().sendMessage("Test 3 : " + su.getGroup());
        e.getPlayer().sendMessage("Test 4 : " + su.getExp());
    }
}
