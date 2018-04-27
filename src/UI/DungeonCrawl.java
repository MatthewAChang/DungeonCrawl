package UI;

import Helper.Help;
import Helper.Story;
import Party.Members.PartyMember;
import World.Dungeon;
import World.Enemy;
import World.World;

public class DungeonCrawl {
    protected static UIFrame ui;
    public static World world;

    public static void main(String[] args)
    {
        ui = ui.getInstance();
        world = world.getInstance();
        new NewGame();
        new MainMenu();
    }

    protected static void updateInformation()
    {
        ui.clearPartyInfo();
        ui.clearOtherInfo();
        ui.appendOther("Location: " + world.getCurrentTown().getName() + "\n");
        updatePartyInfo();
        if(world.getCurrentTown().getCurrentDungeon() != null)
            updateEnemyInformation();
    }

    private static void updatePartyInfo()
    {
        for(PartyMember p : world.getParty())
        {
            String basic = String.format("L.%-3s%-10sEXP:%s", p.getLevel(), p.getName(), p.getExpForNextLvlRelative());
            ui.appendParty(basic + "\n");
            String battle = String.format("HP:%4s/%-4s  MP:%4s/%-4s", p.getHP(), p.getMaxHP(), p.getMana(), p.getMaxMana());
            ui.appendParty(battle + "\n");
        }
    }

    private static void updateEnemyInformation()
    {
        Dungeon dungeon = world.getCurrentTown().getCurrentDungeon();
        for(Enemy e : dungeon)
        {
            String info = e.getName() + "\t";
            info += e.getHP();
            info += "/" + e.getMaxHP();
            ui.appendOther(info + "\n");
        }
    }

    protected static void story(int index) {
        ui.clearMainText();
        ui.appendMain(Story.story(index));
        waitForNullInput();
        ui.clearMainText();
    }

    protected static void help(int index) {
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
                    waitForInput();
                    String pointStr = ui.getTextInput();
                    if (pointStr.matches("[1-4]")) {
                        world.getParty().getPlayer().levelUp(Integer.parseInt(pointStr));
                        pointsLeft--;
                    }
                    else if (pointStr.matches("5"))
                        help(2);
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
        while(!ui.isInputEntered())
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // nothing
            }
        }
        ui.setInputEntered(false);
    }
}

