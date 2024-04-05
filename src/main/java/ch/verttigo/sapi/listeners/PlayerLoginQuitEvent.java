package ch.verttigo.sapi.listeners;

import ch.verttigo.sapi.sbcTest.sbManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerLoginQuitEvent implements Listener {

    @EventHandler
    public void onLogin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        sbManager.addViewer(p);
    }
}
