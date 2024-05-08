import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class UrbanTransportationApp implements Serializable {
    static final long serialVersionUID = 99L;
    
    public HyperloopTrainNetwork readHyperloopTrainNetwork(String filename) {
        HyperloopTrainNetwork hyperloopTrainNetwork = new HyperloopTrainNetwork();
        hyperloopTrainNetwork.readInput(filename);
        return hyperloopTrainNetwork;
    }

    public class Node {
        String name;
        Point coordinates;
    
        public Node(String name, Point coordinates) {
            this.name = name;
            this.coordinates = coordinates;
        }

        public String getName() {
            return name;
        }
    }

    public class Graph {
        List<Node> nodes;
        Map<Node, Map<Node, Double>> edges;

        public Graph() {
            nodes = new ArrayList<>();
            edges = new HashMap<>();
        }

        public void addNode(Node node) {
            nodes.add(node);
            edges.put(node, new HashMap<>());
        }

        public void addEdge(Node node1, Node node2, double weight) {
            edges.get(node1).put(node2, weight);
            edges.get(node2).put(node1, weight);
        }

        public List<Node> getNodes() {
            return nodes;
        }

        public Map<Node, Map<Node, Double>> getEdges() {
            return edges;
        }

        public Node findNodeInGraph(String name) {
            for (Node node : this.getNodes()) {
                if (node.getName().equals(name)) {
                    return node;
                }
            }
            return null; 
        }
    }


    /**
     * Function calculate the fastest route from the user's desired starting point to 
     * the desired destination point, taking into consideration the hyperloop train
     * network. 
     * @return List of RouteDirection instances
     */
    public List<RouteDirection> getFastestRouteDirections(HyperloopTrainNetwork network) {
        List<RouteDirection> routeDirections = new ArrayList<>();
        
        // TODO: Your code goes here

        Graph graph = new Graph();

        for (TrainLine line : network.lines) {
            List<Station> stations = line.trainLineStations;

            for (int i = 0; i < stations.size(); i++) {
                Station station = stations.get(i);
                Node node = new Node(line.trainLineName + " Line " + station.description, station.coordinates);
                graph.addNode(node);
            }
        }

        Node startNode = new Node("Starting Point", network.startPoint.coordinates);
        Node destinationNode = new Node("Final Destination", network.destinationPoint.coordinates);

        graph.addNode(startNode);
        graph.addNode(destinationNode);

        // WALKING FIRSTTTT
        for (Node station1 : graph.getNodes()) {
            for (Node station2 : graph.getNodes()) {
                if (station1 != station2) {
                    double distance = Math.sqrt(Math.pow(station1.coordinates.x - station2.coordinates.x, 2) + Math.pow(station1.coordinates.y - station2.coordinates.y, 2));
                    double time = distance / network.averageWalkingSpeed; 
                
                    graph.addEdge(station1, station2, time);
                }
            }
        }

        // UPDATE TRAIN EDGES
        for (TrainLine line : network.lines) {
            List<Station> stations = line.trainLineStations;
        
            for (int i = 0; i < stations.size() - 1; i++) {
                Station station1 = stations.get(i);
                Station station2 = stations.get(i + 1);
            
                Node node1 = graph.findNodeInGraph(line.trainLineName + " Line " + station1.description);
                Node node2 = graph.findNodeInGraph(line.trainLineName + " Line " + station2.description);
            
                double distance = Math.sqrt(Math.pow(node1.coordinates.x - node2.coordinates.x, 2) + Math.pow(node1.coordinates.y - node2.coordinates.y, 2)); // distance in meters
                double speedKmPerHour = network.averageTrainSpeed; // km/h
                double speedMetersPerMinute = speedKmPerHour * 1000 / 60; // meters per minute
                double time = distance / speedMetersPerMinute; 
            
                graph.addEdge(node1, node2, time);
            }
        }

        for (Node station : graph.getNodes()) {
            double distanceToStart = Math.sqrt(Math.pow(station.coordinates.x - startNode.coordinates.x, 2) + Math.pow(station.coordinates.y - startNode.coordinates.y, 2));
            double timeToStart = distanceToStart / network.averageWalkingSpeed; 
            graph.addEdge(startNode, station, timeToStart);
        
            double distanceToEnd = Math.sqrt(Math.pow(station.coordinates.x - destinationNode.coordinates.x, 2) + Math.pow(station.coordinates.y - destinationNode.coordinates.y, 2));
            double timeToEnd = distanceToEnd / network.averageWalkingSpeed;
            graph.addEdge(station, destinationNode, timeToEnd);
        }

        Map<Node, Double> distances = new HashMap<>();
        for (Node node : graph.getNodes()) {
            distances.put(node, Double.POSITIVE_INFINITY);
        }

        distances.put(startNode, 0.0);

        Map<Node, Node> previousNode = new HashMap<>();

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparing(distances::get));
        queue.add(startNode);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
        
            for (Map.Entry<Node, Double> entry : graph.getEdges().get(currentNode).entrySet()) {
                Node neighbor = entry.getKey();
                double weight = entry.getValue();
                double newDistance = distances.get(currentNode) + weight;
        
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previousNode.put(neighbor, currentNode);
                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        Node currentNode = destinationNode;
        while (currentNode != null) {
            Node nextNode = previousNode.get(currentNode);

            if (nextNode != null) {
                boolean trainRide = !(currentNode.getName().equals("Starting Point") || currentNode.getName().equals("Final Destination") || nextNode.getName().equals("Starting Point") || nextNode.getName().equals("Final Destination"));
                RouteDirection routeDirection = new RouteDirection(nextNode.getName(), currentNode.getName(), distances.get(currentNode) - distances.get(nextNode), trainRide);
                routeDirections.add(routeDirection);
            }
            currentNode = nextNode;
        }

        Collections.reverse(routeDirections);

        return routeDirections;
    }

    /**
     * Function to print the route directions to STDOUT
     */
    public void printRouteDirections(List<RouteDirection> directions) {
        double totalDuration = 0;
        for (RouteDirection direction : directions) {
            totalDuration += direction.duration;
        }

        System.out.printf("The fastest route takes %d minute(s).\n", Math.round(totalDuration));
        System.out.println("Directions");
        System.out.println("----------");
        
        int step = 1;
        
        for (RouteDirection direction : directions) {
            String mode = direction.trainRide ? "Get on the train" : "Walk";
            System.out.printf("%d. %s from \"%s\" to \"%s\" for %.2f minutes.\n", step++, mode, direction.startStationName, direction.endStationName, direction.duration);
        }
    }
}

/*
 * Average Train Speed: 400.0
Average Walking Speed: 166.66666666666666
Number of Train Lines: 2
Start Point: Starting Point at (0, 0)
Destination Point: Destination Point at (10000, 1000)
Lines: [Kizilay-Sincan line with stations: [Station 1 at (0, 200), Station 2 at (5000, 200), Station 3 at (7000, 200)], Ulus-Beytepe line with stations: [Station 1 at (2000, 600), Station 2 at (5000, 600), Station 3 at (10000, 600)]]
 */