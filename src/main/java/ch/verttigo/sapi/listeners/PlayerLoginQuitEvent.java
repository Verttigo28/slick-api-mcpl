package ch.verttigo.sapi.listeners;

import ch.verttigo.sapi.SAPI;
import ch.verttigo.sapi.manager.user.SUser;
import ch.verttigo.sapi.sbcTest.sbManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class PlayerLoginQuitEvent implements Listener {

    @EventHandler
    public void onLogin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        SUser su = new SUser(uuid);
        p.sendMessage(SAPI.getPrefix() + "Trying to set your coins to 99");
        Instant start = Instant.now();
        if (su.setCoins(99)) {
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            p.sendMessage(SAPI.getPrefix() + "Change made in " + timeElapsed + "ms");
            p.sendMessage(SAPI.getPrefix() + "New coins : " + su.getCoins());
        } else {
            p.sendMessage("Error :(");
        }

        sbManager.addViewer(p);
    }
}

