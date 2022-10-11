package UnweightedGraphs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Graph {
    int N, E;
    Node[] nodes;

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

                    System.out.println("Node count: "+N+" Edge count: "+E);
                    nodes = new Node[N];
                    for (int i=0; i<N; i++) nodes[i] = new Node();
                    isSet = true;

                //the rest of lines - graph's content
                } else {
                    System.out.println(line);
                    int from = Integer.valueOf(line.split("\\s")[0]);
                    int to = Integer.valueOf(line.split("\\s")[1]);

                    Edge e = new Edge(nodes[to], nodes[from].edge);

                    nodes[from].edge = e;
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
        DfsPredecessor nodeData = (DfsPredecessor)node.data;
        nodeData.foundTime = DfsPredecessor.readTime();
        System.out.println("what!!");
        for (Edge e = node.edge; e != null; e = e.next) {
            System.out.println("what");
            DfsPredecessor md = (DfsPredecessor)e.to.data;
            if (md.foundTime == 0) {
                md.pred = node;
                md.distance = nodeData.distance + 1;
                dfsSearch(e.to);
            }
        }
        nodeData.finishedTime = DfsPredecessor.readTime();
    }

    public void dfs() {
        Node startNode = nodes[0];
        dfsInit();
        ((DfsPredecessor)startNode.data).distance = 0;
        dfsSearch(startNode);
    }


   /* public void transpose() {

    }*/

 /*   public void reverseSort() {
        Node[] newNodes = new Node[nodes.length];
        int counter = 0;
        for (int i = nodes.length-1; i >= 0; i--) {
            newNodes[counter] = nodes[i];
            counter++;
        }

        nodes = newNodes;
    }*/

    public static void main(String[] args) {
        Graph newGraph = new Graph();
        newGraph.dfs();
        //System.out.println(newGraph);
    }

    @Override
    public String toString() {
        String nodeDatas = "";

        return nodeDatas;
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

    @Override
    public String toString() {
        return "Found time: "+foundTime+" Finished time: "+finishedTime+"\n";
    }
}

class Node {
    Edge edge;
    Object data;
}

class Edge {
    Edge next;
    Node to;
    public Edge(Node n, Edge nxt) {
        to = n;
        next = nxt;
    }

}
