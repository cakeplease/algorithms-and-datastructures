package HuffmanAlgorithm;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HuffmanTree {
    Node root;
    PriorityQueue<Node> pq;
    int maxTreeHeight;

    public HuffmanTree(int frequencies[]) {
        root = null;
        int pqCapacity = 0;

        //find size of priority queue needed, based on frequency table
        for (int i = 0; i<frequencies.length; i++) {
            if (frequencies[i] != 0) {
                pqCapacity++;
            }
        }

        //create nodes and put them in priority queue in ascending frequency order
        pq = new PriorityQueue<>(pqCapacity, Comparator.comparingInt(a -> a.frequency));
        for (int i = 0; i<frequencies.length; i++) {
            if (frequencies[i] != 0) {
                pq.add(new Node((char)i, frequencies[i], null));
            }
        }

        //make huffman tree
        while (pq.size() > 2) {

            root = connectNodes(pq.poll(),pq.poll());
            pq.add(root);
        }

        //Connect last two nodes
        root = connectNodes(pq.poll(),pq.poll());

       // System.out.println(root);
        maxTreeHeight = getHeight(root);
      //  System.out.println("Max height of the tree: "+maxTreeHeight);
    }


    //this is wrong because it saves all paths, not only to the char we're searching for
    /*public void find(Node node, char charToFind, String bit) {
        if (node != null && (charToFind!=node.character)) {
            find(node.left, charToFind, "0");
            find(node.right, charToFind, "1");
        }

    }*/


    //we need dfs instead
    //find and save the path from root to a given node
    /*public String dfs(Node startNode, Node target) {

    }*/

    public String DFS(Node n, char c){
        if(n.character == c){
            return "";
        }
        if(n.left == null && n.right == null) return null;
        String x;
        if((x = DFS(n.left, c)) != null && n.left != null) {
            //if(n.b != 2) return n.b+x;
            return "0";
        }
        if((x = DFS(n.right, c)) != null && n.right != null) {
            //if(n.b != 2) return n.b+x;
            return "1";
        }
        return null;
    }

    public void encode(DataInputStream stream) throws IOException {
        while (stream.available() > 0) {
            char character = (char)stream.readUnsignedByte();
            System.out.println("Character: "+character+" Sq: "+DFS(root, character));
            //System.out.println("Char: "+character+" Seq: "+s);
        }

    }

    public void decode() {
        //https://www.geeksforgeeks.org/huffman-coding-greedy-algo-3/
    }

    //use this to determine max size of bit string? for example: height=3 on root means the maximum of bits for a char is 3 -> 000, or 001 etc.
    public int getHeight(Node node) {
        if (node == null) {
            return -1;
        } else {
            int leftHeight = getHeight(node.left);
            int rightHeight = getHeight(node.right);

            return Math.max(leftHeight,rightHeight) + 1;
        }
    }

    public Node connectNodes(Node node1, Node node2) {
        Node root = new Node('\0', node1.frequency+node2.frequency, null);
        root.left = node1;
        root.right = node2;

        node1.parent = root;
        node2.parent = root;
        return root;
    }
}


class Node {
    char character;
    int frequency;
    Node left;
    Node right;
    Node parent;

    public Node(char character, int freq, Node parent) {
        this.character = character;
        this.frequency = freq;
        this.left = null;
        this.right = null;
        this.parent = parent;
    }

    public static int getDepth(Node node) {
        int depthCounter = -1;
        while (node != null) {
            node = node.parent;
            depthCounter++;
        }

        return depthCounter;
    }

    @Override
    public String toString() {
        return "Character: "+character +" Frequency: "+frequency+"\n";
    }
}


