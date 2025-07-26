package zxc.Razzberry.plugins.evaluation;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TelegramNotifier {
    private final JavaPlugin plugin;
    private final String botToken;
    private final String chatId;

    public TelegramNotifier(JavaPlugin plugin, String botToken, String chatId) {
        this.plugin = plugin;
        this.botToken = botToken;
        this.chatId = chatId;
    }

    // Асинхронная отправка сообщения
    public void sendToTelegram(String message) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                // Формируем URL для API Telegram
                String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";

                // Кодируем сообщение для URL
                String encodedMsg = URLEncoder.encode(message, "UTF-8");
                String postData = "chat_id=" + chatId + "&text=" + encodedMsg + "&parse_mode=HTML";

                // Настраиваем HTTP-подключение
                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                // Отправляем данные
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(postData.getBytes(StandardCharsets.UTF_8));
                }

                // Проверяем ответ
                if (conn.getResponseCode() != 200) {
                    plugin.getLogger().warning("Ошибка Telegram API: HTTP " + conn.getResponseCode());
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Ошибка при отправке в Telegram: " + e.getMessage());
            }
        });
    }
}