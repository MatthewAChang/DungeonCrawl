package UI;

import Party.Item.Armour;
import Party.Item.Equipment;
import Party.Item.Weapon;
import Party.Members.PartyMember;

public class MainMenu extends DungeonCrawl {

    public MainMenu()
    {
        townMainMenu();
    }

    private void townMainMenu()
    {
        while(true)
        {
            updateInformation();
            ui.clearMainText();
            ui.appendMain("Main Menu:\n");
            ui.appendMain("1) Dungeon\n");
            ui.appendMain("2) Shop\n");
            ui.appendMain("3) Talk\n");
            ui.appendMain("4) Inn\n");
            ui.appendMain("5) Inventory\n");
            ui.appendMain("6) Travel\n");
            ui.appendMain("7) Stats\n");
            ui.appendMain("8) Help\n");
            while(true)
            {
                waitForInput();
                String optionStr = ui.getTextInput();
                if(optionStr.matches("[1-7]"))
                {
                    int option = Integer.parseInt(optionStr);
                    ui.clearMainText();
                    switch (option)
                    {
                        case 1:
                            new Battle();
                            break;
                        case 4:
                            inn();
                            break;
                        case 5:
                            inventory();
                            break;
                        case 7:
                            stats();
                            break;

                    }
                    break;
                }
                else if(optionStr.matches("8"))
                    Help.helpTownMainMenu();

            }
        }
    }

    private void inn()
    {
        ui.appendMain("Inn Keeper:\n");
        ui.appendMain("Would you like to stay the night?\n");
        ui.appendMain("1) Yes  2) No\n");
        while(true)
        {
            waitForInput();
            String answer = ui.getTextInput();
            if(answer.matches("1")) {
                for (PartyMember p : party)
                    p.resetHP();
                ui.appendMain(".................\n");
                waitForNullInput();
                break;
            }
            else if(answer.matches("2"))
            {
                break;
            }
        }
        ui.appendMain("Thank you. Please come again.\n");
        waitForNullInput();
    }

    private void inventory()
    {
        while(true)
        {
            partyView("Inventory:\n");
            waitForNullInput();
            String answer = ui.getTextInput();
            ui.clearMainText();
            if(answer.matches("1"))
            {
                equip(true);
            }
            else if(answer.matches("2"))
            {
                equip(false);
            }
            else if(answer.matches("3"))
            {
                ui.appendMain(String.format("%24s%4s%4s%5s", "Str", "Dex", "Wil", "Con\n"));
                for(Equipment e: party.getInventory())
                {
                    ui.appendMain(String.format("%20s%4s%4s%4s%5s", e.getName() + ":", e.getStrength(), e.getDexterity(), e.getWillpower(), e.getConstitution() + "\n"));
                }
                waitForNullInput();
            }
            else if(answer.matches("4"))
                break;
            ui.clearMainText();
        }
    }

    private boolean equip(boolean equip)
    {
        int count = 1;

        ui.appendMain(String.format("%24s%4s%4s%5s", "Str", "Dex", "Wil", "Con\n"));
        for(Equipment e: party.getInventory())
        {
            ui.appendMain(String.format("%3s%17s%4s%4s%4s%5s", count++ + ") ", e.getName(), e.getStrength(), e.getDexterity(), e.getWillpower(), e.getConstitution() + "\n"));
        }
        ui.appendMain(count + ") Back\n");
        return false;
    }

    private void stats()
    {
        partyView:
        while(true) {
            partyView("Who to view:\n");
            while (true) {
                waitForInput();
                String optionStr = ui.getTextInput();
                if (optionStr.matches("[1-3]")) {
                    int option = Integer.parseInt(optionStr);
                    PartyMember member = party.getPartyMember(option - 1);
                    ui.clearMainText();
                    ui.appendMain(member.getName() + "\n");
                    ui.appendMain(String.format("%24s%4s%4s%5s", "Str", "Dex", "Wil", "Con\n"));
                    ui.appendMain(String.format("%20s%4s%4s%4s%5s", "Base:", member.getBaseStrength(), member.getBaseDexterity(), member.getBaseWillpower(), member.getBaseConstitution() + "\n"));
                    if (member.getHead() != null) {
                        Armour head = member.getHead();
                        ui.appendMain(String.format("%20s%4s%4s%4s%5s", head.getName(), head.getStrength(), head.getDexterity(), head.getWillpower(), head.getConstitution() + "\n"));
                    }
                    if (member.getBody() != null) {
                        Armour body = member.getBody();
                        ui.appendMain(String.format("%20s%4s%4s%4s%5s", body.getName() + ":", body.getStrength(), body.getDexterity(), body.getWillpower(), body.getConstitution() + "\n"));
                    }
                    if (member.getRightArm() != null) {
                        Weapon rightArm = member.getRightArm();
                        ui.appendMain(String.format("%20s%4s%4s%4s%5s", rightArm.getName() + ":", rightArm.getStrength(), rightArm.getDexterity(), rightArm.getWillpower(), rightArm.getConstitution() + "\n"));
                    }
                    if (member.getLeftArm() != null) {
                        Equipment leftArm = member.getLeftArm();
                        ui.appendMain(String.format("%20s%4s%4s%4s%5s", leftArm.getName() + ":", leftArm.getStrength(), leftArm.getDexterity(), leftArm.getWillpower(), leftArm.getConstitution() + "\n"));
                    }
                    ui.appendMain(String.format("%20s%4s%4s%4s%5s", "Total:", member.getStrength(), member.getDexterity(), member.getWillpower(), member.getConstitution() + "\n"));
                    waitForNullInput();
                    ui.clearMainText();
                    break;
                } else if (optionStr.matches("4"))
                    break partyView;
            }
        }
    }

    private void partyView(String statement)
    {
        ui.appendMain(statement);
        int count = 1;
        for (PartyMember p : party)
            ui.appendMain(count++ + ". " + p.getName() + "  ");
        ui.appendMain("\n");
        ui.appendMain(count + ". Back\n");
    }
}
