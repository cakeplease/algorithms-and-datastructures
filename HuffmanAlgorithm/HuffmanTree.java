package HuffmanAlgorithm;

import java.util.Comparator;
import java.util.PriorityQueue;

public class HuffmanTre {
    Node root;
    PriorityQueue<Node> pq;
    Node node;

    public HuffmanTre(int frequencies[]) {
        root = null;
        int pqCapacity = 0;
        for (int i = 0; i<frequencies.length; i++) {
            if (frequencies[i] != 0) {
                pqCapacity++;
            }
        }

        pq = new PriorityQueue<>(pqCapacity,Comparator.comparingInt(a -> ((Node) a).frequency));

        for (int i = 0; i<frequencies.length; i++) {
            if (frequencies[i] != 0) {
                node = new Node((char)i, frequencies[i], null);
                pq.add(node);
            }
        }

        for (int i = 0; i<pqCapacity; i++) {

            System.out.println(pq.poll());
        }


    }


    public int getHeight(Node node) {
        if (node == null)
            return -1;
        else {
            int leftHeight = getHeight(node.left);
            int rightHeight = getHeight(node.right);

            return Math.max(leftHeight,rightHeight) + 1;
        }
    }

    //TODO change insert method accordingly to exercise
    public void insert(Node root, char character, int frequency) {

        //while loop så lenge lista er på over 2
        //get 2 nodes with lowest freq and make an extra root node with \0
        // connect root nodes to a tree

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


