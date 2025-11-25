public class Point implements Comparable<Point> {
   // ...
   public int compareTo(Point p) {
      // ...
   }
}

// Sort according to Point's compareTo()-method
Collections.sort(list);