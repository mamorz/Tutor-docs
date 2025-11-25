public static Point[] copy(Point[] a) {
    //                     ^^^^^
    Point[] b = new Point[a.length];
    for (int i = 0; i < a.length; i++) {
        b[i] = a[i];
    }
    return b;
}