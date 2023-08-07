package ui;

import model.BrickBreakGame;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the panel in which the scoreboard is displayed. From CPSC 210 SpaceInvadersBase
 */
public class ScorePanel extends JPanel {
    private static final String SCORE_TXT = "Score: ";
    private static final int LBL_WIDTH = 100;
    private static final int LBL_HEIGHT = 30;
    private final BrickBreakGame game;
    private final JLabel scoreLabel;

    // Constructs a score panel
    // EFFECTS: sets the background colour and draws the initial labels;
    //          updates this with the game whose score is to be displayed
    public ScorePanel(BrickBreakGame g) {
        game = g;
        setBackground(new Color(0, 200, 130));
        scoreLabel = new JLabel(SCORE_TXT + game.getScore(), SwingConstants.CENTER);
        scoreLabel.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(scoreLabel);
    }

    // Updates the score panel
    // MODIFIES: this
    // EFFECTS:  updates current score in game
    public void update() {
        scoreLabel.setText(SCORE_TXT + game.getScore());
        repaint();
    }
}