package Helper.Enum;

public enum ClassList {

    WARRIOR(1),
    ROGUE(2),
    MAGE(3),
    ALL(4);

    private int role;

    ClassList(int role)
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
            case 4: return "All";
        }
        return null;
    }
}
