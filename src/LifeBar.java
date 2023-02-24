import bagel.*;
import bagel.util.*;

/** Class to create life bar of full and empty hearts */
public class LifeBar {

    private final Image HEART_FULL_IMAGE = new Image("res/level/fullLife.png");
    private final Image HEART_EMPTY_IMAGE = new Image("res/level/noLife.png");
    private final int HEART_GAP = 50;
    private final Point INITIAL_LOC = new Point(100, 15);
    private int totalLives;
    private int livesLeft;

    /** Create life bar with specified number of lives
     * @param  numLives Number of lives */
    public LifeBar(int numLives) {
        totalLives = numLives;
        livesLeft = totalLives;
    }

    /** Update and render current life bar
     * with top-left of first life at (100, 15) */
    public void update() {
        double nextLifeX = INITIAL_LOC.x;
        double y = INITIAL_LOC.y;
        int i;

        // render fullLives
        for (i = 0; i < livesLeft; i++) {
            HEART_FULL_IMAGE.drawFromTopLeft(nextLifeX, y);
            nextLifeX += HEART_GAP;
        }

        // render noLives
        for (i = livesLeft; i < totalLives; i++) {
            HEART_EMPTY_IMAGE.drawFromTopLeft(nextLifeX, y);
            nextLifeX += HEART_GAP;
        }
    }

    /** Find how many lives life bar has
     * @return Returns count of number of lives in bar */
    public int getLivesLeft() {
        return livesLeft;
    }

    /** Set number of lives in bar
     * @param numLives Number of lives */
    public void setLivesLeft(int numLives) {
        livesLeft = numLives;
    }
}
