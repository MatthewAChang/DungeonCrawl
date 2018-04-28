package UI;

import World.Character.Class;
import World.Character.Enemy;
import World.Character.PartyMember;
import World.Item.Equipment;
import World.Item.Spell;
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
                                        int attack = targetEnemy();
                                        if(attack == -1) {
                                            break cont;
                                        } else {
                                            option = (option * 100) + attack;
                                            break;
                                        }
                                    case 2:
                                        int spell = spell(p);
                                        if(spell == -1) {
                                            break cont;
                                        } else {
                                            option = (option * 100) + spell;
                                            break;
                                        }
                                    case 3:
                                        break cont;
                                    case 4:
                                        if (!run())
                                            break battle;
                                        p.setAttacked(true);
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
                while (true) {
                    int option = rand.nextInt(world.getParty().getPartyMembers().size()) + 1;
                    if(world.getParty().getPartyMember(option - 1).isAlive()) {
                        e.setOption(option);
                        break;
                    }
                }
            }
            battleCalculations();
            fighting = checkEndBattle();
        }
        endBattle();
    }

    private boolean checkEndBattle()
    {
        return !(dungeon.allDead() || world.getParty().allDead());
    }

    private void endBattle()
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

    private int targetEnemy()
    {
        ui.clearThenAppendMain("Who to attack:\n");
        int i = 1;
        for(Enemy e : dungeon)
        {
            ui.appendMain(i +  ". " + e.getName() + "  ");
            if(i++ % 3 == 0)
                ui.appendMain("\n");
        }
        ui.appendMain(i + ". Back\n");
        while(true)
        {
            int option = checkValidInput();
            if(option == i)
                return -1;
            if (dungeon.getEnemies().get(option - 1).isAlive()) {
                return option;
            }
        }
    }

    private int targetAlly() {
        ui.clearThenAppendMain("Who to help:\n");
        int i = 1;
        for(PartyMember p : world.getParty())
        {
            ui.appendMain(i +  ". " + p.getName() + "  ");
            if(i++ % 3 == 0)
                ui.appendMain("\n");
        }
        ui.appendMain(i + ". Back\n");
        while(true)
        {
            int option = checkValidInput();
            if(option == i)
                return -1;
            if (world.getParty().getPartyMember(option - 1).isAlive()) {
                return option;
            }
        }
    }

    private int spell(PartyMember p) {
        while(true) {
            ui.clearThenAppendMain("What spell to use:\n");
            int i = 1;
            for (Spell s : p) {
                ui.appendMain(i++ + ". " + s.getName() + "\n");
            }
            ui.appendMain(i + ". Back\n");
            while (true) {
                int option = checkValidInput();
                if (option == i)
                    return -1;
                else if (option > 0 && option < i) {
                    Spell spell = p.getSpells().get(option - 1);
                    // Single target enemy
                    if (!spell.isTargetSelf() && !spell.isTargetAlly() && spell.isTargetEnemy() && !spell.isAoe()) {
                        int target = targetEnemy();
                        if(target == -1)
                            break;
                        return (spell.getId() * 10) + target;
                    // Single target ally
                    } else if (spell.isTargetSelf() && spell.isTargetAlly() && !spell.isTargetEnemy() && !spell.isAoe()) {
                        int target = targetAlly();
                        if(target == -1)
                            break;
                        return (spell.getId() * 10) + targetAlly();
                    // AOE
                    } else {
                        return spell.getId() * 10;
                    }
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
            if(rand.nextInt(2) == 0) {
                PartyMember p = world.getParty().getPartyMember(rand.nextInt(world.getParty().getPartyMembers().size()));
                if(p.isAlive() && !p.hasAttacked()) {
                    int option = p.getOption();
                    switch(option / 100) {
                        case 1:
                            Enemy e = dungeon.getEnemies().get((option % 100) - 1);
                            if(e.isAlive()) {
                                e.damage(damageCalculations(p, e));
                                updateInformation();
                                ui.appendMain(p.getName() + " attacked " + e.getName() + " for " + damageCalculations(p, e) + ".\n");
                            } else {
                                ui.appendMain(p.getName() + " attacked " + e.getName() + ", but " + e.getName() + " is already dead.\n");
                            }
                            waitForNullInput();
                            p.setAttacked(true);
                            break;
                    }
                }
            }
            else
            {
                Enemy e = dungeon.getEnemy(rand.nextInt(dungeon.getEnemies().size()));
                if(e.isAlive() && !e.hasAttacked())
                {
                    PartyMember p = world.getParty().getPartyMember(e.getOption() - 1);
                    if(p.isAlive()) {
                        p.damage(damageCalculations(e, p));
                        updateInformation();
                        ui.appendMain(e.getName() + " attacked " + p.getName() + " for " + damageCalculations(e, p) + ".\n");
                    } else {
                        ui.appendMain(e.getName() + " attacked " + p.getName() + ", but " + p.getName() + " is already dead.\n");
                    }
                    waitForNullInput();
                    e.setAttacked(true);
                }
            }
            fight = checkEndTurn();
        }
        ui.clearMainText();
    }

    private int damageCalculations(PartyMember member, Enemy enemy)
    {
        if(member.getRole() == Class.MAGE.role())
            return member.getDamage();
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
