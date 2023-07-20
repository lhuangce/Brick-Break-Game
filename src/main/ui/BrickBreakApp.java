package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.Ball;
import model.Brick;
import model.BrickBreakGame;
import model.Paddle;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import static com.googlecode.lanterna.gui2.dialogs.TextInputDialog.showNumberDialog;

/**
 * Begins the game (based on TerminalGame from SnakeConsole-Lanterna tutorial
 */
public class BrickBreakApp {
    private BrickBreakGame game;
    private Screen screen;
    private WindowBasedTextGUI endGui;

    /**
     * Begins the game and method does not leave execution
     * until game is complete.
     */
    public void start() throws IOException, InterruptedException {
        screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();
        TerminalSize terminalSize = screen.getTerminalSize();

        game = new BrickBreakGame(setBricks());

        beginTicks();
    }

    /**
     * Sets number of bricks to start game based on user input
     */
    private int setBricks() throws IOException {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);

        BigInteger val = showNumberDialog(textGui, "Start",
                "Choose the starting number of bricks (0-30)", "");
        return val.intValue();
    }

    /**
     * Begins the game cycle. Ticks once every BrickBreakGame.TICKS_PER_SECOND unless paused until
     * game has ended and the endGui has been exited.
     */
    private void beginTicks() throws IOException, InterruptedException {
        while (!game.gameOver() || endGui.getActiveWindow() != null) {
            if (!game.isPaused()) {
                tick();
                Thread.sleep(1000L / BrickBreakGame.TICKS_PER_SECOND);
            }
        }

        System.exit(0);
    }

    /**
     * Handles one cycle in the game by taking user input,
     * ticking the game internally, and rendering the effects
     */
    private void tick() throws IOException {
        //handleUserInput();

        game.update();

        //screen.setCursorPosition(new TerminalPosition(0, 0));
        screen.clear();
        render();
        screen.refresh();

        //screen.setCursorPosition(new TerminalPosition(screen.getTerminalSize().getColumns() - 1, 0));
    }

    /**
     * Renders the current screen.
     * Draws the end screen if the game has ended, otherwise
     * draws the ball, paddle, and bricks.
     */
    private void render() {
        if (game.gameOver()) {
            if (endGui == null) {
                drawEndScreen();
            }

            return;
        }

        drawBall();
        drawPaddle();
        drawBricks();
    }

    private void drawEndScreen() {
        endGui = new MultiWindowTextGUI(screen);

        new MessageDialogBuilder()
                .setTitle("Game over!")
                //.setText("You finished with a score of " + game.getScore() + "!")
                //.setText("Press R to restart!")
                .addButton(MessageDialogButton.Close)
                .build()
                .showDialog(endGui);
    }

    @SuppressWarnings({"checkstyle:AvoidEscapedUnicodeCharacters", "checkstyle:SuppressWarnings"})
    private void drawBall() {
        Ball ball = game.getBall();

        drawPosition(ball.getX(), ball.getY(), TextColor.ANSI.RED, '\u2B24', false);
    }

    @SuppressWarnings({"checkstyle:AvoidEscapedUnicodeCharacters", "checkstyle:SuppressWarnings"})
    private void drawPaddle() {
        Paddle paddle = game.getPaddle();

        drawPosition(paddle.getX(), paddle.getyPos(), TextColor.ANSI.BLUE, '\u2588', true);
    }

    @SuppressWarnings({"checkstyle:AvoidEscapedUnicodeCharacters", "checkstyle:SuppressWarnings"})
    private void drawBricks() {
        ArrayList<Brick> bricks = game.getBricks();

        for (Brick b : bricks) {
            drawPosition(b.getX(), b.getY(), TextColor.ANSI.YELLOW, '\u2610', true);
        }
    }

    /**
     * Draws a character in a given position on the terminal.
     * If wide, it will draw the character twice to make it appear wide.
     */
    private void drawPosition(int x, int y, TextColor color, char c, boolean wide) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(x * 2, y + 1, String.valueOf(c));

        if (wide) {
            text.putString(x * 2 + 1, y + 1, String.valueOf(c));
        }
    }
}
