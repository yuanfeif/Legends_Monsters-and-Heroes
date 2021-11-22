# CS611-Legends

Team information
-------------------------------------------------------------------------------------------------

| Name | BU id | email|
| :--- | :--- | :--- |
| Feifan Yuan| U61632796   | ffyuan@bu.edu |
| Junchen Liu                         | U36314175 | junchenl@bu.edu |
| BhaskarDurgaVeeraVenkata Ganga Raju | U89625488   | bhaskar9@bu.edu |

!!How to run!!:
-------------------------------------------------------------------------------------------------
> In order to make the game fancy and colorful, we use some unicode when drawing the grid. So you may run the project in IDEA to see the results.
> 
> We tested this project in IDEA with JDK 1.8 and file encodeing UTF-8 on a Windows-based machine.

1. Open this project in IntelliJ IDEA
2. Click the "Run" button in Main class to run the project
3. Make sure you have a JDK installed in your PC with version at least 1.8
4. If you have problems with running this project, please feel free to contact Feifan Yuan to have a face-to-face presentation.

Files
-------------------------------------------------------------------------------------------------

1. Main
   * Entrance of the project
   * contains main() funciton
2. Game
   * abstract class for all kinds of games
3. GameHost
   * a host of the whole game to run different games
4. GameRPG
   * abstract class for all kinds of RPG games
   * inherits from Game
5. GameLMH
   * game of Legends
   * inherits from GameRPG
6. GameLV
   * game of Legends of Valor
   * inherits from GameRPG
7. GamePrintUtil
   * print some common things in different games
8. UserInputUtil
   * deal with the input from a user
9. PlayMusic
   * use to play the background music
10. GenericCharacter
   * represents characters in RPG games
11. Hero
    * heroes in Legends
    * inherits from GenericCharacter
12. HeroPaladin
    * one type of hero 
    * inherits from Hero
13. HeroSorcerer
    * one type of hero 
    * inherits from Hero
14. HeroWarrior
    * one type of hero
    * inherits from Hero
15. HeroList
    * a class used to parse hero files and print all types of heroes
16. StrategyLevelUp
    * an interface used to realize strategy pattern for level up strategies of different heroes
17. HeroPaladinLevelUp
    * Hero Paladins level up strategy
    * implements StrategyLevelUp
18. HeroSorcererLevelUp
    * Hero Sorcerers level up strategy
    * implements StrategyLevelUp
19. HeroWarriorsLevelUp
    * Hero Warriors level up strategy
    * implements StrategyLevelUp
20. Monster
    * monsters in Legends
    * inherits from GenericCharacter
21. MonsterDragon
    * one type of monster 
    * inherits from Monster
22. MonsterExoskeleton
    * one type of monster
    * inherits from Monster
23. MonsterSpirit
    * one type of monster
    * inherits from Monster
24. MonsterList
    * a class used to parse monster files and print all types of monsters
25. Bag
    * bags for each hero
    * contains different types of props and money
26. Prop
    * props in Legends
27. Weapon
    * weapons in Legends
    * inherits Prop
28. Armor
    * armors in Legends
    * inherits Prop
29. Potion
    * potions in Legends
    * inherits Prop
30. Spell
    * spells in Legends
    * inherits Prop
31. SpellFile
    * one type of spell
    * inherits spell
32. SpellIce
    * one type of spell
    * inherits spell
33. SpellLightning
    * one type of spell
    * inherits spell
34. isAttackable
    * if a weapon is attackable
35. isCastble
    * if a spell is castable
36. isTradable
    * if a prop is tradable
37. isUsable
    * if a potion is usable
38. Market
    * markets for all type of games
39. MarketLMH
    * market for Legends
    * inherits from Market
40. MarketPrinter
    * an interface used to print props in markets
    * implemented by Market
41. MarketFactory
    * a class used to realize simple factory pattern
    * is extendable for other different types of markets
42. Grid
    * grids for all type of games
43. GridLMH
    * grid for Legends
    * inherits from Grid
44. GridLV
    * grid for Legends of Valor
    * inherits from Grid
45. GridPrinter
    * an interface used to print grid 
    * implemented by Grid
46. GridFactory
    * a class used to realize simple factory pattern
    * is extendable for other different types of grids
47. Cell
    * basic cell of a grid
48. CellBirth
    * birth place of a player
49. CellHero
    * position of a player
50. CellCommon
    * common space in a grid
51. CellInaccessible
    * inaccessible space in a grid
52. CellMarket
    * markets in a grid
53. CellHeroNexus
    * nexus of heroes
54. CellMonsterNexus
    * nexus of monsters
55. CellBush
    * bushes in a grid
56. CellCave
    * caves in a grid
57. CellPlain
    * plains in a grid
58. CellKoulou
    * koulous in a grid
59. StrategyParsing
    * an interface used to realize strategy pattern
    * implemented by Weapon, Armor, Potion, Spell
60. ParsingFile
    * like a Context class in strategy pattern
61. Colors
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
