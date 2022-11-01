package HuffmanAlgorithm;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HuffmanCompress {
    //extended ascii table
    int frequencies[] = new int[256];
    PriorityQueue<Node> pq;
    Node node;

    public HuffmanCompress(String filename) throws FileNotFoundException, IOException {
        DataInputStream dataStream = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));
        int pqCapacity = 0;
        //read frequencies and put those in frequencies-array
        while (dataStream.available() > 0) {
            // Print bytes
            int asciiValue = dataStream.readUnsignedByte();

            if (frequencies[asciiValue] == 0) {
                pqCapacity++;
            }

            frequencies[asciiValue] += 1;


        }


        pq = new PriorityQueue<>(pqCapacity,(b1, b2) -> {
            if (b1.frequency == b2.frequency) {
                return 0;
            } else if (b1.frequency > b2.frequency) {
                return 1;
            } else {
                return -1;
            }
        });


        for (int i = 0; i<frequencies.length; i++) {
            node = new Node((char)i, frequencies[i], null);
            if (node.frequency > 0) {
                pq.add(node);
            }
        }

        System.out.println(pq);

        //BinaryTree tree = new BinaryTree();

        //from frequency-array, make nodes and put those in priority queue based on frequency
        //insert nodes into binary tree based on queue


    }

    public static void main(String[] args) throws FileNotFoundException,IOException {
        //HuffmanCompress hc = new HuffmanCompress(args[0]);  use this when later running in terminal
        HuffmanCompress hc = new HuffmanCompress("test.txt");
    }

}

