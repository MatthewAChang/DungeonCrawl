package World.Character;

import World.Item.Weapon;

public class Rogue extends PartyMember {

    private static int HP_PER_LEVEL = 5;
    private static int MANA_PER_LEVEL = 4;

    public Rogue(String name, int baseHp, int baseMana, int str, int dex, int wil, int con)
    {
        super(name, Class.ROGUE.role(), baseHp, baseMana, str, dex, wil, con);
        setStats();
    }

    public int getDamage()
    {
        int damage = super.getDamage()  + (getStrength() / 2) + (getDexterity() / 2);
        if(getLeftArm() != null) {
            Weapon dagger = (Weapon) getLeftArm();
            damage += dagger.getDamage();
        }
        return damage;
    }

    @Override
    public void levelUp()
    {
        increaseBaseStats(HP_PER_LEVEL++, MANA_PER_LEVEL);
        increaseLevel();
        setStats();
    }

    @Override
    public void autoLevel()
    {
        if(getLevel() % 3 == 0)
            increaseAttributes(0, 2, 0, 1);
        else if(getLevel() % 10 == 0)
            increaseAttributes(1, 0, 1, 1);
        else
            increaseAttributes(0, 3, 0, 0);
        levelUp();
    }
}
