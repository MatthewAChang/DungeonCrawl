package Party.Members;

public class Mage extends PartyMember {

    public Mage(String name)
    {
        super(name, Class.MAGE.role());
        baseHP = 450;
        setHP();
        willpower = INITIAL_STAT_UP;
    }

    @Override
    public int getDamage()
    {
        return super.getDamage() + (willpower / 2);
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
