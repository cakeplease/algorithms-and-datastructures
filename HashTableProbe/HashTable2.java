package HashTableProbe;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * Hash table with double hashing as collision handling
 */
public class HashTable2 {
    public int[] table;
    public int collisions;

    public HashTable2(int size) {
        collisions = 0;
        if (!isPrime(size)) {
            while(!isPrime(size)) {
                size++;
            }
        }
        table = new int[size];
    }

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

    public int hash1(int k) {
        return k % table.length;
    }

    public int hash2(int k) {
        return k % (table.length-1) + 1;
    }

    public void add(int k) {
        int pos = hash1(k);
        if (table[pos] == 0) {
            table[pos] = k;
            return;
        }
        collisions++;
        int h2 = hash2(k);
        for (;;) {
            pos += h2;
            pos = pos%table.length;
            if (table[pos] == 0) {
                table[pos] = k;
                return;
            }
        }
    }

    public static void main(String[] args) {
        int baseSize = 10000000;
        Random random = new Random();
        int[] numbers = new int[baseSize];

        for (int i=0; i<baseSize; i++) {
            numbers[i] = random.nextInt(Integer.MAX_VALUE-1)+1;
        }

        HashTable2 hashTable2 = new HashTable2(baseSize);
        Date start = new Date();
        for (int number : numbers) {
            hashTable2.add(number);
        }
        Date end = new Date();

        System.out.println("My HashTable: time in milliseconds: "+(double)(end.getTime()-start.getTime()));
        System.out.println("Collisions in my hash table: "+ hashTable2.collisions);
        System.out.println("Load factor in my hashtable: "+(double)numbers.length/(double) hashTable2.table.length);

        HashMap<Integer, Integer> hashMap = new HashMap<>();
        start = new Date();
        for (int number : numbers) {
            hashMap.put(number, number);
        }
        end = new Date();

        System.out.println("Java\'s HashMap: time in milliseconds: "+(double)(end.getTime()-start.getTime()));
    }

    @Override
    public String toString() {
        String nodeDatas = "";
        for (int number : table) {
            nodeDatas += number + "\n";
        }
        return nodeDatas;
    }
}

