public class HanoiTower {


    public static void hanoi(int disks, int from, int to) {

        if (disks == 1) {
            System.out.println("From: "+from + " To: "+to);
            return;
        } else {
            int spare = 6 - from - to;
            hanoi(disks-1, from, spare);
            System.out.println("Oi! From: "+from + " To: "+to);
            hanoi(disks-1, spare, to);
            return;
        }
    }

    public static int factorial(int x) {
        if (x == 0) {
            return 1;
        }
        return x*factorial(x-1);
    }

    public static void main(String[] args) {
        hanoi(3,1,3);
        //factorial(4);
    }
}
