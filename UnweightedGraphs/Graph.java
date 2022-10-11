package UnweightedGraphs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Graph {
    int N, E;
    Node[] nodes;
    static ArrayList<Node> visited = new ArrayList<>();

    public Graph() {
        BufferedReader bufferReader = null;
        //TODO find better name for this variable
        boolean isSet = false;
        try {
            bufferReader = new BufferedReader(new FileReader("oppg6.txt"));
            String line = "";

            while ((line = bufferReader.readLine()) != null) {
                //first line - N (node count) and E (edge count) values
                if (!isSet) {
                    N = Integer.valueOf(line.split("\\s")[0]);
                    E = Integer.valueOf(line.split("\\s")[1]);

                    nodes = new Node[N];
                    for (int i=0; i<N; i++) nodes[i] = new Node();
                    isSet = true;

                //the rest of lines - graph's content
                } else {
                    int from = Integer.valueOf(line.split("\\s")[0]);
                    int to = Integer.valueOf(line.split("\\s")[1]);

                    nodes[to].setValue(to);
                    nodes[from].setValue(from);

                    Edge e = new Edge(nodes[from], nodes[to], nodes[from].edge);
                    nodes[from].edge = e;
                    
                    //System.out.println(e);
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

    public void dfsSearch(Node node) {
        DfsPredecessor nodeData = node.data;
        nodeData.foundTime = DfsPredecessor.readTime();
        for (Edge e = node.edge; e != null; e = e.next) {
            DfsPredecessor md = e.to.data;
            if (md.foundTime == 0) {
                md.pred = node;
                md.distance = nodeData.distance + 1;
                dfsSearch(e.to);
            }
        }
        visited.add(node);
        nodeData.finishedTime = DfsPredecessor.readTime();
    }

    public void dfs() {
        for (Node node : nodes) {
            dfsInit();
            (node.data).distance = 0;
            dfsSearch(node);
        }

    }


    public void transpose() {

    }

    public void reverseSort() {
        //Arrays.sort(nodes, (Node s1, Node s2) -> s1.data.getFinishedTime().compareTo(s2.data.getFinishedTime()));

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
        Graph newGraph = new Graph();
        newGraph.dfs();

        newGraph.reverseSort();
        newGraph.transpose();

        for (Node node : newGraph.nodes) {
            System.out.println(node);
        }
    }

    /*@Override
    public String toString() {
        String nodeDatas = "";

        return nodeDatas;
    }*/
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
        return "Node: "+value+" Data: "+data;
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
