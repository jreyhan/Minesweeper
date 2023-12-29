package org.cis120.minesweeper;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameState {
    private final MineField board = new MineField("");
    private String path;

    /**
     * stringsToFile(String toWrite) turns the string representations into files with
     * the file name being the unique timestamp.
     * @param toWrite the String containing board information and cell states
     */
    public void stringsToFile(String toWrite) {
        BufferedWriter br;

        try {
            int currentTime = board.getTime();
            path = "src/main/java/org/cis120/minesweeper";
            String timeAndFilePath = path + "/" +
                    new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
            br = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(timeAndFilePath+".txt"), StandardCharsets.UTF_8));
            br.write(toWrite + "\n");
            br.write(board + "\n"+ currentTime);

            br.flush();
            br.close();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * getBoardInfo() translates the current visibility of all the cells on the
     * board into a String.
     * @return String representation of the visibility of all cells in the board
     */
    public String getBoardInfo() {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (board.getCell(j) == 0) {
                    s.append("E");
                } else if (board.getCell(j) == 1 ||
                        board.getCell(j) == 2 ||
                        board.getCell(j) == 3 ||
                        board.getCell(j) == 4 ||
                        board.getCell(j) == 5 ||
                        board.getCell(j) == 6 ||
                        board.getCell(j) == 7 ||
                        board.getCell(j) == 8) {
                    s.append("N");
                } else if (board.getCell(j) == 9) {
                    s.append("B");
                } else if (board.getCell(j) == 11 ||
                        board.getCell(j) == 12) {
                    s.append("F");
                } else if (board.getCell(j) == 10) {
                    s.append("H");
                }
            }
            s.append("\n");
        }
        s.append(board.getStatus());
        return s.toString();
    }

    /**
     * exportGameState() exports the current game into a .txt file and puts file in project folder
     */
    public void exportGame() {
        JOptionPane.showMessageDialog(null, "File exported to the files folder!");
        stringsToFile(getBoardInfo());
    }
}

