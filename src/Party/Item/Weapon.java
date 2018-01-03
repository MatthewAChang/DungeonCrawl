package Party.Item;

public class Weapon extends Equipment {
    private int damage;

    public Weapon(String name, int role, int equip, int strength, int dexterity, int willpower, int constitution, int damage)
    {
        super(name, role, equip, strength, dexterity, willpower, constitution);
        this.damage = damage;
    }

    public int getDamage()
    {
        return damage;
    }
}
