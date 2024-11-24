package service;

import entity.Barrel;
import strategy.Strategy;

import java.util.*;

public class BarrelService<T> implements Strategy<Barrel<T>> {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public List<Barrel<T>> fillData() {
        List<Barrel<T>> barrels = new ArrayList<>();
        System.out.println("Как заполнить данные? 1 - Вручную, 2 - Рандомно, 3 - Из файла");
        int choice = scanner.nextInt();
        System.out.println("Введите желаемую длину массива:");
        int length = scanner.nextInt();

        switch (choice) {
            case 1 -> fillDataManually(barrels, length);
            case 2 -> fillDataRandomly(barrels, length);
            case 3 -> fillDataFromFile(barrels, length);
            default -> System.out.println("Некорректный выбор!");
        }
        return barrels;
    }

    private void fillDataManually(List<Barrel<T>> barrels, int length) {
        for (int i = 0; i < length; i++) {
            System.out.println("Введите объем бочки:");
            double volume = scanner.nextDouble();
            System.out.println("Введите тип хранимого материала (строка):");
            String materialType = scanner.next();
            System.out.println("Введите материал, из которого изготовлена бочка:");
            String material = scanner.next();

            // Указываем дженерик T в Builder
            Barrel<T> barrel = new Barrel.Builder<T>()
                    .volume(volume)
                    .materialType((T) materialType) // String к T
                    .material(material)
                    .build();
            barrels.add(barrel);
        }
    }

    private void fillDataRandomly(List<Barrel<T>> barrels, int length) {
        for (int i = 0; i < length; i++) {
            double volume = Math.random() * 100;
            T materialType = (T) ("MaterialType" + (i + 1));
            String material = "Material" + (i + 1);

            Barrel<T> barrel = new Barrel.Builder<T>()
                    .volume(volume)
                    .materialType(materialType)
                    .material(material)
                    .build();
            barrels.add(barrel);
        }
    }

    private void fillDataFromFile(List<Barrel<T>> barrels, int length) {
        System.out.println("Введите путь к файлу:");
        String filePath = scanner.next();

    }

    @Override
    public void sort(List<Barrel<T>> data) {
        System.out.println("Как сортировать? 1 - По объему, 2 - По материалу");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> Collections.sort(data);  // Сортировка по объему
            case 2 -> data.sort(Comparator.comparing(Barrel::getMaterial));  // Сортировка по материалу
            default -> System.out.println("Некорректный выбор!");
        }
    }

    @Override
    public int search(List<Barrel<T>> data, Barrel<T> key) {
        Collections.sort(data);
        int low = 0, high = data.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            Barrel<T> midBarrel = data.get(mid);
            int cmp = Double.compare(midBarrel.getVolume(), key.getVolume());

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
