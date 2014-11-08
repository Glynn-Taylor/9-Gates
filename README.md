##About
For my second year computing coursework I decided to create a 3D roguelike, as it was not something that I had seen done before (that wasn't isometric). Initially I had planned to release a full singleplayer game; however as time went on and I realised how much work this would take, I had to cut out parts of the project, such as storyline, ranged weapons, shops and a few other key features.

This project was incredibly fun to do and was probably my longest project to date; I feel like the vast majority of it was well written, and a lot of the GUI code I have taken on to develop in my later projects.

##Features
- A* pathfinding
- OpenGL rendered maps and GUI
- Front end GUI and menus
- Primitive rpg system, stats + random equipable items and potions
- End game boss and end state
- Randomly generated maps, generated using prefabs and placed/connected using algorithms
- Turn based combat
- Saving and loading of games and prefabs
- Many different mobs

##Technical information
The entire project was rendered using OpenGL, though written in Java (uses LWJGL), using both VBOs for static geometry and immediate mode for rendering entities. Both game saves, (maps, entities, character statistics and equipment etc) and prefabs are fully serializeable and reloadable; I had some issue here where vbo buffers were not serializeable, and so had to also save map tile data to be remade into VBOs later on.

##Links

- [WarwickGameDesign](https://www.warwickgamedesign.co.uk)
  - [Forums](https://www.warwickgamedesign.co.uk/forum)
    - [Games](https://www.warwickgamedesign.co.uk/games)

##Builds
    - [Windows/Linux/Mac](http://gtaylor.warwickcompsoc.co.uk/downloads/9-gates/9Gates_win-mac-linux_08-11-2014.zip)
