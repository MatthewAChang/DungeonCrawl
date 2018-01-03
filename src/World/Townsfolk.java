package World;

import java.util.ArrayList;
import java.util.List;

public class Townsfolk extends NPC {

    private List<String> dialogue;

    public Townsfolk(String name)
    {
        super(name);
        dialogue = new ArrayList<>();
    }
}
