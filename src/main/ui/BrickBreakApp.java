package ui;

import model.Ball;
import model.BrickBreakGame;
import model.exceptions.MaxBricksException;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

/**
 * Begins the game with text representations of game events (based on TellerApp from project stage 1 demo)
 */

public class BrickBreakApp {
    private BrickBreakGame game;
    private KeyEvent input;
    private Scanner scanner;
    private KeyListener keyHandler;

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
                    System.out.println("Press 'p' again to resume game, 'q' to quit.");
                    command = scanner.next();
                    command = command.toLowerCase();
                    if (command.equals("p")) {
                        game.gameAction(KeyEvent.VK_P);
                    } else if (command.equals("q")) {
                        keepGoing = false;
                        System.exit(0);
                    }
                }
            }
        }
        System.out.println("Game over! Press 'r' to restart or 'q' to quit");

        while (keepGoing) {
            command = scanner.next();
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
    private void init() {
        scanner = new Scanner(System.in);
        input = null;
        keyHandler = new KeyHandler();
        boolean keepGoing = true;
        int brickCount = 0;

        while (keepGoing) {
            System.out.println("Choose the starting number of bricks (1-30):");
            brickCount = brickEntry(scanner);
            if (brickCount != 0) {
                keepGoing = false;
            }
        }

        game = new BrickBreakGame(brickCount);
        Ball ball = game.getBall();

        System.out.println("New game with " + brickCount + " bricks.");
        System.out.println("Ball at (x = " + ball.getX() + ", y = "
                + ball.getY() + "), moving at (dx = "
                + ball.getDx() + ", dy = " + ball.getDy() + ").");
        System.out.println("Paddle at (x = " + game.getPaddle().getX() + ").");
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

        command = scanner.next();
        command = command.toLowerCase();

        processCommandTemp(command);
    }

    // MODIFIES: this
    // EFFECTS: processes commands from user input (temporary)
    private void processCommandTemp(String command) {
        if (command.equals("a")) {
            game.gameAction(KeyEvent.VK_A);
        } else if (command.equals("d")) {
            game.gameAction(KeyEvent.VK_D);
        } else if (command.equals("p")) {
            game.gameAction(KeyEvent.VK_P);
        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            input = e;
            game.gameAction(e.getKeyCode());
        }
    }
}