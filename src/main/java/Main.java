import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        //бесконечный цикл while, в котором в консоли запрашивается путь к файлу
        int sumPath = 0;
        int lineCount, maxLengthLine, minLengthLine;
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
                maxLengthLine = 0;
                minLengthLine = 1024;
                sumPath++;
                System.out.println("Путь указан верно. Это файл номер N" + sumPath);
                String regex = "^(\\S+)\\s+" +
                        "(\\S+)\\s+(\\S+)\\s+" +
                        "\\[([^]]+)\\]\\s+" +
                        "\"(\\S+\\s+[^\"]+)\"\\s+" +
                        "(\\d+)\\s+" +
                        "(\\d+)\\s+" +
                        "\"([^\"]*)\"\\s+" +
                        "\"([^\"]*)\"$";
                Pattern pattern = Pattern.compile(regex);
                try {
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader = new BufferedReader(fileReader);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lineCount++;
                        int length = line.length();
                        Matcher matcher = pattern.matcher(line);
                        if (length > 1024) throw new RuntimeException("количество символов в строке первышает 1024");

                        if (matcher.matches()) {
                            String ip = matcher.group(1);
                            String dateTime = matcher.group(4);
                            String httpMethod = matcher.group(5);
                            String httpCodeResponse = matcher.group(6);
                            String sizeResponse = matcher.group(7);
                            String referer = matcher.group(8);
                            String userAgent = matcher.group(9);
                            if (userAgent.contains("YandexBot")) sumYandexBot++;
                            if (userAgent.contains("Googlebot")) sumGooglebot++;
                            //System.out.println("ip - " + ip);
                            //System.out.println("dateTime - " + dateTime);
                            //System.out.println("httpMethod - " + httpMethod);
                            //System.out.println("httpCodeResponse - " + httpCodeResponse);
                            //System.out.println("sizeResponse - " + sizeResponse);
                            //System.out.println("referer - " + referer);
                            //System.out.println("userAgent - " + userAgent);
                        } else {
                            System.out.println("некорректный формат строки N" + lineCount);
                        }
                        //if (length > maxLengthLine) maxLengthLine = length;
                        //if (length < minLengthLine) minLengthLine = length;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                fractionYandexBot = Math.round(((sumYandexBot * 10000.0) / lineCount)) / 100.0;
                fractionGooglebot = Math.round(((sumGooglebot * 10000.0) / lineCount)) / 100.0;
                System.out.println("общее количество строк в файле - " + lineCount);
                System.out.println("Доля запросов YandexBot - " + fractionYandexBot + " %, общее количество запросов - " + sumYandexBot);
                System.out.println("Доля запросов Googlebot - " + fractionGooglebot + " %, общее количество запросов - " + sumGooglebot);

                //System.out.println("длина самой длинной строки в файле - " + maxLengthLine);
                //System.out.println("длина самой короткой строки в файле - " + minLengthLine);
            } else {
                System.out.println("Указанный путь является путём к папке");
            }
        }
    }
}
