import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {

        // Set the default locale to English
        Locale locale = new Locale("en_EN"); 
        Locale.setDefault(locale);

        System.out.println("### URBAN INFRASTRUCTURE DEVELOPMENT START ###");
        UrbanInfrastructureDevelopment urbanInfrastructureDevelopment = new UrbanInfrastructureDevelopment();
        List<Project> projectList = urbanInfrastructureDevelopment.readXML(args[0]);
        urbanInfrastructureDevelopment.printSchedule(projectList);
        System.out.println("### URBAN INFRASTRUCTURE DEVELOPMENT END ###");

        System.out.println("### URBAN TRANSPORTATION APP START ###");
        UrbanTransportationApp urbanTransportationApp = new UrbanTransportationApp();
        HyperloopTrainNetwork network = urbanTransportationApp.readHyperloopTrainNetwork(args[1]);

        // System.out.println("Average Train Speed: " + network.averageTrainSpeed);
        // System.out.println("Average Walking Speed: " + network.averageWalkingSpeed);
        // System.out.println("Number of Train Lines: " + network.numTrainLines);
        // System.out.println("Start Point: " + network.startPoint);
        // System.out.println("Destination Point: " + network.destinationPoint);
        // System.out.println("Lines: " + network.lines);
        // THEY WORK AS EXPECTED

        List<RouteDirection> directions = urbanTransportationApp.getFastestRouteDirections(network);
        urbanTransportationApp.printRouteDirections(directions);
        System.out.println("### URBAN TRANSPORTATION APP END ###");
    }
}

