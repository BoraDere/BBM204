import java.util.ArrayList;

/**
 * This class accomplishes Mission POWER GRID OPTIMIZATION
 */
public class PowerGridOptimization {
    private ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour;

    public PowerGridOptimization(ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour){
        this.amountOfEnergyDemandsArrivingPerHour = amountOfEnergyDemandsArrivingPerHour;
    }

    public ArrayList<Integer> getAmountOfEnergyDemandsArrivingPerHour() {
        return amountOfEnergyDemandsArrivingPerHour;
    }
    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalPowerGridSolution
     */
    public OptimalPowerGridSolution GetOptimalPowerGridSolutionDP() {
        int n = amountOfEnergyDemandsArrivingPerHour.size();
        int[] sol = new int[n + 1];
        ArrayList<ArrayList<Integer>> hours = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) {
            hours.add(new ArrayList<>());
        }

        for (int j = 1; j <= n; j++) {
            int maxVal = Integer.MIN_VALUE;
            ArrayList<Integer> maxHours = new ArrayList<>();
            for (int i = 1; i <= j; i++) {
                int val = sol[j - i] + Math.min(i*i, amountOfEnergyDemandsArrivingPerHour.get(j - 1));
                if (val > maxVal) {
                    maxVal = val;
                    maxHours = new ArrayList<>(hours.get(j - i));
                    maxHours.add(j);
                }
            }
            sol[j] = maxVal;
            hours.set(j, maxHours);
        }

        return new OptimalPowerGridSolution(sol[n], hours.get(n));
    }
}
