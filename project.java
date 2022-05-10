
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class

project {
    private Map<String, Integer> nodePositions = new HashMap<>();

    // You will read file and split along 1 or more spaces! You cannot hardcode data like this.
    // I used real data for A-E and then cut it off with Z to avoid typing in the whole matrix!
    private int[][] graph = {
  //Position: 0   1   2    3    4   5
            //A   B   C    D    E   Z        Position
            {0,  71, 0,   0,    0,  0},  // A     0
            {71, 0,  75,  0,    0,  0},  // B     1
            {0,  75, 0,   118,  0,  0},  // C     2
            {0,  0,  118, 0,  111, 150}, // D     3
            {0,  0,  0,   111,  0, 0},   // E     4
            {0,  0,  0,   150,  0, 0}    // Z     5
    };

    // You will populate nodePositions by reading the file! Don't assume letter input starts with A.
    public project() {
        //add elements to HashMap. Associates each node with it's position in the array.
        nodePositions.put("A", 0);
        nodePositions.put("B", 1);
        nodePositions.put("C", 2);
        nodePositions.put("D", 3);
        nodePositions.put("E", 4);
        nodePositions.put("Z", 5);
    }

    //This is not Algorithm 1 or 2. This just finding the shortest edge.
    public String findShortestEdge(String node) {
        // Gets the node letter/array position from the hashmap
        int nodeNum = nodePositions.get(node);

        //Lowest Value or Lowest Distance
        int lowestValue = Integer.MAX_VALUE; // 99999999...
        //Index position of the lowest value/distance
        int chosenPosition = Integer.MAX_VALUE; // 99999999...

        // graph.length is vertical depth (y axis)
        // i is across or x axis
        //[y][x]
        for (int i = 0; i < graph.length; i++) {
            if (graph[nodeNum][i] == 0) {
                continue; // not interested in entries with 0.
            }

            // Find lowest distance and it's position
            if (graph[nodeNum][i] < lowestValue) {
                lowestValue = graph[nodeNum][i];
                chosenPosition = i;
            }
        }
        // Convert position to letter
        String temp = getNodeByPosition(chosenPosition);
        return temp;
    }

    // This finds by key by value. This is opposite of how Maps were intended. But, we have a 1:1 mapping.
    // There are better ways to do this. Just a starting point!
    public String getNodeByPosition(int nodePos) {
        // Look through each map key/value
        for(Map.Entry keyValue: nodePositions.entrySet()){
            //if key = nodePos return the which is the node letter.
            if(keyValue.getValue().equals(nodePos)) {
                return (String)keyValue.getKey();
            }
        }
        return null; //node not found
    }

    public void readFileTest() {
        String lineFromFile = "";
        String [] tokens;
        int count = 0;

        try {
            Scanner fileInput = new Scanner (new File("graph_input2.txt"));

            // Get one line at a time and repeat the same process for all expressions in the input file.
            while (fileInput.hasNextLine()) {

                // Get the next line
                lineFromFile = fileInput.nextLine();

                //For now, skips the header row in the file A..B..C..D..
                if (count == 0) {
                    count++;
                    continue;
                }

                //Split the string fromFile using spaces...produces an Array of Strings
                tokens = lineFromFile.split("\\s+");

                for (int i = 0; i < tokens.length - 1; i++) {
                    //System.out.print(tokens[i]);
                    //System.out.print(" ");
                    if (i == 0)
                        continue;
                    int value = Integer.parseInt(tokens[i]);
                    graph[count-1][i-1] = value;

                }
                //while loop counter
                count++;
            }

            //printing out the 2d array as a test
            for (int i = 0; i < graph.length; i++) {
                for (int j = 0; j < graph.length; j++) {
                    System.out.print(graph[i][j]);
                    System.out.print(" ");
                }
                System.out.println();
            }


        }

        // File not found
        catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage() + ": File not found. Exiting.");
            System.exit(0);
        }

        // Something else went wrong. Catch all
        catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        project p = new project();
        p.readFileTest();


        //System.out.println(p.findShortestEdge("D"));

        // TODO could add findShortestDistance (between 2 nodes with a few lines of code)
        // You can start to build this into your project.


    }






}
