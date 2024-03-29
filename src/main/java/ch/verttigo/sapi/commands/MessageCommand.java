package ch.verttigo.sapi.commands;

public class MessageCommand {

    /*
    @CraftokCommand(getCommand = "msg", getDescription = "Send a message", getParameters = 2, getUsage = "/msg <player> <message>", canOverrideParameterLimit = true, getDefaultError = "Joueur hors-ligne ou un probleme est survenue lors de votre message")
    public void onMessage(CommandSender sender, String[] args) {
        Optional<User> user = userManager.getUserByName(args[0]);
        Player player = (Player) sender;
        if (user.isPresent()) {
            if (canMessage(player.getUniqueId(), user.get())) {
                StringBuilder msg = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    msg.append(args[i] + " ");
                }
                user.get().sendMessage("§7[§a" + sender.getName() + " §7→ §c" + user.get().getName() + "§7] §e: " + msg);
                sender.sendMessage("§7[§a" + sender.getName() + " §7→ §6" + user.get().getName() + "§7] §e: " + msg);
            } else {
                sender.sendMessage(ChatColor.RED + "Vous ne pouvez pas envoyer un message a ce joueur");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Joueur hors-ligne");
        }
    }

     */

}
