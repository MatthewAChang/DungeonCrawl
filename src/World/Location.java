package World;

public abstract class Location {
    private int id;
    private String name;
    private boolean town;

    public Location(int id, String name, boolean town)
    {
        this.id = id;
        this.name = name;
        this.town = town;
    }

    public int getId() {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public boolean isTown() {
        return town;
    }
}
