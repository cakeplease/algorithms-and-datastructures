package UnweightedGraphs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
    int N, K;
    Node[] node;

    public void newGraph() throws IOException {
        Scanner sc = new Scanner(Path.of("oppg6.txt"), StandardCharsets.UTF_8);

        ArrayList<String> names = new ArrayList<>();
        while (sc.hasNextLine()) {
            names.add(sc.nextLine());
        }
    }
}

class Node {
    Edge edge;
    Object d;
}

class Edge {
    Edge next;
    Node to;
    public Edge(Node n, Edge next) {
        to = n;
        next = next;
    }
}
