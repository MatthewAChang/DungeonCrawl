package UI;

import Helper.Creation;
import Party.Members.Class;
import Party.Members.*;

public class NewGame extends DungeonCrawl{

    public NewGame()
    {
        world.getParty().addMembers(Creation.createNewParty(getInfo()));
        for(PartyMember p : world.getParty())
            levelUp(p);
        story(1);
    }

    private PartyMember getInfo()
    {
        String name;
        int role = 0;

        while(true)
        {
            ui.clearThenAppendMain("What is your name?\n");

            while (true)
            {
                waitForInput();
                name = ui.getTextInput();
                if (!name.isEmpty() && name.chars().allMatch(Character::isLetter))
                    break;
                else
                    ui.appendMain("Invalid name.\n");
            }
            role:
            while (true) {
                ui.clearThenAppendMain("What is your class:\n");
                ui.appendMain("1) Warrior  2) Rogue  3) Mage\n");
                ui.appendMain("4) Help\n");
                while (true) {
                    waitForInput();
                    String roleStr = ui.getTextInput();
                    if (roleStr.matches("[1-3]")) {
                        role = Integer.parseInt(roleStr);
                        break role;
                    } else if (roleStr.matches("4")) {
                        help(1);
                        break;
                    }
                }
            }
            if (confirm(name, role))
                break;
            else ui.clearMainText();
        }
        switch (role)
        {
            case 1: return new Warrior(name);
            case 2: return new Rogue(name);
            case 3: return new Mage(name);
            default: return null;
        }
    }


    private boolean confirm(String name, int role)
    {
        ui.clearThenAppendMain("Are you sure your name is " + name + " , and your class is " + Class.toString(role) + ":\n");
        ui.appendMain("1) Yes   2) No\n");
        while(true)
        {
            waitForInput();
            String sure = ui.getTextInput();
            if(sure.equals("1"))
                return true;
            else if(sure.equals("2"))
                return false;
        }
    }
}
