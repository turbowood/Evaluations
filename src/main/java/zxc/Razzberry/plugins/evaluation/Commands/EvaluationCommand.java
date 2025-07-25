package zxc.Razzberry.plugins.evaluation.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import zxc.Razzberry.plugins.evaluation.ConfigHelper;
import zxc.Razzberry.plugins.evaluation.Evaluation;

import static zxc.Razzberry.plugins.evaluation.Evaluation.noPermission;
import static zxc.Razzberry.plugins.evaluation.Evaluation.withColor;

public class EvaluationCommand implements CommandExecutor {

    ConfigHelper evaluations = Evaluation.getEvaluations();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equalsIgnoreCase("help")) {
            if (!sender.hasPermission("evaluation.help")) {
                sender.sendMessage(noPermission);
                return true;
            }

            sender.sendMessage(withColor("&e---------------"));
            sender.sendMessage("");
            sender.sendMessage(withColor("&6/ev help &f- this page."));
            sender.sendMessage(withColor("&6/rate <0 .. 10> <comment> &f- rate the server"));
            sender.sendMessage(withColor("&6/ev find <player> &f- See the player's opinion"));
            sender.sendMessage("");
            sender.sendMessage(withColor("&e---------------"));
        }
        else if (args[0].equalsIgnoreCase("find")) {
            if (!sender.hasPermission("evaluation.find")) {
                sender.sendMessage(noPermission);
                return true;
            }

            if (args[1].isEmpty() || args[1] == null) {
                return false;
            }

            sender.sendMessage(withColor("&ePlayer: &6" + args[1]));
            sender.sendMessage("");
            sender.sendMessage(withColor("&eName: &f" + args[1]));
            sender.sendMessage(withColor("&eRate: &f" + evaluations.getInt(args[1] + ".rate", -1)));
            sender.sendMessage(withColor("&eComment: &f" + evaluations.getString(args[1] + ".text", "none")));
            sender.sendMessage(withColor(""));
        }
        return true;
    }
}
