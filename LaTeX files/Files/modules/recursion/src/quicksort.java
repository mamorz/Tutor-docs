public class Quicksort {
    public static void sort(long[] a) {
        qsort(a, 0, a.length - 1);
    }
    /* Sortiert Array a [left..right] */
    private static void qsort(long[] a, int left, int right) {
        int i = left;
        int j = right;
        long p = pivot(a, left, right); // Waehle einbel. Element
        while (i <= j) {
         while (a[i] < p) { i++; } // Suche El. links >= p
         while (a[j] > p) { j--; } // Suche El. rechts <= p
         if (i <= j) {
             long tmp = a[i];
             a[i] = a[j]; a[j] = tmp; // Vertausche
             i++; j--;
         }
        }
        if (left < j) { qsort(a, left, j); } // Sortiere linken Teil
        if (i < right) { qsort(a, i, right); } // Sortiere rechten Teil
    }
}