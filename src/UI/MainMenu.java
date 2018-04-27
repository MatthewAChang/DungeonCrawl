package UI;

import Party.Item.Armour;
import Party.Item.Equipment;
import Party.Item.Restriction;
import Party.Item.Weapon;
import Party.Members.Class;
import Party.Members.PartyMember;
import World.Dungeon;
import World.Town;

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
                if(optionStr.matches("[1-8]"))
                {
                    int option = Integer.parseInt(optionStr);
                    ui.clearMainText();
                    switch (option)
                    {
                        case 1:
                            battle();
                            break;
                        case 2:
                            ToBeCreated();
                            break;
                        case 3:
                            ToBeCreated();
                            break;
                        case 4:
                            inn();
                            break;
                        case 5:
                            inventory();
                            break;
                        case 6:
                            travel();
                            break;
                        case 7:
                            stats();
                            break;
                        case 8:
                            help(3);
                    }
                    break;
                }
            }
        }
    }

    private void ToBeCreated()
    {
        ui.clearThenAppendMain("Not implemented yet. Please wait.\n");
        waitForNullInput();
    }

    private void battle() {
        ui.appendMain("Where would you like to go:\n");
        int i = 1;
        for(Dungeon d: world.getCurrentTown()) {
            ui.appendMain(i + ") " + d.getName() + "\n");
            i++;
        }
        ui.appendMain(i + ") Back\n");
        while(true) {
            waitForInput();
            String answer = ui.getTextInput();
            if(answer.matches("[0-9]+")) {
                int option = Integer.parseInt(answer);
                if(option > 0 && option <= world.getCurrentTown().getDungeons().size()) {
                    new Battle(world.getCurrentTown().getDungeon(option - 1));
                    break;
                }
                else if(option == i) {
                    break;
                }
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
                    p.setStats();
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
        inventory:
        while(true)
        {
            ui.clearMainText();
            int count = 1;
            ui.appendMain(String.format("%24s%4s%4s%5s\n", "Str", "Dex", "Wil", "Con"));
            for(Equipment e: party.getInventory())
            {
                ui.appendMain(String.format("%2s) %17s%4s%4s%4s%5s\n", count++, e.getName(), e.getStrength(), e.getDexterity(), e.getWillpower(), e.getConstitution()));
            }
            ui.appendMain(String.format("%2s) Back\n", count));
            while(true) {
                waitForInput();
                String optionStr = ui.getTextInput();
                if(optionStr.matches("[0-9]+")) {
                    int option = Integer.parseInt(optionStr);
                    if(option == count) break inventory;
                    else if(option < count && option > 0) {
                        equip(option - 1);
                        break;
                    }
                }
            }
        }
    }

    private void equip(int equipment)
    {
        partyView("Who to equip to:\n");
        while(true) {
            waitForInput();
            String optionStr = ui.getTextInput();
            if (optionStr.matches("[1-3]")) {
                int option = Integer.parseInt(optionStr);
                equip(option - 1, equipment);
                break;
            } else if (optionStr.matches("4"))
                break;
        }
    }

    private void equip(int member, int equipment) {
        PartyMember p = party.getPartyMember(member);
        Equipment e = party.getEquipment(equipment);

        if(e.getRole() != p.getRole()) {
            ui.appendMain("Can only equip to " + Class.toString(e.getRole()) + "\n");
            waitForNullInput();
            return;
        }

        Equipment old = null;

        if(e.getEquip() == Restriction.HEAD.equip())
            p.equipHead((Armour)e);
        else if(e.getEquip() == Restriction.BODY.equip())
            p.equipBody((Armour)e);
        else if(e.getEquip() == Restriction.RIGHT_ARM.equip())
            p.equipRightArm((Weapon) e);
        else if(e.getEquip() == Restriction.LEFT_ARM.equip())
            p.equipLeftArm(e);
        else if(e.getEquip() == Restriction.ARM.equip())
            if(arm()) p.equipLeftArm(e);
            else p.equipRightArm((Weapon)e);

        party.removeEquipment(equipment);
        if(old != null)
            party.addEquipment(old);
        updateInformation();
    }

    private boolean arm() {
        while(true) {
            ui.appendMain("Which arm to equip to:\n");
            ui.appendMain("1) Left\n");
            ui.appendMain("2) Right\n");
            while(true) {
                waitForInput();
                String option = ui.getTextInput();
                if(option.matches("1"))
                    return true;
                else if(option.matches("2"))
                    return false;
            }
        }
    }

    private void travel() {
        travel:
        while(true) {
            ui.appendMain("Where would you like to travel:\n");
            int count = 1;
            for(Town t: world)
            {
                ui.appendMain(count++ + ") " + t.getName() + "\n");
            }
            ui.appendMain(count + ") Back\n");
            while(true) {
                waitForInput();
                String optionStr = ui.getTextInput();
                if(optionStr.matches("[0-9]+")) {
                    int option = Integer.parseInt(optionStr);
                    if(option == count) break travel;
                    else if(option < count && option > 0) {
                        world.setCurrentTown(option - 1);
                        updateInformation();
                        break travel;
                    }
                }
            }
        }
    }

    private void stats() {
        stats:
        while(true) {
            partyView("Who to view:\n");
            while (true) {
                waitForInput();
                String optionStr = ui.getTextInput();
                if (optionStr.matches("[1-3]")) {
                    int option = Integer.parseInt(optionStr);
                    PartyMember member = party.getPartyMember(option - 1);
                    ui.clearMainText();
                    ui.appendMain(String.format(" %s:  %s\n", member.getName(), Class.toString(member.getRole())));
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
                    break stats;
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
