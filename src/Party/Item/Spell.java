package Party.Item;

public class Spell {
    private int id;
    private String name;

    private int effect;

    private boolean targetAlly;
    private boolean targetEnemy;
    private boolean aoe;

    private static int newID = 1;

    public Spell(String name, int effect, boolean targetAlly, boolean targetEnemy, boolean aoe) {
        id = newID++;
        this.name = name;

        this.effect = effect;

        this.targetAlly = targetAlly;
        this.targetEnemy = targetEnemy;
        this.aoe = aoe;
    }

    public String getName() {
        return name;
    }

    public int getEffect() {
        return effect;
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
