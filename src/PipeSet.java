import bagel.*;
import bagel.util.*;

/**
 * getBox functions provided by Betty Lin in "ShadowFlap answer project1"
 *
 * Class to create a set of 2 pipes
 */

public abstract class PipeSet implements Destroyable {

    /** Initial rate of spawning pipes in frames per pipe set */
    public static final int INIT_SPAWN_FRAME = 100;
    /** Current rate of spawning pipes in frames per pipe set */
    public static int spawnFrame = INIT_SPAWN_FRAME;
    /** Initial speed of pipes in pixels */
    public static final double INIT_PIPE_SPEED = 5;     // initial speed set to 5 (instead of 3) for better gameplay
    /** Current speed of pipes in pixels */
    public static double pipeSpeed = INIT_PIPE_SPEED;

    protected final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI);
    protected Image pipeImage;
    protected double topPipeY;
    protected double bottomPipeY;
    protected double pipeX = Window.getWidth();

    private final int PIPE_GAP = 168;
    private final int PIPE_IMAGE_HEIGHT = 768;
    private boolean birdPassed;

    /** Create a set of top and bottom pipes with the gap between the pipes
     * starting at a random y value between 100 and 500 */
    public PipeSet() {
        topPipeY = -PIPE_IMAGE_HEIGHT / 2.0 + randomisePipeY();
        bottomPipeY = topPipeY + PIPE_IMAGE_HEIGHT + PIPE_GAP;
        birdPassed = false;
    }

    /** Render and update location of pipe set */
    public void update() {
        renderPipeSet();
        pipeX -= pipeSpeed;
    }

    /** Draw the top and bottom pipes on the screen */
    public void renderPipeSet() {
        pipeImage.draw(pipeX, topPipeY);
        pipeImage.draw(pipeX, bottomPipeY, ROTATOR);
    }

    /** Remove pipe from the game screen */
    @Override
    public void destroy() {
        pipeX = -pipeImage.getWidth();
        birdPassed = true;
    }

    /** Set whether pipes have passed a Bird object
     * @param b Status of whether pipe has passed Bird */
    public void setBirdPassed(boolean b) {
        birdPassed = b;
    }

    /** Check if pipes have passed a Bird object
     * @return Returns whether pipe has passed bird yet */
    public boolean getBirdPassed() {
        return birdPassed;
    }

    /** Create a Rectangle defined by the top pipe's image centered at its current location
     * @return Returns a Rectangle around the top pipe image */
    public Rectangle getTopBox() {
        return pipeImage.getBoundingBoxAt(new Point(pipeX, topPipeY));
    }

    /** Create a Rectangle defined by the bottom pipe's image centered at its current location
     * @return Returns a Rectangle around the bottom pipe image */
    public Rectangle getBottomBox() {
        return pipeImage.getBoundingBoxAt(new Point(pipeX, bottomPipeY));
    }

    protected abstract int randomisePipeY();
}
