@Override
public int hashCode() {
    // Vertrag zur equals beruecksichtigen und selben Attribute verwenden
    return Objects.hash(color, id, name, etc);
}