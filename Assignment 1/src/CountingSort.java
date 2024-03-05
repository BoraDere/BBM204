public class CountingSort {
    public static int[] countingSort(int[] A, int k) {
        int[] count = new int[k + 1];
        int size = A.length;
        int[] output = new int[size];

        for (int j : A) {
            count[j]++;
        }

        for (int i = 1; i <= k; i++) {
            count[i] += count[i - 1];
        }

        for (int i = size - 1; i >= 0; i--) {
            output[count[A[i]] - 1] = A[i];
            count[A[i]]--;
        }

        return output;
    }
}
