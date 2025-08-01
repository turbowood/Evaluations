package zxc.Razzberry.plugins.evaluation.Commands;

import org.bukkit.Bukkit;
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
    ConfigHelper messages = Evaluation.getMessages();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(withColor(messages.getString("noPlayer", "Not from you :D")));
            return true;
        }

        Player player = (Player) sender;

        int rate = 0;
        try {
            rate = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e) {
            player.sendMessage(withColor(messages.getString("error-rate", "You have not entered a rating from 0 to 10!")));
            return false;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

        if (rate < 0 || rate > 10) {
            player.sendMessage(withColor(messages.getString("error-rate", "You have not entered a rating from 0 to 10!")));
            return false;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            builder.append(args[i]);
            if (!(i == args.length - 1)) builder.append(" ");
        }

        player.sendMessage(withColor(messages.getString("ready-rate", "You have left a review about the server, thank you!")));
        saveReview(player.getUniqueId().toString(), rate, builder.toString(), player);

        return true;
    }

    private void saveReview(String uuid, int rate, String comment, Player player) {
        if (evaluations.contains(uuid + ".rate")) {
            int oldRate = evaluations.getInt(uuid + ".rate", -1);
            if (allrate.contains(oldRate)) {
                allrate.remove((Integer) oldRate);
            }
        }

        String namePlayer = Bukkit.getPlayer(player.getUniqueId()).getName();
        evaluations.set(uuid + ".nickname", namePlayer);
        evaluations.set(uuid + ".rate", rate);
        evaluations.set(uuid + ".text", comment);
        allrate.add(rate);

        evaluations.set("arithmetic-mean", Math.round(getAverage(allrate) * 10) / 10.0);
        config.set("all", allrate);

        if (tgEnable)
            telegram.sendToTelegram("New rate!\nName: " + namePlayer + "\nRate: " + rate + "\nComment: " + comment);
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
