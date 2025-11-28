import java.util.Scanner;

public class Main {
    private static final int KONSTANTE = 5;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        test("ja", "nein");


        BranchesAndLoopExercises etwas = new BranchesAndLoopExercises();
        String str = etwas.toString();
        System.out.println(str);
    }

    public static void test(String... smth) {
        for (String s : smth) {
            System.out.println(s);
        }
    }
}