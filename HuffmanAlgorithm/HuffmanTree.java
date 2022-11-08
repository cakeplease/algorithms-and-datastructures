package HuffmanAlgorithm;

import java.io.*;
import java.util.ArrayList;
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

        //create nodes and put them in priority queue in ascending frequency order, if the frequency is equal, sort based on alphabetical order
        pq = new PriorityQueue<>(pqCapacity, (b1, b2) -> {
            if (b1.frequency < b2.frequency) {
                return -1;
            } else if (b1.frequency > b2.frequency) {
                return 1;
            } else {
                if (b1.character > b2.character) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        for (int i = 0; i<frequencies.length; i++) {
            if (frequencies[i] != 0) {
                pq.add(new Node((char)i, frequencies[i], null));
            }
        }

        //create huffman tree
        while (pq.size() > 2) {
            root = connectNodes(pq.poll(),pq.poll());
            pq.add(root);
        }

        //connect last two nodes
        root = connectNodes(pq.poll(),pq.poll());

        maxTreeHeight = getHeight(root);
    }

    public static boolean findPath(Node root, ArrayList<String> sequence, char characterToFind, String bit) {
        //could not find path
        if (root==null) return false;
        if (!bit.isEmpty()) sequence.add(bit);
        if (root.character == characterToFind) return true;
        if (findPath(root.left, sequence, characterToFind, "0") || findPath(root.right, sequence, characterToFind, "1")) return true;

        //wrong path, remove last bit from the sequence
        sequence.remove(sequence.size()-1);
        return false;
    }

    public void encode(DataInputStream stream) throws IOException {
        ArrayList<String> sequence;
        ArrayList<String> allSequences = new ArrayList<>();
        BitsToFile bitsToFile = new BitsToFile();

        while (stream.available() > 0) {
            char character = (char)stream.readUnsignedByte();
            sequence = new ArrayList<>();
            if (findPath(root, sequence, character,"")) {
                //System.out.println(sequence);

                //String listString = String.join("", sequence);

                //long seq = Long.parseLong(listString, 2);
                //System.out.println(Long.toBinaryString(seq));
                //System.out.println(seq);

                //BitString bitString = new BitString(seq, listString.length());

                for (String bit : sequence) {
                    allSequences.add(bit);
                }


            }
        }

        //System.out.println(allSequences);
        bitsToFile.addSequenceArray(allSequences);
        bitsToFile.readBytes();
       // System.out.println(bitsToFile.readBytes());


    }

    public void decode() {

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


