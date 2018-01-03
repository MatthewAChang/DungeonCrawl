package World;

public abstract class Location {
    private String name;
    private boolean town;

    public Location(String name, boolean town)
    {
        this.name = name;
        this.town = town;
    }

    public String getName()
    {
        return name;
    }

    public boolean isTown() {
        return town;
    }
}
