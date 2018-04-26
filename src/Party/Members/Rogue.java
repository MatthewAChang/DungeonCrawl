package Party.Members;

import Party.Item.Weapon;

public class Rogue extends PartyMember {

    private static int HP_PER_LEVEL = 5;
    private static int MANA_PER_LEVEL = 4;

    public Rogue(String name)
    {
        super(name, Class.ROUGE.role());
        baseHP = 90;
        baseMana = 90;
        dexterity = 14;
        willpower = 12;
        setStats();
    }

    public int getDamage()
    {
        Weapon dagger = null;
        if(leftArm != null)
            dagger = (Weapon) this.leftArm;
        return super.getDamage() + dagger.getDamage() + (strength / 2) + (dexterity / 2);
    }

    @Override
    public void levelUp()
    {
        baseHP += HP_PER_LEVEL++;
        baseMana += MANA_PER_LEVEL;
        level += 1;
        setStats();
    }

    @Override
    public void autoLevel()
    {
        if(level % 3 == 0)
        {
            dexterity += 2;
            constitution += 1;
        }
        else if(level % 10 == 0)
        {
            strength += 1;
            willpower += 1;
            constitution += 1;
        }
        else
        {
            dexterity += 3;
        }
        levelUp();
    }

    @Override
    public String roleToString()
    {
        return "Rogue";
    }
}
