import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SmallestCharacter {
    public static void main(String[] args) throws FileNotFoundException {
        File input = new File(args[0]);
        Scanner scanner = new Scanner(input);

        String[] queries = scanner.nextLine().split(" ");
        String[] words = scanner.nextLine().split(" ");

        int[] answers = counter(queries, words);

        System.out.print("[");
        for (int i = 0; i < answers.length; i++) {
            System.out.print(answers[i]);

            if (i < answers.length - 1) {
                System.out.print(",");
            }
        }

        System.out.println("]");

        scanner.close();
    }

    static int[] counter(String[] queries, String[] words) {
        int[] answers = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int count = 0;
            int smallestCharFreq = findSmallestCharFreq(queries[i]);

            for (String word : words) {
                if (findSmallestCharFreq(word) > smallestCharFreq) {
                    count++;
                }
            }

            answers[i] = count;
        }

        return answers;
    }

    private static int findSmallestCharFreq(String s) {
        char smallestChar = s.charAt(0);
        int freq = 1;

        for (int i = 1; i < s.toCharArray().length; i++) {
            if (s.toCharArray()[i] < smallestChar) {
                smallestChar = s.toCharArray()[i];
                freq = 1;
            }
            else if (s.toCharArray()[i] == smallestChar) {
                freq++;
            }
        }

        return freq;
    }
}
