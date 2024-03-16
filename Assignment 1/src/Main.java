import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.time.Instant;
import java.time.Duration;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.io.IOException;

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

    static double[] insertionSorter(int[][] arrays) {
        double[] results = new double[arrays.length];

        for (int i = 0; i < arrays.length; i++) {
            int[] array = arrays[i];
            double result = 0;

            for (int j = 0; j < 10; j++) {
                Instant start = Instant.now();

                InsertionSort.insertionSort(array);

                Instant end = Instant.now();

                long elapsed = Duration.between(start, end).toMillis();
                result += (double) elapsed / 10;
            }

            results[i] = result;
        }

        return results;
    }

    static double[] mergeSorter(int[][] arrays) {
        double[] results = new double[arrays.length];

        for (int i = 0; i < arrays.length; i++) {
            int[] array = arrays[i];
            double result = 0;

            for (int j = 0; j < 10; j++) {
                Instant start = Instant.now();

                MergeSort.mergeSort(array);

                Instant end = Instant.now();

                long elapsed = Duration.between(start, end).toMillis();
                result += (double) elapsed / 10;
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

    static double[] countingSorter(int[][] arrays) {
        double[] results = new double[arrays.length];

        for (int i = 0; i < arrays.length; i++) {
            int[] array = arrays[i];
            double result = 0;

            for (int j = 0; j < 10; j++) {
                Instant start = Instant.now();

                CountingSort.countingSort(array, findMax(array));

                Instant end = Instant.now();

                long elapsed = Duration.between(start, end).toMillis();
                result += (double) elapsed / 10;
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

    static double[] linearSearcher(int[][] arrays) {
        double[] results = new double[arrays.length];

        for (int i = 0; i < arrays.length; i++) {
            int[] array = arrays[i];
            double result = 0;

            for (int j = 0; j < 1000; j++) {
                int rnd  = new Random().nextInt(array.length);
                int element = array[rnd];

                Instant start = Instant.now();

                LinearSearch.linearSearch(array, element);

                Instant end = Instant.now();

                long elapsed = Duration.between(start, end).toNanos();
                result += (double) elapsed / 1000;
            }

            results[i] = result;
        }

        return results;
    }

    static double[] binarySearcher(int[][] arrays) {
        double[] results = new double[arrays.length];

        for (int i = 0; i < arrays.length; i++) {
            int[] array = arrays[i];
            double result = 0;

            for (int j = 0; j < 1000; j++) {
                int rnd  = new Random().nextInt(array.length);
                int element = array[rnd];

                Instant start = Instant.now();

                BinarySearch.binarySearch(array, element);

                Instant end = Instant.now();

                long elapsed = Duration.between(start, end).toNanos();
                result += (double) elapsed / 1000;
            }

            results[i] = result;
        }

        return results;
    }

    public static void showAndSaveChart(String title, int[] xAxis, double[][] yAxis, String[] names, String time) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in " + time).xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        for (int i = 0; i < names.length; i++) {
            chart.addSeries(names[i], doubleX, yAxis[i]);
        }

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }

    public static void main(String[] args) throws IOException{
        String filePath = args[0];

        int[][] arrays = arrayReturner(filePath); // 500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000
        int[] xAxis = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};

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

        double[] insertionRandomResults = insertionSorter(arrays);
        double[] insertionSortedResults = insertionSorter(sortedArrays);
        double[] insertionReverseResults = insertionSorter(reverseSortedArrays);

        double[] mergeRandomResults = mergeSorter(arrays);
        double[] mergeSortedResults = mergeSorter(sortedArrays);
        double[] mergeReverseResults = mergeSorter(reverseSortedArrays);

        double[] countRandomResults = countingSorter(arrays);
        double[] countSortedResults = countingSorter(sortedArrays);
        double[] countReverseResults = countingSorter(reverseSortedArrays);

        double[] linearRandomResults = linearSearcher(arrays);
        double[] linearSortedResults = linearSearcher(sortedArrays);
        // long[] linearReverseResults = linearSearcher(reverseSortedArrays);

        // long[] binaryRandomResults = binarySearcher(arrays);
        double[] binarySortedResults = binarySearcher(sortedArrays);
        // long[] binaryReverseResults = binarySearcher(reverseSortedArrays);

        System.out.println("Random Input Data Timing Results in ms:");

        for (long i = 0; i < 39; i++) {
            System.out.print("-");
        }

        System.out.println();

        System.out.println("Insertion Sort: " + Arrays.toString(insertionRandomResults));
        System.out.println("Merge Sort: " + Arrays.toString(mergeRandomResults));
        System.out.println("Counting Sort: " + Arrays.toString(countRandomResults));

        System.out.println();

        System.out.println("Sorted Input Data Timing Results in ms:");

        for (int i = 0; i < 39; i++) {
            System.out.print("-");
        }

        System.out.println();

        System.out.println("Insertion Sort: " + Arrays.toString(insertionSortedResults));
        System.out.println("Merge Sort: " + Arrays.toString(mergeSortedResults));
        System.out.println("Counting Sort: " + Arrays.toString(countSortedResults));

        System.out.println();

        System.out.println("Reversely Sorted Input Data Timing Results in ms:");

        for (int i = 0; i < 49; i++) {
            System.out.print("-");
        }

        System.out.println();

        System.out.println("Insertion Sort: " + Arrays.toString(insertionReverseResults));
        System.out.println("Merge Sort: " + Arrays.toString(mergeReverseResults));
        System.out.println("Counting Sort: " + Arrays.toString(countReverseResults));

        System.out.println();

        System.out.println("Experiments with Searching Algorithms:");

        for (int i = 0; i < 38; i++) {
            System.out.print("-");
        }

        System.out.println();

        System.out.println("Linear Search (random data): " + Arrays.toString(linearRandomResults));
        System.out.println("Linear Search (sorted data): " + Arrays.toString(linearSortedResults));
        System.out.println("Binary Search (sorted data)" + Arrays.toString(binarySortedResults));

        double[][] randoms = {insertionRandomResults, mergeRandomResults, countRandomResults};
        String[] sortNames = {"Insertion Sort", "Merge Sort", "Counting Sort"};

        showAndSaveChart("Test on Random Data", xAxis, randoms, sortNames, "Milliseconds");

        double[][] sorteds = {insertionSortedResults, mergeSortedResults, countSortedResults};

        showAndSaveChart("Test on Sorted Data", xAxis, sorteds, sortNames, "Milliseconds");

        double[][] reverses = {insertionReverseResults, mergeReverseResults, countReverseResults};

        showAndSaveChart("Test on Reversely Sorted Data", xAxis, reverses, sortNames, "Milliseconds");

        double[][] searches = {linearRandomResults, linearSortedResults, binarySortedResults};
        String[] searchNames = {"Linear Search (random)", "Linear Search (sorted)", "Binary Search (sorted)"};

        showAndSaveChart("Test on Search Algorithms", xAxis, searches, searchNames, "Nanoseconds");
    }
}