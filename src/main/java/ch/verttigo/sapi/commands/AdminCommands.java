package ch.verttigo.sapi.commands;

import org.bukkit.command.CommandSender;

public class AdminCommands {

    @SAPICommand(getCommand = "sapi refresh", getDescription = "Refresh cache data", getUsage = "/sapi refresh",
            getPermission = "sapi.refresh", canOverrideParameterLimit = false, hasConsoleSupport = true)
    public void sapiRefreshCommand(CommandSender sender, String[] args) {

    }
    //Coins ADD
    //coins Set
    //Coins deduct
    //SAPI cacheInfo
}
