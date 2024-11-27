package service;

import entity.Barrel;
import strategy.Strategy;
import utils.InputUtils;

import java.io.*;
import java.util.*;

import static utils.InputUtils.getIntInput;

public class BarrelService<T> implements Strategy<Barrel<T>> {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public List<Barrel<T>> fillData() {
        List<Barrel<T>> barrels = new ArrayList<>();
        System.out.println("Как заполнить данные? 1 - Вручную, 2 - Рандомно, 3 - Из файла");
        int choice = getIntInput("Ваш выбор: ");
        int length = getArrayLength();

        switch (choice) {
            case 1 -> fillDataManually(barrels, length);
            case 2 -> fillDataRandomly(barrels, length);
            case 3 -> fillDataFromFile(barrels, length);
            default -> System.out.println("Некорректный выбор!");
        }
        return barrels;
    }

    private int getArrayLength() {
        int length;
        do {
            length = getIntInput("Введите желаемую количество бочек (положительное число): ");
            if (length <= 0) {
                System.out.println("Количество бочек должна быть положительным числом. Попробуйте снова.");
            }
        } while (length <= 0);
        return length;
    }

    private void fillDataManually(List<Barrel<T>> barrels, int length) {
        for (int i = 0; i < length; i++) {
            System.out.println("Введите данные для бочки №" + (i + 1));
            double volume = InputUtils.getDoubleInput("Введите объем бочки (положительное число): ");
            T materialType = (T) InputUtils.getStringInput("Введите тип хранимого материала:");  // Приведение к типу T
            String material = InputUtils.getStringInput("Введите материал, из которого изготовлена бочка:");
            barrels.add(new Barrel.Builder<T>().volume(volume).materialType(materialType).material(material).build());
        }
    }

    private void fillDataRandomly(List<Barrel<T>> barrels, int length) {
        for (int i = 0; i < length; i++) {
            double volume = Math.random() * 100;
            T materialType = (T) ("MaterialType" + (i + 1));  // Приведение типа
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

                if (parts.length < 3) {
                    System.out.println("Ошибка: некорректный формат данных в строке: " + line);
                    continue; // Пропустить эту строку
                }

                try {
                    double volume = Double.parseDouble(parts[0]);
                    // Преобразование materialType в тип T
                    T materialType = convertToMaterialType(parts[1]); // Метод для преобразования
                    String material = parts[2];

                    barrels.add(new Barrel.Builder<T>()
                            .volume(volume)
                            .materialType(materialType)  // Передаем T
                            .material(material)
                            .build());
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка преобразования данных: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    // Пример метода для конвертации строки в тип T
    private T convertToMaterialType(String str) {
        // Пример для строкового типа T
        return (T) str; // Простое приведение
    }


    @Override
    public void sort(List<Barrel<T>> data) {
        System.out.println("Как сортировать? 1 - По объему, 2 - По материалу, 3 - По типу хранимого материала");
        int choice = getIntInput("Ваш выбор: ");

        switch (choice) {
            case 1 -> sort(data, Comparator.comparing(Barrel::getVolume)); // Сортировка по объему
            case 2 -> sort(data, Comparator.comparing(Barrel::getMaterial)); // Сортировка по материалу бочки
            case 3 -> sort(data, Comparator.comparing(barrel -> barrel.getMaterialType().toString()));  // По типу хранимого материала
            default -> System.out.println("Некорректный выбор!");
        }
    }

    public static <T> void sort(List<T> data, Comparator<? super T> comparator) {
        if (data == null || data.isEmpty()) {
            System.out.println("Список пуст или null. Сортировка невозможна.");
            return;
        }

        if (!(data instanceof ArrayList)) {
            data = new ArrayList<>(data); // Изменяемая копия, если список неизменяемый
        }

        try {
            data.sort(comparator); // Используем Comparator для сортировки
            System.out.println("Сортировка завершена.");
        } catch (Exception e) {
            System.out.println("Ошибка при сортировке: " + e.getMessage());
        }
    }

    @Override
    public int search(List<Barrel<T>> data, Barrel<T> key) {
        return search(data, key, Comparator.comparing(Barrel::getVolume)); // Сортировка по объему по умолчанию
    }

    public int search(List<Barrel<T>> data, Barrel<T> key, Comparator<Barrel<T>> comparator) {
        // Если переданный список не является экземпляром ArrayList, он копируется в новый список типа ArrayList
        if (!(data instanceof ArrayList)) {
            // сортировка перед бинарным поиском
            data = new ArrayList<>(data);
        }
        data.sort(comparator);
        int min = 0, max = data.size() - 1;

        while (min <= max) {
            int mid = (min + max) / 2; //индекс среднего элемента
            Barrel<T> midBarrel = data.get(mid); // извлекается сам объект из списка
            int cmp = comparator.compare(midBarrel, key); // сравнение среднего с искомым с помощью компаратора

            if (cmp < 0) {
                min = mid + 1; // если средний меньше искомого, то ищем в правой половине
            } else if (cmp > 0) {
                max = mid - 1; // если средний больше искомого, то ищем в левой половине
            } else {
                return mid; // если средний равен искомому, то возвращается индекс
            }
        }
        return -1;
    }
}

//    // Дополнительная сортировка четные/нечетные
//    public void sortEvenOdd(List<Barrel<T>> data) {
//        data.sort((barrel1, barrel2) -> {
//            // Сортировка по четности (сначала четные, потом нечетные)
//            int evenOddCompare = (int) barrel1.getVolume() % 2 - (int) barrel2.getVolume() % 2;
//
//            // Если оба четные или оба нечетные, то сортируем по объему
//            if (evenOddCompare == 0) {
//                return Double.compare(barrel1.getVolume(), barrel2.getVolume());
//            }
//
//            return evenOddCompare;
//        });
//    }
//
//    // Запись результатов в файл
//    public void saveToFile(List<Barrel<T>> data, String filePath) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
//            for (Barrel<T> barrel : data) {
//                writer.write(barrel.toString());  // Можно улучшить формат записи
//                writer.newLine(); // Добавилась нова строка
//            }
//            System.out.println("Данные успешно сохранены в файл.");
//        } catch (IOException e) {
//            System.out.println("Ошибка записи в файл: " + e.getMessage());
//        }
//    }

