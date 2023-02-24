import bagel.*;
import bagel.util.*;

/** Class to create a controllable bird entity */
public class BirdLevel0 extends Bird {

    private final Image WING_DOWN_IMAGE = new Image("res/level-0/birdWingDown.png");
    private final Image WING_UP_IMAGE = new Image("res/level-0/birdWingUp.png");
    private final int INITIAL_LIFE = 3;

    /** Create a BirdLevel0 with centre at point (200, 350) that has 3 lives */
    public BirdLevel0() {
        super();
        wingDownImage = WING_DOWN_IMAGE;
        wingUpImage = WING_UP_IMAGE;
        lifeBar = new LifeBar(INITIAL_LIFE);
        boundingBox = wingDownImage.getBoundingBoxAt(new Point(X, y));
    }
}
