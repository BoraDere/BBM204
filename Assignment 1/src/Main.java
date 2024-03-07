import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.time.Instant;
import java.time.Duration;

public class Main {
    static int countLines(String filePath) throws FileNotFoundException {
        int count = -1; // header line
        Scanner sc = new Scanner(new File(filePath));

        while (sc.hasNextLine()) {
            sc.nextLine();
            count++;
        }

        return count;
    }

    static int[] reader(String filePath) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filePath));
        sc.useDelimiter(",");

        int[] array = new int[countLines(filePath)];

        int index = 0;

        if (sc.hasNextLine()) { // skip header line
            sc.nextLine();
        }

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] columns = line.split(",");

            int value = Integer.parseInt(columns[6].trim());
            array[index++] = value;
        }

        sc.close();

        return array;
    }

    static int[][] arrayReturner(String filePath) throws FileNotFoundException {
        int[] arr = reader(filePath);
        int[] sizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};

        int[][] arrays = new int[sizes.length][];

        for (int i = 0; i < sizes.length; i++) {
            int size = sizes[i];
            int[] newArr = new int[size];

            System.arraycopy(arr, 0, newArr, 0, size);
            arrays[i] = newArr;
        }

        return arrays;
    }

    static int[] insertionSorter(int[][] arrays) {
        int[] results = new int[arrays.length];

        for (int i = 0; i < arrays.length; i++) {
            int[] array = arrays[i];
            int result = 0;

            for (int j = 0; j < 10; j++) {
                Instant start = Instant.now();

                InsertionSort.insertionSort(array);

                Instant end = Instant.now();

                long elapsed = Duration.between(start, end).toMillis();
                result += elapsed / 10;
            }

            results[i] = result;
        }

        return results;
    }

    static int[] mergeSorter(int[][] arrays) {
        int[] results = new int[arrays.length];

        for (int i = 0; i < arrays.length; i++) {
            int[] array = arrays[i];
            int result = 0;

            for (int j = 0; j < 10; j++) {
                Instant start = Instant.now();

                MergeSort.mergeSort(array);

                Instant end = Instant.now();

                long elapsed = Duration.between(start, end).toMillis();
                result += elapsed / 10;
            }

            results[i] = result;
        }

        return results;
    }

    static int findMax(int[] array) {
        int max = array[0];

        for (Integer integer : array) {
            if (integer > max) {
                max = integer;
            }
        }

        return max;
    }

    static int[] countingSorter(int[][] arrays) {
        int[] results = new int[arrays.length];

        for (int i = 0; i < arrays.length; i++) {
            int[] array = arrays[i];
            int result = 0;

            for (int j = 0; j < 10; j++) {
                Instant start = Instant.now();

                CountingSort.countingSort(array, findMax(array));

                Instant end = Instant.now();

                long elapsed = Duration.between(start, end).toMillis();
                result += elapsed / 10;
            }

            results[i] = result;
        }

        return results;
    }

    static int[] reverse(int[] array) {
        int[] temp = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            temp[i] = array[array.length - i - 1];
        }

        return temp;
    }

    static int[] linearSearcher(int[][] arrays) {
        int[] results = new int[arrays.length];

        for (int i = 0; i < arrays.length; i++) {
            int[] array = arrays[i];
            int result = 0;

            for (int j = 0; j < 1000; j++) {
                int rnd  = new Random().nextInt(array.length);
                int element = array[rnd];

                Instant start = Instant.now();

                LinearSearch.linearSearch(array, element);

                Instant end = Instant.now();

                long elapsed = Duration.between(start, end).toNanos();
                result += elapsed / 1000;
            }

            results[i] = result;
        }

        return results;
    }

    static int[] binarySearcher(int[][] arrays) {
        int[] results = new int[arrays.length];

        for (int i = 0; i < arrays.length; i++) {
            int[] array = arrays[i];
            int result = 0;

            for (int j = 0; j < 1000; j++) {
                int rnd  = new Random().nextInt(array.length);
                int element = array[rnd];

                Instant start = Instant.now();

                BinarySearch.binarySearch(array, element);

                Instant end = Instant.now();

                long elapsed = Duration.between(start, end).toNanos();
                result += elapsed / 1000;
            }

            results[i] = result;
        }

        return results;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String filePath = args[0];

        int[][] arrays = arrayReturner(filePath); // 500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000

        int[][] sortedArrays = {
                MergeSort.mergeSort(arrays[0]),
                MergeSort.mergeSort(arrays[1]),
                MergeSort.mergeSort(arrays[2]),
                MergeSort.mergeSort(arrays[3]),
                MergeSort.mergeSort(arrays[4]),
                MergeSort.mergeSort(arrays[5]),
                MergeSort.mergeSort(arrays[6]),
                MergeSort.mergeSort(arrays[7]),
                MergeSort.mergeSort(arrays[8]),
                MergeSort.mergeSort(arrays[9])
        };

        int[][] reverseSortedArrays = {
                reverse(sortedArrays[0]),
                reverse(sortedArrays[1]),
                reverse(sortedArrays[2]),
                reverse(sortedArrays[3]),
                reverse(sortedArrays[4]),
                reverse(sortedArrays[5]),
                reverse(sortedArrays[6]),
                reverse(sortedArrays[7]),
                reverse(sortedArrays[8]),
                reverse(sortedArrays[9])
        };

        int[] insertionRandomResults = insertionSorter(arrays);
        int[] insertionSortedResults = insertionSorter(sortedArrays);
        int[] insertionReverseResults = insertionSorter(reverseSortedArrays);

        int[] mergeRandomResults = mergeSorter(arrays);
        int[] mergeSortedResults = mergeSorter(sortedArrays);
        int[] mergeReverseResults = mergeSorter(reverseSortedArrays);

        int[] countRandomResults = countingSorter(arrays);
        int[] countSortedResults = countingSorter(sortedArrays);
        int[] countingReverseResults = countingSorter(reverseSortedArrays);

        int[] linearRandomResults = linearSearcher(arrays);
        int[] linearSortedResults = linearSearcher(sortedArrays);
        int[] linearReverseResults = linearSearcher(reverseSortedArrays);

        int[] binaryRandomResults = binarySearcher(arrays);
        int[] binarySortedResults = binarySearcher(sortedArrays);
        int[] binaryReverseResults = binarySearcher(reverseSortedArrays);

        System.out.println("insertionRandomResults: " + Arrays.toString(insertionRandomResults));
        System.out.println("insertionSortedResults: " + Arrays.toString(insertionSortedResults));
        System.out.println("insertionReverseResults: " + Arrays.toString(insertionReverseResults));

        System.out.println();

        System.out.println("mergeRandomResults: " + Arrays.toString(mergeRandomResults));
        System.out.println("mergeSortedResults: " + Arrays.toString(mergeSortedResults));
        System.out.println("mergeReverseResults: " + Arrays.toString(mergeReverseResults));

        System.out.println();

        System.out.println("countRandomResults: " + Arrays.toString(countRandomResults));
        System.out.println("countSortedResults: " + Arrays.toString(countSortedResults));
        System.out.println("countingReverseResults: " + Arrays.toString(countingReverseResults));

        System.out.println();

        System.out.println("linearRandomResults: " + Arrays.toString(linearRandomResults));
        System.out.println("linearSortedResults: " + Arrays.toString(linearSortedResults));
        System.out.println("linearReverseResults: " + Arrays.toString(linearReverseResults));

        System.out.println();

        System.out.println("binaryRandomResults: " + Arrays.toString(binaryRandomResults));
        System.out.println("binarySortedResults: " + Arrays.toString(binarySortedResults));
        System.out.println("binaryReverseResults: " + Arrays.toString(binaryReverseResults));
    }
}