package HuffmanAlgorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class HuffmanAlgorithm {
    public static void main(String[] args) throws Exception {
        String action ="";
        String filename ="";
        try {
            action = args[0];
            filename = args[1];
        } catch (Exception e) {
            throw new Exception("Use action name as first parameter and filename as second. For example: compress diverse.lyx.lz");
        }


        switch(action) {
            case "compile":
                HuffmanCompress hc = new HuffmanCompress(filename, filename+".huff");
                break;
            case "decompile":
                HuffmanDecompress hd = new HuffmanDecompress(filename, "decoded-"+filename.replace(".huff", ""));
                break;
            default:
                System.out.println("Invalid action. Try using \"compress\" or \"decompress\" instead following by a filename.");
        }
    }
}

class HuffmanCompress {
    int frequencies[] = new int[256];

    public HuffmanCompress(String fileIn, String fileOut) throws IOException {
        DataInputStream dataStream = new DataInputStream(new BufferedInputStream(new FileInputStream(fileIn)));
        while (dataStream.available() > 0) {
            int asciiValue = dataStream.readUnsignedByte();
            frequencies[asciiValue] += 1;
        }

        dataStream.close();

        HuffmanTree tree = new HuffmanTree(frequencies);
        tree.encode(fileIn, fileOut);
    }

}

class HuffmanDecompress {
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

class HuffmanTree {
    Node root;
    PriorityQueue<Node> pq;

    int[] frequencies;

    public HuffmanTree(int freqTable[]) {
        frequencies = freqTable;
        root = null;
        int pqCapacity = 0;

        //find size of priority queue needed, based on frequency table
        for (int i = 0; i<frequencies.length; i++) {
            if (frequencies[i] != 0) {
                pqCapacity++;
            }
        }

        //create nodes and put them in priority queue in ascending frequency order, if the frequency is equal, sort based on alphabetical order
        pq = new PriorityQueue<>(pqCapacity, (b1, b2) -> {
            if (b1.frequency < b2.frequency) {
                return -1;
            } else if (b1.frequency > b2.frequency) {
                return 1;
            } else {
                if (b1.character == null && b2.character == null) {
                    return 0;
                }
                if (b1.character == null) {
                    return -1;
                }
                if (b2.character == null) {
                    return 1;
                }

                if (b1.character > b2.character) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        for (int i = 0; i<frequencies.length; i++) {
            if (frequencies[i] != 0) {
                pq.add(new Node((char)i, frequencies[i], null));
            }
        }

        //create huffman tree
        while (pq.size() > 2) {
            root = connectNodes(pq.poll(),pq.poll());
            pq.add(root);
        }

        //connect last two nodes
        root = connectNodes(pq.poll(),pq.poll());
    }

    public static boolean findPath(Node root, ArrayList<String> sequence, char characterToFind, String bit) {
        //could not find path
        if (root==null) return false;
        if (!bit.isEmpty()) sequence.add(bit);
        if (root.character != null && (root.character == characterToFind)) return true;
        if (findPath(root.left, sequence, characterToFind, "0") || findPath(root.right, sequence, characterToFind, "1")) return true;

        //wrong path, remove last bit from the sequence
        sequence.remove(sequence.size()-1);
        return false;
    }


    public void encode(String fileIn, String fileOut) throws IOException {
        DataInputStream stream = new DataInputStream(new BufferedInputStream(new FileInputStream(fileIn)));
        ArrayList<String> sequence;
        ArrayList<String> allSequences = new ArrayList<>();

        while (stream.available() > 0) {
            char character = (char)stream.readUnsignedByte();
            sequence = new ArrayList<>();
            if (findPath(root, sequence, character,"")) {
                for (String bit : sequence) {
                    allSequences.add(bit);
                }
            }
        }
        stream.close();

        BitsToFile bitsToFile = new BitsToFile(allSequences, frequencies);
        bitsToFile.writeToFile(fileOut);
    }

    public void decode(String decodedFilePath, ArrayList<String> encodedSequence, int validBitCount) throws IOException {
        DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(decodedFilePath)));
        //decode function for traversing the tree and decoding huffman code
        ArrayList<Byte> bytesToWrite = new ArrayList<>();
        Node currentNode = root;
        for (int i=0;i<validBitCount;i++) {
            if (encodedSequence.get(i).equals("0")) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
            if (currentNode.left == null && currentNode.right == null) {
                bytesToWrite.add((byte)(char)currentNode.character);
                currentNode = root;
            }
        }

        for (byte b : bytesToWrite) {
            output.write(b);
        }
        output.close();

    }

    public Node connectNodes(Node node1, Node node2) {
        Node root = new Node(null, node1.frequency+node2.frequency, null);
        root.left = node1;
        root.right = node2;

        node1.parent = root;
        node2.parent = root;
        return root;
    }
}

class Node {
    Character character;
    int frequency;
    Node left;
    Node right;
    Node parent;

    public Node(Character character, int freq, Node parent) {
        this.character = character;
        this.frequency = freq;
        this.left = null;
        this.right = null;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Character: "+character +" Frequency: "+frequency+"\n";
    }
}

class BitsToFile {

    ArrayList<Byte> byteArray;
    ArrayList<String> sequenceArray;
    byte temp = 0b00000000;
    int bitCounter = 0;
    int[] frequencies;

    public BitsToFile( ArrayList<String> seqArray, int[] freqTable) {
        sequenceArray = seqArray;
        frequencies = freqTable;
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


