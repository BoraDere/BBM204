public class Main {
    public static void main(String[] args) {
        int[] A = {21, 56, 5, 6, 8, 7, 4, 9, 10};

        A = CountingSort.countingSort(A, 56);

        System.out.print("anan: ");

        for (Integer i : A) {
            System.out.print(i + " ");
        }

        System.out.println();
    }
}