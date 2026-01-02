import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntry {
    final String ipAddr;
    final LocalDateTime time;
    final EhttpMethod httpMethod;
    final String path;
    final int responseCode;
    final int responseSize;
    final String referer;
    final String userAgent;

    public String getIpAddr() {
        return ipAddr;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public EhttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public LogEntry(String line) {
        String regex = "^(\\S+)\\s+" +
                "(\\S+)\\s+(\\S+)\\s+" +
                "\\[([^]]+)\\]\\s+" +
                "\"(\\S+\\s+[^\"]+)\"\\s+" +
                "(\\d+)\\s+" +
                "(\\d+)\\s+" +
                "\"([^\"]*)\"\\s+" +
                "\"([^\"]*)\"$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        boolean isHmethod = false;

        if (matcher.matches()) {
            this.ipAddr = matcher.group(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
            this.time = OffsetDateTime.parse(matcher.group(4), formatter).toLocalDateTime();
            String[] tmpArr = matcher.group(5).split(" ");
            for (EhttpMethod ehm : EhttpMethod.values()) {
                if (ehm.name().equalsIgnoreCase(tmpArr[0])) {
                    isHmethod = true;
                }
            }
            if (isHmethod) {
                this.httpMethod = EhttpMethod.valueOf(tmpArr[0].toUpperCase());
            } else {
                this.httpMethod = null;
                System.out.println("ОШИБКА: некорректный http метод, строка - " + Main.lineCount);
            }
            this.path = tmpArr[1];
            this.responseCode = Integer.parseInt(matcher.group(6));
            this.responseSize = Integer.parseInt(matcher.group(7));
            this.referer = matcher.group(8);
            this.userAgent = matcher.group(9);
        } else {
            System.out.println("ОШИБКА: некорректный формат строки, строка - " + Main.lineCount);
            this.ipAddr = "";
            this.time = null;
            this.httpMethod = null;
            this.path = "";
            this.responseCode = 0;
            this.responseSize = 0;
            this.referer = "";
            this.userAgent = "";
        }

    }
}
