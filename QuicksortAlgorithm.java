import java.util.Date;
import java.util.Random;

public class QuicksortAlgorithm {
    public static void quicksortFromTheBook(int[] t, int v, int h) {
        if (h - v > 2) {
            int delepos = divide(t,v,h);
            quicksortFromTheBook(t,v,delepos - 1);
            quicksortFromTheBook(t,delepos +1, h);
        } else {
            median3sort(t,v,h);
        }
    }

    public static void swap(int[] t, int i, int j) {
        int k = t[j];
        t[j] = t[i];
        t[i] = k;
    }

    public static int median3sort(int[] t, int v, int h) {
        int m = (v+h)/2;
        if (t[v] > t[m]) {
            swap(t,v,m);
        }
        if (t[m] > t[h]) {
            swap(t,m,h);
            if (t[v]>t[m]) {
                swap(t,v,m);
            }
        }
        return m;
    }

    public static int divide(int[] t, int v, int h) {
        int iv, ih;
        int m = median3sort(t,v,h);
        int dv = t[m];
        swap(t,m,h-1);
        for (iv = v, ih = h - 1;;) {
            while(t[++iv] < dv);
            while(t[--ih]>dv);
            if (iv >= ih) break;
            swap(t,iv,ih);

        }
        swap(t,iv,h-1);
        return iv;
    }

    static void dualPivotQuickSort(int[] arr, int low, int high) {
        if (low < high) {
            // piv[] stores left pivot and right pivot.
            // piv[0] means left pivot and
            // piv[1] means right pivot
            int[] piv;
            piv = partition(arr, low, high);

            dualPivotQuickSort(arr, low, piv[0] - 1);
            dualPivotQuickSort(arr, piv[0] + 1, piv[1] - 1);
            dualPivotQuickSort(arr, piv[1] + 1, high);
        }
    }

    static int[] partition(int[] arr, int low, int high) {

        int pivot1 = arr[low + (high - low) / 3];
        int pivot2 = arr[high - (high - low) / 3];

        if (pivot1 > pivot2) {
            swap(arr, low + (high - low) / 3, high);
            swap(arr, high - (high - low) / 3, low);
        } else {
            swap(arr, high - (high - low) / 3, high);
            swap(arr, low + (high - low) / 3, low);
        }

        if (arr[low] > arr[high])
            swap(arr, low, high);

        // p is the left pivot, and q
        // is the right pivot.
        int j = low + 1;
        int g = high - 1, k = low + 1,
                p = arr[low], q = arr[high];

        while (k <= g) {

            // If elements are less than the left pivot
            if (arr[k] < p) {
                swap(arr, k, j);
                j++;
            }

            // If elements are greater than or equal
            // to the right pivot
            else if (arr[k] >= q) {
                while (arr[g] > q && k < g)
                    g--;

                swap(arr, k, g);
                g--;

                if (arr[k] < p) {
                    swap(arr, k, j);
                    j++;
                }
            }
            k++;
        }
        j--;
        g++;

        // Bring pivots to their appropriate positions.
        swap(arr, low, j);
        swap(arr, high, g);

        // Returning the indices of the pivots
        // because we cannot return two elements
        // from a function, we do that using an array.
        return new int[] { j, g };
    }

    public static boolean testSortingAlg(int[] arr, int sumBefore, int sumAfter) {
        for (int i=0; i<arr.length; i++) {
            if ((i+1) != arr.length) {
                if (!(arr[i+1] >= arr[i])) {
                    return false;
                }
            }
        }

        if (sumBefore != sumAfter) {
            return false;
        }

        return true;
    }

    public static int[] generateRandomNumbers(int n) {
        int[] randomIntegers = new int[n];

        Random rn = new Random();
        for (int i=0; i<n; i++) {
            randomIntegers[i] = rn.nextInt();
        }

        return randomIntegers;
    }

    public static int[] generateArrayWithDuplicates(int n) {
        int[] randomIntegers = new int[n];

        Random rn = new Random();
        int duplicateValue = rn.nextInt();
        for (int i=0; i<n; i++) {
            if (i%2==0) {
                randomIntegers[i] = duplicateValue;
            } else {
                randomIntegers[i] = rn.nextInt();
            }
        }

        return randomIntegers;
    }

    public static int[] generateSortedList(int n) {
        int[] sortedList = new int[n];

        for (int i=0; i<n; i++) {
            sortedList[i] = i;
        }

        return sortedList;
    }


    public static void main(String[] args) {
        int n = 10000000;

        //uncomment desired array to use it
        int[] array = generateRandomNumbers(n);
        //int[] array = generateArrayWithDuplicates(n);
        //int[] array = generateSortedList(n);

        int sumBefore = 0;
        for(int i = 0; i < array.length; i++) {
            sumBefore += array[i];
        }

        Date start = new Date();
        int rounds = 0;
        double time;
        Date end;

        do {
            //uncomment desired algorithm to use it
            quicksortFromTheBook(array, 0, n-1);
            //dualPivotQuickSort(array, 0, n-1);
            end = new Date();
            ++rounds;
        } while (end.getTime()-start.getTime() < 1000);
        time = (double)(end.getTime()-start.getTime()) / rounds;
        System.out.println("Millisecond per round:" + time);

        int sumAfter = 0;
        for(int i = 0; i < array.length; i++) {
            sumAfter += array[i];
        }


        //Testing if the array is sorted correctly
        if (testSortingAlg(array, sumBefore, sumAfter)) {
            System.out.println("Tests passed");
        } else {
            System.out.println("Tests didn't pass.");
        }


    }
}
