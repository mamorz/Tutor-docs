public class Arrays {

    int sum;

    public static int arraySum(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    public static double average(int[] array) {
        return (double) arraySum(array) / array.length;
    }

    public static double[] sum(double[] vectorA, double[] vectorB) {
        double[] vecSum = new double[vectorA.length];
        if (vectorA.length == vectorB.length) {
            for (int i = 0; i < vectorA.length; i++) {
                vecSum[i] = vectorA[i] + vectorB[i];
            }
        }
        return vecSum;
    }

    public static void main(String... args) {
        int[] array1 = {2, 12, 7, 8, 69};
        double[] vec1 = {1.5, 2, 2.5};
        double[] vec2 = {1.5, 2, 2.5};

        System.out.println("Sum: " + arraySum(array1));
        System.out.println("Avg: " + average(array1));
        double[] sum = sum(vec1, vec2);

    }

}
