package io.github.unisim.achievement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.unisim.Timer;
import io.github.unisim.building.BuildingType;
import io.github.unisim.world.World;
import java.util.ArrayList;

/**
 * Manages the achievements accessible to the player, and the common methods.
 */
public class AchievementManager {
    private ArrayList<Achievement> achievements = new ArrayList<>();
    private Achievement currentAchievement;

    // The queue of achievements waiting to be displayed.
    private ArrayList<Achievement> displayQueue = new ArrayList<>();

    private World world;
    private Timer displayTimer;
    private Image achievementImage;

    /**
     * Adds the achievements to the list possible to achieve by the player. Sets the display timer for the initial
     * welcome message to the player.
     */
    public AchievementManager(World world) {
        displayTimer = new Timer(10_000);
        this.world = world;
        achievementImage = new Image(new Texture("achievements/trophy.png"));

        // The initial message to display to the user when the game starts.
        achievements.add(new Achievement("Welcome!",
            "Increase score to win the game!",
            achievementImage,
            0,
            new WelcomeRequirement()));
        displayQueue.add(achievements.get(0));

        // Adds possible achievements to achieve

        achievements.add(new Achievement("High Achiever",
            "You've maintained a high score for over a minute!",
            achievementImage,
            10,
            new MaintainScoreRequirement(60_000f, 80f)));
        achievements.add(new Achievement("Maximalist",
            "You've placed 20 buildings!",
            achievementImage,
            10,
            new BuildingPlacementRequirement(BuildingType.values(), 20)));
        achievements.add(new Achievement("Academic weapon",
            "Your students can work hard and do well!",
            achievementImage,
            10,
            new BuildingPlacementRequirement(new BuildingType[]{BuildingType.LEARNING}, 5)));
        achievements.add(new Achievement("Slacker",
            "You've placed 0 buildings! Is this even a university?",
            achievementImage,
            1,
            new BuildingPlacementRequirement(BuildingType.values(), 0, 500)));
        achievements.add(new Achievement("Minimalist",
            "You've only placed 5 buildings!",
            achievementImage,
            10,
            new BuildingPlacementRequirement(BuildingType.values(), 5, 500)));

    }

    public Achievement getCurrentAchievement() {
        return currentAchievement;
    }

  /**
   * @return True if the there is an achievement currently being displayed, false otherwise.
   */
  public boolean displaying() {
        if (currentAchievement != null) {
            return true;
        }
        return false;
    }

    /**
     * Displays the next achievement in the queue.
     *
     * @param next If the current achievement's time to display is up.
     */
    public void displayAchievement(boolean next) {
        if (displayQueue.isEmpty()) {
            currentAchievement = null;
        }
        else {
            currentAchievement = displayQueue.remove(0);
        }

        if (!displayQueue.isEmpty() && currentAchievement == null) {
            currentAchievement = displayQueue.remove(0);
        }
        else if (displayQueue.isEmpty() && next) {
            currentAchievement = null;
        }
    }

    /**
     * Checks if any achievement has been achieved. Manages currently displayed achievements, and the queue for
     * displaying them.
     *
     * @return The amount to change the score by if a new achievement is achieved and displayed.
     */
    public float achievementDisplayTick() {
        // If an achievement is currently being displayed, check the display timer isn't up, and return the amount
        // to change the score.
        if (currentAchievement != null) {
            float scoreChange = currentAchievement.getScoreChange();
            if (!displayTimer.tick(Gdx.graphics.getDeltaTime() * 1000f)) {
                displayAchievement(true);
                displayTimer.reset();
            }
            return scoreChange;
        }
        displayAchievement(false);
        checkChangedAchievements();
        return 0;
    }

    /**
     * Iterates over each achievement and checks if the status of achievement has changed.
     */
    private void checkChangedAchievements() {
        for (Achievement achievement : achievements) {
            if (achievement.checkAchieved(world)) {
                displayQueue.add(achievement);
            }
        }
    }

  /**
   * Sets current achievement to not null
   * Exclusively for testing
   */
  public void testCurrentAchievement(){
      currentAchievement = achievements.get(1);
    }

    public void reset() {
        currentAchievement = null;
        displayTimer.reset();
        for (Achievement achievement : achievements) {
            achievement.reset();
        }
    }

}
