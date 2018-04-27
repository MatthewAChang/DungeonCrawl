package UI;

import Helper.Help;
import Party.Members.Party;
import Party.Members.PartyMember;
import World.Dungeon;
import World.Enemy;
import World.World;

public class DungeonCrawl {
    protected static UIFrame ui;
    protected static World world;
    protected static Party party;

    public static void main(String[] args)
    {
        ui = ui.getInstance();
        world = world.getInstance();
        party = party.getInstance();
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
        for(PartyMember p : party)
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

    protected static void help(int index) {
        ui.clearMainText();
        ui.appendMain(Help.help(index));
        waitForNullInput();
        ui.clearMainText();
    }

    protected static void levelUp(PartyMember member)
    {
        int pointsLeft = PartyMember.LEVEL_UP_POINTS;
        if(member == party.getPlayer())
        {
            while (pointsLeft > 0) {
                ui.clearMainText();
                ui.appendMain("You have leveled up!\n");
                ui.appendMain("You have " + pointsLeft + " skill points left:\n");
                ui.appendMain(String.format("%-16s%s", "1) Strength", party.getPlayer().getBaseStrength() + "\n"));
                ui.appendMain(String.format("%-16s%s", "2) Dexterity", party.getPlayer().getBaseDexterity() + "\n"));
                ui.appendMain(String.format("%-16s%s", "3) Willpower", party.getPlayer().getBaseWillpower() + "\n"));
                ui.appendMain(String.format("%-16s%s", "4) Constitution", party.getPlayer().getBaseConstitution() + "\n"));
                ui.appendMain("5) Help\n");
                while (true) {
                    waitForInput();
                    String pointStr = ui.getTextInput();
                    if (pointStr.matches("[1-4]")) {
                        party.getPlayer().levelUp(Integer.parseInt(pointStr));
                        pointsLeft--;
                    }
                    else if (pointStr.matches("5"))
                        help(2);
                    break;
                }

            }
            party.getPlayer().levelUp();
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

