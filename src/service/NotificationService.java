package service;

import config.AppConfig;
import model.DetectionEvent;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class NotificationService {

    // ✅ Send Telegram text alert
    public void sendAlert(DetectionEvent event) {
        try {
            String message = "🚨 INTRUDER ALERT!\n"
                + "📅 Time     : " + event.getTime() + "\n"
                + "👤 Faces    : " + event.getFaceCount() + " detected\n"
                + "📸 Snapshot : " + event.getImagePath() + "\n"
                + "🔴 Please check your premises immediately!";

            String encodedMessage = URLEncoder.encode(message, "UTF-8");

            String urlString = "https://api.telegram.org/bot"
                + AppConfig.BOT_TOKEN
                + "/sendMessage?chat_id=" + AppConfig.CHAT_ID
                + "&text=" + encodedMessage;

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                System.out.println("📱 Telegram Alert Sent Successfully!");
            } else {
                System.out.println("❌ Telegram alert failed! Code: " + responseCode);
                System.out.println("👉 Check your BOT_TOKEN and CHAT_ID in AppConfig.java");
            }

            conn.disconnect();

        } catch (Exception e) {
            System.out.println("❌ Notification error: " + e.getMessage());
        }
    }
}
