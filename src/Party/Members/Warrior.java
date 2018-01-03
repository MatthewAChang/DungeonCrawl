package Party.Members;

import Party.Item.Armour;

public class Warrior extends PartyMember {

    public Warrior(String name)
    {
        super(name, Class.WARRIOR.role());
        baseHP = 550;
        setHP();
        strength = INITIAL_STAT_UP;
    }

    public int getDamage()
    {
        return super.getDamage() + strength;
    }

    public int getArmour()
    {
        int armour = 0;
        Armour shield = null;
        if(leftArm != null)
        {
            shield = (Armour) leftArm;
            armour += shield.getArmour();
        }
        armour += super.getArmour();
        return armour;
    }

    @Override
    public void autoLevel()
    {
        if(level % 2 == 0)
        {
            strength += 2;
            constitution += 1;
        }
        else if(level % 10 == 0)
        {
            dexterity += 1;
            willpower += 1;
            constitution += 1;
        }
        else
        {
            strength += 3;
        }
        levelUp();
    }

    @Override
    public String roleToString()
    {
        return "Warrior";
    }
}
