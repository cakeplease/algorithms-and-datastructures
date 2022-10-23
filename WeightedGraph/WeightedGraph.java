package WeightedGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WeightedGraph {
    String filename = "vg1.txt";
    int nodeCount, edgeCount;
    Node[] nodes;

    public WeightedGraph() throws IOException {
        BufferedReader bufferReader = new BufferedReader(new FileReader(filename));
        StringTokenizer st = new StringTokenizer(bufferReader.readLine());
        nodeCount = Integer.parseInt(st.nextToken());
        nodes = new Node[nodeCount];

        for (int i=0; i<nodeCount; i++) {
            nodes[i] = new Node();
            nodes[i].value = i;
        }

        edgeCount = Integer.parseInt(st.nextToken());

        for (int i=0; i<edgeCount; i++) {
            st = new StringTokenizer(bufferReader.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            System.out.println(nodes[from].edge);
            Edge edge = new Edge(nodes[from], nodes[to], nodes[from].edge, weight);
            nodes[from].edge = edge;
        }

    }

    public static void main(String[] args) throws IOException{
        WeightedGraph graph = new WeightedGraph();
        graph.dijkstra(graph.nodes[0]);
        //System.out.println(graph);
    }

    public void dijkstra(Node startNode) {

    }

    @Override
    public String toString() {
        String nodesContent = "";

        for (Node node : nodes) {
            nodesContent += node+"\n";
        }

        return nodesContent;
    }

}

class Edge {
    Edge next;
    Node from;
    Node to;
    int weight;

    public Edge(Node f, Node t, Edge nxt, int w) {
        from = f;
        to = t;
        next = nxt;
        weight = w;
    }

    @Override
    public String toString() {
        return from+" -> "+to+ " Weight: "+weight;
    }
}


class Node {
    Edge edge;
    int value;
    int distance;
    Node predecessor;
    boolean isVisited;

    public Node() {
        distance = Integer.MAX_VALUE;
        isVisited = false;
        predecessor = null;
    }

    public void setValue(int val) {
        value = val;
    }

    @Override
    public String toString() {
        //return "Node: "+value + " Distance: "+distance + " Predecessor: "+predecessor;
        return "Node: "+value;
    }
}

/*class Heap {
    int length;
    Node nodes[];

    public Heap(Node[] n) {
        nodes = n;
        length = n.length;
        heapSort();
    }

    public void heapSort() {
        createHeap();
        int l = length;
        while(length > 1) {
            Node x = getMin();
            nodes[length] = x;
        }
        length = l;
    }

    public void createHeap() {
        int i = length / 2;
        while(i--> 0) {
            fixHeap(i);
        }
    }

    int over(int i) {
        return (i-1)>>1;
    }

    int left(int i) {
        return (i<<1)+1;
    }

    int right(int i) {
        return (i+1)<<1;
    }

    public static void swap(Node[] t, int i, int j) {
        Node k = t[j];
        t[j] = t[i];
        t[i] = k;
    }

    public Node getMin() {
        Node min = nodes[0];
        nodes[0] = nodes[--length];
        fixHeap(0);
        return min;
    }

    public void fixHeap(int i) {
        int m = left(i);
        if (m < length) {
            int h = m+1;
            if (h < length && nodes[h].distance < nodes[m].distance) {
                m=h;
            }
            if (nodes[m].distance < nodes[i].distance) {
                swap(nodes,i,m);
                fixHeap(m);
            }
        }
    }

    @Override
    public String toString() {
        String nodesContent = "";
        for (Node node : nodes) {
            nodesContent+= node+"\n";
        }
        return "Heap size: "+length+"\n" +
                "Content: \n" + nodesContent;
    }
}*/
