import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

//Locations for user input are line 178 and line 214
public class

project { //Initialize data structures needed
    private Map<String, Integer> nodePositions = new HashMap<>();
    private Map<String, Integer> directDistance = new HashMap<>();
    private Stack projectStack = new Stack(); //Use Stack for ShortestPath
    private Queue<String> projectQueue = new LinkedList<String>(); //Use Queue for sequence of all nodes
    private int[][] graph = {};
    private LinkedList<String> currentNode = new LinkedList<String>(); //Use LinkedList to track currentNode

    //Finding the shortest edge.
    /** First Method
     * Finds shortest edge via graph (2d array) given a node input by String
     * @param node
     * Input "node" is the input node by String
     * @return
     * Returns node by String
     */
    public String findShortestEdge(String node) {
        //Gets the node letter/array position from the hashmap
        int nodeNum = nodePositions.get(node);
        //Lowest Value or Lowest Distance
        int lowestValue = Integer.MAX_VALUE; // 99999999...
        //Index position of the lowest value/distance
        int chosenPosition = Integer.MAX_VALUE; // 99999999...

        //graph.length is vertical depth (y axis)
        //’i’ is across (x axis)
        //[y][x]
        for (int i = 0; i < graph.length; i++) {
            if (graph[nodeNum][i] == 0) {
                continue; //Not interested in entries with 0.
            }
            if (directDistance.get(getNodeByPosition(i)) == 0) {
                lowestValue = graph[nodeNum][i];
                chosenPosition = i;
                break; //If ‘Z’ is an option, choose ‘Z’
            }
            if (projectQueue.contains(getNodeByPosition(i))) {
                continue; //Not interested in nodes already in queue
            }
            //Find lowest distance and its position
            if (graph[nodeNum][i] < lowestValue) {
                lowestValue = graph[nodeNum][i];
                chosenPosition = i;
            }
        }
        // Convert position to letter
        return getNodeByPosition(chosenPosition);
    }

    //Finding the shortest distance.

    /** Second Method
     * Finds shortest distance via directDistance (HashMap) given a node by input String
     * @param node
     * Input "node" is the input node by String
     * @return
     * Returns node by String
     */
    public String findShortestDistance(String node) {
        //Gets the node letter/array position from the hashmap
        int nodeNum = nodePositions.get(node);

        //Lowest Value or Lowest Distance
        int lowestValue = Integer.MAX_VALUE;
        //Index position of the lowest value/distance
        int chosenPosition = Integer.MAX_VALUE;

        //graph.length is vertical depth (y axis)
        //’i’ is across (x axis)
        //[y][x]
        for (int i = 0; i < graph.length; i++) {
            if (graph[nodeNum][i] == 0) {
                continue; //Not interested in entries with 0.
            }
            if (directDistance.get(getNodeByPosition(i)) == 0) {
                lowestValue = graph[nodeNum][i];
                chosenPosition = i;
                break; //If ‘Z’ is an option, choose ‘Z’
            }
            if (projectQueue.contains(getNodeByPosition(i))) {
                continue; //Not interested in nodes already in queue
            }
            //Find lowest distance and its position
            if (directDistance.get(getNodeByPosition(i)) < lowestValue) {
                lowestValue = directDistance.get(getNodeByPosition(i));
                chosenPosition = i;
            }
        }
        //Convert position to letter
        return getNodeByPosition(chosenPosition);
    }

    //Finding the shortest distance.

    /** Third Method
     * Finds shortest distance of graph (2d array) and directDistance (HashMap) combined given a node by input String
     * @param node
     * Input "node" is the input node by String
     * @return
     * returns node by String
     */
    public String findShortestEdgeDistance(String node) {
        //Gets the node letter/array position from the hashmap
        int nodeNum = nodePositions.get(node);

        //Lowest Value or Lowest Distance
        int lowestValue = Integer.MAX_VALUE;
        //Index position of the lowest value/distance
        int chosenPosition = Integer.MAX_VALUE;

        //graph.length is vertical depth (y axis)
        //’i’ is across (x axis)
        //[y][x]
        for (int i = 0; i < graph.length; i++) {
            if (graph[nodeNum][i] == 0) {
                continue; //Not interested in entries with 0.
            }
            if (directDistance.get(getNodeByPosition(i)) == 0) {
                lowestValue = graph[nodeNum][i];
                chosenPosition = i;
                break; //If ‘Z’ is an option, choose ‘Z’
            }
            if (projectQueue.contains(getNodeByPosition(i))) {
                continue; //Not interested in nodes already in queue
            }
            //Find lowest distance and its position
            if (directDistance.get(getNodeByPosition(i))+graph[nodeNum][i] < lowestValue) {
                lowestValue = directDistance.get(getNodeByPosition(i))+graph[nodeNum][i];
                chosenPosition = i;
            }
        }
        //Convert position to letter
        return getNodeByPosition(chosenPosition);
    }

    //This finds by key by value

    /** Fourth Method
     * Finds node String via int nodePosition
     * @param nodePos
     * Input "nodePos" is the nodePosition by int
     * @return
     * returns node by String, null if not found
     */
    public String getNodeByPosition(int nodePos) {
        //Look through each map key/value
        for(Map.Entry keyValue: nodePositions.entrySet()){
            //If key = nodePos, return the which is the node letter
            if(keyValue.getValue().equals(nodePos)) {
                return (String)keyValue.getKey();
            }
        }
        return null; //node not found
    }

    /** Fifth Method
     * Reads direct_distance file via Scanner and populates to HashMap and creates empty graph (2d array)
     * No input
     * No output
     */
    public void readDirectDistanceFile() {
        int count = 0;

        try {
            Scanner fileInput = new Scanner(new File("direct_distance.txt")); //User input here

            //While there is a next line, populate directDistance/nodePositions from scanner
            while (fileInput.hasNextLine()) {
                String[] columns = fileInput.nextLine().split("\\s+");
                directDistance.put(columns[0], Integer.parseInt(columns[1]));
                nodePositions.put(columns[0],count);
                count++;
            }
        }
        //File not found
        catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage() + ": File not found. Exiting.");
            System.exit(0);
        }
        //Something else went wrong- catch all
        catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            System.exit(0);
        }
        int nodeTotal = directDistance.size(); //Create graph based on number of nodes
        graph = new int [nodeTotal][nodeTotal];
    }

    /** Sixth Method
     * reads graph_input file via Scanner and populates to graph (2d array)
     * No input
     * No output
     */
    public void readGraphFile() {
        String lineFromFile = "";
        String [] tokens;
        int count = 0;

        try {
            Scanner fileInput = new Scanner (new File("graph_input.txt")); //User input here

            //Get one line at a time and repeat the same process for all expressions in the input file.
            while (fileInput.hasNextLine()) {

                //Get the next line
                lineFromFile = fileInput.nextLine();

                //For now, skips the header row in the file A..B..C..D..
                if (count == 0) {
                    count++;
                    continue;
                }

                //Split the string fromFile using spaces
                tokens = lineFromFile.split("\\s+");
                //Populate graph
                for (int i = 0; i < tokens.length; i++) {
                    if (i == 0)
                        continue;
                    int value = Integer.parseInt(tokens[i]);
                    graph[count-1][i-1] = value;
                }
                //While loop counter
                count++;
            }
        }
        //File not found
        catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage() + ": File not found. Exiting.");
            System.exit(0);
        }

        //Something else went wrong- catch all
        catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            System.exit(0);
        }
    }

    /** Seventh Method
     * Testing method to print graph (2d array) and directDistance (HashMap)
     * No input
     * No output
     */
    public void printTest() {
        //Prints out 2d array and hashmap from .txt
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                System.out.print(graph[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println(directDistance);
    }

    /** Algorithm One
     * Traverses through graph to 'Z', prioritizing nodes with smallest directDistance value
     * @param userInput
     * Input "userInput" is the user's input by String.
     * Prints sequence of all nodes and shortest path to 'Z' as well as shortest path length
     * No output
     */
    public void algorithmOne(String userInput) {
        currentNode.add(userInput); //Set currentNode to userInput
        while (directDistance.get(currentNode.get(0)) != 0) { //While loop
            projectStack.push(currentNode.get(0)); //Add currentNode to Stack
            projectQueue.add(currentNode.get(0)); //Add currentNode to Queue
            if (currentNode.get(0) == "Z") {
                break; //If ‘Z’, break
                //If dead-end or have previously visited all options, backtrack
            } else if (findShortestDistance(currentNode.get(0)) == null) {
                projectStack.pop(); //Distance Method for algorithmOne
                currentNode.clear();
                currentNode.add((String) projectStack.pop());
                //Else progress to closest node
            } else {
                String temp = currentNode.get(0);
                currentNode.clear();
                currentNode.add(findShortestDistance(temp));
            }
        }
        projectStack.push(currentNode.get(0)); //If ‘Z’, push and add
        projectQueue.add(currentNode.get(0));

        //Print
        System.out.println("");
        System.out.println("Algorithm 1: ");
        System.out.println("");
        System.out.print("Sequence of all nodes: ");
        int queueSize = projectQueue.size(); //Set queue size as int
        for (int i = 0; i < queueSize-1; i++) { //Print with ‘->’
            System.out.print(projectQueue.remove());
            System.out.print("");
            System.out.print(" -> ");
        }
        System.out.println(projectQueue.remove()); //Print last
        System.out.print("Shortest path: ");
        for (int i = 0; i < projectStack.size()-1; i++) { //Print with ‘->’
            System.out.print(projectStack.get(i));
            System.out.print("");
            System.out.print(" -> ");
        }
        System.out.println(projectStack.peek()); //Print last
        int shortestPathLength = 0;
        //Print distance between Shortest Path nodes
        for (int i = 1; i < projectStack.size(); i++) {
            int a = nodePositions.get(projectStack.get(i));
            int b = nodePositions.get(projectStack.get(i-1));
            shortestPathLength = shortestPathLength + graph[a][b];
        }
        System.out.print("Shortest path length: ");
        System.out.println(shortestPathLength);
        projectStack.clear(); //Clear
        projectQueue.clear();
        currentNode.clear();
    }
    /** Algorithm Two
     * Traverses through graph to 'Z', prioritizing nodes with smallest directDistance + graph value
     * @param userInput
     * Input "userInput" is the user's input by String.
     * Prints sequence of all nodes and shortest path to 'Z' as well as shortest path length
     * No output
     */
    public void algorithmTwo(String userInput) {
        currentNode.add(userInput); //Set currentNode to userInput
        while (directDistance.get(currentNode.get(0)) != 0) { //While loop
            projectStack.push(currentNode.get(0)); //Add currentNode to Stack
            projectQueue.add(currentNode.get(0)); //Add currentNode to Queue
            if (currentNode.get(0) == "Z") {
                break; //If ‘Z’, break
//If dead-end or have previously visited all options, backtrack
            } else if (findShortestEdgeDistance(currentNode.get(0)) == null) {
                projectStack.pop(); //EdgeDistance Method for algorithmTwo
                currentNode.clear();
                currentNode.add((String) projectStack.pop());
                //Else progress to closest node
            } else {
                String temp = currentNode.get(0);
                currentNode.clear();
                currentNode.add(findShortestEdgeDistance(temp));
            }
        }
        projectStack.push(currentNode.get(0)); //If ‘Z’, push and add
        projectQueue.add(currentNode.get(0));

//Print
        System.out.println("");
        System.out.println("Algorithm 2: ");
        System.out.println("");
        System.out.print("Sequence of all nodes: ");
        int queueSize = projectQueue.size(); //Set queue size as int
        for (int i = 0; i < queueSize-1; i++) { //Print with ‘->’
            System.out.print(projectQueue.remove());
            System.out.print("");
            System.out.print(" -> ");
        }
        System.out.println(projectQueue.remove()); //Print last
        System.out.print("Shortest path: ");
        for (int i = 0; i < projectStack.size()-1; i++) { //Print with ‘->’
            System.out.print(projectStack.get(i));
            System.out.print("");
            System.out.print(" -> ");
        }
        System.out.println(projectStack.peek()); //Print last
        int shortestPathLength = 0;
//Print distance between Shortest Path nodes
        for (int i = 1; i < projectStack.size(); i++) {
            int a = nodePositions.get(projectStack.get(i));
            int b = nodePositions.get(projectStack.get(i-1));
            shortestPathLength = shortestPathLength + graph[a][b];
        }
        System.out.print("Shortest path length: ");
        System.out.println(shortestPathLength);
        projectStack.clear(); //Clear
        projectQueue.clear();
        currentNode.clear();
    }

    /** Eighth Method
     * Executes algorithmOne and algorithmTwo given user prompted input
     * @param args
     * main
     * No output
     */
    public static void main(String[] args) {
        project p = new project(); //New project
        p.readDirectDistanceFile(); //Read data from file
        p.readGraphFile();

        //Check for valid input
        String [] validInput=new String [p.nodePositions.size()];
        for (int i=0; i<p.nodePositions.size(); i++) {
            validInput[i]=p.getNodeByPosition(i);
        } //Only take input that is in nodePositions
        Scanner userInput = new Scanner(System.in);
        System.out.println("Please enter your starting node: ");
        while (!userInput.hasNext(Arrays.toString(validInput))) {
            System.out.println("Please input a functional starting node.");
            userInput.next();
        }
        String inputString = userInput.next();

        p.algorithmOne(inputString); //Execute algorithmOne with inputString
        p.algorithmTwo(inputString); //Execute algorithmTwo with inputString
    }

}

