import java.util.Date;

public class ExponentialFunctions {

    public static double simpleExponentFunction(double x, int n) {
        if (n == 0) {
            return 1;
        } else {
            return x * simpleExponentFunction(x, n-1);
        }
    }

    public static double advancedExponentialFunction(double x, int n) {
        if (n==0) {
            return 1;
        }

        if (n%2 == 0) {
            return advancedExponentialFunction(x*x, n/2);
        } else {
            return x * advancedExponentialFunction(x*x, (n-1)/2);
        }
    }

    public static double normalExponentialFunction(double x, int n) {
        return Math.pow(x,n);
    }

    public static void testAllFunctionsAndTime(double base, int exponent) {
        Date start;
        int rounds = 0;
        double time;
        Date end;
        double res1;
        double res2;
        double res3;

        System.out.println("Base x = "+base + " Exponent n = "+exponent);
        start = new Date();
        do {
            res1 = simpleExponentFunction(base, exponent);
            end = new Date();
            ++rounds;
        } while (end.getTime()-start.getTime() < 1000);
        time = (double)(end.getTime()-start.getTime()) / rounds;
        System.out.println("----------------------\nMetode 1: oppg. 2.1-1");
        System.out.println("Millisekund pr. runde:" + time);
        System.out.println("Result: "+res1);


        rounds = 0;
        start = new Date();
        do {
            res2 = advancedExponentialFunction(base, exponent);
            end = new Date();
            ++rounds;
        } while (end.getTime()-start.getTime() < 1000);
        time = (double)(end.getTime()-start.getTime()) / rounds;
        System.out.println("----------------------\nMetode 2: oppg. 2.2-3");
        System.out.println("Millisekund pr. runde:" + time);
        System.out.println("Result: "+res2);

        rounds = 0;
        start = new Date();
        do {
            res3 = normalExponentialFunction(base, exponent);
            end = new Date();
            ++rounds;
        } while (end.getTime()-start.getTime() < 1000);
        time = (double)(end.getTime()-start.getTime()) / rounds;

        System.out.println("----------------------\nMetode 3: Math.pow()");
        System.out.println("Millisekund pr. runde:" + time);
        System.out.println("Result: "+res3);
    }



    public static void main(String[] args) {

        double x = 1.001;
        int n = 5000;

        testAllFunctionsAndTime(x,n);

    }
}
