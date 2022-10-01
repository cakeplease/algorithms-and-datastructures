package HashTableLinkedList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Hash table with linked lists as collision handling
 */
public class HashTable {
    public Node[] table;
    int collisions;

    public HashTable() {
        table = new Node[23];
        collisions = 0;
    }

    public int hash(int k) {
        return k%table.length;
    }

    public void add(int k) {
        int hash = hash(k);

        if (table[hash] == null) {
            table[hash] = new Node(k, null);
        } else {
            Node thisNode = table[hash];
            while(thisNode.next != null) {
                thisNode = thisNode.next;
            }
            thisNode.next = new Node(k, null);
        }
    }


    public static double expFunc(double x, int n) {
        if (n==0) {
            return 1;
        }

        if (n%2 == 0) {
            return expFunc(x*x, n/2);
        } else {
            return x * expFunc(x*x, (n-1)/2);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(Path.of("navn.txt"), StandardCharsets.UTF_8);
        HashTable hashTable = new HashTable();

        String name;
        int asciiValue;
        while (sc.hasNextLine()) {

            name = sc.nextLine();
            asciiValue = 0;
            for (int i=0; i < name.length(); i++) {
                //asciiValue += (int)name.charAt(i) * expFunc(3,i);
                asciiValue += (int)name.charAt(i) * i+1;
            }
            hashTable.add(hashTable.hash(asciiValue));
        }

        //HashTable hashTable = new HashTable();
        System.out.println(hashTable);

    }

    @Override
    public String toString() {
        String nodeDatas = "";
        for (Node node : table) {
            if (node == null) {
                nodeDatas += "null\n";
            } else {
                nodeDatas += node.data + "\n";

               /* while (node.next != null) {
                    nodeDatas += node.next.data + "\n";
                    node = node.next;
                }*/
            }
        }
        return nodeDatas;
    }

    class Node {
        Node next;
        int data;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public int getData() {
            return data;
        }

        public Node getNext() {
            return next;
        }

        @Override
        public String toString() {
            return "Data: "+data;
        }
    }

    class LinkedList {
        private int listSize = 0;
        private Node head = null;

        public LinkedList() {
            this.head = null;
            this.listSize = 0;
        }

        public void setBefore(int data) {
            head = new Node(data, head);
            listSize++;
        }

        public void setAfter(int data) {
            if (head != null) {
                Node thisNode = head;
                while(thisNode.next != null) {
                    thisNode = thisNode.next;
                }
                head = new Node(data,null);
            } else {
                head = new Node(data, null);
                listSize++;
            }
        }

        public int getListSize() {
            return listSize;
        }

        public Node getHead() {
            return head;
        }
    }

}
