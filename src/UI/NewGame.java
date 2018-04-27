package UI;

import Party.Item.Armour;
import Party.Item.Restriction;
import Party.Item.Weapon;
import Party.Members.Class;
import Party.Members.*;

public class NewGame extends DungeonCrawl{

    public NewGame()
    {
        party.addMember(getInfo());
        addOtherMembers();
        addBaseEquipment();
        for(PartyMember p : party)
            levelUp(p);
        startMessage();
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

    private void addOtherMembers()
    {
        if(party.getPlayer().getRole() == Class.WARRIOR.role())
        {
            party.addMember(new Rogue("Leliana"));
            party.addMember(new Mage("Luxanna"));
        }
        else if(party.getPlayer().getRole() == Class.ROUGE.role())
        {
            party.addMember(new Warrior("Marth"));
            party.addMember(new Mage("Vanille"));
        }
        else if(party.getPlayer().getRole() == Class.MAGE.role())
        {
            party.addMember(new Warrior("Ogma"));
            party.addMember(new Rogue("Rose"));
        }
    }

    private void addBaseEquipment()
    {
        for(PartyMember p : party)
        {
            if(p.getRole() == Class.WARRIOR.role())
            {
                p.equipBody(new Armour("Bronze Armour", Class.WARRIOR.role(), Restriction.BODY.equip(),1, 0, 0, 1, 10));
                p.equipRightArm(new Weapon("Bronze Sword", Class.WARRIOR.role(), Restriction.RIGHT_ARM.equip(),1, 0, 0, 0, 50));
                p.equipLeftArm(new Armour("Bronze Shield", Class.WARRIOR.role(), Restriction.LEFT_ARM.equip(),0, 0, 0, 1, 2));
            }
            else if(p.getRole() == Class.ROUGE.role())
            {
                p.equipBody(new Armour("Worn Cloak", Class.ROUGE.role(), Restriction.BODY.equip(),1, 0, 0, 1, 5));
                p.equipRightArm(new Weapon("Bronze Dagger", Class.ROUGE.role(), Restriction.ARM.equip(),0, 1, 0, 0, 30));
                p.equipLeftArm(new Weapon("Bronze Dagger", Class.ROUGE.role(), Restriction.ARM.equip(),0, 1, 0, 0, 30));
            }
            else if(p.getRole() == Class.MAGE.role())
            {
                p.equipBody(new Armour("Old Tunic", Class.MAGE.role(), Restriction.BODY.equip(),0, 0, 1, 1, 5));
                p.equipRightArm(new Weapon("Old Staff", Class.MAGE.role(), Restriction.RIGHT_ARM.equip(),0, 0, 2, 0, 60));
            }
        }
        party.addEquipment(new Armour("Assassin Mask", Class.ROUGE.role(), Restriction.HEAD.equip(), 0, 2, 0, 1, 5));
        party.addEquipment(new Weapon("Assassin's Dagger", Class.ROUGE.role(), Restriction.ARM.equip(), 0, 5, 0, 0, 50));
        party.addEquipment(new Weapon("Long Sword", Class.WARRIOR.role(), Restriction.RIGHT_ARM.equip(), 5, 0, 0, 0, 65));
    }

    private void startMessage()
    {
        ui.clearMainText();
        ui.appendMain("Welcome to the world of " + world.getName() + ".\n");
        ui.appendMain("    You begin in the town of " + world.getCurrentTown().getName() + ". This is the town where all adventurers begin and where you will make a legacy. ");
        ui.appendMain("On the main town menu, there will be several options. You can go exploring dungeons and discover riches, visit the shop, talk to townsfolk, and visit the inn.\n");
        waitForNullInput();
    }
}
