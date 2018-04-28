package World.World;

import World.Character.Enemy;
import World.Item.Equipment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Dungeon extends Location implements Iterable<Enemy> {

    private List<Enemy> enemies;

    public Dungeon(int id, String name, List<Enemy> enemies)
    {
        super(id, name);
        this.enemies = new ArrayList<>();

        if(enemies != null)
            this.enemies.addAll(enemies);
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
            List<Equipment> potentialDrops = e.getDrops();
            if(potentialDrops.isEmpty())
                return null;
            Random rand = new Random();
            int num;
            if(e.isBoss())
                drops.add(potentialDrops.get(rand.nextInt(potentialDrops.size())));
            else {
                num = rand.nextInt(potentialDrops.size() * 2);
                if (num < (potentialDrops.size() - 1))
                    drops.add(potentialDrops.get(num));
            }
        }
        return drops;
    }

    @Override
    public Iterator<Enemy> iterator() {
        return enemies.iterator();
    }
}
