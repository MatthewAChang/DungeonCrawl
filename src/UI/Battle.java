package UI;

import Party.Item.Armour;
import Party.Item.Equipment;
import Party.Item.Restriction;
import Party.Item.Weapon;
import Party.Members.PartyMember;
import Party.Members.Class;
import World.Dungeon;
import World.Enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battle extends DungeonCrawl {

    private Dungeon dungeon;

    public Battle()
    {
        dungeon = createDungeon();
        ui.appendMain("You are now entering " + dungeon.getName() + ".\n");
        waitForNullInput();
        ui.clearMainText();
        party.setLocation(dungeon);
        battle();
        ui.clearAll();
        party.setLocation(town);
    }

    private Dungeon createDungeon()
    {
        Random rand = new Random();
        List<Enemy> enemies = new ArrayList<>();

        List<Equipment> drops = getDrops(rand);

        int ID = 1;
        for(int i = 1; i < rand.nextInt(3) + 2; i++) {
            int gold = rand.nextInt(15) + 1;
            enemies.add(new Enemy("Goblin" + i, ID++, 100, 90, 30, false, gold, 20, drops));
        }
        for(int i = 1; i < rand.nextInt(2) + 2; i++) {
            int gold = rand.nextInt(20) + 5;
            enemies.add(new Enemy("Troll" + i, ID++, 200, 120, 40, false, gold, 40, drops));
        }
        return new Dungeon("Dung", enemies);
    }

    private List<Equipment> getDrops(Random rand)
    {
        List<Equipment> drops = new ArrayList<>();
        drops.add(new Weapon("Bronze Dagger", Class.ROUGE.role(), Restriction.ARM.equip(),rand.nextInt(2), rand.nextInt(3) + 1, 0, 0, rand.nextInt(6) + 40));
        drops.add(new Weapon("Bronze Sword", Class.WARRIOR.role(), Restriction.RIGHT_ARM.equip(),rand.nextInt(3) + 1, 0, 0, 0, rand.nextInt(6) + 50));
        drops.add(new Armour("Worn Cowl", Class.MAGE.role(), Restriction.HEAD.equip(),0, 0, rand.nextInt(4) + 1, rand.nextInt(3), rand.nextInt(4) + 20));
        drops.add(new Armour("Bronze Helmet", Class.WARRIOR.role(), Restriction.HEAD.equip(),rand.nextInt(2), 0, 0, rand.nextInt(3), rand.nextInt(4) + 30));
        return drops;
    }

    private void battle()
    {
        boolean fighting = true;
        battle:
        while(fighting)
        {
            updateInformation();
            newTurn();
            // Player picks target for attack
            for(PartyMember p : party)
            {
                if(p.isAlive()) {
                    selection:
                    while(true) {
                        ui.clearThenAppendMain(p.getName() + ":\n");
                        ui.appendMain("1) Attack  2) Spell  3) Item  4) Run\n");
                        ui.appendMain("5) Help\n");
                        while (true) {
                            waitForInput();
                            String optionStr = ui.getTextInput();
                            if (optionStr.matches("[14]")) {
                                int option = Integer.parseInt(optionStr);
                                switch (option) {
                                    case 1:
                                        option = (option * 100) + attack();
                                        break;
//                            case 2:
//                                break;
//                            case 3:
//                                break;
                                    case 4:
                                        if (!run())
                                            break battle;
                                }
                                p.setOption(option);
                                break selection;
                            } else if (optionStr.matches("5"))
                                Help.help(4);
                            break;
                        }
                    }
                }
            }
            // Enemy picks target for attack
            for(Enemy e : dungeon) {
                Random rand = new Random();
                int option;
                check:
                while (true) {
                    option = rand.nextInt(party.getPartyMembers().size()) + 1;
                    for(PartyMember p : party)
                        if(option == p.getID() && p.isAlive())
                            break check;
                }
                e.setTarget(option);
            }
            battleCalculations();
            fighting = checkEndBattle();
        }
        spoilsOfBattle();
    }

    private boolean checkEndBattle()
    {
        if(dungeon.allDead() || party.allDead())
            return false;
        return true;
    }

    private void spoilsOfBattle()
    {
        ui.clearMainText();
        if(dungeon.allDead())
        {
            for(PartyMember p : party)
            {
                if(p.isAlive()) {
                    ui.appendMain(p.getName() + " earned " + dungeon.getExp() + " exp.\n");
                    waitForNullInput();
                    if (p.addExp(dungeon.getExp())) {
                        levelUp(p);
                    }
                }
            }
            party.addGold(dungeon.getGold());
            ui.appendMain("Drops:\n");
            ui.appendMain(dungeon.getGold() + " gold\n");
            List<Equipment> equipmentDrops = dungeon.getDrops();
            if(!equipmentDrops.isEmpty())
            {
                for (Equipment equip : equipmentDrops)
                {
                    party.addEquipment(equip);
                    ui.appendMain(equip.getName() + "\n");
                }
            }
            waitForNullInput();
        }
    }

    private int attack()
    {
        ui.appendMain("Who to attack:");
        for(Enemy e : dungeon)
        {
            if(e.getID() % 3 == 1)
                ui.appendMain("\n");
            ui.appendMain(e.getID() +  ". " + e.getName() + "  ");
        }
        ui.appendMain("\n");
        while(true)
        {
            waitForInput();
            String optionStr = ui.getTextInput();
            if (optionStr.matches("[0-9]{1,2}"))
            {
                int option = Integer.parseInt(optionStr);
                for(Enemy e : dungeon)
                    if(e.getID() == option && e.isAlive())
                    {
                        return option;
                    }
            }
        }
    }

    private boolean run()
    {
        Random rand = new Random();
        if(rand.nextInt(100) + 1 > 5) {
            ui.clearMainText();
            ui.appendMain("You ran from battle!\n");
            waitForNullInput();
            return false;
        }
        ui.appendMain("Run failed!\n");
        waitForNullInput();
        return true;
    }

    private void battleCalculations()
    {
        ui.clearMainText();
        boolean fight = true;
        while(fight)
        {
            Random rand = new Random();
            if(rand.nextInt(2) == 0)
            {
                PartyMember p = party.getPartyMember(rand.nextInt(party.getPartyMembers().size()));
                if(p.isAlive() && !p.hasAttacked())
                {
                    int option = p.getOption();
                    switch(option / 100)
                    {
                        case 1:
                            for(Enemy e : dungeon)
                            {
                                if(e.getID() == option % 100)
                                {
                                    if(e.isAlive()) {
                                        e.damage(damageCalculations(p, e));
                                        updateInformation();
                                        ui.appendMain(p.getName() + " attacked " + e.getName() + " for " + damageCalculations(p, e) + ".\n");
                                    }
                                    else
                                    {
                                        ui.appendMain(p.getName() + " attacked " + e.getName() + ", but " + e.getName() + " is already dead.\n");
                                    }
                                    waitForNullInput();
                                    p.setAttacked(true);
                                    break;
                                }
                            }
                            break;
                    }
                }
            }
            else
            {
                Enemy e = dungeon.getEnemy(rand.nextInt(dungeon.getEnemies().size()));
                if(e.isAlive() && !e.hasAttacked())
                {
                    for(PartyMember p : party)
                    {
                        if(p.getID() == e.getTarget())
                        {
                            if(p.isAlive()) {
                                p.damage(damageCalculations(e, p));
                                updateInformation();
                                ui.appendMain(e.getName() + " attacked " + p.getName() + " for " + damageCalculations(e, p) + ".\n");
                            }
                            else {
                                ui.appendMain(e.getName() + " attacked " + p.getName() + ", but " + p.getName() + " is already dead.\n");
                            }
                            waitForNullInput();
                            e.setAttacked(true);
                            break;
                        }
                    }
                }
            }
            fight = checkEndTurn();
        }
        ui.clearMainText();
    }

    private int damageCalculations(PartyMember member, Enemy enemy)
    {
        return member.getDamage() - enemy.getArmour();
    }

    private int damageCalculations(Enemy enemy, PartyMember member)
    {
        return enemy.getDamage() - member.getArmour();
    }

    private void newTurn()
    {
        for(PartyMember p : party)
            p.setAttacked(false);
        for(Enemy e : dungeon)
            e.setAttacked(false);
    }

    private boolean checkEndTurn()
    {
        boolean again = false;
        for(PartyMember p : party)
            if(p.isAlive() && !p.hasAttacked())
                again = true;
        for(Enemy e : dungeon)
            if(e.isAlive() && !e.hasAttacked())
                again = true;
        if(!again)
            return false;
        return true;
    }
}
