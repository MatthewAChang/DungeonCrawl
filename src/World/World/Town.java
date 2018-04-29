package World.World;

import Helper.Creation;
import World.Item.Equipment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Town extends Location implements Iterable<Dungeon>{

    private List<Dungeon> dungeons;
    private Dungeon currentDungeon;

    private List<Equipment> shop;

    public Town(int id, String[] names)
    {
        super(id, names[0]);
        dungeons = new ArrayList<>();
        addDungeons(names);
        currentDungeon = null;
        shop = Creation.addInventory(id);
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

    public List<Equipment> getShop() {
        return shop;
    }

    public Equipment getEquipment(int index) {
        return shop.get(index);
    }

    public void setCurrentDungeon(Dungeon dungeon) {
        this.currentDungeon = dungeon;
    }

    @Override
    public Iterator<Dungeon> iterator() {
        return dungeons.iterator();
    }
}
