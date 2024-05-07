import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HyperloopTrainNetwork implements Serializable {
    static final long serialVersionUID = 11L;
    public double averageTrainSpeed;
    public final double averageWalkingSpeed = 1000 / 6.0;;
    public int numTrainLines;
    public Station startPoint;
    public Station destinationPoint;
    public List<TrainLine> lines;

    /**
     * Method with a Regular Expression to extract integer numbers from the fileContent
     * @return the result as int
     */
    public int getIntVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]+)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Integer.parseInt(m.group(1));
    }

    /**
     * Write the necessary Regular Expression to extract string constants from the fileContent
     * @return the result as String
     */
    public String getStringVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*\"([^\"]*)\"");
        Matcher m = p.matcher(fileContent);
        m.find();
        return m.group(1);
    }

    /**
     * Write the necessary Regular Expression to extract floating point numbers from the fileContent
     * Your regular expression should support floating point numbers with an arbitrary number of
     * decimals or without any (e.g. 5, 5.2, 5.02, 5.0002, etc.).
     * @return the result as Double
     */
    public Double getDoubleVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]*\\.?[0-9]+)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Double.parseDouble(m.group(1));
    }

    /**
     * Write the necessary Regular Expression to extract a Point object from the fileContent
     * points are given as an x and y coordinate pair surrounded by parentheses and separated by a comma
     * @return the result as a Point object
     */
    public Point getPointVar(String varName, String fileContent) {
        Pattern pattern = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*\\([\\t ]*(\\d+)[\\t ]*,[\\t ]*(\\d+)[\\t ]*\\)");
    
        Matcher matcher = pattern.matcher(fileContent);
    
        if (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
    
            return new Point(x, y);
        } else {
            throw new IllegalArgumentException("Could not parse point from line: " + fileContent);
        }
    }
    
    public List<Station> getStations(String line) {
        List<Station> stations = new ArrayList<>();
    
        Pattern pattern = Pattern.compile("[\\t ]*\\([\\t ]*(\\d+)[\\t ]*,[\\t ]*(\\d+)[\\t ]*\\)[\\t ]*");
    
        Matcher matcher = pattern.matcher(line);
    
        int stationNumber = 1;
    
        while (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
    
            stations.add(new Station(new Point(x, y), "Station " + stationNumber));
    
            stationNumber++;
        }
    
        return stations;
    }

    /**
     * Function to extract the train lines from the fileContent by reading train line names and their 
     * respective stations.
     * @return List of TrainLine instances
     */
    public List<TrainLine> getTrainLines(String fileContent) {
        List<TrainLine> trainLines = new ArrayList<>();
    
        String trainLineName = null;
    
        for (String line : fileContent.split("\n")) {

                if (line.contains("train_line_name")) {
                    trainLineName = getStringVar("train_line_name", line);
                } 
                else if (line.contains("train_line_stations")) {
                    List<Station> stations = getStations(line);
                    trainLines.add(new TrainLine(trainLineName, stations));
                }
            
        }
    
        return trainLines;
    }

    public String readFile(String filename) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)));
        } 
        catch (IOException e) {
            int nop = 0;
        }

        return content;
    }

    public double getWalkingTime(Station station1, Station station2) {
        double distance = getDistance(station1, station2);
        return distance / averageWalkingSpeed;
    }
    
    public double getTrainRideTime(Station station1, Station station2) {
        double distance = getDistance(station1, station2);
        return distance / averageTrainSpeed;
    }
    
    public double getDistance(Station station1, Station station2) {
        // This method should return the distance between the two stations.
        // You need to implement this method based on your data structure.
        return Math.abs(station1.coordinates.x - station2.coordinates.x) + Math.abs(station1.coordinates.y - station2.coordinates.y);
    }

    /**
     * Function to populate the given instance variables of this class by calling the functions above.
     */
    public void readInput(String filename) {

        // TODO: Your code goes here

        String fileContent = readFile(filename);

        StringBuilder trainLinesAndStations = new StringBuilder();


         for (String line : fileContent.split("\n")) {
                if (line.contains("num_train_lines")) {
                    numTrainLines = getIntVar("num_train_lines", line);
                } 
                else if (line.contains("starting_point")) {
                    startPoint = new Station(getPointVar("starting_point", line), "Starting Point");
                } 
                else if (line.contains("destination_point")) {
                    destinationPoint = new Station(getPointVar("destination_point", line), "Destination Point");
                } 
                else if (line.contains("average_train_speed")) {
                    averageTrainSpeed = getDoubleVar("average_train_speed", line);
                } 
                else if (line.contains("train_line_name") || line.contains("train_line_stations")) {
                    trainLinesAndStations.append(line).append("\n");
                }
        }

        lines = getTrainLines(trainLinesAndStations.toString());

    }
}