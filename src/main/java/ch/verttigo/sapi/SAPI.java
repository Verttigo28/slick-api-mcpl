package ch.verttigo.sapi;


import ch.verttigo.sapi.commands.AdminCommands;
import ch.verttigo.sapi.commands.CommandHandler;
import ch.verttigo.sapi.economy.EconomyService;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Server;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class SAPI extends JavaPlugin {
    private static SAPI instance;
    private final Server server = getServer();

    public static SAPI getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        server.getServicesManager().register(Economy.class, new EconomyService(), getInstance(), ServicePriority.High);

    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        CommandHandler handler = new CommandHandler();
        handler.registerCommands(new AdminCommands());
    }

}
