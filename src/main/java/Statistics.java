import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {
    //общий объём данных, отданных сервером
    private double totalTraffic;
    //общее количество посещений не ботов
    private double totalUserVisit;
    //минмальное и максимальное время лога
    private LocalDateTime minTime, maxTime;
    //список всех существующих страниц сайта
    private HashSet<String> existingPagesSet = new HashSet<>();
    //список всех несуществующих страниц сайта
    private HashSet<String> errorPagesSet = new HashSet<>();
    //частота встречаемости каждой операционной системы
    private HashMap<String, Integer> platformCount = new HashMap<>();
    //частота встречаемости каждого браузера
    private HashMap<String, Integer> browserCount = new HashMap<>();
    //средняя посещаемость одним пользователем
    private HashMap<String, Integer> uniqueIpCount = new HashMap<>();
    //общее количество ошибочных запросов
    public int errorResponce = 0;

    public void addEntry(LogEntry logEntry) {
        UserAgent uAgent = new UserAgent(logEntry.userAgent);
        //считаем общий объём данных
        this.totalTraffic += logEntry.responseSize;
        //если не бот
        if (!uAgent.isBot()) {
            //считаем общее количество посещений пользователей при условии, что это не bot
            this.totalUserVisit ++;
            //считаем частоту посещаемости одним пользователем при условии, что это не bot
            if (uniqueIpCount.containsKey(logEntry.ipAddr)) {
                uniqueIpCount.put(logEntry.ipAddr, uniqueIpCount.get(logEntry.ipAddr) + 1);
            } else {
                uniqueIpCount.put(logEntry.ipAddr, 1);
            }
        }
        //определяем минимальное и максимальное время лога
        if (logEntry.time != null) {
            if (logEntry.time.isBefore(this.minTime)) this.minTime = logEntry.time;
            if (logEntry.time.isAfter(this.maxTime)) this.maxTime = logEntry.time;
        }
        //считаем количество существующих страниц сайта
        if (logEntry.responseCode == 200) {
            existingPagesSet.add(logEntry.path);
        }
        //считаем количество несуществующих страниц сайта
        if (logEntry.responseCode == 404) {
            errorPagesSet.add(logEntry.path);
        }
        //считаем количество ответов с ошибкой
        if (logEntry.responseCode >= 400 && logEntry.responseCode <= 599) this.errorResponce ++;
        //считаем частоту встречаемости каждой операционной системы
        if (platformCount.containsKey(uAgent.platform)) {
            platformCount.put(uAgent.platform, platformCount.get(uAgent.platform) + 1);
        } else {
            platformCount.put(uAgent.platform, 1);
        }
        //считаем частоту встречаемости каждого браузера
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

    public double getUniqueIpRate() {
        //средняя посещаемость одним пользователем
        //System.out.println("this.totalUserVisit - " + this.totalUserVisit + " uniqueIpCount - " + uniqueIpCount);
        return Math.round(this.totalUserVisit / uniqueIpCount.size() * 100.00) / 100.00;
    }

    public double getUserVisitRate() {
        //количество посещений не ботов в час
        double UserVisitRate;
        //разница между maxTime и minTime в часах
        int hours = getDiffMinMaxTime();
        UserVisitRate = totalUserVisit / hours;
        UserVisitRate = Math.round(UserVisitRate * 100.00) / 100.00;
        return UserVisitRate;
    }

    public double getTrafficRate() {
        //количество трафика в час
        double trafficRate;
        //разница между maxTime и minTime в часах
        int hours = getDiffMinMaxTime();
        trafficRate = totalTraffic / hours;
        trafficRate = Math.round(trafficRate * 100.00) / 100.00;
        return trafficRate;
    }

    public double getErrorResponceRate() {
        //количество ошибочных запросов в час
        double errorResponceRate;
        //разница между maxTime и minTime в часах
        int hours = getDiffMinMaxTime();
        errorResponceRate = (double) errorResponce / hours;
        errorResponceRate = Math.round(errorResponceRate * 100.00) / 100.00;
        return errorResponceRate;
    }

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = LocalDateTime.now();
        this.maxTime = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC);
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

    public int getDiffMinMaxTime() {
        return (int) ChronoUnit.HOURS.between(minTime, maxTime);
    }
}
