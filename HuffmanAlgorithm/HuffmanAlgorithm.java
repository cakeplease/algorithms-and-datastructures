package HuffmanAlgorithm;

import java.io.IOException;

public class HuffmanAlgorithm {

    public static void main(String[] args) throws IOException {
        String toEncode = "diverse.txt.lz";
        String encoded = "encodedHuffman.huff";
        String decoded = "decodedHuffman.txt";

        HuffmanCompress hc = new HuffmanCompress(toEncode, encoded);
        HuffmanDecompress hd = new HuffmanDecompress(encoded, decoded);
    }
}
