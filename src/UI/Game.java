package UI;

import Helper.Help;
import Helper.Story;
import World.Character.Enemy;
import World.Character.PartyMember;
import World.World.Dungeon;
import World.World.World;

public class Game {
    protected static UIFrame ui;
    protected static World world;

    public static void Game()
    {
        ui = ui.getInstance();
        world = world.getInstance();
        new NewGame();
        new MainMenu();
    }

    protected static void updateInformation()
    {
        ui.clearAll();
        updatePartyInfo();
        if(world.getCurrentTown().getCurrentDungeon() != null) {
            ui.appendOther("Location: " + world.getCurrentTown().getCurrentDungeon().getName() + "\n");
            updateEnemyInformation();
        } else {
            ui.appendOther("Location: " + world.getCurrentTown().getName() + "\n");
        }
    }

    private static void updatePartyInfo()
    {
        for(PartyMember p : world.getParty())
        {
            ui.appendParty(String.format("L.%-3s%-11sEXP:%s\n", p.getLevel(), p.getName(), p.getExpForNextLvlRelative()));
            ui.appendParty(String.format("HP:%4s/%-4s  MP:%4s/%-4s\n", p.getHp(), p.getMaxHp(), p.getMana(), p.getMaxMana()));
        }
        ui.appendParty("Gold: " + world.getParty().getGold());
    }

    private static void updateEnemyInformation()
    {
        Dungeon dungeon = world.getCurrentTown().getCurrentDungeon();
        for(Enemy e : dungeon)
        {
            ui.appendOther(String.format("%14s%5s/%s\n", e.getName(), e.getHp(), e.getMaxHp()));
        }
    }

    protected static void printStory(int index) {
        ui.clearMainText();
        ui.appendMain(Story.story(index));
        waitForNullInput();
        ui.clearMainText();
    }

    protected static void printHelp(int index) {
        ui.clearMainText();
        ui.appendMain(Help.help(index));
        waitForNullInput();
        ui.clearMainText();
    }

    protected static void levelUp(PartyMember member)
    {
        int pointsLeft = PartyMember.LEVEL_UP_POINTS;
        if(member == world.getParty().getPlayer())
        {
            while (pointsLeft > 0) {
                ui.clearMainText();
                ui.appendMain("You have leveled up!\n");
                ui.appendMain("You have " + pointsLeft + " skill points left:\n");
                ui.appendMain(String.format("%-16s%s", "1) Strength", world.getParty().getPlayer().getBaseStrength() + "\n"));
                ui.appendMain(String.format("%-16s%s", "2) Dexterity", world.getParty().getPlayer().getBaseDexterity() + "\n"));
                ui.appendMain(String.format("%-16s%s", "3) Willpower", world.getParty().getPlayer().getBaseWillpower() + "\n"));
                ui.appendMain(String.format("%-16s%s", "4) Constitution", world.getParty().getPlayer().getBaseConstitution() + "\n"));
                ui.appendMain("5) Help\n");
                while (true) {
                    int option = checkValidInput();
                    if(option > 0 && option < 5) {
                        world.getParty().getPlayer().levelUp(option);
                        pointsLeft--;
                    }
                    else if (option == 5)
                        printHelp(2);
                    break;
                }

            }
            world.getParty().getPlayer().levelUp();
            ui.clearMainText();
        }
        else
        {
            member.autoLevel();
        }
    }

    protected static int checkValidInput() {
        waitForInput();
        String option = ui.getTextInput();
        if(option.matches("[0-9]+"))
            return Integer.parseInt(option);
        return -1;
    }

    // Takes input
    protected static void waitForInput()
    {
        ui.appendMain(">> ");
        while(!ui.isInputEntered())
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // nothing
            }
        }
    }

    // Just press enter
    protected static void waitForNullInput()
    {
        ui.appendMain(">> ");
        while(!ui.isInputEntered()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // nothing
            }
        }
        ui.setInputEntered(false);
    }
}
