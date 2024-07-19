package ch.verttigo.sapi;


import ch.verttigo.sapi.commands.AdminCommands;
import ch.verttigo.sapi.commands.CommandHandler;
import ch.verttigo.sapi.listeners.PlayerLoginQuitEvent;
import ch.verttigo.sapi.nats.NatsClient;
import ch.verttigo.sapi.sbcTest.sbManager;
import ch.verttigo.sapi.utils.Logger;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

import static ch.verttigo.sapi.utils.Logger.log;

public class SAPI extends JavaPlugin {
    private static SAPI instance;

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
        log(Logger.LogType.info, "Stating SAPI");
        instance = this;

        this.saveDefaultConfig();

        NatsClient.connectNats();

        registerCommands();

        sbManager.createScoreboard();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerLoginQuitEvent(), this);
        //this.getServer().getServicesManager().register(Economy.class, new EconomyService(), getInstance(), ServicePriority.High);

    }


    @Override
    public void onDisable() {
        log(Logger.LogType.info, "Disabling SAPI");
    }

    private void registerCommands() {
        CommandHandler handler = new CommandHandler();
        handler.registerCommands(new AdminCommands());
    }

}
