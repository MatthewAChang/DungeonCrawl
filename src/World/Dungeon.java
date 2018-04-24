package World;

import Party.Item.Equipment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Dungeon extends Location implements Iterable<Enemy>{

    private List<Enemy> enemies;

    public Dungeon(int id, String name, List<Enemy> enemies)
    {
        super(id, name, false);
        this.enemies = new ArrayList<>();

        if(enemies != null)
            for(Enemy e : enemies)
                this.enemies.add(e);
    }

    public Enemy getEnemy(int get) {
        return enemies.get(get);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public boolean allDead()
    {
        for(Enemy e: this)
            if(e.isAlive())
                return false;
        return true;
    }

    public int getExp()
    {
        int exp = 0;
        for(Enemy e: this)
            exp += e.getExpDrop();
        return exp;
    }

    public int getGold()
    {
        int gold = 0;
        for(Enemy e: this)
            gold += e.getGoldDrop();
        return gold;
    }

    public List<Equipment> getDrops()
    {
        List<Equipment> drops = new ArrayList<>();
        for(Enemy e: this)
        {
            Equipment equipment = e.getRandomDrop();
            if(equipment != null)
                drops.add(equipment);
        }
        return drops;
    }

    @Override
    public Iterator<Enemy> iterator() {
        return enemies.iterator();
    }
}
