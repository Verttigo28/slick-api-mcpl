package ch.verttigo.sapi.sbcTest;

import ch.verttigo.sapi.SAPI;
import ch.verttigo.sapi.manager.user.SUser;
import me.catcoder.sidebar.ProtocolSidebar;
import me.catcoder.sidebar.Sidebar;
import me.catcoder.sidebar.text.TextIterators;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class sbManager {

    private static final Sidebar<Component> sidebar = ProtocolSidebar.newAdventureSidebar(TextIterators.textFadeHypixel("SAPI-TESTER"), SAPI.getInstance());

    public static void createScoreboard() {
        sidebar.addBlankLine();
        sidebar.addUpdatableLine(player -> Component.text("Nbr de coins : ").append(Component.text(new SUser(player.getUniqueId()).getCoins()).color(NamedTextColor.GREEN)));
        sidebar.addBlankLine();
        sidebar.updateLinesPeriodically(0, 20);
    }

    public static void addViewer(Player p) {
        sidebar.addViewer(p);
    }

    public static void removeViewer(Player p) {
        sidebar.removeViewer(p);
    }


}
