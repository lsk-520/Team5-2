package io.github.unisim.achievement;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.unisim.world.World;

/**
 * Represents an award a user can win by fulfilling requirements.
 */
public class Achievement {
    public String name;
    public String description;
    public Image icon;

    private float scoreFactor;

    private boolean achieved;
    AchievementRequirement requirement;

    /**
     * Create a new achievement to add to the list of achievements a player can achieve throughout,
     * and at the end of, the game.
     *
     * @param name The name of the achievement.
     * @param description A description of how the achievement was achieved.
     * @param icon The image to display alongside the achievement.
     * @param scoreFactor The amount to change the score by when the achievement is achieved.
     * @param requirement The requirement to achieve the achievement.
     */
    public Achievement(String name, String description, Image icon, float scoreFactor, AchievementRequirement requirement) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.scoreFactor = scoreFactor;
        achieved = false;
        this.requirement = requirement;
    }

    /**
     * Returns the amount to change score by. Sets scoreFactor to 0 so it can only affect score once.
     *
     * @return The amount to change score by.
     */
    public float getScoreChange() {
        float scoreChange = scoreFactor;
        scoreFactor = 0;
        return scoreChange;
    }

    /**
     * Checks if the achievement requirements have been met, and so the achievement has been achieved.
     *
     * @param world The world of the game.
     * @return True if the achievement has been achieved, when before it had not, false otherwise.
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
    }
}
