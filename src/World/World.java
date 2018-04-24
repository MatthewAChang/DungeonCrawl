package World;

import java.util.ArrayList;
import java.util.List;

public class World {
    private static World instance;

    private String name;

    private List<Town> towns;
    protected Town currentTown;

    protected String[][] names = {
            {"Kingsland", "Forest of Trolls", "Cave of Glass", "King's Lair"}

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

    public List<Town> getTowns() {
        return towns;
    }

    public Town getCurrentTown() {
        return currentTown;
    }
}
