import bagel.*;
import bagel.util.*;

/** Class to create a set of 2 steel pipes */
public class SteelPipeSet extends PipeSet implements Destroyable {

    private final Image STEEL_PIPE_IMAGE = new Image("res/level-1/steelPipe.png");
    private final Image FLAME_IMAGE = new Image("res/level-1/flame.png");
    private final int FLAME_SPAWN_FRAME = 20;
    private final int FLAME_DURATION = 30;
    private final int PIPE_Y_MAX = 500;
    private final int PIPE_Y_MIN = 100;
    private int flameFrames;
    private int framesRendered;
    private double topFlameY;
    private double bottomFlameY;
    private boolean renderingFlames;

    /** Create a set of top and bottom steel pipes with the gap between the pipes
     * starting at a random y value between 100 and 500 */
    public SteelPipeSet() {
        super();
        pipeImage = STEEL_PIPE_IMAGE;
        topFlameY =  getTopBox().bottom() +  FLAME_IMAGE.getHeight() / 2.0;
        bottomFlameY = getBottomBox().top() - FLAME_IMAGE.getHeight() / 2.0;
        flameFrames = 0;
        renderingFlames = false;
        framesRendered = 0;
    }

    /** Draw the top and bottom pipes along with flames spewing out of ends */
    @Override
    public void renderPipeSet() {
        super.renderPipeSet();

        // render flames
        if (flameFrames % FLAME_SPAWN_FRAME == 0 && !renderingFlames) {
            renderingFlames = true;
        }

        if (renderingFlames) {
            if (framesRendered < FLAME_DURATION) {
                FLAME_IMAGE.draw(pipeX, topFlameY);
                FLAME_IMAGE.draw(pipeX, bottomFlameY, ROTATOR);
                framesRendered += 1;
            } else {
                renderingFlames = false;
                framesRendered = 0;
            }
        }

        flameFrames += 1;
    }

    /** Create a Rectangle defined by the top pipe's flame image centered at its current location
     * @return Returns a Rectangle around the top pipe's flame image */
    public Rectangle getTopFlameBox() {
        return FLAME_IMAGE.getBoundingBoxAt(new Point(pipeX, topFlameY));
    }

    /** Create a Rectangle defined by the bottom pipe's flame image centered at its current location
     * @return Returns a Rectangle around the bottom pipe's flame image */
    public Rectangle getBotFlameBox() {
        return FLAME_IMAGE.getBoundingBoxAt(new Point(pipeX, bottomFlameY));
    }

    /** Check if the flames are currently being rendered
     * @return Returns whether flames are being rendered */
    public boolean getRenderingFlames() {
        return renderingFlames;
    }

    @Override
    protected int randomisePipeY() {
        return (int)(Math.random() * (PIPE_Y_MAX - PIPE_Y_MIN + 1)) + PIPE_Y_MIN;
    }
}
