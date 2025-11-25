    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(final Object object) {
        if (object != null && getClass().equals(object.getClass())) {
            final Point point = (Point) object;
            return x == point.getX() && y == point.getY();
        }
        return false;
    }
}