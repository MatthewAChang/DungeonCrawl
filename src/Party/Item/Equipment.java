package Party.Item;

public abstract class Equipment  {
    private String name;
    private int role;
    private int equip;

    private int strength;
    private int dexterity;
    private int willpower;
    private int constitution;

    public Equipment(String name, int role, int equip, int strength, int dexterity, int willpower, int constitution) {
        this.name = name;
        this.role = role;
        this.equip = equip;
        this.strength = strength;
        this.dexterity = dexterity;
        this.willpower = willpower;
        this.constitution = constitution;
    }

    public String getName()
    {
        return name;
    }

    public int getRole()
    {
        return role;
    }

    public int getEquip()
    {
        return equip;
    }

    public int getStrength()
    {
        return strength;
    }

    public int getDexterity()
    {
        return dexterity;
    }

    public int getWillpower()
    {
        return willpower;
    }

    public int getConstitution()
    {
        return constitution;
    }
}
