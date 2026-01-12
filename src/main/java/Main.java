import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static int lineCount;
    public static void main(String[] args) {
        //бесконечный цикл while, в котором в консоли запрашивается путь к файлу
        int sumPath = 0;
        int maxLengthLine, minLengthLine;
        while (true) {
            System.out.println("Укажите путь к файлу:");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            if (!fileExists) {
                System.out.println("Указанный файл не существует");
                continue;
            }
            boolean isDirectory = file.isDirectory();
            if (!isDirectory) {
                lineCount = 0;
                int sumYandexBot = 0;
                int sumGooglebot = 0;
                double fractionYandexBot = 0.0;
                double fractionGooglebot = 0.0;
                Statistics stat = new Statistics();
                maxLengthLine = 0;
                minLengthLine = 1024;
                sumPath++;
                System.out.println("Путь указан верно. Это файл номер N" + sumPath);

                try {
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader = new BufferedReader(fileReader);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lineCount++;
                        int length = line.length();
                        if (length > 1024) throw new RuntimeException("количество символов в строке первышает 1024");
                        LogEntry log = new LogEntry(line);
                        //UserAgent uAgent = new UserAgent(log.userAgent);
                        //if (userAgent.contains("YandexBot")) sumYandexBot++;
                        //if (userAgent.contains("Googlebot")) sumGooglebot++;
                        stat.addEntry(log);
                        //System.out.println("ip - " + log.ipAddr);
                        //System.out.println("dateTime - " + log.time);
                        //System.out.println("httpMethod - " + log.httpMethod);
                        //System.out.println("path - " + log.path);
                        //System.out.println("httpCodeResponse - " + log.responseCode);
                        //System.out.println("sizeResponse - " + log.responseSize);
                        //System.out.println("referer - " + log.referer);
                        //System.out.println("userAgent - " + log.userAgent);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //fractionYandexBot = Math.round(((sumYandexBot * 10000.0) / lineCount)) / 100.0;
                //fractionGooglebot = Math.round(((sumGooglebot * 10000.0) / lineCount)) / 100.0;
                System.out.println("общее количество строк в файле - " + lineCount);
                System.out.println("minTime - " + stat.getMinTime());
                System.out.println("maxTime - " + stat.getMaxTime());
                System.out.println("объем часового трафика - " + stat.getTrafficRate());
                System.out.println("все существующие страницы - " + stat.getAllExistingPages());
                System.out.println("статистика ОС - " + stat.getPlatformRate());
                //System.out.println("TrafficRate - " + st.getTrafficRate());
                //System.out.println("Доля запросов YandexBot - " + fractionYandexBot + " %, общее количество запросов - " + sumYandexBot);
                //System.out.println("Доля запросов Googlebot - " + fractionGooglebot + " %, общее количество запросов - " + sumGooglebot);

                //System.out.println("длина самой длинной строки в файле - " + maxLengthLine);
                //System.out.println("длина самой короткой строки в файле - " + minLengthLine);
            } else {
                System.out.println("Указанный путь является путём к папке");
            }
        }
    }
}
