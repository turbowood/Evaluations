package zxc.Razzberry.plugins.evaluation.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import zxc.Razzberry.plugins.evaluation.ConfigHelper;
import zxc.Razzberry.plugins.evaluation.Evaluation;

import java.util.List;

import static zxc.Razzberry.plugins.evaluation.Evaluation.*;

public class RateCommand implements CommandExecutor {
    ConfigHelper evaluations = Evaluation.getEvaluations();
    ConfigHelper config = Evaluation.getConfigHelper();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Not from you :D");
            return true;
        }
        else if (!sender.hasPermission("evaluation.rate")) {
            sender.sendMessage(noPermission);
            return true;
        }

        int rate = 0;
        try {
            rate = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e) {
            sender.sendMessage(withColor("&cYou have not entered a rating from 0 to 10!"));
            return false;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

        if (rate < 0 || rate > 10) {
            sender.sendMessage(withColor("&cNumber from 0 to 10!"));
            return false;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            builder.append(args[i]);
            if (!(i == args.length - 1)) builder.append(" ");
        }

        sender.sendMessage("You have left a review about the server, thank you! (" + rate + "/10)");
        sender.sendMessage("Text: " + builder.toString());
        saveReview(sender.getName(), rate, builder.toString());

        return true;
    }

    private void saveReview(String name, int rate, String comment) {
        evaluations.set(name, null);
        evaluations.set(name + ".rate", rate);
        evaluations.set(name + ".text", comment);

        allrate.add(rate);

        evaluations.set("arithmetic-mean", Math.round(getAverage(allrate) * 10) / 10.0);
        config.set("all", allrate);
    }

    private double getAverage(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0.0;
        }

        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        return (double) sum / numbers.size();
    }
}
