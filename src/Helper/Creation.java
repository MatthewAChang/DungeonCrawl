package Helper;

import Helper.Enum.ClassList;
import Helper.Enum.EnemyList;
import Helper.Enum.Restriction;
import World.Character.*;
import World.Item.Armour;
import World.Item.Equipment;
import World.Item.Spell;
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
        List<PartyMember> members = addBasePartyMembers(player);
        addBaseEquipment(members);
        return members;
    }

    private static List<PartyMember> addBasePartyMembers(PartyMember player) {
        List<PartyMember> members = new ArrayList<>();
        members.add(player);
        if(player.getRole() == ClassList.WARRIOR.role())
        {
            members.add(createNewRogue("Leliana"));
            members.add(createNewMage("Vanille"));
        }
        else if(player.getRole() == ClassList.ROGUE.role())
        {
            members.add(createNewWarrior("Marth"));
            members.add(createNewMage("Luxanna"));
        }
        else if(player.getRole() == ClassList.MAGE.role())
        {
            members.add(createNewWarrior("Ogma"));
            members.add(createNewRogue("Rose"));
        }
        return members;
    }

    private static void addBaseEquipment(List<PartyMember> members) {
        for(PartyMember p : members)
        {
            if(p.getRole() == ClassList.WARRIOR.role())
            {
                p.equipBody(new Armour("Bronze Armour", ClassList.WARRIOR.role(), Restriction.BODY.equip(),1, 0, 0, 1, 10));
                p.equipRightArm(new Weapon("Bronze Sword", ClassList.WARRIOR.role(), Restriction.RIGHT_ARM.equip(),1, 0, 0, 0, 50));
                p.equipLeftArm(new Armour("Bronze Shield", ClassList.WARRIOR.role(), Restriction.LEFT_ARM.equip(),0, 0, 0, 1, 2));
            }
            else if(p.getRole() == ClassList.ROGUE.role())
            {
                p.equipBody(new Armour("Worn Cloak", ClassList.ROGUE.role(), Restriction.BODY.equip(),1, 0, 0, 1, 5));
                p.equipRightArm(new Weapon("Bronze Dagger", ClassList.ROGUE.role(), Restriction.ARM.equip(),0, 1, 0, 0, 30));
                p.equipLeftArm(new Weapon("Bronze Dagger", ClassList.ROGUE.role(), Restriction.ARM.equip(),0, 1, 0, 0, 30));
            }
            else if(p.getRole() == ClassList.MAGE.role())
            {
                p.equipBody(new Armour("Old Tunic", ClassList.MAGE.role(), Restriction.BODY.equip(),0, 0, 1, 1, 4));
                p.equipRightArm(new Weapon("Old Staff", ClassList.MAGE.role(), Restriction.RIGHT_ARM.equip(),0, 0, 2, 0, 60));
            }
        }
    }

    public static void addSpell(PartyMember p) {
        if(p.getRole() == ClassList.WARRIOR.role())
            warriorSpell((Warrior)p);
        else if(p.getRole() == ClassList.ROGUE.role())
            rogueSpell((Rogue)p);
        else if(p.getRole() == ClassList.MAGE.role())
            mageSpell((Mage)p);

    }

    private static void warriorSpell(Warrior warrior) {
        switch (warrior.getLevel()) {
            case 0:
                warrior.addSpell(new Spell(1, "Shield Bash", 1.25, 30, false,false, true, false));
                break;
            case 3:
                warrior.addSpell(new Spell(2, "Hunker Down", 0.5, 40, true,false, false, false));
                break;
        }
    }

    private static void rogueSpell(Rogue rogue) {
        switch (rogue.getLevel()) {
            case 0:
                rogue.addSpell(new Spell(1, "Cheap Shot", 2, 50, false,false, true, false));
                break;
            case 3:
                rogue.addSpell(new Spell(2, "Throwing Knives", 0.66, 40,false, false, true, true));
                break;
        }
    }

    private static void mageSpell(Mage mage) {
        switch (mage.getLevel()) {
            case 0:
                mage.addSpell(new Spell(1, "Heal", 0.75, 50, true,true, false, false));
                break;
            case 3:
                mage.addSpell(new Spell(2, "Fireball", 0.5, 40,false, false, true, true));
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

        int enemyId = 1;
        switch(nextDungeonId) {
            case 1:
                for(int i = 1; i < rand.nextInt(3) + 2; i++) {
                    enemies.add(new Enemy(EnemyList.toString(EnemyList.GOBLIN.enemy()) + i, enemyId++, 100, 30, 5, false, rand.nextInt(15) + 1, 50, createDrops(EnemyList.GOBLIN.enemy())));
                }
                for(int i = 1; i < rand.nextInt(2) + 2; i++) {
                    enemies.add(new Enemy(EnemyList.toString(EnemyList.TROLL.enemy()) + i, enemyId++, 200, 40, 10, false, rand.nextInt(20) + 5, 100, createDrops(EnemyList.TROLL.enemy())));
                }
                break;
            case 2:
                for(int i = 1; i < rand.nextInt(5) + 2; i++) {
                    enemies.add(new Enemy(EnemyList.toString(EnemyList.GOBLIN.enemy()) + i, enemyId++, 100, 30, 5, false, rand.nextInt(15) + 1, 50, createDrops(EnemyList.GOBLIN.enemy())));
                }
                for(int i = 1; i < rand.nextInt(3) + 2; i++) {
                    enemies.add(new Enemy(EnemyList.toString(EnemyList.TROLL.enemy()) + i, enemyId++, 200, 40, 10, false, rand.nextInt(20) + 5, 100, createDrops(EnemyList.TROLL.enemy())));
                }
                break;
            case 3:
                for(int i = 1; i < rand.nextInt(4) + 2; i++) {
                    enemies.add(new Enemy(EnemyList.toString(EnemyList.TROLL.enemy()) + i, enemyId++, 200, 40, 10, false, rand.nextInt(20) + 5, 100, createDrops(EnemyList.TROLL.enemy())));
                }
                enemies.add(new Enemy(EnemyList.toString(EnemyList.TROLL_LEADER.enemy()), enemyId++, 300, 50, 25, true, rand.nextInt(100) + 100, 40, createDrops(EnemyList.TROLL_LEADER.enemy())));
                break;
            case 4:
                for(int i = 1; i < rand.nextInt(5) + 2; i++) {
                    enemies.add(new Enemy(EnemyList.toString(EnemyList.TROLL.enemy()) + i, enemyId++, 200, 40, 10, false, rand.nextInt(20) + 5, 100, createDrops(EnemyList.TROLL.enemy())));
                }
                break;
            case 5:
                for(int i = 1; i < rand.nextInt(5) + 2; i++) {
                    enemies.add(new Enemy(EnemyList.toString(EnemyList.GOBLIN.enemy()) + i, enemyId++, 100, 30, 5, false, rand.nextInt(15) + 1, 50, createDrops(EnemyList.GOBLIN.enemy())));
                }
                for(int i = 1; i < rand.nextInt(4) + 2; i++) {
                    enemies.add(new Enemy(EnemyList.toString(EnemyList.TROLL.enemy()) + i, enemyId++, 200, 40, 10, false, rand.nextInt(20) + 5, 100, createDrops(EnemyList.TROLL.enemy())));
                }
                break;
            case 6:
                for(int i = 1; i < rand.nextInt(3) + 2; i++) {
                    enemies.add(new Enemy(EnemyList.toString(EnemyList.TROLL.enemy()) + i, enemyId++, 200, 40, 10, false, rand.nextInt(20) + 5, 100, createDrops(EnemyList.TROLL.enemy())));
                }
                enemies.add(new Enemy(EnemyList.toString(EnemyList.TROLL_LEADER.enemy()), enemyId++, 300, 50, 25, true, rand.nextInt(100) + 100, 40, createDrops(EnemyList.TROLL_LEADER.enemy())));
                break;
        }
        return new Dungeon(nextDungeonId++, name, enemies);
    }

    private static List<Equipment> createDrops(int enemy)
    {
        Random rand = new Random();
        List<Equipment> drops = new ArrayList<>();
        if(enemy == EnemyList.GOBLIN.enemy()) {
            drops.add(new Weapon("Bronze Dagger", ClassList.ROGUE.role(), Restriction.ARM.equip(), rand.nextInt(2), rand.nextInt(3) + 2, 0, 0, rand.nextInt(6) + 30));
            drops.add(new Weapon("Bronze Sword", ClassList.WARRIOR.role(), Restriction.RIGHT_ARM.equip(), rand.nextInt(3) + 2, 0, 0, 0, rand.nextInt(6) + 50));
            drops.add(new Weapon("Old Staff", ClassList.MAGE.role(), Restriction.RIGHT_ARM.equip(), 0, 0, rand.nextInt(3) + 2, 0, rand.nextInt(6) + 60));
            drops.add(new Armour("Ancient Tunic", ClassList.ALL.role(), Restriction.HEAD.equip(), 1, 1, 1, 1, rand.nextInt(4) + 5));
            drops.add(new Armour("Bronze Helmet", ClassList.WARRIOR.role(), Restriction.HEAD.equip(), rand.nextInt(2), 0, 0, rand.nextInt(3) + 1, rand.nextInt(2) + 1));
        } else if (enemy == EnemyList.TROLL.enemy()){
            drops.add(new Weapon("Bronze Dagger", ClassList.ROGUE.role(), Restriction.ARM.equip(), rand.nextInt(2), rand.nextInt(3) + 2, 0, 0, rand.nextInt(6) + 30));
            drops.add(new Weapon("Bronze Sword", ClassList.WARRIOR.role(), Restriction.RIGHT_ARM.equip(), rand.nextInt(3) + 2, 0, 0, 0, rand.nextInt(6) + 50));
            drops.add(new Weapon("Old Staff", ClassList.MAGE.role(), Restriction.RIGHT_ARM.equip(), 0, 0, rand.nextInt(3) + 2, 0, rand.nextInt(6) + 60));
            drops.add(new Armour("Ancient Tunic", ClassList.ALL.role(), Restriction.BODY.equip(), 1, 1, 1, 1, rand.nextInt(4) + 5));
            drops.add(new Armour("Bronze Helmet", ClassList.WARRIOR.role(), Restriction.HEAD.equip(), rand.nextInt(2), 0, 0, rand.nextInt(3) + 1, rand.nextInt(2) + 1));
            drops.add(new Weapon("Glass Sword", ClassList.WARRIOR.role(), Restriction.HEAD.equip(), 5, 3, 2, 1, 40));
        } else if (enemy == EnemyList.TROLL_LEADER.enemy()) {
            drops.add(new Weapon("Bronze Dagger", ClassList.ROGUE.role(), Restriction.ARM.equip(), rand.nextInt(2), rand.nextInt(3) + 2, 0, 0, rand.nextInt(6) + 30));
            drops.add(new Weapon("Bronze Sword", ClassList.WARRIOR.role(), Restriction.RIGHT_ARM.equip(), rand.nextInt(3) + 2, 0, 0, 0, rand.nextInt(6) + 50));
            drops.add(new Weapon("Old Staff", ClassList.MAGE.role(), Restriction.RIGHT_ARM.equip(), 0, 0, rand.nextInt(3) + 2, 0, rand.nextInt(6) + 60));
            drops.add(new Armour("Ancient Tunic", ClassList.ALL.role(), Restriction.BODY.equip(), 1, 1, 1, 1, rand.nextInt(4) + 5));
            drops.add(new Armour("Bronze Helmet", ClassList.WARRIOR.role(), Restriction.HEAD.equip(), rand.nextInt(2), 0, 0, rand.nextInt(3) + 1, rand.nextInt(2) + 1));
            drops.add(new Weapon("Glass Sword", ClassList.WARRIOR.role(), Restriction.HEAD.equip(), 5, 3, 2, 1, 40));
        }
        return drops;
    }

    public static List<Equipment> addInventory(int town) {
        List<Equipment> inventory = new ArrayList<>();
        switch (town) {
            case 1:
                inventory.add(new Weapon("Steel Sword", ClassList.WARRIOR.role(), Restriction.RIGHT_ARM.equip(), 5, 2, 0, 0, 60));
                inventory.add(new Weapon("Steel Dagger", ClassList.ROGUE.role(), Restriction.ARM.equip(), 2, 5, 0, 0, 40));
                inventory.add(new Weapon("Apprentice Staff", ClassList.MAGE.role(), Restriction.RIGHT_ARM.equip(), 0, 2, 5, 0, 70));
                inventory.add(new Armour("Steel Plate", ClassList.WARRIOR.role(), Restriction.BODY.equip(), 3, 0, 0, 3, 13));
                inventory.add(new Armour("Leather Armour", ClassList.ROGUE.role(), Restriction.BODY.equip(), 0, 2, 0, 2, 7));
                inventory.add(new Armour("Stiffened Robe", ClassList.MAGE.role(), Restriction.BODY.equip(), 0, 0, 4, 1, 6));
                break;
            case 2:
                inventory.add(new Weapon("Steel Sword", ClassList.WARRIOR.role(), Restriction.RIGHT_ARM.equip(), 5, 2, 0, 0, 60));
                inventory.add(new Weapon("Steel Dagger", ClassList.ROGUE.role(), Restriction.ARM.equip(), 2, 5, 0, 0, 40));
                inventory.add(new Weapon("Apprentice Staff", ClassList.MAGE.role(), Restriction.RIGHT_ARM.equip(), 0, 2, 5, 0, 70));
                inventory.add(new Armour("Steel Helmet", ClassList.MAGE.role(), Restriction.BODY.equip(), 2, 1, 0, 1, 3));
                inventory.add(new Armour("Leather Helm", ClassList.MAGE.role(), Restriction.BODY.equip(), 1, 1, 0, 1, 2));
                inventory.add(new Armour("Stiffened Hood", ClassList.MAGE.role(), Restriction.BODY.equip(), 0, 0, 3, 1, 1));
                break;
        }
        return inventory;
    }

    public static String getWorldName() {
        return WORLD_NAME;
    }
}
