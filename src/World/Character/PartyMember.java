package World.Character;

import World.Item.Armour;
import World.Item.Equipment;
import World.Item.Spell;
import World.Item.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class PartyMember extends Character{
    private Armour head;
    private Armour body;
    private Weapon rightArm;
    private Equipment leftArm;

    private List<Spell> spells;

    private int role;
    private int level;
    private int exp;
    private int expForNextLvl;
    private int baseHp;
    private int baseMana;
    private int mana;
    private int maxMana;
    private int strength;
    private int dexterity;
    private int willpower;
    private int constitution;

    private final int CONVERSION = 5;

    public final static int LEVEL_UP_POINTS = 3;

    private static int newID = 1;

    public PartyMember(String name, int role, int baseHp, int baseMana, int str, int dex, int wil, int con)
    {
        super(newID++, name);
        this.role = role;
        this.level = 0;
        this.baseHp = baseHp;
        this.baseMana = baseMana;
        this.strength = str;
        this.dexterity = dex;
        this.willpower = wil;
        this.constitution = con;

        spells = new ArrayList<>();

        expForNextLvl = 2000;

        head = null;
        body = null;
        rightArm = null;
        leftArm = null;
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

    public int getExpForNextLvlRelative()
    {
        return expForNextLvl - exp;
    }

    public int getMana()
    {
        return mana;
    }

    public int getMaxMana()
    {
        return maxMana;
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

    public int getStrength()
    {
        return strength;
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

    public void increaseBaseStats(int increaseHp, int increaseMana) {
        baseHp += increaseHp;
        baseMana += increaseMana;
    }

    public void increaseAttributes(int str, int dex, int wil, int con) {
        strength += str;
        dexterity += dex;
        willpower += wil;
        constitution += con;
    }

    public void increaseLevel() {
        level += 1;
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

    public void resetStats() {
        resetHp();
        mana = maxMana;
    }

    @Override
    public int getDamage()
    {
        int damage = 0;
        if(rightArm != null)
            damage += rightArm.getDamage();
        damage += level;
        return damage;
    }

    @Override
    public int getArmour()
    {
        int armour = 0;
        if(head != null)
            armour += head.getArmour();
        if(body != null)
            armour += body.getArmour();
        if(getRole() == Class.WARRIOR.role() && leftArm != null) {
            Armour shield = (Armour)leftArm;
            armour += shield.getArmour();
        }
        return armour;
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
    }

    protected void setStats() {
        setHp(baseHp + CONVERSION * constitution);
        mana = baseMana + CONVERSION * willpower;
        maxMana = mana;
    }

    private void setMaxStats() {
        setMaxHp(baseHp + CONVERSION * constitution);
        maxMana = baseMana + CONVERSION * willpower;
        if(mana > maxMana)
            mana = maxMana;
    }

    public abstract void levelUp();

    public abstract void autoLevel();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartyMember other = (PartyMember) o;
        return getId() == other.getId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
