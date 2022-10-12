package UnweightedGraphs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Graph {
    int N, E;
    Node[] nodes;
    boolean[] visited;
    static String filename = "ø6g6.txt";

    public Graph(boolean transpose) {
        BufferedReader bufferReader;
        boolean isNotFirstLine = false;
        try {
            bufferReader = new BufferedReader(new FileReader(filename));
            String line;
            int index;
            //read from file
            while ((line = bufferReader.readLine()) != null) {
                //check first line for N-nodes and E-edges
                if (!isNotFirstLine) {
                    N = Integer.valueOf(line.split("\\s")[0]);
                    E = Integer.valueOf(line.split("\\s")[1]);

                    visited = new boolean[N];
                    for (boolean value : visited) {
                        value = false;
                    }

                    nodes = new Node[N];
                    for (int i=0; i<N; i++) {
                        nodes[i] = new Node();
                        nodes[i].value = i;
                    }

                    isNotFirstLine = true;

                //the rest of lines - graph's content
                } else {
                    //trick for handling different whitespaces
                    index = 0;
                    if (line.split("\\s+")[0].isEmpty()) {
                        index = index +1;
                    }

                    int from = Integer.valueOf(line.split("\\s+")[index]);
                    int to = Integer.valueOf(line.split("\\s+")[index+1]);

                    Edge edge;

                    if (transpose) {
                        edge = new Edge(nodes[to], nodes[from], nodes[to].edge);
                        nodes[to].edge = edge;
                    } else {
                        edge = new Edge(nodes[from], nodes[to], nodes[from].edge);
                        nodes[from].edge = edge;
                    }

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void dfsInit() {
        for (int i = N; i-->0;) {
            nodes[i].data = new DfsPredecessor();
        }
        DfsPredecessor.resetTime();
    }

    public void dfsSearch(Node node, boolean transpose) {
        visited[node.value] = true;
        if (transpose) {
            System.out.print(node.value+" ");
        }
        DfsPredecessor nodeData = node.data;
        nodeData.foundTime = DfsPredecessor.readTime();
        for (Edge e = node.edge; e != null; e = e.next) {
            DfsPredecessor md = e.to.data;
            if (md.foundTime == 0) {
                md.pred = node;
                md.distance = nodeData.distance + 1;
                dfsSearch(e.to, transpose);
            }
        }

        nodeData.finishedTime = DfsPredecessor.readTime();
    }

    public void dfs() {
        dfsInit();

        for (Node node : nodes) {
            if (!visited[node.value]) {
                (node.data).distance = 0;
                dfsSearch(node, false);
            }
        }
    }

    public int transposeAndDFS() {
        Graph transposeGraph = new Graph(true);
        transposeGraph.dfsInit();
        int index;
        int componentCounter = 1;
        for (Node node : nodes) {
            index = node.value;
            if (!transposeGraph.visited[index]) {
                transposeGraph.nodes[index].data.distance = 0;
                System.out.print("Kompontent "+componentCounter+": ");
                transposeGraph.dfsSearch(transposeGraph.nodes[index], true);
                System.out.println();
                componentCounter++;
            }
        }

        return componentCounter-1;
    }

    public void reverseSort() {
        Arrays.sort(nodes, (b1, b2) -> {
            if (b1.data.getFinishedTime() < b2.data.getFinishedTime()) {
                return 1;
            } else if (b1.data.getFinishedTime() > b2.data.getFinishedTime()) {
                return -1;
            } else {
                return 0;
            }
        });
    }

    public static void main(String[] args) {
        Graph normalGraph = new Graph(false);
        normalGraph.dfs();
        normalGraph.reverseSort();
        System.out.println("\nKomponentene til grafen "+filename+": \n");
        int componentCounter = normalGraph.transposeAndDFS();
        System.out.println("\nGrafen har "+componentCounter+" sterkt sammenhengende kompontent(er).");
    }

}
class Predecessor {
    int distance;
    Node pred;
    static int infinite = 1000000000;

    public Predecessor() {
        distance = infinite;
    }
}

class DfsPredecessor extends Predecessor {
    int foundTime, finishedTime;
    static int time;

    static void resetTime() {
        time = 0;
    }
    static int readTime() {
        return ++time;
    }

    public int getFinishedTime() {
        return finishedTime;
    }

    @Override
    public String toString() {
        return "Found time: "+foundTime+" Finished time: "+finishedTime;
    }
}

class Node {
    Edge edge;
    DfsPredecessor data;
    int value;

    public void setValue(int val) {
        value = val;
    }

    @Override
    public String toString() {
        return "Node: "+value;
    }
}

class Edge {
    Edge next;
    Node from;
    Node to;

    public Edge(Node fr, Node t, Edge nxt) {
        from = fr;
        to = t;
        next = nxt;
    }

    @Override
    public String toString() {
        return from+" -> "+to;
    }
}
