import bagel.*;

/** Class to generate level 0 of ShadowFlap */
public class Level0 extends Level {

    /** Create level 0 of ShadowFlap */
    public Level0() {
        super();
        backgroundImage = new Image("res/level-0/background.png");
        bird = new BirdLevel0();
    }

    /** Update state of the game, increasing/decrease game speed when L/K is pressed
     * @param input Input from keyboard */
    @Override
    public void update(Input input) {
        super.update(input);

        // render level-up screen
        if (score == LEVEL1_SCORE) {
            gameOn = false;
            if (framesRendered < LEVEL_UP_SCREEN_FRAME) {
                renderLevelUpScreen();
                framesRendered += 1;
            } else {
                passed = true;
            }
        }
    }
}