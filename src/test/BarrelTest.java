package test;

import entity.Barrel;
import service.BarrelService;

import java.util.ArrayList;
import java.util.List;

public class BarrelTest {
    public static void main(String[] args) {
        testWithStrings();
        testWithIntegers();
    }

    private static void testWithStrings() {
        List<Barrel<String>> barrels = new ArrayList<>(List.of(
                new Barrel.Builder<String>().volume(10.5).materialType("Water").material("Metal").build(),
                new Barrel.Builder<String>().volume(7.2).materialType("Oil").material("Plastic").build(),
                new Barrel.Builder<String>().volume(15.8).materialType("Milk").material("Wood").build()
        ));

        BarrelService<String> service = new BarrelService<>();
        service.sort(barrels);

        System.out.println("Отсортированные бочки (строки):");
        barrels.forEach(System.out::println);
    }

    private static void testWithIntegers() {
        System.out.println("Тест с типом данных Integer:");
        BarrelService<Integer> barrelService = new BarrelService<>();

        // Используем изменяемый список
        List<Barrel<Integer>> barrels = new ArrayList<>(List.of(
                new Barrel.Builder<Integer>().volume(10.0).materialType(1).material("Дерево").build(),
                new Barrel.Builder<Integer>().volume(25.0).materialType(2).material("Металл").build(),
                new Barrel.Builder<Integer>().volume(20.0).materialType(3).material("Пластик").build()
        ));

        System.out.println("До сортировки:");
        barrels.forEach(System.out::println);

        barrelService.sort(barrels);

        System.out.println("После сортировки:");
        barrels.forEach(System.out::println);

        // Поиск бочки с объемом 25.0
        Barrel<Integer> searchKey = new Barrel.Builder<Integer>().volume(25.0).build();
        int index = barrelService.search(barrels, searchKey);
        System.out.println("Результат поиска: " + (index >= 0 ? barrels.get(index) : "Не найден"));
        System.out.println();
    }
}