package org.cis120.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/*
 * MineField
 * This class represents the game board - MineField -, which covers all the major characteristics of
 * the Minesweeper.
 */

public class MineField extends JPanel {
    // cell fields
    private int cell = 0;
    private final int cellSize = 15;
    private final int cellCover = 10;
    private final int emptyCell = 0;
    private final int bombCell = 9;
    private final int coveredBombCell = bombCell + cellCover;
    private final int markedBombCell = coveredBombCell + cellCover;
    private Image[] img;

    // board size
    private int rows = 9;
    private int cols = 9;
    private int allCells = rows * cols;
    private final int boardWidth = 400;

    private int[] field;
    private boolean inGame;
    private int numbOfMines = 10;
    private int minesLeft;
    private String status;

    // interval for timer
    public static final int interval = 1000;
    private int seconds = 0;
    private boolean timeStarted = false;

    // constructor
    public MineField(String status) {
        this.status = status;
        initializeBoard();
    }

    // timer to count the seconds since game was started
    final Timer timer = new Timer(interval, e -> tick());

    /**
     * getStatus() returns the status of the game
     */
    public String getStatus() {
        return status;
    }

    /**
     * getTime() returns the time to complete the game
     */
    public int getTime() {
        return seconds;
    }

    /**
     * getNumbOfMines() returns the time to complete the game
     */
    public int getNumbOfMines() {
        return minesLeft;
    }

    /**
     * getCell(int x) returns the value of the cell in a board
     * @param x - represents the number of column in a field
     */
    public int getCell(int x) {
        return field[x];
    }

    /**
     * setStatus(String status) updates the Board's status property to the given string
     * @param status - represents the new status to update the Board's status to
     */
    private void setStatus(String status) {
        this.status = status;
    }

    /**
     * getRows() returns the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * getCols() returns the number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * tick() increases the seconds and repaints the window.
     */
    void tick() {
        if (inGame) {
            seconds++;
            repaint();
        }
    }

    /**
     * setLevel(char level) resets the board to a set size from the given level. It should be called
     * when selecting difficulty level.
     * @param level - represents the first character of the given level (either B, I, or E)
     */
    public void setLevel(char level) {
        inGame = false;
        timeStarted = false;
        seconds = 0;
        // game basics
        if (level == 'B') {
            rows = 9;
            cols = 9;
            allCells = rows * cols;
            numbOfMines = 10;
        } else if (level == 'I') {
            rows = 16;
            cols = 16;
            allCells = rows * cols;
            numbOfMines = 40;
        } else if (level == 'E') {
            rows = 23;
            cols = 23;
            allCells = rows * cols;
            numbOfMines = 100;
        }
        field = new int[allCells];
        newGame();
        repaint();
    }

    /**
     * reset() sets the board back to its original state with all blank cells. It should be called
     * when user presses the "new game" button.
     */
    public void reset() {
        inGame = true;
        timeStarted = false;
        status = "";
        seconds = 0;
        newGame();
    }

    /**
     * initializeBoard() initializes the game board - minefield - by setting up the dimensions
     * and adding pictures.
     */
    private void initializeBoard() {
        int boardHeight = 400;
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        int numbOfImg = 13;
        img = new Image[numbOfImg];

        for (int i = 0; i < numbOfImg; i++) {
            String path = "src/main/java/org/cis120/minesweeper/resources/" + i + ".png";
            img[i] = (new ImageIcon(path)).getImage();
        }

        addMouseListener(new MinesAdapter());
        newGame();
    }

