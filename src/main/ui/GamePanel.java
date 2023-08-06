package ui;

import model.*;
import model.BrickBreakGame;

import javax.swing.*;
import java.awt.*;

/**
 * The panel in which the game is rendered. Based on CPSC 210 Lab 3 PaddleBall Project.
 */
public class GamePanel extends JPanel {

    private static final String OVER = "Game over!";
    private static final String OVER_OPTIONS = "Press 'r' to restart, or 'q' to quit.";
    private static final String PAUSE = "Game paused! Press the space bar to resume, 's' to save, or 'q' to quit.";

    private final BrickBreakGame game;

    // Constructs a game panel
    // EFFECTS: sets size and background colour of panel,
    //           updates this with the game to be displayed
    GamePanel(BrickBreakGame g) {
        setPreferredSize(new Dimension(BrickBreakGame.WIDTH, BrickBreakGame.HEIGHT));
        setBackground(Color.BLACK);
        this.game = g;
    }

    // MODIFIES: g
    // EFFECTS: draws components of game onto g
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);

        if (game.gameOver()) {
            gameOver(g);
        } else if (game.getBricks().isEmpty()) {
            gameOver(g);
        } else if (game.isPaused()) {
            pauseMenu(g);
        }
    }

    // Draws the game
    // MODIFIES: g
    // EFFECTS:  draws the game onto g
    private void drawGame(Graphics g) {
        drawPaddle(g);
        drawBall(g);
        drawBricks(g);
    }

    // Draw the paddle
    // MODIFIES: g
    // EFFECTS:  draws the paddle onto g
    private void drawPaddle(Graphics g) {
        Paddle t = game.getPaddle();
        Color savedCol = g.getColor();
        g.setColor(Paddle.COLOR);
        g.fillRect(t.getX() - Paddle.SIZE_X / 2, Paddle.Y_POS - Paddle.SIZE_Y / 2, Paddle.SIZE_X, Paddle.SIZE_Y);
        g.setColor(savedCol);
    }

    // Draw the ball
    // MODIFIES: g
    // EFFECTS:  draws the ball onto g
    private void drawBall(Graphics g) {
        Ball b = game.getBall();
        Color savedCol = g.getColor();
        g.setColor(Ball.COLOR);
        g.fillOval(b.getX() - Ball.SIZE / 2, b.getY() - Ball.SIZE / 2, Ball.SIZE, Ball.SIZE);
        g.setColor(savedCol);
    }

    // Draw the bricks
    // MODIFIES: g
    // EFFECTS: draws the bricks onto g
    private void drawBricks(Graphics g) {
        for (Brick b : game.getBricks()) {
            drawBrick(g, b);
        }
    }

    // Draw a brick
    // MODIFIES: g
    // EFFECTS: draws a brick onto g
    private void drawBrick(Graphics g, Brick b) {
        Color savedCol = g.getColor();
        g.setColor(Brick.COLOR);
        g.fillRect(b.getX() - Brick.SIZE_X / 2, b.getY() - Brick.SIZE_Y / 2, Brick.SIZE_X, Brick.SIZE_Y);
        g.setColor(savedCol);
        g.drawRect(b.getX() - Brick.SIZE_X / 2, b.getY() - Brick.SIZE_Y / 2, Brick.SIZE_X, Brick.SIZE_Y);
    }

    // Draws the "pause" message
    // MODIFIES: g
    // EFFECTS:  draws "pause" onto g
    private void pauseMenu(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(PAUSE, g, fm, BrickBreakGame.HEIGHT / 2);
        g.setColor(saved);
    }

    // Draws the "game over" message and options instructions
    // MODIFIES: g
    // EFFECTS:  draws "game over" and options instructions onto g
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(OVER, g, fm, BrickBreakGame.HEIGHT / 2);
        centreString(OVER_OPTIONS, g, fm, BrickBreakGame.HEIGHT / 2 + 50);
        g.setColor(saved);
    }

    // Centres a string on the screen
    // MODIFIES: g
    // EFFECTS:  centres the string str horizontally onto g at vertical position y
    private void centreString(String str, Graphics g, FontMetrics fm, int y) {
        int width = fm.stringWidth(str);
        g.drawString(str, (BrickBreakGame.WIDTH - width) / 2, y);
    }
}
