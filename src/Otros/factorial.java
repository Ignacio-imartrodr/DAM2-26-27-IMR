package Otros;

public class factorial {
    public static void main(String[] args) {
        int fact = 65;
        System.out.println(factorialRec(fact));
        System.out.println(factorialLin(fact));
        System.out.println(mult(fact));
    }

    private static long factorialRec(int a){
        if (a <= 0) {
            return -1;
        }
        if (a == 1) {
            return 1L;
        }
        return a * factorialRec(a - 1);
    }

    private static long factorialLin(int a){
        if (a <= 0) {
            return -1;
        }
        long b = 1;
        for (int i = a; i > 1; i--) {
            b = i * b;
        }
        return b;
    }

    private static long mult(int c){
        return factorialLin(c) * (c * 1);
    }
}
