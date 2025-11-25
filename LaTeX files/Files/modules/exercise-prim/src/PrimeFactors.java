public class PrimeFactors {

    public static void main(String... args) {
        if (args.length < 1) {
            return;
        }

        int prime = Integer.parseInt(args[0]);

        // prime factors
        System.out.println("Prime Factors for " + prime + ": ");
        printPrimeFactors(prime);
    }

    private static void printPrimeFactors(int number) {
        int i = 2;
        while (number > 1) {
            if (number % i == 0 && Primes.isPrime(i)) {
                System.out.println(i);
                number = number / i;
            } else {
                i++;
            }
        }
    }

}