# DungeonCrawl

Dungeon Crawl is a text-based game played on a JFrame. The program uses an Action Listener to obtain input from the user by putting the thread to sleep until the user enters input. Dungeon Crawl implements the Iterable interface multiple times for simpler for loops and also uses the singleton design pattern to ensure only a single instance of the world and the party. Dungeon Crawl has 27 class to organize and reduce the 2750 lines of code.

When starting the game, the user can choose to be a Warrior, Rogue, or Mage and then they will be on the main menu. From the main menu, the player can travel to a dungeon to battle monsters, visit the shop to buy or sell items, visit the inn to heal, check their inventory and equip or remove equipment, travel to another town, or view their own stats. When battling in the dungeon, the user can attack, use a spell, or run from battle. The spells can do anything, from an AOE attack on all the enemies to a heal on another party member. When leveling up the user can allocate their stat points and can also learn new spells at certain levels.
