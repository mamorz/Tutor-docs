double multiplyScalar(double[] u, double[] v) {
    assert u.length == v.length; // Precondition
    double s= 0;
    for (int i = 0; i < u.length; i++) {
        assert s == sum(i, u, v); // Invariante
        s += u[i] * v[i];
    }
    assert s == sum(u.length - 1, u, v); // Postcondition
    return s;
}