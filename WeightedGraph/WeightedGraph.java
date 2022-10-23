package WeightedGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WeightedGraph {
    String filename = "vg1.txt";
    int nodeCount, edgeCount;
    PriorityQueue<Node> pq;
    Node[] nodes;

    public WeightedGraph() throws IOException {
        BufferedReader bufferReader = new BufferedReader(new FileReader(filename));
        StringTokenizer st = new StringTokenizer(bufferReader.readLine());
        nodeCount = Integer.parseInt(st.nextToken());
        nodes = new Node[nodeCount];
        for (int i=0; i < nodeCount; i++) {
            nodes[i] = new Node(i);
        }

        edgeCount = Integer.parseInt(st.nextToken());
        for (int i=0; i < edgeCount; i++) {
            st = new StringTokenizer(bufferReader.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            Edge edge = new Edge(nodes[to], nodes[from].edge, weight);
            nodes[from].edge = edge;
        }
    }

    public void dijkstra(Node startNode) {
        startNode.distance = 0;
        pq = new PriorityQueue<>(nodeCount, (a, b) -> ((Node)a).distance - ((Node)b).distance);
        for (int i = 0; i < nodeCount; i++) {
            pq.add(nodes[i]);
        }

        for (int i = nodeCount; i > 1; i--) {
            Node n = pq.poll();
            for(Edge e = n.edge; e != null; e = e.next) {
                if (e.to.distance > n.distance + e.weight) {
                    e.to.distance = n.distance + e.weight;
                    e.to.pred = n;
                    pq.remove(e.to);
                    pq.add(e.to);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        WeightedGraph graph = new WeightedGraph();
        int startIndex = 1;
        graph.dijkstra(graph.nodes[startIndex]);

        System.out.format("%-7s%10s%12s%n", "Node", "Forgjenger", "Distanse");
        for (int i = 0; i < graph.nodeCount; i++) {
            if (graph.nodes[i].distance != Node.infinity) {
                String from = (graph.nodes[i].value == startIndex) ? "Start": String.valueOf(graph.nodes[i].pred.value);
                System.out.format("%-7s%10s%12s%n", graph.nodes[i].value,from,(graph.nodes[i].distance));
            } else {
                System.out.format("%-7s%10s%12s%n",graph.nodes[i].value,"","Nåes ikke");
            }
        }
    }
}

class Node {
    Edge edge;
    int value;
    int distance;
    Node pred;
    static int infinity = 1000000000;

    public Node(int val) {
        value = val;
        distance = infinity;
    }

    @Override
    public String toString() {
        return "Node: "+value;
    }
}

class Edge {
    Edge next;
    Node to;
    int weight;

    public Edge(Node t, Edge nxt, int w) {
        to = t;
        next = nxt;
        weight = w;
    }

    @Override
    public String toString() {
        return to+ " Weight: "+weight;
    }
}