    /**
     * newGame() starts a new game by randomly distributing the bombs across the field
     */
    private void newGame() {
        Random random = new Random();
        inGame = true;
        minesLeft = numbOfMines;

        field = new int[allCells];
        for (int i = 0; i < allCells; i++) {
            field[i] = cellCover;
        }

        int i = 0;
        while (i < numbOfMines) {
            int position = (int) (allCells * random.nextDouble());
            if ((position < allCells)
                    && (field[position] != coveredBombCell)) {
                int current_col = position % cols;
                field[position] = coveredBombCell;
                i++;

                if (current_col > 0) {
                    cell = position - 1 - cols;
                    if (cell >= 0) {
                        if (field[cell] != coveredBombCell) {
                            field[cell] += 1;
                        }
                    }
                    cell = position - 1;
                    if (cell >= 0) {
                        if (field[cell] != coveredBombCell) {
                            field[cell] += 1;
                        }
                    }

                    cell = position + cols - 1;
                    if (cell < allCells) {
                        if (field[cell] != coveredBombCell) {
                            field[cell] += 1;
                        }
                    }
                }

                cell = position - cols;
                if (cell >= 0) {
                    if (field[cell] != coveredBombCell) {
                        field[cell] += 1;
                    }
                }

                cell = position + cols;
                if (cell < allCells) {
                    if (field[cell] != coveredBombCell) {
                        field[cell] += 1;
                    }
                }

                if (current_col < (cols - 1)) {
                    cell = position - cols + 1;
                    if (cell >= 0) {
                        if (field[cell] != coveredBombCell) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + cols + 1;
                    if (cell < allCells) {
                        if (field[cell] != coveredBombCell) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + 1;
                    if (cell < allCells) {
                        if (field[cell] != coveredBombCell) {
                            field[cell] += 1;
                        }
                    }
                }
            }
        }
    }

    /**
     * findEmptyCells(int x) recursively finds all the empty cells in the minefield adjacent to the given
     * empty cell
     * @param x - represents the value of the empty cell.
     */
    private void findEmptyCells(int x) {
        int currentCol = x % cols;
        if (currentCol > 0) {
            cell = x - cols - 1;
            if (cell >= 0) {
                if (field[cell] > bombCell) {
                    field[cell] -= cellCover;
                    if (field[cell] == emptyCell) {
                        findEmptyCells(cell);
                    }
                }
            }

            cell = x - 1;
            if (cell >= 0) {
                if (field[cell] > bombCell) {
                    field[cell] -= cellCover;
                    if (field[cell] == emptyCell) {
                        findEmptyCells(cell);
                    }
                }
            }

            cell = x + cols - 1;
            if (cell < allCells) {
                if (field[cell] > bombCell) {
                    field[cell] -= cellCover;
                    if (field[cell] == emptyCell) {
                        findEmptyCells(cell);
                    }
                }
            }
        }

        cell = x - cols;
        if (cell >= 0) {
            if (field[cell] > bombCell) {
                field[cell] -= cellCover;
                if (field[cell] == emptyCell) {
                    findEmptyCells(cell);
                }
            }
        }

        cell = x + cols;
        if (cell < allCells) {
            if (field[cell] > bombCell) {
                field[cell] -= cellCover;
                if (field[cell] == emptyCell) {
                    findEmptyCells(cell);
                }
            }
        }

        if (currentCol < (cols - 1)) {
            cell = x - cols + 1;
            if (cell >= 0) {
                if (field[cell] > bombCell) {
                    field[cell] -= cellCover;
                    if (field[cell] == emptyCell) {
                        findEmptyCells(cell);
                    }
                }
            }

            cell = x + cols + 1;
            if (cell < allCells) {
                if (field[cell] > bombCell) {
                    field[cell] -= cellCover;
                    if (field[cell] == emptyCell) {
                        findEmptyCells(cell);
                    }
                }
            }

            cell = x + 1;
            if (cell < allCells) {
                if (field[cell] > bombCell) {
                    field[cell] -= cellCover;
                    if (field[cell] == emptyCell) {
                        findEmptyCells(cell);
                    }
                }
            }
        }

    }

    /**
     * MinesAdapter class extends MouseAdapter class and overrides the mousePressed(MouseEvent e)
     * function in that class.
     */
    private class MinesAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            int cellCol = x / cellSize;
            int cellRow = y / cellSize;

            boolean needRepaint = false;

            //start timer on first click
            if (!timeStarted) {
                timer.start();
                timeStarted = true;
            }
            if (inGame) {
                if ((x < cols * cellSize) && (y < rows * cellSize)) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        if (field[(cellRow * cols) + cellCol] > bombCell) {
                            needRepaint = true;
                            if (field[(cellRow * cols) + cellCol] <= coveredBombCell) {
                                if (minesLeft > 0) {
                                    field[(cellRow * cols) + cellCol] += markedBombCell;
                                    minesLeft--;
                                } else {
                                    status = "No flags left";
                                }
                            } else {

                                field[(cellRow * cols) + cellCol] -= markedBombCell;
                                minesLeft++;
                            }
                        }
                    } else {
                        if (field[(cellRow * cols) + cellCol] > coveredBombCell) {
                            return;
                        }
                        if ((field[(cellRow * cols) + cellCol] > bombCell)
                                && (field[(cellRow * cols) + cellCol] < markedBombCell)) {
                            field[(cellRow * cols) + cellCol] -= cellCover;
                            needRepaint = true;
                            if (field[(cellRow * cols) + cellCol] == bombCell) {
                                inGame = false;
                            }
                            if (field[(cellRow * cols) + cellCol] == emptyCell) {
                                findEmptyCells((cellRow * cols) + cellCol);
                            }
                        }
                    }
                    if (needRepaint) {
                        repaint();
                    }
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw the timer
        String time = "Time: " + seconds;
        int timeWidth = time.length() * 7 + 8;
        g.drawRect(boardWidth - timeWidth, 0, timeWidth, 20);
        g.drawString(time, boardWidth - timeWidth + 5, 15);

        int uncover = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int cell = field[(i * cols) + j];
                if (inGame && cell == bombCell) {
                    inGame = false;
                }
                int drawFlag = 11;
                int drawCover = 10;
                if (!inGame) {

                    if (cell == coveredBombCell) {
                        cell = 9;
                    } else if (cell == markedBombCell) {
                        cell = drawFlag;
                    } else if (cell > coveredBombCell) {
                        cell = 12;
                    } else if (cell > bombCell) {
                        cell = drawCover;
                    }
                } else {
                    if (cell > coveredBombCell) {
                        cell = drawFlag;
                    } else if (cell > bombCell) {
                        cell = drawCover;
                        uncover++;
                    }
                }
                g.drawImage(img[cell], (j * cellSize),
                        (i * cellSize), this);
            }
        }
        setStatus("Bombs Left: " + minesLeft);
        if (uncover == 0 && inGame) {
            inGame = false;
            setStatus("You won! :)");
        } else if (!inGame) {
            setStatus("You lost! :(");
        }

        // draw game status
        int center = status.length() * 7 + 8;
        g.drawRect(boardWidth - timeWidth - center, 0, center, 20);
        g.drawString(status, boardWidth - timeWidth - center + 15, 15);
    }

}
