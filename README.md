# CS611-Legends

Name
-------------------------------------------------------------------------------------------------

Feifan Yuan
U61632796


Files
-------------------------------------------------------------------------------------------------

1. Main
   * Entrance of the project
   * contains main() funciton
2. Game
   * abstract class for all kinds of games
3. GameRPG
   * abstract class for all kinds of RPG games
   * inherits from Game
4. GameLMH
   * game of Legends
   * inherits from GameRPG
5. GenericCharacter
   * represents characters in RPG games
6. Hero
   * heroes in Legends
   * inherits from GenericCharacter
7. HeroPaladin
   * one type of hero 
   * inherits from Hero
8. HeroSorcerer
   * one type of hero 
   * inherits from Hero
9. HeroWarrior
   * one type of hero
   * inherits from Hero
10. HeroList
    * a class used to parse hero files and print all types of heroes
11. StrategyLevelUp
    * an interface used to realize strategy pattern for level up strategies of different heroes
12. HeroPaladinLevelUp
    * Hero Paladins level up strategy
    * implements StrategyLevelUp
13. HeroSorcererLevelUp
    * Hero Sorcerers level up strategy
    * implements StrategyLevelUp
14. HeroWarriorsLevelUp
    * Hero Warriors level up strategy
    * implements StrategyLevelUp
15. Monster
    * monsters in Legends
    * inherits from GenericCharacter
16. MonsterDragon
    * one type of monster 
    * inherits from Monster
17. MonsterExoskeleton
    * one type of monster
    * inherits from Monster
18. MonsterSpirit
    * one type of monster
    * inherits from Monster
19. MonsterList
    * a class used to parse monster files and print all types of monsters
20. Bag
    * bags for each hero
    * contains different types of props and money
21. Prop
    * props in Legends
22. Weapon
    * weapons in Legends
    * inherits Prop
23. Armor
    * armors in Legends
    * inherits Prop
24. Potion
    * potions in Legends
    * inherits Prop
25. Spell
    * spells in Legends
    * inherits Prop
26. SpellFile
    * one type of spell
    * inherits spell
27. SpellIce
    * one type of spell
    * inherits spell
28. SpellLightning
    * one type of spell
    * inherits spell
29. Market
    * markets for all type of games
30. MarketLMH
    * market for Legends
    * inherits from Market
31. MarketPrinter
    * an interface used to print props in markets
    * implemented by Market
32. MarketFactory
    * a class used to realize simple factory pattern
    * is extendable for other different types of markets
33. Grid
    * grids for all type of games
34. GridLMH
    * grid for Legends
    * inherits from Grid
35. GridPrinter
    * an interface used to print grid 
    * implemented by Grid
36. GridFactory
    * a class used to realize simple factory pattern
    * is extendable for other different types of grids
37. Cell
    * basic cell of a grid
38. CellBirth
    * birth place of a player
39. CellHero
    * position of a player
40. CellCommon
    * common space in a grid
41. CellInaccessible
    * inaccessible space in a grid
42. CellMarket
    * markets in a grid
43. StrategyParsing
    * an interface used to realize strategy pattern
    * implemented by Weapon, Armor, Potion, Spell
44. ParsingFile
    * like a Context class in strategy pattern
45. Colors
    * store some useful color to realize colorful console

Notes:
-------------------------------------------------------------------------------------------------

1. Bonus
   * realize colorful console and use some icons to make the grid more user friendly.
   * use design patterns
     * simple factory pattern (class MarketFactory and GridFactory) 
     * strategy pattern (class StrategyParsing and Strategy LevelUp)

2. Things to note
   * hp of heroes = level * 1000 (It is easy for a hero to be fainted in only 1 turn because of high damage of a monster)
   * dodge_chance of heroes = 0.001 * agility (controlled between 0.0-1.0 which is same as monsters)
   * If all the heroes are fainted, the game will automatically end.


How to run:
-------------------------------------------------------------------------------------------------
1. Navigate to the directory after downloading the project
2. Run the following instructions on command line:
	javac *.java
	java Main.java