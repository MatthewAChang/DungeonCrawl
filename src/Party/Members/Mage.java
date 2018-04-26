package Party.Members;

public class Mage extends PartyMember {

    private static int HP_PER_LEVEL = 4;
    private static int MANA_PER_LEVEL = 6;

    public Mage(String name)
    {
        super(name, Class.MAGE.role());
        baseHP = 85;
        baseMana = 115;
        willpower = 14;
        constitution = 11;
        setStats();
    }

    @Override
    public int getDamage()
    {
        return super.getDamage() + (willpower / 2);
    }

    @Override
    public void levelUp()
    {
        baseHP += HP_PER_LEVEL;
        baseMana += MANA_PER_LEVEL++;
        level += 1;
        setStats();
    }

    @Override
    public void autoLevel()
    {
        if(level % 4 == 0)
        {
            willpower += 2;
            constitution += 1;
        }
        else if(level % 10 == 0)
        {
            strength += 1;
            dexterity += 1;
            constitution += 1;
        }
        else
        {
            willpower += 3;
        }
        levelUp();
    }

    @Override
    public String roleToString()
    {
        return "Mage";
    }
}
