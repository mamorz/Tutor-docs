List<Integer> list = new ArrayList:>();
// Add some elements//.

// ------ 1. Variante: Anonyme Klasse ------
list.sort(new Comparator<Integer>() {
	@Override
	public int compare(Integer x, Integer y) {
		return y - x;
	}
});

// ------ 2. Variante: Lambda Ausdruck ------
list.sort( (x, y) -> y - x );