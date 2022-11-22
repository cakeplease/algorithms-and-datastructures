package Dijkstra_Alt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Dijkstra_AStar {
    String nodesFile = "noderNorden.txt";
    String edgesFile = "kanterNorden.txt";
    String interestPointsFile = "interessepktNorden.txt";
    int nodeCount, edgeCount;
    PriorityQueue<Node> pq;
    Node[] nodes;
    HashMap<String, Integer> places = new HashMap();
    static boolean menu = true;


    public Dijkstra_AStar() throws IOException {
        getNodes(nodesFile);
        getEdges(edgesFile, this.nodes);
        getInterestPoints(interestPointsFile);
    }

    public static void main(String[] args) throws IOException {
        Dijkstra_AStar graph = new Dijkstra_AStar();
        menu = true;
        while (menu) {
            System.out.print("-------------------------------\n\n");
            graph.menu();
            graph.reset();
        }

    }


    public void menu(){
        boolean poi=false;
        boolean dijkstra = false;
        boolean aStar =false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose");
        System.out.println("1. Dijkstra");
        System.out.println("2. A*");
        System.out.println("3. Both");
        System.out.println("4. Point of interest");
        System.out.println("5. Exit menu");
        System.out.println("Anything else: Quit ");
        int choice = Integer.parseInt(sc.nextLine());
        if(choice ==1){
            dijkstra = true;
        } else if(choice==2){
            aStar = true;
        } else if (choice==3) {
            aStar = true;
            dijkstra = true;
        } else if (choice==4) {
            poi = true;
        } else if (choice==5) {
            menu = false;
            return;
        } else{
            System.exit(0);
        }

        if(poi){
            System.out.println("Type location and Point-of-interest code, and amount");
            ArrayList<Node> poiNodes = new ArrayList<>();

            Node start = nodes[this.places.get('"' + sc.nextLine() + '"')];
            int poiCode = Integer.parseInt(sc.nextLine());
            int amount = Integer.parseInt(sc.nextLine());

            run_poi_dijkstra(poiNodes, start, poiCode, amount);
            reset();



        }else {
            System.out.println("Type start and end place");
            int start = this.places.get('"' + sc.nextLine() + '"');
            int end = this.places.get('"' + sc.nextLine() + '"');
            if (dijkstra) {
                run_dijkstra(this, start, end);
                reset();
            }
            if(aStar){
                run_astar(this, start, end);
                reset();
            }

        }
    }



    public Node[] getNodes(String fileName) throws IOException {
        BufferedReader br = new BufferedReader((new FileReader(fileName)));
        StringTokenizer st = new StringTokenizer(br.readLine());
        this.nodeCount = Integer.parseInt(st.nextToken());
        this.nodes = new Node[nodeCount];
        for (int i = 0; i < nodeCount; ++i) {
            st = new StringTokenizer(br.readLine());
            int index = Integer.parseInt(st.nextToken());
            double lat = Double.parseDouble(st.nextToken())*(Math.PI/180);
            double lon = Double.parseDouble(st.nextToken())*(Math.PI/180);
            nodes[index] = new Node(index, lat, lon);
            nodes[index].cos_width = Math.cos(lat);
        }

        return nodes;
    }

    public void getEdges(String fileName, Node[] nodes) throws IOException {
        BufferedReader br = new BufferedReader((new FileReader(fileName)));
        StringTokenizer st = new StringTokenizer(br.readLine());
        this.edgeCount = Integer.parseInt(st.nextToken());
        for (int i = 0; i < edgeCount; ++i) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            int length = Integer.parseInt(st.nextToken());
            int speedLimit = Integer.parseInt(st.nextToken());
            Edge e = new Edge(nodes[to], nodes[from].edge, weight, speedLimit, length);
            nodes[from].edge = e;
        }
    }

    public void getInterestPoints(String fileName) throws IOException {
        BufferedReader br = new BufferedReader((new FileReader(fileName)));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        for (int i = 0; i < m; ++i) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int type = Integer.parseInt(st.nextToken());
            String name = st.nextToken();
            while (st.hasMoreTokens()) {
                name += " " + st.nextToken();
            }

            this.nodes[n].type = type;
            this.nodes[n].name = name;
            this.places.put(name, n);
        }
    }


    public double distance(Node n1, Node n2) {
        double sin_bredde = Math.sin((((n1.latitude * Math.PI) / 180)-((n2.latitude* Math.PI) / 180))/2.0);
        double sin_lengde = Math.sin((((n1.longitude * Math.PI) /180)-((n2.longitude * Math.PI) / 180))/2.0);
        return (41701090.90909090909090909091*Math.asin(Math.sqrt(
                sin_bredde*sin_bredde+Math.cos(n1.latitude)*Math.cos(n2.latitude)*sin_lengde*sin_lengde)));
    }

    public ArrayList<Node> dijkstra(Node startNode, int poiCode, int amount) {
        ArrayList<Node> list = new ArrayList<>();
        startNode.distance = 0;
        pq = new PriorityQueue<>(nodeCount, Comparator.comparingDouble(a -> a.distance));
        pq.add(startNode);
        int count = 0;

        while (!pq.isEmpty() && count != amount) {
            Node priorityNode = pq.poll();
            if(checkNodeType(priorityNode.type, poiCode)){
                list.add(priorityNode);
                count++;
            }
            for(Edge e = priorityNode.edge; e != null; e = e.next) {
                if (e.to.distance > priorityNode.distance + e.weight) {
                    e.to.distance = priorityNode.distance + e.weight;
                    e.to.pred = priorityNode;
                    pq.add(e.to);
                }
            }
        }

        return list;
    }

    public boolean checkNodeType(int type, int poiCode){
        if((type & 1) ==1 && poiCode == 1 ){
            return true;
        }
        if((type & 2) ==2 && poiCode == 2){
            return true;
        }
        if((type & 4) ==4 && poiCode == 4){
            return true;
        }
        if ((type & 8) == 8 && poiCode == 8){
            return true;
        }
        if((type & 16) ==16 && poiCode == 16){
            return true;
        }
        if((type & 32) == 32 && poiCode == 32){
            return true;
        }
        return false;
    }
    public int dijkstra(Node startNode, Node endNode) {
        startNode.distance = 0;
        pq = new PriorityQueue<>(nodeCount, Comparator.comparingDouble(a -> a.distance));
        pq.add(startNode);
        int count = 0;
        endNode.end = true;

        while (!pq.isEmpty()) {
            Node priorityNode = pq.poll();
            count++;
            if (priorityNode.end) {
                return count;
            }

            for(Edge e = priorityNode.edge; e != null; e = e.next) {
                if (e.to.distance > priorityNode.distance + e.weight) {
                    e.to.distance = priorityNode.distance + e.weight;
                    e.to.pred = priorityNode;
                    pq.add(e.to);
                }
            }
        }

        return -1;
    }

    public int AStar(Node startNode, Node endNode) {
        startNode.distance = 0;
        startNode.distToEnd = distance(startNode, endNode);
        startNode.fullDistance = startNode.distToEnd;
        endNode.end = true;
        pq = new PriorityQueue<>(nodeCount, Comparator.comparingDouble(a -> a.fullDistance));
        pq.add(startNode);
        int count = 0;

        while (!pq.isEmpty()) {
            Node priorityNode = pq.poll();
            count++;
            if (priorityNode.end) {
                return count;
            }

            for(Edge e = priorityNode.edge; e != null; e = e.next) {
                if (e.to.distance > priorityNode.distance + e.weight) {
                    if (e.to.distToEnd == -1) {
                        e.to.distToEnd = distance(e.to, endNode);
                    }
                    e.to.distance = priorityNode.distance + e.weight;
                    e.to.pred = priorityNode;
                    e.to.fullDistance = e.to.distance + e.to.distToEnd;
                    pq.remove(e.to);
                    pq.add(e.to);
                }
            }
        }
        return -1;
    }


    public static String timeFormat(int time) {
        int hours = time/(60*60*100);
        time = time-(hours*60*60*100);
        int minutes = time/(60*100);
        time = time-(minutes*60*100);
        int seconds = time/(100);
        String timeString = hours + " hours, " + minutes + " minutes and  " + seconds + " seconds";
        return  timeString;
    }

    public void run_dijkstra(Dijkstra_AStar g, int start, int end) {
        long tstart = System.nanoTime();
        int m = g.dijkstra(g.nodes[start], g.nodes[end]);
        long tend = System.nanoTime();

        long total = tend-tstart;
        System.out.println("Dijkstra time: " + (double)total/1000000000 +"s");
        Node n = g.nodes[end];
        int counter = 0;
        try {
            FileWriter os = new FileWriter("dijkstra.csv");
            System.out.println("Total time by driving: "+timeFormat((int)n.distance));

            while(n !=null){
                counter++;
                os.write(n.getCoordinates() +"\n");
                n = n.pred;
            }
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Antall noder checked: "+m);
        System.out.println("Antall noder på veien: "+counter);
    }

    public void run_astar(Dijkstra_AStar g, int start, int end) {
        long tstart = System.nanoTime();
        int m = g.AStar(g.nodes[start], g.nodes[end]);
        long tend = System.nanoTime();
        long total = tend-tstart;
        System.out.println("Astar time: " + (double)total/1000000000 +"s");
        Node n = g.nodes[end];
        int counter = 0;
        try {
            FileWriter os = new FileWriter("astar.csv");
            System.out.println("Total time by driving: "+timeFormat((int)n.distance)+" : ");
            while(n !=null){
                os.write(n.getCoordinates() +"\n");
                n = n.pred;
                counter++;
            }
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Antall noder checked: "+m);
        System.out.println("Antall noder på veien: "+counter);

    }

    public void run_poi_dijkstra(ArrayList<Node> poiNodes, Node start, int poiCode, int amount) {
        poiNodes = dijkstra(start,poiCode,amount);
        poiNodes.stream().forEach(p -> System.out.println(p.toString()));

        try {
            FileWriter os = new FileWriter("poi.csv");

            for (Node node : poiNodes) {
                os.write(node.getCoordinates() +"\n");
            }

            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void reset(){
        for (Node n : nodes) {
            n.pred = null;
            n.end = false;
            n.driveTime = Node.infinity;
            n.distance = Node.infinity;
            n.fullDistance = Node.infinity;
            n.distToEnd = -1;
        }
    }
}

class Node {
    Edge edge;
    int value;
    int driveTime;
    double distance;
    Node pred;
    static int infinity = 1000000000;
    double longitude;
    double latitude;
    double cos_width;
    int type;
    String name;
    double fullDistance;
    double distToEnd;
    boolean end;

    public Node(int val, double longitude, double latitude) {
        value = val;
        driveTime = infinity;
        distance = infinity;
        fullDistance = infinity;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distToEnd = -1;
    }

    public String getCoordinates() {
        return this.longitude*(180/Math.PI)+","+this.latitude*(180/Math.PI);
    }
    @Override
    public String toString() {
        return this.name+" "+this.type;
    }
}

class Edge {
    Edge next;
    Node to;
    int weight;
    int speedLimit;
    int length;

    public Edge(Node t, Edge nxt, int w, int s, int l) {
        to = t;
        next = nxt;
        weight = w;
        this.speedLimit = s;
        this.length = l;
    }

    @Override
    public String toString() {
        return to+ " Weight: "+weight;
    }
}


