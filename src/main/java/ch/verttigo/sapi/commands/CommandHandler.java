package ch.verttigo.sapi.commands;

import ch.verttigo.sapi.SAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandHandler implements CommandExecutor {
    private final Logger logger = SAPI.getInstance().getLogger();

    private final Map<String, RegisteredCommand> registeredCommandTable = new HashMap<>();

    private final CommandResultHandler resultHandler = (result, sender, command) -> {

        if (result == CommandResult.SUCCESSFUL) {
            return;
        }
        String error = ChatColor.RED + command.annotation.getDefaultError();

        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage((result == CommandResult.REFLECTION_ERROR ? error : result.getResultMessage()));
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Usage : " + command.annotation.getUsage());
            return;
        }
        sender.sendMessage((result == CommandResult.REFLECTION_ERROR ? error : result.getResultMessage()));
        if (result != CommandResult.NO_PERMISSION) {
            sender.sendMessage(ChatColor.RED + "Usage : " + command.annotation.getUsage());
        }
    };

    public CommandHandler() {
        logger.log(Level.CONFIG, "Handling Commands...");
    }

    public void registerCommands(Object object) {

        for (Method method : object.getClass().getMethods()) {
            SAPICommand annotation = method.getAnnotation(SAPICommand.class);
            if (annotation != null) {
                String base = annotation.getCommand().split(" ")[0];
                PluginCommand pluginCommand = SAPI.getInstance().getServer().getPluginCommand(base);
                if (pluginCommand == null) {
                    throw new RuntimeException(String.format("Command Registration Failed", base));
                } else {
                    pluginCommand.setExecutor(this);
                    registeredCommandTable.put(annotation.getCommand(), new RegisteredCommand(method, object, annotation));
                    logger.log(Level.INFO, "Registered " + pluginCommand.getName());
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        StringBuilder builder = new StringBuilder();
        for (int i = -1; i <= args.length - 1; i++) {

            if (i == -1) {
                builder.append(command.getName().toLowerCase());
            } else {
                builder.append(" ").append(args[i].toLowerCase());
            }

            for (String usage : registeredCommandTable.keySet()) {
                if (usage.contentEquals(builder)) {
                    RegisteredCommand wrapper = registeredCommandTable.get(usage);
                    SAPICommand annotation = wrapper.annotation;

                    String[] actualParams = Arrays.copyOfRange(args, annotation.getCommand().split(" ").length - 1, args.length);

                    if (!(sender instanceof Player) && !annotation.hasConsoleSupport()) {
                        resultHandler.handleResult(CommandResult.NOT_PLAYER, sender, wrapper);
                        return true;
                    }


                    if (actualParams.length != annotation.getParameters() && !annotation.canOverrideParameterLimit()) {
                        if (actualParams.length > annotation.getParameters()) {
                            resultHandler.handleResult(CommandResult.REDUNDANT_PARAMETER, sender, wrapper);
                            return true;
                        } else if (actualParams.length < annotation.getParameters()) {
                            resultHandler.handleResult(CommandResult.INSUFFICIENT_PARAMETER, sender, wrapper);
                            return true;
                        }
                    }

                    if (sender instanceof Player player) {

                        if (!player.hasPermission(annotation.getPermission())) {
                            resultHandler.handleResult(CommandResult.NO_PERMISSION, sender, wrapper);
                            return true;
                        }

                    }

                    try {
                        wrapper.method.invoke(wrapper.instance, sender, actualParams);
                        resultHandler.handleResult(CommandResult.SUCCESSFUL, sender, wrapper);
                        return true;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        resultHandler.handleResult(CommandResult.REFLECTION_ERROR, sender, wrapper);
                        e.printStackTrace();
                    }
                    return true;
                }
            }
        }

        resultHandler.handleResult(CommandResult.COMMAND_NOT_FOUND, sender, null);
        return true;
    }


    enum CommandResult {

        SUCCESSFUL(""),
        INSUFFICIENT_PARAMETER(ChatColor.RED + "Please insert valid parameters! (Too Short)"),
        REDUNDANT_PARAMETER(ChatColor.RED + "Please insert valid parameters! (Too Long)"),
        NO_PERMISSION(ChatColor.RED + "You do not have the permission to execute this command"),
        NOT_PLAYER(ChatColor.RED + "You need to be a player to execute this command"),
        COMMAND_NOT_FOUND(ChatColor.RED + "We could not find this command"),
        WRONG_SERVER(ChatColor.RED + "You cannot execute this command on this server"),
        REFLECTION_ERROR(ChatColor.RED + "Invalid registration! Please check the commands registry.");

        private final String resultMessage;

        CommandResult(String resultMessage) {
            this.resultMessage = resultMessage;
        }

        public String getResultMessage() {
            return resultMessage;
        }
    }

    public interface CommandResultHandler {
        void handleResult(CommandResult reason, CommandSender sender, RegisteredCommand command);
    }

    final static class RegisteredCommand {
        private final Object instance;

        private final Method method;
        private final SAPICommand annotation;

        RegisteredCommand(Method method, Object instance, SAPICommand annotation) {
            this.method = method;
            this.instance = instance;
            this.annotation = annotation;
        }
    }
}

