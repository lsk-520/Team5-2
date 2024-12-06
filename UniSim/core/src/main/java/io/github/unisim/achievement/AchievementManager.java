package io.github.unisim.achievement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.unisim.Timer;
import io.github.unisim.building.BuildingType;
import io.github.unisim.world.World;
import java.util.ArrayList;

public class AchievementManager {
    // achievements possible to achieve
    private ArrayList<Achievement> achievements = new ArrayList<>();
    // undisplayed achieved achievements
    //private ArrayList<Achievement> undisplayedAchievements;
    // current displayed achievement
    private Achievement currentAchievement;
    // displayed achievements
    //private ArrayList<Achievement> achievedAchievements;

    private ArrayList<Achievement> displayQueue = new ArrayList<>();


    private World world;
    private Timer displayTimer;

    public AchievementManager(World world) {
        displayTimer = new Timer(10_000);
        this.world = world;

        achievements.add(new Achievement("Welcome!",
            "Increase score to win the game!",
            new Image(new Texture(Gdx.files.internal("buildings/accommodation.png"))),
            0,
            new WelcomeRequirement()));
        displayQueue.add(achievements.get(0));

        achievements.add(new Achievement("High Achiever",
            "You've maintained a high score for over a minute!",
            new Image(new Texture(Gdx.files.internal("achievements/trophy.png"))),
            10,
            new MaintainScoreRequirement(60_000f, 80f)));
        achievements.add(new Achievement("Maximalist",
            "You've placed 20 buildings!",
            new Image(new Texture(Gdx.files.internal("achievements/trophy.png"))),
            10,
            new BuildingPlacementRequirement(BuildingType.values(), 20)));
        achievements.add(new Achievement("Academic weapon",
            "Your students can work hard and do well!",
            new Image(new Texture(Gdx.files.internal("buildings/library.png"))),
            10,
            new BuildingPlacementRequirement(new BuildingType[]{BuildingType.LEARNING}, 5)));

        // add achievements in
    }

    public Achievement getCurrentAchievement() {
        return currentAchievement;
    }

    public boolean displaying() {
        if (currentAchievement != null) {
            return true;
        }
        return false;
    }

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

    public float achievementDisplayTick() {
        // if theres an achievement being displayed, tick though display time
        // check if display time is finished, and if so, call nextevent
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

    private void checkChangedAchievements() {
        for (Achievement achievement : achievements) {
            if (achievement.checkAchieved(world)) {
                displayQueue.add(achievement);
            }
        }
    }

    public void reset() {
        currentAchievement = null;
        displayTimer.reset();
        for (Achievement achievement : achievements) {
            achievement.reset();
        }
    }

}
