import java.util.Scanner;
public class ScannerExample {
	public static void main(String[] args) {
		// create new scanner (reading from system.in)
		Scanner scanner = new Scanner(System.in);
		// read one line
		String line = scanner.nextLine();
		System.out.println(line);
		// close scanner (already optional)
		scanner.close();
	}
}