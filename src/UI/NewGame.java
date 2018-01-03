package UI;

import Party.Item.Armour;
import Party.Item.Weapon;
import Party.Item.Restriction;
import Party.Members.Class;
import Party.Members.*;
import World.Town;

public class NewGame extends DungeonCrawl{

    public NewGame()
    {
        ui = new UIFrame();
        party = new Party();
        player = getInfo();
        party.addMember(player);
        addOtherMembers();
        for(PartyMember p : party)
            levelUp(p);
        addBaseEquipment();
        party.setLocation(new Town("Kingsland"));
        town = party.getLocation();
        startMessage();
    }

    private PartyMember getInfo()
    {
        String name;
        int role;

        while(true)
        {
            ui.appendMain("What is your name?\n");

            while (true)
            {
                waitForInput();
                name = ui.getTextInput();
                if (name.chars().allMatch(Character::isLetter))
                    break;
                else
                    ui.appendMain("Invalid name.\n");
            }
            ui.appendMain("What is your class:\n");
            ui.appendMain("1) Warrior  2) Rouge  3) Mage\n");
            ui.appendMain("4) Help\n");
            while (true)
            {
                waitForInput();
                String roleStr = ui.getTextInput();
                if (roleStr.matches("[1-3]"))
                {
                    role = Integer.parseInt(roleStr);
                    break;
                }
                else if (roleStr.matches("4"))
                    Help.helpClass();
            }
            if (confirm(name, role))
                break;
        }
        switch (role)
        {
            case 1: return new Warrior(name);
            case 2: return new Rouge(name);
            case 3: return new Mage(name);
            default: return null;
        }
    }


    private boolean confirm(String name, int role)
    {
        ui.appendMain("Are you sure your name is " + name + " , and your class is " + Class.toString(role) + ":\n");
        ui.appendMain("1) Yes   2) No\n");
        while(true)
        {
            waitForInput();
            String sure = ui.getTextInput();
            ui.clearMainText();
            if(sure.equals("1"))
                return true;
            else if(sure.equals("2"))
                return false;
        }
    }

    private void addOtherMembers()
    {
        if(player.getRole() == Class.WARRIOR.role())
        {
            party.addMember(new Rouge("Katarina"));
            party.addMember(new Mage("Lux"));
        }
        else if(player.getRole() == Class.ROUGE.role())
        {
            party.addMember(new Warrior("Leona"));
            party.addMember(new Mage("Syndra"));
        }
        else if(player.getRole() == Class.MAGE.role())
        {
            party.addMember(new Warrior("Leona"));
            party.addMember(new Rouge("Katarina"));
        }
    }

    private void addBaseEquipment()
    {
        for(PartyMember p : party)
        {
            if(p.getRole() == Class.WARRIOR.role())
            {
                p.equipBody(new Armour("Bronze Armour", Class.WARRIOR.role(), Restriction.BODY.equip(),1, 0, 0, 1, 60));
                p.equipRightArm(new Weapon("Bronze Sword", Class.WARRIOR.role(), Restriction.RIGHT_ARM.equip(),1, 0, 0, 0, 500));
                p.equipLeftArm(new Armour("Bronze Shield", Class.WARRIOR.role(), Restriction.LEFT_ARM.equip(),0, 0, 0, 1, 20));
            }
            else if(p.getRole() == Class.ROUGE.role())
            {
                p.equipBody(new Armour("Worn Cloak", Class.ROUGE.role(), Restriction.BODY.equip(),1, 0, 0, 1, 50));
                p.equipRightArm(new Weapon("Bronze Dagger", Class.ROUGE.role(), Restriction.ARM.equip(),0, 1, 0, 0, 400));
                p.equipLeftArm(new Weapon("Bronze Dagger", Class.ROUGE.role(), Restriction.ARM.equip(),0, 1, 0, 0, 40));
            }
            else if(p.getRole() == Class.MAGE.role())
            {
                p.equipBody(new Armour("Old Tunic", Class.MAGE.role(), Restriction.BODY.equip(),0, 0, 1, 1, 40));
                p.equipRightArm(new Weapon("Old Staff", Class.MAGE.role(), Restriction.RIGHT_ARM.equip(),0, 0, 2, 0, 600));
            }
        }
        party.addEquipment(new Armour("Assassin Mask", Class.ROUGE.role(), Restriction.HEAD.equip(), 0, 2, 0, 1, 20));
        party.addEquipment(new Weapon("Assassin's Dagger", Class.ROUGE.role(), Restriction.ARM.equip(), 0, 5, 0, 0, 50));
    }

    private void startMessage()
    {
        ui.clearMainText();
        ui.appendMain("Welcome to the world of _______\n");
        ui.appendMain("    You begin in the town of Kingsland. This is the town where all adventurers begin and where you will make a legacy. ");
        ui.appendMain("On the main town menu, there will be several options. You can go exploring dungeons and discover riches, visit the shop, talk to townsfolk, and visit the inn.\n");
        waitForNullInput();
        ui.clearMainText();
    }
}
