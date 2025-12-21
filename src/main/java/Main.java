import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        //бесконечный цикл while, в котором в консоли запрашивается путь к файлу
        int sumPath = 0;
        int lineCount,maxLengthLine,minLengthLine;
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
                maxLengthLine = 0;
                minLengthLine = 1024;
                sumPath++;
                System.out.println("Путь указан верно. Это файл номер N" + sumPath);
                try{
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader = new BufferedReader(fileReader);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lineCount ++;
                        int length = line.length();
                        if (length > 1024) throw new RuntimeException("количество символов в строке первышает 1024");
                        if (length > maxLengthLine) maxLengthLine = length;
                        if (length < minLengthLine) minLengthLine = length;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println("общее количество строк в файле - " + lineCount);
                System.out.println("длина самой длинной строки в файле - " + maxLengthLine);
                System.out.println("длина самой короткой строки в файле - " + minLengthLine);
            } else {
                System.out.println("Указанный путь является путём к папке");
            }
        }
    }
}
