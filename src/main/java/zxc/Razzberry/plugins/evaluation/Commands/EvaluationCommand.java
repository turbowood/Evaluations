package zxc.Razzberry.plugins.evaluation.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import zxc.Razzberry.plugins.evaluation.ConfigHelper;
import zxc.Razzberry.plugins.evaluation.Evaluation;

import static zxc.Razzberry.plugins.evaluation.Evaluation.withColor;

public class EvaluationCommand implements CommandExecutor {

    ConfigHelper evaluations = Evaluation.getEvaluations();
    ConfigHelper config = Evaluation.getConfigHelper();
    ConfigHelper messages = Evaluation.getMessages();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0 || args[0] == null || args[0].isEmpty()) {
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "help": {
                sendHelp(sender);
                break;
            }
            case "find": {
                if (args.length < 2) {
                    return false;
                }
                if (isValidArgs(sender, args[1])) {
                    findPlayer(sender, args[1]);
                } else return false;
                break;
            }
            case "reload": {
                config.reload();
                messages.reload();
            }
            default: {
                return false;
            }
        }
        return true;
    }

    public void findPlayer(CommandSender sender, String name) {
        String uuid = Bukkit.getPlayer(name).getUniqueId().toString();
        if (sender.hasPermission("evaluation.find.uuid")) sender.sendMessage(withColor("&eUUID: &6" + uuid));
        sender.sendMessage("");
        sender.sendMessage(withColor("&eName: &f" + name));
        sender.sendMessage(withColor("&eRate: &f" + evaluations.getInt(uuid + ".rate", -1)));
        sender.sendMessage(withColor("&eComment: &f" + evaluations.getString(uuid + ".text", "Unknown")));
        sender.sendMessage("");
    }

    private boolean isValidArgs(CommandSender sender, String arg) {
        if (arg.isEmpty()) {
            return false;
        }
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(withColor("&e---------------"));
        sender.sendMessage("");
        sender.sendMessage(withColor("&6/ev help &f- this page."));
        sender.sendMessage(withColor("&6/rate <0 .. 10> <comment> &f- rate the server"));
        sender.sendMessage(withColor("&6/ev find <player> &f- See the player's opinion"));
        sender.sendMessage("");
        sender.sendMessage(withColor("&e---------------"));
    }
}
