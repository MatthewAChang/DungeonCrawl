package World;

import Party.Item.Armour;
import Party.Item.Equipment;
import Party.Item.Restriction;
import Party.Item.Weapon;
import Party.Members.Class;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
            dungeons.add(createDungeon(i * getId(), names[i]));
        }
    }

    private Dungeon createDungeon(int id, String name)
    {
        Random rand = new Random();
        List<Enemy> enemies = new ArrayList<>();

        List<Equipment> drops = getDrops(rand, id);

        int enemyId = 1;
        switch(id) {
            case 1:
                for(int i = 1; i < rand.nextInt(3) + 2; i++) {
                    int gold = rand.nextInt(15) + 1;
                    enemies.add(new Enemy("Goblin" + i, enemyId++, 100, 90, 30, false, gold, 20, drops));
                }
                for(int i = 1; i < rand.nextInt(2) + 2; i++) {
                    int gold = rand.nextInt(20) + 5;
                    enemies.add(new Enemy("Troll" + i, enemyId++, 200, 120, 40, false, gold, 40, drops));
                }
                break;
            case 2:
                for(int i = 1; i < rand.nextInt(5) + 2; i++) {
                    int gold = rand.nextInt(15) + 1;
                    enemies.add(new Enemy("Goblin" + i, enemyId++, 100, 90, 30, false, gold, 20, drops));
                }
                for(int i = 1; i < rand.nextInt(4) + 2; i++) {
                    int gold = rand.nextInt(20) + 5;
                    enemies.add(new Enemy("Troll" + i, enemyId++, 200, 120, 40, false, gold, 40, drops));
                }
                break;
            case 3:
                for(int i = 1; i < rand.nextInt(2) + 2; i++) {
                    int gold = rand.nextInt(20) + 5;
                    enemies.add(new Enemy("Troll" + i, enemyId++, 200, 120, 40, false, gold, 40, drops));
                }
                int gold = rand.nextInt(100) + 100;
                enemies.add(new Enemy("Troll Leader", enemyId++, 300, 150, 50, true, gold, 40, drops));
                break;
        }

        return new Dungeon(id, name, enemies);
    }

    private List<Equipment> getDrops(Random rand, int id)
    {
        List<Equipment> drops = new ArrayList<>();
        switch(id) {
            case 1:
                drops.add(new Weapon("Bronze Dagger", Class.ROUGE.role(), Restriction.ARM.equip(),rand.nextInt(2), rand.nextInt(3) + 1, 0, 0, rand.nextInt(6) + 40));
                drops.add(new Weapon("Bronze Sword", Class.WARRIOR.role(), Restriction.RIGHT_ARM.equip(),rand.nextInt(3) + 1, 0, 0, 0, rand.nextInt(6) + 50));
                drops.add(new Armour("Worn Cowl", Class.MAGE.role(), Restriction.HEAD.equip(),0, 0, rand.nextInt(4) + 1, rand.nextInt(3), rand.nextInt(4) + 20));
                drops.add(new Armour("Bronze Helmet", Class.WARRIOR.role(), Restriction.HEAD.equip(),rand.nextInt(2), 0, 0, rand.nextInt(3), rand.nextInt(4) + 30));
                break;
            case 2:
                drops.add(new Weapon("Bronze Dagger", Class.ROUGE.role(), Restriction.ARM.equip(),rand.nextInt(2), rand.nextInt(3) + 1, 0, 0, rand.nextInt(6) + 40));
                drops.add(new Weapon("Bronze Sword", Class.WARRIOR.role(), Restriction.RIGHT_ARM.equip(),rand.nextInt(3) + 1, 0, 0, 0, rand.nextInt(6) + 50));
                drops.add(new Armour("Worn Cowl", Class.MAGE.role(), Restriction.HEAD.equip(),0, 0, rand.nextInt(4) + 1, rand.nextInt(3), rand.nextInt(4) + 20));
                drops.add(new Armour("Bronze Helmet", Class.WARRIOR.role(), Restriction.HEAD.equip(),rand.nextInt(2), 0, 0, rand.nextInt(3), rand.nextInt(4) + 30));
                break;
            case 3:
                drops.add(new Weapon("Bronze Dagger", Class.ROUGE.role(), Restriction.ARM.equip(),rand.nextInt(2), rand.nextInt(3) + 1, 0, 0, rand.nextInt(6) + 40));
                drops.add(new Weapon("Bronze Sword", Class.WARRIOR.role(), Restriction.RIGHT_ARM.equip(),rand.nextInt(3) + 1, 0, 0, 0, rand.nextInt(6) + 50));
                drops.add(new Armour("Worn Cowl", Class.MAGE.role(), Restriction.HEAD.equip(),0, 0, rand.nextInt(4) + 1, rand.nextInt(3), rand.nextInt(4) + 20));
                drops.add(new Armour("Bronze Helmet", Class.WARRIOR.role(), Restriction.HEAD.equip(),rand.nextInt(2), 0, 0, rand.nextInt(3), rand.nextInt(4) + 30));
                break;
        }

        return drops;
    }

    public List<Dungeon> getDungeons() {
        return dungeons;
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
