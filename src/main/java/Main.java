import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        //бесконечный цикл while, в котором в консоли запрашивается путь к файлу
        int sumPath = 0;
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
                sumPath++;
                System.out.println("Путь указан верно. Это файл номер N" + sumPath);
            } else {
                System.out.println("Указанный путь является путём к папке");
            }
        }
    }
}
