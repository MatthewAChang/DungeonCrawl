package Party.Members;

import Party.Item.Weapon;

public class Rogue extends PartyMember {

    public Rogue(String name)
    {
        super(name, Class.ROUGE.role());
        baseHP = 500;
        setHP();
        dexterity = INITIAL_STAT_UP;
    }

    public int getDamage()
    {
        int damage = 0;
        Weapon dagger = null;
        if(leftArm != null)
            dagger = (Weapon) this.leftArm;
        return super.getDamage() + dagger.getDamage() + (strength / 2) + (dexterity / 2);
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
