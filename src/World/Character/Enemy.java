package World.Character;

import World.Item.Equipment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Enemy extends Character {

    private int damage;
    private int armour;

    private boolean boss;

    private int goldDrop;
    private int expDrop;
    private List<Equipment> drops;

    public Enemy(String name, int id, int hp, int damage, int armour, boolean boss, int goldDrop, int expDrop, List<Equipment> drops) {
        super(id, name, hp);
        this.damage = damage;
        this.armour = armour;

        this.boss = boss;
        this.goldDrop = goldDrop;
        this.expDrop = expDrop;
        this.drops = new ArrayList<>();
        if (drops != null)
            this.drops.addAll(drops);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public int getArmour() {
        return armour;
    }

    public boolean isBoss() {
        return boss;
    }

    public int getGoldDrop() {
        return goldDrop;
    }

    public int getExpDrop() {
        return expDrop;
    }

    public List<Equipment> getDrops() {
        return drops;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enemy other = (Enemy) o;
        return getId() == other.getId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
