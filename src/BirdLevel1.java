import bagel.*;
import bagel.util.*;

/** Class to create a controllable bird entity */
public class BirdLevel1 extends Bird {

    private final Image WING_DOWN_IMAGE = new Image("res/level-1/birdWingDown.png");
    private final Image WING_UP_IMAGE = new Image("res/level-1/birdWingUp.png");
    private final int INITIAL_LIFE = 6;
    private boolean hasWeapon;

    /** Create a BirdLevel1 with centre at point (200, 350) that has 6 lives */
    public BirdLevel1() {
        super();
        wingDownImage = WING_DOWN_IMAGE;
        wingUpImage = WING_UP_IMAGE;
        lifeBar = new LifeBar(INITIAL_LIFE);
        boundingBox = wingDownImage.getBoundingBoxAt(new Point(X, y));
        hasWeapon = false;
    }

    /** Check if bird is holding a weapon
     * @return Returns true/false if bird is holding weapon or not */
    public boolean getHasWeapon() {
        return hasWeapon;
    }

    /** Set weapon status of bird */
    public void setHasWeapon(boolean b) {
        hasWeapon = b;
    }
}
