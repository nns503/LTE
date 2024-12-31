package lte.backend.util.formatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomTimeFormatter {

    public static String formatToDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
