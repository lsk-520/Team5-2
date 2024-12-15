package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.unisim.GameState;

public class TutorialScreen implements Screen {
  private Stage stage;
  private Table table;
  private Skin skin;
  private Label titleLabel;
  private Label descriptionLabel;
  private TextButton mMenuButton;
  private TextButton settingsButton;
  private InputMultiplexer inputMultiplexer = new InputMultiplexer();

  public TutorialScreen() {
    stage = new Stage();
    table = new Table();
    skin = GameState.defaultSkin;

    String descriptionText = "  - Rotate buildings when placing them by pressing 'R'\n"
                           + "  - Watch out for events happening through the game\n"
                           + "  - Change your username by going into settings\n"
                           + "  - Keep buildings close together to increase satisfaction - don't make your students walk too far!\n"
                           + "  - Place the same number of different types of buildings\n"
                           + "  - Remember you can press pause at any time! ";

    // Title label
    titleLabel = new Label("Playing tips!", skin);
    titleLabel.setFontScale(1.5f);

    // Description label
    descriptionLabel = new Label(descriptionText, skin);
    descriptionLabel.setWrap(true);

    // Main menu button
    mMenuButton = new TextButton("Main menu", skin);
    mMenuButton.setPosition(150, 80);
    mMenuButton.setSize(100,50);
    mMenuButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        GameState.currentScreen = GameState.startScreen;
      }
    });

    // Settings button
    settingsButton = new TextButton("Settings", skin);
    settingsButton.setPosition(150,80);
    settingsButton.setSize(100,50);
    settingsButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        GameState.currentScreen = GameState.settingScreen;
      }
    });

    Table buttonTable = new Table();
    buttonTable.add(mMenuButton).width(150).height(40).padRight(8);
    buttonTable.add(settingsButton).width(150).height(40);

    table.setFillParent(true);
    table.center().center();
    table.pad(100,100,100,100);
    table.add(titleLabel).expandX().align(Align.center).padBottom(8);
    table.row();
    table.add(descriptionLabel).center().width(350).height(180).padBottom(8);
    table.row();
    table.add(buttonTable).expandX().center().width(350).height(40);
    //table.setDebug(true);
    stage.addActor(table);

    inputMultiplexer.addProcessor(GameState.fullscreenInputProcessor);
    inputMultiplexer.addProcessor(stage);
  }

  @Override
  public void show() {}

  @Override
  public void render(float delta) {
    ScreenUtils.clear(GameState.UISecondaryColour);

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
