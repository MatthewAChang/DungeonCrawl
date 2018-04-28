package World.Item;

public class Armour extends Equipment{
    private int armour;

    public Armour(String name, int role, int equip, int strength, int dexterity, int willpower, int constitution, int armour)
    {
        super(name, role, equip, strength, dexterity, willpower, constitution);
        this.armour = armour;
    }

    public int getArmour()
    {
        return armour;
    }
}
