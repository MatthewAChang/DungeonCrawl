package Helper.Enum;

public enum EnemyList {
    GOBLIN(1),
    TROLL(2),
    TROLL_LEADER(3);

    private int enemy;

    EnemyList(int enemy)
    {
        this.enemy = enemy;
    }

    public int enemy()
    {
        return this.enemy;
    }

    public static String toString(int enemy) {
        switch(enemy)
        {
            case 1: return "Goblin";
            case 2: return "Troll";
            case 3: return "Troll Leader";
        }
        return null;
    }
}
