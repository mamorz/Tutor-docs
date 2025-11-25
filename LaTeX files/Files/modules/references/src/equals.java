public class Player {
    private String name;
    private int    age;

    @Override
    public boolean equals(final Object object) {
        if (object != null && getClass().equals(object.getClass())) {
            final Player player = (Player) object;
            return name.equals(player.name) && age == player.age;
        }
        return false;
    }
}