package Helper;

import UI.DungeonCrawl;

public class Story {

    public static String story(int story) {
        String str;
        switch(story) {
            case 1:
                str = startMessage();
                break;
            default:
                str = null;
        }
        return str;
    }

    public static String startMessage()
    {
        String str;
        str =  "Welcome to the world of " + DungeonCrawl.world.getName() + ".\n";
        str += "    You begin in the town of " + DungeonCrawl.world.getCurrentTown().getName() + ". This is the town where all adventurers begin and where you will make a legacy. ";
        str += "On the main town menu, there will be several options. You can go exploring dungeons and discover riches, visit the shop, talk to townsfolk, and visit the inn.\n";
        return str;
    }
}
