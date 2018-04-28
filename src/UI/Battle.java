package UI;

import World.Character.Enemy;
import World.Character.PartyMember;
import World.Item.Equipment;
import World.World.Dungeon;

import java.util.List;
import java.util.Random;

public class Battle extends Game {

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
                        cont:
                        while (true) {
                            int option = checkValidInput();
                            if (option > 0 && option < 5) {
                                switch (option) {
                                    case 1:
                                        int attack = attack();
                                        if(attack == -1) {
                                            break cont;
                                        } else {
                                            option = (option * 100) + attack;
                                            break;
                                        }
                                    case 2:
                                        break cont;
                                    case 3:
                                        break cont;
                                    case 4:
                                        if (!run())
                                            break battle;
                                }
                                p.setOption(option);
                                break selection;
                            } else if (option == 5)
                                printHelp(4);
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
                        if(option == p.getId() && p.isAlive())
                            break check;
                }
                e.setOption(option);
            }
            battleCalculations();
            fighting = checkEndBattle();
        }
        spoilsOfBattle();
    }

    private boolean checkEndBattle()
    {
        return !(dungeon.allDead() || world.getParty().allDead());
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
        ui.clearThenAppendMain("Who to attack:\n");
        int back = 1;
        for(Enemy e : dungeon)
        {
            ui.appendMain(e.getId() +  ". " + e.getName() + "  ");
            if(e.getId() % 3 == 0)
                ui.appendMain("\n");
            back = e.getId() + 1;
        }
        ui.appendMain(back + ". Back");
        ui.appendMain("\n");
        while(true)
        {
            int option = checkValidInput();
            if(option == back)
                return -1;
            for(Enemy e : dungeon) {
                if (e.getId() == option && e.isAlive()) {
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
                                if(e.getId() == option % 100)
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
                        if(p.getId() == e.getOption())
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
        double damage = 1 - (member.getArmour() / 100.0);
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
        return again;
    }
}
