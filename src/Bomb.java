import bagel.*;

/** Class to create a Bomb weapon */
public class Bomb extends Weapon{

    private final Image BOMB_IMAGE = new Image("res/level-1/bomb.png");

    /** Create a bomb at the right border of the game window
     * and at a random y position between 100 and 500 */
    public Bomb() {
        super();
        weaponImage = BOMB_IMAGE;
        range = 50;
    }

}
