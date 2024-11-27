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
                scanner.nextLine(); // Очистка неверного ввода (например, если ввели текст)
            }
        }
    }

    public static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите корректное числовое значение!");
                scanner.nextLine(); // Очистка неверного ввода (например, если ввели буквы)
            }
        }
    }

    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        // nextLine() для считывания всей строки
        return scanner.nextLine();
    }
}