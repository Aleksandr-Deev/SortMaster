import entity.Barrel;
import service.BarrelService;
import utils.InputUtils;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BarrelService<String> barrelService = new BarrelService<>();  // Создание экземпляра класса
        List<Barrel<String>> barrels = new ArrayList<>(); // Создание списка

        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1 - Заполнить данные");
            System.out.println("2 - Сортировать данные");
            System.out.println("3 - Найти элемент");
            System.out.println("4 - Выход");

            int choice = InputUtils.getIntInput("Ваш выбор: ");  // Статический метод вызван через класс

            switch (choice) {
                case 1:
                    barrels = barrelService.fillData();  // Нестатический метод вызван через экземпляр.
                    break;
                case 2:
                    barrelService.sort(barrels);  // Нестатический метод вызван через экземпляр.
                    System.out.println("Данные отсортированы.");
                    break;
                case 3:
                    double volumeToSearch = InputUtils.getDoubleInput("Введите объем для поиска: ");  // Нестатический метод вызван через экземпляр.
                    Barrel<String> key = new Barrel.Builder<String>().volume(volumeToSearch).build();
                    int index = barrelService.search(barrels, key);  // Нестатический метод вызван через экземпляр.
                    System.out.println(index != -1
                            ? "Элемент найден: " + barrels.get(index) // Нестатический метод вызван через экземпляр.
                            : "Элемент не найден.");
                    break;
                case 4:
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Некорректный выбор! Попробуйте снова.");
            }
        }
    }
}