# Minesweeper Game Implementation

  MineField.java -> This class represents the game board - MineField -, which covers all the major/core characteristics
  of the Minesweeper. The class extends JPanel and overrides the paintComponent function to draw the individual elements
  of the Minesweeper board and includes the private MinesAdapter class, which extends the MinesAdapter class and overrides
  mousePressed function to check if the user has clicked in any of the cells to update the game status, seconds, number
  mines, and repaint the board with new features. The class also implements the findEmptyCells recursive function, reset(),
  tick(), setLevel(), and newGame() functions. Additionally, the class includes public getter methods
  and private helper methods, like the initializeBoard() function.

  GameState.java -> This class handles the File I/O component of the game. It includes 3 functions -
  stringsToFile, which turns the string representations into files with the file name being the unique timestamp,
  getBoardInfo, which translates the current visibility of all the cells on the board into a String, and
  exportGame, which exports the current game into a .txt file and puts the file in the project folder.

  RunMinesweeper.java -> This class implements Runnable, and creates the user interface of the game.
  This class focuses on specifying the frame and widgets of the GUI, including the reset and export buttons at
  the top and the instructions and settings buttons at the bottom. When the user wants to run the actual game,
  this file should be run.

# External Sources

  http://www.java2s.com/Code/Java/Event/Handlemousebuttonclickevent.htm
  http://minesweeperonline.com/ - played the game a couple of times to get the sense of the algorithm and design
  https://apkpure.com/minesweeper-classic/com.jurajkusnier.minesweeper - clipped the game icons from
  these pictures
