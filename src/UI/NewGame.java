package UI;

import Helper.Creation;
import Party.Members.Class;
import Party.Members.*;

public class NewGame extends Game{

    public NewGame()
    {
        world.getParty().addMembers(Creation.createNewParty(getInfo()));
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
                    int option = checkValidInput();
                    if (option > 0 && option < 3) {
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
            int option = checkValidInput();
            if(option == 1)
                return true;
            else if(option == 2)
                return false;
        }
    }
}
