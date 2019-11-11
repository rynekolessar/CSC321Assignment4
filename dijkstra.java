import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.List;
import java.util.PriorityQueue;

// edge class to hold the edge source, destinationa and the weight of the edge

class GraphEdge {
    private char from;
    private char to;
    private int weight;
    public GraphEdge(char from, char to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
    public char getFrom() {
        return from;
    }

    public char getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }
    public String toString() {
        return "From: " + from + "\tTo: " + to + "\tWeight: " + weight;
    }
}

public class dijkstra {
    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("Case1.txt"); // input file
        Scanner obj = new Scanner(file); // scanner object to read data from file

        char source = 'A'; // source vertex
        char destination = 'B'; // destination vertex

        ArrayList<GraphEdge> edges = new ArrayList<GraphEdge>(); // list to hold all edges
        HashSet<Character> uniqueVertices = new HashSet<Character>(); // all unique vertices in map
        ArrayList<Character> allVertices = new ArrayList<Character>(); // list to hold all vertices

        int numberOfVertices = Integer.parseInt(obj.nextLine());
        
        while (obj.hasNextLine()) { // while input file has lines left
            String line = obj.nextLine();
            String vertex[] = line.split(" "); // split the line into source vertex, destination vertex and the weight

            GraphEdge edge = new GraphEdge(vertex[0].charAt(0), vertex[1].charAt(0), Integer.parseInt(vertex[2]));
            edges.add(edge);

            if (!uniqueVertices.contains(vertex[0].charAt(0))) { // if vertex not present in unique vertices map, add it in map and arraylist
                uniqueVertices.add(vertex[0].charAt(0));
                allVertices.add(vertex[0].charAt(0));
            }

            if (!uniqueVertices.contains(vertex[1].charAt(0))) { // if vertex not present in unique vertices map, add it in map and arraylist
                uniqueVertices.add(vertex[1].charAt(0));
                allVertices.add(vertex[1].charAt(0));
            }
        }

        HashMap<Character, Integer> distance = new HashMap<Character, Integer>();

        for (Character ch : allVertices) {
            distance.put(ch, Integer.MAX_VALUE);
        }

        distance.put(source, 0); // source distance is 0, update in distance map

        // comparator to arrange the vertices in the priority queue in min heap fashion
        Comparator<Character> cmp = new Comparator<Character>() {
            public int compare(Character a, Character b) {
                if (distance.get(a) < distance.get(b))
                    return -1;

                else if (distance.get(a) == distance.get(b))
                    return 0;

                else
                    return 1;
            }
        };

        // create a priority queue
        PriorityQueue<Character> pq = new PriorityQueue<Character>(cmp);// priority queue / min heap to arrange vertices
                                                                        // in min heap fashion

        for (Character ch : allVertices)
            pq.add(ch);// add all the vertices to pq

        String path = "";// hold the final path from a to b

        while (!pq.isEmpty()) { // while there are elements in the pq
            char ch = pq.remove();// get the vertex
            path += ch;// append the vertex to path
            if (ch == destination)// if reached destination
                break;// loop out

            List<GraphEdge> outVertex = getAllOutgoingEdges(edges, ch);

            for (GraphEdge edge : outVertex) { // for each egde in the outVertex list
                if (pq.contains(edge.getTo())) { // if the destination vertex in the edge is present in the pq
                    if (distance.get(ch) + edge.getWeight() < distance.get(edge.getTo())) {// if new distance is less than the old distance
                        distance.put(edge.getTo(), distance.get(ch) + edge.getWeight());// put new distance
                    }

                    // the below 2 lines are executed to update the priority queue after change to
                    // distance
                    pq.remove(edge.getTo());// remove destination vertex of edge to pq
                    pq.add(edge.getTo());// add destination vertex of edge to pq
                }
            }
        }
        
        // solution file

        // File output = new File("DijkstraSolution.txt");

        // PrintWriter pw = new PrintWriter(output);

        // pw.println(distance.get(destination));// print the minuimum distance to destination to file

        // pw.println(path);// print the path to file

        System.out.println(distance.get(destination));

        // print a successful message to the console

        System.out.println("Successful!");

        //pw.flush(); // flush the output to file

        // pw.close(); // close pw

        obj.close(); // close obj
    }

    // get all the outgoing edges in a list from the source vetrtex

    public static List<GraphEdge> getAllOutgoingEdges(ArrayList<GraphEdge> list, char source) {
        List<GraphEdge> result = new ArrayList<GraphEdge>();
        for (GraphEdge edge : list) {
            if (edge.getFrom() == source)
                result.add(edge);
        }
        return result;
    }
}