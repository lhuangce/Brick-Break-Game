package ui;

import model.Ball;
import model.BrickBreakGame;
import model.exceptions.MaxBricksException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Begins the game with text representations of game events (based on TellerApp from project stage 1 demo)
 */

public class BrickBreakApp {
    private static final String JSON_STORE = "./data/game.json";
    private BrickBreakGame game;
    private Scanner input = new Scanner(System.in);
    private KeyListener keyHandler = new KeyHandler();
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    // EFFECTS: runs brick break game application
    public BrickBreakApp() throws InterruptedException {
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: processes user input during game
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void runGame() throws InterruptedException {
        boolean keepGoing = true;
        String command;

        init();

        while (!game.gameOver()) {
            if (!game.isPaused()) {
                while (true) {
                    doUpdate();
                    if (game.isPaused()) {
                        break;
                    }
                    Thread.sleep(100L);
                }
            } else {
                while (game.isPaused()) {
                    System.out.println("Press 'p' again to resume game, 's' to save game, 'q' to quit.");
                    command = input.next();
                    command = command.toLowerCase();
                    if (command.equals("p")) {
                        game.gameAction(KeyEvent.VK_SPACE);
                    } else if (command.equals("s")) {
                        saveGame();
                    } else if (command.equals("q")) {
                        keepGoing = false;
                        System.exit(0);
                    }
                }
            }
        }
        System.out.println("Game over! Press 'r' to restart or 'q' to quit");

        while (keepGoing) {
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("r")) {
                keepGoing = false;
                break;
            } else if (command.equals("q")) {
                keepGoing = false;
                System.exit(0);
            }
        }
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: initializes scanner, input, key handler, and initial game state
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void init() {
        boolean keepGoing = true;
        boolean toLoad = false;
        String command;
        int brickCount = 0;

        while (keepGoing) {
            System.out.println("Press 'l' to load the saved game, or any other key to start a new game.");
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("l")) {
                toLoad = true;
                break;
            }
            System.out.println("Choose the starting number of bricks (1-30):");
            brickCount = brickEntry(input);
            if (brickCount != 0) {
                keepGoing = false;
            }
        }

        if (toLoad) {
            loadGame();
        } else {
            game = new BrickBreakGame(brickCount);
            Ball ball = game.getBall();

            System.out.println("New game with " + brickCount + " bricks.");
            System.out.println("Ball at (x = " + ball.getX() + ", y = "
                    + ball.getY() + "), moving at (dx = "
                    + ball.getDx() + ", dy = " + ball.getDy() + ").");
            System.out.println("Paddle at (x = " + game.getPaddle().getX() + ").");
        }
    }

    // EFFECTS: produces number of bricks to start game with based on user input
    private int brickEntry(Scanner s) {
        int brickCount = 0;
        try {
            brickCount = checkMaxBricks(s);
        } catch (MaxBricksException e) {
            System.out.println("Maximum number of bricks cannot exceed 30!");
        }
        return brickCount;
    }

    private int checkMaxBricks(Scanner s) throws MaxBricksException {
        int brickCount = s.nextInt();

        if (brickCount > 30) {
            throw new MaxBricksException();
        }
        return brickCount;
    }

    // MODIFIES: this
    // EFFECTS: updates game state
    private void doUpdate() {
        String command;

        game.update();

        System.out.println("Press 'a' to move the paddle left, 'd' to move right, or 'p' to pause the game.");

        command = input.next();
        command = command.toLowerCase();

        processCommandTemp(command);
    }

    // MODIFIES: this
    // EFFECTS: processes commands from user input (temporary)
    private void processCommandTemp(String command) {
        if (command.equals("a")) {
            game.gameAction(KeyEvent.VK_LEFT);
        } else if (command.equals("d")) {
            game.gameAction(KeyEvent.VK_RIGHT);
        } else if (command.equals("p")) {
            game.gameAction(KeyEvent.VK_SPACE);
        }
    }

    // EFFECTS: saves the game to file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved current game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads game from file
    private void loadGame() {
        try {
            game = jsonReader.read();
            System.out.println("Loaded game from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            game.gameAction(e.getKeyCode());
        }
    }
}