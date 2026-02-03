@Override
public boolean equals(Object otherObj) {
	if (this == otherObj) { return true; }
	if (otherObj == null) { return false; }
	if (getClass() != obj.getClass()) { return false; } // oder instanceof
	
	MyClass other = (MyClass) otherObj;
	// Vergleiche Eigenschaften
	boolean isEqualA = this.a == other.a; // primitiver Datentyp
	boolean isEqualB = this.b == other.b; // Enum
	boolean isEqualC = this.c.equals(other.c); // Objekte
	boolean isEqualD = this.d.compareTo(other.d) == 0; // String-Buffer
	return isAEqual && isBEqual && isCEqual && ...;
}