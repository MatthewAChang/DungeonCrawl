package Helper;

public class Help{
    public static String help(int help) {
        String str;
        switch(help) {
            case 1:
                str = helpClass();
                break;
            case 2:
                str = helpLevelUp();
                break;
            case 3:
                str = helpTownMainMenu();
                break;
            case 4:
                str = helpBattle();
                break;
            default:
                str = null;
        }
        return str;
    }

    private static String helpClass()
    {
        String str;
        str =  "Warrior - Tank/DPS\n";
        str += "Rogue   - DPS\n";
        str += "Mage    - DPS/Heal\n";
        return str;
    }

    private static String helpLevelUp()
    {
        String str;
        str =  "Strength     - Increased damage with attacks\n";
        str += "Dexterity    - Increased critical chance and chance to dodge\n";
        str += "Willpower    - Increased damage with staves and spells and increased mana\n";
        str += "Constitution - Increased health\n";
        return str;
    }

    private static String helpTownMainMenu()
    {
        String str;
        str =  "Dungeon   - Go and explore nearby dungeons\n";
        str += "Shop      - visit the local shop to buy and sell items\n";
        str += "Talk      - talk to townsfolk\n";
        str += "Inn       - restore health and stamina/mana and save your game\n";
        str += "Inventory - view your inventory and equip and unequip items\n";
        str += "Travel    - travel to another town\n";
        return str;
    }

    private static String helpBattle()
    {
        String str;
        str =  "Attack - attack an enemy with your main weapon\n";
        str += "Spell  - use a spell to do damage or heal your allies\n";
        str += "Item   - use an item to buff yourself or your allies\n";
        str += "Run    - run back to town (chance to fail)\n";
        return str;
    }
}
