package HuffmanAlgorithm;

import java.io.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class HuffmanCompress {
    int frequencies[] = new int[256];
    PriorityQueue<Node> pq;
    Node node;

    public HuffmanCompress(String filename) throws IOException {
        DataInputStream dataStream = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));
        //read frequencies and put those in frequencies-array
        while (dataStream.available() > 0) {
            int asciiValue = dataStream.readUnsignedByte();
            frequencies[asciiValue] += 1;
        }

        HuffmanTree tree = new HuffmanTree(frequencies);

        //TODO generate code based on huffman tree and save it in a file with the frequency table

        tree.encode(new DataInputStream(new BufferedInputStream(new FileInputStream(filename))));

        //DataOutputStream newFile = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("encodedHuffman.txt")));
        //newFile.writeBytes(String.valueOf(frequencies));


    }

    public static void main(String[] args) throws FileNotFoundException,IOException {
        //HuffmanCompress hc = new HuffmanCompress(args[0]);  use this when later running in terminal
        HuffmanCompress hc = new HuffmanCompress("test.txt");
    }

}

