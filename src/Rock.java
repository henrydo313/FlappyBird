import bagel.*;

/** Class to create a Rock weapon */
public class Rock extends Weapon{

    private final Image ROCK_IMAGE = new Image("res/level-1/rock.png");

    /** Create a rock at the right border of the game window
     * and at a random y position between 100 and 500 */
    public Rock() {
        super();
        weaponImage = ROCK_IMAGE;
        range = 25;
    }
}
