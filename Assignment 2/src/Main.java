import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Main class
 */
// FREE CODE HERE
public class Main {


    public static void main(String[] args) throws IOException {

       /** MISSION POWER GRID OPTIMIZATION BELOW **/

        System.out.println("##MISSION POWER GRID OPTIMIZATION##");
        // TODO: Your code goes here
        // You are expected to read the file given as the first command-line argument to read
        File demandsFile = new File(args[0]);
        ArrayList<Integer> demandSchedule = new ArrayList<>(); // 01.00, 02.00 ...
        ArrayList<Integer> charge = new ArrayList<>();
        Scanner scanner = new Scanner(demandsFile);

        while (scanner.hasNextInt()) {
            demandSchedule.add(scanner.nextInt());
        }

        for (int i = 1; i <= demandSchedule.size(); i++) {
            charge.add(i*i);
        }


        // the energy demands arriving per hour. Then, use this data to instantiate a

        PowerGridOptimization pgo = new PowerGridOptimization(demandSchedule);
        // PowerGridOptimization object. You need to call GetOptimalPowerGridSolutionDP() method
        // of your PowerGridOptimization object to get the solution, and finally print it to STDOUT.
        OptimalPowerGridSolution sol1 = pgo.GetOptimalPowerGridSolutionDP();
        System.out.print("The total number of demanded gigawatts: ");
        System.out.println(demandSchedule.stream().mapToInt(Integer::intValue).sum());
        System.out.print("Maximum number of satisfied gigawatts: ");
        System.out.println(sol1.getmaxNumberOfSatisfiedDemands());
        System.out.print("Hours at which the battery bank should be discharged: ");
        System.out.println(sol1.getHoursToDischargeBatteriesForMaxEfficiency().stream().map(String::valueOf).collect(Collectors.joining(", ")));
        System.out.print("The number of unsatisfied gigawatts: ");
        System.out.println(demandSchedule.stream().mapToInt(Integer::intValue).sum() - sol1.getmaxNumberOfSatisfiedDemands());

        System.out.println("##MISSION POWER GRID OPTIMIZATION COMPLETED##");

        /** MISSION ECO-MAINTENANCE BELOW **/

        System.out.println("##MISSION ECO-MAINTENANCE##");
        // TODO: Your code goes here
        // You are expected to read the file given as the second command-line argument to read
        // the number of available ESVs, the capacity of each available ESV, and the energy requirements
        // of the maintenance tasks. Then, use this data to instantiate an OptimalESVDeploymentGP object.
        // You need to call getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity) method
        // of your OptimalESVDeploymentGP object to get the solution, and finally print it to STDOUT.
        System.out.println("##MISSION ECO-MAINTENANCE COMPLETED##");
    }
}
