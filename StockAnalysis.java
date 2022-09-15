import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class StockAnalysis {
    static int[] stockPriceChanges = {-3,1,-9,2,2,-1,2,-1,-5};

    public static HashMap findLargestDifference(int[] stockPriceChanges) {
        HashMap<String, Integer> result = new HashMap<>();
        Integer biggestSum = 0;
        Integer buyDay = 0;
        Integer sellDay = 0;

        for (int i = 0; i<stockPriceChanges.length; i++) {
            Integer sum = stockPriceChanges[i];
            for (int j = i+1; j<stockPriceChanges.length; j++) {
                sum += stockPriceChanges[j];

                if (sum > biggestSum) {
                    biggestSum = sum;
                    buyDay = i;
                    sellDay = j+1;
                }
            }
        }
        result.put("maximal difference", biggestSum);
        result.put("bought day", buyDay);
        result.put("sold day", sellDay);

        return result;
    }
    public static void main(String[] args) {
        int n = 100;
        int[] randomIntegers = new Random().ints(n, -10, 10).toArray();

        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        HashMap res;

        do {
            res = findLargestDifference(randomIntegers);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime()-start.getTime() < 1000);
        tid = (double)(slutt.getTime()-start.getTime()) / runder;

        System.out.println("Millisekund pr. runde:" + tid);
        System.out.println("Result: "+res);

    }
}
