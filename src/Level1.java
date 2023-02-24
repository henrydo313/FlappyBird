import bagel.*;
import bagel.util.*;
import java.util.ArrayList;

/** Class to generate level 1 of ShadowFlap */
public class Level1 extends Level {

    private final String SHOOT_MSG = "PRESS 'S' TO SHOOT";
    private final int SHOOT_MSG_OFFSET = 68;
    private ArrayList<Weapon> weapons;
    private ArrayList<Weapon> unusedWeapons;

    /** Create level 1 of ShadowFlap */
    public Level1() {
        super();
        backgroundImage = new Image("res/level-1/background.png");
        bird = new BirdLevel1();
        weapons = new ArrayList<>();
        unusedWeapons = new ArrayList<>();
    }

    /** Update state of the game, increasing/decrease game speed when L/K is pressed
     * @param input Input from keyboard */
    @Override
    public void update(Input input) {
        super.update(input);

        if (gameOn && !passed && bird.getLivesLeft() > 0) {

            // generate weapons
            spawnWeapon(weapons);

            for (Weapon w: weapons) {

                // check for weapon bird collision
                if (detectWeaponPickup(bird.getBox(), w.getBox()) && !w.isPickedUp() && !((BirdLevel1) bird).getHasWeapon()) {
                    w.setPickedUp(true);
                    ((BirdLevel1) bird).setHasWeapon(true);
                }

                // rendering if weapon is picked up
                if (w.isPickedUp() && !w.isActive()) {
                    w.setX(bird.getBox().right());
                    w.setY(bird.getY());
                    w.render();
                }

                // activate weapon
                if (w.isPickedUp() && input.wasPressed(Keys.S) && !w.isActive() && ((BirdLevel1) bird).getHasWeapon()) {
                    w.activate();
                    ((BirdLevel1) bird).setHasWeapon(false);
                }

                // rendering for fired weapon
                if (w.isActive()) {

                    // check collision with pipes when fired
                    for (PipeSet p: pipeSets) {
                        if (!p.getBirdPassed() && detectWeaponPipeCollision(w, p)) {
                            w.destroy();
                            p.destroy();
                            unusedWeapons.add(w);
                            unusedPipes.add(p);
                            score += 1;
                            framesRendered = 0;
                        }
                    }

                    // render it firing
                    if (framesRendered < w.getRange()) {
                        w.renderFiredWeapon();
                        framesRendered += 1;
                    } else {
                        w.destroy();
                        framesRendered = 0;
                        unusedWeapons.add(w);
                    }

                }

                // stop rendering weapon if exits the screen
                if (!w.isPickedUp() && w.getBox().right() > 0) {
                    w.update();
                } else if (w.getBox().right() <= 0){
                    unusedWeapons.add(w);
                }
            }

            // remove unused weapons
            weapons.removeAll(unusedWeapons);
            unusedWeapons.clear();
        }

        // render win screen
        if (score == WIN_SCORE) {
            gameOn = false;
            passed = true;
            renderWinScreen();
        }
    }

    @Override
    protected void renderInstructionScreen(Input input) {
        FONT.drawString(SHOOT_MSG, (Window.getWidth()/2.0-(FONT.getWidth(SHOOT_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SHOOT_MSG_OFFSET);
        super.renderInstructionScreen(input);
    }

    @Override
    protected void spawnPipeSet() {
        if (ShadowFlap.frameCount % PipeSet.spawnFrame == 0) {
            if (randBinary()) {
                pipeSets.add(new PlasticPipeSet());
            } else {
                pipeSets.add(new SteelPipeSet());
            }
        }
    }

    @Override
    protected boolean detectPipeCollision(Bird bird, PipeSet pipe) {
        Rectangle birdBox = bird.getBox();
        boolean collided;

        collided = super.detectPipeCollision(bird, pipe);
        if (pipe instanceof SteelPipeSet) {
            if (((SteelPipeSet) pipe).getRenderingFlames()) {
                collided = detectCollision(birdBox,
                        ((SteelPipeSet) pipe).getTopFlameBox(),
                        ((SteelPipeSet) pipe).getBotFlameBox());
            }
        }

        return collided;
    }

    // spawn weapon
    private void spawnWeapon(ArrayList<Weapon> weaponList) {
        if (ShadowFlap.frameCount % PipeSet.spawnFrame == PipeSet.spawnFrame / 2 && randBinary()) {
            if (randBinary()) {
                weaponList.add(new Rock());
            } else {
                weaponList.add(new Bomb());
            }
        }
    }

    // generate random true/false
    private boolean randBinary () {
        int rand = (int)(Math.random() * 2);
        if (rand == 1) {
            return true;
        } else {
            return false;
        }
    }

    // check whether bird has picked up weapon
    private boolean detectWeaponPickup(Rectangle birdBox, Rectangle weaponBox) {
        return birdBox.intersects(weaponBox);
    }

    // check if fired weapon hits pipe
    private boolean detectWeaponPipeCollision(Weapon w, PipeSet p) {
        Rectangle weaponBox = w.getBox();
        Rectangle topPipeBox = p.getTopBox();
        Rectangle bottomPipeBox = p.getBottomBox();

        if ((w instanceof Rock) && (p instanceof SteelPipeSet)) {
            if (detectCollision(weaponBox, topPipeBox, bottomPipeBox)) {
                w.destroy();
                unusedWeapons.add(w);
            }
            return false;
        }

        return detectCollision(weaponBox, topPipeBox, bottomPipeBox);
    }
}