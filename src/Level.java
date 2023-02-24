import bagel.*;
import bagel.util.*;
import java.util.ArrayList;

/**
 * Text constants and rendering functions provided
 * by Betty Lin in "ShadowFlap answer project1"
 *
 * Class to generate a ShadowFlap level
 */

public class Level {

    protected final int LEVEL_UP_SCREEN_FRAME = 20;
    protected final int LEVEL1_SCORE = 10;
    protected final int WIN_SCORE = 30;
    protected final int FONT_SIZE = 48;
    protected final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);
    protected Image backgroundImage;
    protected Bird bird;
    protected ArrayList<PipeSet> pipeSets;
    protected ArrayList<PipeSet> unusedPipes;
    protected int framesRendered = 0;
    protected int score;
    protected boolean gameOn;
    protected boolean gameStarted;
    protected boolean passed;

    private final String INSTRUCTION_MSG = "PRESS SPACE TO START";
    private final String GAME_OVER_MSG = "GAME OVER!";
    private final String SCORE_MSG = "SCORE: ";
    private final String FINAL_SCORE_MSG = "FINAL SCORE: ";
    private final String LEVEL_UP_MSG = "LEVEL-UP!";
    private final String CONGRATS_MSG = "CONGRATULATIONS!";
    private final int SCORE_MSG_OFFSET = 75;
    private final double SCORE_MSG_X = 100;
    private final double SCORE_MSG_Y = 100;
    private final int START_SCORE = 0;
    private final int TIMESCALE_MIN = 1;
    private final int TIMESCALE_MAX = 5;
    private final double TIMESCALE_INCREMENT = 1.5;
    private int timescale;

    /** Create a level in the game */
    public Level() {
        pipeSets = new ArrayList<>();
        unusedPipes = new ArrayList<>();
        score = START_SCORE;
        gameOn = false;
        gameStarted = false;
        passed = false;
        resetTimescale();
    }

    /** Update state of the game, increasing/decrease game speed when L/K is pressed
     * @param input Input from keyboard */
    public void update(Input input) {
        backgroundImage.draw(Window.getWidth() / 2.0, Window.getHeight() /2.0);

        if(input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // load initial start screen
        if (!gameOn && !gameStarted) {
            renderInstructionScreen(input);
        }

        // game over
        if (bird.getLivesLeft() <= 0) {
            renderGameOverScreen();
        }

        // game is running
        if (gameOn && !passed && bird.getLivesLeft() > 0) {

            // if bird out of bounds
            if (birdOutOfBound()) {
                bird.setLivesLeft(bird.getLivesLeft() - 1);
                bird.respawn();
            }

            // increasing timescale
            if (input.wasPressed(Keys.L) && timescale < TIMESCALE_MAX) {
                increaseTimescale();
            }

            // decreasing timescale
            if (input.wasPressed(Keys.K) && timescale > TIMESCALE_MIN) {
                decreaseTimescale();
            }

            // generate pipes
            spawnPipeSet();

            for (PipeSet p: pipeSets) {

                // check collision
                if (detectPipeCollision(bird, p)) {
                    birdCollided(p);
                }

                // update score
                if (!p.getBirdPassed() && bird.getX() > p.getTopBox().right()) {
                    p.setBirdPassed(true);
                    score += 1;
                }

                // stop rendering pipes if exits the screen
                if (p.getTopBox().right() > 0) {
                    p.update();
                } else {
                    unusedPipes.add(p);
                }
            }

            // remove unused pipes
            pipeSets.removeAll(unusedPipes);
            unusedPipes.clear();

            bird.update(input);
            renderScore();
        }
    }

    // check for collision with pipe
    protected boolean detectPipeCollision(Bird bird, PipeSet pipe) {
        Rectangle birdBox = bird.getBox();
        Rectangle topPipeBox = pipe.getTopBox();
        Rectangle botPipeBox = pipe.getBottomBox();

        return detectCollision(birdBox, topPipeBox, botPipeBox);
    }

    // check for collision with any 2 image rectangles
    protected boolean detectCollision(Rectangle obj, Rectangle rect2, Rectangle rect3) {
        return obj.intersects(rect2) ||
                obj.intersects(rect3);
    }

    // spawn pipes
    protected void spawnPipeSet() {
        if (ShadowFlap.frameCount % PipeSet.spawnFrame == 0) {
            pipeSets.add(new PlasticPipeSet());
        }
    }

    // decrease bird lives by 1 and remove pipe
    protected void birdCollided(PipeSet pipe) {
        bird.setLivesLeft(bird.getLivesLeft() - 1);
        pipe.destroy();
        unusedPipes.add(pipe);
        pipe.setBirdPassed(true);
    }

    protected void renderInstructionScreen(Input input) {
        FONT.drawString(INSTRUCTION_MSG, (Window.getWidth()/2.0-(FONT.getWidth(INSTRUCTION_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        if (input.wasPressed(Keys.SPACE)) {
            gameOn = true;
            gameStarted = true;
        }
    }

    protected void renderScore() {
        String scoreMsg = SCORE_MSG + score;
        FONT.drawString(scoreMsg, SCORE_MSG_X, SCORE_MSG_Y);
    }

    protected void renderLevelUpScreen() {
        FONT.drawString(LEVEL_UP_MSG, (Window.getWidth()/2.0-(FONT.getWidth(LEVEL_UP_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
    }

    protected void renderWinScreen() {
        FONT.drawString(CONGRATS_MSG, (Window.getWidth()/2.0-(FONT.getWidth(CONGRATS_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    protected boolean getPassed() {
        return passed;
    }

    private void resetTimescale() {
        timescale = TIMESCALE_MIN;
        PipeSet.pipeSpeed = PipeSet.INIT_PIPE_SPEED;
        PipeSet.spawnFrame = PipeSet.INIT_SPAWN_FRAME;
    }

    private void renderGameOverScreen() {
        FONT.drawString(GAME_OVER_MSG, (Window.getWidth()/2.0-(FONT.getWidth(GAME_OVER_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    private boolean birdOutOfBound() {
        return (bird.getY() > Window.getHeight()) || (bird.getY() < 0);
    }

    private void increaseTimescale() {
        timescale += 1;
        PipeSet.pipeSpeed *= TIMESCALE_INCREMENT;
        PipeSet.spawnFrame = (int)Math.round(PipeSet.spawnFrame / TIMESCALE_INCREMENT);
    }

    private void decreaseTimescale() {
        timescale -= 1;
        PipeSet.pipeSpeed /= TIMESCALE_INCREMENT;
        PipeSet.spawnFrame = (int)Math.round(PipeSet.spawnFrame * TIMESCALE_INCREMENT);
    }
}