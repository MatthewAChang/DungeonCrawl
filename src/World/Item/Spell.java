package World.Item;

public class Spell {
    private int id;
    private String name;

    private int effect;
    private int cost;

    private boolean targetSelf;
    private boolean targetAlly;
    private boolean targetEnemy;
    private boolean aoe;

    public Spell(int id, String name, int effect, int cost, boolean targetSelf, boolean targetAlly, boolean targetEnemy, boolean aoe) {
        this.id = id;
        this.name = name;

        this.effect = effect;
        this.cost = cost;

        this.targetSelf = targetSelf;
        this.targetAlly = targetAlly;
        this.targetEnemy = targetEnemy;
        this.aoe = aoe;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getEffect() {
        return effect;
    }

    public boolean isTargetSelf() {
        return targetSelf;
    }

    public boolean isTargetAlly() {
        return targetAlly;
    }

    public boolean isTargetEnemy() {
        return targetEnemy;
    }

    public boolean isTargetAll() {
        return targetAlly && targetEnemy;
    }

    public boolean isAoe() {
        return aoe;
    }
}
