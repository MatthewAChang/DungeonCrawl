package World;

import Helper.Creation;
import Party.Members.Party;

import java.util.Iterator;
import java.util.List;

public class World implements Iterable<Town>{
    private static World instance;

    private static Party party;

    private String name;

    private List<Town> towns;
    private Town currentTown;

    private World(String name) {
        party = party.getInstance();

        this.name = name;
        this.towns = Creation.createTowns();
        this.currentTown = this.towns.get(0);
    }

    public static World getInstance() {
        if(instance == null) {
            instance = new World(Creation.getWorldName());
        }
        return instance;
    }

    public Party getParty() {
        return party.getInstance();
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
