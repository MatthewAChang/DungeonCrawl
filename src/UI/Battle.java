package UI;

import Party.Item.Equipment;
import Party.Members.Class;
import Party.Members.PartyMember;
import Party.Members.Warrior;
import World.Dungeon;
import World.Enemy;

import java.util.List;
import java.util.Random;

public class Battle extends DungeonCrawl {

    private Dungeon dungeon;

    public Battle(Dungeon dungeon)
    {
        this.dungeon = dungeon;
        ui.appendMain("You are now entering " + dungeon.getName() + ".\n");
        waitForNullInput();
        world.getCurrentTown().setCurrentDungeon(dungeon);
        ui.clearMainText();
        battle();
        ui.clearAll();
        world.getCurrentTown().setCurrentDungeon(null);
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
            for(PartyMember p : world.getParty())
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
                                help(4);
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
                    option = rand.nextInt(world.getParty().getPartyMembers().size()) + 1;
                    for(PartyMember p : world.getParty())
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
        if(dungeon.allDead() || world.getParty().allDead())
            return false;
        return true;
    }

    private void spoilsOfBattle()
    {
        ui.clearMainText();
        if(dungeon.allDead())
        {
            for(PartyMember p : world.getParty())
            {
                if(p.isAlive()) {
                    ui.appendMain(p.getName() + " earned " + dungeon.getExp() + " exp.\n");
                    waitForNullInput();
                    if (p.addExp(dungeon.getExp())) {
                        levelUp(p);
                    }
                }
            }
            world.getParty().addGold(dungeon.getGold());
            ui.appendMain("Drops:\n");
            ui.appendMain(dungeon.getGold() + " gold\n");
            List<Equipment> equipmentDrops = dungeon.getDrops();
            if(!equipmentDrops.isEmpty())
            {
                for (Equipment equip : equipmentDrops)
                {
                    world.getParty().addEquipment(equip);
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
                PartyMember p = world.getParty().getPartyMember(rand.nextInt(world.getParty().getPartyMembers().size()));
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
                    for(PartyMember p : world.getParty())
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
        double damage = 1 - (enemy.getArmour() / 100.0);
        return (int)(member.getDamage() * damage);
    }

    private int damageCalculations(Enemy enemy, PartyMember member)
    {
        int reduction = member.getArmour();
        if(member.getRole() == Class.WARRIOR.role()) {
            Warrior t = (Warrior) member;
            reduction = t.getWarriorArmour();
        }
        double damage = 1 - (reduction / 100.0);

        return (int)(enemy.getDamage() * damage);
    }

    private void newTurn()
    {
        for(PartyMember p : world.getParty())
            p.setAttacked(false);
        for(Enemy e : dungeon)
            e.setAttacked(false);
    }

    private boolean checkEndTurn()
    {
        boolean again = false;
        for(PartyMember p : world.getParty())
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
