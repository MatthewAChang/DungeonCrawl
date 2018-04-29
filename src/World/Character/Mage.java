package World.Character;

import Helper.Enum.ClassList;

public class Mage extends PartyMember {

    private static int HP_PER_LEVEL = 4;
    private static int MANA_PER_LEVEL = 6;

    public Mage(String name, int baseHp, int baseMana, int str, int dex, int wil, int con)
    {
        super(name, ClassList.MAGE.role(), baseHp, baseMana, str, dex, wil, con);
        setStats();
    }

    @Override
    public int getDamage()
    {
        return super.getBaseDamage() + (getWillpower() / 2);
    }

    @Override
    public void levelUp()
    {
        increaseBaseStats(HP_PER_LEVEL, MANA_PER_LEVEL++);
        increaseLevel();
        setStats();
    }

    @Override
    public void autoLevel()
    {
        if(getLevel() % 4 == 0)
            increaseAttributes(0, 0, 2, 1);
        else if(getLevel() % 10 == 0)
            increaseAttributes(0, 1, 1, 1);
        else
            increaseAttributes(0, 0, 3, 0);
        levelUp();
    }
}
