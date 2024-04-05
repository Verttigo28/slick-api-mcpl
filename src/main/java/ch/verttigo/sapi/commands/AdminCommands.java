package ch.verttigo.sapi.commands;

import ch.verttigo.sapi.SAPI;
import ch.verttigo.sapi.manager.user.SUser;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;

public class AdminCommands {

    @SAPICommand(getCommand = "coins add", getDescription = "Add coins", getUsage = "/coins add <player> <amount>",
            getPermission = "sapi.admin", getParameters = 2, hasConsoleSupport = true, getDefaultError = "Joueur introuvable")
    public void addCoinsCommand(CommandSender sender, String[] args) {
        Instant start = Instant.now();
        Player receiver = Bukkit.getPlayer(args[0]);
        if (receiver != null) {
            SUser su = new SUser(receiver.getUniqueId());
            boolean success = su.setCoins(su.getCoins() + Integer.parseInt(args[1]));
            if (success) {
                sender.sendMessage(SAPI.getPrefix() + "Successfuly credit coins to the player");
            } else {
                sender.sendMessage(SAPI.getPrefix() + "Coulnd't add coins to player");
            }
        } else {
            //TODO User not found on server, might be on the server, will need to implement cloudnet
            sender.sendMessage(SAPI.getPrefix() + "User not found");
        }
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        sender.sendMessage(SAPI.getPrefix() +" Took : " + timeElapsed + "ms");
    }
    //Coins ADD
    //coins Set
    //Coins deduct
    //SAPI cacheInfo
}
