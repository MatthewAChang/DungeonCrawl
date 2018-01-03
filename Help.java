package UI;

public class Help extends DungeonCrawl {

    private boolean help;

    public void setTutorial(boolean help)
    {
        help = false;
    }

    public boolean isHelp() {
        return help;
    }

    protected static void helpClass()
    {
        ui.appendMain("Warrior - Tank/DPS\n");
        ui.appendMain("Rouge   - DPS\n");
        ui.appendMain("Mage    - DPS/Heal\n");
    }

    protected static void helpLevelUp()
    {
        ui.appendMain("Strength     - Increased damage with attacks\n");
        ui.appendMain("Dexterity    - Increased critical chance and chance to dodge\n");
        ui.appendMain("Willpower    - Increased damage with staves and spells\n");
        ui.appendMain("Constitution - Increased health\n");
    }

    protected static void helpTownMainMenu()
    {
        ui.appendMain("Dungeon   - Go and explore nearby dungeons\n");
        ui.appendMain("Shop      - visit the local shop to buy and sell items\n");
        ui.appendMain("Talk      - talk to townsfolk\n");
        ui.appendMain("Inn       - restore health and stamina/mana and save your game\n");
        ui.appendMain("Inventory - view your inventory and equip and unequip items\n");
        ui.appendMain("Travel    - travel to another town\n");
    }

    protected static void helpBattle()
    {
        ui.appendMain("Attack - attack an enemy with your main weapon\n");
        ui.appendMain("Spell  - use a spell to do damage or heal your allies\n");
        ui.appendMain("Item   - use an item to buff yourself or your allies\n");
        ui.appendMain("Run    - run back to town (chance to fail)\n");
    }
}
