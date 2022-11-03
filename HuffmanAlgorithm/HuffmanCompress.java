package HuffmanAlgorithm;

import java.io.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class HuffmanCompress {
    //extended ascii table
    int frequencies[] = new int[256];
    PriorityQueue<Node> pq;
    Node node;

    public HuffmanCompress(String filename) throws IOException {
        DataInputStream dataStream = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));
        int pqCapacity = 0;
        //read frequencies and put those in frequencies-array
        while (dataStream.available() > 0) {
            int asciiValue = dataStream.readUnsignedByte();
            //System.out.println(asciiValue);
            //pqCapacity++;
            if (frequencies[asciiValue] == 0) {
                pqCapacity++;
            }
            frequencies[asciiValue] += 1;
        }



        //BinaryTree tree = new BinaryTree();
        //from frequency-array, make nodes and put those in priority queue based on frequency
        //insert nodes into binary tree based on queue


    }

    public static void main(String[] args) throws FileNotFoundException,IOException {
        //HuffmanCompress hc = new HuffmanCompress(args[0]);  use this when later running in terminal
        HuffmanCompress hc = new HuffmanCompress("test.txt");
    }

}

