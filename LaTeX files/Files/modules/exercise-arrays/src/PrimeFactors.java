public class PrimeFactors {

    public static void main(String... args) {
        if (args.length < 1) {
            return;
        }

        int prime = Integer.parseInt(args[0]);

        System.out.println("Prime Factors for " + prime + ": ");
        printPrimeFactors(prime);
    }

    private static void printPrimeFactors(int number) {
        int prime = number;
        int i = 2;
        while (prime > 1) {
            if (prime % i == 0 && Primes.isPrime(i)) {
                System.out.println(i);
                prime = prime / i;
            } else {
                i++;
            }
        }
    }
}