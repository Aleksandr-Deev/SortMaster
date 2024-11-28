package util;

import java.util.Comparator;

public class SearchUtil {
    public static <T> int binarySearch(T[] arr, T key, Comparator<? super T> comparator) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = comparator.compare(arr[mid], key);

            if (cmp == 0) {
                return mid; // Элемент найден
            }

            if (cmp < 0) {
                left = mid + 1; // Сдвинуть вправо
            } else {
                right = mid - 1; // Сдвинуть влево
            }
        }
        return -1; // Элемент не найден
    }
}
