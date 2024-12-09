package io.github.unisim.achievement;

import com.badlogic.gdx.Gdx;
import io.github.unisim.Timer;
import io.github.unisim.world.World;

/**
 * A requirement that depends on the score maintained over a specific amount of time.
 */
public class MaintainScoreRequirement implements AchievementRequirement {
    public Timer timeScoreMaintained;
    public float timeRequired;
    public float scoreRequired;

    /**
     * Creates a requirement dependent on how long a score can be maintained.
     *
     * @param timeRequired The time required for the score to be maintained.
     * @param scoreRequired The number the score must stay above.
     */
    public MaintainScoreRequirement(float timeRequired, float scoreRequired) {
        this.timeRequired = timeRequired;
        this.scoreRequired = scoreRequired;
        timeScoreMaintained = new Timer(timeRequired);
    }

    /**
     * @return True if the score has been maintained over the time required, false otherwise.
     */
    @Override
    public boolean checkAchieved(World world) {
        if (world.getScore() < scoreRequired) {
            timeScoreMaintained = new Timer(timeRequired);
        }
        if (!timeScoreMaintained.tick(Gdx.graphics.getDeltaTime() * 1000f)) {
            return true;
        }
        return false;
    }
}
