import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * This class accomplishes Mission Eco-Maintenance
 */
public class OptimalESVDeploymentGP
{
    private ArrayList<Integer> maintenanceTaskEnergyDemands;

    /*
     * Should include tasks assigned to ESVs.
     * For the sample input:
     * 8 100
     * 20 50 40 70 10 30 80 100 10
     * 
     * The list should look like this:
     * [[100], [80, 20], [70, 30], [50, 40, 10], [10]]
     * 
     * It is expected to be filled after getMinNumESVsToDeploy() is called.
     */
    private ArrayList<ArrayList<Integer>> maintenanceTasksAssignedToESVs = new ArrayList<>();

    ArrayList<ArrayList<Integer>> getMaintenanceTasksAssignedToESVs() {
        return maintenanceTasksAssignedToESVs;
    }

    public OptimalESVDeploymentGP(ArrayList<Integer> maintenanceTaskEnergyDemands) {
        this.maintenanceTaskEnergyDemands = maintenanceTaskEnergyDemands;
    }

    public ArrayList<Integer> getMaintenanceTaskEnergyDemands() {
        return maintenanceTaskEnergyDemands;
    }

    /**
     *
     * @param maxNumberOfAvailableESVs the maximum number of available ESVs to be deployed
     * @param maxESVCapacity the maximum capacity of ESVs
     * @return the minimum number of ESVs required using first fit approach over reversely sorted items.
     * Must return -1 if all tasks can't be satisfied by the available ESVs
     */
    public int getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity)
    {
        // TODO: Your code goes here
        ArrayList<Integer> tasks = new ArrayList<>(maintenanceTaskEnergyDemands);
        tasks.sort(Collections.reverseOrder());

        ArrayList<Integer> ESVRemainingCapacities = new ArrayList<>(Collections.nCopies(maxNumberOfAvailableESVs, maxESVCapacity));

        int minNumESVsToDeploy = 0;

        for (int taskEnergy : tasks) {
            boolean taskAssigned = false;
            for (int i = 0; i < minNumESVsToDeploy; i++) {
                if (ESVRemainingCapacities.get(i) >= taskEnergy) {
                    ESVRemainingCapacities.set(i, ESVRemainingCapacities.get(i) - taskEnergy);
                    maintenanceTasksAssignedToESVs.get(i).add(taskEnergy);
                    taskAssigned = true;
                    break;
                }
            }

            if (!taskAssigned) {
                if (minNumESVsToDeploy == maxNumberOfAvailableESVs) {
                    return -1;
                }
                ESVRemainingCapacities.set(minNumESVsToDeploy, maxESVCapacity - taskEnergy);
                ArrayList<Integer> newESV = new ArrayList<>();
                newESV.add(taskEnergy);
                maintenanceTasksAssignedToESVs.add(newESV);
                minNumESVsToDeploy++;
            }
        }

        return minNumESVsToDeploy;
    }

}
