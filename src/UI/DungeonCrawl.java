package UI;

import Party.Members.Party;
import Party.Members.PartyMember;
import World.Dungeon;
import World.Enemy;
import World.Location;

public class DungeonCrawl {
    protected static Party party;
    protected static PartyMember player;
    protected static UIFrame ui;
    protected static Location town;

    public static void main(String[] args)
    {
        new NewGame();
        new MainMenu();
    }

    protected static void updateInformation()
    {
        ui.clearPartyInfo();
        ui.clearOtherInfo();
        ui.appendOther("Location: " + party.getLocation().getName() + "\n");
        updatePartyInfo();
        if(!party.getLocation().isTown())
            updateEnemyInformation();
    }

    private static void updatePartyInfo()
    {
        for(PartyMember p : party)
        {
            String basic = String.format("%-6s%-13s%-8s", "L." + p.getLevel(), p.getName(), p.roleToString());
            ui.appendParty(basic + "\n");
            String battle = String.format("%3s%9s%7s%4s", "HP:", p.getHP() + "/" + p.getMaxHP(), "EXP:", p.getExpForNextLvlRelative());
            ui.appendParty(battle + "\n");
        }
    }

    private static void updateEnemyInformation()
    {
        Dungeon dungeon = (Dungeon)party.getLocation();
        for(Enemy e : dungeon)
        {
            String info = e.getName() + "\t";
            info += e.getHP();
            info += "/" + e.getMaxHP();
            ui.appendOther(info + "\n");
        }
    }



    protected static void levelUp(PartyMember member)
    {
        int pointsLeft = PartyMember.LEVEL_UP_POINTS;
        if(member == player)
        {
            while (pointsLeft > 0) {
                ui.clearMainText();
                ui.appendMain("You have leveled up!\n");
                ui.appendMain("You have " + pointsLeft + " skill points left:\n");
                ui.appendMain(String.format("%-16s%s", "1) Strength", player.getBaseStrength() + "\n"));
                ui.appendMain(String.format("%-16s%s", "2) Dexterity", player.getBaseDexterity() + "\n"));
                ui.appendMain(String.format("%-16s%s", "3) Willpower", player.getBaseWillpower() + "\n"));
                ui.appendMain(String.format("%-16s%s", "4) Constitution", player.getBaseConstitution() + "\n"));
                ui.appendMain("5) Help\n");
                while (true) {
                    waitForInput();
                    String pointStr = ui.getTextInput();
                    if (pointStr.matches("[1-4]")) {
                        player.levelUp(Integer.parseInt(pointStr));
                        pointsLeft--;
                    }
                    else if (pointStr.matches("5"))
                        Help.help(2);
                    break;
                }

            }
            player.levelUp();
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

