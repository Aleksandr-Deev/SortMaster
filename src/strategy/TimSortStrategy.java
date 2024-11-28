package strategy;

import java.util.Arrays;
import java.util.Comparator;

public class TimSortStrategy<T> {
    private static final int RUN = 32;

    private void insertionSort(T[] array, int left, int right, Comparator<? super T> cmp) {
        for (int i = left + 1; i <= right; i++) {
            T key = array[i];
            int j = i - 1;

            while (j >= left && cmp.compare(array[j], key) > 0) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    private void merge(T[] array, int left, int mid, int right, Comparator<? super T> cmp) {
        int len1 = mid - left + 1;
        int len2 = right - mid;

        T[] leftArray = Arrays.copyOfRange(array, left, mid + 1);
        T[] rightArray = Arrays.copyOfRange(array, mid + 1, right + 1);

        int i = 0, j = 0, k = left;

        while (i < len1 && j < len2) {
            if (cmp.compare(leftArray[i], rightArray[j]) <= 0) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }
        while (i < len1) {
            array[k++] = leftArray[i++];
        }
        while (j < len2) {
            array[k++] = rightArray[j++];
        }
    }

    public void sort(T[] array, Comparator<? super T> cmp) {
        for (int start = 0; start < array.length; start += RUN) {
            int end = Math.min(start + RUN - 1, array.length - 1);
            insertionSort(array, start, end, cmp);
        }

        for (int size = RUN; size < array.length; size *= 2) {
            for (int left = 0; left < array.length; left += 2 * size) {
                int mid = Math.min(left + size - 1, (array.length - 1));
                int right = Math.min((left + 2 * size - 1), (array.length - 1));

                if (mid < right) {
                    merge(array, left, mid, right, cmp);
                }
            }
        }
    }
}
