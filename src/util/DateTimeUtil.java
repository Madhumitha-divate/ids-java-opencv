package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter FILE_FORMATTER =
        DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    // ✅ For display and Telegram message
    public static String getCurrentTime() {
        return LocalDateTime.now().format(FORMATTER);
    }

    // ✅ For safe file naming (no colons)
    public static String getFileTimestamp() {
        return LocalDateTime.now().format(FILE_FORMATTER);
    }
}
