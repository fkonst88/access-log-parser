import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {
    private double totalTraffic;
    private LocalDateTime minTime, maxTime;
    private HashSet<String> existingPagesSet = new HashSet<>();
    private HashSet<String> errorPagesSet = new HashSet<>();
    private HashMap<String, Integer> platformCount = new HashMap<>();
    private HashMap<String, Integer> browserCount = new HashMap<>();

    public void addEntry(LogEntry logEntry) {
        UserAgent uAgent = new UserAgent(logEntry.userAgent);
        this.totalTraffic += logEntry.responseSize;
        if (logEntry.time != null) {
            if (logEntry.time.isBefore(this.minTime)) this.minTime = logEntry.time;
            if (logEntry.time.isAfter(this.maxTime)) this.maxTime = logEntry.time;
        }

        if (logEntry.responseCode == 200) {
            existingPagesSet.add(logEntry.path);
        }

        if (logEntry.responseCode == 404) {
            errorPagesSet.add(logEntry.path);
        }

        if (platformCount.containsKey(uAgent.platform)) {
            platformCount.put(uAgent.platform, platformCount.get(uAgent.platform) + 1);
        } else {
            platformCount.put(uAgent.platform, 1);
        }

        if (browserCount.containsKey(uAgent.browser)) {
            browserCount.put(uAgent.browser, browserCount.get(uAgent.browser) + 1);
        } else {
            browserCount.put(uAgent.browser, 1);
        }
    }

    public HashSet getAllExistingPages() {
        return existingPagesSet;
    }

    public HashSet getAllErrorPages() {
        return errorPagesSet;
    }

    public HashMap getPlatformRate() {
        int sumAllPlatform = 0;
        HashMap<String, Double> platformRate = new HashMap<>();
        double currentRate = 0.00;

        for (int value : platformCount.values()) {
            sumAllPlatform += value;
        }

        for (String key : platformCount.keySet()) {
            currentRate = platformCount.get(key).doubleValue() / sumAllPlatform;
            currentRate = Math.round(currentRate * 100.00) / 100.00;
            platformRate.put(key, currentRate);
        }

        return platformRate;
    }

    public HashMap getBrowserRate() {
        int sumAllBrowser = 0;
        HashMap<String, Double> browserRate = new HashMap<>();
        double currentRate = 0.00;

        for (int value : browserCount.values()) {
            sumAllBrowser += value;
        }

        for (String key : browserCount.keySet()) {
            currentRate = browserCount.get(key).doubleValue() / sumAllBrowser;
            currentRate = Math.round(currentRate * 100.00) / 100.00;
            browserRate.put(key, currentRate);
        }

        return browserRate;
    }

    public double getTrafficRate() {
        double trafficRate;
        int hours;
        hours = (int) ChronoUnit.HOURS.between(minTime, maxTime);
        //System.out.println("hours - " + hours);
        //System.out.println("totalTraffic - " + totalTraffic);
        System.out.println("totalTraffic - " + totalTraffic);
        System.out.println("hours - " + hours);
        trafficRate = totalTraffic / hours;
        trafficRate = Math.round(trafficRate*100.00)/100.00;
        return trafficRate;
    }

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = LocalDateTime.now();
        this.maxTime = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC);
        ;
    }

    public double getTotalTraffic() {
        return totalTraffic;
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }
}
