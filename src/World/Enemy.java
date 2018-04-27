package World;

import Party.Item.Equipment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Enemy extends NPC {

    private int ID;
    private int HP;
    private int MaxHP;
    private int damage;
    private int armour;

    private boolean alive;
    private boolean boss;

    private int goldDrop;
    private int expDrop;
    private List<Equipment> drops;

    private boolean attacked;
    private int target;

    public Enemy(String name, int ID, int HP, int damage, int armour, boolean boss, int goldDrop, int expDrop, List<Equipment> drops) {
        super(name);
        this.ID = ID;
        this.HP = HP;
        this.MaxHP = this.HP;
        this.damage = damage;
        this.armour = armour;
        alive = true;
        this.boss = boss;
        this.goldDrop = goldDrop;
        this.expDrop = expDrop;
        this.drops = new ArrayList<>();
        if (drops != null)
            this.drops.addAll(drops);

        attacked = false;
        target = -1;
    }

    public int getID() {
        return ID;
    }

    public int getHP() {
        return HP;
    }

    public int getMaxHP() {
        return MaxHP;
    }

    public int getDamage() {
        return damage;
    }

    public int getArmour() {
        return armour;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getGoldDrop() {
        return goldDrop;
    }

    public int getExpDrop() {
        return expDrop;
    }

    public boolean hasAttacked() {
        return attacked;
    }

    public int getTarget() {
        return target;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public void damage(int damage) {
        if (damage >= HP) {
            HP = 0;
            setAlive(false);
        } else
            HP -= damage;
    }

    public Equipment getRandomDrop()
    {
        if(drops.isEmpty())
            return null;
        Random rand = new Random();
        int num;
        if(isBoss())
            num = rand.nextInt(drops.size());
        else
            num = rand.nextInt(drops.size() * 2);
        if(num < (drops.size() - 1))
            return drops.get(num);
        else
            return null;
    }

    private boolean isBoss() {
        return boss;
    }

    private void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enemy enemy = (Enemy) o;
        return ID == enemy.ID;
    }

    @Override
    public int hashCode() {

        return Objects.hash(ID);
    }
}
