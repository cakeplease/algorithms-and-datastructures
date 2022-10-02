package HashTableLinkedList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hash table with linked lists as collision handling
 */
public class HashTable {
    public Node[] table;
    int collisions;
    int elementCount;

    public HashTable(int size) {
        collisions = 0;
        elementCount = size;
        if (!isPrime(size)) {
            while(!isPrime(size)) {
                size++;
            }
        }

        table = new Node[size];
    }

    //we assume that the number is higher than 3
    public boolean isPrime(int n) {
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }

        return true;
    }

    public int hash(String name) {
        int asciiValue = 0;
        for (int i=0; i < name.length(); i++) {
            asciiValue += (int)name.charAt(i) * i+1;
        }

        return asciiValue%table.length;
    }

    public void add(String name) {
        int hash = hash(name);

        if (table[hash] == null) {
            table[hash] = new Node(name, null);
        } else {
            System.out.println("Collision with "+name+" on "+table[hash].data);
            table[hash] = new Node(name, table[hash]);
            collisions++;
        }
    }

    public boolean isInTable(String name) {
        int hash = hash(name);

        if (table[hash] == null) {
            return false;
        } else {
            Node temp = table[hash];
            while (temp != null) {
                if (temp.data.equals(name)) {
                    return true;
                } else {
                    temp = temp.next;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(Path.of("navn.txt"), StandardCharsets.UTF_8);

        ArrayList<String> names = new ArrayList<>();
        while (sc.hasNextLine()) {
            names.add(sc.nextLine());
        }

       HashTable hashTable = new HashTable(names.size());
       System.out.println("\n-----COLLISIONS-----");
       for (String name : names) {
            hashTable.add(name);
        }

        System.out.println("\n-----HASHTABLE-----"+hashTable+"\n");
        System.out.println("-----RESULTS-----");
        System.out.println("Is Katarzyna Szlejter (me) in table?: "+hashTable.isInTable("Katarzyna Szlejter"));
        System.out.println("Collisions: "+hashTable.collisions);
        System.out.println("Load factor: "+ ((double)hashTable.elementCount / (double)hashTable.table.length));
        System.out.println("Collisions per pers: "+((double)hashTable.collisions / (double)hashTable.elementCount));
    }

    @Override
    public String toString() {
        String nodeDatas = "";
        for (Node node : table) {
            if (node == null) {
                nodeDatas += "\n null";
            } else {
                nodeDatas += "\n";
                Node thisNode = node;

                while (thisNode!= null) {
                    nodeDatas += "->"+thisNode.data;
                    thisNode = thisNode.next;
                }

            }
        }
        return nodeDatas;
    }

    class Node {
        Node next;
        String data;

        public Node(String data, Node next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Data: "+data;
        }
    }

}
