package ui;

import model.BrickBreakGame;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the panel in which the scoreboard is displayed. From CPSC 210 SpaceInvadersBase
 */
public class ScorePanel extends JPanel {
    private static final String SCORE_TXT = "Score: ";
//    private static final String MISSILES_TXT = "Missiles remaining: ";
    private static final int LBL_WIDTH = 100;
    private static final int LBL_HEIGHT = 30;
    private final BrickBreakGame game;
    private final JLabel scoreLabel;
//    private JLabel missilesLbl;

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
//        missilesLbl = new JLabel(MISSILES_TXT + SIGame.MAX_MISSILES);
//        missilesLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        add(scoreLabel);
//        add(Box.createHorizontalStrut(10));
//        add(missilesLbl);
    }

    // Updates the score panel
    // MODIFIES: this
    // EFFECTS:  updates number of invaders shot and number of missiles
    //           remaining to reflect current state of game
    public void update() {
        scoreLabel.setText(SCORE_TXT + game.getScore());
//        missilesLbl.setText(MISSILES_TXT + (SIGame.MAX_MISSILES - game.getNumMissiles()));
        repaint();
    }
}