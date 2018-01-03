package World;

import java.util.ArrayList;
import java.util.List;

public class Town extends Location {

    private List<Townsfolk> townsfolk;
    private List<Dungeon> dungeons;

    public Town(String name)
    {
        super(name, true);
        townsfolk = new ArrayList<>();
        dungeons = new ArrayList<>();
    }
}
