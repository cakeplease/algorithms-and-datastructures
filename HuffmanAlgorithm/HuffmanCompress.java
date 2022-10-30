package HuffmanAlgorithm;

import java.io.*;
import java.util.PriorityQueue;

public class HuffmanCompress {
    //extended ascii table
    int frequencies[] = new int[256];
    PriorityQueue pq = new PriorityQueue<>();
    byte b[] = new byte[6];
    public HuffmanCompress(String filename) throws FileNotFoundException, IOException {
        DataInputStream file = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));

        //read frequencies and put those in frequencies-array
        file.readFully(b, 0,5);
        System.out.println(b);

        //BinaryTree tree = new BinaryTree();

        //from frequency-array, make nodes and put those in priority queue based on frequency
        //insert nodes into binary tree based on queue
        //

    }

    public static void main(String[] args) throws FileNotFoundException,IOException {
        //HuffmanCompress hc = new HuffmanCompress(args[0]);  use this when later running in terminal
        HuffmanCompress hc = new HuffmanCompress("text-forelesning.txt");
    }

}

