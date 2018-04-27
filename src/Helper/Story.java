package Helper;

import UI.Game;

public class Story extends Game {

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

    private static String startMessage()
    {
        String str;
        str =  "Welcome to the world of " + Game.world.getName() + ".\n";
        str += "    You begin in the town of " + Game.world.getCurrentTown().getName() + ". This is the town where all adventurers begin and where you will make a legacy. ";
        str += "On the main town menu, there will be several options. You can go exploring dungeons and discover riches, visit the shop or inn, and talk to townsfolk.\n";
        return str;
    }
}
