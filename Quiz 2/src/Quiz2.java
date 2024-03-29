import java.util.*;
import java.io.*;

public class Quiz2 {
    public static void main(String[] args) throws IOException {
        
        File file = new File(args[0]);
        Scanner scanner = new Scanner(file);

        String[] firstLine = scanner.nextLine().split(" ");
        int M = Integer.parseInt(firstLine[0]);
        int n = Integer.parseInt(firstLine[1]);

        String[] secondLine = scanner.nextLine().split(" ");
        int[] resources = new int[n];

        for (int i = 0; i < n; i++) {
            resources[i] = Integer.parseInt(secondLine[i]);
        }

        scanner.close();

        boolean[][] L = new boolean[M+1][n+1];
        L[0][0] = true;

        for (int i = 1; i <= n; i++) {
            for (int m = 0; m <= M; m++) {
                if (m < resources[i-1]) {
                    L[m][i] = L[m][i-1];
                }
                else {
                    L[m][i] = L[m][i-1] || L[m-resources[i-1]][i-1];
                }
            }
        }

        int maxWeight = 0;
        
        for (int m = M; m >= 0; m--) {
            if (L[m][n]) {
                maxWeight = m;
                break;
            }
        }

        System.out.println(maxWeight);

        for (int m = 0; m <= M; m++) {
            for (int i = 0; i <= n; i++) {
                System.out.print(L[m][i] ? 1 : 0);
            }
            System.out.println();
        }
    }
}
