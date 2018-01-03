package Party.Item;

public enum Restriction {

    HEAD(1),
    BODY(2),
    RIGHT_ARM(3),
    LEFT_ARM(4),
    ARM(5);

    private int equip;

    Restriction(int equip)
    {
        this.equip = equip;
    }

    public int equip()
    {
        return this.equip;
    }

    public static String toString(int equip) {
        switch(equip)
        {
            case 1: return "head";
            case 2: return "body";
            case 3: return "right arm";
            case 4: return "left arm";
        }
        return null;
    }
}
