import entity.Barrel;
import service.BarrelService;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BarrelService<String> barrelService = new BarrelService<>();

        List<Barrel<String>> barrels = new ArrayList<>();


        while (true) {
            // Меню для пользователя
            System.out.println("Выберите действие:");
            System.out.println("1 - Заполнить данные");
            System.out.println("2 - Сортировать данные");
            System.out.println("3 - Найти элемент");
            System.out.println("4 - Выход");

            // Выбор пользователя
            int choice = getIntInput(scanner, "Ваш выбор: ");

            switch (choice) {
                case 1:
                    // Заполнение данных
                    barrels = barrelService.fillData();
                    break;
                case 2:
                    // Сортировка данных
                    barrelService.sort(barrels);
                    System.out.println("Данные отсортированы.");
                    break;
                case 3:
                    // Поиск элемента
                    double volumeToSearch = getDoubleInput(scanner, "Введите объем для поиска: ");
                    Barrel<String> key = new Barrel.Builder<String>().volume(volumeToSearch).build();
                    int index = barrelService.search(barrels, key);
                    if (index != -1) {
                        System.out.println("Элемент найден: " + barrels.get(index));
                    } else {
                        System.out.println("Элемент не найден.");
                    }
                    break;
                case 4:

                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Некорректный выбор! Попробуйте снова.");
            }
        }
    }

    // Целое число от пользователя
    private static int getIntInput(Scanner scanner, String prompt) {
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

    // Число с плавающей точкой от пользователя
    private static double getDoubleInput(Scanner scanner, String prompt) {
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
}