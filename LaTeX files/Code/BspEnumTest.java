public class EnumTest {
	public static void main(String[] args) {
		Day currentDay;
		currentDay = Day.FRIDAY;
		System.out.println(currentDay);
		System.out.println(currentDay = Day.FRIDAY);
	}
}
enum Day {
	SUNDAY, MONDAY, TUESDAY, WEDNESDAY, 
	THURSDAY, FRIDAY, SATURSDAY
}