package World.Character;

public enum Class {

    WARRIOR(1),
    ROUGE(2),
    MAGE(3);

    private int role;

    Class(int role)
    {
        this.role = role;
    }

    public int role()
    {
        return this.role;
    }

    public static String toString(int role) {
        switch(role)
        {
            case 1: return "Warrior";
            case 2: return "Rogue";
            case 3: return "Mage";
        }
        return null;
    }
}
