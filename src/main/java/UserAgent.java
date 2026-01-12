public class UserAgent {
    final String platform;
    final String browser;

    public UserAgent(String line) {
        if (line.toLowerCase().contains("windows")) {
            this.platform = "Windows";
        } else if (line.toLowerCase().contains("linux")) {
            this.platform = "Linux";
        } else if (line.toLowerCase().contains("mac")) {
            this.platform = "macOS";
        } else {
            this.platform = "other";
        }

        if (line.toLowerCase().contains("firefox")) {
            this.browser = "Firefox";
        } else if (line.toLowerCase().contains("chrome") & line.toLowerCase().contains("safari")) {
            this.browser = "Chrome";
        } else if (line.toLowerCase().contains("safari") & !line.toLowerCase().contains("chrome")) {
            this.browser = "Safari";
        } else if (line.toLowerCase().contains("opr") || line.toLowerCase().contains("opera")) {
            this.browser = "Opera";
        } else if (line.toLowerCase().contains("edg")) {
            this.browser = "Edge";
        } else {
            this.browser = "other";
        }
    }

    public String getPlatform() {
        return platform;
    }

    public String getBrowser() {
        return browser;
    }
}
