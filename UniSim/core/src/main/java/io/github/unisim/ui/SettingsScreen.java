package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.unisim.GameState;

/**
 * The settings screen that allows the player to adjust the volume.
 */
public class SettingsScreen implements Screen {
  private Stage stage;
  private Table table;
  private Skin skin = GameState.defaultSkin;
  private Slider volumeSlider;
  private Label volumeLabel;
  private TextButton backButton;
  private TextButton changeUsernameButton;
  private TextButton tutorialButton;
  private InputMultiplexer inputMultiplexer = new InputMultiplexer();

  final String[] newUsername = {""};

  /**
   * Create a new Settings screen and draw the initial UI layout.
   */
  public SettingsScreen() {
    stage = new Stage();
    table = new Table();

    // Volume label
    volumeLabel = new Label("Volume: ", skin);
    volumeLabel.setColor(new Color(0.9f, 0.9f, 0.9f, 1.0f));

    // Volume slider
    volumeSlider = new Slider(0.0f, 1.0f, 0.1f, false, skin);
    volumeSlider.setValue(GameState.settings.getVolume()); // Set current volume
    volumeSlider.setPosition(150, 150);
    volumeSlider.setSize(200, 50);
    volumeSlider.addListener(event -> {
      // Adjust the game volume based on slider value
      GameState.settings.setVolume(volumeSlider.getValue());
      return false;
    });

    // Username input button
    changeUsernameButton = new TextButton("Change Username", skin);
    changeUsernameButton.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        Dialog dialog = new Dialog("", skin) {
          @Override
          protected void result(Object object) {
            if ((Boolean) object) {
              // Set new username if the user enters something
              TextField textField = findActor("usernameField");
              String username = textField.getText();
              if (!username.isEmpty()) {
                // Input validation3
                if (username.contains(" ")) {
                  username = username.replaceAll(" ", "");
                }
                newUsername[0] = username;
              }
            }
          }
        };

        // Set up the textField and add buttons
        TextField textField = new TextField("", skin);
        textField.setName("usernameField");
        dialog.getContentTable().add(textField).width(200).pad(10);
        dialog.button("OK", true);
        dialog.button("Cancel", false);
        dialog.show(stage);
        stage.setKeyboardFocus(textField);
      }
    });

    // Tutorial button
    tutorialButton = new TextButton("Tutorial", skin);
    tutorialButton.setSize(200, 50);
    tutorialButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        GameState.currentScreen = GameState.tutorialScreen;
      }
    });

    // Back button
    backButton = new TextButton("Back", skin);
    backButton.setPosition(150, 80);
    backButton.setSize(200, 50);
    backButton.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        // Go back to the start menu
        GameState.currentScreen = GameState.startScreen;
      }
    });

    // Add UI elements to stage
    table.setFillParent(true);
    table.center().center();
    table.pad(100, 100, 100, 100);
    table.add(volumeLabel).center();
    table.row();
    table.add(volumeSlider).center().width(250).height(57);
    table.row();
    table.add(changeUsernameButton).center().width(250).height(57).padBottom(10);
    table.row();
    table.add(tutorialButton).center().width(250).height(57).padBottom(10);
    table.row();
    table.add(backButton).center().width(250).height(57);
    //table.setDebug(true);
    stage.addActor(table);

    inputMultiplexer.addProcessor(GameState.fullscreenInputProcessor);
    inputMultiplexer.addProcessor(stage);
  }

  @Override
  public void show() {}

  @Override
  public void render(float delta) {
    // Clear the screen
    ScreenUtils.clear(GameState.UISecondaryColour);

    // Resetting settings based on any changes
    if (!(newUsername[0] == "")) {
      GameState.settings.setUsername(newUsername[0]);
    }
    GameState.settings.setVolume(volumeSlider.getValue());

    // Draw the stage containing the volume slider and buttons
    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {
    Gdx.input.setInputProcessor(inputMultiplexer);
  }

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    stage.dispose();
    skin.dispose();
  }
}
