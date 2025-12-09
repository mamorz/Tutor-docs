

public class Player {
    int id;
    String name;

    public Player copy() {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass().equals(obj.getClass())) {
            final Player pla = (Player) obj;
            return this.id == pla.id && this.name.equals(pla.name);
        }
        return false;
    }
}
