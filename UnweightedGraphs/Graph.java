package UnweightedGraphs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Graph {
    int N, E;
    Node[] nodes;
    static boolean[] originStack;
    static ArrayList<Node> visited = new ArrayList<>();

    public Graph(boolean transpose) {
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
                    originStack = new boolean[N];
                    for (boolean value : originStack) {
                        value = false;
                    }

                    nodes = new Node[N];
                    for (int i=0; i<N; i++) nodes[i] = new Node();

                    isSet = true;

                //the rest of lines - graph's content
                } else {
                    int from = Integer.valueOf(line.split("\\s")[0]);
                    int to = Integer.valueOf(line.split("\\s")[1]);


                    Edge edge;
                    nodes[to].setValue(to);
                    nodes[from].setValue(from);
                    if (transpose) {
                        edge = new Edge(nodes[to], nodes[from], nodes[to].edge);
                        nodes[to].edge = edge;
                    } else {
                        edge = new Edge(nodes[from], nodes[to], nodes[from].edge);
                        nodes[from].edge = edge;
                    }
                    
                    //System.out.println(edge);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createEdge(Node from, Node to, Edge nextEdge) {
        Edge e = new Edge(from, to, from.edge);
        from.edge = e;
    }

    public void dfsInit() {
        for (int i = N; i-->0;) {
            nodes[i].data = new DfsPredecessor();
        }
        DfsPredecessor.resetTime();
    }

    public void dfsSearch(Node node, boolean transpose) {
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

        if (!transpose) {
            if (!originStack[node.value]) {
                originStack[node.value] = true;
            }
        }

        if (!Arrays.asList(visited).contains(node) && transpose) {
            visited.add(node);
        }

        nodeData.finishedTime = DfsPredecessor.readTime();
    }

    public void dfs() {
        for (Node node : nodes) {
            if (!originStack[node.value]) {
                dfsInit();
                (node.data).distance = 0;
                dfsSearch(node, false);
            }
        }
    }

    public Graph transposeAndDFS() {
        Graph transposeGraph = new Graph(true);

        for (int i = 0; i < nodes.length; i++) {
            for (int j= 0; j<transposeGraph.nodes.length; j++) {
                if (nodes[i].value == transposeGraph.nodes[i].value) {
                    transposeGraph.dfsInit();
                    (transposeGraph.nodes[i].data).distance = 0;
                    transposeGraph.dfsSearch(transposeGraph.nodes[i], true);
                }
            }
        }

        return transposeGraph;
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
        Graph transposeGraph = normalGraph.transposeAndDFS();

        /*  for (Node node : visited) {
            System.out.println(node);
        }*/


        for (Node node : normalGraph.nodes) {
            System.out.println(node);
        }
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
