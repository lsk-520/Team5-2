package io.github.unisim.achievement;

import com.badlogic.gdx.Gdx;
import io.github.unisim.Timer;
import io.github.unisim.world.World;

public class MaintainScoreRequirement implements AchievementRequirement {
    public Timer timeScoreMaintained;
    public float timeRequired;
    public float scoreRequired;

    public MaintainScoreRequirement(float timeRequired, float scoreRequired) {
        this.timeRequired = timeRequired;
        this.scoreRequired = scoreRequired;
        timeScoreMaintained = new Timer(timeRequired);
    }

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
