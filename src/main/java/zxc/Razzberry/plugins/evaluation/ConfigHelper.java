package zxc.Razzberry.plugins.evaluation;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigHelper {
    private final JavaPlugin plugin;
    private final String fileName;
    private FileConfiguration config;
    private File configFile;

    // Конструктор (если нужен кастомный файл, например, data.yml)
    public ConfigHelper(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        setup();
    }

    // Создает файл конфига, если его нет
    public void setup() {
        configFile = new File(plugin.getDataFolder(), fileName);

        if (!configFile.exists()) {
            plugin.saveResource(fileName, false); // Копирует из ресурсов плагина
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    // Получаем конфиг (FileConfiguration)
    public FileConfiguration get() {
        return config;
    }

    // Сохраняет изменения в файл
    public void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Не удалось сохранить " + fileName + ": " + e.getMessage());
        }
    }

    // Перезагружает конфиг (например, после ручного изменения файла)
    public void reload() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    // ===== Быстрые методы для чтения (с значениями по умолчанию) =====
    public String getString(String path, String def) {
        return config.getString(path, def);
    }

    public int getInt(String path, int def) {
        return config.getInt(path, def);
    }

    public boolean getBoolean(String path, boolean def) {
        return config.getBoolean(path, def);
    }

    public double getDouble(String path, double def) {
        return config.getDouble(path, def);
    }

    public List<Integer> getIntegerList(String path) {
        return config.getIntegerList(path);
    }

    // ===== Методы для записи =====
    public void set(String path, Object value) {
        config.set(path, value);
        save(); // Автосохранение (можно убрать, если не нужно)
    }

    public boolean contains(String content) {
        return config.contains(content);
    }
}