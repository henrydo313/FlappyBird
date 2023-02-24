import bagel.*;
import bagel.util.*;

/**
 * Text render functions provided by Betty Lin in "ShadowFlap answer project1"
 *
 * Class to create a controllable bird entity
 */

public class Bird {

    protected final double X = 200;
    protected final double FLY_SIZE = 6;
    protected final double FALL_SIZE = 0.4;
    protected final double Y_TERMINAL_VELOCITY = 10;
    protected Image wingDownImage;
    protected Image wingUpImage;
    protected LifeBar lifeBar;
    protected Rectangle boundingBox;
    protected double y;

    private final double INITIAL_Y = 350;
    private final double SWITCH_FRAME = 10;
    private double yVelocity;

    /** Create a Bird with centre at point (200, 350) */
    public Bird() {
        y = INITIAL_Y;
        yVelocity = 0;
    }

    /** Update state of bird, allowing it to fly when SPACE is pressed
     * @param input Input from keyboard */
    public void update(Input input) {
        if (input.wasPressed(Keys.SPACE)) {
            yVelocity = -FLY_SIZE;
        } else {
            yVelocity = Math.min(yVelocity + FALL_SIZE, Y_TERMINAL_VELOCITY);
        }
        render();
        y += yVelocity;
        lifeBar.update();
    }

    /** Reset location to (200, 350) and speed of bird to 0 */
    public void respawn() {
        y = INITIAL_Y;
        yVelocity = 0;
    }

    /** Find current x position of bird
     * @return Returns x value of bird's location */
    public double getX() {
        return X;
    }

    /** Find current y position of bird
     * @return Returns y value of bird's location */
    public double getY() {
        return y;
    }

    /** Creates a Rectangle defined by the bird image centred at its current point
     * @return Returns Rectangle define by bird */
    public Rectangle getBox() {
        return boundingBox;
    }

    /** Find how many lives bird currently has
     * @return Returns number of lives bird has */
    public int getLivesLeft() {
        return lifeBar.getLivesLeft();
    }

    /** Set how many lives bird has
     * @param numLives Number of lives bird has left */
    public void setLivesLeft(int numLives) {
        lifeBar.setLivesLeft(numLives);
    }

    private void render() {
        if (ShadowFlap.frameCount % SWITCH_FRAME == 0) {
            wingUpImage.draw(X, y);
            boundingBox = wingUpImage.getBoundingBoxAt(new Point(X, y));
        } else {
            wingDownImage.draw(X, y);
            boundingBox = wingDownImage.getBoundingBoxAt(new Point(X, y));
        }
    }
}
