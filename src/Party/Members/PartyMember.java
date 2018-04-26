package Party.Members;

import Party.Item.Armour;
import Party.Item.Equipment;
import Party.Item.Spell;
import Party.Item.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class PartyMember {
    protected Armour head;
    protected Armour body;
    protected Weapon rightArm;
    protected Equipment leftArm;

    protected List<Spell> spells;

    protected Party party;

    protected String name;
    protected int ID;
    protected int role;
    protected int level;
    protected int exp;
    protected int expForNextLvl;
    protected int baseHP;
    protected int HP;
    protected int maxHP;
    protected int baseMana;
    protected int mana;
    protected int maxMana;
    protected int strength;
    protected int dexterity;
    protected int willpower;
    protected int constitution;

    protected boolean alive;

    protected boolean attacked;
    protected int option;

    protected final int INITIAL_STAT = 10;

    protected final int CONVERSION = 5;

    public final static int LEVEL_UP_POINTS = 3;

    private static int newID = 1;

    public PartyMember(String name, int role)
    {
        this.name = name;
        this.ID = newID++;
        this.role = role;
        level = 0;
        strength = INITIAL_STAT;
        dexterity = INITIAL_STAT;
        willpower = INITIAL_STAT;
        constitution = INITIAL_STAT;

        spells = new ArrayList<>();

        expForNextLvl = 2000;

        alive = true;

        head = null;
        body = null;
        rightArm = null;
        leftArm = null;

        party = null;

        attacked = false;
        option = -1;
    }

    public String getName()
    {
        return name;
    }

    public int getID()
    {
        return ID;
    }

    public int getRole()
    {
        return role;
    }

    public int getLevel()
    {
        return level;
    }

    public int getCurrentExp()
    {
        return exp;
    }

    public int getExpForNextLvlAbsolute()
    {
        return expForNextLvl;
    }

    public int getExpForNextLvlRelative()
    {
        return expForNextLvl - exp;
    }

    public int getHP()
    {
        return HP;
    }

    public int getMaxHP()
    {
        return maxHP;
    }

    public int getMana()
    {
        return mana;
    }

    public int getMaxMana()
    {
        return maxMana;
    }

    public int getStrength()
    {
        return strength;
    }

    public int getBaseStrength()
    {
        int strength = this.strength;
        if(this.head != null)
            strength -= head.getStrength();
        if(this.body != null)
            strength -= body.getStrength();
        if(this.rightArm != null)
            strength -= rightArm.getStrength();
        if(this.leftArm != null)
            strength -= leftArm.getStrength();
        return strength;
    }

    public int getBaseDexterity()
    {
        int dexterity = this.dexterity;
        if(this.head != null)
            dexterity -= head.getDexterity();
        if(this.body != null)
            dexterity -= body.getDexterity();
        if(this.rightArm != null)
            dexterity -= rightArm.getDexterity();
        if(this.leftArm != null)
            dexterity -= leftArm.getDexterity();
        return dexterity;
    }

    public int getBaseWillpower()
    {
        int willpower = this.willpower;
        if(this.head != null)
            willpower -= head.getWillpower();
        if(this.body != null)
            willpower -= body.getWillpower();
        if(this.rightArm != null)
            willpower -= rightArm.getWillpower();
        if(this.leftArm != null)
            willpower -= leftArm.getWillpower();
        return willpower;
    }

    public int getBaseConstitution()
    {
        int constitution = this.constitution;
        if(this.head != null)
            constitution -= head.getConstitution();
        if(this.body != null)
            constitution -= body.getConstitution();
        if(this.rightArm != null)
            constitution -= rightArm.getConstitution();
        if(this.leftArm != null)
            constitution -= leftArm.getConstitution();
        return constitution;
    }

    public int getDexterity()
    {
        return dexterity;
    }

    public int getWillpower()
    {
        return willpower;
    }

    public int getConstitution()
    {
        return constitution;
    }

    public boolean isAlive()
    {
        return alive;
    }

    public Party getParty()
    {
        return this.party;
    }

    public boolean hasAttacked()
    {
        return attacked;
    }

    public int getOption()
    {
        return option;
    }

    public boolean addExp(int exp)
    {
        this.exp += exp;
        if(this.exp >= expForNextLvl)
        {
            expForNextLvl += 500;
            return true;
        }
        return false;
    }

    public Armour getHead() {
        return head;
    }

    public Armour getBody() {
        return body;
    }

    public Weapon getRightArm() {
        return rightArm;
    }

    public Equipment getLeftArm() {
        return leftArm;
    }

    public void setParty(Party party)
    {
        this.party = party;
    }

    public void levelUp(int stat)
    {
        switch(stat)
        {
            case 1:
                strength += 1;
                break;
            case 2:
                dexterity += 1;
                break;
            case 3:
                willpower += 1;
                break;
            case 4:
                constitution += 1;
                break;
        }
    }

    public Equipment equipHead(Armour head)
    {
        Armour old = null;
        if(this.head != null) {
            setStatsFromUnequip(this.head);
            old = this.head;
        }
        this.head = head;
        if(this.head != null)
            setStatsFromEquip(head);
        return old;
    }

    public Equipment equipBody(Armour body)
    {
        Armour old = null;
        if(this.body != null) {
            setStatsFromUnequip(this.body);
            old = this.body;
        }
        this.body = body;
        if(this.body != null)
            setStatsFromEquip(body);
        return old;
    }

    public Equipment equipRightArm(Weapon rightArm)
    {
        Weapon old = null;
        if(this.rightArm != null) {
            setStatsFromUnequip(this.rightArm);
            old = this.rightArm;
        }
        this.rightArm = rightArm;
        if(this.rightArm != null)
            setStatsFromEquip(rightArm);
        return old;
    }

    public Equipment equipLeftArm(Equipment leftArm)
    {
        Weapon old = null;
        if(this.leftArm != null) {
            setStatsFromUnequip(this.leftArm);
            old = this.rightArm;
        }
        this.leftArm = leftArm;
        if(this.leftArm != null)
            setStatsFromEquip(leftArm);
        return old;
    }

    private void setStatsFromEquip(Equipment equipment)
    {
        strength += equipment.getStrength();
        dexterity += equipment.getDexterity();
        willpower += equipment.getWillpower();
        constitution += equipment.getConstitution();
        setMaxStats();
    }

    private void setStatsFromUnequip(Equipment equipment)
    {
        strength -= equipment.getStrength();
        dexterity -= equipment.getDexterity();
        willpower -= equipment.getWillpower();
        constitution -= equipment.getConstitution();
        setMaxStats();
        party.addEquipment(equipment);
    }

    public void setStats() {
        HP = baseHP + CONVERSION * constitution;
        maxHP = HP;
        mana = baseMana + CONVERSION * willpower;
        maxMana = mana;
    }

    protected void setMaxStats() {
        maxHP = baseHP + CONVERSION * constitution;
        if(HP > maxHP)
            HP = maxHP;
        maxMana = baseMana + CONVERSION * willpower;
        if(mana > maxMana)
            mana = maxMana;
    }

    public void setAlive(boolean alive)
    {
        this.alive = alive;
    }

    public void setAttacked(boolean attacked)
    {
        this.attacked = attacked;
    }

    public void setOption(int option)
    {
        this.option = option;
    }

    public void damage(int damage)
    {
        if(damage >= HP)
        {
            HP = 0;
            setAlive(false);
        }
        else
            HP -= damage;
    }

    public int getDamage()
    {
        int damage = 0;
        if(rightArm != null)
            damage += rightArm.getDamage();
        damage += level;
        return damage;
    }

    public int getArmour()
    {
        int armour = 0;
        if(head != null)
            armour += head.getArmour();
        if(body != null)
            armour += body.getArmour();
        return armour;
    }

    public abstract void levelUp();

    public abstract void autoLevel();

    public abstract String roleToString();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartyMember that = (PartyMember) o;
        return ID == that.ID;
    }

    @Override
    public int hashCode() {

        return Objects.hash(ID);
    }
}
