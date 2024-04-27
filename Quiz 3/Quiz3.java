import java.util.*;
import java.io.*;

public class Quiz3 {

    static class Station {
        int x, y, id;

        public Station(int x, int y, int id) {
            this.x = x;
            this.y = y;
            this.id = id;
        }
    }

    static class Edge implements Comparable<Edge> {
        int u, v;
        double weight;

        public Edge(int u, int v, double weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Double.compare(this.weight, other.weight);
        }
    }

    static int[] parent;

    static int find(int x) {
        if (x == parent[x]) return x;

        return parent[x] = find(parent[x]);
    }

    static boolean union(int x, int y) {
        int xRoot = find(x), yRoot = find(y);

        if (xRoot != yRoot) {
            parent[xRoot] = yRoot;
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(new File(args[0]));
        int T = input.nextInt();

        while (T-- > 0) {
            int S = input.nextInt(), P = input.nextInt();
            Station[] stations = new Station[P];

            for (int i = 0; i < P; i++) {
                stations[i] = new Station(input.nextInt(), input.nextInt(), i);
            }

            PriorityQueue<Edge> edges = new PriorityQueue<>();

            for (int i = 0; i < P; i++) {
                for (int j = i + 1; j < P; j++) {
                    double dist = Math.hypot(stations[i].x - stations[j].x, stations[i].y - stations[j].y);
                    edges.offer(new Edge(i, j, dist));
                }
            }

            parent = new int[P];
            for (int i = 0; i < P; i++) parent[i] = i;

            double maxWeight = 0;

            while (!edges.isEmpty()) {
                Edge edge = edges.poll();

                if (union(edge.u, edge.v)) {
                    maxWeight = Math.max(maxWeight, edge.weight);
                    if (--P == S) break;
                }
            }

            System.out.printf("%.2f\n", maxWeight);
        }
        input.close();
    }
}