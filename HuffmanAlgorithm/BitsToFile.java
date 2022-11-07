package HuffmanAlgorithm;

import java.io.*;
import java.util.ArrayList;

//Det kan være lurt å lage en
//egen klasse for å sende bitstrenger til fil.
public class BitsToFile {

    ArrayList<Byte> byteArray;
    //byte[] byteArray;
    ArrayList<BitString> bitStrings;

    ArrayList<String> sequenceArray;

    byte temp = 0;
    int bitCounter = 0;

    public BitsToFile() {
        bitStrings = new ArrayList<>();
    }

    public void addBitString(BitString bitString) {
        bitStrings.add(bitString);
        //System.out.println(bitString);
    }

    public int maskValue(int length) {
        String maskValue = "";

        for (int i=0;i<length; i++) {
            maskValue +="1";
        }

        return Integer.parseInt(maskValue);
    }

    public void addSequenceArray(ArrayList<String> seq) {
        sequenceArray = seq;
    }

    public void flush() {
        temp = 0;
        bitCounter = 0;
    }

    public ArrayList<Byte> readBytes() throws IOException {
       /* int bitSum = 0;
        for (int i = 0; i<bitStrings.size(); i++) {
            bitSum += bitStrings.get(i).length;
        }
        //System.out.println(bitSum);
        int numberOfBytesNeeded = bitSum/8;
        if (bitSum%8 != 0) {
            numberOfBytesNeeded++;
        }
        byteArray = new ArrayList<>(numberOfBytesNeeded);*/

        int numberOfBytesNeeded = (sequenceArray.size()/8) + 1;
        byteArray = new ArrayList<>(numberOfBytesNeeded);
        int temp = 0b00000000;

        for (String seq : sequenceArray) {
            temp = temp << 1;

            temp += Integer.parseInt(seq);

            bitCounter++;

            if (bitCounter == 8) {
                byteArray.add((byte)temp);
                flush();
            }
        }

        System.out.println(write(byteArray, "test111.txt"));

       /* for (byte b : byteArray) {
            for (int i = index; i < (index+8); i++) {
                seq += sequenceArray.get(i);
            }

            index += 8;
        }*/

        //System.out.println(byteArray);

       /* long l;
        for (BitString bitString : bitStrings) {
            if (bitCounter == 0) {
                if (bitString.length == 8) {
                    temp = (byte)(bitString.sequence);
                    byteArray.add(temp);
                    flush();
                } else if (bitString.length < 8) {
                    temp = (byte)(bitString.sequence);
                    bitCounter = bitString.length;
                    // noe slik? : 00001001; bitCounter = 4, må venstreskifte neste bitString med 4 plasser for å ikke overskrive det vi allerede har lagt inn

                //bitString.length > 8
                } else {
                    int byteCount = bitString.length/8;
                    temp = (byte)(bitString.sequence & maskValue(8));
                    byteArray.add(temp);
                    flush();
                    for (int i=0; i<byteCount-1; i++) {
                        bitString.sequence = bitString.sequence << 8;
                        temp = (byte)(bitString.sequence & maskValue(8));
                        byteArray.add(temp);
                        flush();
                    }
                    int remainingBits = byteCount&8;
                    if (remainingBits !=0) {
                        temp = (byte)(bitString.sequence & maskValue(remainingBits));
                        bitCounter = remainingBits;
                    }
                }
            } else {
                //temp is not empty
                if (bitString.length == 8) {
                    int freeSpace = 8 - bitCounter;
                    temp |= (byte)(bitString.sequence & maskValue(freeSpace));
                    byteArray.add(temp);
                    temp = (byte)(bitString.sequence << freeSpace);
                    bitCounter = freeSpace;

                } else if (bitString.length < 8) {
                    int freeSpace = 8 - bitCounter;
                    if (bitString.length == freeSpace) {
                        //litt usikker på om de ikke vil overlappe
                        temp |= (byte)(bitString.sequence);
                        byteArray.add(temp);
                        flush();
                    } else if (bitString.length > freeSpace) {
                        temp |= (byte)(bitString.sequence & maskValue(freeSpace));
                        byteArray.add(temp);
                        temp = (byte)(bitString.sequence << freeSpace);
                    //bitString.length < freeSpace
                    } else {
                        temp |= (byte)(bitString.sequence & maskValue(bitString.length));
                        flush();
                    }
                //bitString.length > 8
                } else {
                    int byteCount = bitString.length/8;
                    temp |= (byte)(bitString.sequence & maskValue(8));
                    flush();
                    for (int i=0; i<byteCount -1; i++) {
                        temp = (byte)(bitString.sequence << 8);
                        flush();
                    }

                    if ((byteCount&8) !=0) {
                        temp = (byte)(bitString.sequence & maskValue(byteCount&8));
                    }
                }
            }
        }*/


        return byteArray;
    }

    public int write(ArrayList<Byte> bytes, String fileName) throws IOException {
        DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
        for (byte b : bytes) {
            output.write(b);
            output.flush();
        }

        return output.size();
    }
}
