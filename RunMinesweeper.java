
package org.cis120.minesweeper;

// imports necessary libraries for Java swing

import java.awt.*;
import javax.swing.*;

/*
 * RunMinesweeper
 * Game Main class that specifies the frame and widgets of the GUI
 */

public class RunMinesweeper implements Runnable {

    public void run() {
        // top-level frame in which game components live
        final JFrame frame = new JFrame("Minesweeper");
        frame.setLocation(200, 200);

        // game board
        final MineField board = new MineField("");
        frame.add(board, BorderLayout.CENTER);

        // instructions and settings panel
        final JPanel settings_panel = new JPanel();
        frame.add(settings_panel, BorderLayout.SOUTH);

        // instructions window
        final JFrame instrFrame = new JFrame("Instructions");
        instrFrame.setSize(400, 400);
        instrFrame.setLocation(200, 200);

        // settings window
        final JFrame setFrame = new JFrame("Settings");
        setFrame.setSize(400, 400);
        setFrame.setLocation(200, 200);

        // create instructions button functionality
        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(e -> {
            String message = "Welcome to Minesweeper!\n\n";
            message += """
                    The game is simple: avoid the bombs. Left-click on
                    a square to reveal its state and right-click on it to
                    flag it.

                    To win, you need to correctly flag all of the bombs
                    in the game, and you can keep track of your progress
                    using the counter on the right.There are three difficulty\s
                    levels, and you can choose which
                    one to play at in the settings menu. And if you ever
                    want to start a new game, click the "new game" button
                    at the top. Lastly, if you want to export the game, click on\s
                     the " export " button at the top.

                    Good luck!""";
            JOptionPane.showMessageDialog(instrFrame, message, "Instructions",
                    JOptionPane.PLAIN_MESSAGE);
        });

        // create settings button functionality
        final JButton settings = new JButton("Settings");
        settings.addActionListener(e -> {
            String message = "Select a level:";
            String[] choices = {"Beginner (9 x 9 with 10 bombs)",
                    "Intermediate (16 x 16 with 40 bombs)", "Expert (23 x 23 with 100 bombs)"};
            String level = (String) JOptionPane.showInputDialog(setFrame, message, "Settings",
                    JOptionPane.PLAIN_MESSAGE, null, choices, "Name");
            // update game size and number of bombs based on user's choice
            if (level != null) {
                char c = level.charAt(0);
                if (c == 'B') {
                    board.setLevel(c);
                }
                if (c == 'I') {
                    board.setLevel(c);
                }
                if (c == 'E') {
                    board.setLevel(c);
                }
            }
        });

        settings_panel.add(instructions);
        settings_panel.add(settings);

        // reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        final JButton reset = new JButton("New Game");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        GameState game = new GameState();
        //Button that allows user to export text file of game
        final JButton export = new JButton("Export");
        export.addActionListener(e -> game.exportGame());
        control_panel.add(export);

        // put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // start game
        board.reset();
    }

    /*
     * Main method run to start and run the game. Initializes the GUI elements
     * specified in Game and runs it. IMPORTANT: Do NOT delete! You MUST include
     * this in the final submission of your game.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new RunMinesweeper());
    }
}
