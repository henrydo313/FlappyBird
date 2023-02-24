import bagel.*;

/** Class to create a set of 2 plastic pipes */
public class PlasticPipeSet extends PipeSet implements Destroyable{

    private final Image PLASTIC_PIPE_IMAGE = new Image("res/level/plasticPipe.png");
    private final int HIGH_GAP_Y = 100;
    private final int MID_GAP_Y = 300;
    private final int LOW_GAP_Y = 500;
    private final int HIGH_PIPE = 3;
    private final int MID_PIPE = 2;
    private final int LOW_PIPE = 1;

    /** Create a set of top and bottom plastic pipes with the gap between the pipes
     * starting at a random y value between 100 and 500 */
    public PlasticPipeSet() {
        super();
        pipeImage = PLASTIC_PIPE_IMAGE;
    }

    @Override
    protected int randomisePipeY() {
        int rand = (int)(Math.random() * (HIGH_PIPE - LOW_PIPE + 1)) + LOW_PIPE;

        if (rand == HIGH_PIPE) {
            return HIGH_GAP_Y;
        } else if (rand == MID_PIPE) {
            return MID_GAP_Y;
        } else {
            return LOW_GAP_Y;
        }
    }

}
