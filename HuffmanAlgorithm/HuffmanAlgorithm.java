package HuffmanAlgorithm;

import java.io.IOException;

public class HuffmanAlgorithm {

    public static void main(String[] args) throws IOException {
        String toEncode = "diverse.lyx.lz";
        String encoded = "encodedHuffman.lyx.lz.huff";
        String decoded = "decodedHuffman.lyx.lz";

        HuffmanCompress hc = new HuffmanCompress(toEncode, encoded);
        HuffmanDecompress hd = new HuffmanDecompress(encoded, decoded);
    }
}
