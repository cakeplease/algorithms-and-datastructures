package HuffmanAlgorithm;

import java.io.*;

public class HuffmanDecompress {

    public HuffmanDecompress(String filename) throws IOException {
        DataInputStream dataStream = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));

        while (dataStream.available() > 0) {
            System.out.println(dataStream.readByte());
            int b = dataStream.readUnsignedByte();
            //System.out.println(b&1);
            //System.out.println(b);
            for (int i = 0; i<7; i++) {
                /*System.out.println(b&1);
                b = (byte)(b<<1);*/

            }
        }
        //TODO get frequency table from the file
        //use it to build huffman tree and decode rest of the code with it
        //HuffmanTree tree = new HuffmanTree(frequencies);
        //tree.decode();
    }

    public static void main(String[] args) throws FileNotFoundException {
        //TODO pakk ut frekvenstabellen fra filen og encoda huffman kode
        //TODO lag et tre og decode koden ved hjelp av det
        //TODO lagre dataen til en ny fil og returnere den
    }
}
