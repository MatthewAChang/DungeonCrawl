package World.Character;

public abstract class Character {

    private int id;
    private String name;

    private int hp;
    private int maxHp;

    private boolean alive;

    private boolean attacked;
    private int option;

    public Character(int id, String name) {
        this.id = id;
        this.name = name;

        this.hp = 0;
        this.maxHp = 0;

        this.alive = true;
        this.attacked = false;
        this.option = -1;
    }

    public Character(int id, String name, int hp) {
        this.id = id;
        this.name = name;

        this.hp = hp;
        this.maxHp = hp;

        this.alive = true;
        this.attacked = false;
        this.option = -1;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHp()
    {
        return hp;
    }

    public int getMaxHp()
    {
        return maxHp;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean hasAttacked() {
        return attacked;
    }

    public int getOption() {
        return option;
    }

    public void heal(int heal) {
        if(heal + hp > maxHp)
            hp = maxHp;
        else
            hp += heal;
    }

    public void setHp(int hp) {
        this.maxHp = hp;
        this.hp = this.maxHp;
    }

    public void setMaxHp(int hp) {
        this.maxHp = hp;
        if(this.hp > this.maxHp)
            this.hp = this.maxHp;
    }

    public void resetHp() {
        this.hp = this.maxHp;
        setAlive(true);
    }

    public void setAttacked(boolean attacked)
    {
        this.attacked = attacked;
    }

    public void setOption(int option)
    {
        this.option = option;
    }

    public void damage(int damage) {
        if(damage >= this.hp)
        {
            this.hp = 0;
            setAlive(false);
        }
        else
            this.hp -= damage;
    }

    private void setAlive(boolean alive)
    {
        this.alive = alive;
    }

    public abstract int getDamage();

    public abstract int getArmour();
}
