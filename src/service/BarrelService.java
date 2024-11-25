package service;

import entity.Barrel;
import strategy.Strategy;

import java.io.*;
import java.util.*;

public class BarrelService<T> implements Strategy<Barrel<T>> {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public List<Barrel<T>> fillData() {
        List<Barrel<T>> barrels = new ArrayList<>();
        System.out.println("Как заполнить данные? 1 - Вручную, 2 - Рандомно, 3 - Из файла");
        int choice = getIntInput("Ваш выбор: ");
        int length = getIntInput("Введите желаемую длину массива:");

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
            System.out.println("Введите данные для бочки №" + (i + 1));

            double volume;
            while (true) {
                volume = getDoubleInput("Введите объем бочки (положительное число): ");
                if (volume > 0) break;
                System.out.println("Объем должен быть больше 0. Попробуйте снова.");
            }

            String materialType = getStringInput("Введите тип хранимого материала:");
            String material = getStringInput("Введите материал, из которого изготовлена бочка:");

            barrels.add(new Barrel.Builder<T>()
                    .volume(volume)
                    .materialType((T) materialType)
                    .material(material)
                    .build());
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
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null && barrels.size() < length) {
                String[] parts = line.split(",");
                double volume = Double.parseDouble(parts[0]);
                T materialType = (T) parts[1];
                String material = parts[2];

                barrels.add(new Barrel.Builder<T>()
                        .volume(volume)
                        .materialType(materialType)
                        .material(material)
                        .build());
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    @Override
    public void sort(List<Barrel<T>> data) {
        System.out.println("Как сортировать? 1 - По объему, 2 - По материалу");
        int choice = getIntInput("Ваш выбор: ");

        switch (choice) {
            case 1 -> Collections.sort(data); // Сортировка по объему
            case 2 -> data.sort(Comparator.comparing(Barrel::getMaterial)); // Сортировка по материалу
            default -> System.out.println("Некорректный выбор!");
        }
    }

    @Override
    public int search(List<Barrel<T>> data, Barrel<T> key) {
        // Поиск по умолчанию — например, по объему
        return search(data, key, Comparator.comparing(Barrel::getVolume));
    }

    public int search(List<Barrel<T>> data, Barrel<T> key, Comparator<Barrel<T>> comparator) {
        data.sort(comparator);

        int low = 0, high = data.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            Barrel<T> midBarrel = data.get(mid);
            int cmp = comparator.compare(midBarrel, key);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid; // Найден
            }
        }
        return -1; // Не найден
    }

    private int getIntInput(String prompt) {
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

    private double getDoubleInput(String prompt) {
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

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

//    // Дополнительная сортировка четные/нечетные
//    public void sortEvenOdd(List<Barrel<T>> data) {
//        List<Barrel<T>> even = new ArrayList<>();
//        for (Barrel<T> barrel : data) {
//            if ((int) barrel.getVolume() % 2 == 0) {
//                even.add(barrel);
//            }
//        }
//        even.sort(Comparator.comparing(Barrel::getVolume));
//
//        int evenIndex = 0;
//        for (int i = 0; i < data.size(); i++) {
//            if ((int) data.get(i).getVolume() % 2 == 0) {
//                data.set(i, even.get(evenIndex++));
//            }
//        }
//    }
//
//    // Запись результатов в файл
//    public void saveToFile(List<Barrel<T>> data, String filePath) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
//            for (Barrel<T> barrel : data) {
//                writer.write(barrel.toString());
//                writer.newLine();
//            }
//            System.out.println("Данные успешно сохранены в файл.");
//        } catch (IOException e) {
//            System.out.println("Ошибка записи в файл: " + e.getMessage());
//        }
//  }
}
