import java.util.Scanner;
public class ScannerExample {
	public static void main(String[] args) {
		//create a scanner within a code block
		try(Scanner scanner = new Scanner(System.in)) {
			//read lines
			String line = scanner.nextLine();
		}
		//no need to worry for closing it!
	}
}