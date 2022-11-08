package HuffmanAlgorithm;

import java.io.*;
import java.util.ArrayList;

public class HuffmanDecompress {
    public HuffmanDecompress(String encodedFile, String decodedFile) throws IOException {
        DataInputStream dataStream = new DataInputStream(new BufferedInputStream(new FileInputStream(encodedFile)));
        int[] freq = new int[256];
        for (int i=0; i<256;i++) {
            freq[i] = dataStream.readInt();
        }

        int bits = dataStream.readInt();
        int byteCount = dataStream.readInt();
        byte[] byteArray = new byte[byteCount];

        for (int i=0;i<byteCount;i++) {
            byteArray[i] = dataStream.readByte();
        }

        ArrayList<String> encodedSequence = new ArrayList<>();

        for (byte b : byteArray) {
            String s1 = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            for (int i=0; i<8;i++) {
                String bit = String.valueOf(s1.charAt(i));
                encodedSequence.add(bit);
            }
        }

        HuffmanTree tree = new HuffmanTree(freq);
        tree.decode(decodedFile, encodedSequence, bits);
    }
}
