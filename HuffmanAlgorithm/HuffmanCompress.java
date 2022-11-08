package HuffmanAlgorithm;

import java.io.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class HuffmanCompress {
    int frequencies[] = new int[256];

    public HuffmanCompress(String fileIn, String fileOut) throws IOException {
        DataInputStream dataStream = new DataInputStream(new BufferedInputStream(new FileInputStream(fileIn)));
        //read frequencies and put those in frequencies-array
        while (dataStream.available() > 0) {
            int asciiValue = dataStream.readUnsignedByte();
            frequencies[asciiValue] += 1;
        }
        dataStream.close();

        HuffmanTree tree = new HuffmanTree(frequencies);
        tree.encode(fileIn, fileOut);
    }


}

