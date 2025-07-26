package zxc.Razzberry.plugins.evaluation;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import zxc.Razzberry.plugins.evaluation.Commands.EvaluationCommand;
import zxc.Razzberry.plugins.evaluation.Commands.RateCommand;

import java.util.ArrayList;
import java.util.List;

public final class Evaluation extends JavaPlugin {

    private static Evaluation instance;
    private static ConfigHelper config;
    private static ConfigHelper evaluations;
    private static ConfigHelper messages;

    public static List<Integer> allrate = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        config = new ConfigHelper(this, "config.yml");
        evaluations = new ConfigHelper(this, "evaluations.yml");
        messages = new ConfigHelper(this, "messages.yml");

        allrate = config.getIntegerList("all");

        getCommand("evaluation").setExecutor(new EvaluationCommand());
        getCommand("rate").setExecutor(new RateCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ConfigHelper getConfigHelper() {
        return config;
    }

    public static ConfigHelper getEvaluations() {
        return evaluations;
    }

    public static ConfigHelper getMessages() {
        return messages;
    }

    public static Evaluation getInstance() {
        return instance;
    }

    public static String withColor(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
