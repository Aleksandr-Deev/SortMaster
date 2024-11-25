package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUtils {

    private static final Scanner scanner = new Scanner(System.in);

    public static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите целое число!");
                scanner.next(); // Очистка неверного ввода
            }
        }
    }

    public static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите числовое значение!");
                scanner.next(); // Очистка неверного ввода
            }
        }
    }

    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }
}
