public class Main {
    public static void main(String... args) {
        // just some testing
        String out = "";
        Point tmp = null;
        Point p1 = new Point(1, 1);
        Point p3 = new Point(3, 3);
        LinkedPointList list = new LinkedPointList();
        System.out.println("Created new empty list.");
        System.out.println("Size " + list.size() + ": " + list.toString());
        list = new LinkedPointList(p1);
        System.out.println("Created new list containing 1 object");
        System.out.println("Size " + list.size() + ": " + list.toString());
        list.add(new Point(2, 2));
        System.out.println("Added a 2nd point");
        System.out.println("Size " + list.size() + ": " + list.toString());
        list.addFirst(p3);
        System.out.println("Added new 1st point");
        System.out.println("Size " + list.size() + ": " + list.toString());
        list.addLast(new Point(4, 4));
        System.out.println("Added new last point");
        System.out.println("Size " + list.size() + ": " + list.toString());
        System.out.println("List contains 3rd point added: " + list.contains(p3));
        System.out.println("Index of object equalling a point (2/2): " + list.indexOfFirstSimilar(new Point(2,2)));
        System.out.println("Index of object equalling a point (9/9): " + list.indexOfFirstSimilar(new Point(9,9)));
        System.out.println("First: " + list.getFirst().toString() + " last: " + list.getLast().toString());
        list.removeFirst();
        System.out.println("Removed first element");
        System.out.println("Size " + list.size() + ": " + list.toString());
        list.removeLast();
        System.out.println("Removed last element");
        System.out.println("Size " + list.size() + ": " + list.toString());
        list.remove(p1);
        System.out.println("Removed 1st point");
        System.out.println("Size " + list.size() + ": " + list.toString());
        tmp = list.get(2);
        if (tmp == null) {
            out = "not found";
        } else {
            out = tmp.toString();
        }
        System.out.println("Get index 2-element: " + out);
        tmp = list.get(1);
        if (tmp == null) {
            out = "not found";
        } else {
            out = tmp.toString();
        }
        System.out.println("Get index 1-element: " + out);
        tmp = list.get(0);
        if (tmp == null) {
            out = "not found";
        } else {
            out = tmp.toString();
        }
        System.out.println("Get index 0-element: " + out);
        list.removeLast();
        System.out.println("Removed last point");
        System.out.println("Size " + list.size() + ": " + list.toString());
        list.add(p1);
        System.out.println("Added point");
        System.out.println("Size " + list.size() + ": " + list.toString());
        list.addFirst(p3);
        System.out.println("Added point as first element");
        System.out.println("Size " + list.size() + ": " + list.toString());
        list.add(p3);
        System.out.println("Tried to add same point again");
        System.out.println("Size " + list.size() + ": " + list.toString());
        list.removeFirst();
        System.out.println("Removed first point");
        System.out.println("Size " + list.size() + ": " + list.toString());
    }
}