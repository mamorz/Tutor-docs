@Override
public int hashCode() {
	int result = 0;
	// Ganzzahl
	result = 31 * result + i;
	// Float oder Double
	result = 31 * result + (f != 0.0f ? Float.floatToIntBits(f) : 0);
	// Objekte oder Enums
	result = 31 * result + (name != null ? name.hashCode() : 0);
	
	return result;
}
