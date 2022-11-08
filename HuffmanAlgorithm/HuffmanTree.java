package HuffmanAlgorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class HuffmanTree {
    Node root;
    PriorityQueue<Node> pq;

    int[] frequencies;

    public HuffmanTree(int freqTable[]) {
        frequencies = freqTable;
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


    public void encode(String fileIn, String fileOut) throws IOException {
        DataInputStream stream = new DataInputStream(new BufferedInputStream(new FileInputStream(fileIn)));
        ArrayList<String> sequence;
        ArrayList<String> allSequences = new ArrayList<>();

        while (stream.available() > 0) {
            char character = (char)stream.readUnsignedByte();
            sequence = new ArrayList<>();
            if (findPath(root, sequence, character,"")) {
                for (String bit : sequence) {
                    allSequences.add(bit);
                }
            }
        }
        stream.close();

        BitsToFile bitsToFile = new BitsToFile(allSequences, frequencies);
        bitsToFile.writeToFile(fileOut);
    }

    public void decode(String decodedFilePath, ArrayList<String> encodedSequence, int validBitCount) throws IOException {
        DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(decodedFilePath)));
        //decode function for traversing the tree and decoding huffman code
        ArrayList<Byte> bytesToWrite = new ArrayList<>();
        String text = "";
        Node currentNode = root;
        for (int i=0;i<validBitCount;i++) {
            if (encodedSequence.get(i).equals("0")) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
            if (currentNode.left == null && currentNode.right == null) {
                int test = currentNode.character;
                bytesToWrite.add((byte)currentNode.character);
                //text += currentNode.character;
                currentNode = root;
            }
        }
        //System.out.println(text);
        //output.write(text.getBytes());
        for (byte b : bytesToWrite) {
            output.write(b);
        }
        output.close();

    }

    public Node connectNodes(Node node1, Node node2) {
        Node root = new Node('\u0000', node1.frequency+node2.frequency, null);
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

    @Override
    public String toString() {
        return "Character: "+character +" Frequency: "+frequency+"\n";
    }
}


