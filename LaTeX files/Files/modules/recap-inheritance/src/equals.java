@Override
public boolean equals(final Object object) {
    // object ist von genau der selben Klasse (keiner Unterklasse!)
    if (object != null && getClass().equals(object.getClass())) {
        final Point2D point = (Point2D) object;

        return x == point.getX() && y == point.getY();
    }

    return false;
}