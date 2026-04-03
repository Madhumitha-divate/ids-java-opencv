package config;


public class AppConfig {

    // ✅ Paste your Telegram Bot Token here
    public static final String BOT_TOKEN = "8645531078:AAGJLuGopXe4Mo-dRgbLmaP6SEE6PF8661I";

    // ✅ Paste your Telegram Chat ID here
    // How to get Chat ID:
    // 1. Message your bot on Telegram
    // 2. Open this URL in browser:
    //    https://api.telegram.org/bot<YOUR_BOT_TOKEN>/getUpdates
    // 3. Find "chat" -> "id" in the response
    public static final String CHAT_ID = "6446415715";

    // ✅ Cooldown in seconds between alerts (avoid scam)
    public static final int ALERT_COOLDOWN_SECONDS = 10;

    // ✅ Path to haarcascade XML file
    public static final String CASCADE_PATH =
        System.getProperty("user.dir") + "/src/haarcascade_frontalface_default.xml";
}
