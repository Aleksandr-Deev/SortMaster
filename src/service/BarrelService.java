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
[1mdiff --git a/src/service/BarrelService.java b/src/service/BarrelService.java[m
[1mindex f1e8a62..bf2e3b6 100644[m
[1m--- a/src/service/BarrelService.java[m
[1m+++ b/src/service/BarrelService.java[m
[36m@@ -3,6 +3,7 @@[m [mpackage service;[m
 import entity.Barrel;[m
 import strategy.Strategy;[m
 [m
[32m+[m[32mimport java.io.*;[m
 import java.util.*;[m
 [m
 public class BarrelService<T> implements Strategy<Barrel<T>> {[m
[36m@@ -12,9 +13,8 @@[m [mpublic class BarrelService<T> implements Strategy<Barrel<T>> {[m
     public List<Barrel<T>> fillData() {[m
         List<Barrel<T>> barrels = new ArrayList<>();[m
         System.out.println("Как заполнить данные? 1 - Вручную, 2 - Рандомно, 3 - Из файла");[m
[31m-        int choice = scanner.nextInt();[m
[31m-        System.out.println("Введите желаемую длину массива:");[m
[31m-        int length = scanner.nextInt();[m
[32m+[m[32m        int choice = getIntInput("Ваш выбор: ");[m
[32m+[m[32m        int length = getIntInput("Введите желаемую длину массива:");[m
 [m
         switch (choice) {[m
             case 1 -> fillDataManually(barrels, length);[m
[36m@@ -27,20 +27,23 @@[m [mpublic class BarrelService<T> implements Strategy<Barrel<T>> {[m
 [m
     private void fillDataManually(List<Barrel<T>> barrels, int length) {[m
         for (int i = 0; i < length; i++) {[m
[31m-            System.out.println("Введите объем бочки:");[m
[31m-            double volume = scanner.nextDouble();[m
[31m-            System.out.println("Введите тип хранимого материала (строка):");[m
[31m-            String materialType = scanner.next();[m
[31m-            System.out.println("Введите материал, из которого изготовлена бочка:");[m
[31m-            String material = scanner.next();[m
[31m-[m
[31m-            // Указываем дженерик T в Builder[m
[31m-            Barrel<T> barrel = new Barrel.Builder<T>()[m
[32m+[m[32m            System.out.println("Введите данные для бочки №" + (i + 1));[m
[32m+[m
[32m+[m[32m            double volume;[m
[32m+[m[32m            while (true) {[m
[32m+[m[32m                volume = getDoubleInput("Введите объем бочки (положительное число): ");[m
[32m+[m[32m                if (volume > 0) break;[m
[32m+[m[32m                System.out.println("Объем должен быть больше 0. Попробуйте снова.");[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[32m            String materialType = getStringInput("Введите тип хранимого материала:");[m
[32m+[m[32m            String material = getStringInput("Введите материал, из которого изготовлена бочка:");[m
[32m+[m
[32m+[m[32m            barrels.add(new Barrel.Builder<T>()[m
                     .volume(volume)[m
[31m-                    .materialType((T) materialType) // String к T[m
[32m+[m[32m                    .materialType((T) materialType)[m
                     .material(material)[m
[31m-                    .build();[m
[31m-            barrels.add(barrel);[m
[32m+[m[32m                    .build());[m
         }[m
     }[m
 [m
[36m@@ -62,39 +65,121 @@[m [mpublic class BarrelService<T> implements Strategy<Barrel<T>> {[m
     private void fillDataFromFile(List<Barrel<T>> barrels, int length) {[m
         System.out.println("Введите путь к файлу:");[m
         String filePath = scanner.next();[m
[31m-[m
[32m+[m[32m        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {[m
[32m+[m[32m            String line;[m
[32m+[m[32m            while ((line = reader.readLine()) != null && barrels.size() < length) {[m
[32m+[m[32m                String[] parts = line.split(",");[m
[32m+[m[32m                double volume = Double.parseDouble(parts[0]);[m
[32m+[m[32m                T materialType = (T) parts[1];[m
[32m+[m[32m                String material = parts[2];[m
[32m+[m
[32m+[m[32m                barrels.add(new Barrel.Builder<T>()[m
[32m+[m[32m                        .volume(volume)[m
[32m+[m[32m                        .materialType(materialType)[m
[32m+[m[32m                        .material(material)[m
[32m+[m[32m                        .build());[m
[32m+[m[32m            }[m
[32m+[m[32m        } catch (IOException | NumberFormatException e) {[m
[32m+[m[32m            System.out.println("Ошибка чтения файла: " + e.getMessage());[m
[32m+[m[32m        }[m
     }[m
 [m
     @Override[m
     public void sort(List<Barrel<T>> data) {[m
         System.out.println("Как сортировать? 1 - По объему, 2 - По материалу");[m
[31m-        int choice = scanner.nextInt();[m
[32m+[m[32m        int choice = getIntInput("Ваш выбор: ");[m
 [m
         switch (choice) {[m
[31m-            case 1 -> Collections.sort(data);  // Сортировка по объему[m
[31m-            case 2 -> data.sort(Comparator.comparing(Barrel::getMaterial));  // Сортировка по материалу[m
[32m+[m[32m            case 1 -> Collections.sort(data); // Сортировка по объему[m
[32m+[m[32m            case 2 -> data.sort(Comparator.comparing(Barrel::getMaterial)); // Сортировка по материалу[m
             default -> System.out.println("Некорректный выбор!");[m
         }[m
     }[m
 [m
     @Override[m
     public int search(List<Barrel<T>> data, Barrel<T> key) {[m
[31m-        Collections.sort(data);[m
[32m+[m[32m        // Поиск по умолчанию — например, по объему[m
[32m+[m[32m        return search(data, key, Comparator.comparing(Barrel::getVolume));[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public int search(List<Barrel<T>> data, Barrel<T> key, Comparator<Barrel<T>> comparator) {[m
[32m+[m[32m        data.sort(comparator);[m
[32m+[m
         int low = 0, high = data.size() - 1;[m
 [m
         while (low <= high) {[m
             int mid = (low + high) / 2;[m
             Barrel<T> midBarrel = data.get(mid);[m
[31m-            int cmp = Double.compare(midBarrel.getVolume(), key.getVolume());[m
[32m+[m[32m            int cmp = comparator.compare(midBarrel, key);[m
 [m
             if (cmp < 0) {[m
                 low = mid + 1;[m
             } else if (cmp > 0) {[m
                 high = mid - 1;[m
             } else {[m
[31m-                return mid;[m
[32m+[m[32m                return mid; // Найден[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
[32m+[m[32m        return -1; // Не найден[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    private int getIntInput(String prompt) {[m
[32m+[m[32m        while (true) {[m
[32m+[m[32m            System.out.print(prompt);[m
[32m+[m[32m            try {[m
[32m+[m[32m                return scanner.nextInt();[m
[32m+[m[32m            } catch (InputMismatchException e) {[m
[32m+[m[32m                System.out.println("Ошибка: введите целое число!");[m
[32m+[m[32m                scanner.next(); // Очистка неверного ввода[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    private double getDoubleInput(String prompt) {[m
[32m+[m[32m        while (true) {[m
[32m+[m[32m            System.out.print(prompt);[m
[32m+[m[32m            try {[m
[32m+[m[32m                return scanner.nextDouble();[m
[32m+[m[32m            } catch (InputMismatchException e) {[m
[32m+[m[32m                System.out.println("Ошибка: введите числовое значение!");[m
[32m+[m[32m                scanner.next(); // Очистка неверного ввода[m
             }[m
         }[m
[31m-        return -1;[m
     }[m
[32m+[m
[32m+[m[32m    private String getStringInput(String prompt) {[m
[32m+[m[32m        System.out.print(prompt);[m
[32m+[m[32m        return scanner.next();[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m//    // Дополнительная сортировка четные/нечетные[m
[32m+[m[32m//    public void sortEvenOdd(List<Barrel<T>> data) {[m
[32m+[m[32m//        List<Barrel<T>> even = new ArrayList<>();[m
[32m+[m[32m//        for (Barrel<T> barrel : data) {[m
[32m+[m[32m//            if ((int) barrel.getVolume() % 2 == 0) {[m
[32m+[m[32m//                even.add(barrel);[m
[32m+[m[32m//            }[m
[32m+[m[32m//        }[m
[32m+[m[32m//        even.sort(Comparator.comparing(Barrel::getVolume));[m
[32m+[m[32m//[m
[32m+[m[32m//        int evenIndex = 0;[m
[32m+[m[32m//        for (int i = 0; i < data.size(); i++) {[m
[32m+[m[32m//            if ((int) data.get(i).getVolume() % 2 == 0) {[m
[32m+[m[32m//                data.set(i, even.get(evenIndex++));[m
[32m+[m[32m//            }[m
[32m+[m[32m//        }[m
[32m+[m[32m//    }[m
[32m+[m[32m//[m
[32m+[m[32m//    // Запись результатов в файл[m
[32m+[m[32m//    public void saveToFile(List<Barrel<T>> data, String filePath) {[m
[32m+[m[32m//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {[m
[32m+[m[32m//            for (Barrel<T> barrel : data) {[m
[32m+[m[32m//                writer.write(barrel.toString());[m
[32m+[m[32m//                writer.newLine();[m
[32m+[m[32m//            }[m
[32m+[m[32m//            System.out.println("Данные успешно сохранены в файл.");[m
[32m+[m[32m//        } catch (IOException e) {[m
[32m+[m[32m//            System.out.println("Ошибка записи в файл: " + e.getMessage());[m
[32m+[m[32m//        }[m
[32m+[m[32m//  }[m
 }[m
