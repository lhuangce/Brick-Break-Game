package ui;

import model.BrickBreakGame;
import model.exceptions.GameResumeException;
import model.exceptions.MaxBricksException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Integer.parseInt;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Represents the main window in which the brick break game is played. Based on CPSC 210 Lab 3 PaddleBall Project.
 */
//@SuppressWarnings("serial")
public class BrickBreakFrame extends JFrame {
    private static final int INTERVAL = 20;
    private static final String JSON_STORE = "./data/game.json";

    private BrickBreakGame game;
    private final GamePanel gp;
    private final JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private final JsonReader jsonReader = new JsonReader(JSON_STORE);
    private Timer timer;

    // Constructs main window
    // EFFECTS: sets up window in which the paddle ball game will be played
    BrickBreakFrame() {
        super("Brick Break Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        initGame();
        gp = new GamePanel(game);
        add(gp);
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
        addTimer();
    }

    private void initGame() {
        boolean keepGoing = true;
        int brickCount;

        boolean load = toLoad();

        if (!load) {
            while (keepGoing) {
                String input = showInputDialog("Choose the starting number of bricks (1-30):");
                if (input == null) {
                    initGame();
                    break;
                }
                brickCount = brickEntry(input);

                if (brickCount != 0) {
                    keepGoing = false;
                    game = new BrickBreakGame(brickCount);
                }
            }
        } else {
            loadGame();
        }
    }

    private boolean toLoad() {
        Object[] options = {"Load from file", "New game"};
        int n = JOptionPane.showOptionDialog(this, "Please choose from the following options:",
                "Start", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[1]);

        if (n == -1) {
            System.exit(0);
        }
        return n == 0;
    }

    // Set up timer
    // MODIFIES: none
    // EFFECTS:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        timer = new Timer(INTERVAL, ae -> {
            game.update();
            gp.repaint();

            if (game.gameOver()) {
                timer.stop();
            } else if (game.isPaused()) {
                timer.stop();
            }
        });

        timer.start();
    }

    // Centres frame on desktop
    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    // EFFECTS: produces number of bricks to start game with based on user input
    private int brickEntry(String input) {
        int brickCount = 0;

        try {
            brickCount = checkMaxBricks(input);
        } catch (MaxBricksException e) {
            showMessageDialog(this, "Maximum number of bricks cannot exceed 30!");
        } catch (NumberFormatException e) {
            showMessageDialog(this, "Please enter a numeric value from 1 to 30.");
        }
        return brickCount;
    }

    private int checkMaxBricks(String input) throws MaxBricksException, NumberFormatException {
        int brickCount;
        brickCount = parseInt(input);

        if (brickCount > 30) {
            throw new MaxBricksException();
        }
        return brickCount;
    }

    // MODIFIES: this
    // EFFECTS: processes commands from user input (buttons, if necessary)
    private void processCommand(int keyCode) throws GameResumeException {
        if (game.gameOver()) {
            if (keyCode == KeyEvent.VK_R) {
                new BrickBreakFrame();
            } else if (keyCode == KeyEvent.VK_Q) {
                System.exit(0);
            }
        } else if (keyCode == KeyEvent.VK_S && game.isPaused()) {
            saveGame();
        } else if (keyCode == KeyEvent.VK_Q && game.isPaused()) {
            System.exit(0);
        } else {
            game.gameAction(keyCode);
        }
    }

    // EFFECTS: saves the game to file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            showMessageDialog(this, "Saved current game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads game from file
    private void loadGame() {
        try {
            game = jsonReader.read();
            showMessageDialog(this, "Loaded game from " + JSON_STORE);
        } catch (IOException e) {
            showMessageDialog(this, "Unable to read from file: " + JSON_STORE);
        }
    }

    /**
     * A key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent ke) {
            try {
                processCommand(ke.getKeyCode());
            } catch (GameResumeException e) {
                timer.restart();
            }
        }
    }
}
