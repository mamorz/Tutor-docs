import java.util.Scanner;

public class BranchesAndLoopExercises {

    static void branchLoopExercise1() {
        Scanner scanner = new Scanner(System.in);

        int i = scanner.nextInt();
        /*
        Aufgabe:
        Schreibe eine Schleife, bei der wiederholt Zahlen aus der Kommandozeile eingelesen werden sollen.
        Darauf hin soll entschieden werden, ob die eingegebene Zahl einstellig, zweistellig oder dreistellig ist.
        Ist die Zahl vierstellig oder mehr, gebe "Die Zahl ist zu groß" aus.
        Eine negative Zahl soll zum Beenden der Schleife führen.
         */

    }

    static void branchLoopExercise2() {
        /*
        Aufgabe:
        Schreibe eine Methode, mit der jede zweistellige Kombination aus Großbuchstaben in der Command-Line ausgegeben werden wird.

        Tipp:
        'A' == (char) 65;
        'Z' == (char) 90;
         */
    }

    static void branchLoopExercise3() {
        Scanner scanner = new Scanner(System.in);

        int i = scanner.nextInt();
        /*
        Aufgabe:
        Schreibe eine Schleife, die wiederholt Zahlen aus der Kommandozeile einliest.
        Die Schleife soll daraufhin den größten Binärwert der eingelesenen Zahl ausgeben.

        Beispiel:
        Eingabe: 17 => Ausgabe: 16
        Eingabe: 14 => Ausgabe: 8

        Der höchste berechenbare Binärwert soll 1024 sein. Wird ein größerer Binärwert gefunden,
        soll "Die Zahl ist zu groß" ausgegeben werden.
         */

    }

    static void branchLoopExercise4() {

        /*
        Aufgabe:
        Was passiert in folgenden Zeilen Code?
         */

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < i; j++) {
                System.out.println(i + j);
            }
        }

    }

    static void branchLoopExercise5() {

        /*
        Aufgabe:
        Berechnet alle Primzahlen, die kleiner sind als 100, und gebt sie in der Kommandozeile aus.
         */

    }

}
