import models.Animal;
import models.Barrel;
import models.Person;
import strategy.BinarySearchStrategy;
import strategy.TimSortStrategy;
import util.Validator;

import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {

        TimSortStrategy<Animal> animalSorter = new TimSortStrategy<>();
        TimSortStrategy<Barrel> barrelSorter = new TimSortStrategy<>();
        TimSortStrategy<Person> personSorter = new TimSortStrategy<>();
        BinarySearchStrategy binarySearch = new BinarySearchStrategy();

        Animal[] animals = new Animal[0];
        Barrel[] barrels = new Barrel[0];
        Person[] persons = new Person[0];

        while (true) {
            System.out.println("Выберите опцию:");
            System.out.println("1. Ввод животных вручную");
            System.out.println("2. Ввод бочек вручную");
            System.out.println("3. Ввод людей вручную");
            System.out.println("4. Сортировка животных");
            System.out.println("5. Сортировка бочек");
            System.out.println("6. Сортировка людей");
            System.out.println("7. Поиск животного по виду");
            System.out.println("8. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очищаем буфер

            switch (choice) {
                case 1:
                    animals = inputAnimals(); // Изменение для сохранения массива
                    break;

                case 2:
                    barrels = inputBarrels(); // Изменение для сохранения массива
                    break;

                case 3:
                    persons = inputPersons(); // Изменение для сохранения массива
                    break;

                case 4:
                    animalSorter.sort(animals, Comparator.comparing(Animal::getSpecies));
                    System.out.println("Животные отсортированы по видам:");
                    for (Animal animal : animals) {
                        System.out.println(animal.getSpecies());
                    }
                    break;

                case 5:
                    barrelSorter.sort(barrels, Comparator.comparingDouble(Barrel::getVolume));
                    System.out.println("Бочки отсортированы по объему:");
                    for (Barrel barrel : barrels) {
                        System.out.println(barrel.getVolume());
                    }
                    break;

                case 6:
                    personSorter.sort(persons, Comparator.comparing(Person::getSurname));
                    System.out.println("Люди отсортированы по фамилии:");
                    for (Person person : persons) {
                        System.out.println(person.getSurname());
                    }
                    break;

                case 7:
                    System.out.println("Введите вид животного для поиска:");
                    String speciesToSearch = scanner.nextLine();
                    int foundIndex = binarySearch.binarySearch(animals, new Animal.AnimalBuilder().setSpecies(speciesToSearch).build(), Comparator.comparing(Animal::getSpecies));
                    if (foundIndex != -1) {
                        System.out.println("Животное найдено по индексу: " + foundIndex);
                    } else {
                        System.out.println("Животное не найдено.");
                    }
                    break;

                case 8:
                    System.out.println("Выход из программы.");
                    return;

                default:
                    System.out.println("Некорректный выбор.");
            }
        }
    }

    private static Animal[] inputAnimals() {
        System.out.println("Введите количество животных:");
        int animalCount = scanner.nextInt();
        scanner.nextLine(); // Очищаем буфер
        Animal[] animals = new Animal[animalCount];

        for (int i = 0; i < animalCount; i++) {
            System.out.println("Введите вид:");
            String species = scanner.nextLine();
            System.out.println("Введите цвет глаз:");
            String eyeColor = scanner.nextLine();
            System.out.println("Есть ли шерсть (true/false):");
            boolean fur = scanner.nextBoolean();
            scanner.nextLine(); // Очищаем буфер

            if (!Validator.validateEyeColor(eyeColor)) {
                System.out.println("Некорректный цвет глаз!");
                i--; // Повторяем ввод для этого индекса
                continue;
            }

            animals[i] = new Animal.AnimalBuilder()
                    .setSpecies(species)
                    .setEyeColor(eyeColor)
                    .setFur(fur)
                    .build();
        }
        return animals;
    }

    private static Barrel[] inputBarrels() {
        System.out.println("Введите количество бочек:");
        int barrelCount = scanner.nextInt();
        scanner.nextLine(); // Очищаем буфер
        Barrel[] barrels = new Barrel[barrelCount];

        for (int i = 0; i < barrelCount; i++) {
            System.out.println("Введите объем:");
            double volume = scanner.nextDouble();
            scanner.nextLine(); // Очищаем буфер
            System.out.println("Введите хранимый материал:");
            String storedMaterial = scanner.nextLine();
            System.out.println("Введите материал изготовления:");
            String material = scanner.nextLine();

            if (!Validator.validateVolume(volume)) {
                System.out.println("Некорректный объем!");
                i--; // Повторяем ввод для этого индекса
                continue;
            }

            barrels[i] = new Barrel.BarrelBuilder()
                    .setVolume(volume)
                    .setMaterial(material)
                    .setStoredMaterial(storedMaterial)
                    .build();
        }
        return barrels;
    }

    private static Person[] inputPersons() {
        System.out.println("Введите количество людей:");
        int personCount = scanner.nextInt();
        scanner.nextLine(); // Очищаем буфер
        Person[] persons = new Person[personCount];

        for (int i = 0; i < personCount; i++) {
            System.out.println("Введите пол (Male/Female):");
            String gender = scanner.nextLine();
            if (!Validator.validateGender(gender)) {
                System.out.println("Некорректный пол!");
                i--; // Повторяем ввод для этого индекса
                continue;
            }

            System.out.println("Введите возраст:");
            int age = scanner.nextInt();
            scanner.nextLine(); // Очищаем буфер
            if (!Validator.validateAge(age)) {
                System.out.println("Некорректный возраст!");
                i--; // Повторяем ввод для этого индекса
                continue;
            }

            System.out.println("Введите фамилию:");
            String surname = scanner.nextLine();

            persons[i] = new Person.PersonBuilder()
                    .setGender(gender)
                    .setAge(age)
                    .setSurname(surname)
                    .build();
        }
        return persons;
    }
}
