package strategy;

import java.util.List;

public interface Strategy<T> {
    List<T> fillData();  // Наполнение данных
    void sort(List<T> data);  // Сортировка данных
    int search(List<T> data, T key);  // Поиск данных
}