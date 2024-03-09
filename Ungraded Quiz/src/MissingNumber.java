import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class MissingNumber {

    public static void main(String[] args) throws FileNotFoundException {
        int n = Integer.parseInt(args[0]);

        File input = new File(args[1]);
        Scanner scanner = new Scanner(input);

        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        int result = findMissingNumber(nums, n);
        System.out.println(result);

        scanner.close();
    }

    private static int findMissingNumber(int[] nums, int n) {
        int xor = 0;

        for (int i = 1; i <= n; i++) {
            xor ^= i;
        }

        for (int num : nums) {
            xor ^= num;
        }

        return xor;
    }
}
