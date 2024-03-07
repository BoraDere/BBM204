public class MergeSort {

    public static int[] mergeSort(int[] array) {
        int n = array.length;

        if (n <= 1) {
            return array;
        }

        int mid = n / 2;

        int[] left = new int[mid];
        int[] right = new int[n - mid];

        System.arraycopy(array, 0, left, 0, mid);
        System.arraycopy(array, mid, right, 0, n - mid);

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    static int[] merge(int[] A, int[] B) {
        int lenA = A.length;
        int lenB = B.length;

        int[] C = new int[lenA + lenB];
        int i = 0, j = 0, k = 0;

        while (i < lenA && j < lenB) {
            if (A[i] > B[j]) {
                C[k++] = B[j++];
            } else {
                C[k++] = A[i++];
            }
        }

        while (i < lenA) {
            C[k++] = A[i++];
        }

        while (j < lenB) {
            C[k++] = B[j++];
        }

        return C;
    }
}