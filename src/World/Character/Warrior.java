package World.Character;

public class Warrior extends PartyMember {

    private static int HP_PER_LEVEL = 6;
    private static int MANA_PER_LEVEL = 5;

    public Warrior(String name, int baseHp, int baseMana, int str, int dex, int wil, int con)
    {
        super(name, Class.WARRIOR.role(), baseHp, baseMana, str, dex, wil, con);
        setStats();
    }

    public int getDamage()
    {
        return super.getDamage() + getStrength();
    }

    @Override
    public void levelUp()
    {
        increaseBaseStats(HP_PER_LEVEL++, MANA_PER_LEVEL++);
        increaseLevel();
        setStats();
    }

    @Override
    public void autoLevel()
    {
        if(getLevel() % 2 == 0)
            increaseAttributes(2, 0, 0, 1);
        else if(getLevel() % 10 == 0)
            increaseAttributes(0, 1, 1, 1);
        else
            increaseAttributes(3, 0, 0, 0);
        levelUp();
    }
}
