package World.Item;

public enum SpellList {

    SHIELD_BASH(1),
    HUNKER_DOWN(2),
    CHEAP_SHOT(4),
    THROWING_KNIVES(5),
    HEAL(7),
    FIREBALL(8);

    private int spell;

    SpellList(int spell)
    {
        this.spell = spell;
    }

    public int role()
    {
        return this.spell;
    }

    public static String toString(int spell) {
        switch(spell)
        {
            case 1: return "Shield Bash";
            case 2: return "Hunker Down";
            case 4: return "Cheap Shot";
            case 5: return "Throwing Knives";
            case 7: return "Heal";
            case 8: return "Fireball";
        }
        return null;
    }
}
