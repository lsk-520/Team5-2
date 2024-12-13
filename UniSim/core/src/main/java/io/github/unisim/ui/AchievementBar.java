package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import io.github.unisim.GameState;
import io.github.unisim.achievement.Achievement;
import io.github.unisim.achievement.BuildingPlacementRequirement;
import io.github.unisim.achievement.WelcomeRequirement;
import io.github.unisim.building.BuildingType;
import io.github.unisim.world.World;

/**
 * A bar to display achievements.
 */
public class AchievementBar {
    ShapeActor bar;
    private Table achievementsTable = new Table();
    private Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

    private World world;
    // can be null
    private Achievement currentAchievement;

    private Label achievementNameLabel;
    private Label achievementDescriptionLabel;

    private Cell<Label> achievementNameCell;
    private Cell<Label> achievementDescriptionCell;
    private Cell<Image> achievementIconCell;

  /**
   * Create a new AchievementBar and add its components.
   *
   * @param stage - The stage to draw the achievementBar.
   * @param world - The world to access achievements from.
   */
  public AchievementBar(Stage stage, World world) {
        this.world = world;

        currentAchievement = new Achievement("Start",
            "game",
            new Image(new Texture(Gdx.files.internal("buildings/accommodation.png"))),
            0,
            new WelcomeRequirement());

        achievementNameLabel = new Label(currentAchievement.name, skin);
        achievementDescriptionLabel = new Label(currentAchievement.description, skin);
        achievementNameLabel.setWrap(true);
        achievementDescriptionLabel.setWrap(true);

        achievementsTable.center().center();
        achievementIconCell = achievementsTable.add(currentAchievement.icon);
        achievementNameCell = achievementsTable.add(achievementNameLabel).align(Align.center);
        achievementDescriptionCell = achievementsTable.add(achievementDescriptionLabel).align(Align.center);
        achievementsTable.row();

        bar = new ShapeActor(GameState.UISecondaryColour);
        stage.addActor(bar);
        stage.addActor(achievementsTable);
        bar.setVisible(false);
        achievementsTable.setVisible(false);
    }

  /**
   * Called when the ui needs to be updated, usually on every frame.
   */
  public void update() {
        currentAchievement = world.getCurrentAchievement();
        if (currentAchievement != null) {
            setVisible(true);
        }
        else {
            setVisible(false);
        }
        // and world.getNextAchievement()
        // then call and change visability + currentAchievement

        if (currentAchievement != null) {
            // currentAchievement = world.getCurrentAchievement()
            achievementNameLabel.setText(currentAchievement.name);
            achievementNameLabel.setAlignment(Align.center);
            achievementDescriptionLabel.setText(currentAchievement.description);
            achievementDescriptionLabel.setAlignment(Align.center);

            achievementNameCell.setActor(achievementNameLabel);
            achievementDescriptionCell.setActor(achievementDescriptionLabel);
            achievementIconCell.setActor(currentAchievement.icon);
        }
    }
  /**
   * Update the bounds of the background & table actors to fit the new size of the screen.

   * @param width - The new width of the screen in pixels.
   * @param height - The enw height of the screen in pixels.
   */
    public void resize(int width, int height) {
        float barWidth = height * 0.6f;
        float barHeight = height * 0.1f;
        bar.setBounds(width - barWidth, height-(barHeight * 2f)-(height * 0.05f), barWidth, barHeight);
        achievementsTable.setBounds(width - barWidth, height-(barHeight * 2f)-(height * 0.05f), barWidth, barHeight);

        achievementNameLabel.setFontScale(height * 0.002f);
        achievementDescriptionLabel.setFontScale(height * 0.0015f);

        achievementIconCell.width(barWidth * 0.2f).height(height * 0.1f);
        achievementNameCell.width(barWidth * 0.4f / 2f).height(height * 0.10f);
        achievementDescriptionCell.width(barWidth * 1.2f / 2f).height(height * 0.10f);
    }

    public void reset() {
        setVisible(false);
    }

    public void setVisible(boolean visible) {
        achievementsTable.setVisible(visible);
        bar.setVisible(visible);
    }
}
