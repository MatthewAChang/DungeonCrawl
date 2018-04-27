package World;

import Helper.Creation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Town extends Location implements Iterable<Dungeon>{

    private List<Townsfolk> townsfolk;
    private List<Dungeon> dungeons;
    private Dungeon currentDungeon;



    public Town(int id, String[] names)
    {
        super(id, names[0]);
        townsfolk = new ArrayList<>();
        dungeons = new ArrayList<>();
        addDungeons(names);
        currentDungeon = null;
    }

    private void addDungeons(String[] names) {
        for (int i = 1; i < names.length; i++) {
            dungeons.add(Creation.createDungeon(names[i]));
        }
    }

    public Dungeon getDungeon(int index) {
        return dungeons.get(index);
    }

    public Dungeon getCurrentDungeon() {
        return currentDungeon;
    }

    public void setCurrentDungeon(Dungeon dungeon) {
        this.currentDungeon = dungeon;
    }

    @Override
    public Iterator<Dungeon> iterator() {
        return dungeons.iterator();
    }
}
