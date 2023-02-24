import bagel.*;
import bagel.util.*;

/** Class to create a weapon */
public class Weapon implements Destroyable {

    protected Image weaponImage;
    protected int range;

    private final int MIN_Y = 100;
    private final int MAX_Y = 500;
    private final double WEAPON_FIRE_SPEED = 5;
    private double weaponSpeed = PipeSet.pipeSpeed;
    private double y;
    private double x = Window.getWidth();
    private boolean active;
    private boolean pickedUp;

    /** Create a weapon at the right border of the game window
     * and at a random y position between 100 and 500 */
    public Weapon() {
        y = randomY();
        deactivate();
        pickedUp = false;
    }

    /** Update and render weapon moving from the right to the left of the screen */
    public void update() {
        render();
        weaponSpeed = PipeSet.pipeSpeed;
        x -= weaponSpeed;
    }

    /** Render weapon when it is fired */
    public void renderFiredWeapon() {
        x += WEAPON_FIRE_SPEED;
        render();
    }

    /** Draw an image of the weapon on screen */
    public void render() {
        weaponImage.draw(x, y);
    }

    /** Set the weapon status to active */
    public void activate() {
        active = true;
    }

    /** Set the weapon status to inactive */
    public void deactivate() {
        active = false;
    }

    /** Create a Rectangle defined by the weapon's image centered at its current location
     * @return Returns a Rectangle around the weapon image */
    public Rectangle getBox() {
        return weaponImage.getBoundingBoxAt(new Point(x, y));
    }

    /** Check if the weapon has been picked up
     * @return Returns the weapon's picked up status */
    public boolean isPickedUp() {
        return pickedUp;
    }

    /** Set the weapon's picked up status
     * @param b Picked up status of weapon */
    public void setPickedUp(boolean b) {
        pickedUp = b;
    }

    /** Check if the weapon has been fired
     * @return Returns fire status of weapon */
    public boolean isActive() {
        return active;
    }

    /** Set weapon's x position
     * @param x X position of weapon */
    public void setX(double x) {
        this.x = x;
    }

    /** Set weapon's y position
     * @param y Y position of weapon */
    public void setY(double y) {
        this.y = y;
    }

    /** Find the range of the weapon
     * @return Returns weapon's range */
    public int getRange() {
        return range;
    }

    /** Deactivate and remove weapon from screen */
    @Override
    public void destroy() {
        x = -weaponImage.getWidth();
        deactivate();
        pickedUp = false;
    }

    private double randomY() {
        return (Math.random() * (MAX_Y - MIN_Y + 1)) + MIN_Y;
    }
}
