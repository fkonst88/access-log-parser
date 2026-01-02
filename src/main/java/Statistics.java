import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class Statistics {
    private double totalTraffic;
    private LocalDateTime minTime,maxTime;

    public void addEntry(LogEntry logEntry){
        this.totalTraffic +=  logEntry.responseSize;
        if (logEntry.time != null){
            if (logEntry.time.isBefore(this.minTime)) this.minTime = logEntry.time;
            if (logEntry.time.isAfter(this.maxTime)) this.maxTime = logEntry.time;
        }
    }

    public double getTrafficRate(){
        double trafficRate;
        int hours;
        hours = (int) ChronoUnit.HOURS.between(minTime, maxTime);
        //System.out.println("hours - " + hours);
        //System.out.println("totalTraffic - " + totalTraffic);
        trafficRate = totalTraffic / hours;
        return trafficRate;
    }

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = LocalDateTime.now();
        this.maxTime = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC);;
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
