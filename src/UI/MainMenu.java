package UI;

import World.Character.PartyMember;
import Helper.Enum.ClassList;
import World.Item.Armour;
import World.Item.Equipment;
import Helper.Enum.Restriction;
import World.Item.Weapon;
import World.World.Dungeon;
import World.World.Town;

public class MainMenu extends Game {

    public MainMenu()
    {
        townMainMenu();
    }

    private void townMainMenu() {
        while(true) {
            updateInformation();
            ui.clearMainText();
            ui.appendMain("Main Menu:\n");
            ui.appendMain("1) Dungeon\n");
            ui.appendMain("2) Shop\n");
            ui.appendMain("3) Inn\n");
            ui.appendMain("4) Inventory\n");
            ui.appendMain("5) Travel\n");
            ui.appendMain("6) Stats\n");
            ui.appendMain("7) Help\n");
            while(true) {
                int option = checkValidInput();
                if(option > 0 && option < 8)
                {
                    ui.clearMainText();
                    switch (option)
                    {
                        case 1:
                            battle();
                            break;
                        case 2:
                            shop();
                            break;
                        case 3:
                            inn();
                            break;
                        case 4:
                            inventory();
                            break;
                        case 5:
                            travel();
                            break;
                        case 6:
                            stats();
                            break;
                        case 7:
                            printHelp(3);
                            break;
                    }
                    break;
                }
            }
        }
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
            int option = checkValidInput();
            if(option > 0 && option < i) {
                new Battle(world.getCurrentTown().getDungeon(option - 1));
                break;
            } else if(option == i) {
                break;
            }
        }
    }

    private void shop() {
        shop:
        while(true) {
            ui.clearThenAppendMain("Shop Keeper:\n");
            ui.appendMain("Would you like to buy or sell?\n");
            ui.appendMain("1) Buy  2) Sell 3) Back\n");
            while (true) {
                int option = checkValidInput();
                if (option == 1) {
                    buy();
                    break;
                } else if (option == 2) {
                    sell();
                    break;
                } else if (option == 3) {
                    break shop;
                }
            }
        }
    }

    private void buy() {
        buy:
        while(true) {
            ui.clearThenAppendMain("What would you like to buy:\n");
            int i = 1;
            ui.appendMain(String.format("%28s%4s%4s%4s%4s%4s%4s\n", "Str", "Dex", "Wil", "Con", "Dmg", "Arm", "Cst"));
            for (Equipment e : world.getCurrentTown().getShop()) {
                ui.appendMain(String.format("%2s) %20s%4s%4s%4s%4s", i++, e.getName(), e.getStrength(), e.getDexterity(), e.getWillpower(), e.getConstitution()));
                if (e.getEquip() == Restriction.BODY.equip() || e.getEquip() == Restriction.HEAD.equip()) {
                    Armour a = (Armour) e;
                    ui.appendMain(String.format("%8s", a.getArmour()));
                } else {
                    Weapon w = (Weapon) e;
                    ui.appendMain(String.format("%4s%4s", w.getDamage(), ""));
                }
                ui.appendMain(String.format("%4s\n", e.getBuyValue()));
            }
            ui.appendMain(String.format("%2s) %20s\n", i, "Back"));
            while (true) {
                int option = checkValidInput();
                if (option > 0 && option < i) {
                    if(world.getParty().minusGold(world.getCurrentTown().getEquipment(option - 1).getBuyValue())) {
                        world.getParty().addEquipment(world.getCurrentTown().getEquipment(option - 1));
                        ui.clearThenAppendMain("You bought a " + world.getCurrentTown().getEquipment(option - 1).getName() + ".\n");

                    } else {
                        ui.clearThenAppendMain("You cannot afford this item.\n");
                    }
                    waitForNullInput();
                    updateInformation();
                    break;
                } else if (option == i) {
                    break buy;
                }
            }
        }
    }

    private void sell() {
        sell:
        while(true) {
            ui.clearThenAppendMain("What would you like to sell:\n");
            int i = printInventory();
            while (true) {
                int option = checkValidInput();
                if (option > 0 && option < i) {
                    world.getParty().addGold(world.getParty().getEquipment(option - 1).getSellValue());
                    ui.clearThenAppendMain("You sold a " + world.getParty().getEquipment(option - 1).getName() + ".\n");
                    world.getParty().removeEquipment(option - 1);
                    waitForNullInput();
                    updateInformation();
                    break;
                } else if (option == i) {
                    break sell;
                }
            }
        }
    }

    private void inn()
    {
        ui.appendMain("Inn Keeper:\n");
        ui.appendMain("Would you like to stay the night(10G)?\n");
        ui.appendMain("1) Yes  2) No\n");
        while(true)
        {
            int option = checkValidInput();
            if(option == 1) {
                if(world.getParty().minusGold(10))
                    for (PartyMember p : world.getParty())
                        p.resetStats();
                else {
                    ui.appendMain("You do not have enough gold.\n");
                    waitForNullInput();
                }
                ui.appendMain(".................\n");
                waitForNullInput();
                break;
            } else if(option == 2) {
                break;
            }
        }
        ui.appendMain("Thank you. Please come again.\n");
        waitForNullInput();
    }

    private void inventory()
    {
        inventory:
        while(true) {
            ui.clearMainText();
            int i = printInventory();
            while(true) {
                int option = checkValidInput();
                if(option > 0 && option < i) {
                    equip(option - 1);
                    break;
                } else if(option == i) {
                    break inventory;
                }
            }
        }
    }

    private int printInventory() {
        int i = 1;
        ui.appendMain(String.format("%28s%4s%4s%4s%4s%4s%4s\n", "Str", "Dex", "Wil", "Con", "Dmg", "Arm", "Cst"));
        for(Equipment e: world.getParty().getInventory())
        {
            ui.appendMain(String.format("%2s) %20s%4s%4s%4s%4s", i++, e.getName(), e.getStrength(), e.getDexterity(), e.getWillpower(), e.getConstitution()));
            if(e.getEquip() == Restriction.BODY.equip() || e.getEquip() == Restriction.HEAD.equip()) {
                Armour a = (Armour) e;
                ui.appendMain(String.format("%8s", a.getArmour()));
            } else {
                Weapon w = (Weapon) e;
                ui.appendMain(String.format("%4s%4s", w.getDamage(), ""));
            }
            ui.appendMain(String.format("%4s\n", e.getSellValue()));
        }
        ui.appendMain(String.format("%2s) %20s:\n", i, "Back"));
        return i;
    }

    private void equip(int equipment)
    {
        partyView("Who to equip to:\n");
        while(true) {
            int option = checkValidInput();
            if (option > 0 && option <= world.getParty().getPartySize()) {
                equip(option - 1, equipment);
                break;
            } else if (option == world.getParty().getPartySize() + 1)
                break;
        }
    }

    private void equip(int member, int equipment) {
        PartyMember p = world.getParty().getPartyMember(member);
        Equipment e = world.getParty().getEquipment(equipment);

        if(e.getRole() != ClassList.ALL.role() && e.getRole() != p.getRole()) {
            ui.appendMain("Can only equip to " + ClassList.toString(e.getRole()) + "\n");
            waitForNullInput();
            return;
        }

        Equipment old = null;

        if(e.getEquip() == Restriction.HEAD.equip())
            old = p.equipHead((Armour)e);
        else if(e.getEquip() == Restriction.BODY.equip())
            old = p.equipBody((Armour)e);
        else if(e.getEquip() == Restriction.RIGHT_ARM.equip())
            old = p.equipRightArm((Weapon) e);
        else if(e.getEquip() == Restriction.LEFT_ARM.equip())
            old = p.equipLeftArm(e);
        else if(e.getEquip() == Restriction.ARM.equip())
            if(arm())
                old = p.equipLeftArm(e);
            else
                old = p.equipRightArm((Weapon)e);

        world.getParty().removeEquipment(equipment);
        if(old != null)
            world.getParty().addEquipment(old);
        updateInformation();
    }

    private boolean arm() {
        while(true) {
            ui.appendMain("Which arm to equip to:\n");
            ui.appendMain("1) Left\n");
            ui.appendMain("2) Right\n");
            while(true) {
                int option = checkValidInput();
                if(option == 1)
                    return true;
                else if(option == 2)
                    return false;
            }
        }
    }

    private void travel() {
        travel:
        while(true) {
            ui.appendMain("Where would you like to travel:\n");
            int i = 1;
            for(Town t: world)
            {
                ui.appendMain(i++ + ") " + t.getName() + "\n");
            }
            ui.appendMain(i + ") Back\n");
            while(true) {
                int option = checkValidInput();
                if(option > 0 && option < i) {
                    world.setCurrentTown(option - 1);
                    updateInformation();
                    break travel;
                } else if(option == i)
                    break travel;
            }
        }
    }

    private void stats() {
        stats:
        while(true) {
            partyView("Who to view:\n");
            while (true) {
                int option = checkValidInput();
                if (option > 0 && option <= world.getParty().getPartySize()) {
                    PartyMember member = world.getParty().getPartyMember(option - 1);
                    ui.clearMainText();
                    ui.appendMain(String.format(" %s: %-9sTotal EXP: %s\n", member.getName(), ClassList.toString(member.getRole()), member.getCurrentExp()));
                    ui.appendMain(String.format("%25s%4s%4s%4s%4s%4s\n", "Str", "Dex", "Wil", "Con", "Dmg", "Arm"));
                    ui.appendMain(String.format("%20s%4s%4s%4s%4s\n", "Base", member.getBaseStrength(), member.getBaseDexterity(), member.getBaseWillpower(), member.getBaseConstitution()));
                    if (member.getHead() != null) {
                        Armour head = member.getHead();
                        ui.appendMain(String.format("%20s%4s%4s%4s%4s%8s\n", head.getName(), head.getStrength(), head.getDexterity(), head.getWillpower(), head.getConstitution(), head.getArmour()));
                    }
                    if (member.getBody() != null) {
                        Armour body = member.getBody();
                        ui.appendMain(String.format("%20s%4s%4s%4s%4s%8s\n", body.getName(), body.getStrength(), body.getDexterity(), body.getWillpower(), body.getConstitution(), body.getArmour()));
                    }
                    if (member.getRightArm() != null) {
                        Weapon rightArm = member.getRightArm();
                        ui.appendMain(String.format("%20s%4s%4s%4s%4s%4s\n", rightArm.getName(), rightArm.getStrength(), rightArm.getDexterity(), rightArm.getWillpower(), rightArm.getConstitution(), rightArm.getDamage()));
                    }
                    if (member.getLeftArm() != null) {
                        Equipment leftArm = member.getLeftArm();
                        ui.appendMain(String.format("%20s%4s%4s%4s%4s", leftArm.getName(), leftArm.getStrength(), leftArm.getDexterity(), leftArm.getWillpower(), leftArm.getConstitution()));
                        if(leftArm.getRole() == ClassList.WARRIOR.role()) {
                            Armour a = (Armour)leftArm;
                            ui.appendMain(String.format("%8s\n", a.getArmour()));
                        } else if (leftArm.getRole() == ClassList.ROGUE.role()) {
                            Weapon w = (Weapon)leftArm;
                            ui.appendMain(String.format("%4s\n", w.getDamage()));
                        }
                    }
                    ui.appendMain(String.format("%20s%4s%4s%4s%4s%4s%4s\n", "Total", member.getStrength(), member.getDexterity(), member.getWillpower(), member.getConstitution(), member.getBaseDamage(), member.getArmour()));
                    waitForNullInput();
                    ui.clearMainText();
                    break;
                } else if (option == world.getParty().getPartySize() + 1)
                    break stats;
            }
        }
    }

    private void partyView(String statement)
    {
        ui.appendMain(statement);
        int count = 1;
        for (PartyMember p : world.getParty())
            ui.appendMain(count++ + ". " + p.getName() + "  ");
        ui.appendMain("\n");
        ui.appendMain(count + ". Back\n");
    }
}
