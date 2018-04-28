package Helper;

import World.Character.Class;
import World.Character.*;
import World.Item.Armour;
import World.Item.Equipment;
import World.Item.Restriction;
import World.Item.Weapon;
import World.World.Dungeon;
import World.World.Town;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Creation {

    private static int nextDungeonId = 1;

    private final static String WORLD_NAME = "Crystallis";

    private final static String[][] NAMES = {
            {"Kingsland", "Bright Garden", "Cave of Glass", "King's Lair"},
            {"The Green Forest", "The Cave of Trolls", "Forgotten Place", "The"}

    };

    public static Warrior createNewWarrior(String name) {
        return new Warrior(name, 100, 100, 14, 13, 10, 13);
    }

    public static Rogue createNewRogue(String name) {
        return new Rogue(name, 90, 90, 10, 14, 12, 10);
    }

    public static Mage createNewMage(String name) {
        return new Mage(name, 85, 115, 10, 10, 14, 11);
    }

    public static List<PartyMember> createNewParty(PartyMember player) {
        List<PartyMember> members = addPartyMembers(player);
        addEquipment(members);
        return members;
    }

    private static List<PartyMember> addPartyMembers(PartyMember player) {
        List<PartyMember> members = new ArrayList<>();
        members.add(player);
        if(player.getRole() == Class.WARRIOR.role())
        {
            members.add(createNewRogue("Leliana"));
            members.add(createNewMage("Vanille"));
        }
        else if(player.getRole() == Class.ROUGE.role())
        {
            members.add(createNewWarrior("Marth"));
            members.add(createNewMage("Luxanna"));
        }
        else if(player.getRole() == Class.MAGE.role())
        {
            members.add(createNewWarrior("Ogma"));
            members.add(createNewRogue("Rose"));
        }
        return members;
    }

    private static void addEquipment(List<PartyMember> members) {
        for(PartyMember p : members)
        {
            if(p.getRole() == Class.WARRIOR.role())
            {
                p.equipBody(new Armour("Bronze Armour", Class.WARRIOR.role(), Restriction.BODY.equip(),1, 0, 0, 1, 10));
                p.equipRightArm(new Weapon("Bronze Sword", Class.WARRIOR.role(), Restriction.RIGHT_ARM.equip(),1, 0, 0, 0, 50));
                p.equipLeftArm(new Armour("Bronze Shield", Class.WARRIOR.role(), Restriction.LEFT_ARM.equip(),0, 0, 0, 1, 2));
            }
            else if(p.getRole() == Class.ROUGE.role())
            {
                p.equipBody(new Armour("Worn Cloak", Class.ROUGE.role(), Restriction.BODY.equip(),1, 0, 0, 1, 5));
                p.equipRightArm(new Weapon("Bronze Dagger", Class.ROUGE.role(), Restriction.ARM.equip(),0, 1, 0, 0, 30));
                p.equipLeftArm(new Weapon("Bronze Dagger", Class.ROUGE.role(), Restriction.ARM.equip(),0, 1, 0, 0, 30));
            }
            else if(p.getRole() == Class.MAGE.role())
            {
                p.equipBody(new Armour("Old Tunic", Class.MAGE.role(), Restriction.BODY.equip(),0, 0, 1, 1, 5));
                p.equipRightArm(new Weapon("Old Staff", Class.MAGE.role(), Restriction.RIGHT_ARM.equip(),0, 0, 2, 0, 60));
            }
        }
    }

    public static List<Town> createTowns() {
        List<Town> towns = new ArrayList<>();
        for(int i = 0; i < NAMES.length; i++) {
            towns.add(new Town(i + 1, NAMES[i]));
        }
        return towns;
    }

    public static Dungeon createDungeon(String name)
    {
        Random rand = new Random();
        List<Enemy> enemies = new ArrayList<>();

        List<Equipment> drops = getDrops(rand, nextDungeonId);

        int enemyId = 1;
        int gold;
        switch(nextDungeonId) {
            case 1:
                for(int i = 1; i < rand.nextInt(3) + 2; i++) {
                    gold = rand.nextInt(15) + 1;
                    enemies.add(new Enemy("Goblin" + i, enemyId++, 100, 30, 5, false, gold, 50, drops));
                }
                for(int i = 1; i < rand.nextInt(2) + 2; i++) {
                    gold = rand.nextInt(20) + 5;
                    enemies.add(new Enemy("Troll" + i, enemyId++, 200, 40, 10, false, gold, 100, drops));
                }
                break;
            case 2:
                for(int i = 1; i < rand.nextInt(5) + 2; i++) {
                    gold = rand.nextInt(15) + 1;
                    enemies.add(new Enemy("Goblin" + i, enemyId++, 100, 30, 5, false, gold, 50, drops));
                }
                for(int i = 1; i < rand.nextInt(3) + 2; i++) {
                    gold = rand.nextInt(20) + 5;
                    enemies.add(new Enemy("Troll" + i, enemyId++, 200, 40, 10, false, gold, 100, drops));
                }
                break;
            case 3:
                for(int i = 1; i < rand.nextInt(4) + 2; i++) {
                    gold = rand.nextInt(20) + 5;
                    enemies.add(new Enemy("Troll" + i, enemyId++, 200, 40, 10, false, gold, 100, drops));
                }
                gold = rand.nextInt(100) + 100;
                enemies.add(new Enemy("Troll Leader", enemyId++, 300, 50, 25, true, gold, 40, drops));
                break;
            case 4:
                for(int i = 1; i < rand.nextInt(5) + 2; i++) {
                    gold = rand.nextInt(20) + 5;
                    enemies.add(new Enemy("Troll" + i, enemyId++, 200, 40, 10, false, gold, 100, drops));
                }
                break;
            case 5:
                for(int i = 1; i < rand.nextInt(5) + 2; i++) {
                    gold = rand.nextInt(15) + 1;
                    enemies.add(new Enemy("Goblin" + i, enemyId++, 100, 30, 5, false, gold, 50, drops));
                }
                for(int i = 1; i < rand.nextInt(4) + 2; i++) {
                    gold = rand.nextInt(20) + 5;
                    enemies.add(new Enemy("Troll" + i, enemyId++, 200, 40, 10, false, gold, 100, drops));
                }
                break;
            case 6:
                for(int i = 1; i < rand.nextInt(3) + 2; i++) {
                    gold = rand.nextInt(20) + 5;
                    enemies.add(new Enemy("Troll" + i, enemyId++, 200, 40, 10, false, gold, 100, drops));
                }
                gold = rand.nextInt(100) + 100;
                enemies.add(new Enemy("Troll Leader", enemyId++, 300, 50, 25, true, gold, 40, drops));
                break;
        }
        return new Dungeon(nextDungeonId++, name, enemies);
    }

    private static List<Equipment> getDrops(Random rand, int id)
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

    public static String getWorldName() {
        return WORLD_NAME;
    }
}
