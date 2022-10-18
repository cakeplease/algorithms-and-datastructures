package WeightedGraph;

//dijkstra and heap structure
public class WeightedGraph {

    void dijkstra(Node s) {
        
    }


}

class WeightedEdge extends Edge {
    int weight;

    public WeightedEdge(Node n, WeightedEdge nxt, int w) {
        super(n, nxt);
        weight = w;
    }
}

class Edge {
    Edge next;
    Node from;
    Node to;

    public Edge(Node t, Edge nxt) {
        to = t;
        next = nxt;
    }

    @Override
    public String toString() {
        return from+" -> "+to;
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
