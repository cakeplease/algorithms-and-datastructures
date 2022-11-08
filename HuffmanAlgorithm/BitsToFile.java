package HuffmanAlgorithm;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

//Det kan være lurt å lage en
//egen klasse for å sende bitstrenger til fil.
public class BitsToFile {

    ArrayList<Byte> byteArray;
    ArrayList<BitString> bitStrings;
    ArrayList<String> sequenceArray;
    byte temp = 0b00000000;
    int bitCounter = 0;
    int[] frequencies;

    public BitsToFile( ArrayList<String> seqArray, int[] freqTable) {
        sequenceArray = seqArray;
        frequencies = freqTable;
        bitStrings = new ArrayList<>();
    }

    public void flush() {
        temp = 0b00000000;
        bitCounter = 0;
    }

    public ArrayList<Byte> readBytes() {

        int numberOfBytesNeeded = (sequenceArray.size()/8) + 1;
        byteArray = new ArrayList<>(numberOfBytesNeeded);

        for (String seq : sequenceArray) {
            temp = (byte)(temp << 1);

            temp += Integer.parseInt(seq);

            bitCounter++;

            if (bitCounter == 8) {
                byteArray.add(temp);
                flush();
            }
        }

        if (bitCounter != 0) {
            int rotateLeft = 8 - bitCounter;
            temp = (byte)(temp << rotateLeft);
            byteArray.add(temp);
            flush();
        }

        return byteArray;
    }

    public void writeToFile(String fileOut) throws IOException {
        DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileOut)));

        for (int i = 0; i < frequencies.length; i++) {
            output.writeInt(frequencies[i]);
        }

        output.writeInt(sequenceArray.size());
        ArrayList<Byte> encodedHuffman = readBytes();
        output.writeInt(encodedHuffman.size());


        for (byte b : encodedHuffman) {
            output.writeByte(b);
        }

        output.close();
    }

}
