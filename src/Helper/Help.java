package Helper;

public class Help {

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
        str += "Dexterity    - Increased damage with attacks\n";
        str += "Willpower    - Increased damage with staves and increased mana\n";
        str += "Constitution - Increased health\n";
        return str;
    }

    private static String helpTownMainMenu()
    {
        String str;
        str =  "Dungeon   - Go and explore nearby dungeons\n";
        str += "Shop      - Visit the local shop to buy and sell items\n";
        str += "Inn       - Restore health and stamina/mana and save your game\n";
        str += "Inventory - View your inventory and equip and unequip items\n";
        str += "Travel    - Travel to another town\n";
        return str;
    }

    private static String helpBattle()
    {
        String str;
        str =  "Attack - Attack an enemy with your main weapon\n";
        str += "Spell  - Use a spell to do damage or heal your allies\n";
        str += "Run    - Run back to town (chance to fail)\n";
        return str;
    }
}
