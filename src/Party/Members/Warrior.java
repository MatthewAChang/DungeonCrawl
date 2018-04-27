package Party.Members;

import Party.Item.Armour;

public class Warrior extends PartyMember {

    private static int HP_PER_LEVEL = 6;
    private static int MANA_PER_LEVEL = 5;

    public Warrior(String name)
    {
        super(name, Class.WARRIOR.role());
        baseHP = 100;
        baseMana = 100;
        strength = 14;
        dexterity = 13;
        constitution = 13;
        setStats();
    }

    public int getDamage()
    {
        return super.getDamage() + strength;
    }

    public int getWarriorArmour()
    {
        int armour = 0;
        if(leftArm != null)
        {
            Armour shield = (Armour) leftArm;
            armour += shield.getArmour();
        }
        armour += super.getArmour();
        return armour;
    }

    @Override
    public void levelUp()
    {
        baseHP += HP_PER_LEVEL++;
        baseMana += MANA_PER_LEVEL++;
        level += 1;
        setStats();
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
}
