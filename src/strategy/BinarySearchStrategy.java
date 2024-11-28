package strategy;

import java.util.Comparator;

public class BinarySearchStrategy {
    public static <T> int binarySearch(T[] array, T key, Comparator<? super T> cmp) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int comparison = cmp.compare(array[mid], key);
            if (comparison == 0) {
                return mid; // элемент найден
            }
            if (comparison < 0) {
                left = mid + 1; // ищем в правой части
            } else {
                right = mid - 1; // ищем в левой части
            }
        }
        return -1; // элемент не найден
    }
}