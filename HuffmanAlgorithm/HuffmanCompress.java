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

        saveFrequencyTable(frequencies);

        HuffmanTree tree = new HuffmanTree(frequencies);

        //TODO generate code based on huffman tree and save it in a file with the frequency table

        tree.encode(new DataInputStream(new BufferedInputStream(new FileInputStream(filename))));

        //DataOutputStream newFile = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("encodedHuffman.txt")));
        //newFile.writeBytes(String.valueOf(frequencies));


    }

    public void saveFrequencyTable(int[] frequencyTable) throws FileNotFoundException {
        //save frequency table in output file
        DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("encodedHuffman.txt")));

        for (int i = 0; i< frequencyTable.length; i++) {
            //output.write();
        }

    }

    public static void main(String[] args) throws IOException {
        //HuffmanCompress hc = new HuffmanCompress(args[0]);  use this when later running in terminal
        HuffmanCompress hc = new HuffmanCompress("navn.txt");
    }

}

