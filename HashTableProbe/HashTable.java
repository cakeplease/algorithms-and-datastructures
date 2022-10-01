package HashTableProbe;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Hash table with probe sequence and double hashing as collision handling
 */
public class HashTable {

    public int[] table;

    public int hash1(int k, int m) {
        return k%m;
    }

    public int hash2(int k, int m) {
        return k % (m-1) + 1;
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

    //kollisjonshåndtering med åpen addressering og dobbel hashing
    public void add(int k) {
        int pos = hash1(k, table.length);
        if (table[pos] == 0) {
            table[pos] = k;
            return;
        }
        int h2 = hash2(k, table.length);
        for (;;) {
            pos += h2;
            if (table[pos] == 0) {
                table[pos] = k;
                return;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(Path.of("navn.txt"), StandardCharsets.UTF_8);
        HashTable hashTable = new HashTable();
        while (sc.hasNextLine()) {
            String name = sc.nextLine();
            int asciiValue = 0;
            for (int i=1; i <= name.length(); i++) {
                asciiValue += (int)name.charAt(i) * expFunc(7,i);
            }
            hashTable.add(asciiValue);
        }
    }
}

