package HuffmanAlgorithm;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HuffmanDecompress {

    public HuffmanDecompress(String filename) throws FileNotFoundException {
        DataInputStream dataStream = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));

        //TODO get frequency table from the file
        //use it to build huffman tree and decode rest of the code with it

    }

    public static void main(String[] args) throws FileNotFoundException {
        //TODO pakk ut frekvenstabellen fra filen og encoda huffman kode
        //TODO lag et tre og decode koden ved hjelp av det

        //TODO lagre dataen til en ny fil og returnere den
    }
}
