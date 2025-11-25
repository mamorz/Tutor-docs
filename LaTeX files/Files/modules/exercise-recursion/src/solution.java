int fibonacci(int n) {
  if (n > 1) {
    return fibonacci(n - 1) + fibonacci(n-2);
  } else if (n == 1) {
    return 1;
  } else {
    return 0;
  }
}
	
int ackermann(int m, int n) {
  if (m < 0 || n < 0) {
    return 0;
  }
  if (m == 0) {
    return n + 1;
  } else if (n == 0) {
    return ackermann(m - 1, 1);
  } else {
    return ackermann(m - 1, ackermann(m, n - 1));
  }
}