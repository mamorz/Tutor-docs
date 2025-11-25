public class PointCompare implements Comparator<Point> {
   // ...
   public int compare(Point p1, Point p2) {
      // ...
   }
}

// Sort according to PointCompare's compare()-method
PointCompare pointCompare = new PointCompare();
Collections.sort(list, pointCompare);