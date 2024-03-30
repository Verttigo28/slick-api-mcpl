package ch.verttigo.sapi;


import ch.verttigo.sapi.commands.AdminCommands;
import ch.verttigo.sapi.commands.CommandHandler;
import ch.verttigo.sapi.listeners.PlayerLoginQuitEvent;
import ch.verttigo.sapi.nats.natsClient;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class SAPI extends JavaPlugin {
    private static SAPI instance;
    private final Server server = getServer();

    public static SAPI getInstance() {
        return instance;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static UUID getUUID() {
        return uuid;
    }

    private static String prefix;
    private static UUID uuid;

    @Override
    public void onLoad() {
        instance = this;
        prefix = "§c§lSlick§b§lAPI §8➟ §f";
        uuid = UUID.randomUUID();
    }

    @Override
    public void onEnable() {
        instance = this;

        this.saveDefaultConfig();

        natsClient.connectNats();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerLoginQuitEvent(), this);
        //   server.getServicesManager().register(Economy.class, new EconomyService(), getInstance(), ServicePriority.High);

    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        CommandHandler handler = new CommandHandler();
        handler.registerCommands(new AdminCommands());
    }

}
