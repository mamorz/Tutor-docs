public class BranchesExercises {


    static void branchExercise1() {
        int i = 10;

        /*
        Aufgabe:
        Diese Methode soll mit System.out.println() ausgeben, ob i größer oder kleiner 0 ist.
        Vervollständigt die Methode mithilfe einer if-Verzweigung.
         */
    }


    static void branchExercise2() {
        int a = 10;
        int b = 6;

        /*
        Aufgabe:
        Diese Methode soll bestimmen, ob a oder b größer ist und mit System.out.println() den größeren Wert ausgeben.
        Vervollständigt die Methode mithilfe einer if-Verzweigung.
         */
    }


    enum Weekday {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    static void branchExercise3() {
        int i = 3;

        /*
        Aufgabe:
        Die folgende if-else Verzweigung ist nicht optimal.
        Schreibt die Verzweigung als switch-case
         */

        Weekday result = null;
        if (i == 0) {
            result = Weekday.MONDAY;
        } else if (i == 1) {
            result = Weekday.TUESDAY;
        } else if (i == 2) {
            result = Weekday.WEDNESDAY;
        } else if (i == 3) {
            result = Weekday.THURSDAY;
        } else if (i == 4) {
            result = Weekday.FRIDAY;
        } else if (i == 5) {
            result = Weekday.SATURDAY;
        } else if (i == 6) {
            result = Weekday.SUNDAY;
        } else {
            result = null;
        }
    }

    /*
Aufgabe:
Diese Methode soll berechnen, ob das gegebene Jahr ein Schaltjahr ist oder nicht.
Das Ergebnis soll im boolean isLeapYear gespeichert werden.

Es gibt folgende Regeln, die bestimmen, ob ein Jahr ein Schaltjahr ist oder nicht:
    1. Ein Jahr ist ein Schaltjahr, wenn es restlos durch 4 teilbar ist. (Bsp.: 2024)
    2. Falls sich die Jahrzahl durch 100 restlos teilen lässt, ist es kein Schaltjahr. (Bsp: 1900)
    3. Falls auch eine Teilung durch 400 ganzzahlig möglich ist, dann ist es ein Schaltjahr. (Bsp.: 1600)

Vervollständigt diese Methode mit der Verzweigung eurer Wahl.
Tipp: Teilbarkeit prüft man mit % (Beispiel: 16 % 4 == 0)
 */
    static void branchExercise4(int year) {

        boolean isLeapYear = year % 4 == 0
                && !(year % 100 == 0)
                || (year % 400 == 0);
    }

    boolean isTrue() {
        return true;
    }


}
