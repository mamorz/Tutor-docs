public class Primes {
    public static boolean isPrime(int candidate) {
        if (candidate < 2) { // there is no prime < 2
            return false;
        }
        for (int i = 2; i < candidate; i++) {
            if (candidate % i == 0) {
                return false;
            }
        }
        return true;
    }
}