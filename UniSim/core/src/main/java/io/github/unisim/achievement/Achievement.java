package io.github.unisim.achievement;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.unisim.world.World;

public class Achievement {
    public String name;
    public String description;
    public Image icon;

    private float scoreFactor;

    private boolean achieved;
    AchievementRequirement requirement;

    public Achievement(String name, String description, Image icon, float scoreFactor, AchievementRequirement requirement) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.scoreFactor = scoreFactor;
        achieved = false;
        this.requirement = requirement;
    }

    public float getScoreChange() {
        float scoreChange = scoreFactor;
        scoreFactor = 0;
        return scoreChange;
    }

    /**
     * Checks if the achievement requirements have been met, and so the achievement has been achieved.
     *
     * @param world The world of the game.
     * @return True if the achievement achieved, false otherwise.
     */
    public boolean checkAchieved(World world) {
        boolean reqAchieved = requirement.checkAchieved(world);
        if (!achieved && reqAchieved) {
            achieved = true;
            return true;
        }
        return false;
    }

    public void reset() {
        achieved = false;
        //does this ever actually change though
    }
}
