package io.github.unisim.ui;

import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.unisim.GameState;

import java.util.Random;

/**
 * The start menu screen which presents the player with the option to start the
 * game
 * or access the settings menu.
 */
public class StartMenuScreen implements Screen {
  private Stage stage;
  private Table table;
  private Skin skin;
  private Label unisimLabel;
  private Label usernameLabel;
  private TextButton playButton;
  private TextButton settingsButton;
  private TextButton tutorialButton;
  private InputMultiplexer inputMultiplexer = new InputMultiplexer();

  private Random random = new Random();

  /**
   * Create a new StartMenuScreen and draw the initial UI layout.
   */
  public StartMenuScreen() {
    stage = new Stage();
    table = new Table();
    skin = GameState.defaultSkin;

    // Set new random username
    String randomUsername = random.ints(97,123)
      .limit(5)
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
      .toString();
    GameState.settings.setUsername(randomUsername);

    // UniSim title
    unisimLabel = new Label("UniSim", skin);
    unisimLabel.setFontScale(2.5f);

    // Welcome message
    usernameLabel = new Label("You're playing as: " + GameState.settings.getUsername() + "!", skin);
    usernameLabel.setFontScale(1f);

    // Play button
    playButton = new TextButton("Play", skin);
    playButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        // Switch to the game screen
        GameState.currentScreen = GameState.gameScreen;
        GameState.mainScreen = GameState.gameScreen;
      }
    });

    // Settings button
    settingsButton = new TextButton("Settings", skin);
    settingsButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        // Switch to the settings screen
        GameState.currentScreen = GameState.settingScreen;
      }
    });

    tutorialButton = new TextButton("Tutorial", skin);
    tutorialButton.setSize(200, 50);
    tutorialButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        GameState.currentScreen = GameState.tutorialScreen;
      }
    });

    // Add UI elements to the stage
    table.setFillParent(true);
    table.center().center();
    table.pad(100, 100, 100, 100);
    table.add(unisimLabel).expandX().align(Align.center).padBottom(20);
    table.row();
    table.add(usernameLabel).expandX().align(Align.center).padBottom(20);
    table.row();
    table.add(playButton).center().width(250).height(70).padBottom(8);
    table.row();
    table.add(settingsButton).center().width(250).height(40).padBottom(8);
    table.row();
    table.add(tutorialButton).center().width(250).height(40).padBottom(8);
    stage.addActor(table);

    inputMultiplexer.addProcessor(GameState.fullscreenInputProcessor);
    inputMultiplexer.addProcessor(stage);
  }

  @Override
  public void show() {
  }

  @Override
  public void render(float delta) {
    // Clear the screen
    ScreenUtils.clear(GameState.UISecondaryColour);

    // Set welcome label
    usernameLabel.setText("You're playing as: " + GameState.settings.getUsername());

    // Draw the stage containing buttons
    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
    Gdx.input.setInputProcessor(inputMultiplexer);
  }

  @Override
  public void hide() {
  }

  @Override
  public void dispose() {
    stage.dispose();
    skin.dispose();
  }
}
