import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Получаем два числа из консоли
        System.out.println("Введите первое число:");
        int numberOne = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число:");
        int numberTwo = new Scanner(System.in).nextInt();

        //Вычисляем сумму двух чисел
        int addition = numberOne + numberTwo;
        System.out.println("сумма: " + numberOne + " + " + numberTwo + " = " + addition);

        //Вычисляем разность двух чисел
        int subtraction = numberOne - numberTwo;
        System.out.println("разность: " + numberOne + " - " + numberTwo + " = " + subtraction);

        //Вычисляем произведение двух чисел
        int multiplication = numberOne * numberTwo;
        System.out.println("произведение: " + numberOne + " * " + numberTwo + " = " + multiplication);

        //Вычисляем частное двух чисел
        double division = (double)numberOne / numberTwo;
        System.out.println("частное: " + numberOne + " / " + numberTwo + " = " + division);
    }
}
