package World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class World implements Iterable<Town>{
    private static World instance;

    private String name;

    private List<Town> towns;
    private Town currentTown;

    protected String[][] names = {
            {"Kingsland", "Bright Garden", "Cave of Glass", "King's Lair"},
            {"The Green Forest", "The Cave of Trolls", "Forgotten Place", "The"}

    };

    private World(String name) {
        this.name = name;
        this.towns = new ArrayList<>();
        addTowns();
    }

    public static World getInstance() {
        if(instance == null) {
            instance = new World("Crystallis");
        }
        return instance;
    }

    private void addTowns() {
        for(int i = 0; i < names.length; i++) {
            towns.add(new Town(i + 1, names[i]));
        }
        currentTown = this.towns.get(0);
    }

    public String getName() {
        return name;
    }

    public Town getCurrentTown() {
        return currentTown;
    }

    public void setCurrentTown(int index) {
        currentTown = towns.get(index);
    }


    @Override
    public Iterator<Town> iterator() {
        return towns.iterator();
    }
}
