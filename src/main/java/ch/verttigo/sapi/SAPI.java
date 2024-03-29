package ch.verttigo.sapi;


import ch.verttigo.sapi.commands.AdminCommands;
import ch.verttigo.sapi.commands.CommandHandler;
import ch.verttigo.sapi.economy.EconomyService;
import ch.verttigo.sapi.listeners.PlayerLoginQuitEvent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

public class SAPI extends JavaPlugin {
    private static SAPI instance;
    private final Server server = getServer();

    public static SAPI getInstance() {
        return instance;
    }
    private String prefix;
    private File configFile;
    @Override
    public void onLoad() {
        instance = this;
        prefix = "§c§lSlick§b§lAPI §8➟ §f";
        configFile = new File(getDataFolder(), "config.yml");
    }

    @Override
    public void onEnable() {
        instance = this;

        if(!configFile.exists()) {
            try(InputStream in = this.getResource("config.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }

            getLogger().severe("----[!] CONFIG.YML NOT CONFIGURED!----");
            getPluginLoader().disablePlugin(this);
            return;
        }

        try {
            getConfig().load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }



        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerLoginQuitEvent(), this);
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
