package Helper;

import World.Character.Class;
import World.Character.*;
import World.Item.*;
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
        List<PartyMember> members = addBasePartyMembers(player);
        addBaseEquipment(members);
        return members;
    }

    private static List<PartyMember> addBasePartyMembers(PartyMember player) {
        List<PartyMember> members = new ArrayList<>();
        members.add(player);
        if(player.getRole() == Class.WARRIOR.role())
        {
            members.add(createNewRogue("Leliana"));
            members.add(createNewMage("Vanille"));
        }
        else if(player.getRole() == Class.ROGUE.role())
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

    private static void addBaseEquipment(List<PartyMember> members) {
        for(PartyMember p : members)
        {
            if(p.getRole() == Class.WARRIOR.role())
            {
                p.equipBody(new Armour("Bronze Armour", Class.WARRIOR.role(), Restriction.BODY.equip(),1, 0, 0, 1, 10));
                p.equipRightArm(new Weapon("Bronze Sword", Class.WARRIOR.role(), Restriction.RIGHT_ARM.equip(),1, 0, 0, 0, 50));
                p.equipLeftArm(new Armour("Bronze Shield", Class.WARRIOR.role(), Restriction.LEFT_ARM.equip(),0, 0, 0, 1, 2));
            }
            else if(p.getRole() == Class.ROGUE.role())
            {
                p.equipBody(new Armour("Worn Cloak", Class.ROGUE.role(), Restriction.BODY.equip(),1, 0, 0, 1, 5));
                p.equipRightArm(new Weapon("Bronze Dagger", Class.ROGUE.role(), Restriction.ARM.equip(),0, 1, 0, 0, 30));
                p.equipLeftArm(new Weapon("Bronze Dagger", Class.ROGUE.role(), Restriction.ARM.equip(),0, 1, 0, 0, 30));
            }
            else if(p.getRole() == Class.MAGE.role())
            {
                p.equipBody(new Armour("Old Tunic", Class.MAGE.role(), Restriction.BODY.equip(),0, 0, 1, 1, 5));
                p.equipRightArm(new Weapon("Old Staff", Class.MAGE.role(), Restriction.RIGHT_ARM.equip(),0, 0, 2, 0, 60));
            }
        }
    }

    public static void addSpell(PartyMember p) {
        if(p.getRole() == Class.WARRIOR.role())
            warriorSpell((Warrior)p);
        else if(p.getRole() == Class.ROGUE.role())
            rogueSpell((Rogue)p);
        else if(p.getRole() == Class.MAGE.role())
            mageSpell((Mage)p);

    }

    private static void warriorSpell(Warrior warrior) {
        switch (warrior.getLevel()) {
            case 0:
                warrior.addSpell(new Spell(1,"Shield Bash", (int)(warrior.getDamage() * 1.5), 30, false,false, true, false));
                break;
            case 3:
                warrior.addSpell(new Spell(2,"Hunker Down", warrior.getArmour() * 2, 40, true,false, false, false));
                break;
        }
    }

    private static void rogueSpell(Rogue rogue) {
        switch (rogue.getLevel()) {
            case 0:
                rogue.addSpell(new Spell(4,"Cheap Shot", (rogue.getDamage() * 2), 50, false,false, true, false));
                break;
            case 3:
                rogue.addSpell(new Spell(5,"Throwing Knives", (2 * rogue.getDamage()) / 3, 40,false, false, true, true));
                break;
        }
    }

    private static void mageSpell(Mage mage) {
        switch (mage.getLevel()) {
            case 0:
                mage.addSpell(new Spell(7,"Heal", mage.getDamage() / 2, 50, true,true, false, false));
                break;
            case 3:
                mage.addSpell(new Spell(8,"Fireball", mage.getDamage() / 2, 40,false, false, true, true));
                break;
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

        List<Equipment> drops = createDrops(rand, nextDungeonId);

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

    private static List<Equipment> createDrops(Random rand, int id)
    {
        List<Equipment> drops = new ArrayList<>();
        switch(id) {
            case 1:
                drops.add(new Weapon("Bronze Dagger", Class.ROGUE.role(), Restriction.ARM.equip(),rand.nextInt(2), rand.nextInt(3) + 1, 0, 0, rand.nextInt(6) + 40));
                drops.add(new Weapon("Bronze Sword", Class.WARRIOR.role(), Restriction.RIGHT_ARM.equip(),rand.nextInt(3) + 1, 0, 0, 0, rand.nextInt(6) + 50));
                drops.add(new Armour("Worn Cowl", Class.MAGE.role(), Restriction.HEAD.equip(),0, 0, rand.nextInt(4) + 1, rand.nextInt(3), rand.nextInt(4) + 20));
                drops.add(new Armour("Bronze Helmet", Class.WARRIOR.role(), Restriction.HEAD.equip(),rand.nextInt(2), 0, 0, rand.nextInt(3), rand.nextInt(4) + 30));
                break;
            case 2:
                drops.add(new Weapon("Bronze Dagger", Class.ROGUE.role(), Restriction.ARM.equip(),rand.nextInt(2), rand.nextInt(3) + 1, 0, 0, rand.nextInt(6) + 40));
                drops.add(new Weapon("Bronze Sword", Class.WARRIOR.role(), Restriction.RIGHT_ARM.equip(),rand.nextInt(3) + 1, 0, 0, 0, rand.nextInt(6) + 50));
                drops.add(new Armour("Worn Cowl", Class.MAGE.role(), Restriction.HEAD.equip(),0, 0, rand.nextInt(4) + 1, rand.nextInt(3), rand.nextInt(4) + 20));
                drops.add(new Armour("Bronze Helmet", Class.WARRIOR.role(), Restriction.HEAD.equip(),rand.nextInt(2), 0, 0, rand.nextInt(3), rand.nextInt(4) + 30));
                break;
            case 3:
                drops.add(new Weapon("Bronze Dagger", Class.ROGUE.role(), Restriction.ARM.equip(),rand.nextInt(2), rand.nextInt(3) + 1, 0, 0, rand.nextInt(6) + 40));
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
