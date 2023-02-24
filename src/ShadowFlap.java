import bagel.*;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2021
 *
 * @author Henry Do
 * @version 1.0
 */

public class ShadowFlap extends AbstractGame {
    /** Width of game screen */
    public static final int SCREEN_W = 1024;
    /** Height of game screen */
    public static final int SCREEN_H = 768;
    /** Total frames game has run for */
    public static int frameCount;

    private Level level;
    private int currLevel;

    /** Create new ShadowFlap game */
    public ShadowFlap() {
        super(SCREEN_W, SCREEN_H, "ShadowFlap");
        frameCount = 0;
        level = new Level0();
        currLevel = 0;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        level.update(input);

        if (currLevel == 0 && level.getPassed()) {
            level = new Level1();
            currLevel += 1;
        }

        frameCount += 1;
    }
}
