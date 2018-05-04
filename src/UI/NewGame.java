package UI;

import Helper.Creation;
import World.Character.PartyMember;
import Helper.Enum.ClassList;

public class NewGame extends Game{

    public NewGame()
    {
        world.getParty().addMembers(Creation.createNewParty(getInfo()));
        world.getParty().addGold(10);
        for(PartyMember p : world.getParty())
            levelUp(p);
        printStory(1);
    }

    private PartyMember getInfo()
    {
        String name;
        int role;

        while(true)
        {
            ui.clearThenAppendMain("What is your name(Max. 10 characters)?\n");

            while (true)
            {
                waitForInput();
                name = ui.getTextInput();
                if (!name.isEmpty() && name.length() <= 10 && name.chars().allMatch(Character::isLetter))
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
                    int option = checkValidInput();
                    if (option > 0 && option < 4) {
                        role = option;
                        break role;
                    } else if (option == 4) {
                        printHelp(1);
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
            case 1: return Creation.createNewWarrior(name);
            case 2: return Creation.createNewRogue(name);
            case 3: return Creation.createNewMage(name);
            default: return null;
        }
    }


    private boolean confirm(String name, int role)
    {
        ui.clearThenAppendMain("Are you sure your name is " + name + " , and your class is " + ClassList.toString(role) + ":\n");
        ui.appendMain("1) Yes   2) No\n");
        while(true)
        {
            int option = checkValidInput();
            if(option == 1)
                return true;
            else if(option == 2)
                return false;
        }
    }
}
