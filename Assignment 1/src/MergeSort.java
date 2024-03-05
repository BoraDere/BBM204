import java.util.Arrays;
import java.util.ArrayList;

public class MergeSort {

    public static int[] mergeSort(int[] array) {
        int n = array.length;

        if (n <= 1) {
            return array;
        }

        int[] left = Arrays.copyOfRange(array, 0, n/2);
        int[] right = Arrays.copyOfRange(array, n/2, n);

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    static int[] merge(int[] A, int[] B) {
        ArrayList<Integer> listA = new ArrayList<>();

        for (int num : A) {
            listA.add(num);
        }

        ArrayList<Integer> listB = new ArrayList<>();

        for (int num : B) {
            listB.add(num);
        }

        ArrayList<Integer> listC = new ArrayList<>();

        while (!listA.isEmpty() && !listB.isEmpty()) {
            if (listA.get(0) > listB.get(0)) {
                listC.add(listB.get(0));
                listB.remove(0);
            }
            else {
                listC.add(listA.get(0));
                listA.remove(0);
            }
        }

        while (!listA.isEmpty()) {
            listC.add(listA.get(0));
            listA.remove(0);
        }

        while (!listB.isEmpty()) {
            listC.add(listB.get(0));
            listB.remove(0);
        }

        int[] C = new int[listC.size()];

        for (int i = 0; i < listC.size(); i++) {
            C[i] = listC.get(i);
        }

        return C;
    }
}